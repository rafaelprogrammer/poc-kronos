<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down"  v-if="!desabilitaIncluir" slot="activator" color="primary" dark alt="Incluir Competência" @click="abrirCadastro()" title="Incluir Competência"><v-icon>add</v-icon>Incluir</v-btn>
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
                <v-select :disabled="somenteLeitura" v-model="competencia.idNivel" :error-messages="fieldErrors('competencia.idNivel')"
                  @blur="$v.competencia.idNivel.$touch()" item-text="nome" item-value="id"
                  @change="carregarComponentes(competencia.idNivel)"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="BNCC" @change="atualizarInstituicao(competencia.bncc)" v-model="competencia.bncc"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="Ativo" v-model="competencia.ativo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="Geral" @change="bloquearComponentes(competencia.geral)" v-model="competencia.geral"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="competencia.codigo" :disabled="somenteLeitura" :error-messages="fieldErrors('competencia.codigo')"
                  @blur="$v.competencia.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" :disabled="somenteLeitura" label="Descrição" v-model="competencia.descricao"
                :error-messages="fieldErrors('competencia.descricao')" @blur="$v.competencia.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="competencia.idComponente" item-text="nome" :disabled="bloqueaComponente || somenteLeitura"
                  item-value="id" :items="componentes" label="Componente"
                  ></v-select>
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
import Competencia from "@/model/basecurricular/Competencia";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroCompetencia",
  props: {
    desabilitaIncluir: {
      type: Boolean,
      default: false
    },
    niveis: Array
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    competencia: Competencia.validations(required, maxLength)
  },
  validationMessages: {
    competencia: Competencia.validationMessages()
  },
  data() {
    return {
      somenteLeitura: false,
      bloqueaComponente: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      instituicao: null,
      componentes: [],
      competencia: new Competencia()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("visualizaCompetencia", async (id) => {
      this.formTitle = "Visualizar Competência";
      this.somenteLeitura = true;
      this.abrirEditar(id);
    });
    EventBus.$on("editaCompetencia", async (id) => {
      this.formTitle = "Alterar Competência";
      this.btnTitle = "Alterar";
      this.abrirEditar(id);
    });
  },
  methods: {
    async abrirEditar(id) {
      this.competencia = await baseCurricularService.editarCompetencia(id);
      this.bloquearComponentes(this.competencia.geral);
      this.carregarComponentes(this.competencia.idNivel);
      this.atualizarInstituicao(this.competencia.bncc);
      this.dialog = true;
    },
    bloquearComponentes(geral) {
      if (geral) {
        this.competencia.idComponente = null;
        this.bloqueaComponente = true;
      } else {
        this.bloqueaComponente = false;
      }
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Competência";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.competencia.ativo = true;
      this.instituicao = this.$store.getters.user.instituicao;
    },
    salvar() {
      this.$v.competencia.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarCompetencia(this.competencia);
      this.$emit("competenciaCadastrada", true);
      this.dialog = false;
    },
    atualizarInstituicao(bncc) {
      this.instituicao = bncc ? null : this.$store.getters.user.instituicao;
    },
    async carregarComponentes(idNivel) {
      this.componentes = await baseCurricularService.comboComponentes(idNivel);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.competencia = new Competencia();
        this.bloqueaComponente = false;
      }, 300);
    }
  }
};
</script>
