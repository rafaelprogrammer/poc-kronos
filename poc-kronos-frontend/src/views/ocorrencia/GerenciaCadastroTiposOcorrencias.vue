<template>
  <div>
    <section-definition title="Tipos de Ocorrências" event-update="pesquisar"></section-definition>
    <v-toolbar dark color="primary">
      <v-toolbar-title>Gerenciar Tipos de Ocorrências</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
      <modal-cadastro-tipo-ocorrencia @tipoOcorrenciaCadastrada="cadastroEfetuado = $event"/>
    </v-toolbar>
    <v-container fluid>
    <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
          <v-layout align-center row fill-height justify-space-between>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Código" v-model="tipoOcorrencia.codigo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select item-text="nome" v-model="tipoOcorrencia.fator" item-value="valor" :items="fatores" label="Fator (P/N)"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select item-text="nome" v-model="tipoOcorrencia.vigente" item-value="valor" :items="vigentes" label="Vigente"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
        </v-layout>
      </v-form>
    <v-data-table :loading="loading" :headers="headers" :items="tiposOcorrencias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
      <template slot="items" slot-scope="props">
        <td>{{ props.item.codigo }}</td>
        <td>{{ props.item.nomeFator }}</td>
        <td>{{ props.item.valor }}</td>
        <td>{{ props.item.dataInicioVigencia }}</td>
        <td>{{ props.item.descricao }}</td>
        <td>{{ props.item.dataFinalVigencia }}</td>
        <td>
            <v-icon medium class="mr-2" @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon medium class="mr-2" @click="deleteItem(props.item)" >
              delete
            </v-icon>
        </td>
      </template>
       <template slot="no-data">
          Não existem informações cadastradas!
      </template>
    </v-data-table>
    </v-container>
  </div>
</template>
<script>
import ocorrenciaService from "@/service/ocorrencia/OcorrenciaService";
import TipoOcorrencia from "@/model/ocorrencia/TipoOcorrencia";
import EventBus from "@/utils/EventBus.js";
import Pagination from "@/utils/Pagination.js";
// import ModalSelecionaPessoa from "./ModalSelecionaPessoa";
import ModalCadastroTipoOcorrencia from "./ModalCadastroTipoOcorrencia";
// import { booleanFilter } from "@/filters/Filters.js";

export default {
  components: {
    ModalCadastroTipoOcorrencia
  },
  // filters: {
  //   booleanFilter: booleanFilter
  // },
  data() {
    return {
      dialog: false,
      loading: true,
      pagination: new Pagination(1, 10),
      cadastroEfetuado: false,
      tiposOcorrencias: [],
      fatores: [],
      vigentes: [],
      tipoOcorrencia: new TipoOcorrencia(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Fator",
          align: "left",
          sortable: false,
          value: "nomeFator"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valor"
        },
        {
          text: "Data Inicio Vigência",
          align: "left",
          sortable: false,
          value: "dataInicioVigencia"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        },
        {
          text: "Data Final Vigência",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
        },
        { text: "Ações", value: "name", sortable: false }
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
      this.tipoOcorrencia = new TipoOcorrencia();
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await ocorrenciaService.listarTodosTiposOcorrencias(
        this.tipoOcorrencia,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.tiposOcorrencias = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    deleteItem(tipoOcorrencia) {
      confirm("Você deseja realmente excluir o Tipo Ocorrência?") &&
        ocorrenciaService.excluirTipoOcorrencia(tipoOcorrencia).then(() => {
          this.tiposOcorrencias.splice(this.tiposOcorrencias.indexOf(tipoOcorrencia), 1);
          this.pesquisar();
        });
    },
    editItem(tipoOcorrencia) {
      EventBus.$emit("editaTipoOcorrencia", tipoOcorrencia.id);
    },
    async carregarDadosBasicos() {
      this.fatores = await ocorrenciaService.comboFatorTipoOcorrencia();
      this.vigentes = await ocorrenciaService.comboVigenteTipoOcorrencia();
    }
  }
};
</script>
