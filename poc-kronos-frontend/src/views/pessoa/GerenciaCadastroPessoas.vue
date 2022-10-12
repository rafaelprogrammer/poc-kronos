<template>
  <div>
    <modal-upload-arquivo></modal-upload-arquivo>
     <modal-preview></modal-preview>
    <section-definition title="Pessoas"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Pessoas</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-btn color="primary" alt="Alterar Pessoa" v-show="pessoa && pessoa.id" @click="editar" title="Alterar Pessoa"><v-icon>edit</v-icon>Editar</v-btn>
        <modal-seleciona-pessoa></modal-seleciona-pessoa>
        <v-spacer></v-spacer>
        <modal-cadastro-pessoa></modal-cadastro-pessoa>
    </v-toolbar>
            <v-container fluid>
                  <v-layout row>
                      <v-layout wrap justify-space-between>
                        <v-flex xs12 sm2 md2>
                          <v-text-field color="primary" disabled label="Nr Registro" v-model="pessoa.numeroRegistro"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm2 md2>
                          <v-text-field color="primary" disabled label="CPF" v-model="pessoa.cpf"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm2 md2>
                          <v-text-field color="primary" disabled label="Data Nascimento" v-model="pessoa.dataNascimento"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm1 md2>
                          <v-select v-model="pessoa.idGenero" disabled item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
                        </v-flex>
                        <v-flex xs12 sm1 md1>
                          <v-select v-model="pessoa.idCidade" disabled item-text="nome" item-value="id" :items="cidades" label="Cidade"></v-select>
                        </v-flex>
                        <v-flex xs12 sm2 md2>
                          <v-text-field color="primary" disabled label="Banco Talentos" v-model="pessoa.bancoTalentoStr"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm2 md2>
                          <v-text-field color="primary" disabled label="Grau Comportamento" v-model="pessoa.grauComportamento"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm4 md4>
                          <v-text-field color="primary" disabled label="Nome" v-model="pessoa.nome"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm4 md4>
                          <v-text-field color="primary" disabled label="Nome Social" v-model="pessoa.nomeSocial"></v-text-field>
                        </v-flex>
                      </v-layout>
                      <v-flex xs12 sm3 md3 class="text-md-center">
                        <img :src="arquivo && arquivo.dadosBase64 ? arquivo.dadosBase64 : '/static/doc-images/lists/avatar-default.jpg'" width="80%" :alt="arquivo && arquivo.legenda ? arquivo.legenda : ''" />
                        <modal-foto @fotoCadastrada="foto = $event" :pessoa="pessoa"></modal-foto>
                      </v-flex>
                </v-layout>
                <v-layout wrap justify-space-between>  
                      <v-flex xs12 sm4 md4>
                        <v-text-field color="primary" disabled label="Nome Usual" v-model="pessoa.nomeUsual"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md3>
                        <v-text-field color="primary" disabled label="Email" v-model="pessoa.email"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-select v-model="pessoa.idInstituicao" disabled item-text="nome" item-value="id" :items="instituicoes" label="Instituição"></v-select>
                      </v-flex>
                </v-layout>
            </v-container>
            <v-tabs color="primary" v-if="pessoa.id" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Endereços</v-tab>
              <v-tab>Telefones</v-tab>
              <v-tab>Filiação</v-tab>
              <v-tab>Responsáveis</v-tab>
              <v-tab>Titulações</v-tab>
              <v-tab>Documentos</v-tab>
              <v-tab>Talentos</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-endereco-pessoa :id-pessoa="pessoa.id"></lista-endereco-pessoa>
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-telefone-pessoa :id-pessoa="pessoa.id"></lista-telefone-pessoa>
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-filiacao :id-pessoa="pessoa.id"></lista-filiacao></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-responsavel :id-pessoa="pessoa.id"></lista-responsavel></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-titulacao :id-pessoa="pessoa.id"></lista-titulacao></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                         <v-card-text><lista-documento :id-pessoa="pessoa.id"></lista-documento></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-talento :id-pessoa="pessoa.id"></lista-talento></v-card-text>
                      </v-card>
                    </v-tab-item>
              </v-tabs-items>
            </v-tabs>
     </div>
</template>
<script>
import pessoaService from "@/service/pessoa/PessoaService";
import arquivoService from "@/service/arquivo/ArquivoService";
import EventBus from "@/utils/EventBus";
import ModalUploadArquivo from "../arquivo/ModalUploadArquivo";
import ModalPreview from "../arquivo/ModalPreview";
import ModalSelecionaPessoa from "./ModalSelecionaPessoa";
import ModalCadastroPessoa from "./ModalCadastroPessoa";
import ModalFoto from "./ModalFoto";
import ListaEnderecoPessoa from "./ListaEnderecoPessoa";
import ListaTelefonePessoa from "./ListaTelefonePessoa";
import ListaFiliacao from "./ListaFiliacao";
import ListaResponsavel from "./ListaResponsavel";
import ListaDocumento from "./ListaDocumento";
import ListaTitulacao from "./ListaTitulacao";
import ListaTalento from "./ListaTalento";
import apoioService from "@/service/ApoioService";
import instituicaoService from "@/service/InstituicaoService";
import Pessoa from "@/model/pessoa/Pessoa";
import Arquivo from "@/model/arquivo/Arquivo";
export default {
  name: "GerenciaCadastroPessoas",
  components: {
    ModalPreview,
    ModalUploadArquivo,
    ModalSelecionaPessoa,
    ModalCadastroPessoa,
    ModalFoto,
    ListaEnderecoPessoa,
    ListaTelefonePessoa,
    ListaFiliacao,
    ListaResponsavel,
    ListaDocumento,
    ListaTitulacao,
    ListaTalento
  },
  data() {
    return {
      foto: null,
      tiposGeneros: [],
      cidades: [],
      instituicoes: [],
      pessoa: {},
      arquivo: {}
    };
  },
  watch: {
    foto() {
      this.arquivo.dadosBase64 = this.foto;
      this.arquivo.legenda = this.pessoa.nome;
      this.arquivo.nome = this.pessoa.numeroRegistro;
    }
  },
  async created() {
    this.tiposGeneros = await pessoaService.tiposGeneros();
    this.cidades = await apoioService.cidades();
    //TODO não precisar ser uma combo - alterar para somente mostrar o nome
    const resultado = await instituicaoService.listar();
    this.instituicoes = resultado.dados;
    this.pessoa = Object.assign({}, Pessoa);
    this.arquivo = null;
    EventBus.$on("selecionaPessoa", async dados => {
      this.pessoa = new Pessoa(dados);
      if (this.pessoa.idArqAnexo || this.pessoa.idArqAnexo > 0) {
        this.arquivo = await arquivoService.buscarPorId(this.pessoa.idArqAnexo);
      } else {
        this.arquivo = new Arquivo();
      }
    });
  },
  methods: {
    editar() {
      EventBus.$emit("editaPessoa", this.pessoa.id);
    }
  }
};
</script>
