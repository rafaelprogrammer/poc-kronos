<template>
<div>
  <section-definition title="Frequência" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Frequência</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap justify-space-between>
       <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idPeriodoExecucao" :items="periodosExecucoes" item-text="nome" item-value="id" label="Período"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md1>
        <v-select v-model="idTurma" :items="turmas" item-text="sigla" item-value="id" label="Turma"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idDisciplina" item-text="sigla" item-value="id" :items="disciplinas"
        label="Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm12 md2>
            <v-select v-model="idSubFaseExecucao" @change="atualizarIdSubFase(idSubFaseExecucao)" item-text="siglaSubFase" item-value="id"
              :items="subFasesExecucoes" label="Bimestre"></v-select>
      </v-flex>
    </v-layout>
    <v-layout row justify-space-between>
      <v-flex xs12 sm12 md4 v-if="idSubFaseExecucao">
        <gera-atividades :visualiza-gerar="false" :id-turma="idTurma" :id-disciplina="idDisciplina" @idSubFaseExecucaoSelecionado="idSubFaseExecucao = $event"/>
      </v-flex>
      <v-flex xs12 sm12 md9 v-if="idDisciplina && idAtividade">
        <v-layout wrap>
          <v-flex xs12 sm6 md12 v-if="idDisciplina && idAtividade && idSubFaseExecucao">
            <lista-frequencia :id-disciplina="idDisciplina" :id-sub-fase-execucao="idSubFaseExecucao" />
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</div>
</template>
<script>
import subFaseService from "@/service/curso/SubFaseService";
import cursoService from "@/service/curso/CursoService";
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import turmaService from "@/service/turma/TurmaService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
//import Pagination from "@/utils/Pagination.js";
import ListaFrequencia from "./ListaFrequencia";
import GeraAtividades from "../diario/GeraAtividades";
import EventBus from "@/utils/EventBus.js";

export default {
  name: "GerenciaCadastroDiario",
  components: {
    GeraAtividades,
    ListaFrequencia
  },
  data() {
    return {
      idSubFase: null,
      subFasesExecucoes: [],
      idSubFaseExecucao: null,
      idAtividade: null,
      idCurso: null,
      cursos: [],
      idPeriodoExecucao: null,
      periodosExecucoes: [],
      idDisciplina: null,
      disciplinas: [],
      idTurma: null,
      turmas: []
    };
  },
  watch: {
    async idCurso() {
      if (this.idCurso) {
        this.periodosExecucoes = await periodoExecucaoService.comboParaModulosDosProfessores(this.idCurso);
      }
      this.idPeriodoExecucao = null;
      this.idTurma = null;
      this.idDisciplina = null;
      this.idSubFaseExecucao = null;
      this.idAtividade = null;
    },
    async idPeriodoExecucao() {
      if (this.idPeriodoExecucao) {
        this.turmas = await turmaService.comboParaModulosDosProfessores(this.idPeriodoExecucao);
        this.idTurma = null;
        this.idDisciplina = null;
        this.idSubFaseExecucao = null;
        this.idAtividade = null;
      }
    },
    async idTurma() {
      if (this.idTurma) {
        this.disciplinas = await disciplinaService.comboParaModulosDosProfessores(this.idTurma);
        this.idDisciplina = null;
        this.idSubFaseExecucao = null;
        this.idAtividade = null;
      }
    },
    async idDisciplina() {
      if (this.idDisciplina) {
        this.subFasesExecucoes = await subFaseService.comboSubFasesExecucaoParaDiario(this.idTurma, this.idDisciplina);
        this.idSubFaseExecucao = null;
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
     EventBus.$on("selecionaAtividade", async (idAtividade) => {
      this.idAtividade = idAtividade;
      setTimeout(() => {
        EventBus.$emit("pesquisaFrequencia", idAtividade);
      }, 500);
    });
  },
  methods: {
    async carregarDadosBasicos() {
      this.cursos = await cursoService.comboParaModulosDosProfessores();
    },
    atualizarIdSubFase(idSubFaseExecucao) {
      setTimeout(() => {
        const arraySubFaseExecucao = this.subFasesExecucoes.filter(s => {
            return s.id === idSubFaseExecucao;
        });
        this.idSubFase = arraySubFaseExecucao[0].idSubFase;
        EventBus.$emit("apresentaAtividades", this.idSubFase, idSubFaseExecucao);
      }, 500);
    }
  }
};
</script>
