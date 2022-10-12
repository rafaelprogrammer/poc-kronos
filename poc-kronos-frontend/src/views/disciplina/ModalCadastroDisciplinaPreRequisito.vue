<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">Disciplinas Pré-Requisitos</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md9>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="siglaPeriodo" disabled
                  label="Sigla do Período"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="disciplina.codigo" disabled
                  label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="disciplina.nome" disabled
                  label="Nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="disciplina.sigla" disabled
                  label="Sigla"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="disciplinaPreRequisito.idsDisciplinasPreRequisitos" item-text="nome" item-value="id" :items="disciplinas" attach chips label="Disciplinas Pré-Requisitos" multiple></v-select>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" dark>Registrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import disciplinaService from "@/service/disciplina/DisciplinaService";
import DisciplinaPreRequisito from "@/model/disciplina/pre-requisito/DisciplinaPreRequisito";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroDisciplinaPreRequisito",
  mixins: [validationMixin, messageMixin],
  validations: {
    disciplinaPreRequisito: DisciplinaPreRequisito.validations(required)
  },
  validationMessages: {
    disciplinaPreRequisito: DisciplinaPreRequisito.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      idCurso: 0,
      nomeCurso: "",
      siglaPeriodo: "",
      numeroPeriodo: 0,
      disciplina: {},
      disciplinas: [],
      disciplinaPreRequisito: new DisciplinaPreRequisito()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("abrirCadastroDisciplinaPreRequisito", async (idCurso, nomeCurso, numeroPeriodo, disciplina) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.siglaPeriodo = disciplina.siglaPeriodo;
      this.numeroPeriodo = numeroPeriodo;
      this.disciplina = disciplina;
      this.disciplinaPreRequisito.idDisciplina = disciplina.id;
      this.disciplinaPreRequisito.idsDisciplinasPreRequisitos = await disciplinaService.buscarPreRequisitosRegistrado(disciplina.id)
      this.carregarDadosBasicos();
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.$v.disciplinaPreRequisito.$touch();
      if (this.$v.$invalid) {
        return;
      }
      disciplinaService.registrarPreRequisito(this.disciplinaPreRequisito).then(() => {
        this.dialog = false;
        });
    },
    async carregarDadosBasicos() {
      this.disciplinas = await disciplinaService.comboUsadoEmPreRequisito(
        this.disciplina.id,
        this.idCurso,
        this.numeroPeriodo
      );
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
      }, 300);
    }
  }
};
</script>
