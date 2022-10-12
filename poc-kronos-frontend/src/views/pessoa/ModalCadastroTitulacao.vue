<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <modal-seleciona-empresa></modal-seleciona-empresa>
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Titulação" @click="abrirCadastro()" title="Incluir Titulação"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md11>
                <v-text-field v-model="titulacao.nomeEmpresa" disabled :error-messages="fieldErrors('titulacao.idEmpresa')"
                  label="Empresa"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-btn color="primary" fab small alt="Pesquisar" title="Pesquisar" @click="abrirSelecionaEmpresa()"> <v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-select v-model="titulacao.idTipoTitulo" :error-messages="fieldErrors('titulacao.idTipoTitulo')"
                  @blur="$v.titulacao.idTipoTitulo.$touch()" item-text="nome" item-value="id" :items="tiposTitulos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <date-picker-custom label="Data Expedição" :v-model="titulacao.data" @date="titulacao.data = $event" 
                :error-messages="fieldErrors('titulacao.data')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="titulacao.nomeTitulo" :error-messages="fieldErrors('titulacao.nomeTitulo')"
                  @blur="$v.titulacao.nomeTitulo.$touch()" label="Nome" required></v-text-field>
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
import titulacaoService from "@/service/pessoa/TitulacaoService";
import Titulacao from "@/model/pessoa/Titulacao";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import ModalSelecionaEmpresa from "../empresa/ModalSelecionaEmpresa";
export default {
  name: "ModalCadastroTitulacao",
  components: {
    DatePickerCustom,
    ModalSelecionaEmpresa
  },
  props: {
    idPessoa: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    titulacao: Titulacao.validations(required, maxLength)
  },
  validationMessages: {
    titulacao: Titulacao.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposTitulos: [],
      titulacao: new Titulacao()
    };
  },
  watch: {
    idPessoa() {
      this.titulacao.idPessoa = this.idPessoa;
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.carregarDadosBasicos();
    EventBus.$on("editaTitulacao", async idTitulacao => {
      this.formTitle = "Alterar Titulação";
      this.btnTitle = "Alterar";
      this.titulacao = await titulacaoService.editar(idTitulacao);
      this.dialog = true;
    });
    EventBus.$on("selecionaEmpresa", empresa => {
      this.titulacao.idEmpresa = empresa.id;
      this.titulacao.nomeEmpresa = empresa.nomeFantasia;
    });
  },
  methods: {
    abrirSelecionaEmpresa() {
      EventBus.$emit("abrirSelecionaEmpresa");
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Titulação";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.titulacao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      titulacaoService.salvar(this.titulacao);
      this.$emit("titulacaoCadastrada", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.tiposTitulos = await titulacaoService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.titulacao = new Titulacao();
        this.titulacao.idPessoa = this.idPessoa;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
