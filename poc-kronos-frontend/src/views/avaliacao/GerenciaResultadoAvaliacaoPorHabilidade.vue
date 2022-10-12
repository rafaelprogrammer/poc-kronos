<template>
<div>
  <section-definition title="Avaliação" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Resultado de Avaliações Por Habilidade</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap justify-space-between>
       <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idPeriodoExecucao" :items="periodosExecucoes" item-text="nome" item-value="id" label="Período"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md1>
        <v-select v-model="idTurma" :items="turmas" item-text="sigla" item-value="id" label="Turma"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idDisciplina" item-text="sigla" item-value="id" :items="disciplinas"
        label="Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idAvaliacao" item-text="dataPrevista" item-value="id" :items="avaliacoes"
        label="Data Avaliação" @change="recuperarAvaliacao(idAvaliacao)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.horaInicio" disabled label="Hora Início"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md4 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.tempoDuracao" disabled label="Tempo de duração em minutos"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.nomeTipoAbrangencia" disabled label="Abrangência"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.nomeTipoAvaliacao" disabled label="Tipo"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.nomeTipoFormato" disabled label="Formato"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idAvaliacao">
        <v-checkbox color="primary" disabled label="Grupo" v-model="avaliacao.grupo"></v-checkbox>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.peso" disabled label="Peso"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idAvaliacao">
        <v-checkbox color="primary" disabled label="Anulada" v-model="avaliacao.anulada"></v-checkbox>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idAvaliacao">
        <v-text-field v-model="avaliacao.qtdAvaliados" disabled label="Nr Avaliados"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idAvaliacao">
        <v-btn icon @click="atualizar" small>
          <v-icon color="primary">refresh</v-icon>
        </v-btn>
      </v-flex>
    </v-layout>
    <v-layout wrap v-if="idAvaliacao">
      <v-subheader>Habilidades Avaliadas</v-subheader>
      <v-flex xs12 sm2 md12>
          <v-data-table :loading="loading" :headers="headers" hide-actions :items="avaliacoesHabilidades" class="elevation-1">
            <template slot="items" slot-scope="props">
              <td>
                <v-btn v-if="idAvaliadosSelecionados && idAvaliadosSelecionados.length > 0" 
                  @click="registrarSelecionados(props.item)" 
                  title="Registrar Selecionados" alt="Registrar Selecionados" color="primary" dark>Reg. Selecionados</v-btn>
              </td>
              <td>
                <v-select v-if="idAvaliadosSelecionados && idAvaliadosSelecionados.length > 0" v-model="props.item.idMencao" item-text="simbolo" item-value="id" :items="mencoes"
                  label="Menção"></v-select>
              </td>
              <td>{{ props.item.codigo }}</td>
              <td>{{ props.item.bncc | booleanFilter }}</td>
              <td width="40%">
                <v-tooltip bottom>
                    <span slot="activator" class="">{{ props.item.descricao | limitCaracteres(100) }}</span>
                    <span>{{ props.item.descricao }}</span>
                </v-tooltip>
             </td>
            </template>
            <template slot="no-data">
              Não existem informações cadastradas!
            </template>
          </v-data-table>
      </v-flex>
      <v-flex xs12 sm2 md12>
          <seleciona-avaliado-resultado :id-avaliacao="idAvaliacao" :data-avaliacao="avaliacao.dataPrevista" />
      </v-flex>
    </v-layout>
  </v-container>
</div>
</template>
<script>
import instituicaoService from "@/service/InstituicaoService";
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import cursoService from "@/service/curso/CursoService";
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import turmaService from "@/service/turma/TurmaService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import EventBus from "@/utils/EventBus.js";
import Avaliacao from "@/model/avaliacao/Avaliacao";
import AvaliacaoHabilidade from "@/model/avaliacao/AvaliacaoHabilidade";
import ResultadoHabilidade from "@/model/avaliacao/ResultadoHabilidade";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import SelecionaAvaliadoResultado from "./SelecionaAvaliadoResultado";

export default {
  name: "GerenciaResultadoAvaliacaoPorHabilidade",
  components: {
    SelecionaAvaliadoResultado
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      idAvaliadosSelecionados: [],
      idsMencoes: [],
      loading: true,
      idCurso: null,
      cursos: [],
      idPeriodoExecucao: null,
      periodosExecucoes: [],
      idDisciplina: null,
      disciplinas: [],
      idTurma: null,
      anoTurma: null,
      turmas: [],
      mencoes: [],
      idAvaliacao: null,
      avaliacao: new Avaliacao(),
      avaliacoes: [],
      avaliacoesHabilidades: [],
      avaliacaoHabilidade: new AvaliacaoHabilidade(),
      resultadoHabilidade: new ResultadoHabilidade(),
      cadastroEfetuado: false,
      headers: [
        { text: "", value: "name", sortable: false },
        {
          text: "Resultado",
          align: "left",
          sortable: false
        },
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "BNCC",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        }
      ]
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
      this.idAvaliacao = null;
      this.limparPesquisa();
    },
    async idPeriodoExecucao() {
      if (this.idPeriodoExecucao) {
        this.turmas = await turmaService.comboParaModulosDosProfessores(this.idPeriodoExecucao);
        this.idTurma = null;
        this.idDisciplina = null;
        this.idAvaliacao = null;
        this.limparPesquisa();
      }
    },
    async idTurma() {
      if (this.idTurma) {
        this.disciplinas = await disciplinaService.comboParaModulosDosProfessores(this.idTurma);
        this.idAvaliacao = null;
        this.idDisciplina = null;
        this.limparPesquisa();
      }
    },
    async idDisciplina() {
      if (this.idTurma && this.idDisciplina) {
        this.avaliacoes = await avaliacaoService.combo(
          this.idTurma,
          this.idDisciplina
        );
        this.idAvaliacao = null;
        this.limparPesquisa();
      }
    },
    idAvaliacao() {
      this.atualizar();
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    EventBus.$on("idsAvaliadosSelecionados", (idsAvaliados, idsMencoes) => {
      this.idAvaliadosSelecionados = idsAvaliados;
      this.idsMencoes = idsMencoes;
    });
    this.carregarDadosBasicos();
  },
  methods: {
    recuperarAnoTurma() {
      const resultado = this.turmas.filter(t => t.id === this.idTurma);
      this.anoTurma = resultado[0].ano;
    },
    registrarSelecionados(avaliacaoHabilidade) {
      this.resultadoHabilidade.idAvaliacaoHabilidade = avaliacaoHabilidade.id;
      this.resultadoHabilidade.idsAvaliados = this.idAvaliadosSelecionados;
      this.resultadoHabilidade.idsMencoes = this.idsMencoes;
      this.resultadoHabilidade.idMencao = avaliacaoHabilidade.idMencao;
      avaliacaoService.salvarResultadoHabilidade(this.resultadoHabilidade).then(() => {
        EventBus.$emit("limpaSelecao");
        this.atualizar();
      });
    },
    recuperarAvaliacao(idAvaliacao) {
      const resultado = this.avaliacoes.filter(a => a.id === idAvaliacao);
      this.avaliacao = resultado[0];
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.comboParaModulosDosProfessores();
      this.mencoes = await instituicaoService.listarMencoes();
    },
    atualizar() {
      setTimeout(() => {
        this.pesquisar();
        this.cadastroEfetuado = false;
        this.recuperarAnoTurma();
        EventBus.$emit("pesquisaResultados", this.anoTurma);
       
      }, 500);
    },
    limparPesquisa() {
      this.avaliacoesHabilidades = [];
    },
    async pesquisar() {
      if (this.idAvaliacao) {
        this.loading = true;
        this.avaliacoesHabilidades = await avaliacaoService.listarAvaliacoesHabilidades(
          this.idAvaliacao
        );
        this.loading = false;
      }
    }
  }
};
</script>
