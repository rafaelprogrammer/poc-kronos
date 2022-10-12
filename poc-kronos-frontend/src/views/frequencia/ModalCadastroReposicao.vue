<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="20%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                  <date-picker-custom label="Data Reposição" :v-model="frequencia.dataReposicao" @date="frequencia.dataReposicao = $event" 
                  :error-messages="fieldErrors('frequencia.dataReposicao')"></date-picker-custom>
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
import frequenciaService from "@/service/frequencia/FrequenciaService";
import Frequencia from "@/model/frequencia/Frequencia";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastroReposicao",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    frequencia: Frequencia.validations(required)
  },
  validationMessages: {
    frequencia: Frequencia.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      frequencia: new Frequencia()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("abrirReposicao", async (frequencia) => {
      this.formTitle = "Reposição";
      this.btnTitle = "Salvar";
      console.log(frequencia);
      this.frequencia = new Frequencia(frequencia);
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.$v.frequencia.$touch();
      if (this.$v.$invalid) {
        return;
      }
      frequenciaService.registrarReposicao(this.frequencia).then((dados) => {
        if (dados) {
          this.$emit("reposicaoRegistrada", true);
          this.dialog = false;
        }
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.frequencia = new Frequencia();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
