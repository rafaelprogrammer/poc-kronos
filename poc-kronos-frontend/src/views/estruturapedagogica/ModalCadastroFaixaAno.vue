<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Faixa/Ano" @click="abrirCadastro()" title="Incluir Faixa/Ano"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md5>
                <v-select v-model="faixaAno.idNivel" :error-messages="fieldErrors('faixaAno.idNivel')"
                  @blur="$v.faixaAno.idNivel.$touch()" item-text="nome" item-value="id"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md7>
                <v-text-field v-model="faixaAno.nome" :error-messages="fieldErrors('faixaAno.nome')"
                  @blur="$v.faixaAno.nome.$touch()" label="Nome"></v-text-field>
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
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import FaixaAno from "@/model/basecurricular/FaixaAno";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroFaixaAno",
  props: {
    niveis: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    faixaAno: FaixaAno.validations(required, maxLength)
  },
  validationMessages: {
    faixaAno: FaixaAno.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      faixaAno: new FaixaAno()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaFaixaAno", async (id) => {
      this.formTitle = "Alterar Faixa/Ano";
      this.btnTitle = "Alterar";
      this.faixaAno = await baseCurricularService.editarFaixaAno(id);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Faixa/Ano";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.faixaAno.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarFaixaAno(this.faixaAno);
      this.$emit("faixaAnoCadastrado", true);
      this.dialog = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.faixaAno = new FaixaAno();
      }, 300);
    }
  }
};
</script>
