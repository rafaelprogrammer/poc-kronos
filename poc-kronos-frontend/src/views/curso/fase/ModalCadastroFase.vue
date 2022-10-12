<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Fase" @click="abrirCadastro()" title="Incluir Fase"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3> 
                <v-text-field v-model="siglaPeriodo" disabled
                  label="Sigla Período"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-select v-model="fase.idTipoPeriodo" :error-messages="fieldErrors('fase.idTipoPeriodo')"
                  @blur="$v.fase.idTipoPeriodo.$touch()" item-text="nome" item-value="id" :items="tiposPeriodos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="fase.nome" :error-messages="fieldErrors('fase.nome')"
                  label="Nome"  @blur="$v.fase.nome.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="fase.sigla" :error-messages="fieldErrors('fase.sigla')"
                  label="Sigla"  @blur="$v.fase.sigla.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="fase.numero" :error-messages="fieldErrors('fase.numero')"
                  label="Número"  @blur="$v.fase.numero.$touch()"></v-text-field>
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
import periodoService from "@/service/curso/PeriodoService";
import faseService from "@/service/curso/FaseService";
import Fase from "@/model/curso/fase/Fase";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroFase",
  props: {
    idPeriodo: Number,
    idTipoPeriodo: Number,
    nomeCurso: String,
    siglaPeriodo: String,
    nomeTipoPeriodo: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    fase: Fase.validations(required, maxLength, numeric)
  },
  validationMessages: {
    fase: Fase.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposPeriodos: [],
      fase: new Fase()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaFase", async idFase => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Período";
      this.btnTitle = "Alterar";
      this.fase = await faseService.editar(idFase);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIds();
      this.formTitle = "Cadastrar Fase";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.fase.$touch();
      if (this.$v.$invalid) {
        return;
      }
      faseService.salvar(this.fase).then(() => {
        this.$emit("faseCadastrada", true);
        this.dialog = false;
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.fase = new Fase();
      }, 300);
    },
    async carregarDadosBasicos() {
      this.tiposPeriodos = await periodoService.tipos();
    },
    setaIds() {
      this.fase.idTipoPeriodo = this.idTipoPeriodo;
      this.fase.idPeriodo = this.idPeriodo;
    }
  }
};
</script>
