<template>
<div>
  <modal-cadastro-direito :desabilita-incluir="true" :niveis="niveis" />
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastra-disciplina-direito :id-disciplina="idDisciplina" :id-nivel="idNivel"  
      :id-curso="idCurso" :id-periodo="idPeriodo"
      @disciplinaDireitoCadastrada="cadastroEfetuado = $event" />
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
      <v-data-table :loading="loading" :headers="headers" :items="disciplinasDireitos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
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
import DisciplinaDireito from "@/model/disciplina/direito/DisciplinaDireito";
import ModalCadastraDisciplinaDireito from "./ModalCadastraDisciplinaDireito";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroDireito from "../estruturapedagogica/ModalCadastroDireito";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ListaDisciplinaDireito",
  props: {
    idDisciplina: Number,
    idNivel: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraDisciplinaDireito,
    ModalCadastroDireito,
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
      niveis: [],
      dialog: false,
      disciplinasDireitos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      disciplinaDireito: new DisciplinaDireito(),
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
    async finalizar(disciplinaDireito) {
      disciplinaDireito.dataFinalVigencia = this.dataFinalVigencia;
      if (this.validarDataFinalVigencia(disciplinaDireito)) {
        disciplinaDireito.dataFinalVigencia = null;
        this.dataFinalInvalida = true;
        return;
      } 
      disciplinaService.salvarDisciplinaDireito(disciplinaDireito).then(() => {
        this.dataFinalInvalida = false;
        this.atualizar();
      });
    },
    validarDataFinalVigencia(disciplinaDireito) {
      if (!DisciplinaDireito.validarDataFinalVigencia(disciplinaDireito.dataInicioVigencia, disciplinaDireito.dataFinalVigencia)) {
        return false;
      }
      return true;
    },
    async visualizar(disciplinaDireito) {
      this.niveis = await baseCurricularService.comboNiveis();
      EventBus.$emit("visualizaDireito", disciplinaDireito.idDireito);
    },
    excluir(disciplinaDireito) {
      confirm("Você deseja realmente excluir Disciplina Direito?") &&
        disciplinaService.excluirDireitoDisciplina(disciplinaDireito).then(() => {
          this.disciplinasDireitos.splice(
            this.disciplinasDireitos.indexOf(disciplinaDireito),
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
      this.disciplinaDireito.idDisciplina = this.idDisciplina;
      const paginacaoResultado = await disciplinaService.listarTodasDisciplinasDireitos(
        this.disciplinaDireito,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.disciplinasDireitos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
