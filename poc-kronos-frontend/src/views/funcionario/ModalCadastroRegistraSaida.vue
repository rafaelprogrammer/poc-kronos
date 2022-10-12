<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field label="Período"   v-model="ponto.nomeTipoPeriodoPonto" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Início Prevista"   v-model="ponto.horaInicialPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Final Prevista"   v-model="ponto.horaFinalPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Início Informado" :v-model="ponto.horaInicialInformado" @time="ponto.horaInicialInformado = $event" :disabled-date="true"/>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Final Informado" :v-model="ponto.horaFinalInformado" @time="ponto.horaFinalInformado = $event"/>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar" :disabled="!ponto.horaFinalInformado">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Ponto from "@/model/funcionario/Ponto";
import EventBus from "@/utils/EventBus";
import TimePickerCustom from "@/components/Custom/TimePickerCustom";
export default {
  name: "ModalCadastroRegistraSaida",
  components: {
    TimePickerCustom
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
    EventBus.$on("editaRegistraSaida", async idPonto => {
      this.edicao = true;
      this.formTitle = "Registrar Saída";
      this.ponto = await funcionarioService.editarPonto(idPonto);
      this.recuperarHorasPrevistas(this.ponto.idTipoPeriodoPonto);
      this.carregarDadosBasicos();
      this.dialog = true;
    });
  },

  methods: {
    salvar() {
      funcionarioService.salvarPonto(this.ponto);
      this.$emit("pontoSaidaCadastrado", true);
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
        this.ponto = new Ponto();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
