<template>
  <v-layout row justify-center>
    <modal-seleciona-empresa></modal-seleciona-empresa>
    <v-dialog v-model="dialog" fullscreen hide-overlay persistent width="80%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}} - Número: {{contrato.numero}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <lista-creditos :contrato="contrato"
                :creditos-contratos="contrato.creditosContratos"/>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <lista-convenios-contratos :contrato="contrato"
                :visualiza-acoes="visualizaAcoes"
                @conveniosContratosSelecionados="contrato.conveniosContratos = $event"/>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-subheader inset>Dados Contratos</v-subheader>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.numero" label="Número"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.ano" label="Ano"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.data" label="Data"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.nomePeriodo" label="Período"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.percentualMultaAtraso" label="Multa Atraso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.percentualJurosAtraso" label="Juros Atraso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="contrato.percentualBomPagador" label="Bom Pagador"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.nomeTipoComposicaoValor" label="Composição Valor"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-btn color="primary" v-show="visualizaAcoes" dark alt="Atualizar" @click="atualizar()" title="Atualizar"><v-icon>settings</v-icon>Atualizar</v-btn>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select :disabled="!visualizaAcoes" v-model="contrato.idResponsavelFinanceiro" :error-messages="fieldErrors('contrato.idResponsavelFinanceiro')"
                  @blur="$v.contrato.idResponsavelFinanceiro.$touch()"
                  item-text="nomePessoaResponsavel" item-value="id" :items="responsaveisFinanceiros" 
                  label="Responsável Financeiro" @change="buscarCPF(contrato.idResponsavelFinanceiro)"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="cpfResponsavel" label="CPF"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select :disabled="!visualizaAcoes" v-model="contrato.idTipoFormaPagamento" :error-messages="fieldErrors('contrato.idTipoFormaPagamento')"
                  @blur="$v.contrato.idTipoFormaPagamento.$touch()"
                  item-text="nome" item-value="id" :items="tiposFormasPagamento" 
                  label="Forma Pagamento"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :disabled="!visualizaAcoes" v-model="contrato.numeroParcelas" :error-messages="fieldErrors('contrato.numeroParcelas')"
                  @blur="$v.contrato.numeroParcelas.$touch()" @change="calcularDiaVencimento()" :items="numerosParcelas" 
                  label="Nr. Parcela"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom :disabled-date="!visualizaAcoes" label="Data Primeira Parcela" :v-model="contrato.dataVencimentoPrimeiraParcela" 
                @date="atualizarDataPrimeiraParcela($event)"
                :error-messages="fieldErrors('contrato.dataVencimentoPrimeiraParcela')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.diaVencimentoParcela" label="Dia Vencimento"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.dataVencimentoUltimaParcela" label="Data Última Parcela"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.nomeTipoSituacaoContrato" label="Situação"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field disabled v-model="contrato.valorTotalFinal" label="Valor Total"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field  v-model="empresa.nomeFantasia" disabled :error-messages="fieldErrors('contrato.idEmpresaOrigem')"
                  label="Instituição de Origem"  @blur="$v.contrato.idEmpresaOrigem.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-btn color="primary" v-show="visualizaAcoes" fab small dark alt="Selecionar Instituição de Origem" @click="abrirSelecionaEmpresa()" title="Selecionar Instituição de Origem"><v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-textarea :disabled="!visualizaAcoes" rows="5" cols="5" label="Observações" v-model="contrato.observacao"></v-textarea>
               </v-flex>
               <v-flex xs12 sm6 md12>
                <gera-parcelas :contrato="contrato" :visualiza-acoes="visualizaAcoes"
                @parcelasGeradas="contrato.parcelas = $event"/>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" v-show="visualizaAcoes" type="submit" @click.native="confirmar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import empresaService from "@/service/empresa/EmpresaService";
import pessoaService from "@/service/pessoa/PessoaService";
import matriculaService from "@/service/matricula/MatriculaService";
import contratoService from "@/service/contrato/ContratoService";
import responsavelService from "@/service/pessoa/ResponsavelService";
import Contrato from "@/model/contrato/Contrato";
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
import ListaCreditos from "./ListaCreditos";
import ListaConveniosContratos from "./ListaConveniosContratos";
import GeraParcelas from "./GeraParcelas";
import ModalSelecionaEmpresa from "../empresa/ModalSelecionaEmpresa";
import Empresa from "@/model/empresa/Empresa";
export default {
  name: "ModalAnaliseContrato",
  components: {
    DatePickerCustom,
    ModalSelecionaEmpresa,
    ListaCreditos,
    ListaConveniosContratos,
    GeraParcelas
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    contrato: Contrato.validations(
      required,
      maxLength,
      numeric,
      decimal,
      dataFimMenor
    )
  },
  validationMessages: {
    contrato: Contrato.validationMessages()
  },
  data() {
    return {
      visualizaAcoes: true,
      numerosParcelas: [3,6,9,12],
      responsaveisFinanceiros: [],
      tiposFormasPagamento: [],
      cpfResponsavel: null,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      contrato: new Contrato(),
      empresa: {},
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("selecionaEmpresa", dados => {
      this.empresa = new Empresa(dados);
      this.contrato.idEmpresaOrigem = this.empresa.id;
    });
    EventBus.$on("analiseContrato", async (idContrato, visualizaAcoes) => {
      this.visualizaAcoes = visualizaAcoes;
      this.formTitle = "Análise Contrato";
      this.btnTitle = "Confirmar Análise";
      this.contrato = await contratoService.editar(idContrato);
      this.empresa.nomeFantasia = this.contrato.nomeEmpresaOrigem;
      this.carregarDadosBasicos();
      this.buscarCPF(this.contrato.idResponsavelFinanceiro);
      this.dialog = true;
    });
  },
  methods: {
    abrirSelecionaEmpresa() {
      EventBus.$emit("abrirSelecionaEmpresa");
    },
    atualizarDataPrimeiraParcela(data) {
      this.contrato.dataVencimentoPrimeiraParcela = data;
      this.calcularDiaVencimento();
    },
    async calcularDiaVencimento() {
      if (this.contrato.numeroParcelas && this.contrato.dataVencimentoPrimeiraParcela) {
        const dados = await contratoService.cacularDiaVencimento(
          this.contrato.numeroParcelas,
          this.contrato.dataVencimentoPrimeiraParcela
        );
        this.contrato.diaVencimentoParcela = dados.diaVencimento;
        this.contrato.dataVencimentoUltimaParcela = dados.dataUltimaParcela;
      }
    },
    async buscarCPF(idResponsavelFinanceiro) {
      setTimeout(() => {
        this.responsaveisFinanceiros.map(async resp => {
          if (resp.id === idResponsavelFinanceiro) {
            const pessoa = await pessoaService.editar(resp.idPessoa);
            this.cpfResponsavel = pessoa.cpf;
          }
        });
      }, 500);
    },
    confirmar() {
      contratoService.finalizarAnalise(this.contrato).then(() => {
        this.$emit("analiseConfirmada", true);
        this.dialog = false;
      });
    },
    atualizar() {
      this.$v.contrato.$touch();
      if (this.$v.$invalid) {
        return;
      }
      contratoService.atualizarAnalise(this.contrato).then(() => {
        setTimeout(async () => {
          this.contrato = await contratoService.editar(this.contrato.id);
        }, 500);
      });
    },
    async carregarDadosBasicos() {
      this.responsaveisFinanceiros = await responsavelService.responsaveisFinanceiros(this.contrato.idMatricula);
      this.tiposFormasPagamento = await contratoService.tiposFormasPagamento();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.contrato = new Contrato();
      }, 300);
    }
  }
};
</script>
