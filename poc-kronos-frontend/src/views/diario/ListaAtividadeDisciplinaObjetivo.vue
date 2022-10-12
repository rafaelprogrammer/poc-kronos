<template>
<div>
  <modal-cadastro-objetivo :desabilita-incluir="true" :faixas-anos="faixasAnos" />
  <v-layout wrap>
    <v-flex xs12 sm2 md3>
      <modal-cadastra-atividade-disciplina-objetivo 
      :id-disciplina="idDisciplina" :id-atividade="idAtividade" :id-sub-fase="idSubFase"
      :id-curso="idCurso" :id-periodo="idPeriodo"
      @atividadeDisciplinaObjetivoCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" hide-actions :items="atividadesDisciplinasObjetivos" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigoObjetivo }}</td>
          <td>{{ props.item.bncc | booleanFilter}}</td>
          <td>{{ props.item.siglaSubFase }}</td>
          <td width="50%">
            <v-tooltip bottom>
                <span slot="activator" class="">{{ props.item.descricaoObjetivo | limitCaracteres(100) }}</span>
                <span>{{ props.item.descricaoObjetivo }}</span>
            </v-tooltip>
          </td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" small title="Opções para objetivos" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Analisar" @click="excluir(props.item)">
                  <v-list-tile-title>Excluir</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Visualizar" @click="visualizar(props.item)">
                  <v-list-tile-title>Visualizar</v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu>
          </td>
        </template>
        <template slot="no-data">
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</div>
</template>
<script>
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import atividadeService from "@/service/atividade/AtividadeService";
import AtividadeDisciplinaObjetivo from "@/model/atividade/objetivo/AtividadeDisciplinaObjetivo";
import ModalCadastraAtividadeDisciplinaObjetivo from "./ModalCadastraAtividadeDisciplinaObjetivo";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroObjetivo from "../estruturapedagogica/ModalCadastroObjetivo";

export default {
  name: "ListaAtividadeDisciplinaObjetivo",
  props: {
    idDisciplina: Number,
    idAtividade: Number,
    idSubFase: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraAtividadeDisciplinaObjetivo,
    ModalCadastroObjetivo
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      faixasAnos: [],
      dialog: false,
      atividadesDisciplinasObjetivos: [],
      loading: false,
      cadastroEfetuado: false,
      atividadeDisciplinaObjetivo: new AtividadeDisciplinaObjetivo(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigoObjetivo"
        },
        {
          text: "Bncc",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Bimestre",
          align: "left",
          sortable: false,
          value: "siglaSubFase"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricaoObjetivo"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    idAtividade() {
      this.atualizar();
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    }
  },
  created () {
    this.atualizar();
  },
  methods: {
    async visualizar(atividadeDisciplinaObjetivo) {
      this.faixasAnos = await baseCurricularService.faixasAnos();
      EventBus.$emit("visualizaObjetivo", atividadeDisciplinaObjetivo.idObjetivo);
    },
    excluir(atividadeDisciplinaObjetivo) {
      confirm("Você deseja realmente excluir Objetivo?") &&
        atividadeService.excluirAtividadeDisciplinaObjetivo(atividadeDisciplinaObjetivo).then(() => {
          this.atividadesDisciplinasObjetivos.splice(
            this.atividadesDisciplinasObjetivos.indexOf(atividadeDisciplinaObjetivo),
            1
          );
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      this.atividadeDisciplinaObjetivo.idAtividade = this.idAtividade;
      this.atividadesDisciplinasObjetivos = await atividadeService.listarAtividadesDisciplinasObjetivos(
        this.atividadeDisciplinaObjetivo.idAtividade
      );
      this.loading = false;
    }
  }
};
</script>
