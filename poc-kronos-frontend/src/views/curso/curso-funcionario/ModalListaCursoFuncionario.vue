<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Funções no Curso</span>
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
                    <modal-cadastro-curso-funcionario :id-curso="this.cursoFuncionario.idCurso" :nome-curso="nomeCurso"
                      @cursoFuncionarioCadastrado="cadastroEfetuado = $event"></modal-cadastro-curso-funcionario>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="cursosFuncionarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoFuncao }}</td>
                    <td>{{ props.item.nomeFuncionario }}</td>
                    <td>{{ props.item.dataInicio }}</td>
                    <td>{{ props.item.dataFinal }}</td>
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
                    Não existem infomações!
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
import cursoFuncionarioService from "@/service/curso/CursoFuncionarioService";
import EventBus from "@/utils/EventBus";
import CursoFuncionario from "@/model/curso/curso-funcionario/CursoFuncionario";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroCursoFuncionario from "./ModalCadastroCursoFuncionario";
export default {
  name: "ModalListaCursoFuncionario",
  components: {
    ModalCadastroCursoFuncionario
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      cursoFuncionario: new CursoFuncionario(),
      cursosFuncionarios: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoFuncao"
        },
        {
          text: "Funcionário",
          align: "left",
          sortable: false,
          value: "nomeFuncionario"
        },
        {
          text: "Data Início",
          align: "left",
          sortable: false,
          value: "dataInicio"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "dataFinal"
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
    EventBus.$on("abrirCursoFuncionario", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    excluir(cursoFuncionario) {
      confirm("Você deseja realmente excluir o Funcionário?") &&
        cursoFuncionarioService.excluir(cursoFuncionario).then(() => {
          this.cursosFuncionarios.splice(
            this.cursosFuncionarios.indexOf(cursoFuncionario),
            1
          );
          this.pesquisar();
        });
    },
    editar(cursoFuncionario) {
      EventBus.$emit("editaCursoFuncionario", cursoFuncionario.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdCurso();
      this.loading = true;
      const paginacaoResultado = await cursoFuncionarioService.listarTodos(
        this.cursoFuncionario,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.cursosFuncionarios = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    atualizarIdCurso() {
      this.cursoFuncionario.idCurso = this.idCurso;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.cursoFuncionario = new CursoFuncionario();
        this.atualizarIdCurso();
      }, 300);
    }
  }
};
</script>
