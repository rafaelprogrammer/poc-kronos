<template>
<div>
  <modal-cadastro-competencia :desabilita-incluir="true" :niveis="niveis" />
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastra-disciplina-competencia :id-disciplina="idDisciplina" :id-nivel="idNivel"
      :id-componente="idComponente" :id-curso="idCurso" :id-periodo="idPeriodo"
      @disciplinaCompetenciaCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm6 md3>
        <date-picker-custom label="Data Final Vigência" :v-model="dataFinalVigencia" @date="dataFinalVigencia = $event" 
        ></date-picker-custom>
    </v-flex>
    <v-flex xs12 sm6 md5>
      <v-alert :value="true" type="error" v-if="dataFinalInvalida">
       {{mensagemErroDataFinalMenor}}
      </v-alert>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="disciplinasCompetencias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigo }}</td>
          <td>{{ props.item.bncc | booleanFilter }}</td>
          <td>{{ props.item.geral | booleanFilter }}</td>
          <td>{{ props.item.siglaSubFase }}</td>
          <td width="50%">
            <v-tooltip bottom>
                <span slot="activator" class="">{{ props.item.descricao | limitCaracteres(100) }}</span>
                <span>{{ props.item.descricao }}</span>
            </v-tooltip>
          </td>
          <td>{{ props.item.ativo | booleanFilter }}</td>
          <td>{{ props.item.dataInicioVigencia }}</td>
          <td>{{ props.item.dataFinalVigencia }}</td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" title="Opções para direitos" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Analisar" @click="excluir(props.item)">
                  <v-list-tile-title>Excluir</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Finalizar Vigência" v-if="dataFinalVigencia"
                  @click="finalizar(props.item)">
                  <v-list-tile-title>Finalizar Vigência</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Visualizar" @click="visualizar(props.item)">
                  <v-list-tile-title>Visualizar</v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu>
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
import disciplinaService from "@/service/disciplina/DisciplinaService";
import DisciplinaCompetencia from "@/model/disciplina/competencia/DisciplinaCompetencia";
import ModalCadastraDisciplinaCompetencia from "./ModalCadastraDisciplinaCompetencia";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroCompetencia from "../estruturapedagogica/ModalCadastroCompetencia";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ListaDisciplinaCompetencia",
  props: {
    idDisciplina: Number,
    idNivel: Number,
    idComponente: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraDisciplinaCompetencia,
    ModalCadastroCompetencia,
    DatePickerCustom
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      mensagemErroDataFinalMenor: "Data Final de Vigência não pode ser menor que a Data Início de Vigência",
      dataFinalInvalida: false,
      dataFinalVigencia: null,
      dialog: false,
      niveis: [],
      disciplinasCompetencias: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      disciplinaCompetencia: new DisciplinaCompetencia(),
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
          text: "Geral",
          align: "left",
          sortable: false,
          value: "geral"
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
        {
          text: "Data Inicial",
          align: "left",
          sortable: false,
          value: "ativo"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "ativo"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    idDisciplina() {
      this.atualizar();
    },
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
    async finalizar(disciplinaCompetencia) {
      disciplinaCompetencia.dataFinalVigencia = this.dataFinalVigencia;
      if (this.validarDataFinalVigencia(disciplinaCompetencia)) {
        disciplinaCompetencia.dataFinalVigencia = null;
        this.dataFinalInvalida = true;
        return;
      } 
      disciplinaService.salvarDisciplinaCompetencia(disciplinaCompetencia).then(() => {
        this.dataFinalInvalida = false;
        this.atualizar();
      });
    },
    validarDataFinalVigencia(disciplinaCompetencia) {
      if (!DisciplinaCompetencia.validarDataFinalVigencia(disciplinaCompetencia.dataInicioVigencia, disciplinaCompetencia.dataFinalVigencia)) {
        return false;
      }
      return true;
    },
    async visualizar(disciplinaCompetencia) {
      this.niveis = await baseCurricularService.comboNiveis();
      EventBus.$emit("visualizaCompetencia", disciplinaCompetencia.idCompetencia);
    },
    excluir(disciplinaCompetencia) {
      confirm("Você deseja realmente excluir Disciplina Competência?") &&
        disciplinaService.excluirDisciplinaCompetencia(disciplinaCompetencia).then(() => {
          this.disciplinasCompetencias.splice(
            this.disciplinasCompetencias.indexOf(disciplinaCompetencia),
            1
          );
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      this.disciplinaCompetencia.idDisciplina = this.idDisciplina;
      const paginacaoResultado = await disciplinaService.listarTodasDisciplinasCompetencias(
        this.disciplinaCompetencia,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.disciplinasCompetencias = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
