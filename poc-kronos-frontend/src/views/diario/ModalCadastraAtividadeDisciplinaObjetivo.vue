<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn slot="activator" small color="primary" @click="abrir()"><v-icon>add</v-icon>Adicionar</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Objetivo</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                <v-layout align-center wrap fill-height>
                    <v-flex xs12 sm6 md3>
                        <v-select v-model="idSubFaseFiltro" item-text="sigla" item-value="id" :items="subFases"
                        label="Bimestre">
                        </v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" label="Código" v-model="codigo"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md3>
                      <v-text-field color="primary" label="Descrição" v-model="descricao"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md3>
                        <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                        <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                    </v-flex>
                  </v-layout>
                </v-form>
              <v-data-table v-model="selected" select-all :loading="loading" :headers="headers" :items="disciplinasObjetivos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                <template slot="items" slot-scope="props">
                  <tr :active="props.selected" @click="props.selected = !props.selected">
                    <td>
                      <v-checkbox
                        :input-value="props.selected"
                        primary
                        hide-details
                      ></v-checkbox>
                    </td>
                    <td>{{ props.item.codigo }}</td>
                    <td>{{ props.item.bncc | booleanFilter }}</td>
                    <td>{{ props.item.siglaSubFase }}</td>
                    <td width="50%">
                      <v-tooltip bottom>
                          <span slot="activator" class="">{{ props.item.descricao | limitCaracteres(100) }}</span>
                          <span>{{ props.item.descricao }}</span>
                      </v-tooltip>
                    </td>
                    <td>{{ props.item.ativo | booleanFilter }}</td>
                  </tr>
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
            <v-btn color="primary" type="submit" v-if="this.selected && this.selected.length > 0" @click.native="salvar" dark>Selecionar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import subFaseService from "@/service/curso/SubFaseService";
import atividadeService from "@/service/atividade/AtividadeService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import DisciplinaObjetivo from "@/model/disciplina/objetivo/DisciplinaObjetivo";
import AtividadeDisciplinaObjetivo from "@/model/atividade/objetivo/AtividadeDisciplinaObjetivo";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";

export default {
  name: "ModalCadastraAtividadeDisciplinaObjetivo",
  props: {
    idDisciplina: Number,
    idAtividade: Number,
    idSubFase: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      codigo: null,
      descricao: null,
      idSubFaseFiltro: null,
      subFases: [],
      dialog: false,
      atividadeDisciplinaObjetivo: new AtividadeDisciplinaObjetivo(),
      disciplinasObjetivos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      liberaPesquisa: false,
      selected: [],
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "BNCC",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Bimestre",
          align: "left",
          sortable: false,
          value: "siglaSubFase"
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
    idSubFaseFiltro() {
      this.liberaPesquisa = true;
      this.pesquisar();
    },
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  methods: {
    limpar() {
      this.codigo = null;
      this.descricao = null;
      this.pesquisar();
    },
    abrir() {
      this.carregarDadosBasicos();
      this.idSubFaseFiltro = this.idSubFase;
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    salvar() {
      this.atividadeDisciplinaObjetivo.idsDisciplinaObjetivo = this.selected.map(s => s.id);
      this.atividadeDisciplinaObjetivo.idAtividade = this.idAtividade;
      atividadeService.salvarAtividadeDisciplinaObjetivo(this.atividadeDisciplinaObjetivo).then(() => {
        this.$emit("atividadeDisciplinaObjetivoCadastrada", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.subFases = await subFaseService.combo(this.idCurso, this.idPeriodo);
      this.subFases.unshift({"id": null, "sigla": "Todos"});
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await disciplinaService.listarParaAtividadeDisciplinaObjetivo(
          this.idDisciplina,
          this.idAtividade,
          this.idSubFaseFiltro,
          this.codigo,
          this.descricao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.disciplinasObjetivos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.atividadeDisciplinaObjetivo = new AtividadeDisciplinaObjetivo();
        this.liberaPesquisa = false;
        this.codigo = null;
        this.descricao = null;
        this.selected = [];
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
