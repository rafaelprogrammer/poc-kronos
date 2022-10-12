<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" absolute dark fab top right rightclass="mb-2" alt="Incluir Calendário" @click="abrirCadastro()" title="Incluir Disciplina"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 m4>
                <v-text-field color="primary" disabled label="Instituição" v-model="this.$store.getters.user.instituicao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" label="Ano" v-model="calendario.ano"
                :error-messages="fieldErrors('calendario.ano')" @blur="$v.calendario.ano.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md4>
                <v-text-field color="primary" disabled label="Número" v-model="calendario.numero"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Descrição" v-model="calendario.descricao"
                  :error-messages="fieldErrors('calendario.descricao')" @blur="$v.calendario.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Início" :v-model="calendario.dataInicio" @date="calendario.dataInicio = $event" 
                :error-messages="fieldErrors('calendario.dataInicio')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Início Ano Letivo" :v-model="calendario.dataInicioAnoLetivo" @date="calendario.dataInicioAnoLetivo = $event" 
                :error-messages="fieldErrors('calendario.dataInicioAnoLetivo')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Início Atribuição Crédito" :v-model="calendario.dataInicioAtribuicaoCredito" @date="calendario.dataInicioAtribuicaoCredito = $event" 
                :error-messages="fieldErrors('calendario.dataInicioAtribuicaoCredito')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final Atribuição Crédito" :v-model="calendario.dataFinalAtribuicaoCredito" @date="calendario.dataFinalAtribuicaoCredito = $event" 
                :error-messages="fieldErrors('calendario.dataFinalAtribuicaoCredito')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final Ano Letivo" :v-model="calendario.dataFinalAnoLetivo" @date="calendario.dataFinalAnoLetivo = $event" 
                :error-messages="fieldErrors('calendario.dataFinalAnoLetivo')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final" :v-model="calendario.dataFinal" @date="calendario.dataFinal = $event" 
                :error-messages="fieldErrors('calendario.dataFinal')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="calendario.idsCursos" item-text="nome" 
                item-value="id" :items="cursos" attach chips label="Cursos"
                :error-messages="fieldErrors('calendario.idsCursos')" @blur="$v.calendario.idsCursos.$touch()" multiple></v-select>
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
import cursoService from "@/service/curso/CursoService";
import calendarioService from "@/service/calendario/CalendarioService";
import Calendario from "@/model/calendario/Calendario";
import { required, numeric, maxLength } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
export default {
  name: "ModalCadastroCalendario",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    calendario: Calendario.validations(required, numeric, maxLength, dataFimMenor)
  },
  validationMessages: {
    calendario: Calendario.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      editaCalendario: false,
      cursos: [],
      // tiposSituacoesCalendarios: [],
      tiposResultados: [],
      calendario: new Calendario()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaCalendario", async idCalendario => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Calendário";
      this.btnTitle = "Alterar";
      this.calendario = await calendarioService.editar(idCalendario);
      this.editaCalendario = true;
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Calendário";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.calendario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      calendarioService.salvar(this.calendario).then((dados) => {
        if(dados) {
          this.dialog = false;
          if (this.editaCalendario) {
            EventBus.$emit("selecionaCalendario", this.calendario);
          }
        }
      });
    },
    async carregarDadosBasicos() {
      // this.tiposSituacoesCalendarios = await matriculaService.tiposSituacoes();
      this.cursos = await cursoService.combo();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.calendario = new Calendario();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
