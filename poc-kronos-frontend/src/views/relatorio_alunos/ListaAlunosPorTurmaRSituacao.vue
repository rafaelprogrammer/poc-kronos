<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm6 md12>
      <v-form @submit.prevent="pesquisar()" ref="form">
        <v-layout wrap> 
          <v-flex xs12 sm6 md2>
            <v-select v-model="ano" item-text="nome" item-value="id"
              :items="anos" label="Ano" @change="carregarCurso(ano)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md5>
            <v-select v-model="idCurso" item-text="sigla" item-value="id"
              :items="cursos" label="Curso" @change="carregarPeriodo(idCurso)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-select v-model="idPeriodoExecucao" item-text="sigla" item-value="id"
              :items="periodos" label="Período" @change="carregarTurma(idPeriodoExecucao)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-select v-model="idTurma" item-text="sigla" item-value="id"
              :items="turmas" label="Turma"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md12>
            <v-select v-model="idsTiposSituacoesContratos" attach chips multiple item-text="nome" item-value="id"
              :items="tiposSituacoesContratos" label="Situações"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md2 v-if="idTurma && idsTiposSituacoesContratos.length > 0">
            <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md4 v-if="alunos.length > 0">
                <v-btn color="primary" alt="Diário PDF" title="Diário PDF" type="submit" @click="gerarPdf()"><v-icon>picture_as_pdf</v-icon></v-btn>
          </v-flex>
        </v-layout>
      </v-form>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="alunos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nr }}</td>
          <td>{{ props.item.numeroRegistro }}</td>
          <td>{{ props.item.cpf }}</td>
          <td>{{ props.item.nome }}</td>
          <td>{{ props.item.situacao }}</td>
          <td>{{ props.item.email }}</td>
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
import relatorioService from "@/service/relatorio/RelatorioService";
import relatorioAlunosService from "@/service/relatorio/RelatorioAlunosService";
import contratoService from "@/service/contrato/ContratoService";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ListaAlunosPorTurmaRSituacao",
  data() {
    return {
      dialog: false,
      idsTiposSituacoesContratos: [],
      tiposSituacoesContratos: [],
      ano: null,
      anos: [],
      periodos: [],
      idPeriodoExecucao: null,
      cursos: [],
      idCurso: null,
      turmas: [],
      idTurma: null,
      alunos: [],
      pagination: new Pagination(1, 5),
      loading: false,
      headers: [
        {
          text: "NR",
          align: "left",
          sortable: false,
          value: "nr"
        },
        {
          text: "Nr Regitro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpf"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "situacao"
        },
        {
          text: "Email",
          align: "left",
          sortable: false,
          value: "email"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
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
    gerarPdf() {
      this.loading = true;
      relatorioAlunosService.gerarPorTurmaRSituacaoPDF(
        this.idTurma,
        this.ano,
        this.idsTiposSituacoesContratos,
        this.cursos.filter(c => c.id === this.idCurso)[0].nome,
        this.periodos.filter(p => p.id === this.idPeriodoExecucao)[0].sigla,
        this.turmas.filter(t => t.id === this.idTurma)[0].sigla
      ).then(dados => {
        if (dados) {
           this.loading = false;
        }
      });
    },
    async carregarCurso(ano) {
      this.cursos = await relatorioService.cursosPorAno(ano);
    },
    async carregarPeriodo(idCurso) {
      this.periodos = await relatorioService.periodosPorCursoEAno(
        idCurso,
        this.ano
      );
    },
    async carregarTurma(idPeriodoExecucao) {
      this.turmas = await relatorioService.turmasPorPeriodoExecucao(
        idPeriodoExecucao
      );
    },
    async carregarBimestre(idPeriodoExecucao) {
      this.bimestres = await relatorioService.bimestresPorPeriodoExecucao(
        idPeriodoExecucao
      );
    },
    async carregarDadosBasicos() {
      this.anos = await relatorioService.anos();
      this.tiposSituacoesContratos = await contratoService.tipos();
    },
    limpar() {
      this.ano = null;
      this.idCurso = null;
      this.idPeriodoExecucao = null;
      this.idTurma = null;
      this.idsTiposSituacoesContratos = [];
    },
    async pesquisar() {
      if (this.idTurma && this.idsTiposSituacoesContratos) {
        this.loading = true;
        const paginacaoResultado = await relatorioAlunosService.listarPorTurmaRSituacoes(
          this.idTurma,
          this.idsTiposSituacoesContratos,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.alunos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
