<template>
  <div>
    <section-definition :title="page.title" event-update="atualizarUsuario"></section-definition>
    <v-toolbar dark color="primary">
      <v-toolbar-title>Lista de Usuários</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
      <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
          <v-layout align-center row fill-height justify-space-between>  
              <v-text-field color="primary" label="Nr Registro" v-model="form.numeroRegistro"></v-text-field>
              <v-text-field color="primary" label="CPF" v-model="form.cpf"></v-text-field>
              <v-text-field color="primary" label="Nome" v-model="form.nome"></v-text-field>
              <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
        </v-layout>
      </v-form>
      <v-spacer></v-spacer>
      <modal-seleciona-pessoa></modal-seleciona-pessoa>
      <modal-cadastro-usuario @usuarioCadastrado="cadastroEfetuado = $event"></modal-cadastro-usuario>
    </v-toolbar>
    <v-data-table :loading="loading" :headers="headers" :items="usuarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
      <template slot="items" slot-scope="props">
        <td>{{ props.item.cpf }}</td>
        <td>{{ props.item.numeroRegistro }}</td>
        <td>{{ props.item.nome }}</td>
        <td>{{ props.item.dataNascimento }}</td>
        <td>{{ props.item.email }}</td>
        <td>{{ props.item.ativo | booleanFilter }}</td>
        <td>{{ props.item.bloqueado | booleanFilter }}</td>
        <td>
            <v-icon medium class="mr-2" @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon medium class="mr-2" @click="deleteItem(props.item)" >
              delete
            </v-icon>
        </td>
      </template>
       <template slot="no-data">
          Não existem informações cadastradas!
      </template>
    </v-data-table>
  </div>
</template>
<script>
import usuarioService from "@/service/UsuarioService";
import Usuario from "@/model/Usuario";
import EventBus from "@/utils/EventBus.js";
import Pagination from "@/utils/Pagination.js";
import ModalSelecionaPessoa from "./ModalSelecionaPessoa";
import ModalCadastroUsuario from "./ModalCadastroUsuario";
import { booleanFilter } from "@/filters/Filters.js";

export default {
  components: {
    ModalSelecionaPessoa,
    ModalCadastroUsuario
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      page: {
        title: "Usuários",
        headline: "Lista de Usuários",
        description: `A blank page is good to quick-start from scratch.`
      },
      pages: 0,
      dialog: false,
      loading: true,
      pagination: new Pagination(1, 10),
      cadastroEfetuado: false,
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
        {
          text: "Data Nasc",
          align: "left",
          sortable: false,
          value: "dataNascimento"
        },
        {
          text: "Email",
          align: "left",
          sortable: false,
          value: "email"
        },
        {
          text: "Ativo",
          align: "left",
          sortable: false,
          value: "ativo"
        },
        {
          text: "Bloqueado",
          align: "left",
          sortable: false,
          value: "bloqueado"
        },
        { text: "Ações", value: "name", sortable: false }
      ],
      usuarios: []
    };
  },
  created() {
    this.form = Object.assign({}, Usuario);
    this.atualizar();
    EventBus.$on("atualizarUsuario", () => {
      this.atualizar();
    });
  },
  methods: {
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await usuarioService.listarTodos(
        this.form,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.usuarios = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async atualizar() {
      this.loading = true;
      const paginacaoResultado = await usuarioService.listarTodos(
        null,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.usuarios = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    deleteItem(item) {
      confirm("Você deseja realmente excluir o usuário?") &&
        usuarioService.excluir(item).then(() => {
          this.pesquisar();
        });
    },
    editItem(item) {
      EventBus.$emit("editaUsuario", item.id, item.idPessoa);
    }
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
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  }
};
</script>
