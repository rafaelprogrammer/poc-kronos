<template>
<div>
  <modal-cadastro-competencia :desabilita-incluir="true" :niveis="niveis" />
  <v-layout wrap>
    <v-flex xs12 sm2 md3>
      <modal-cadastra-atividade-disciplina-competencia :id-disciplina="idDisciplina" :id-atividade="idAtividade" 
      :id-curso="idCurso" :id-periodo="idPeriodo" :id-sub-fase="idSubFase"
      @atividadeDisciplinaCompetenciaCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="secundary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" hide-actions :items="atividadesDisciplinasCompetencias" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigoCompetencia }}</td>
          <td>{{ props.item.bncc | booleanFilter}}</td>
          <td>{{ props.item.siglaSubFase }}</td>
          <td width="50%">
            <v-tooltip bottom>
                <span slot="activator" class="">{{ props.item.descricaoCompetencia | limitCaracteres(100) }}</span>
                <span>{{ props.item.descricaoCompetencia }}</span>
            </v-tooltip>
          </td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" small title="Opções para competências" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
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
import AtividadeDisciplinaCompetencia from "@/model/atividade/competencia/AtividadeDisciplinaCompetencia";
import ModalCadastraAtividadeDisciplinaCompetencia from "./ModalCadastraAtividadeDisciplinaCompetencia";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroCompetencia from "../estruturapedagogica/ModalCadastroCompetencia";

export default {
  name: "ListaAtividadeDisciplinaCompetencia",
  props: {
    idDisciplina: Number,
    idAtividade: Number,
    idSubFase: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraAtividadeDisciplinaCompetencia,
    ModalCadastroCompetencia
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      niveis: [],
      dialog: false,
      atividadesDisciplinasCompetencias: [],
      loading: false,
      cadastroEfetuado: false,
      atividadeDisciplinaCompetencia: new AtividadeDisciplinaCompetencia(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigoCompetencia"
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
          value: "descricaoCompetencia"
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
    async visualizar(atividadeDisciplinaCompetencia) {
      this.niveis = await baseCurricularService.comboNiveis();
      EventBus.$emit("visualizaCompetencia", atividadeDisciplinaCompetencia.idCompetencia);
    },
    excluir(atividadeDisciplinaCompetencia) {
      confirm("Você deseja realmente excluir Competência?") &&
        atividadeService.excluirAtividadeDisciplinaCompetencia(atividadeDisciplinaCompetencia).then(() => {
          this.atividadesDisciplinasCompetencias.splice(
            this.atividadesDisciplinasCompetencias.indexOf(atividadeDisciplinaCompetencia),
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
      this.atividadeDisciplinaCompetencia.idAtividade = this.idAtividade;
      this.atividadesDisciplinasCompetencias = await atividadeService.listarAtividadesDisciplinasCompetencias(
        this.atividadeDisciplinaCompetencia.idAtividade
      );
      this.loading = false;
    }
  }
};
</script>
