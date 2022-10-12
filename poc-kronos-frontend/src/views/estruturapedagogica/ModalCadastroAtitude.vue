<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Atitude" @click="abrirCadastro()" title="Incluir Atitude"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="atitude.codigo" :error-messages="fieldErrors('atitude.codigo')"
                  @blur="$v.atitude.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select v-model="atitude.idValor" :error-messages="fieldErrors('atitude.idValor')"
                  @blur="$v.atitude.idValor.$touch()" item-text="nome" item-value="id"
                  :items="valores" label="Valor"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-text-field v-model="atitude.nome" :error-messages="fieldErrors('atitude.nome')"
                  @blur="$v.atitude.nome.$touch()" label="Nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Ativo" v-model="atitude.ativo"></v-checkbox>
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
import Atitude from "@/model/basecurricular/Atitude";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroAtitude",
  mixins: [validationMixin, messageMixin],
  validations: {
    atitude: Atitude.validations(required, maxLength)
  },
  validationMessages: {
    atitude: Atitude.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      valores: [],
      atitude: new Atitude()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaAtitude", async (id) => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Atitude";
      this.btnTitle = "Alterar";
      this.atitude = await baseCurricularService.editarAtitude(id);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Atitude";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.atitude.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarAtitude(this.atitude);
      this.$emit("atitudeCadastrada", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.valores = await baseCurricularService.comboValores();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.atitude = new Atitude();
      }, 300);
    }
  }
};
</script>
