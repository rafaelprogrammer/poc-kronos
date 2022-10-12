<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm6 md12>
      <v-form @submit.prevent="pesquisar()" ref="form">
        <v-layout align-center wrap fill-height> 
          <v-flex xs12 sm6 md4>
            <v-select v-model="direito.idNivel" item-text="nome" item-value="id"
              :items="niveis" label="Nível"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md2>
            <v-text-field color="primary" label="Código" v-model="direito.codigo"></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md1>
             <check-box-state label="BNCC" @checkBoxStateAtualizado="direito.bncc = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <check-box-state label="Ativo" @checkBoxStateAtualizado="direito.ativo = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm2 md2>
            <modal-cadastro-direito :niveis="niveis" @direitoCadastrado="cadastroEfetuado = $event" />
          </v-flex>
        </v-layout>
      </v-form>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="direitos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nivel }}</td>
          <td>{{ props.item.bncc | booleanFilter }}</td>
          <td>{{ props.item.codigo }}</td>
          <td width="50%">{{ props.item.descricao }}</td>
          <td>{{ props.item.ativo | booleanFilter }}</td>
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
import Direito from "@/model/basecurricular/Direito";
import ModalCadastroDireito from "./ModalCadastroDireito";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter } from "@/filters/Filters.js";
import CheckBoxState from "@/components/Custom/CheckBoxState";

export default {
  name: "ListaDireito",
  components: {
    ModalCadastroDireito,
    CheckBoxState
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      dialog: false,
      idNivel: null,
      niveis: [],
      direitos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      direito: new Direito(),
      headers: [
        {
          text: "Nível",
          align: "left",
          sortable: false,
          value: "nivel"
        },
        {
          text: "BNCC",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
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
    idNivel() {
      this.pesquisar();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
  },
  methods: {
    limpar() {
      this.direito = new Direito();
      EventBus.$emit("resetCheckBoxState");
      this.pesquisar();
    },
    excluir(direito) {
      confirm("Você deseja realmente excluir Direito?") &&
        baseCurricularService.excluirDireito(direito).then(() => {
          this.direitos.splice(
            this.direitos.indexOf(direito),
            1
          );
          this.atualizar();
        });
    },
    editar(direito) {
      EventBus.$emit("editaDireito", direito.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.niveis = await baseCurricularService.comboNiveis();
    },
    async pesquisar() {
      this.loading = true;
      if (this.direito.codigo === "") {
        this.direito.codigo = null;
      }
      const paginacaoResultado = await baseCurricularService.listarTodosDireitos(
        this.direito,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.direitos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
