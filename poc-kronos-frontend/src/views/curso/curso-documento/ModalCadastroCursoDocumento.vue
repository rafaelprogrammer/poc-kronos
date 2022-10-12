<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Documento do Curso" @click="abrirCadastro()" title="Incluir Documento do Curso"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md9>
                <v-select v-model="cursoDocumento.idTipoDocumento" :error-messages="fieldErrors('cursoDocumento.idTipoDocumento')"
                  @blur="$v.cursoDocumento.idTipoDocumento.$touch()" item-text="nome" item-value="id" :items="tiposDocumentos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field type="number" min="1" max="5" v-model="cursoDocumento.numeroCopia" :error-messages="fieldErrors('cursoDocumento.numeroCopia')"
                  label="Nr Cópias"  @blur="$v.cursoDocumento.numeroCopia.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-checkbox type="checkbox" label="Original" v-model="cursoDocumento.original"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-checkbox type="checkbox" label="Autenticação" v-model="cursoDocumento.autenticacao"></v-checkbox>
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
import documentoService from "@/service/pessoa/DocumentoService";
import cursoDocumentoService from "@/service/curso/CursoDocumentoService";
import CursoDocumento from "@/model/curso/curso-documento/CursoDocumento";
import { required, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroCursoDocumento",
  props: {
    idCurso: Number,
    nomeCurso: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    cursoDocumento: CursoDocumento.validations(required, numeric)
  },
  validationMessages: {
    cursoDocumento: CursoDocumento.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      ranger: [1, 5],
      tiposDocumentos: [],
      cursoDocumento: new CursoDocumento()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaCursoDocumento", async idCursoDocumento => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Documento Previsto Matrícula";
      this.btnTitle = "Alterar";
      this.cursoDocumento = await cursoDocumentoService.editar(
        idCursoDocumento
      );
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIdCurso();
      this.formTitle = "Cadastrar Documento Previsto Matrícula";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.cursoDocumento.$touch();
      if (this.$v.$invalid) {
        return;
      }
      cursoDocumentoService.salvar(this.cursoDocumento).then(() => {
        this.$emit("cursoDocumentoCadastrado", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.tiposDocumentos = await documentoService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.cursoDocumento = new CursoDocumento();
      }, 300);
    },
    setaIdCurso() {
      this.cursoDocumento.idCurso = this.idCurso;
    }
  }
};
</script>
