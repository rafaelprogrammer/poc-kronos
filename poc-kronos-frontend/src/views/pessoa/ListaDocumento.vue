<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-documento :id-pessoa="documento.idPessoa"
      @documentoCadastrado="cadastroEfetuado = $event"></modal-cadastro-documento>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="documentos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomeTipo }}</td>
          <td>{{ props.item.numero }}</td>
          <td>{{ props.item.orgaoExpeditor }}</td>
          <td>{{ props.item.dataExpedicao }}</td>
          <td>
            <v-icon title="Editar Documento" medium class="mr-2" @click="editar(props.item)">
              edit
            </v-icon>
            <v-icon title="Excluir Documento" medium class="mr-2" @click="excluir(props.item)" >
                delete
            </v-icon>
          </td>
          <td>
            <v-icon slot="activator" title="Anexar" medium class="mr-2" @click="abrirUpload(props.item)" >
                attach_file
            </v-icon>
            <v-icon slot="activator" v-if="props.item.idArqAnexo" title="Visualizar" alt="Visualizar" medium class="mr-2" @click="abrirPreview(props.item.idArqAnexo)" >
                pageview
            </v-icon>
            <v-icon title="Excluir Anexo" v-if="props.item.idArqAnexo" medium class="mr-2" @click="excluirArquivo(props.item)" >
                delete
            </v-icon>
          </td>
        </template>
        <template slot="no-data">
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</div>
</template>
<script>
import documentoService from "@/service/pessoa/DocumentoService";
import Documento from "@/model/pessoa/Documento";
import ModalCadastroDocumento from "./ModalCadastroDocumento";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
export default {
  name: "ListaDocumento",
  components: {
    ModalCadastroDocumento
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      documentos: [],
      documento: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipo"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Expedidor",
          align: "left",
          sortable: false,
          value: "orgaoExpeditor"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataExpedicao"
        },
        { text: "Documento", value: "name", sortable: false },
        { text: "Anexo", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    },
    idPessoa() {
      this.pesquisar();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.documento = Object.assign({}, Documento);
    EventBus.$on("uploadArquivoDocumento", async arquivo => {
      documentoService.arquivo(arquivo, this.documento).then(() => {
        setTimeout(() => {
          this.documento = new Documento();
          this.pesquisar();
        }, 500);
      });
    });
  },
  methods: {
    abrirPreview(idArqAnexo) {
      EventBus.$emit("abrirPreview", idArqAnexo);
    },
    abrirUpload(item) {
      this.documento = item;
      this.atualizarIdPessoa();
      EventBus.$emit(
        "abrirUpload",
        "uploadArquivoDocumento",
        this.documento.idPessoa,
        this.documento.nomeTipo
      );
    },
    excluirArquivo(documento) {
      documento.idArqAnexo = null;
      confirm("Você deseja realmente excluir o anexo?") &&
        documentoService.salvar(documento).then(() => {
          this.atualizar();
        });
    },
    excluir(documento) {
      confirm("Você deseja realmente excluir o Documento?") &&
        documentoService.excluir(documento).then(() => {
          this.documentos.splice(this.documentos.indexOf(documento), 1);
          this.atualizar();
        });
    },
    editar(documento) {
      EventBus.$emit("editaDocumento", documento.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.documento.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await documentoService.listarTodos(
          this.documento,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.documentos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.documento.idPessoa = this.idPessoa;
    }
  }
};
</script>
