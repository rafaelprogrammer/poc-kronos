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
              <v-flex xs12 sm6 md6>
                <v-text-field color="primary" label="Instituição" disabled v-model="instituicao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select v-model="componente.idNivel" :error-messages="fieldErrors('componente.idNivel')"
                  @blur="$v.componente.idNivel.$touch()" item-text="nome" item-value="id"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md7>
                <v-text-field v-model="componente.nome" :error-messages="fieldErrors('componente.nome')"
                  @blur="$v.componente.nome.$touch()" label="Nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-checkbox color="primary" label="BNCC" @change="atualizarInstituicao(componente.bncc)" v-model="componente.bncc"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Ativo" v-model="componente.ativo"></v-checkbox>
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
import Componente from "@/model/basecurricular/Componente";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroComponente",
  props: {
    niveis: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    componente: Componente.validations(required, maxLength)
  },
  validationMessages: {
    componente: Componente.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      componente: new Componente()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaComponente", async (id) => {
      this.formTitle = "Alterar Componente";
      this.btnTitle = "Alterar";
      this.componente = await baseCurricularService.editarComponente(id);
      this.atualizarInstituicao(this.componente.bncc);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Componente";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.componente.ativo = true;
      this.instituicao = this.$store.getters.user.instituicao;
    },
    salvar() {
      this.$v.componente.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarComponente(this.componente);
      this.$emit("componenteCadastrado", true);
      this.dialog = false;
    },
    atualizarInstituicao(bncc) {
      this.instituicao = bncc ? null : this.$store.getters.user.instituicao;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.componente = new Componente();
      }, 300);
    }
  }
};
</script>
