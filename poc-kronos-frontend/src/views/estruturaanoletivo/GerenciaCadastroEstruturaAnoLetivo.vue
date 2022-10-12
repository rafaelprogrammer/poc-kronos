<template>
<div>
  <modal-visualiza-estrutura-ano-letivo />
  <modal-cadastro-periodo-execucao-ano />
  <modal-gera-fases-execucao @fasesExecucoesGeradasSucesso="fasesExecucoesGeradasSucesso = $event"/>
  <modal-lista-fases-execucao @fasesExecucaoFechada="fasesExecucaoFechada = $event" />
  <modal-gera-sub-fases-execucao @subFasesExecucoesGeradasSucesso="subFasesExecucoesGeradasSucesso = $event"/>
  <modal-lista-sub-fases-execucao @subFasesExecucaoFechada="subFasesExecucaoFechada = $event" />
  <modal-lista-turma @turmaFechada="turmaFechada = $event" />
  <section-definition title="Estrutura do Ano Letivo" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Estrutura do Ano Letivo</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
       <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"
        @change="atualizarNomeCurso(idCurso)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="ano" :items="anos" label="Ano"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md7>
        <v-select v-model="idCalendario" item-text="descricao" item-value="id" :items="calendarios"
        label="Calendário"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-btn color="primary" v-if="idCalendario" left direction="right" 
        alt="Estrutura Períodos de Execução" @click="visualizarEstruturaPeriodoExecucao()" 
        title="Estrutura Períodos de Execução"><v-icon>find_replace</v-icon>Período Execução</v-btn>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-btn color="primary" v-if="idCalendario" left direction="right" 
        alt="Turma" @click="visualizarEstruturaTurma()" 
        title="Turma"><v-icon>find_replace</v-icon>Turma</v-btn>
      </v-flex>
    </v-layout>
    <v-layout wrap  v-if="idCalendario">
      <v-flex xs12 sm2 md2>
        <v-btn color="primary" left direction="right" alt="Gerar Períodos" @click="gerarPeriodosExecucao()" title="Gerar Períodos"><v-icon>settings</v-icon>Gerar Períodos</v-btn>
      </v-flex>
      <v-flex xs12 sm2 md2>
        <v-btn color="primary" left direction="right" alt="Gerar Fases" @click="gerarFasesExecucao()" title="Gerar Fases"><v-icon>settings</v-icon>Gerar Fases</v-btn>
      </v-flex>
      <v-flex xs12 sm2 md2>
        <v-btn color="primary" left direction="right" alt="Gerar Sub-Fases" @click="gerarSubFasesExecucao()" title="Gerar Sub-Fases"><v-icon>settings</v-icon>Gerar Sub-Fases</v-btn>
      </v-flex>
       <v-flex xs12 sm2 md2>
        <v-btn color="primary" left direction="right" alt="Gerar Turmas" @click="gerarTurmas()" title="Gerar Turmas"><v-icon>settings</v-icon>Gerar Turmas</v-btn>
      </v-flex>
      <v-flex xs12 sm2 md12>
        <v-subheader>Períodos de Execução (Anos)</v-subheader>
      </v-flex>
      <v-flex xs12 sm2 md12>
        <v-data-table :loading="loading" :headers="headers" :items="periodosExecucoes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
          <template slot="items" slot-scope="props">
            <td>{{ props.item.ano }}</td>
            <td>{{ props.item.tipoPeriodo }}</td>
            <td>{{ props.item.siglaPeriodo }}</td>
            <td>{{ props.item.numeroPeriodo }}</td>
            <td>{{ props.item.calendario }}</td>
            <td>{{ props.item.dataInicio }}</td>
            <td>{{ props.item.dataFim }}</td>
            <td>{{ props.item.numeroFases }}</td>
            <td>
              <v-menu offset-y>
                <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                <v-list>
                  <v-list-tile alt="Editar" @click="editar(props.item)">
                    <v-list-tile-title>
                      Editar
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Excluir" v-if="props.item.numeroFases === 0" @click="excluir(props.item)">
                    <v-list-tile-title>
                      Excluir
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Fases Execução" v-if="props.item.numeroFases > 0" @click="abrirFases(props.item)">
                    <v-list-tile-title>
                      Fases
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-list-tile alt="Sub-Fases" v-if="props.item.numeroFases > 0" @click="abrirSubFases(props.item)">
                    <v-list-tile-title>
                      Sub-Fases
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-list-tile alt="Turmas" @click="abrirTurmas(props.item)">
                    <v-list-tile-title>
                      Turmas
                    </v-list-tile-title>
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
  </v-container>
</div>
</template>
<script>
import turmaService from "@/service/turma/TurmaService";
import cursoService from "@/service/curso/CursoService";
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import calendarioService from "@/service/calendario/CalendarioService";
import PeriodoExecucaoEstruturaAnoLetivo from "@/model/periodoexecucao/PeriodoExecucaoEstruturaAnoLetivo";
import Turma from "@/model/turma/Turma";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroPeriodoExecucaoAno from "./ModalCadastroPeriodoExecucaoAno";
import ModalGeraFasesExecucao from "./ModalGeraFasesExecucao";
import ModalGeraSubFasesExecucao from "./ModalGeraSubFasesExecucao";
import EventBus from "@/utils/EventBus.js";
import ModalListaFasesExecucao from "./ModalListaFasesExecucao";
import ModalListaSubFasesExecucao from "./ModalListaSubFasesExecucao";
import ModalListaTurma from "./ModalListaTurma";
import ModalVisualizaEstruturaAnoLetivo from "./ModalVisualizaEstruturaAnoLetivo";

export default {
  name: "GerenciaCadastroEstruturaAnoLetivo",
  components: {
    ModalCadastroPeriodoExecucaoAno,
    ModalGeraFasesExecucao,
    ModalListaFasesExecucao,
    ModalGeraSubFasesExecucao,
    ModalListaSubFasesExecucao,
    ModalListaTurma,
    ModalVisualizaEstruturaAnoLetivo
  },
  data() {
    return {
      anos: [],
      ano: null,
      dialog: false,
      idCurso: null,
      nomeCurso: null,
      cursos: [],
      idCalendario: null,
      calendarios: [],
      periodosExecucoes: [],
      periodoExecucao: new PeriodoExecucaoEstruturaAnoLetivo(),
      turma: new Turma(),
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      fasesExecucoesGeradasSucesso: false,
      fasesExecucaoFechada: false,
      subFasesExecucoesGeradasSucesso: false,
      headers: [
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "ano"
        },
        {
          text: "Tipo Período",
          align: "left",
          sortable: false,
          value: "tipoPeriodo"
        },
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "siglaPeriodo"
        },
        {
          text: "Nr Período",
          align: "left",
          sortable: false,
          value: "numeroPeriodo"
        },
        {
          text: "Calendário",
          align: "left",
          sortable: false,
          value: "calendario"
        },
        {
          text: "Data Início",
          align: "left",
          sortable: false,
          value: "dataInicio"
        },
        {
          text: "Data Fim",
          align: "left",
          sortable: false,
          value: "dataFim"
        },
        {
          text: "Nr Fases",
          align: "left",
          sortable: false,
          value: "numeroFases"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    turmaFechada(event) {
      this.atualizarPorEvento(event);
    },
    subFasesExecucaoFechada(event) {
      this.atualizarPorEvento(event);
    },
    subFasesExecucoesGeradasSucesso(event) {
      this.atualizarPorEvento(event);
    },
    fasesExecucoesGeradasSucesso(event) {
      this.atualizarPorEvento(event);
    },
    fasesExecucaoFechada(event) {
      this.atualizarPorEvento(event);
    },
    cadastroEfetuado(event) {
      this.atualizarPorEvento(event);
    },
    async idCurso() {
      if (this.idCurso) {
        this.anos = await calendarioService.comboAnosPorCurso(this.idCurso);
      }
      this.ano = null;
    },
    async ano() {
      if (this.idCurso && this.ano) {
        this.calendarios = await calendarioService.combo(this.idCurso, this.ano);
      }
      this.idCalendario = null;
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
    this.atualizar();
  },
  methods: {
    async visualizarEstruturaPeriodoExecucao() {
      const items = await periodoExecucaoService.visualizarEstrutura(this.idCurso, this.ano);
      EventBus.$emit("visualizaEstrutura", this.nomeCurso, this.ano, items);
    },
    async visualizarEstruturaTurma() {
      const items = await turmaService.visualizarEstrutura(this.idCurso, this.ano);
      EventBus.$emit("visualizaEstrutura", this.nomeCurso, this.ano, items);
    },
    abrirTurmas(periodoExecucao) {
      EventBus.$emit(
        "abrirListaTurma",
        this.nomeCurso,
        periodoExecucao.ano,
        periodoExecucao.id,
        periodoExecucao.siglaPeriodo,
        this.idCalendario
      );
    },
    abrirSubFases(periodoExecucao) {
      EventBus.$emit(
        "abrirListaSubFasesExecucao",
        this.idCurso,
        this.nomeCurso,
        periodoExecucao.ano,
        periodoExecucao.id,
        periodoExecucao.siglaPeriodo,
      );
    },
    abrirFases(periodoExecucao) {
      EventBus.$emit(
        "abrirListaFasesExecucao",
        this.idCurso,
        this.nomeCurso,
        periodoExecucao.ano,
        periodoExecucao.id,
        periodoExecucao.siglaPeriodo,
      );
    },
    gerarPeriodosExecucao() {
      this.periodoExecucao.idCurso = this.idCurso;
      this.periodoExecucao.idCalendario = this.idCalendario;
      this.periodoExecucao.ano = this.ano;
      periodoExecucaoService.salvar(this.periodoExecucao).then(() => {
        this.pesquisar();
      });
    },
    atualizarNomeCurso(id) {
      const arrayCurso = this.cursos.filter(c => {
        return c.id === id;
      });
      this.nomeCurso = arrayCurso[0].nome;
    },
    gerarTurmas() {
      this.turma.idCalendario = this.idCalendario;
      this.turma.idCurso = this.idCurso;
      this.turma.ano = this.ano;
      turmaService.gerar(this.turma);
    },
    gerarFasesExecucao() {
      EventBus.$emit("abrirGeraFases", this.idCurso, this.nomeCurso, this.ano);
    },
    gerarSubFasesExecucao() {
      EventBus.$emit("abrirGeraSubFases", this.idCurso, this.nomeCurso, this.ano);
    },
    editar(periodoExecucao) {
      EventBus.$emit("editaPeriodoExecucaoEstruturaAnoLetivo", periodoExecucao, this.idCurso, this.nomeCurso);
    },
    excluir(periodoExecucao) {
      confirm("Você deseja realmente excluir o Período de Execução?") &&
        periodoExecucaoService.excluir(periodoExecucao).then(() => {
          this.periodosExecucoes.splice(this.periodosExecucoes.indexOf(periodoExecucao), 1);
          this.atualizar();
        });
    },
    atualizarPorEvento(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.subFasesExecucoesGeradasSucesso = false;
          this.fasesExecucoesGeradasSucesso = false;
          this.fasesExecucaoFechada = false;
          this.cadastroEfetuado = false;
          this.subFasesExecucaoFechada = false;
        }, 500);
      }
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.idCalendario) {
        this.loading = true;
        const paginacaoResultado = await periodoExecucaoService.listarParaEstruturaAnoLetivo(
          this.idCurso,
          this.idCalendario,
          this.ano,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.periodosExecucoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.combo();
    }
  }
};
</script>
