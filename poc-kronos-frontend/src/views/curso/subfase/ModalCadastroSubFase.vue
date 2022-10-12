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
              <v-flex xs12 sm6 md6> 
                <v-text-field v-model="siglaPeriodo" disabled
                  label="Sigla Período"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6> 
                <v-text-field v-model="siglaFase" disabled
                  label="Sigla Fase"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-select v-model="subFase.idTipoPeriodo" :error-messages="fieldErrors('subFase.idTipoPeriodo')"
                  @blur="$v.subFase.idTipoPeriodo.$touch()" item-text="nome" item-value="id" :items="tiposPeriodos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="subFase.nome" :error-messages="fieldErrors('subFase.nome')"
                  label="Nome"  @blur="$v.subFase.nome.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="subFase.sigla" :error-messages="fieldErrors('subFase.sigla')"
                  label="Sigla"  @blur="$v.subFase.sigla.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="subFase.numero" :error-messages="fieldErrors('subFase.numero')"
                  label="Número"  @blur="$v.subFase.numero.$touch()"></v-text-field>
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
import subFaseService from "@/service/curso/SubFaseService";
import SubFase from "@/model/curso/subfase/SubFase";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroSubFase",
  props: {
    idFase: Number,
    nomeCurso: String,
    siglaPeriodo: String,
    siglaFase: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    subFase: SubFase.validations(required, maxLength, numeric)
  },
  validationMessages: {
    subFase: SubFase.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposPeriodos: [],
      subFase: new SubFase()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaSubFase", async idSubFase => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Sub-Fase";
      this.btnTitle = "Alterar";
      this.subFase = await subFaseService.editar(idSubFase);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIds();
      this.formTitle = "Cadastrar Sub-Fase";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.subFase.$touch();
      if (this.$v.$invalid) {
        return;
      }
      subFaseService.salvar(this.subFase).then(() => {
        this.$emit("subFaseCadastrada", true);
        this.dialog = false;
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.subFase = new SubFase();
      }, 300);
    },
    async carregarDadosBasicos() {
      this.tiposPeriodos = await periodoService.tipos();
    },
    setaIds() {
      this.subFase.idFase = this.idFase;
    }
  }
};
</script>
