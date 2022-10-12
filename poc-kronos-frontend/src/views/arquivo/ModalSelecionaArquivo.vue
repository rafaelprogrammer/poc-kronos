<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">{{titulo}}</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <modal-preview></modal-preview>
              <modal-upload-arquivo :id-pessoa="idPessoa" @uploadComSucesso="upload = $event"></modal-upload-arquivo>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center row fill-height justify-space-between>
                      <v-select v-model="arquivo.idTipoArquivo"
                      item-text="nome" item-value="id" :items="tiposArquivos" label="Tipo Arquivo"></v-select>
                      <v-select v-model="arquivo.idTipoConteudo"
                      item-text="nome" item-value="id" :items="tiposConteudos" label="Tipo Conteúdo"></v-select>
                      <v-text-field color="primary" label="Nome" v-model="arquivo.nome"></v-text-field>
                      <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="arquivos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoArquivo }}</td>
                    <td>{{ props.item.nomeTipoConteudo }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>
                      <v-icon title="Selecionar" medium class="mr-2" @click="selecionar(props.item)">
                        done
                      </v-icon>
                      <v-icon slot="activator" title="Visualizar" alt="Visualizar" medium class="mr-2" @click="abrirPreview(props.item.id)" >
                          pageview
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                      Arquivo não localizado!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="dialog = false">Fechar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import arquivoService from "@/service/arquivo/ArquivoService";
import EventBus from "@/utils/EventBus";
import Arquivo from "@/model/arquivo/Arquivo";
import Pagination from "@/utils/Pagination.js";
import ModalUploadArquivo from "./ModalUploadArquivo";
import ModalPreview from "./ModalPreview";
export default {
  name: "ModalSelecionaArquivo",
  components: {
    ModalUploadArquivo,
    ModalPreview
  },
  props: {
    idPessoa: Number,
    eventoSeleciona: String,
    titulo: String
  },
  data() {
    return {
      loading: false,
      upload: "",
      dialog: false,
      arquivos: [],
      tiposArquivos: [],
      tiposConteudos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      arquivo: {},
      headers: [
        {
          text: "Tipo Arquivo",
          align: "left",
          sortable: false,
          value: "nomeTipoArquivo"
        },
        {
          text: "Tipo Conteúdo",
          align: "left",
          sortable: false,
          value: "nomeTipoConteudo"
        },
        {
          text: "Arquivo",
          align: "left",
          sortable: false,
          value: "nome"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    upload() {
      setTimeout(() => {
        this.listarTodos();
      }, 800);
    },
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.listarTodos();
      }
    }
  },
  created() {
    this.arquivo = new Arquivo();
    this.carregarDadosBasicos();
    EventBus.$on("abrirAnexos", async () => {
      this.dialog = true;
      this.listarTodos();
    });
  },
  methods: {
    abrirPreview(id) {
      EventBus.$emit("abrirPreview", id);
    },
    selecionar(arquivo) {
      this.dialog = false;
      this.arquivo.idPessoa = this.idPessoa;
      this.$emit(this.eventoSeleciona, arquivo);
    },
    async pesquisar() {
      this.listarTodos(this.arquivo);
    },
    async listarTodos(arquivo) {
      this.loading = true;
      const paginacaoResultado = await arquivoService.listarTodos(
        arquivo,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.arquivos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async carregarDadosBasicos() {
      this.tiposArquivos = await arquivoService.tipos();
      this.tiposConteudos = await arquivoService.tiposConteudos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.arquivo = new Arquivo();
      }, 300);
    }
  }
};
</script>
