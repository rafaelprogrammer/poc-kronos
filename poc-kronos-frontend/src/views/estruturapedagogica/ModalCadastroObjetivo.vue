<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down"  v-if="!desabilitaIncluir" slot="activator" color="primary" dark alt="Incluir Código" @click="abrirCadastro()" title="Incluir Código"><v-icon>add</v-icon>Incluir</v-btn>
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
                <v-select :disabled="somenteLeitura" v-model="objetivo.idFaixaAno" :error-messages="fieldErrors('objetivo.idFaixaAno')"
                  @blur="$v.objetivo.idFaixaAno.$touch()" item-text="nomeCombo" item-value="id"
                  @change="carregarCamposExperiencias(objetivo.idFaixaAno)"
                  :items="faixasAnos" label="Faixa/Ano"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                  <v-checkbox color="primary" :disabled="somenteLeitura" label="BNCC" @change="atualizarInstituicao(objetivo.bncc)" v-model="objetivo.bncc"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="Ativo" v-model="objetivo.ativo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field :disabled="somenteLeitura" v-model="objetivo.codigo" :error-messages="fieldErrors('objetivo.codigo')"
                  @blur="$v.objetivo.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" :disabled="somenteLeitura" label="Descrição" v-model="objetivo.descricao"
                :error-messages="fieldErrors('objetivo.descricao')" @blur="$v.objetivo.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
              <v-select v-model="objetivo.idCampoExperiencia" :disabled="somenteLeitura" item-text="nome" 
                item-value="id" :items="camposExperiencias" attach chips label="Campos de Experiências"
                :error-messages="fieldErrors('objetivo.idCampoExperiencia')"
                @blur="$v.objetivo.idCampoExperiencia.$touch()"></v-select>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary"  v-if="!somenteLeitura" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Objetivo from "@/model/basecurricular/Objetivo";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroObjetivo",
  props: {
    desabilitaIncluir: {
      type: Boolean,
      default: false
    },
    faixasAnos: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    objetivo: Objetivo.validations(required, maxLength)
  },
  validationMessages: {
    objetivo: Objetivo.validationMessages()
  },
  data() {
    return {
      somenteLeitura: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      instituicao: null,
      camposExperiencias: [],
      objetivo: new Objetivo()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("visualizaObjetivo", async (id) => {
      this.formTitle = "Visualizar Objetivo";
      this.somenteLeitura = true;
      this.abrirEditar(id);
    });
    EventBus.$on("editaObjetivo", async (id) => {
      this.formTitle = "Alterar Objetivo";
      this.btnTitle = "Alterar";
      this.abrirEditar(id);
    });
  },
  methods: {
    async abrirEditar(id) {
      this.objetivo = await baseCurricularService.editarObjetivo(id);
      this.carregarCamposExperiencias(this.objetivo.idFaixaAno);
      this.atualizarInstituicao(this.objetivo.bncc);
      this.dialog = true;
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Objetivo";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.instituicao = this.$store.getters.user.instituicao;
    },
    salvar() {
      this.$v.objetivo.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarObjetivo(this.objetivo);
      this.$emit("objetivoCadastrado", true);
      this.dialog = false;
    },
    atualizarInstituicao(bncc) {
      this.instituicao = bncc ? null : this.$store.getters.user.instituicao;
    },
    async carregarCamposExperiencias(idFaixaAno) {
      const faixaAno = await baseCurricularService.editarFaixaAno(idFaixaAno);
      this.camposExperiencias = await baseCurricularService.comboCamposExperiencias(faixaAno.idNivel);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.objetivo = new Objetivo();
        this.somenteLeitura = false;
      }, 300);
    }
  }
};
</script>
