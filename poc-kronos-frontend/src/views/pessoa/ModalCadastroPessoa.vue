<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" alt="Incluir" @click="abrirCadastro()" absolute dark fab top right rightclass="mb-2" title="Incluir Pessoa"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form @submit.prevent="salvar()" ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="pessoa.cpf" :mask="maskCpf" :error-messages="fieldErrors('pessoa.cpf')"
                  @blur="$v.pessoa.cpf.$touch()" label="CPF"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <date-picker-custom label="Data Nascimento" v-bind:birthday="true" :v-model="pessoa.dataNascimento" @date="pessoa.dataNascimento = $event" 
                :error-messages="fieldErrors('pessoa.dataNascimento')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <on-complete-custom id="estados" label="Estado" :v-model="estado" item-text="sigla" v-bind:dados="estados" @select="idEstado = ($event ? $event.id : null)"
                :error-messages="fieldErrors('pessoa.idEstado')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md2 v-show="pessoa.idEstado">
                <on-complete-custom id="cidades" label="Cidade" :v-model="cidade" item-text="nome" v-bind:dados="cidades" @select="pessoa.idCidade = ($event ? $event.id : null)"
                :error-messages="fieldErrors('pessoa.idCidade')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="pessoa.idGenero" :error-messages="fieldErrors('pessoa.idGenero')"
                  @blur="$v.pessoa.idGenero.$touch()" item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="pessoa.nome" :error-messages="fieldErrors('pessoa.nome')"
                  @blur="$v.pessoa.nome.$touch()" label="Nome" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="pessoa.nomeUsual" :error-messages="fieldErrors('pessoa.nomeUsual')"
                  @blur="$v.pessoa.nomeUsual.$touch()" label="Nome Usual" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="pessoa.nomeSocial" :error-messages="fieldErrors('pessoa.nomeSocial')"
                  @blur="$v.pessoa.nomeSocial.$touch()" label="Nome Social" required></v-text-field>
              </v-flex>
               <v-flex xs12 sm6 md6>
                <v-text-field v-model="pessoa.email" :error-messages="fieldErrors('pessoa.email')"
                  @blur="$v.pessoa.email.$touch()" @input="$v.pessoa.email.$touch()" label="Email"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-spacer></v-spacer>
                <v-checkbox type="checkbox" label="Banco Talentos" v-model="pessoa.bancoTalento"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="pessoa.grauComportamento" :error-messages="fieldErrors('pessoa.grauComportamento')"
                  @blur="$v.pessoa.grauComportamento.$touch()" label="Grau Comportamento" required></v-text-field>
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
import apoioService from "@/service/ApoioService";
import Pessoa from "@/model/pessoa/Pessoa";
import EventBus from "@/utils/EventBus";
import OnCompleteCustom from "@/components/Custom/OnCompleteCustom";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, email, decimal, maxValue } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import { cpf } from "@/utils/validators";
export default {
  name: "ModalCadastroPessoa",
  components: {
    OnCompleteCustom,
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    pessoa: Pessoa.validations(required, cpf, email, decimal, maxValue)
  },
  validationMessages: {
    pessoa: Pessoa.validationMessages()
  },
  data() {
    return {
      btnTitle: "",
      editaPessoa: false,
      dialog: false,
      formTitle: "",
      estados: [],
      estado: null,
      cidade: null,
      cidades: [],
      idEstado: "",
      tiposGeneros: [],
      maskCpf: "###.###.###-##",
      messageDataNascimento: "",
      pessoa: Object.assign({}, Pessoa)
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    async idEstado(idEstado) {
      this.cidades = await apoioService.cidades(null, idEstado);
      this.pessoa.idEstado = idEstado;
    }
  },
  async created() {
    this.carregarDadosBasicos();
    EventBus.$on("editaPessoa", async idPessoa => {
      this.formTitle = "Alterar Pessoa";
      this.btnTitle = "Alterar";
      this.editaPessoa = true;
      this.pessoa = await pessoaService.editar(idPessoa);
      this.editarComponentesOncomplete();
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.editaPessoa = false;
      this.formTitle = "Cadastrar Pessoa";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.pessoa.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.pessoa.idInstituicao = this.$store.getters.user.idInstituicao;
      pessoaService.salvar(this.pessoa).then((dados) => {
        if (dados) {
          this.dialog = false;
          EventBus.$emit("selecionaPessoa", this.pessoa);
        }
      });
    },
    async carregarDadosBasicos() {
      this.estados = await apoioService.estados();
      this.tiposGeneros = await pessoaService.tiposGeneros();
    },
    async editarComponentesOncomplete() {
      const arrayCidade = await apoioService.cidades(
        this.pessoa.idCidade,
        null
      );
      const arrayEstado = await apoioService.estados(arrayCidade[0].idEstado);
      this.pessoa.idEstado = arrayCidade[0].idEstado;
      // TODO ver uma forma para nao buscar duas vezes, pois esta sendo feito no watch tambem
      this.cidades = await apoioService.cidades(null, arrayEstado[0].id);
      this.estado = arrayEstado[0];
      this.cidade = arrayCidade[0];
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.pais = null;
        this.estado = null;
        this.cidade = null;
        this.editaPessoa = false;
        EventBus.$emit("resetCustom");
        this.pessoa = Object.assign({}, Pessoa);
      }, 300);
    }
  }
};
</script>
