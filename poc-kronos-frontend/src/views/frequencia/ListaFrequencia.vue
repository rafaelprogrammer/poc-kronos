<template>
<div>
  <modal-cadastro-reposicao @reposicaoRegistrada="cadastroEfetuado = $event"/>
  <v-layout wrap>
    <v-flex xs12 sm6 md2>
      <v-subheader>Frequências</v-subheader>
    </v-flex>
    <v-flex xs12 sm6 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm6 md9>
      <opcoes-frequencia :id-atividade="idAtividade" :id-sub-fase-execucao="idSubFaseExecucao" 
            :id-disciplina="idDisciplina" :ano-turma="anoTurma" :todos="true" />
    </v-flex>
    <v-flex xs12 sm12 md12>
      <v-data-table :loading="loading" item-key="aluno" :headers="headers" hide-actions :items="frequencias" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td :style="retornarStyleTipoFrequenciaAtestado(props.item)">
            <v-tooltip color="transparent" left>
              <span slot="activator" @mouseover="buscarFoto(props.item)">{{props.item.aluno}} - {{props.item.numeroAtividade}} - {{props.item.dataReposicao}} - {{retornarTipoFrequenciaAtestado(props.item)}}</span>
              <v-avatar :size="100">
              <img :src="foto && foto.dadosBase64 ? foto.dadosBase64 : '/static/doc-images/lists/avatar-default.jpg'" :alt="foto && foto.legenda ? foto.legenda : ''" />
              </v-avatar>
            </v-tooltip>
          </td>
          <td :style="retornarStyleTipoFrequenciaAtestado(props.item)">
            <span v-for="(freq, index) in props.item.frequencia" :key="index">
              <v-menu offset-y :disabled="verificarSeExisteFrequenciaAtestado(props.item)">
                <v-btn slot="activator" small icon :color="selecionarCorBotao(freq)">
                  {{freq === 'I' ? '?' : freq}}
                </v-btn>
                <v-list>
                  <v-list-tile v-if="freq === 'P' ? false : (!isFrequenciaAtestadoAD(props.item) ? true : false)" alt="Presença" @click="atualizarIndividual('P', index, props.item)">
                    <v-list-tile-title>P</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile v-if="freq === 'F' ? false : true" alt="Falta" @click="atualizarIndividual('F', index, props.item)">
                    <v-list-tile-title>F</v-list-tile-title>
                  </v-list-tile>
                  <v-list-tile v-if="freq === 'F' || !isFrequenciaAtestadoAD(props.item) ? true : false" alt="Falta" @click="atualizarIndividual('AD', index, props.item)">
                    <v-list-tile-title>AD</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile v-if="freq === 'I' ? false : (!isFrequenciaAtestadoAD(props.item) ? true : false)" alt="Indefinido" @click="atualizarIndividual('I', index, props.item)">
                    <v-list-tile-title>?</v-list-tile-title>
                  </v-list-tile>
                </v-list>
              </v-menu>
            </span>
          </td>
          <td :style="retornarStyleTipoFrequenciaAtestado(props.item)" >
            <span v-if="!verificarSeExisteFrequenciaAtestado(props.item)">
            <opcoes-frequencia :id-atividade="idAtividade" :id-sub-fase-execucao="idSubFaseExecucao" 
            :id-disciplina="idDisciplina" :frequencia="props.item" />
            </span>
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
import arquivoService from "@/service/arquivo/ArquivoService";
import frequenciaService from "@/service/frequencia/FrequenciaService";
import Frequencia from "@/model/frequencia/Frequencia";
import EventBus from "@/utils/EventBus";
import { limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroReposicao from "./ModalCadastroReposicao";
import OpcoesFrequencia from "./OpcoesFrequencia";

export default {
  name: "ListaFrequencia",
  props: {
    idSubFaseExecucao: Number,
    idDisciplina: Number,
    anoTurma: Number
  },
  components: {
    ModalCadastroReposicao,
    OpcoesFrequencia
  },
  filters: {
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      idAtividade: null,
      visualizaFoto: false,
      foto: null,
      cadastroEfetuado: false,
      dialog: false,
      frequencias: [],
      loading: true,
      frequencia: new Frequencia(),
      headers: [
        {
          text: "Alunos",
          align: "left",
          sortable: false
        },
        {
          text: "Frequência",
          align: "left",
          sortable: false,
          value: "frequencia"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    cadastroEfetuado(event) {
      if (event) {
       this.atualizar();
      }
    }
  },
  created() {
    EventBus.$on("pesquisaFrequencia", (idAtividade) => {
      this.idAtividade = idAtividade;
      this.atualizar();
    });
    EventBus.$on("atualizadoOpcoesFrequencia", () => {
      this.atualizar();
    });
  },
  methods: {
    selecionarCorBotao(freq) {
      switch (freq) {
        case Frequencia.PRESENCA():
          return "success";
        case Frequencia.ATIVIDADE_DOMICILIAR():
          return "orange";
        case Frequencia.FALTA():
          return "red accent-3";
        case Frequencia.FALTA_JUSTIFICADA():
          return "purple lighten-3";
        case Frequencia.INDEFINIDO():
          return "deep-purple lighten-3";
        case Frequencia.TRANSFERENCIA():
          return "lime";
        case Frequencia.CANCELADA():
          return "grey accent-3";
        default:
          return "primary";
      }
    },
    verificarSeExisteFrequenciaAtestado(freq) {
      return freq.tipoFrequenciaAtestado && freq.tipoFrequenciaAtestado !== '' && freq.tipoFrequenciaAtestado !== 'AD';
    },
    isFrequenciaAtestadoAD(freq) {
      return freq.tipoFrequenciaAtestado && freq.tipoFrequenciaAtestado !== '' && freq.tipoFrequenciaAtestado === 'AD';
    },
    retornarTipoFrequenciaAtestado(freq) {
      switch (freq.tipoFrequenciaAtestado) {
        case "T":
          return "Transferência - T";
        case "C":
          return "Cancelamento - C";
        case "FJ":
          return "Falta Justificada - FJ";
        case "AD":
          return "Atividade Domiciliar - AD";
        default:
          break;
      }
    },
    retornarStyleTipoFrequenciaAtestado(freq) {
      return freq.tipoFrequenciaAtestado ? "background: #CFD8DC;" : "";
    },
    async buscarFoto(frequencia) {
      this.foto = await arquivoService.buscarFoto(frequencia.idPessoa);
      this.visualizaFoto = true;
    },
    atualizarIndividual(tipo, index, frequencia) {
      frequencia.frequencia[index] = tipo;
      frequenciaService.atualizarIndividual(frequencia).then((dados) => {
        if (dados) {
          this.atualizar();
        }
      });
    },
    atualizar() {
      setTimeout(() => {
        this.pesquisar();
      }, 500);
    },
    async pesquisar() {
      this.loading = true;
      this.frequencias = await frequenciaService.listarTodos(
        this.idDisciplina,
        this.idAtividade,
        this.idSubFaseExecucao,
        this.anoTurma
      );
      this.loading = false;
    }
  }
};
</script>
