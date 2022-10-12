<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" fullscreen hide-overlay persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir / Renovar Contratos" @click="abrirCadastro()" title="Incluir / Renovar Contratos"><v-icon>add</v-icon>Incluir / Renovar</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                  <v-text-field :disabled="true" v-model="contrato.numero"
                    label="NR Contrato"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-text-field :disabled="true" v-model="numeroRegistroPessoa"
                    label="NR Registro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-text-field :disabled="true" v-model="cpfPessoa"
                    label="CPF"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                  <v-text-field :disabled="true" v-model="nomePessoa"
                    label="Nome"></v-text-field>
              </v-flex>
            </v-layout>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md1>
                <v-text-field :disabled="anoDesabilitado" v-model="contrato.ano" :error-messages="fieldErrors('contrato.ano')"
                  label="Ano"  @blur="$v.contrato.ano.$touch()" @change="carregarPeriodoAtual(contrato.ano)"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom :disabled-date="dataDesabilitada" label="Data" :v-model="contrato.data" @date="contrato.data = $event" 
                :error-messages="fieldErrors('contrato.data')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :disabled="periodoAtualDesabilitado" v-model="contrato.idPeriodoExecucao" :error-messages="fieldErrors('contrato.idPeriodoExecucao')"
                  @blur="$v.contrato.idPeriodoExecucao.$touch()" @change="carregarTurma(contrato.idPeriodoExecucao)" item-text="sigla" item-value="id" :items="periodos" label="Período Atual"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :disabled="turmaDesabilitada" v-model="idTurma"
                  item-text="sigla" item-value="id" :items="turmas" label="Turma"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :disabled="turmaDesabilitada" v-model="contrato.idTipoCredito"
                  item-text="nome" item-value="id" :items="tiposCreditos" label="Crédito"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :disabled="turmaDesabilitada" v-model="contrato.idTipoPrograma"
                  item-text="nome" item-value="id" :items="tiposProgramas" label="Programa"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-alert :value="true" type="error" v-if="verificarErroCreditosObrigatorios()">
                  {{fieldErrorsText('contrato.creditosContratos')}}
                </v-alert>
                <gera-creditos :contrato="contrato" :id-turma="idTurma" :visualiza-acoes="visualizaAcoes"
                :creditos-contratos-salvos="contrato.creditosContratos"
                @creditosGerados="contrato.creditosContratos = $event" />
              </v-flex>
              <v-flex xs12 sm6 md12>
                <gera-creditos-pendentes :tipos-creditos-pendentes="tiposCreditos"
                  :visualiza-acoes="visualizaAcoes"
                  :tipos-programas-pendentes="tiposProgramas" :ano="contrato.ano"
                  :numero="numeroPeriodoExecucao" :id-matricula="idMatricula"
                  :id-curso="idCurso" />
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" v-if="visualizaAcoes" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import turmaService from "@/service/turma/TurmaService";
import matriculaService from "@/service/matricula/MatriculaService";
import contratoService from "@/service/contrato/ContratoService";
// import turmaService from "@/service/turma/TurmaService";
import periodoService from "@/service/curso/PeriodoService";
import ContratoMatricula from "@/model/contrato/ContratoMatricula";
import {
  required,
  maxLength,
  numeric,
  decimal
} from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import GeraCreditos from "./GeraCreditos";
import GeraCreditosPendentes from "./GeraCreditosPendentes";
export default {
  name: "ModalCadastroContrato",
  components: {
    DatePickerCustom,
    GeraCreditos,
    GeraCreditosPendentes
  },
  props: {
    idMatricula: Number,
    idCurso: Number,
    numeroRegistroPessoa: Number,
    cpfPessoa: String,
    nomePessoa: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    contrato: ContratoMatricula.validations(
      required,
      maxLength,
      numeric,
      decimal,
      dataFimMenor
    )
  },
  validationMessages: {
    contrato: ContratoMatricula.validationMessages()
  },
  data() {
    return {
      anoDesabilitado: false,
      dataDesabilitada: false,
      periodoAtualDesabilitado: true,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposCreditos: [],
      tiposProgramas: [],
      turmas: [],
      periodos: [],
      turmaDesabilitada: true,
      idTurma: 0,
      contrato: new ContratoMatricula(),
      numeroPeriodoExecucao: null,
      visualizaAcoes: true
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaContrato", async idContrato => {
      this.formTitle = "Visualizar Contrato";
      this.visualizaAcoes = false;
      this.editarContrato(idContrato);
    });
    EventBus.$on("incluiCreditoPendente", async credito => {
      this.contrato.creditosContratos.push(credito);
    });
    EventBus.$on("editaContrato", async idContrato => {
      this.formTitle = "Renovar Contrato";
      this.btnTitle = "Alterar";
      this.editarContrato(idContrato);
    });
  },
  methods: {
    async editarContrato(idContrato) {
      this.carregarDadosBasicos();
      this.periodoAtualDesabilitado = true;
      this.turmaDesabilitada = true;
      this.anoDesabilitado = true;
      this.dataDesabilitada = true;
      this.contrato = await contratoService.editarContratoMatricula(idContrato);
      this.editarCamposCreditos(this.contrato.creditosContratos);
        this.carregarPeriodoAtual(this.contrato.ano).then(() => {
          this.carregarTurma(this.contrato.idPeriodoExecucao);
        });
        this.dialog = true;
    },
    editarCamposCreditos(creditosContratos) {
      if (creditosContratos && creditosContratos.length > 0) {
        this.contrato.idTipoCredito = creditosContratos[0].idTipoCredito;
        this.contrato.idTipoPrograma = creditosContratos[0].idTipoPrograma;
        this.contrato.idTurma = creditosContratos[0].idTurma;
        this.idTurma = creditosContratos[0].idTurma;
      }
    },
    verificarErroCreditosObrigatorios() {
      if (this.$v.contrato.creditosContratos.$error) {
        return true;
      } else {
        return false;
      }
    },
    async carregarTurma(idPeriodoExecuca) {
      this.turmas = await turmaService.combo(idPeriodoExecuca);
      if (this.contrato.id === null && this.turmas && this.turmas.length > 0) {
        this.turmaDesabilitada = false;
      } else {
        this.turmaDesabilitada = true;
      }
      this.buscarNumeroPeriodoExecucao();
    },
    async carregarPeriodoAtual(ano) {
      this.periodos = await periodoService.comboPeriodoContrato(this.idCurso, ano, this.idMatricula);
      if (this.contrato.id === null && this.periodos && this.periodos.length > 0) {
        this.periodoAtualDesabilitado = false;
      } else {
        this.periodoAtualDesabilitado = true;
      }
    },
    buscarNumeroPeriodoExecucao() {
      this.periodos.map(p => {
        if (p.id = this.contrato.idPeriodoExecucao) {
          this.numeroPeriodoExecucao = p.numero;
        }
      });
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIds();
      this.formTitle = "Incluir Contrato";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.contrato.$touch();
      if (this.$v.$invalid) {
        return;
      }
      contratoService.salvar(this.contrato).then(() => {
        this.$emit("contratoCadastrado", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.tiposCreditos = await matriculaService.tiposCreditos();
      this.tiposProgramas = await matriculaService.tiposProgramas();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.contrato = new ContratoMatricula();
        this.periodoAtualDesabilitado = true;
        this.turmaDesabilitada = true;
        this.anoDesabilitado = false;
        this.dataDesabilitada = false;
        this.idTurma = null;
        this.turmas = [];
        this.periodos = [];
        EventBus.$emit("limpaCreditos");
        EventBus.$emit("resetCustom");
      }, 300);
    },
    setaIds() {
      this.contrato.idMatricula = this.idMatricula;
      this.contrato.idInstituicao = this.$store.getters.user.idInstituicao;
    }
  }
};
</script>
