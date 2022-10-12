<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Configuração Ponto" @click="abrirCadastro()" title="Incluir Configuração Ponto"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <date-picker-custom label="Data Inicio Vigência" :v-model="configuracaoPonto.dataInicialVigencia" @date="configuracaoPonto.dataInicialVigencia = $event" 
                :error-messages="fieldErrors('configuracaoPonto.dataInicialVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <date-picker-custom label="Data Final Vigência" :v-model="configuracaoPonto.dataFinalVigencia" @date="configuracaoPonto.dataFinalVigencia = $event" 
                :error-messages="fieldErrors('configuracaoPonto.dataFinalVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Inicial P1" :v-model="configuracaoPonto.horaInicialP1" @time="configuracaoPonto.horaInicialP1 = $event" 
                />
              </v-flex>
               <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Final P1" :v-model="configuracaoPonto.horaFinalP1" @time="configuracaoPonto.horaFinalP1 = $event" 
                :error-messages="fieldErrors('configuracaoPonto.horaFinalP1')"/>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Inicial P2" :v-model="configuracaoPonto.horaInicialP2" @time="configuracaoPonto.horaInicialP2 = $event" 
                :error-messages="fieldErrors('configuracaoPonto.horaInicialP2')"
                />
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Final P2" :v-model="configuracaoPonto.horaFinalP2" @time="configuracaoPonto.horaFinalP2 = $event" 
                :error-messages="fieldErrors('configuracaoPonto.horaFinalP2')"/>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Inicial P3" :v-model="configuracaoPonto.horaInicialP3" @time="configuracaoPonto.horaInicialP3 = $event" 
                :error-messages="fieldErrors('configuracaoPonto.horaInicialP3')"
                />
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Final P3" :v-model="configuracaoPonto.horaFinalP3" @time="configuracaoPonto.horaFinalP3 = $event" 
                 :error-messages="fieldErrors('configuracaoPonto.horaFinalP3')"/>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-checkbox color="primary" label="Tarefa Online" v-model="configuracaoPonto.tarefaOnline"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field label="CH Semana" v-model="configuracaoPonto.cargaHorariaSemanal" :error-messages="fieldErrors('configuracaoPonto.cargaHorariaSemanal')"></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Tolerância" :v-model="configuracaoPonto.tolerancia" @time="configuracaoPonto.tolerancia = $event" 
                 :error-messages="fieldErrors('configuracaoPonto.tolerancia')"/>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import ConfiguracaoPonto from "@/model/funcionario/ConfiguracaoPonto";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, numeric } from "vuelidate/lib/validators";
import { dataFimMenor, horaFinalMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import TimePickerCustom from "@/components/Custom/TimePickerCustom";

export default {
  name: "ModalCadastroConfiguracaoPonto",
  props: {
    idFuncionario: Number
  },
  components: {
    DatePickerCustom,
    TimePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    configuracaoPonto: ConfiguracaoPonto.validations(
      required,
      numeric,
      dataFimMenor,
      horaFinalMenor
    )
  },
  validationMessages: {
    configuracaoPonto: ConfiguracaoPonto.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      configuracaoPonto: new ConfiguracaoPonto()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaConfiguracaoPonto", async idConfiguracaoPonto => {
      this.formTitle = "Alterar Configuração Ponto";
      this.btnTitle = "Alterar";
      this.configuracaoPonto = await funcionarioService.editarConfiguracaoPonto(
        idConfiguracaoPonto
      );
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.formTitle = "Cadastrar Configuração Ponto";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.configuracaoPonto.idFuncionario = this.idFuncionario;
      this.$v.configuracaoPonto.$touch();
      if (this.$v.$invalid) {
        return;
      }
      funcionarioService
        .salvarConfiguracaoPonto(this.configuracaoPonto)
        .then((dados) => {
          if (dados) {
            this.$emit("configuracaoPontoCadastrada", true);
            this.dialog = false;
          }
        });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.configuracaoPonto = new ConfiguracaoPonto();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
