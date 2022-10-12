<template>
<div>
  <section-definition title="Resultado de Bimestre" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Resultado de Bimestre</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <modal-resultado-avaliacoes />
  <v-container fluid>
    <v-layout wrap justify-space-between>
       <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md1>
        <v-select v-model="idPeriodoExecucao" :items="periodosExecucoes" item-text="nome" item-value="id" label="Período"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idTurma" :items="turmas" item-text="sigla" item-value="id" label="Turma"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idDisciplina" item-text="sigla" item-value="id" :items="disciplinas"
        label="Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idSubFaseExecucao" item-text="numeroSubFase" item-value="id" :items="subFasesExecucoes"
        label="Bimestre" @change="recuperarDados(idSubFaseExecucao)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idSubFaseExecucao">
        <v-btn icon @click="atualizar" small>
          <v-icon color="primary">refresh</v-icon>
        </v-btn>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idSubFaseExecucao">
        <v-text-field v-model="dataInicial" disabled label="Data Inicial"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idSubFaseExecucao">
        <v-text-field v-model="dataFinal" disabled label="Data Final"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idSubFaseExecucao">
        <v-text-field v-model="estatisticaResultadoSubFase.qtdAulasDadas" disabled label="Aulas Dadas"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idSubFaseExecucao">
        <v-text-field v-model="estatisticaResultadoSubFase.nrHabilidadesTrabalhadas" disabled label="Nr Hab Trabalhadas"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idSubFaseExecucao">
        <v-text-field v-model="estatisticaResultadoSubFase.nrHabilidadesAvaliadas" disabled label="Nr Hab Avaliadas"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idSubFaseExecucao">
        <v-select v-if="alunosSelecionados && alunosSelecionados.length > 0" v-model="idMencao" item-text="simbolo" item-value="id" :items="mencoes"
              @change="atualizarMencao(idMencao)" label="Menção"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idSubFaseExecucao && alunosSelecionados && alunosSelecionados.length > 0 && idMencao">
        <v-btn color="primary" dark @click.native="registrarSelecionados()">Registrar Selecionados</v-btn>
      </v-flex>
    </v-layout>
    <v-layout wrap v-if="idSubFaseExecucao">
      <v-flex xs12 sm2 md12>
          <v-data-table v-model="selected" hide-actions select-all item-key="idCredito" :loading="loading" :headers="headers" :items="alunos" class="elevation-1">
            <template slot="items" slot-scope="props">
              <td :active="props.selected" @click="props.selected = !props.selected">
                <v-checkbox
                  :input-value="props.selected"
                  primary
                  hide-details
                ></v-checkbox>
              </td>
              <td>{{ props.item.numeroRegistro }}</td>
              <td>{{ props.item.nomeAluno }}</td>
              <td>{{ props.item.numeroHabilidadesAvaliadas }}</td>
              <td>{{ props.item.numeroHabilidadesRecuperadas }}</td>
              <td>{{ props.item.totalAusencia }}</td>
              <td>{{ props.item.percentualAusencia }} %</td>
              <td>{{ props.item.situacao }}</td>
              <td>{{ props.item.mencao }}</td>
              <td>
                  <v-icon title="Resultados Avaliações" medium class="mr-2" @click="abrirResultadosAvaliacoes(props.item)">
                    local_offer
                  </v-icon>
              </td>
            </template>
            <template slot="no-data">
              Não existem informações cadastradas!
            </template>
          </v-data-table>
      </v-flex>
      <!--<v-flex xs12 sm2 md12>
          <seleciona-avaliado-resultado :id-avaliacao="idAvaliacao" @idsAvaliadosSelecionados="idAvaliadosSelecionados = $event" />
      </v-flex>-->
    </v-layout>
  </v-container>
</div>
</template>
<script>
import instituicaoService from "@/service/InstituicaoService";
import subFaseService from "@/service/curso/SubFaseService";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus.js";
// import Avaliacao from "@/model/avaliacao/Avaliacao";
// import AvaliacaoHabilidade from "@/model/avaliacao/AvaliacaoHabilidade";
// import ResultadoHabilidade from "@/model/avaliacao/ResultadoHabilidade";
import EstatisticaResultadoSubFase from "@/model/curso/subfase/EstatisticaResultadoSubFase";
import Mencao from "@/model/instituicao/Mencao";
// import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalResultadoAvaliacoes from "./ModalResultadoAvaliacoes";

export default {
  name: "GerenciaResultadoBimestre",
  components: {
    ModalResultadoAvaliacoes
  },
  // filters: {
  //   booleanFilter: booleanFilter,
  //   limitCaracteres: limitCaracteres
  // },
  data() {
    return {
      selected: [],
      alunosSelecionados: [],
      loading: true,
      idCurso: null,
      cursos: [],
      idPeriodoExecucao: null,
      periodosExecucoes: [],
      idDisciplina: null,
      disciplinas: [],
      idTurma: null,
      turmas: [],
      mencoes: [],
      subFasesExecucoes: [],
      idSubFaseExecucao: null,
      alunos: [],
      cadastroEfetuado: false,
      dataInicial: null,
      dataFinal: null,
      idMencao: null,
      estatisticaResultadoSubFase: new EstatisticaResultadoSubFase(),
      headers: [
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomeAluno"
        },
        {
          text: "Nr. Hab. Avaliadas",
          align: "left",
          sortable: false,
          value: "numeroHabilidadesAvaliadas"
        },
        {
          text: "Nr. Hab. Recuperadas",
          align: "left",
          sortable: false,
          value: "numeroHabilidadesRecuperadas"
        },
        {
          text: "Nr. Faltas",
          align: "left",
          sortable: false,
          value: "numeroFaltas"
        },
        {
          text: "% Faltas",
          align: "left",
          sortable: false,
          value: "percentualAusencia"
        },
         {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "situacao"
        },
        {
          text: "Resultado Bimestre",
          align: "left",
          sortable: false,
          value: "mencao"
        },
        {
          text: "Resultados Avaliações",
          align: "left",
          sortable: false,
        }
      ]
    };
  },
  watch: {
    selected() {
      this.selecionarAlunos();
    },
    async idCurso() {
      if (this.idCurso) {
        this.periodosExecucoes = await subFaseService.comboPeriodos(this.idCurso);
      }
      this.idPeriodoExecucao = null;
      this.idTurma = null;
      this.idDisciplina = null;
      this.idSubFaseExecucao = null;
    },
    async idPeriodoExecucao() {
      if (this.idPeriodoExecucao) {
        this.turmas = await subFaseService.comboTurmas(this.idPeriodoExecucao);
        this.subFasesExecucoes = await subFaseService.comboBimestres(
          this.idPeriodoExecucao,
        );
        this.idTurma = null;
        this.idDisciplina = null;
      }
    },
    async idTurma() {
      if (this.idTurma) {
        this.disciplinas = await subFaseService.comboDisciplinas(this.idTurma);
        this.idSubFaseExecucao = null;
      }
    },
    idDisciplina() {
      this.idSubFaseExecucao = null;
    },
    idSubFaseExecucao() {
      this.atualizar();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
  },
  methods: {
    selecionarAlunos() {
      this.alunosSelecionados = this.selected;
    },
    atualizarMencao(idMencao) {
      this.alunosSelecionados.map(aluno => {
        aluno.idMencao = !aluno.idMencao ? idMencao : aluno.idMencao;
        aluno.idSubFaseExecucao = this.idSubFaseExecucao;
      });
    },
    registrarSelecionados() {
      subFaseService.salvarResultadosSubFases(this.alunosSelecionados).then(() => {
        this.atualizar();
        this.selected = [];
        this.alunosSelecionados = [];
        this.idMencao = null;
      });
    },
    abrirResultadosAvaliacoes(aluno) {
      EventBus.$emit("abrirResultadoAvaliacoes", aluno.idCredito, this.idTurma, this.idDisciplina, this.dataInicial, this.dataFinal, aluno.numeroRegistro, aluno.nomeAluno)
    },
    async recuperarDados(idSubFaseExecucao) {
      const arraySubFasesExecucao = this.subFasesExecucoes.filter(s => {
        return s.id === idSubFaseExecucao;
      });
      this.dataInicial = arraySubFasesExecucao[0].dataInicio;
      this.dataFinal = arraySubFasesExecucao[0].dataFim;
      this.estatisticaResultadoSubFase = await subFaseService.recuperarEstatisticasResultado(idSubFaseExecucao, 
      this.idTurma, this.idDisciplina, this.dataInicial, this.dataFinal);
    },
    async carregarDadosBasicos() {
      this.cursos = await subFaseService.comboCursos();
      this.mencoes = await instituicaoService.listarMencoes();
    },
    atualizar() {
      setTimeout(() => {
        this.pesquisar();
      }, 500);
    },
    async pesquisar() {
      if (this.idSubFaseExecucao) {
        this.loading = true;
        this.alunos = await subFaseService.listarAlunosResultados(
          this.idTurma,
          this.idDisciplina,
          this.idSubFaseExecucao,
          this.dataFinal
        );
        this.loading = false;
      }
    }
  }
};
</script>
