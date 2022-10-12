<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Endereço" @click="abrirCadastro()" title="Incluir Pessoa"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="enderecoPessoa.cep" :error-messages="fieldErrors('enderecoPessoa.cep')"
                  @blur="$v.enderecoPessoa.cep.$touch()" label="CEP"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-btn color="primary" fab small dark alt="Buscar Cep" :loading="loading" @click="buscarCep()" title="Buscar Cep"><v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm6 md9>
                <v-text-field v-model="enderecoPessoa.logradouro" disabled :error-messages="fieldErrors('enderecoPessoa.logradouro')"
                  @blur="$v.enderecoPessoa.logradouro.$touch()" label="Logradouro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="enderecoPessoa.bairro" disabled  :error-messages="fieldErrors('enderecoPessoa.bairro')"
                  @blur="$v.enderecoPessoa.bairro.$touch()" label="Bairro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="enderecoPessoa.localidade" disabled :error-messages="fieldErrors('enderecoPessoa.localidade')"
                  @blur="$v.enderecoPessoa.localidade.$touch()" label="Localidade"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="enderecoPessoa.uf" disabled :error-messages="fieldErrors('enderecoPessoa.uf')"
                  @blur="$v.enderecoPessoa.uf.$touch()" label="UF"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md10>
                <v-text-field v-model="enderecoPessoa.complemento" :error-messages="fieldErrors('enderecoPessoa.complemento')"
                  label="Complemento"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="enderecoPessoa.unidade" disabled :error-messages="fieldErrors('enderecoPessoa.unidade')"
                  label="Unidade"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="enderecoPessoa.ibge" disabled :error-messages="fieldErrors('enderecoPessoa.ibge')"
                  @blur="$v.enderecoPessoa.ibge.$touch()" label="IBGE"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="enderecoPessoa.gia" disabled :error-messages="fieldErrors('enderecoPessoa.gia')"
                 label="GIA"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="enderecoPessoa.numero" :error-messages="fieldErrors('enderecoPessoa.numero')"
                  label="Número"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="enderecoPessoa.idTipoEndereco" :error-messages="fieldErrors('enderecoPessoa.idTipoEndereco')"
                  @blur="$v.enderecoPessoa.idTipoEndereco.$touch()" item-text="nome" item-value="id" :items="tiposEnderecos" label="Tipo Endereço"></v-select>
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
import apoioService from "@/service/ApoioService";
import enderecoPessoaService from "@/service/pessoa/EnderecoPessoaService";
import EnderecoPessoa from "@/model/pessoa/EnderecoPessoa";
import {
  required,
  maxLength,
  minLength,
  numeric
} from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
export default {
  name: "ModalCadastroEnderecoPessoa",
  props: {
    idPessoa: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    enderecoPessoa: EnderecoPessoa.validations(
      required,
      maxLength,
      minLength,
      numeric
    )
  },
  validationMessages: {
    enderecoPessoa: EnderecoPessoa.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposEnderecos: [],
      enderecoPessoa: new EnderecoPessoa()
    };
  },
  watch: {
    idPessoa() {
      this.enderecoPessoa.idPessoa = this.idPessoa;
    },
    dialog(val) {
      val || this.close();
    },
    loader() {
      const l = this.loader;
      this[l] = !this[l];

      setTimeout(() => (this[l] = false), 3000);

      this.loader = null;
    }
  },
  async created() {
    this.carregarDadosBasicos();
  },
  methods: {
    async buscarCep() {
      this.$v.enderecoPessoa.cep.$touch();
      if (this.$v.enderecoPessoa.cep.$invalid) {
        return;
      }
      this.loading = true;
      if (this.enderecoPessoa.cep) {
        const cep = await apoioService.cep(this.enderecoPessoa.cep);
        if (cep) {
          this.enderecoPessoa.adicionarDadosCep(cep);
        }
      }
      this.loading = false;
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Endereço";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.enderecoPessoa.$touch();
      if (this.$v.$invalid) {
        return;
      }
      enderecoPessoaService.salvar(this.enderecoPessoa);
      this.$emit("enderecoPessoaCadastrado", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.tiposEnderecos = await enderecoPessoaService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.enderecoPessoa = new EnderecoPessoa();
        this.enderecoPessoa.idPessoa = this.idPessoa;
      }, 300);
    }
  }
};
</script>
