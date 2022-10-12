<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary"  alt="Incluir Grupo" @click="abrirCadastro()" title="Incluir Grupo"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
              <v-layout wrap>
                <v-flex xs12 sm6 md3>
                    <v-text-field v-model="grupoAvaliacao.numero" :error-messages="fieldErrors('grupoAvaliacao.numero')"
                      label="Número"  @blur="$v.grupoAvaliacao.numero.$touch()"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md12>
                    <v-text-field v-model="grupoAvaliacao.tema" :error-messages="fieldErrors('grupoAvaliacao.tema')"
                      label="Tema"  @blur="$v.grupoAvaliacao.tema.$touch()"></v-text-field>
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
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import GrupoAvaliacao from "@/model/avaliacao/GrupoAvaliacao";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroGrupoAvaliacao",
  props: {
    idAvaliacao: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    grupoAvaliacao: GrupoAvaliacao.validations(required, maxLength, numeric)
  },
  validationMessages: {
    grupoAvaliacao: GrupoAvaliacao.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      grupoAvaliacao: new GrupoAvaliacao()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaGrupoAvaliacao", async (idGrupoAvaliacao) => {
      this.formTitle = "Alterar Grupo";
      this.btnTitle = "Alterar";
      this.grupoAvaliacao = await avaliacaoService.editarGrupoAvaliacao(idGrupoAvaliacao);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.formTitle = "Cadastrar Grupo";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.grupoAvaliacao.idAvaliacao = this.idAvaliacao;
      this.$v.grupoAvaliacao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      avaliacaoService.salvarGrupoAvaliacao(this.grupoAvaliacao).then(() => {
        this.$emit("grupoAvaliacaoCadastrado", true);
        this.dialog = false;
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.grupoAvaliacao = new GrupoAvaliacao();
      }, 300);
    }
  }
};
</script>
