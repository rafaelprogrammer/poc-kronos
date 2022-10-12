<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Valor" @click="abrirCadastro()" title="Incluir Valor"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="valor.codigo" :error-messages="fieldErrors('valor.codigo')"
                  @blur="$v.valor.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-text-field v-model="valor.nome" :error-messages="fieldErrors('valor.nome')"
                  @blur="$v.valor.nome.$touch()" label="Nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Ativo" v-model="valor.ativo"></v-checkbox>
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
import Valor from "@/model/basecurricular/Valor";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroValor",
  mixins: [validationMixin, messageMixin],
  validations: {
    valor: Valor.validations(required, maxLength)
  },
  validationMessages: {
    valor: Valor.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      valor: new Valor()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaValor", async (id) => {
      this.formTitle = "Alterar Valor";
      this.btnTitle = "Alterar";
      this.valor = await baseCurricularService.editarValor(id);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Valor";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.valor.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarValor(this.valor);
      this.$emit("valorCadastrado", true);
      this.dialog = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.valor = new Valor();
      }, 300);
    }
  }
};
</script>
