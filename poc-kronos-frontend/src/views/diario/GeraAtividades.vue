<template>
  <v-layout wrap>
          <v-flex xs12 sm12 md12 >
            <v-btn color="primary" v-if="visualizaGerar" small left direction="right" alt="Excluir dias não letivos" @click="excluirDiasNaoLetivos()" title="Excluir dias não letivos"><v-icon>settings</v-icon>Excluir dias não letivos</v-btn>
          </v-flex>
          <v-flex xs12 sm12 md6 >
            <v-btn color="primary" v-if="visualizaGerar" small left direction="right" alt="Gerar Atividades" @click="gerarAtividades()" title="Gerar Atividades"><v-icon>settings</v-icon>Gerar Atividades</v-btn>
            <modal-cadastro-atividade @atividadeAlterada="cadastroEfetuado = $event" />
          </v-flex>
          <v-flex xs12 sm12 md10>
          <v-data-table :loading="loading" :total-items="pagination.totalItems" :pagination.sync="pagination"
              :headers="headers" :items="atividades" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td :class="atualizarCssColunaSelecionada(props.item)" v-if="props.item.dataRealizacao">
                      {{ props.item.diaDaSemana }} - {{ atualizarFormatoData(props.item.dataPrevista) }} 
                      - {{ atualizarFormatoData(props.item.dataRealizacao) }} 
                      - <v-icon v-if="props.item.existeAvaliacao" title="Possui Avaliação">local_offer</v-icon>
                      </td>
                    <td :class="atualizarCssColunaSelecionada(props.item)" v-if="!props.item.dataRealizacao">
                      {{ props.item.diaDaSemana }} - {{ atualizarFormatoData(props.item.dataPrevista) }} 
                      - <span class="red--text text--darken-2">Pendente</span> 
                      - <v-icon v-if="props.item.existeAvaliacao" title="Possui Avaliação">local_offer</v-icon>
                      </td>
                    <td :class="atualizarCssColunaSelecionada(props.item)">
                      <v-menu bottom left>
                        <v-btn slot="activator" icon small title="Opções para Atividades" color="primary"><v-icon>more_vert</v-icon></v-btn>
                        <v-list>
                          <v-list-tile alt="Selecionar" @click="selecionarAtividade(props.item)">
                            <v-list-tile-title>Selecionar</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile alt="Criar Avaliação" @click="criarAvaliacao(props.item)"
                          v-if="!props.item.existeAvaliacao">
                            <v-list-tile-title>Criar Avaliação</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile alt="Analisar" @click="confirmar(props.item)">
                            <v-list-tile-title>Confirmar</v-list-tile-title>
                          </v-list-tile>
                          <v-divider></v-divider>
                          <v-list-tile alt="Editar" @click="editar(props.item)">
                            <v-list-tile-title>Editar</v-list-tile-title>
                          </v-list-tile>
                          <v-divider></v-divider>
                          <v-list-tile alt="Visualizar" @click="visualizar(props.item)">
                            <v-list-tile-title>Visualizar</v-list-tile-title>
                          </v-list-tile>
                        </v-list>
                      </v-menu>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem atividades!
                </template>
             </v-data-table>
             </v-flex>
  </v-layout>
</template>
<script>
import Vue from "vue";
import atividadeService from "@/service/atividade/AtividadeService";
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
// import subFaseService from "@/service/curso/SubFaseService";
import Atividade from "@/model/atividade/Atividade";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroAtividade from "./ModalCadastroAtividade";

export default {
  name: "GeraAtividades",
  props: {
    visualizaGerar: {
      type: Boolean,
      default: true
    },
    idTurma: Number,
    idDisciplina: Number,
    idPeriodoExecucao: Number,
    anoTurma: Number
  },
  components: {
    ModalCadastroAtividade
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
    idDisciplina() {
      this.cssColuna = null;
      this.atividades = [];
      this.idSubFaseExecucao = null;
    },
    pagination: { 
      handler() {
        this.pesquisar();
      }
    }
  },
  data() {
    return {
      idAtividadeSelecionada: null,
      cssColuna: null,
      cadastroEfetuado: false,
      idSubFase: null,
      idSubFaseExecucao: null,
      loading: false,
      liberaPesquisa: false,
      atividade: new Atividade(),
      atividades: [],
      pagination: new Pagination(1, 25),
      headers: [
        {
          text: "Atividades",
          align: "left",
          sortable: false,
        },
        { text: "", value: "name", sortable: false },
      ]
    };
  },
  created() {
    EventBus.$on("apresentaAtividades", async (idSubFase, idSubFaseExecucao) => {
      this.idSubFase = idSubFase;
      this.idSubFaseExecucao = idSubFaseExecucao;
      this.liberaPesquisa = true;
      this.pesquisar();
      this.$emit("idSubFaseExecucaoSelecionado", this.idSubFaseExecucao);
    });
    EventBus.$on("atualizaAtividade", async () => {
      this.pesquisar();
    });
  },
  methods: {
    atualizarFormatoData(data) {
      return Vue.moment(data, "DD/MM/YYYY").locale('pt-br').format('DD/MMM');
    },
    excluirDiasNaoLetivos() {
      confirm("Você deseja realmente excluir dias não letivos?") &&
        atividadeService.excluirDiasNaoLetivos(this.idTurma, this.idDisciplina, this.idPeriodoExecucao).then(() => {
           this.atualizar();
        });
    },
    atualizarCssColunaSelecionada(atividade) {
      return this.idAtividadeSelecionada === atividade.id ? this.cssColuna : null;
    },
    selecionarAtividade(atividade) {
      this.idAtividadeSelecionada = atividade.id;
      this.cssColuna = "primary";
      EventBus.$emit("selecionaAtividade", atividade.id, this.idSubFase);
    },
    visualizar(atividade) {
      EventBus.$emit("visualizaAtividade", atividade.id);
    },
    editar(atividade) {
      EventBus.$emit("editaAtividade", atividade.id, this.idDisciplina);
    },
    criarAvaliacao(atividade) {
      avaliacaoService.salvarAutomatica(atividade.id, this.idTurma, this.idDisciplina).then(dados => {
        if (dados) {
          this.atualizar();
        }
      });
    },
    confirmar(atividade) {
      atividade.idDisciplina = this.idDisciplina;
      atividade.anoTurma = this.anoTurma;
      atividadeService.confirmar(atividade).then(dados => {
        if (dados) {
          this.atualizar();
        }
      });
    },
    gerarAtividades() {
      this.atividade.idTurma = this.idTurma;
      this.atividade.idDisciplina = this.idDisciplina;
      this.atividade.idPeriodoExecucao = this.idPeriodoExecucao;
      atividadeService.salvar(this.atividade).then((dados) => {
          if (dados) {
            this.atualizar();
          }
      });
    },
    atualizar() {
      setTimeout(() => {
          this.pesquisar();
      }, 500);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.atividade.idDisciplina = this.idDisciplina;
        this.atividade.idTurma = this.idTurma;
        this.atividade.idSubFaseExecucao = this.idSubFaseExecucao;
        const paginacaoResultado = await atividadeService.listarTodos(
          this.atividade,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.atividades = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
        this.cadastroEfetuado = false;
      }
    }
  }
};
</script>
