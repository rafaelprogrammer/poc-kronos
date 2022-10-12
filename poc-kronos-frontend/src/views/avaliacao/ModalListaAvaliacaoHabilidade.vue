<template>
  <v-layout wrap justify-center>
      <v-dialog v-model="dialog" hide-overlay persistent width="80%">
      <v-card>
          <v-card-title>
            <span class="headline">Habilidades</span>
          </v-card-title>
          <v-card-text>
            <v-layout wrap>
                <v-flex xs12 sm6 md3>
                  <modal-seleciona-disciplina-habilidade :id-disciplina="idDisciplina"
                  :id-avaliacao="idAvaliacao"
                  @avaliacaoHabilidadeCadastrada="cadastroEfetuado = $event"/>
                </v-flex>
            </v-layout>
            <v-container grid-list-md>
                <v-data-table hide-actions
                    :headers="headers" :items="avaliacoesHabilidades" class="elevation-1"
                    >
                        <template slot="items" slot-scope="props">
                          <td>{{ props.item.codigo }}</td>
                          <td width="50%">
                            <v-tooltip bottom>
                                <span slot="activator" class="">{{ props.item.descricao | limitCaracteres(100) }}</span>
                                <span>{{ props.item.descricao }}</span>
                            </v-tooltip>
                          </td>
                          <td>
                            <v-icon v-if="props.item.qtdResultado === 0" medium class="mr-2" @click="excluir(props.item)" >
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
import ModalSelecionaDisciplinaHabilidade from "./ModalSelecionaDisciplinaHabilidade";
import { limitCaracteres } from "@/filters/Filters.js";

export default {
  name: "ModalListaAvaliacaoHabilidade",
  props: {
    idDisciplina: Number
  },
  components: {
    ModalSelecionaDisciplinaHabilidade
  },
  filters: {
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      loading: false,
      idAvaliacao: null,
      cadastroEfetuado: false,
      dialog: false,
      avaliacoesHabilidades: [],
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
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
    EventBus.$on("abrirListaAvaliacaoHabilidade", idAvaliacao => {
      this.idAvaliacao = idAvaliacao;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    excluir(avaliacaoHabilidade) {
      confirm("Você deseja realmente excluir o Habilidade?") &&
        avaliacaoService.excluirAvaliacaoHabilidade(avaliacaoHabilidade).then((dados) => {
          if (dados) {
            this.avaliacoesHabilidades.splice(this.avaliacoesHabilidades.indexOf(avaliacaoHabilidade), 1);
          }
        });
    },
    async pesquisar() {
      if (this.idAvaliacao) {
        this.loading = true;
        this.avaliacoesHabilidades = await avaliacaoService.listarAvaliacoesHabilidades(
          this.idAvaliacao
        );
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.avaliacoesHabilidades = [];
      }, 300);
    }
  }
};
</script>
