<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Turma" @click="abrirCadastro()" title="Incluir Turma"><v-icon>add</v-icon>Incluir</v-btn>
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
                <v-text-field color="primary" label="Tipo Período" disabled v-model="turma.tipoPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-text-field color="primary" label="Calendário" disabled v-model="turma.calendario"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field color="primary" label="Sigla" v-model="turma.sigla"
                :error-messages="fieldErrors('turma.sigla')"
                @blur="$v.turma.sigla.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="turma.idTipoTurno" :error-messages="fieldErrors('turma.idTipoTurno')"
                  @blur="$v.turma.idTipoTurno.$touch()" item-text="nome" item-value="id" :items="tiposTurnos" label="Turno"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-checkbox color="primary" label="Ativa" v-model="turma.ativa"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-checkbox color="primary" label="Aberta" v-model="turma.aberta"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md1>
                  <v-checkbox color="primary" label="Encerrada" v-model="turma.encerrada"></v-checkbox>
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
import turmaService from "@/service/turma/TurmaService";
import Turma from "@/model/turma/Turma";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroTurma",
  props: {
    nomeCurso: String,
    ano: Number,
    idPeriodoExecucao: Number,
    idCalendario: Number,
    siglaPeriodo: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    turma: Turma.validations(required)
  },
  validationMessages: {
    turma: Turma.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposTurnos: [],
      turma: new Turma()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaTurma", async (idTurma) => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Turma";
      this.btnTitle = "Alterar";
      this.turma = await turmaService.editar(idTurma);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Turma";
      this.btnTitle = "Salvar";
      this.turma.idCalendario = this.idCalendario;
      this.turma.idPeriodoExecucao = this.idPeriodoExecucao;
      this.turma.ano = this.ano;
      this.dialog = true;
    },
    salvar() {
      this.$v.turma.$touch();
      if (this.$v.$invalid) {
        return;
      }
      turmaService.salvar(this.turma).then((dados) => {
        if (dados) {
          this.$emit("turmaCadastrada", true);
          this.dialog = false;
        }
      });
    },
    async carregarDadosBasicos() {
      this.tiposTurnos = await cursoService.tiposTurnos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.turma = new Turma();
      }, 300);
    }
  }
};
</script>
