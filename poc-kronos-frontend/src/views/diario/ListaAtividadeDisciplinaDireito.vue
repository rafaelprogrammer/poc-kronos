<template>
<div>
  <modal-cadastro-direito :desabilita-incluir="true" :niveis="niveis" />
  <v-layout wrap>
    <v-flex xs12 sm2 md3>
      <modal-cadastra-atividade-disciplina-direito :id-disciplina="idDisciplina" :id-atividade="idAtividade"  
      :id-sub-fase="idSubFase" :id-curso="idCurso" :id-periodo="idPeriodo"
      @atividadeDisciplinaDireitoCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" hide-actions :items="atividadesDisciplinasDireitos" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigoDireito }}</td>
          <td>{{ props.item.bncc | booleanFilter}}</td>
          <td>{{ props.item.siglaSubFase }}</td>
          <td width="50%">
            <v-tooltip bottom>
                <span slot="activator" class="">{{ props.item.descricaoDireito | limitCaracteres(100) }}</span>
                <span>{{ props.item.descricaoDireito }}</span>
            </v-tooltip>
          </td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" small title="Opções para direitos" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
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
import AtividadeDisciplinaDireito from "@/model/atividade/direito/AtividadeDisciplinaDireito";
import ModalCadastraAtividadeDisciplinaDireito from "./ModalCadastraAtividadeDisciplinaDireito";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroDireito from "../estruturapedagogica/ModalCadastroDireito";

export default {
  name: "ListaAtividadeDisciplinaDireito",
  props: {
    idDisciplina: Number,
    idAtividade: Number,
    idSubFase: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraAtividadeDisciplinaDireito,
    ModalCadastroDireito
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      niveis: [],
      dialog: false,
      atividadesDisciplinasDireitos: [],
      loading: false,
      cadastroEfetuado: false,
      atividadeDisciplinaDireito: new AtividadeDisciplinaDireito(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigoDireito"
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
          value: "descricaoDireito"
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
    async visualizar(atividadeDisciplinaDireito) {
      this.niveis = await baseCurricularService.comboNiveis();
      EventBus.$emit("visualizaDireito", atividadeDisciplinaDireito.idDireito);
    },
    excluir(atividadeDisciplinaDireito) {
      confirm("Você deseja realmente excluir Direito?") &&
        atividadeService.excluirAtividadeDisciplinaDireito(atividadeDisciplinaDireito).then(() => {
          this.atividadesDisciplinasDireitos.splice(
            this.atividadesDisciplinasDireitos.indexOf(atividadeDisciplinaDireito),
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
      this.atividadeDisciplinaDireito.idAtividade = this.idAtividade;
      this.atividadesDisciplinasDireitos = await atividadeService.listarAtividadesDisciplinasDireitos(
        this.atividadeDisciplinaDireito.idAtividade
      );
      this.loading = false;
    }
  }
};
</script>
