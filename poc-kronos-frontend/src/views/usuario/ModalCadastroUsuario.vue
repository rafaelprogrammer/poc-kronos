<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
               <v-flex xs12 sm6 md6>
                <v-text-field v-model="this.pessoa.nome" disabled></v-text-field>
              </v-flex>
               <v-flex xs12 sm6 md6>
                <v-text-field v-model="this.pessoa.email" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="this.pessoa.cpf" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field  v-model="this.pessoa.numeroRegistro" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field  v-model="this.pessoa.dataNascimento" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="pessoa.idGenero" disabled item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Ativo" v-model="form.ativo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Bloqueado" v-model="form.bloqueado"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="form.idsPerfis" item-text="nome" item-value="id" :items="perfis" attach chips label="Perfis" multiple></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" dark @click.native="salvar">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import usuarioService from "@/service/UsuarioService";
import pessoaService from "@/service/pessoa/PessoaService";
import apoioService from "@/service/ApoioService";
import Usuario from "@/model/Usuario";
import Pessoa from "@/model/pessoa/Pessoa";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastraUsuario",
  data() {
    return {
      dialog: false,
      formTitle: "",
      perfis: [],
      idsPerfis: [],
      tiposGeneros: [],
      idTipoGenero: "",
      form: Object.assign({}, Usuario),
      pessoa: ""
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.perfis = await apoioService.perfis();
    this.tiposGeneros = await pessoaService.tiposGeneros();
    // this.form = new Usuario();
    EventBus.$on("selecionaPessoa", async pessoa => {
      this.formTitle = "Cadastrar Usuário";
      this.dialog = true;
      this.form.idPessoa = pessoa.id;
      this.pessoa = new Pessoa(pessoa);
    });

    EventBus.$on("editaUsuario", async (idUsuario, idPessoa) => {
      this.formTitle = "Alterar Usuário";
      //TODO reduzir essas consultas para somente uma.
      this.form = await usuarioService.editar(idUsuario);
      this.pessoa = await pessoaService.editar(idPessoa);
      this.dialog = true;
    });
  },

  methods: {
    salvar() {
      this.form.idInstituicao = this.$store.getters.user.idInstituicao;
      // this.form.perfis = this.idsPerfis;
      usuarioService.salvar(this.form);
      this.$emit("usuarioCadastrado", true);
      this.dialog = false;
      this.form = Object.assign({}, Usuario);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.form = Object.assign({}, Usuario);
      }, 300);
    }
  }
};
</script>
