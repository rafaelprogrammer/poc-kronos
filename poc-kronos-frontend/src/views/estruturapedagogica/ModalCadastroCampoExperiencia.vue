<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Componente" @click="abrirCadastro()" title="Incluir Componente"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md5>
                <v-select v-model="campoExperiencia.idNivel" :error-messages="fieldErrors('campoExperiencia.idNivel')"
                  @blur="$v.campoExperiencia.idNivel.$touch()" item-text="nome" item-value="id"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md7>
                <v-text-field v-model="campoExperiencia.nome" :error-messages="fieldErrors('campoExperiencia.nome')"
                  @blur="$v.campoExperiencia.nome.$touch()" label="Nome"></v-text-field>
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
import CampoExperiencia from "@/model/basecurricular/CampoExperiencia";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroCampoExperiencia",
  props: {
    niveis: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    campoExperiencia: CampoExperiencia.validations(required, maxLength)
  },
  validationMessages: {
    campoExperiencia: CampoExperiencia.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      campoExperiencia: new CampoExperiencia()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaCampoExperiencia", async (id) => {
      this.formTitle = "Alterar Campo Experiência";
      this.btnTitle = "Alterar";
      this.campoExperiencia = await baseCurricularService.editarCampoExperiencia(id);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Campo Experiência";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.campoExperiencia.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarCampoExperiencia(this.campoExperiencia);
      this.$emit("campoExperienciaCadastrado", true);
      this.dialog = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.campoExperiencia = new CampoExperiencia();
      }, 300);
    }
  }
};
</script>
