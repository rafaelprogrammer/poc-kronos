<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn slot="activator" color="primary" @click="abrir()"><v-icon>add</v-icon>Adicionar</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Objetivo</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
                <v-layout align-center wrap fill-height>
                  <v-flex xs12 sm6 md3>
                    <date-picker-custom label="Data Início Vigência" :v-model="disciplinaObjetivo.dataInicioVigencia" @date="disciplinaObjetivo.dataInicioVigencia = $event" 
                    ></date-picker-custom>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                      <v-select v-model="disciplinaObjetivo.idSubFase" item-text="sigla" item-value="id" :items="subFases"
                      label="Bimestre">
                      </v-select>
                  </v-flex>
                </v-layout>
              <v-data-table v-model="selected" select-all :loading="loading" :headers="headers" :items="objetivos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
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
            <v-btn color="primary" type="submit" v-if="disciplinaObjetivo.dataInicioVigencia 
            && (this.selected && this.selected.length > 0) " @click.native="salvar" dark>Selecionar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import subFaseService from "@/service/curso/SubFaseService";
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import Objetivo from "@/model/basecurricular/Objetivo";
import DisciplinaObjetivo from "@/model/disciplina/objetivo/DisciplinaObjetivo";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastraDisciplinaObjetivo",
  props: {
    idDisciplina: Number,
    idFaixaNivel: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    DatePickerCustom
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      dialog: false,
      subFases: [],
      disciplinaObjetivo: new DisciplinaObjetivo(),
      objetivo: new Objetivo(),
      objetivos: [],
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
      this.carregarDadosBasicos();
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    salvar() {
      this.disciplinaObjetivo.idsObjetivos = this.selected.map(s => s.id);
      this.disciplinaObjetivo.idDisciplina = this.idDisciplina;
      disciplinaService.salvarDisciplinaObjetivo(this.disciplinaObjetivo).then(() => {
        this.$emit("disciplinaObjetivoCadastrada", true);
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
        const paginacaoResultado = await baseCurricularService.listarObjetivosParaInclusaoDisciplinasObjetivos(
          this.idDisciplina,
          this.idFaixaNivel,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.objetivos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.disciplinaObjetivo = new DisciplinaObjetivo();
        this.liberaPesquisa = false;
        this.selected = [];
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
