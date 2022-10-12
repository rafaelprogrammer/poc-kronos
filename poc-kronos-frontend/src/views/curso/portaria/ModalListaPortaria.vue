<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Portarias</span>
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
                    <modal-cadastro-portaria :id-curso="this.portaria.idCurso" :nome-curso="nomeCurso"
                      @portariaCadastrada="cadastroEfetuado = $event"></modal-cadastro-portaria>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="portarias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoPortaria }}</td>
                    <td>{{ props.item.descricao }}</td>
                    <td>{{ props.item.dataPublicacao }}</td>
                    <td>{{ props.item.dataInicioVigencia }}</td>
                    <td>{{ props.item.dataFinalVigencia }}</td>
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
                    Portaria não localizada!
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
import portariaService from "@/service/curso/PortariaService";
import EventBus from "@/utils/EventBus";
import Portaria from "@/model/curso/portaria/Portaria";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroPortaria from "./ModalCadastroPortaria";
export default {
  name: "ModalListaPortaria",
  components: {
    ModalCadastroPortaria
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      portaria: new Portaria(),
      portarias: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoPortaria"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataPublicacao"
        },
        {
          text: "Data Início Vigência",
          align: "left",
          sortable: false,
          value: "dataInicioVigencia"
        },
        {
          text: "Data Final Vigência",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
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
    EventBus.$on("abrirPortaria", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    excluir(portaria) {
      confirm("Você deseja realmente excluir a Portaria?") &&
        portariaService.excluir(portaria).then(() => {
          this.portarias.splice(this.portarias.indexOf(portaria), 1);
          this.pesquisar();
        });
    },
    editar(portaria) {
      EventBus.$emit("editaPortaria", portaria.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdCurso();
      this.loading = true;
      const paginacaoResultado = await portariaService.listarTodos(
        this.portaria,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.portarias = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    atualizarIdCurso() {
      this.portaria.idCurso = this.idCurso;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.portaria = new Portaria();
        this.atualizarIdCurso();
      }, 300);
    }
  }
};
</script>
