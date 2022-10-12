<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm6 md3>
        <v-select v-model="idNivel" item-text="nome" item-value="id"
          :items="niveis" label="Nível"></v-select>
    </v-flex>
    <v-flex xs12 sm2 md2 v-if="idNivel">
      <modal-cadastro-campo-experiencia :niveis="niveis" @campoExperienciaCadastrado="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1 v-if="idNivel">
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12 v-if="idNivel">
      <v-data-table :loading="loading" :headers="headers" :items="camposExperiencias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nivel }}</td>
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
import CampoExperiencia from "@/model/basecurricular/CampoExperiencia";
import ModalCadastroCampoExperiencia from "./ModalCadastroCampoExperiencia";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";

export default {
  name: "ListaCampoExperiencia",
  components: {
    ModalCadastroCampoExperiencia
  },
  data() {
    return {
      dialog: false,
      idNivel: null,
      niveis: [],
      camposExperiencias: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      campoExperiencia: new CampoExperiencia(),
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
    excluir(campoExperiencia) {
      confirm("Você deseja realmente excluir Campo Experiência?") &&
        baseCurricularService.excluirCampoExperiencia(campoExperiencia).then(() => {
          this.camposExperiencias.splice(
            this.camposExperiencias.indexOf(campoExperiencia),
            1
          );
          this.atualizar();
        });
    },
    editar(campoExperiencia) {
      EventBus.$emit("editaCampoExperiencia", campoExperiencia.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.niveis = await baseCurricularService.comboNiveis();
    },
    async pesquisar() {
     if(this.idNivel) {
        this.loading = true;
        this.campoExperiencia.idNivel = this.idNivel;
        const paginacaoResultado = await baseCurricularService.listarTodosCamposExperiencias(
          this.campoExperiencia,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.camposExperiencias = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
     }
    }
  }
};
</script>
