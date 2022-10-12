<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-endereco-pessoa :id-pessoa="enderecoPessoa.idPessoa"
      @enderecoPessoaCadastrado="cadastroEfetuado = $event"></modal-cadastro-endereco-pessoa>  
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="enderecosPessoas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.cep }}</td>
          <td>{{ props.item.nomeTipoEndereco }}</td>
          <td>{{ props.item.logradouro }}</td>
          <td>{{ props.item.numero }}</td>
          <td>{{ props.item.bairro }}</td>
          <td>{{ props.item.localidade }}</td>
          <td>{{ props.item.uf }}</td>
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
import enderecoPessoaService from "@/service/pessoa/EnderecoPessoaService";
import EnderecoPessoa from "@/model/pessoa/EnderecoPessoa";
import ModalCadastroEnderecoPessoa from "./ModalCadastroEnderecoPessoa";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ListaEnderecoPessoa",
  components: {
    ModalCadastroEnderecoPessoa
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      enderecosPessoas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "CEP",
          align: "left",
          sortable: false,
          value: "cep"
        },
        {
          text: "Tipo Endereço",
          align: "left",
          sortable: false,
          value: "idTipoEndereco"
        },
        {
          text: "Logradouro",
          align: "left",
          sortable: false,
          value: "logradouro"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Bairro",
          align: "left",
          sortable: false,
          value: "bairro"
        },
        {
          text: "Localidade",
          align: "left",
          sortable: false,
          value: "localidade"
        },
        {
          text: "UF",
          align: "left",
          sortable: false,
          value: "uf"
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
    this.enderecoPessoa = Object.assign({}, EnderecoPessoa);
  },
  methods: {
    excluir(endereco) {
      confirm("Você deseja realmente excluir o Endereço?") &&
        enderecoPessoaService.excluir(endereco).then(() => {
          this.enderecosPessoas.splice(
            this.enderecosPessoas.indexOf(endereco),
            1
          );
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.enderecoPessoa.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await enderecoPessoaService.listarTodos(
          this.enderecoPessoa,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.enderecosPessoas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.enderecoPessoa.idPessoa = this.idPessoa;
    }
  }
};
</script>
