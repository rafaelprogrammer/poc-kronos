<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-filiacao :id-pessoa="filiacao.idPessoaFilho"
      @filiacaoCadastrada="cadastroEfetuado = $event"></modal-cadastro-filiacao>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="filiacoes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomePessoaPais }}</td>
          <td>{{ props.item.nomeTipoFiliacao }}</td>
          <td>{{ props.item.filiacaoAtual | booleanFilter }}</td>
          <td>{{ props.item.dataFiliacao }}</td>
          <td>
            <div title="Excluir" >
              <v-icon medium class="mr-2" @click="excluir(props.item)" >
                delete
              </v-icon>
            </div>
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
import filiacaoService from "@/service/pessoa/FiliacaoService";
import Filiacao from "@/model/pessoa/Filiacao";
import ModalCadastroFiliacao from "./ModalCadastroFiliacao";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter } from "@/filters/Filters.js";
export default {
  name: "ListaFiliacao",
  components: {
    ModalCadastroFiliacao
  },
  filters: {
    booleanFilter: booleanFilter
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      filiacoes: [],
      filiacao: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomePessoaPais"
        },
        {
          text: "Tipo Filiação",
          align: "left",
          sortable: false,
          value: "nomeTipoFiliacao"
        },
        {
          text: "Atual",
          align: "left",
          sortable: false,
          value: "filiacaoAtual"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataFiliacao"
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
    this.filiacao = Object.assign({}, Filiacao);
  },
  methods: {
    excluir(filiacao) {
      confirm("Você deseja realmente excluir a Filiação?") &&
        filiacaoService.excluir(filiacao).then(() => {
          this.filiacoes.splice(this.filiacoes.indexOf(filiacao), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.filiacao.idPessoaFilho) {
        this.loading = true;
        const paginacaoResultado = await filiacaoService.listarTodos(
          this.filiacao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.filiacoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.filiacao.idPessoaFilho = this.idPessoa;
    }
  }
};
</script>
