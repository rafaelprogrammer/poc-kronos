<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark right rightclass="mb-3" alt="Registrar Entrada" @click="abrirCadastro()" title="Registrar Entrada"><v-icon>add</v-icon> Registrar Entrada</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-select v-model="ponto.idTipoPeriodoPonto" :disabled="edicao" :error-messages="fieldErrors('ponto.idTipoPeriodoPonto')"
                  @blur="$v.ponto.idTipoPeriodoPonto.$touch()" @change="recuperarHorasPrevistas(ponto.idTipoPeriodoPonto)" item-text="nome" item-value="id" :items="tiposPeriodosPonto" label="Período"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Início Prevista"   v-model="ponto.horaInicialPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Final Prevista"   v-model="ponto.horaFinalPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md4>
                <time-picker-custom label="Hora Início Informado" :v-model="ponto.horaInicialInformado" @time="ponto.horaInicialInformado = $event" 
                 :error-messages="fieldErrors('ponto.horaInicialInformado')"/>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar" :disabled="!ponto.horaInicialInformado">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Ponto from "@/model/funcionario/Ponto";
import EventBus from "@/utils/EventBus";
import { required, maxLength } from "vuelidate/lib/validators";
import { dataFimMenor, horaFinalMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import TimePickerCustom from "@/components/Custom/TimePickerCustom";
export default {
  name: "ModalCadastroRegistraEntrada",
  props: {
    registraEntrada: Boolean
  },
  components: {
    TimePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    ponto: Ponto.validations(required, maxLength, dataFimMenor, horaFinalMenor)
  },
  validationMessages: {
    ponto: Ponto.validationMessages()
  },
  data() {
    return {
      edicao: false,
      dialog: false,
      formTitle: "",
      tiposPeriodosPonto: [],
      ponto: new Ponto()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaRegistraEntrada", async idPonto => {
      this.carregarDadosBasicos();
      this.edicao = true;
      this.formTitle = "Registrar Entrada";
      this.ponto = await funcionarioService.editarPonto(idPonto);
      this.recuperarHorasPrevistas(this.ponto.idTipoPeriodoPonto);
      this.dialog = true;
    });
  },

  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Ponto";
      this.btnTitle = "Salvar";
      this.selecionaPessoa = true;
      this.dialog = true;
    },
    salvar() {
      this.$v.ponto.$touch();
      if (this.$v.$invalid) {
        return;
      }
      funcionarioService.salvarPonto(this.ponto);
      this.$emit("pontoCadastrado", true);
      this.dialog = false;
    },
    async recuperarHorasPrevistas(idTipoPeriodoPonto) {
      const ponto = await funcionarioService.horasPrevistasPonto(
        idTipoPeriodoPonto
      );
      this.ponto.horaInicialPrevista = ponto.horaInicialPrevista;
      this.ponto.horaFinalPrevista = ponto.horaFinalPrevista;
    },
    async carregarDadosBasicos() {
      this.tiposPeriodosPonto = await funcionarioService.tiposPeriodosPontos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.ponto = new Ponto();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
