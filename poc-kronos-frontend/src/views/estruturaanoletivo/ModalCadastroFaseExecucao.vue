<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md5>
                <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field color="primary" label="Ano" disabled v-model="ano"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Período" disabled v-model="siglaPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Tipo Período" disabled v-model="faseExecucao.tipoPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Fase" disabled v-model="faseExecucao.siglaFase"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Fase" disabled v-model="faseExecucao.numeroFase"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                  <date-picker-custom label="Data Inicial" :v-model="faseExecucao.dataInicio" @date="faseExecucao.dataInicio = $event" 
                  :error-messages="fieldErrors('faseExecucao.dataInicio')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                  <date-picker-custom label="Data Fim" :v-model="faseExecucao.dataFim" @date="faseExecucao.dataFim = $event" 
                  :error-messages="fieldErrors('faseExecucao.dataFim')"></date-picker-custom>
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
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import FaseExecucao from "@/model/periodoexecucao/FaseExecucao";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastroFaseExecucao",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    faseExecucao: FaseExecucao.validations(required, dataFimMenor)
  },
  validationMessages: {
    faseExecucao: FaseExecucao.validationMessages()
  },
  data() {
    return {
      idCurso: null,
      nomeCurso: "",
      ano: null,
      siglaPeriodo: null,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      faseExecucao: new FaseExecucao()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaFaseExecucao", async (idFaseExecucao, nomeCurso, ano, siglaPeriodo) => {
      this.formTitle = "Alterar Fase Execução (Semestre)";
      this.btnTitle = "Alterar";
      this.nomeCurso = nomeCurso;
      this.ano = ano;
      this.siglaPeriodo = siglaPeriodo;
      this.faseExecucao = await periodoExecucaoService.editarFase(idFaseExecucao);
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.$v.faseExecucao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      periodoExecucaoService.alterarFasesExecucao(this.faseExecucao).then((dados) => {
        if (dados) {
          this.$emit("faseExecucaoAlterada", true);
          this.dialog = false;
        }
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.faseExecucao = new FaseExecucao();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
