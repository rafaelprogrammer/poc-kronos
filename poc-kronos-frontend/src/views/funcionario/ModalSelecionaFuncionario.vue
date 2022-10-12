<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Funcionário</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center row fill-height justify-space-between>
                      <v-flex xs12 sm6 md6>
                        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
                      </v-flex>
                      <v-text-field color="primary" label="Nr Registro" v-model="funcionario.numeroRegistro"></v-text-field>
                      <v-text-field color="primary" label="CPF" v-model="funcionario.cpf"></v-text-field>
                      <v-text-field color="primary" label="Nome" v-model="funcionario.nome"></v-text-field>
                      <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="funcionarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numeroRegistro }}</td>
                    <td>{{ props.item.cpf }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
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
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import EventBus from "@/utils/EventBus";
import Funcionario from "@/model/funcionario/Funcionario";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaFuncionario",
  data() {
    return {
      dialog: false,
      funcionario: new Funcionario(),
      funcionarios: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Nr Registro",
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
    EventBus.$on("abrirSelecionaFuncionario", () => {
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    selecionar(funcionario) {
      this.dialog = false;
      EventBus.$emit("selecionaFuncionario", funcionario);
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await funcionarioService.listarTodos(
        this.funcionario,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.funcionarios = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.funcionario = new Funcionario();
      }, 300);
    }
  }
};
</script>
