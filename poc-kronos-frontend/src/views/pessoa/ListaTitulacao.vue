<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md2>
      <modal-cadastro-titulacao :id-pessoa="titulacao.idPessoa"
      @titulacaoCadastrada="cadastroEfetuado = $event"></modal-cadastro-titulacao>
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" :items="titulacoes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.nomeTipoTitulo }}</td>
          <td>{{ props.item.nomeTitulo }}</td>
          <td>{{ props.item.data }}</td>
          <td>{{ props.item.nomeEmpresa }}</td>
          <td>
            <v-icon title="Editar Titulação" medium class="mr-2" @click="editar(props.item)">
              edit
            </v-icon>
            <v-icon title="Excluir Titulação" medium class="mr-2" @click="excluir(props.item)" >
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
import titulacaoService from "@/service/pessoa/TitulacaoService";
import Titulacao from "@/model/pessoa/Titulacao";
import ModalCadastroTitulacao from "./ModalCadastroTitulacao";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
export default {
  name: "ListaTitulacao",
  components: {
    ModalCadastroTitulacao
  },
  props: {
    idPessoa: Number
  },
  data() {
    return {
      dialog: false,
      titulacoes: [],
      titulacao: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoTitulo"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomeTitulo"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Empresa",
          align: "left",
          sortable: false,
          value: "nomeEmpresa"
        },
        { text: "Titulação", value: "name", sortable: false },
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
    this.atualizarIdPessoa();
    this.titulacao = Object.assign({}, Titulacao);
    EventBus.$on("uploadArquivoTitulacao", async arquivo => {
      titulacaoService.arquivo(arquivo, this.titulacao).then(() => {
        setTimeout(() => {
          this.titulacao = new Titulacao();
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
      this.titulacao = item;
      this.atualizarIdPessoa();
      EventBus.$emit(
        "abrirUpload",
        "uploadArquivoTitulacao",
        this.titulacao.idPessoa,
        this.titulacao.idTipoTitulo
      );
    },
    excluirArquivo(titulacao) {
      titulacao.idArqAnexo = null;
      confirm("Você deseja realmente excluir o anexo?") &&
        titulacaoService.salvar(titulacao).then(() => {
          this.atualizar();
        });
    },
    excluir(titulacao) {
      confirm("Você deseja realmente excluir a Titulação?") &&
        titulacaoService.excluir(titulacao).then(() => {
          this.titulacoes.splice(this.titulacoes.indexOf(titulacao), 1);
          this.atualizar();
        });
    },
    editar(titulacao) {
      EventBus.$emit("editaTitulacao", titulacao.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdPessoa();
      if (this.titulacao.idPessoa) {
        this.loading = true;
        const paginacaoResultado = await titulacaoService.listarTodos(
          this.titulacao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.titulacoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      } else {
        this.loading = false;
      }
    },
    atualizarIdPessoa() {
      this.titulacao.idPessoa = this.idPessoa;
    }
  }
};
</script>
