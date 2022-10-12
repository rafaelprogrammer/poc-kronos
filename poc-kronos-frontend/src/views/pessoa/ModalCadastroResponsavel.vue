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
                <on-complete-custom id="pessoasResponsavel" label="Pessoa" :service="service" :v-model="pessoa" item-text="nome" v-bind:dados="pessoas" @select="responsavel.idPessoaResponsavel = ($event ? $event.id : null)"
                :error-messages="fieldErrors('responsavel.idPessoaResponsavel')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="responsavel.idTipoResponsavel" :error-messages="fieldErrors('responsavel.idTipoResponsavel')"
                  @blur="$v.responsavel.idTipoResponsavel.$touch()" item-text="nome" item-value="id" :items="tiposResponsaveis" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Início" :v-model="responsavel.dataInicio" @date="responsavel.dataInicio = $event" 
                :error-messages="fieldErrors('responsavel.dataInicio')" @blur="$v.esponsavel.dataInicio.$touch()"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Fim" :v-model="responsavel.dataFim" @date="responsavel.dataFim = $event" 
                :error-messages="fieldErrors('responsavel.dataFim')" @blur="$v.esponsavel.dataFim.$touch()"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                 <v-text-field v-model="responsavel.prioridade" :error-messages="fieldErrors('responsavel.prioridade')"
                  @blur="$v.responsavel.prioridade.$touch()" label="Prioridade"></v-text-field>
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
import responsavelService from "@/service/pessoa/ResponsavelService";
import Responsavel from "@/model/pessoa/Responsavel";
import OnCompleteCustom from "@/components/Custom/OnCompleteCustom";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, numeric, maxValue } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroResponsavel",
  components: {
    OnCompleteCustom,
    DatePickerCustom
  },
  props: {
    idPessoa: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    responsavel: Responsavel.validations(
      required,
      dataFimMenor,
      maxValue,
      numeric
    )
  },
  validationMessages: {
    responsavel: Responsavel.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposResponsaveis: [],
      pessoas: [],
      pessoa: {},
      responsavel: new Responsavel(),
      service: pessoaService
    };
  },
  watch: {
    idPessoa() {
      this.responsavel.idPessoa = this.idPessoa;
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaResponsavel", async idResponsavel => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Responsavel";
      this.btnTitle = "Alterar";
      this.responsavel = await responsavelService.editar(idResponsavel);
      this.editarComponentesOncomplete();
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Responsável";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.responsavel.$touch();
      if (this.$v.$invalid) {
        return;
      }
      responsavelService.salvar(this.responsavel);
      this.$emit("responsavelCadastrado", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.tiposResponsaveis = await responsavelService.tipos();
    },
    async editarComponentesOncomplete() {
      this.pessoas = [];
      this.pessoa = await pessoaService.editar(
        this.responsavel.idPessoaResponsavel
      );
      this.pessoas.push(this.pessoa);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.responsavel = new Responsavel();
        this.responsavel.idPessoa = this.idPessoa;
        this.pessoa = {};
        this.pessoas = [];
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
