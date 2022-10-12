<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-talento :id-pessoa="talento.idPessoa"
      @talentoCadastrado="cadastroEfetuado = $event"></modal-cadastro-talento>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table id="talentos" :loading="loading" :headers="headers" :items="talentos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomeTipoTalento }}</td>
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
import talentoService from "@/service/pessoa/TalentoService";
import Talento from "@/model/pessoa/Talento";
import ModalCadastroTalento from "./ModalCadastroTalento";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ListaTalento",
  components: {
    ModalCadastroTalento
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      talentos: [],
      talento: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "nomeTipoTalento"
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
    this.talento = Object.assign({}, Talento);
  },
  methods: {
    excluir(talento) {
      confirm("Você deseja realmente excluir o Talento registrado?") &&
        talentoService.excluir(talento).then(() => {
          this.talentos.splice(this.talentos.indexOf(talento), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.talento.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await talentoService.listarTodos(
          this.talento,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.talentos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.talento.idPessoa = this.idPessoa;
    }
  }
};
</script>
