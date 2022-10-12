<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Documentos Previstos Matrícula</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout align-center row fill-height justify-space-between>  
                  <v-flex xs12 sm2 md6>
                  <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
                  </v-flex>
                   <v-flex xs12 sm2 md1>
                    <v-btn icon @click="atualizar">
                      <v-icon color="primary">refresh</v-icon>
                    </v-btn>
                  </v-flex>
                  <v-flex xs12 sm2 md5>
                    <modal-cadastro-curso-documento :id-curso="this.cursoDocumento.idCurso" :nome-curso="nomeCurso"
                      @cursoDocumentoCadastrado="cadastroEfetuado = $event"></modal-cadastro-curso-documento>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="cursosDocumentos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoDocumento }}</td>
                    <td>{{ props.item.numeroCopia }}</td>
                    <td>{{ props.item.original | booleanFilter }}</td>
                    <td>{{ props.item.autenticacao | booleanFilter }}</td>
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
                    Não existem informações!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Fechar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import cursoDocumentoService from "@/service/curso/CursoDocumentoService";
import EventBus from "@/utils/EventBus";
import CursoDocumento from "@/model/curso/curso-documento/CursoDocumento";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroCursoDocumento from "./ModalCadastroCursoDocumento";
import { booleanFilter } from "@/filters/Filters.js";
export default {
  name: "ModalListaCursoDocumento",
  components: {
    ModalCadastroCursoDocumento
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      cursoDocumento: new CursoDocumento(),
      cursosDocumentos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoDocumento"
        },
        {
          text: "Nr Cópia",
          align: "left",
          sortable: false,
          value: "numeroCopia"
        },
        {
          text: "Original",
          align: "left",
          sortable: false,
          value: "original"
        },
        {
          text: "Autenticado",
          align: "left",
          sortable: false,
          value: "autenticado"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
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
    EventBus.$on("abrirCursoDocumento", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    excluir(cursoDocumento) {
      confirm("Você deseja realmente excluir o documento do curso?") &&
        cursoDocumentoService.excluir(cursoDocumento).then(() => {
          this.cursosDocumentos.splice(
            this.cursosDocumentos.indexOf(cursoDocumento),
            1
          );
          this.pesquisar();
        });
    },
    editar(cursoDocumento) {
      EventBus.$emit("editaCursoDocumento", cursoDocumento.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdCurso();
      this.loading = true;
      const paginacaoResultado = await cursoDocumentoService.listarTodos(
        this.cursoDocumento,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.cursosDocumentos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    atualizarIdCurso() {
      this.cursoDocumento.idCurso = this.idCurso;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.cursoDocumento = new CursoDocumento();
        this.atualizarIdCurso();
      }, 300);
    }
  }
};
</script>
