<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark right rightclass="mb-3" alt="Registrar Tarefa Online" @click="abrirCadastro()" title="Registrar Tarefa Online"><v-icon>add</v-icon> Registrar Tarefa Online</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Descrição" v-model="ponto.descricao"
                  :error-messages="fieldErrors('ponto.descricao')" @blur="$v.ponto.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Início Informado" :v-model="ponto.horaInicialInformado" @time="ponto.horaInicialInformado = $event" 
                 :error-messages="fieldErrors('ponto.horaInicialInformado')"/>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <time-picker-custom label="Hora Final Informado" :v-model="ponto.horaFinalInformado" @time="ponto.horaFinalInformado = $event" 
                 :error-messages="fieldErrors('ponto.horaFinalInformado')"/>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar" :disabled="!ponto.horaInicialInformado || !ponto.horaFinalInformado || !ponto.descricao">Salvar</v-btn>
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
  name: "ModalCadastroTarefaOnline",
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
  methods: {
    abrirCadastro() {
      this.formTitle = "Registrar Tarefa Online";
      this.btnTitle = "Salvar";
      this.selecionaPessoa = true;
      this.dialog = true;
    },
    salvar() {
      funcionarioService.salvarTarefaOnline(this.ponto);
      this.$emit("tarefaOnlineCadastrada", true);
      this.dialog = false;
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
