<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Talentos" @click="abrirCadastro()" title="Incluir Talentos"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-select v-model="talento.idsTiposTalentos" item-text="nome" item-value="id" :items="tiposTalentos" attach chips label="Tipos" multiple
                :error-messages="fieldErrors('talento.idsTiposTalentos')"></v-select>
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
import talentoService from "@/service/pessoa/TalentoService";
import Talento from "@/model/pessoa/Talento";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
export default {
  name: "ModalCadastroTalento",
  mixins: [validationMixin, messageMixin],
  validations: {
    talento: Talento.validations(required)
  },
  validationMessages: {
    talento: Talento.validationMessages()
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposTalentos: [],
      talento: new Talento()
    };
  },
  watch: {
    idPessoa() {
      this.talento.idPessoa = this.idPessoa;
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.carregarDadosBasicos();
  },
  methods: {
    async abrirCadastro() {
      this.talento.idsTiposTalentos = await talentoService.buscarTalentosDaPessoa(
        this.talento.idPessoa
      );
      this.formTitle = "Cadastrar Talentos";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.talento.$touch();
      if (this.$v.$invalid) {
        return;
      }
      //TODO melhorar essa parte
      let talentos = [];
      this.talento.idsTiposTalentos.map(idTipo => {
        let talento = new Talento();
        talento.idPessoa = this.idPessoa;
        talento.idTipoTalento = idTipo;
        talentos.push(talento);
      });
      setTimeout(() => {
        talentoService.salvar(talentos);
        this.$emit("talentoCadastrado", true);
        this.dialog = false;
      }, 300);
    },
    async carregarDadosBasicos() {
      this.tiposTalentos = await talentoService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.talento = new Talento();
        this.talento.idPessoa = this.idPessoa;
      }, 300);
    }
  }
};
</script>
