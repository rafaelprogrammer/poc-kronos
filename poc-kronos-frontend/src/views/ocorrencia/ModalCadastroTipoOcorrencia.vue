<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="abrir()" absolute dark fab top right rightclass="mb-2"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Descrição" v-model="tipoOcorrencia.descricao"
                  :error-messages="fieldErrors('tipoOcorrencia.descricao')" @blur="$v.tipoOcorrencia.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="tipoOcorrencia.codigo" disabled
                  label="Código" ></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="tipoOcorrencia.fator" :error-messages="fieldErrors('tipoOcorrencia.fator')"
                  @blur="$v.tipoOcorrencia.fator.$touch()" item-text="nome" item-value="valor" :items="fatores" label="Fator (P/N)"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="tipoOcorrencia.valor"  :error-messages="fieldErrors('tipoOcorrencia.valor')"
                  label="Valor"  @blur="$v.tipoOcorrencia.valor.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Inicio Vigência" :v-model="tipoOcorrencia.dataInicioVigencia" @date="tipoOcorrencia.dataInicioVigencia = $event" 
                :error-messages="fieldErrors('tipoOcorrencia.dataInicioVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final Vigência" :v-model="tipoOcorrencia.dataFinalVigencia"
                :error-messages="fieldErrors('tipoOcorrencia.dataFinalVigencia')" 
                @date="tipoOcorrencia.dataFinalVigencia = $event"
                ></date-picker-custom>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar">{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import ocorrenciaService from "@/service/ocorrencia/OcorrenciaService";
import TipoOcorrencia from "@/model/ocorrencia/TipoOcorrencia";
import EventBus from "@/utils/EventBus";
import {
  required,
  maxLength,
} from "vuelidate/lib/validators";
import { dataFimMenor, decimal_br, maxValue_br } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastroTipoOcorrencia",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    tipoOcorrencia: TipoOcorrencia.validations(
      required,
      maxLength,
      decimal_br,
      dataFimMenor,
      maxValue_br
    )
  },
  validationMessages: {
    tipoOcorrencia: TipoOcorrencia.validationMessages()
  },
  data() {
    return {
      dialog: false,
      formTitle: "",
      btnTitle: "",
      fatores: [],
      tipoOcorrencia: new TipoOcorrencia()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaTipoOcorrencia", async (idTipoOcorrencia) => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Tipo Ocorrência";
      this.btnTitle = "Alterar";
      this.tipoOcorrencia = await ocorrenciaService.editarTipoOcorrencia(idTipoOcorrencia);
      this.dialog = true;
    });
  },

  methods: {
    abrir() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Tipo Ocorrência";
      this.btnTitle = "Cadastrar";
      this.dialog = true;
    },
    salvar() {
      this.$v.tipoOcorrencia.$touch();
      if (this.$v.$invalid) {
        return;
      }
      ocorrenciaService.salvarTipoOcorrencia(this.tipoOcorrencia).then(() => {
        this.$emit("tipoOcorrenciaCadastrada", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.fatores = await ocorrenciaService.comboFatorTipoOcorrencia();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.tipoOcorrencia = new TipoOcorrencia();
        this.fatores = [];
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
