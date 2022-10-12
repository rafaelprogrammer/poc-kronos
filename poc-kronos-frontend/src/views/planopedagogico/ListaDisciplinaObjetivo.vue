<template>
<div>
  <modal-cadastro-objetivo :desabilita-incluir="true" :faixas-anos="faixasAnos" />
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastra-disciplina-objetivo :id-disciplina="idDisciplina" :id-faixa-ano="idFaixaAno"  
      :id-curso="idCurso" :id-periodo="idPeriodo"
      @disciplinaObjetivoCadastrada="cadastroEfetuado = $event" />
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
      <v-data-table :loading="loading" :headers="headers" :items="disciplinasObjetivos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
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
import DisciplinaObjetivo from "@/model/disciplina/objetivo/DisciplinaObjetivo";
import ModalCadastraDisciplinaObjetivo from "./ModalCadastraDisciplinaObjetivo";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroObjetivo from "../estruturapedagogica/ModalCadastroObjetivo";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ListaDisciplinaObjetivo",
  props: {
    idDisciplina: Number,
    idFaixaAno: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraDisciplinaObjetivo,
    ModalCadastroObjetivo,
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
      faixasAnos: [],
      disciplinasObjetivos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      disciplinaObjetivo: new DisciplinaObjetivo(),
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
    async finalizar(disciplinaObjetivo) {
      disciplinaObjetivo.dataFinalVigencia = this.dataFinalVigencia;
      if (this.validarDataFinalVigencia(disciplinaObjetivo)) {
        disciplinaObjetivo.dataFinalVigencia = null;
        this.dataFinalInvalida = true;
        return;
      } 
      disciplinaService.salvarDisciplinaObjetivo(disciplinaObjetivo).then(() => {
        this.dataFinalInvalida = false;
        this.atualizar();
      });
    },
    validarDataFinalVigencia(disciplinaObjetivo) {
      if (!DisciplinaObjetivo.validarDataFinalVigencia(disciplinaObjetivo.dataInicioVigencia, disciplinaObjetivo.dataFinalVigencia)) {
        return false;
      }
      return true;
    },
    async visualizar(disciplinaObjetivo) {
      this.faixasAnos = await baseCurricularService.faixasAnos();
      EventBus.$emit("visualizaObjetivo", disciplinaObjetivo.idObjetivo);
    },
    excluir(disciplinaObjetivo) {
      confirm("Você deseja realmente excluir Disciplina Objetivo?") &&
        disciplinaService.excluirDisciplinaObjetivo(disciplinaObjetivo).then(() => {
          this.disciplinasObjetivos.splice(
            this.disciplinasObjetivos.indexOf(disciplinaObjetivo),
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
      this.disciplinaObjetivo.idDisciplina = this.idDisciplina;
      const paginacaoResultado = await disciplinaService.listarTodasDisciplinasObjetivos(
        this.disciplinaObjetivo,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.disciplinasObjetivos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
