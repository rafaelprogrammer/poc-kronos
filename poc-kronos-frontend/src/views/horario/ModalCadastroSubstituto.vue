<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Período" @click="abrirCadastro()" title="Incluir Período"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md1>
                <v-text-field color="primary" label="Ano" disabled v-model="ano"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field color="primary" label="Período" disabled v-model="siglaPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field color="primary" label="Turma" disabled v-model="siglaTurma"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox disabled color="primary" label="Regular" v-model="horario.regular"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Data Inicial" disabled v-model="horario.dataInicial"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Data Final" disabled v-model="horario.dataFinal"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Disciplina" disabled v-model="horario.disciplina"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Professor" disabled v-model="horario.professor"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field color="primary" label="Regime Letivo" disabled v-model="horario.nomeTipoRegimeLetivo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <date-picker-custom label="Data Início" :v-model="substituto.dataInicial" @date="substituto.dataInicial = $event" 
                :error-messages="fieldErrors('substituto.dataInicial')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <date-picker-custom label="Data Final" :v-model="substituto.dataFinal" @date="substituto.dataFinal = $event" 
                :error-messages="fieldErrors('substituto.dataFinal')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="substituto.idFuncionario" :error-messages="fieldErrors('substituto.idFuncionario')"
                  @blur="$v.substituto.idFuncionario.$touch()" item-text="nome" item-value="id" :items="professores" label="Professor"></v-select>
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
import substitutoService from "@/service/horario/SubstitutoService";
import Substituto from "@/model/horario/Substituto";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalCadastroSubstituto",
  props: {
    nomeCurso: String,
    ano: Number,
    siglaPeriodo: String,
    siglaTurma: String,
    horario: Object
  },
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    substituto: Substituto.validations(required, dataFimMenor)
  },
  validationMessages: {
    substituto: Substituto.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      professores: [],
      substituto: new Substituto()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Substituto";
      this.btnTitle = "Salvar";
      this.substituto.idHorario = this.horario.id;
      this.dialog = true;
    },
    salvar() {
      this.$v.substituto.$touch();
      if (this.$v.$invalid) {
        return;
      }
      substitutoService.salvar(this.substituto).then((dados) => {
        if (dados) {
          this.$emit("substitutoCadastrado", true);
          this.dialog = false;
        }
      });
    },
    async carregarDadosBasicos() {
      this.professores = await substitutoService.comboProfessoresHorario(this.horario.idDisciplina, this.horario.idFuncionario);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.substituto = new Substituto();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
