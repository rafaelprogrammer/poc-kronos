<template>
  <v-layout row justify-center>
    <modal-seleciona-pessoa :botao-seleciona="false"></modal-seleciona-pessoa>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" absolute dark fab top right rightclass="mb-2" alt="Incluir Funcionário" @click="abrirCadastro()" title="Incluir Funcionário"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Nr Registro"   v-model="this.funcionario.numeroRegistro" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="CPF"  v-model="this.funcionario.cpf" disabled></v-text-field>
              </v-flex>
               <v-flex xs12 sm6 md6>
                <v-text-field label="Nome"  v-model="this.funcionario.nome" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field label="Data Nascimento"   v-model="this.funcionario.dataNascimento" disabled></v-text-field>
              </v-flex>
               <v-flex xs12 sm6 md7>
                <v-text-field label="Email"  v-model="this.funcionario.email" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1 v-if="selecionaPessoa">
                <v-btn color="primary" fab small dark alt="Selecionar Pessoa" @click="selecionarPessoa()" title="Selecionar Pessoa"><v-icon>add</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Admissão" :v-model="funcionario.dataAdmissao" @date="funcionario.dataAdmissao = $event" 
                :error-messages="fieldErrors('funcionario.dataAdmissao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field label="Data Demissão"  v-model="this.funcionario.dataDemissao" disabled></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar" :disabled="!funcionario.cpf">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Funcionario from "@/model/funcionario/Funcionario";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import ModalSelecionaPessoa from "../pessoa/ModalSelecionaPessoa";
export default {
  name: "ModalCadastroFuncionario",
  components: {
    DatePickerCustom,
    ModalSelecionaPessoa
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    funcionario: Funcionario.validations(required)
  },
  validationMessages: {
    funcionario: Funcionario.validationMessages()
  },
  data() {
    return {
      selecionaPessoa: true,
      dialog: false,
      formTitle: "",
      funcionario: new Funcionario()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("selecionaPessoa", async pessoa => {
      this.funcionario.nome = pessoa.nome;
      this.funcionario.numeroRegistro = pessoa.numeroRegistro;
      this.funcionario.cpf = pessoa.cpf;
      this.funcionario.email = pessoa.email;
      this.funcionario.dataNascimento = pessoa.dataNascimento;
      this.funcionario.idPessoa = pessoa.id;
    });
    EventBus.$on("editaFuncionario", async idFuncionario => {
      this.formTitle = "Alterar Funcionário";
      this.selecionaPessoa = false;
      this.funcionario = await funcionarioService.editar(idFuncionario);
      this.dialog = true;
    });
  },

  methods: {
    selecionarPessoa() {
      EventBus.$emit("abrirSelecionaPessoa");
    },
    abrirCadastro() {
      this.formTitle = "Cadastrar Funcionário";
      this.btnTitle = "Salvar";
      this.selecionaPessoa = true;
      this.dialog = true;
    },
    salvar() {
      this.$v.funcionario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      funcionarioService.salvar(this.funcionario);
      this.$emit("funcionarioCadastrado", true);
      this.dialog = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.funcionario = new Funcionario();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
