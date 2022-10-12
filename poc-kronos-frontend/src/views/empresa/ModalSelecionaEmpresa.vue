<template>
      <v-dialog v-model="dialog" persistent width="50%">
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Empresa</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center row fill-height justify-space-between>  
                      <v-text-field color="primary" label="CNPJ" v-model="empresa.cnpj"></v-text-field>
                      <v-text-field color="primary" label="Razão Social" v-model="empresa.razaoSocial"></v-text-field>
                      <v-text-field color="primary" label="Nome Fantasia" v-model="empresa.nomeFantasia"></v-text-field>
                      <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="empresas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.cnpj }}</td>
                    <td>{{ props.item.razaoSocial }}</td>
                    <td>{{ props.item.nomeFantasia }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Empresa não localizada!
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
import empresaService from "@/service/empresa/EmpresaService";
import EventBus from "@/utils/EventBus";
import Empresa from "@/model/empresa/Empresa";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaEmpresa",
  data() {
    return {
      dialog: false,
      empresa: new Empresa(),
      empresas: [],
      pagination: new Pagination(1, 5),
      liberaPesquisa: false,
      loading: true,
      headers: [
        {
          text: "CNPJ",
          align: "left",
          sortable: false,
          value: "cnpj"
        },
        {
          text: "Razão Social",
          align: "left",
          sortable: false,
          value: "razaoSocial"
        },
        {
          text: "Nome Fantasia",
          align: "left",
          sortable: false,
          value: "nomeFantasia"
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
    EventBus.$on("abrirSelecionaEmpresa", () => {
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    selecionar(empresa) {
      this.dialog = false;
      EventBus.$emit("selecionaEmpresa", empresa);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await empresaService.listarTodos(
          this.empresa,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.empresas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.empresa = new Empresa();
        this.liberaPesquisa = false;
      }, 300);
    }
  }
};
</script>
