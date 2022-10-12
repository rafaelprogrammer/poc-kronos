<template>
<div>
  <section-definition title="Diário/Frequência" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Diário/Frequência</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md4>
        <v-select v-model="idChaveTurma" item-text="nome" item-value="id" :items="turmas"
        @change="atualizarIdChaveTurma(idChaveTurma)"
        label="Turma/Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
            <v-select v-model="idSubFaseExecucao" @change="atualizarIdSubFase(idSubFaseExecucao)" item-text="siglaSubFase" item-value="id"
              :items="subFasesExecucoes" label="Bimestre"></v-select>
      </v-flex>
    </v-layout>
    <v-layout row justify-space-between>
      <v-flex xs12 sm12 md4 v-if="idSubFaseExecucao">
        <gera-atividades :id-turma="idTurma" :id-disciplina="idDisciplina" :id-periodo-execucao="idPeriodoExecucao" :ano-turma="anoTurma"
        />
      </v-flex>
      <v-flex xs12 sm12 md9 v-if="idAtividade">
        <v-layout wrap>
          <v-flex xs12 sm12 md12>
            <v-tabs color="primary" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Diário</v-tab>
              <v-tab @click="abrirFrequencias">Frequência</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <aba-diario :id-turma="idTurma" :id-disciplina="idDisciplina"
                          :id-curso="idCurso" :id-periodo="idPeriodo" :id-sub-fase="idSubFase"
                          :id-atividade="idAtividade" />
                          </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                            <lista-frequencia :id-disciplina="idDisciplina" :id-sub-fase-execucao="idSubFaseExecucao" :ano-turma="anoTurma" />
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
              </v-tabs-items>
            </v-tabs>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</div>
</template>
<script>
import subFaseService from "@/service/curso/SubFaseService";
import turmaService from "@/service/turma/TurmaService";
import AbaDiario from "./AbaDiario";
import GeraAtividades from "./GeraAtividades";
import ListaFrequencia from "../frequencia/ListaFrequencia";
import EventBus from "@/utils/EventBus.js";

export default {
  name: "GerenciaCadastroDiario",
  components: {
    GeraAtividades,
    ListaFrequencia,
    AbaDiario
  },
  data() {
    return {
      idSubFase: null,
      idAtividade: null,
      idCurso: null,
      idPeriodo: null,
      idSubFaseExecucao: null,
      subFasesExecucoes: [],
      cursos: [],
      idPeriodoExecucao: null,
      periodosExecucoes: [],
      idDisciplina: null,
      idTurma: null,
      idChaveTurma: null,
      turmas: [],
      diarioVisivel: false
    };
  },
  watch: {
    async idChaveTurma() {
      if (this.idChaveTurma) {
        this.subFasesExecucoes = await subFaseService.comboSubFasesExecucaoParaDiario(
          this.idTurma,
          this.idDisciplina
        );
        this.idSubFaseExecucao = null;
        this.idAtividade = null;
      }
    },
    idSubFaseExecucao() {
      if (this.idSubFaseExecucao) {
        this.idAtividade = null;
      }
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
    EventBus.$on("selecionaAtividade", async (idAtividade, idSubFase) => {
      this.idAtividade = null;
      setTimeout(() => {
        this.idAtividade = idAtividade;
        this.idSubFase = idSubFase;
      }, 500);
    });
  },
  methods: {
    abrirFrequencias() {
      setTimeout(() => {
        EventBus.$emit("pesquisaFrequencia", this.idAtividade);
      }, 500);
    },
    atualizarIdChaveTurma(idChaveTurma) {
      const arrayTurma = this.turmas.filter(d => {
        return d.id === idChaveTurma;
      });
      this.idCurso = arrayTurma[0].idCurso;
      this.idPeriodo = arrayTurma[0].idPeriodo;
      this.idPeriodoExecucao = arrayTurma[0].idPeriodoExecucao;
      this.idDisciplina = arrayTurma[0].idDisciplina;
      this.idTurma = arrayTurma[0].idTurma;
      this.anoTurma = arrayTurma[0].anoTurma;
    },
    // atualizarIdPeriodo(id) {
    //   const arrayPeriodoExecucao = this.periodosExecucoes.filter(c => {
    //     return c.id === id;
    //   });
    //   this.idPeriodo = arrayPeriodoExecucao[0].idPeriodo;
    // },
    atualizarIdSubFase(idSubFaseExecucao) {
      setTimeout(() => {
        const arraySubFaseExecucao = this.subFasesExecucoes.filter(s => {
          return s.id === idSubFaseExecucao;
        });
        this.idSubFase = arraySubFaseExecucao[0].idSubFase;
        EventBus.$emit(
          "apresentaAtividades",
          this.idSubFase,
          idSubFaseExecucao
        );
      }, 500);
    },
    async carregarDadosBasicos() {
      this.turmas = await turmaService.comboParaDiarioFrequencia();
    }
  }
};
</script>
