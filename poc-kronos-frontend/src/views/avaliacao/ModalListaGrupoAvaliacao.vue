<template>
  <v-layout wrap justify-center>
    <v-dialog v-model="dialog" hide-overlay persistent width="80%">
      <v-card>
          <v-card-title>
            <span class="headline">Grupos</span>
          </v-card-title>
          <v-card-text>
            <v-layout wrap>
                <v-flex xs12 sm6 md3>
                  <modal-cadastro-grupo-avaliacao :id-avaliacao="idAvaliacao" @grupoAvaliacaoCadastrado="cadastroEfetuado = $event"/>
                </v-flex>
            </v-layout>
              <v-container grid-list-md>
                <v-data-table
                    :headers="headers" :items="gruposAvaliacoes" class="elevation-1"
                    :total-items="pagination.totalItems" :pagination.sync="pagination">
                        <template slot="items" slot-scope="props">
                          <td>{{ props.item.numero }}</td>
                          <td>{{ props.item.tema }}</td>
                          <td>{{ props.item.qtdAvaliados }}</td>
                          <td>
                            <v-icon medium class="mr-2" @click="editar(props.item)" >
                              edit
                            </v-icon>
                            <v-icon v-if="props.item.qtdAvaliados === 0" medium class="mr-2" @click="excluir(props.item)" >
                              delete
                            </v-icon>
                          </td>
                        </template>
                      <template slot="no-data">
                          Não existem créditos!
                      </template>
                  </v-data-table>
                </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Fechar</v-btn>
          </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import EventBus from "@/utils/EventBus";
import ModalCadastroGrupoAvaliacao from "./ModalCadastroGrupoAvaliacao";
import Pagination from "@/utils/Pagination.js";

export default {
  name: "ModalListaGrupoAvaliacao",
  components: {
    ModalCadastroGrupoAvaliacao
  },
  data() {
    return {
      idAvaliacao: null,
      cadastroEfetuado: false,
      pagination: new Pagination(1, 5),
      dialog: false,
      cadastroEfetuado: null,
      gruposAvaliacoes: [],
      headers: [
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Tema",
          align: "left",
          sortable: false,
          value: "tema"
        },
        {
          text: "Nr Avaliados",
          align: "left",
          sortable: false,
          value: "qtdAvaliados"
        },
        { text: "Ações", value: "name", sortable: false }
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
    pagination: {
      handler() {
        this.pesquisar();
      }
    },
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("abrirListaGrupoAvaliacao", idAvaliacao => {
      this.idAvaliacao = idAvaliacao;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    editar(grupoAvaliacao) {
      EventBus.$emit("editaGrupoAvaliacao", grupoAvaliacao.id);
    },
    excluir(grupoAvaliacao) {
      confirm("Você deseja realmente excluir o Grupo?") &&
        avaliacaoService.excluirGrupoAvaliacao(grupoAvaliacao).then((dados) => {
          if (dados) {
            this.gruposAvaliacoes.splice(this.gruposAvaliacoes.indexOf(grupoAvaliacao), 1);
          }
        });
    },
    async pesquisar() {
      if (this.idAvaliacao) {
        this.loading = true;
        const paginacaoResultado = await avaliacaoService.listarTodosGruposAvaliacoes(
          this.idAvaliacao,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.gruposAvaliacoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.gruposAvaliacoes = [];
      }, 300);
    }
  }
};
</script>
