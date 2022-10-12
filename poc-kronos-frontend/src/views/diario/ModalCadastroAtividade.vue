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
              <v-flex xs12 sm6 md12>
                <v-select v-model="atividade.horasAtividades" disabled multiple :items="atividade.horasAtividades" attach chips label="Horas Atividades"
                @blur="$v.objetivo.idCampoExperiencia.$touch()"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Data Prevista" disabled v-model="atividade.dataPrevista"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Dia da Semana" disabled v-model="atividade.diaDaSemana"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Regime Letivo" disabled v-model="atividade.regimeLetivo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                  <date-picker-custom label="Data Realização" :disabled-date="somenteLeitura" :v-model="atividade.dataRealizacao" @date="atividade.dataRealizacao = $event" 
                  :error-messages="fieldErrors('atividade.dataRealizacao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Bimestre" disabled v-model="atividade.bimestre"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field color="primary" label="Professor" disabled v-model="atividade.professor"></v-text-field>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" v-if="!somenteLeitura" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import atividadeService from "@/service/atividade/AtividadeService";
import Atividade from "@/model/atividade/Atividade";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastroAtividade",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    atividade: Atividade.validations(required, dataFimMenor)
  },
  validationMessages: {
    atividade: Atividade.validationMessages()
  },
  data() {
    return {
      idDisciplina: null,
      somenteLeitura: false,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      atividade: new Atividade()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("visualizaAtividade", async (idAtividade) => {
      this.formTitle = "Visualizar Atividade";
      this.somenteLeitura = true;
      this.buscarAtividade(idAtividade);
    });
    EventBus.$on("editaAtividade", async (idAtividade, idDisciplina) => {
      this.formTitle = "Alterar Atividade";
      this.btnTitle = "Alterar";
      this.idDisciplina = idDisciplina;
      this.buscarAtividade(idAtividade);
    });
  },
  methods: {
    async buscarAtividade(idAtividade) {
      this.atividade = await atividadeService.editar(idAtividade);
      this.dialog = true;
    },
    salvar() {
      this.$v.atividade.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.atividade.idDisciplina = this.idDisciplina;
      atividadeService.salvar(this.atividade).then((dados) => {
        if (dados) {
          this.$emit("atividadeAlterada", true);
          this.dialog = false;
        }
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.atividade = new Atividade();
        EventBus.$emit("resetCustom");
        this.somenteLeitura = false;
      }, 300);
    }
  }
};
</script>
