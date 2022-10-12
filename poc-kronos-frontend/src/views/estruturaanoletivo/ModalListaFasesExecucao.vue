<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <modal-cadastro-fase-execucao @faseExecucaoAlterada="faseExecucaoAlterada = $event" />
        <v-card>
          <v-card-title>
            <span class="headline">Fases de Execução (Semestres)</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap justify-space-between>
                <v-flex xs12 sm2 md12>
                  <v-subheader>Curso / Período /  Ano</v-subheader>
                </v-flex>
                <v-flex xs12 sm6 md8>
                  <v-text-field color="primary" disabled label="Curso" v-model="nomeCurso"></v-text-field>
                </v-flex>
                 <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" disabled label="Período" v-model="siglaPeriodo"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" disabled label="Ano" v-model="ano"></v-text-field>
                </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="fasesExecucoes" class="elevation-1"
              :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.tipoPeriodo }}</td>
                    <td>{{ props.item.siglaFase }}</td>
                    <td>{{ props.item.numeroFase }}</td>
                    <td>{{ props.item.dataInicio }}</td>
                    <td>{{ props.item.dataFim }}</td>
                    <td>{{ props.item.numeroSubFases }}</td>
                    <td>
                      <v-icon title="Editar" medium class="mr-2" @click="editar(props.item)">
                        edit
                      </v-icon>
                      <v-icon title="Excluir" medium class="mr-2" @click="excluir(props.item)" >
                          delete
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Dados não localizados!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroFaseExecucao from "./ModalCadastroFaseExecucao";

export default {
  name: "ModalListaFasesExecucao",
  components: {
    ModalCadastroFaseExecucao
  },
  data() {
    return {
      dialog: false,
      idCurso: null,
      nomeCurso: null,
      ano: null,
      idPeriodoExecucao: null,
      siglaPeriodo: null,
      fasesExecucoes: [],
      liberaPesquisa: false,
      pagination: new Pagination(1, 5),
      loading: true,
      faseExecucaoAlterada: false,
      headers: [
        {
          text: "Tipo Período",
          align: "left",
          sortable: false,
          value: "tipoPeriodo"
        },
        {
          text: "Sigla",
          align: "left",
          sortable: false,
          value: "siglaFase"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numeroFase"
        },
        {
          text: "Data Inicial",
          align: "left",
          sortable: false,
          value: "dataInicio"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "dataFim"
        },
        {
          text: "Nr Sub-Fases",
          align: "left",
          sortable: false,
          value: "numeroSubFases"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    faseExecucaoAlterada(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.faseExecucaoAlterada = false;
        }, 500);
      }
    },
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    EventBus.$on("abrirListaFasesExecucao", (idCurso, nomeCurso, ano, idPeriodoExecucao, siglaPeriodo) => {
      this.liberaPesquisa = true;
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.idPeriodoExecucao = idPeriodoExecucao;
      this.siglaPeriodo = siglaPeriodo;
      this.ano = ano;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    excluir(faseExecucao) {
      confirm("Você deseja realmente excluir a Fase Execução?") &&
        periodoExecucaoService.excluirFase(faseExecucao).then(() => {
          this.fasesExecucoes.splice(this.fasesExecucoes.indexOf(faseExecucao), 1);
          this.pesquisar();
        });
    },
    editar(faseExecucao) {
      EventBus.$emit("editaFaseExecucao", faseExecucao.id, this.nomeCurso, this.ano, this.siglaPeriodo);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await periodoExecucaoService.listarFasesExecucao(
          this.idCurso,
          this.idPeriodoExecucao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.fasesExecucoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.$emit("fasesExecucaoFechada", true);
        this.faseExecucaoAlterada = false;
      }, 300);
    }
  }
};
</script>
