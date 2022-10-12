<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Filiação" @click="abrirCadastro()" title="Incluir Filiação"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <on-complete-custom id="pessoasFiliacao" label="Pessoa" :service="service" :v-model="pessoa" item-text="nome" v-bind:dados="pessoas" @select="filiacao.idPessoaPais = ($event ? $event.id : null)"
                :error-messages="fieldErrors('filiacao.idPessoaPais')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Filiação" :v-model="filiacao.dataFiliacao" @date="filiacao.dataFiliacao = $event" 
                :error-messages="fieldErrors('filiacao.dataFiliacao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="filiacao.idTipoFiliacao" :error-messages="fieldErrors('filiacao.idTipoFiliacao')"
                  @blur="$v.filiacao.idTipoFiliacao.$touch()" item-text="nome" item-value="id" :items="tiposFiliacoes" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-spacer></v-spacer>
                <v-checkbox type="checkbox" label="Atual" v-model="filiacao.filiacaoAtual"></v-checkbox>
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
import pessoaService from "@/service/pessoa/PessoaService";
import filiacaoService from "@/service/pessoa/FiliacaoService";
import Filiacao from "@/model/pessoa/Filiacao";
import OnCompleteCustom from "@/components/Custom/OnCompleteCustom";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroFiliacao",
  components: {
    OnCompleteCustom,
    DatePickerCustom
  },
  props: {
    idPessoa: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    filiacao: Filiacao.validations(required)
  },
  validationMessages: {
    filiacao: Filiacao.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposFiliacoes: [],
      pessoas: [],
      pessoa: {},
      filiacao: new Filiacao(),
      service: pessoaService
    };
  },
  watch: {
    idPessoa() {
      this.filiacao.idPessoaFilho = this.idPessoa;
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
      this.formTitle = "Cadastrar Filiação";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.filiacao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      filiacaoService.salvar(this.filiacao);
      this.$emit("filiacaoCadastrada", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.tiposFiliacoes = await filiacaoService.tipos();
      // const resultado = await pessoaService.listarTodos();
      // this.pessoas = resultado.dados;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.filiacao = new Filiacao();
        this.filiacao.idPessoaFilho = this.idPessoa;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
