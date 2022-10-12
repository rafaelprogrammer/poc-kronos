<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-responsavel :id-pessoa="responsavel.idPessoa"
      @responsavelCadastrado="cadastroEfetuado = $event"></modal-cadastro-responsavel>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="responsaveis" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomePessoaResponsavel }}</td>
          <td>{{ props.item.nomeTipoResponsavel }}</td>
          <td>{{ props.item.dataInicio }}</td>
          <td>{{ props.item.dataFim }}</td>
          <td>{{ props.item.prioridade }}</td>
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
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</div>
</template>
<script>
import responsavelService from "@/service/pessoa/ResponsavelService";
import Responsavel from "@/model/pessoa/Responsavel";
import ModalCadastroResponsavel from "./ModalCadastroResponsavel";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
export default {
  name: "ListaResponsavel",
  components: {
    ModalCadastroResponsavel
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      responsaveis: [],
      responsavel: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomePessoaResponsavel"
        },
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoResponsavel"
        },
        {
          text: "Data Início",
          align: "left",
          sortable: false,
          value: "dataInicio"
        },
        {
          text: "Data Fim",
          align: "left",
          sortable: false,
          value: "dataFim"
        },
        {
          text: "Prioridade",
          align: "left",
          sortable: false,
          value: "prioridade"
        },
        { text: "", value: "name", sortable: false }
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
    idPessoa() {
      this.pesquisar();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.responsavel = Object.assign({}, Responsavel);
  },
  methods: {
    editar(responsavel) {
      EventBus.$emit("editaResponsavel", responsavel.id);
    },
    excluir(responsavel) {
      confirm("Você deseja realmente excluir o Responsável?") &&
        responsavelService.excluir(responsavel).then(() => {
          this.responsaveis.splice(this.responsaveis.indexOf(responsavel), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.responsavel.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await responsavelService.listarTodos(
          this.responsavel,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.responsaveis = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.responsavel.idPessoa = this.idPessoa;
    }
  }
};
</script>
