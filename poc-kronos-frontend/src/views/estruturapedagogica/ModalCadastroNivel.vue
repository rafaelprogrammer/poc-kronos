<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Nível" @click="abrirCadastro()" title="Incluir Nível"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nivel.nome" :error-messages="fieldErrors('nivel.nome')"
                  @blur="$v.nivel.nome.$touch()" label="Nome"></v-text-field>
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
import Nivel from "@/model/basecurricular/Nivel";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroNivel",
  mixins: [validationMixin, messageMixin],
  validations: {
    nivel: Nivel.validations(required, maxLength)
  },
  validationMessages: {
    nivel: Nivel.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      nivel: new Nivel()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaNivel", async (id) => {
      this.formTitle = "Alterar Nível";
      this.btnTitle = "Alterar";
      this.nivel = await baseCurricularService.editarNivel(id);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Nível";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.nivel.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarNivel(this.nivel);
      this.$emit("nivelCadastrado", true);
      this.dialog = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.nivel = new Nivel();
      }, 300);
    }
  }
};
</script>
