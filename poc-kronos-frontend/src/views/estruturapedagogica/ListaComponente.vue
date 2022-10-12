<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm6 md12>
      <v-form @submit.prevent="pesquisar()" ref="form">
        <v-layout align-center wrap fill-height> 
          <v-flex xs12 sm6 md2>
            <v-select v-model="idNivel" item-text="nome" item-value="id"
              :items="niveis" label="Nível"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md4>
            <v-text-field color="primary" label="Nome" v-model="componente.nome"></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md1>
             <check-box-state label="BNCC" @checkBoxStateAtualizado="componente.bncc = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <check-box-state label="Ativo" @checkBoxStateAtualizado="componente.ativo = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm2 md2>
            <modal-cadastro-componente :niveis="niveis" @componenteCadastrado="cadastroEfetuado = $event" />
          </v-flex>
        </v-layout>
      </v-form>
      
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="componentes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nivel }}</td>
          <td>{{ props.item.nome }}</td>
          <td>{{ props.item.bncc | booleanFilter }}</td>
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
import Componente from "@/model/basecurricular/Componente";
import ModalCadastroComponente from "./ModalCadastroComponente";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter } from "@/filters/Filters.js";
import CheckBoxState from "@/components/Custom/CheckBoxState";

export default {
  name: "ListaComponente",
  components: {
    ModalCadastroComponente,
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
      componentes: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      componente: new Componente(),
      headers: [
        {
          text: "Nível",
          align: "left",
          sortable: false,
          value: "nivel"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
         {
          text: "BNCC",
          align: "left",
          sortable: false,
          value: "bncc"
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
  created() {
    this.carregarDadosBasicos();
  },
  methods: {
    limpar() {
      this.componente = new Componente();
      this.idNivel = null;
      EventBus.$emit("resetCheckBoxState");
      this.pesquisar();
    },
    excluir(componente) {
      confirm("Você deseja realmente excluir Componente?") &&
        baseCurricularService.excluirComponente(componente).then(() => {
          this.componentes.splice(
            this.componentes.indexOf(componente),
            1
          );
          this.atualizar();
        });
    },
    editar(componente) {
      EventBus.$emit("editaComponente", componente.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.niveis = await baseCurricularService.comboNiveis();
    },
    async pesquisar() {
        this.loading = true;
        this.componente.idNivel = this.idNivel;
        const paginacaoResultado = await baseCurricularService.listarTodosComponentes(
          this.componente,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.componentes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
    }
  }
};
</script>
