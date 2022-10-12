<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-alert :value="true" type="error" v-if="errorUpload">
                  {{fieldErrorsText('arquivo.dados')}}
                </v-alert>
                <upload-btn accept="image/*,application/pdf" large fixedWidth="100%" title="Upload" maxWidth="100" v-if="mostraUpload" @file-update="update">
                  <template slot="icon">
                    <v-icon >attach_file</v-icon>
                  </template>
              </upload-btn>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Fechar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import Arquivo from "@/model/arquivo/Arquivo";
import { required } from "vuelidate/lib/validators";
import { sizeFile } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import UploadButton from "vuetify-upload-button";
export default {
  name: "ModalUploadArquivo",
  components: {
    "upload-btn": UploadButton
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    arquivo: Arquivo.validations(required, sizeFile)
  },
  validationMessages: {
    arquivo: Arquivo.validationMessages()
  },
  data() {
    return {
      evento: "",
      errorUpload: false,
      mostraUpload: false,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      arquivos: [],
      arquivo: new Arquivo()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("abrirUpload", async (evento, idPessoa, nome) => {
      this.arquivo.idPessoa = idPessoa;
      this.arquivo.nome = nome;
      this.evento = evento;
      this.abrirCadastro();
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Upload de Arquivo";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.mostraUpload = true;
    },
    salvar() {
      this.$v.arquivo.$touch();
      this.verificarErroUpload();
      if (this.$v.$invalid) {
        return;
      }
      this.close();
      EventBus.$emit(this.evento, this.arquivo);
    },
    update(file) {
      this.arquivo.dados = file;
      this.arquivo.legenda = this.arquivo.nome + " - " + file.name;
      this.$v.arquivo.dados.$touch();
      this.verificarErroUpload();
    },
    verificarErroUpload() {
      if (this.$v.arquivo.dados.$error) {
        this.errorUpload = true;
      } else {
        this.errorUpload = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.mostraUpload = false;
        this.errorUpload = false;
        this.$v.$reset();
        this.arquivo = new Arquivo();
        this.arquivo.idPessoa = this.idPessoa;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
