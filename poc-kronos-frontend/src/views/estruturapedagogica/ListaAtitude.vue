<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-atitude @atitudeCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="atitudes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigo }}</td>
          <td>{{ props.item.valor }}</td>
          <td>{{ props.item.nome }}</td>
          <td>{{ props.item.ativo }}</td>
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
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Atitude from "@/model/basecurricular/Atitude";
import ModalCadastroAtitude from "./ModalCadastroAtitude";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";

export default {
  name: "ListaAtitude",
  components: {
    ModalCadastroAtitude
  },
  data() {
    return {
      dialog: false,
      atitudes: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      atitude: new Atitude(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valor"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Ativo",
          align: "left",
          sortable: false,
          value: "ativo"
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
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  methods: {
    excluir(atitude) {
      confirm("Você deseja realmente excluir o Atitude?") &&
        baseCurricularService.excluirAtitude(atitude).then(() => {
          this.atitudes.splice(
            this.atitudes.indexOf(atitude),
            1
          );
          this.atualizar();
        });
    },
    editar(atitude) {
      EventBus.$emit("editaAtitude", atitude.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await baseCurricularService.listarTodasAtitudes(
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.atitudes = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
