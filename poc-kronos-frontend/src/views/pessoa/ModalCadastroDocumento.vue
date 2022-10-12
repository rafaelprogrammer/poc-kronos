<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Documento" @click="abrirCadastro()" title="Incluir Documento"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-select v-model="documento.idTipo" :error-messages="fieldErrors('documento.idTipo')"
                  @blur="$v.documento.idTipo.$touch()" item-text="nome" item-value="id" :items="tiposDocumentos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.numero" :error-messages="fieldErrors('documento.numero')"
                  @blur="$v.documento.numero.$touch()" label="Número" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.orgaoExpeditor" :error-messages="fieldErrors('documento.orgaoExpeditor')"
                  label="Expedidor"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="documento.nomeCartorio" :error-messages="fieldErrors('documento.nomeCartorio')"
                  label="Nome Cartório"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Expedição" :v-model="documento.dataExpedicao" @date="documento.dataExpedicao = $event" 
                :error-messages="fieldErrors('documento.dataExpedicao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <on-complete-custom id="estados" label="Estado" :v-model="estado" item-text="sigla" v-bind:dados="estados" @select="idEstado = ($event ? $event.id : null)"
                :error-messages="fieldErrors('documento.idEstado')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md6 v-show="documento.idEstado">
                <on-complete-custom id="cidades" label="Cidade" :v-model="cidade" item-text="nome" v-bind:dados="cidades" @select="documento.idCidade = ($event ? $event.id : null)"
                :error-messages="fieldErrors('documento.idCidade')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.livro" :error-messages="fieldErrors('documento.livro')"
                 label="Livro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.folha" :error-messages="fieldErrors('documento.folha')"
                  label="Folha"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.secao" :error-messages="fieldErrors('documento.secao')"
                  label="Seção"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.serie" :error-messages="fieldErrors('documento.serie')"
                  label="Série"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="documento.zona" :error-messages="fieldErrors('documento.zona')"
                  label="Zona"></v-text-field>
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
import documentoService from "@/service/pessoa/DocumentoService";
import apoioService from "@/service/ApoioService";
import Documento from "@/model/pessoa/Documento";
import OnCompleteCustom from "@/components/Custom/OnCompleteCustom";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroDocumento",
  components: {
    DatePickerCustom,
    OnCompleteCustom
  },
  props: {
    idPessoa: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    documento: Documento.validations(required)
  },
  validationMessages: {
    documento: Documento.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposDocumentos: [],
      estados: [],
      estado: null,
      idEstado: "",
      cidade: null,
      cidades: [],
      documento: new Documento()
    };
  },
  watch: {
    idPessoa() {
      this.documento.idPessoa = this.idPessoa;
    },
    async idEstado(idEstado) {
      this.cidades = await apoioService.cidades(null, idEstado);
      this.documento.idEstado = idEstado;
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.carregarDadosBasicos();
    EventBus.$on("editaDocumento", async idDocumento => {
      this.formTitle = "Alterar Documento";
      this.btnTitle = "Alterar";
      this.documento = await documentoService.editar(idDocumento);
      this.editarComponentesOncomplete();
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Documento";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.documento.$touch();
      if (this.$v.$invalid) {
        return;
      }
      documentoService.salvar(this.documento);
      this.$emit("documentoCadastrado", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.estados = await apoioService.estados();
      this.tiposDocumentos = await documentoService.tipos();
    },
    async editarComponentesOncomplete() {
      const arrayCidade = await apoioService.cidades(
        this.documento.idCidade,
        null
      );
      const arrayEstado = await apoioService.estados(arrayCidade[0].idEstado);
      this.documento.idEstado = arrayCidade[0].idEstado;
      // TODO ver uma forma para nao buscar duas vezes, pois esta sendo feito no watch tambem
      this.cidades = await apoioService.cidades(null, arrayEstado[0].id);
      this.estado = arrayEstado[0];
      this.cidade = arrayCidade[0];
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.estado = null;
        this.cidade = null;
        this.documento = new Documento();
        this.documento.idPessoa = this.idPessoa;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
