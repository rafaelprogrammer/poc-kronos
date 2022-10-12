<template>
  <v-layout row justify-center>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="abrir()"><v-icon>add</v-icon></v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Habilidade</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-data-table v-model="selected" select-all :loading="loading" :headers="headers" :items="disciplinasHabilidades" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
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
    </v-layout>
</template>
<script>
import atividadeService from "@/service/atividade/AtividadeService";
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import DisciplinaHabilidade from "@/model/disciplina/habilidade/DisciplinaHabilidade";
import AvaliacaoHabilidade from "@/model/avaliacao/AvaliacaoHabilidade";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";

export default {
  name: "ModalSelecionaDisciplinaHabilidade",
  props: {
    idAvaliacao: Number,
    idDisciplina: Number,
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      dialog: false,
      pagination: new Pagination(1, 5),
      loading: true,
      disciplinasHabilidades: [],
      avaliacoesHabilidades: [],
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
  methods: {
    abrir() {
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    salvar() {
      this.selected.map(s => {
        let avaliacaoHabilidade = new AvaliacaoHabilidade();
        avaliacaoHabilidade.idAvaliacao = this.idAvaliacao;
        avaliacaoHabilidade.idDisciplinaHabilidade = s.id;
        avaliacaoHabilidade.codigo = s.codigo;
        avaliacaoHabilidade.descricao = s.descricao;
        this.avaliacoesHabilidades.push(avaliacaoHabilidade);
      });
      avaliacaoService.salvarAvaliacaoHabilidade(this.avaliacoesHabilidades).then(() => {
        this.$emit("avaliacaoHabilidadeCadastrada", true);
        this.dialog = false;
      });
    },
    async pesquisar() {
      if (this.liberaPesquisa) {  
        this.loading = true;
        const paginacaoResultado = await disciplinaService.listarParaAvaliacaoHabilidade(
          this.idDisciplina,
          this.idAvaliacao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.disciplinasHabilidades = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.selected = [];
        this.avaliacoesHabilidades = [];
      }, 300);
    }
  }
};
</script>
