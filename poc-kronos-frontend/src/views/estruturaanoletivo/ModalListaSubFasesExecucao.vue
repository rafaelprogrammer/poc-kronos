<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <modal-cadastro-sub-fase-execucao @subFaseExecucaoAlterada="subFaseExecucaoAlterada = $event" />-->
        <v-card>
          <v-card-title>
            <span class="headline">Sub-Fases de Execução (Bimestres)</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap justify-space-between>
                <v-flex xs12 sm2 md12>
                  <v-subheader>Curso / Período /  Ano</v-subheader>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-text-field color="primary" disabled label="Curso" v-model="nomeCurso"></v-text-field>
                </v-flex>
                 <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" disabled label="Período" v-model="siglaPeriodo"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" disabled label="Ano" v-model="ano"></v-text-field>
                </v-flex>
                 <v-flex xs12 sm6 md2>
                <v-select v-model="idFaseExecucao" item-text="siglaFase" item-value="id" :items="fasesExecucoes" label="Fase"></v-select>
                </v-flex>
              </v-layout>
              <v-data-table v-if="idFaseExecucao" :loading="loading" :headers="headers" :items="subFasesExecucoes" class="elevation-1"
              :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.tipoPeriodo }}</td>
                    <td>{{ props.item.siglaSubFase }}</td>
                    <td>{{ props.item.numeroSubFase }}</td>
                    <td>{{ props.item.dataInicio }}</td>
                    <td>{{ props.item.dataFim }}</td>
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
import faseService from "@/service/curso/FaseService";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroSubFaseExecucao from "./ModalCadastroSubFaseExecucao";

export default {
  name: "ModalListaSubFasesExecucao",
  components: {
    ModalCadastroSubFaseExecucao
  },
  data() {
    return {
      dialog: false,
      idCurso: null,
      nomeCurso: null,
      ano: null,
      idFaseExecucao: null,
      idPeriodoExecucao: null,
      siglaPeriodo: null,
      subFasesExecucoes: [],
      fasesExecucoes: [],
      liberaPesquisa: false,
      pagination: new Pagination(1, 5),
      loading: true,
      subFaseExecucaoAlterada: false,
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
          value: "siglaSubFase"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numeroSubFase"
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
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    idFaseExecucao() {
      this.pesquisar();
    },
    subFaseExecucaoAlterada(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.subFaseExecucaoAlterada = false;
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
    EventBus.$on("abrirListaSubFasesExecucao", async (idCurso, nomeCurso, ano, idPeriodoExecucao, siglaPeriodo) => {
      this.liberaPesquisa = true;
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.idPeriodoExecucao = idPeriodoExecucao;
      this.siglaPeriodo = siglaPeriodo;
      this.ano = ano;
      this.fasesExecucoes = await faseService.comboFasesExecucao(this.idPeriodoExecucao);
      this.dialog = true;
    });
  },
  methods: {
    excluir(subFaseExecucao) {
      confirm("Você deseja realmente excluir a Sub-Fase Execução?") &&
        periodoExecucaoService.excluirSubFase(subFaseExecucao).then(() => {
          this.subFasesExecucoes.splice(this.subFasesExecucoes.indexOf(subFaseExecucao), 1);
          this.pesquisar();
        });
    },
    editar(subFaseExecucao) {
      EventBus.$emit("editaSubFaseExecucao", subFaseExecucao.id, this.nomeCurso, this.ano, this.siglaPeriodo);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await periodoExecucaoService.listarSubFasesExecucao(
          this.idCurso,
          this.idPeriodoExecucao,
          this.idFaseExecucao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.subFasesExecucoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.$emit("subFasesExecucaoFechada", true);
        this.subFaseExecucaoAlterada = false;
        this.idFaseExecucao = null;
      }, 300);
    }
  }
};
</script>
