<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-nivel @nivelCadastrado="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="niveis" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nome }}</td>
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
import Nivel from "@/model/basecurricular/Nivel";
import ModalCadastroNivel from "./ModalCadastroNivel";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";

export default {
  name: "ListaNivel",
  components: {
    ModalCadastroNivel
  },
  data() {
    return {
      dialog: false,
      niveis: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      nivel: new Nivel(),
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
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
    excluir(nivel) {
      confirm("Você deseja realmente excluir o Nível?") &&
        baseCurricularService.excluirNivel(nivel).then(() => {
          this.niveis.splice(
            this.niveis.indexOf(nivel),
            1
          );
          this.atualizar();
        });
    },
    editar(nivel) {
      EventBus.$emit("editaNivel", nivel.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await baseCurricularService.listarTodosNiveis(
        this.nivel,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.niveis = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
