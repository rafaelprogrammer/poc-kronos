<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" v-if="!desabilitaIncluir" slot="activator" color="primary" dark alt="Incluir Código" @click="abrirCadastro()" title="Incluir Código"><v-icon>add</v-icon>Incluir</v-btn>
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
                <v-select :disabled="somenteLeitura" v-model="direito.idNivel" :error-messages="fieldErrors('direito.idNivel')"
                  @blur="$v.direito.idNivel.$touch()" item-text="nome" item-value="id"
                  @change="carregarCamposExperiencias(direito.idNivel)"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-checkbox :disabled="somenteLeitura" color="primary" label="BNCC" @change="atualizarInstituicao(direito.bncc)" v-model="direito.bncc"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox :disabled="somenteLeitura" color="primary" label="Ativo" v-model="direito.ativo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field :disabled="somenteLeitura" v-model="direito.codigo" :error-messages="fieldErrors('direito.codigo')"
                  @blur="$v.direito.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea :disabled="somenteLeitura" rows="2" cols="5" label="Descrição" v-model="direito.descricao"
                :error-messages="fieldErrors('direito.descricao')" @blur="$v.direito.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
              <v-select :disabled="somenteLeitura" v-model="direito.idsCamposExperiencias" item-text="nome" 
                item-value="id" :items="camposExperiencias" attach chips label="Campos de Experiências"
                :error-messages="fieldErrors('direito.idsCamposExperiencias')"
                @blur="$v.direito.idsCamposExperiencias.$touch()" multiple></v-select>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" v-if="!somenteLeitura" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Direito from "@/model/basecurricular/Direito";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroDireito",
  props: {
    desabilitaIncluir: {
      type: Boolean,
      default: false
    },
    niveis: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    direito: Direito.validations(required, maxLength)
  },
  validationMessages: {
    direito: Direito.validationMessages()
  },
  data() {
    return {
      somenteLeitura: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      instituicao: null,
      camposExperiencias: [],
      direito: new Direito()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("visualizaDireito", async (id) => {
      this.formTitle = "Visualizar Direito";
      this.somenteLeitura = true;
      this.abrirEditar(id);
    });
    EventBus.$on("editaDireito", async (id) => {
      this.formTitle = "Alterar Direito";
      this.btnTitle = "Alterar";
      this.abrirEditar(id);
    });
  },
  methods: {
    async abrirEditar(id) {
      this.direito = await baseCurricularService.editarDireito(id);
      this.carregarCamposExperiencias(this.direito.idNivel);
      this.atualizarInstituicao(this.direito.bncc);
      this.dialog = true;
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Direito";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.direito.ativo = true;
      this.instituicao = this.$store.getters.user.instituicao;
    },
    salvar() {
      this.$v.direito.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarDireito(this.direito);
      this.$emit("direitoCadastrado", true);
      this.dialog = false;
    },
    atualizarInstituicao(bncc) {
      this.instituicao = bncc ? null : this.$store.getters.user.instituicao;
    },
    async carregarCamposExperiencias(idNivel) {
      this.camposExperiencias = await baseCurricularService.comboCamposExperiencias(idNivel);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.direito = new Direito();
        this.somenteLeitura = false;
      }, 300);
    }
  }
};
</script>
