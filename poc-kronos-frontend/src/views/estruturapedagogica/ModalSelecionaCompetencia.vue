<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn slot="activator" v-if="!desabilitaAcao" color="primary" @click="abrir()"><v-icon>add</v-icon>Adicionar</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Competência</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center wrap fill-height> 
                      <v-flex xs12 sm6 md3>
                        <v-select v-model="competencia.idNivel" item-text="nome" item-value="id"
                          @change="carregarComponentes(competencia.idNivel)"
                          :items="niveis" label="Nível"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-select v-model="competencia.idComponente" item-text="nome"
                          item-value="id" :items="componentes" label="Componente"
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-text-field color="primary" label="Código" v-model="competencia.codigo"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-checkbox color="primary" label="BNCC" v-model="competencia.bncc"></v-checkbox>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-checkbox color="primary" label="Ativo" v-model="competencia.ativo"></v-checkbox>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                      </v-flex>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="competencias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                <template slot="items" slot-scope="props">
                  <td width="10%">{{ props.item.nivel }}</td>
                  <td>{{ props.item.bncc | booleanFilter }}</td>
                  <td>{{ props.item.geral | booleanFilter }}</td>
                  <td>{{ props.item.codigo }}</td>
                  <td>{{ props.item.componente }}</td>
                  <td width="30%">{{ props.item.descricao }}</td>
                  <td>{{ props.item.ativo | booleanFilter }}</td>
                  <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                  </td>
                </template>
                <template slot="no-data">
                  Não existem informações cadastradas!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Competencia from "@/model/basecurricular/Competencia";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter } from "@/filters/Filters.js";

export default {
  name: "ModalSelecionaCompetencia",
  filters: {
    booleanFilter: booleanFilter
  },
  props: {
    desabilitaAcao: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialog: false,
      niveis: [],
      competencia: new Competencia(),
      competencias: [],
      componentes:[],
      anos: [],
      numeros: [],
      pagination: new Pagination(1, 5),
      loading: true,
      liberaPesquisa: false,
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
          text: "Geral",
          align: "left",
          sortable: false,
          value: "geral"
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
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    EventBus.$on("abrirSelecionaCompetencia", () => {
      this.abrir();
    });
  },
  methods: {
    abrir() {
      this.carregarDadosBasicos();
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    selecionar(competencia) {
      this.dialog = false;
      EventBus.$emit("selecionaCompetencia", competencia);
    },
    async atualizarComboNumero(ano) {
      this.numeros = await baseCurricularService.comboNumeros(ano);
    },
    async carregarComponentes(idNivel) {
      this.componentes = await baseCurricularService.comboComponentes(idNivel);
    },
    async carregarDadosBasicos() {
      this.niveis = await baseCurricularService.comboNiveis();
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await baseCurricularService.listarTodasCompetencias(
          this.competencia,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.competencias = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    limpar() {
      this.competencia = new Competencia();
      this.pesquisar();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.competencia = new Competencia();
        this.liberaPesquisa = false;
      }, 300);
    }
  }
};
</script>
