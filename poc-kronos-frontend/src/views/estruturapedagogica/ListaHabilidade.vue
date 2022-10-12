<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm6 md12>
      <v-form @submit.prevent="pesquisar()" ref="form">
        <v-layout align-center wrap fill-height> 
          <v-flex xs12 sm6 md3>
            <v-select v-model="habilidade.idNivel" item-text="nome" item-value="id"
               @change="carregarComponentes(habilidade.idNivel)"
              :items="niveis" label="Nível"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md4>
            <v-select v-model="habilidade.idComponente" item-text="nome"
              item-value="id" :items="componentes" label="Componente"
            ></v-select>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-text-field color="primary" label="Código" v-model="habilidade.codigo"></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <check-box-state label="BNCC" @checkBoxStateAtualizado="habilidade.bncc = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <check-box-state label="Ativo" @checkBoxStateAtualizado="habilidade.ativo = $event"/>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm6 md1>
            <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
          </v-flex>
          <v-flex xs12 sm2 md2>
            <modal-cadastro-habilidade :niveis="niveis" @habilidadeCadastrada="cadastroEfetuado = $event" />
          </v-flex>
        </v-layout>
      </v-form>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="habilidades" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td width="10%">{{ props.item.nivel }}</td>
          <td>{{ props.item.bncc | booleanFilter }}</td>
          <td>{{ props.item.codigo }}</td>
          <td>{{ props.item.componente }}</td>
          <td width="30%">{{ props.item.descricao }}</td>
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
import Habilidade from "@/model/basecurricular/Habilidade";
import ModalCadastroHabilidade from "./ModalCadastroHabilidade";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter } from "@/filters/Filters.js";
import CheckBoxState from "@/components/Custom/CheckBoxState";

export default {
  name: "ListaHabilidade",
  components: {
    ModalCadastroHabilidade,
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
      habilidades: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      habilidade: new Habilidade(),
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
          text: "Componente",
          align: "left",
          sortable: false,
          value: "componente"
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
      this.habilidade = new Habilidade();
      EventBus.$emit("resetCheckBoxState");
      this.pesquisar();
    },
    excluir(habilidade) {
      confirm("Você deseja realmente excluir Habilidade?") &&
        baseCurricularService.excluirHabilidade(habilidade).then(() => {
          this.habilidades.splice(
            this.habilidades.indexOf(habilidade),
            1
          );
          this.atualizar();
        });
    },
    editar(habilidade) {
      EventBus.$emit("editaHabilidade", habilidade.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async carregarComponentes(idNivel) {
      this.componentes = await baseCurricularService.comboComponentes(idNivel);
    },
    async carregarDadosBasicos() {
      this.niveis = await baseCurricularService.comboNiveis();
    },
    async pesquisar() {
      this.loading = true;
      if (this.habilidade.codigo === "") {
        this.habilidade.codigo = null;
      }
      const paginacaoResultado = await baseCurricularService.listarTodasHabilidades(
        this.habilidade,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.habilidades = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
