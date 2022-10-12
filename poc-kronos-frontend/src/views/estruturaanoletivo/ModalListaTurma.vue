<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Turmas</span>
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
                <v-flex xs12 sm6 md2>
                  <modal-cadastro-turma :nome-curso="nomeCurso" :siglaPeriodo="siglaPeriodo" 
                  :id-periodo-execucao="idPeriodoExecucao" :id-calendario="idCalendario"
                  :ano="ano"
                  @turmaCadastrada="turmaCadastrada = $event" />
                </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="turmas" class="elevation-1"
              :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.ano }}</td>
                    <td>{{ props.item.sigla }}</td>
                    <td>{{ props.item.turno }}</td>
                    <td>{{ props.item.ativa | booleanFilter }}</td>
                    <td>{{ props.item.aberta | booleanFilter }}</td>
                    <td>{{ props.item.encerrada | booleanFilter }}</td>
                    <td>{{ props.item.calendario }}</td>
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
import turmaService from "@/service/turma/TurmaService";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroTurma from "./ModalCadastroTurma";
import { booleanFilter } from "@/filters/Filters.js";

export default {
  name: "ModalListaFasesExecucao",
  components: {
    ModalCadastroTurma
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      dialog: false,
      nomeCurso: null,
      ano: null,
      idPeriodoExecucao: null,
      siglaPeriodo: null,
      idCalendario: null,
      turmas: [],
      liberaPesquisa: false,
      pagination: new Pagination(1, 5),
      loading: true,
      turmaCadastrada: false,
      headers: [
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "ano"
        },
        {
          text: "Sigla",
          align: "left",
          sortable: false,
          value: "sigla"
        },
        {
          text: "Turno",
          align: "left",
          sortable: false,
          value: "turno"
        },
        {
          text: "Ativa",
          align: "left",
          sortable: false,
          value: "ativa"
        },
        {
          text: "Aberta",
          align: "left",
          sortable: false,
          value: "aberta"
        },
        {
          text: "Encerrada",
          align: "left",
          sortable: false,
          value: "encerrada"
        },
        {
          text: "Calendário",
          align: "left",
          sortable: false,
          value: "calendario"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    turmaCadastrada(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.turmaCadastrada = false;
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
    EventBus.$on("abrirListaTurma", (nomeCurso, ano, idPeriodoExecucao, siglaPeriodo, idCalendario) => {
      this.liberaPesquisa = true;
      this.nomeCurso = nomeCurso;
      this.idPeriodoExecucao = idPeriodoExecucao;
      this.siglaPeriodo = siglaPeriodo;
      this.idCalendario = idCalendario;
      this.ano = ano;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    excluir(turma) {
      confirm("Você deseja realmente excluir a Turma?") &&
        turmaService.excluir(turma).then(() => {
          this.turmas.splice(this.turmas.indexOf(turma), 1);
          this.pesquisar();
        });
    },
    editar(turma) {
      EventBus.$emit("editaTurma", turma.id, this.nomeCurso, this.ano, this.siglaPeriodo);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await turmaService.listarTodos(
          this.idPeriodoExecucao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.turmas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.$emit("turmaFechada", true);
        this.turmaCadastrada = false;
      }, 300);
    }
  }
};
</script>
