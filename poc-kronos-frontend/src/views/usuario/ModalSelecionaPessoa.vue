<template>
      <v-dialog v-model="dialog" persistent width="50%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="listarTodos()" absolute dark fab top right rightclass="mb-2"><v-icon>add</v-icon></v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Pessoa</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center row fill-height justify-space-between>  
                      <v-text-field color="primary" label="Nr Registro" v-model="form.numeroRegistro"></v-text-field>
                      <v-text-field color="primary" label="CPF" v-model="form.cpf"></v-text-field>
                      <v-text-field color="primary" label="Nome" v-model="form.nome"></v-text-field>
                      <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="pessoas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.cpf }}</td>
                    <td>{{ props.item.numeroRegistro }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    <v-alert :value="true" color="warning" icon="warning">
                      Pessoa não localizada!
                    </v-alert>
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
import pessoaService from "@/service/pessoa/PessoaService";
import EventBus from "@/utils/EventBus";
import Pessoa from "@/model/pessoa/Pessoa";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaPessoa",
  data() {
    return {
      dialog: false,
      pessoas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpf"
        },
        {
          text: "Nr Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
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
    this.form = Object.assign({}, Pessoa);
    // this.listarTodos();
  },
  methods: {
    selecionar(pessoa) {
      this.dialog = false;
      EventBus.$emit("selecionaPessoa", pessoa);
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await pessoaService.listarTodos(
        this.form,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.pessoas = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async listarTodos() {
      this.loading = true;
      const paginacaoResultado = await pessoaService.listarTodos(
        null,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.pessoas = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.form = Object.assign({}, Pessoa);
      }, 300);
    }
  }
};
</script>
