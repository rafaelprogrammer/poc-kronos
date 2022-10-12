<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-telefone-pessoa :id-pessoa="telefonePessoa.idPessoa"
      @telefonePessoaCadastrado="cadastroEfetuado = $event"></modal-cadastro-telefone-pessoa>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="telefonesPessoas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomeCidade }}</td>
          <td>{{ props.item.ddd }}</td>
          <td>{{ props.item.numero }}</td>
          <td>{{ props.item.nomeTipoTelefone }}</td>
          <td>{{ props.item.nomeTipoUso }}</td>
          <td>{{ props.item.nomeOperadora }}</td>
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
import telefonePessoaService from "@/service/pessoa/TelefonePessoaService";
import TelefonePessoa from "@/model/pessoa/TelefonePessoa";
import ModalCadastroTelefonePessoa from "./ModalCadastroTelefonePessoa";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ListaTelefonePessoa",
  components: {
    ModalCadastroTelefonePessoa
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      telefonesPessoas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Cidade",
          align: "left",
          sortable: false,
          value: "nomeCidade"
        },
        {
          text: "DDD",
          align: "left",
          sortable: false,
          value: "ddd"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Tipo Telefone",
          align: "left",
          sortable: false,
          value: "idTipoTelefone"
        },
        {
          text: "Uso",
          align: "left",
          sortable: false,
          value: "idTipoUso"
        },
        {
          text: "Operadora",
          align: "left",
          sortable: false,
          value: "idOperadora"
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
    this.telefonePessoa = Object.assign({}, TelefonePessoa);
  },
  methods: {
    excluir(telefone) {
      confirm("Você deseja realmente excluir o Talento registrado?") &&
        telefonePessoaService.excluir(telefone).then(() => {
          this.telefonesPessoas.splice(
            this.telefonesPessoas.indexOf(telefone),
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
      if (this.telefonePessoa.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await telefonePessoaService.listarTodos(
          this.telefonePessoa,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.telefonesPessoas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.telefonePessoa.idPessoa = this.idPessoa;
    }
  }
};
</script>
