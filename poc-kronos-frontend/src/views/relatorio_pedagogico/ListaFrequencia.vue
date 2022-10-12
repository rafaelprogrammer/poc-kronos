<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md12>
      <v-form @submit.prevent="pesquisar()" ref="form">
        <v-layout wrap> 
          <v-flex xs12 sm6 md2>
            <v-select v-model="ano" item-text="nome" item-value="id"
              :items="anos" label="Ano" @change="carregarCurso(ano)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-select v-model="idCurso" item-text="sigla" item-value="id"
              :items="cursos" label="Curso" @change="carregarPeriodo(idCurso)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-select v-model="idPeriodoExecucao" item-text="sigla" item-value="id"
              :items="periodos" label="Período" @change="carregarTurma(idPeriodoExecucao)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-select v-model="idTurma" item-text="sigla" item-value="id"
              :items="turmas" label="Turma" @change="carregarBimestre(idPeriodoExecucao)"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-select v-model="idSubFaseExecucao" item-text="siglaSubFase" item-value="id"
              :items="bimestres" label="Bimestre"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
          </v-flex>
        </v-layout>
      </v-form>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="diarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nome }}</td>
          <td>{{ props.item.nomeFuncionario }}</td>
          <td>{{ props.item.cargaHorariaPrevista }}</td>
          <td>{{ props.item.cargaHorariaMinistrada }}</td>
          <td>{{ props.item.numeroDias }}</td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" title="Opções para direitos" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Encerrar" v-if="!props.item.encerrado" @click="encerrar(props.item)">
                  <v-list-tile-title>Encerrar</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Reabrir" v-if="props.item.encerrado" @click="reabrir(props.item)">
                  <v-list-tile-title>Reabrir</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Frequência PDF" @click="gerarFrequenciaPdf(props.item)">
                  <v-list-tile-title>PDF</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Frequência XLSX" @click="gerarFrequenciaXLSX(props.item)">
                  <v-list-tile-title>XLSX</v-list-tile-title>
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
import relatorioService from "@/service/relatorio/RelatorioService";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ListaFrequencia",
  data() {
    return {
      dialog: false,
      idSubFaseExecucao: null,
      ano: null,
      anos: [],
      periodos: [],
      idPeriodoExecucao: null,
      cursos: [],
      idCurso: null,
      turmas: [],
      idTurma: null,
      diarios: [],
      bimestres: [],
      pagination: new Pagination(1, 5),
      loading: false,
      headers: [
        {
          text: "Disciplina",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Funcionário",
          align: "left",
          sortable: false,
          value: "nomeFuncionario"
        },
        {
          text: "Carga Horária Prevista",
          align: "left",
          sortable: false,
          value: "cargaHorariaPrevista"
        },
        {
          text: "Carga Horária Ministrada",
          align: "left",
          sortable: false,
          value: "cargaHorariaMinistrada"
        },
        {
          text: "Nr Dias Letivos",
          align: "left",
          sortable: false,
          value: "numeroDias"
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
    gerarFrequenciaPdf(disciplina) {
      relatorioService.gerarFrequenciaPdf(
        disciplina,
        this.idSubFaseExecucao,
        this.cursos.filter(c => c.id === this.idCurso)[0].nome,
        this.periodos.filter(p => p.id === this.idPeriodoExecucao)[0].sigla,
        this.turmas.filter(t => t.id === this.idTurma)[0].sigla,
        this.bimestres.filter(b => b.id === this.idSubFaseExecucao)[0],
        this.idTurma
      );
    },
    gerarFrequenciaXLSX(disciplina) {
      relatorioService.gerarFrequenciaXLSX(
        disciplina,
        this.idSubFaseExecucao,
        this.cursos.filter(c => c.id === this.idCurso)[0].nome,
        this.periodos.filter(p => p.id === this.idPeriodoExecucao)[0].sigla,
        this.turmas.filter(t => t.id === this.idTurma)[0].sigla,
        this.bimestres.filter(b => b.id === this.idSubFaseExecucao)[0],
        this.idTurma
      );
    },
    encerrar(disciplina) {
      relatorioService
        .encerrarDiario(disciplina.id, this.idSubFaseExecucao)
        .then(dados => {
          if (dados) {
            this.pesquisar();
          }
        });
    },
    reabrir(disciplina) {
      relatorioService
        .reabrirDiario(disciplina.id, this.idSubFaseExecucao)
        .then(dados => {
          if (dados) {
            this.pesquisar();
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
    },
    limpar() {
      this.ano = null;
      this.idCurso = null;
      this.idPeriodoExecucao = null;
      this.idTurma = null;
    },
    async pesquisar() {
      if (this.idSubFaseExecucao) {
        this.loading = true;
        const paginacaoResultado = await relatorioService.listarPedagogicos(
          this.idTurma,
          this.idSubFaseExecucao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.diarios = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
