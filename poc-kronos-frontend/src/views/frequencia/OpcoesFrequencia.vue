<template>
<div>
    <v-menu offset-y>
      <v-btn slot="activator" small icon title="Opções para frequência" color="primary"><v-icon>more_vert</v-icon></v-btn>
      <v-list>
        <v-list-tile v-if="!isFrequenciaAtestadoAD()" alt="TP" @click="criarTipoTP(frequencia)">
          <v-list-tile-title>TP</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile v-if="!todos" alt="TF" @click="criarTipoTF(frequencia)">
          <v-list-tile-title>TF</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile v-if="todos"  alt="TAD" @click="criarTipoAD(frequencia)">
          <v-list-tile-title>TAD</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile v-if="todos"  alt="FJ/C/T" @click="criarTiposAtestadosTodos(frequencia)">
          <v-list-tile-title>FJ/C/T</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile v-if="!todos && !isFrequenciaAtestadoAD()" alt="TF" @click="limpar(frequencia)">
          <v-list-tile-title>Limpar</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile alt="TF" v-if="!todos && verificarFrequenciaTodosIndefinidos(frequencia) && !isFrequenciaAtestadoAD()" @click="registrarReposicao(frequencia)">
          <v-list-tile-title>Registrar Reposição</v-list-tile-title>
        </v-list-tile>
        <v-divider></v-divider>
        <v-list-tile alt="TF" v-if="!todos && frequencia.dataReposicao && isFrequenciaAtestadoAD()" @click="anularReposicao(frequencia)">
          <v-list-tile-title>Anular Reposição</v-list-tile-title>
        </v-list-tile>
      </v-list>
    </v-menu>
</div>
</template>
<script>
import frequenciaService from "@/service/frequencia/FrequenciaService";
import Frequencia from "@/model/frequencia/Frequencia";
import EventBus from "@/utils/EventBus";

export default {
  name: "OpcoesFrequencia",
  props: {
    idAtividade: Number,
    idSubFaseExecucao: Number,
    idDisciplina: Number,
    anoTurma: Number,
    frequencia: Object,
    todos: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    isFrequenciaAtestadoAD() {
      return this.frequencia && this.frequencia.tipoFrequenciaAtestado && this.frequencia.tipoFrequenciaAtestado === 'AD';
    },
    verificarFrequenciaTodosIndefinidos(frequencia) {
      if (frequencia.frequencia && frequencia.frequencia.map) {
        let dadosIndefinidos = [];
        frequencia.frequencia.map(f => {
          if (f === Frequencia.INDEFINIDO()) {
            dadosIndefinidos.push(f);
          }
        });
        return dadosIndefinidos.length === frequencia.numeroAtividade;
      }
      return false;
    },
    criarTiposAtestadosTodos() {
      if(this.todos) {
        frequenciaService.criarTiposAtestadosTodos(this.idDisciplina, this.idAtividade, this.idSubFaseExecucao, this.anoTurma).then((dados) => {
            if (dados) {
              this.atualizar();
            }
          });
      }
    },
    criarTipoAD(frequencia) {
      if(this.todos && !this.frequencia) {
          frequenciaService.criarTipoADTodos(this.idDisciplina, this.idAtividade, this.idSubFaseExecucao, this.anoTurma).then((dados) => {
            if (dados) {
              this.atualizar();
            }
          });
        } else {
        frequenciaService.criarTipoAD(frequencia).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      }
    },
    criarTipoTP(frequencia) {
      if(this.todos && !this.frequencia) {
        frequenciaService.criarTipoTPTodos(this.idDisciplina, this.idAtividade, this.idSubFaseExecucao, this.anoTurma).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      } else {
        frequenciaService.criarTipoTP(frequencia).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      }
    },
    criarTipoTF(frequencia) {
      if(this.todos && !this.frequencia) {
        frequenciaService.criarTipoTFTodos(this.idDisciplina, this.idAtividade, this.idSubFaseExecucao, this.anoTurma).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      } else {
        frequenciaService.criarTipoTF(frequencia).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      }
    },
    limpar(frequencia) {
      if(this.todos && !this.frequencia) {
        frequenciaService.limparTodos(this.idDisciplina, this.idAtividade, this.idSubFaseExecucao).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      } else {
        frequenciaService.limpar(frequencia).then((dados) => {
          if (dados) {
            this.atualizar();
          }
        });
      }
    },
    registrarReposicao(frequencia) {
      EventBus.$emit("abrirReposicao", frequencia);
    },
    anularReposicao(frequencia) {
      frequenciaService.anularReposicao(frequencia).then((dados) => {
        if (dados) {
          this.atualizar();
        }
      });
    },
    atualizar() {
      EventBus.$emit("atualizadoOpcoesFrequencia");
    }
  }
};
</script>
