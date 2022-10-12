<template>
<div>
  <modal-cadastro-habilidade :desabilita-incluir="true" :niveis="niveis" />
  <v-layout wrap>
    <v-flex xs12 sm2 md3>
      <modal-cadastra-atividade-disciplina-habilidade :id-disciplina="idDisciplina" :id-atividade="idAtividade"
      :id-sub-fase="idSubFase" :id-curso="idCurso" :id-periodo="idPeriodo"
      @atividadeDisciplinaHabilidadeCadastrada="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm6 md3>
      <v-btn color="primary" alt="Adicionar avaliação de desempenho" v-show="podeAddAvaliacaoDeDesempenho" @click="criarAvaliacao()" title="Adicionar avaliação de desempenho"> <v-icon>add</v-icon>Avaliação desempenho</v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :headers="headers" hide-actions :items="atividadesDisciplinasHabilidades" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.codigoHabilidade }}</td>
          <td>{{ props.item.bncc | booleanFilter}}</td>
          <td>{{ props.item.siglaSubFase }}</td>
          <td width="50%">
            <v-tooltip bottom>
                <span slot="activator" class="">{{ props.item.descricaoHabilidade | limitCaracteres(100) }}</span>
                <span>{{ props.item.descricaoHabilidade }}</span>
            </v-tooltip>
          </td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" small title="Opções para habilidades" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Analisar" @click="excluir(props.item)">
                  <v-list-tile-title>Excluir</v-list-tile-title>
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
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</div>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import atividadeService from "@/service/atividade/AtividadeService";
import AtividadeDisciplinaHabilidade from "@/model/atividade/habilidade/AtividadeDisciplinaHabilidade";
import ModalCadastraAtividadeDisciplinaHabilidade from "./ModalCadastraAtividadeDisciplinaHabilidade";
import EventBus from "@/utils/EventBus";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import ModalCadastroHabilidade from "../estruturapedagogica/ModalCadastroHabilidade";

export default {
  name: "ListaAtividadeDisciplinaHabilidade",
  props: {
    idDisciplina: Number,
    idTurma: Number,
    idAtividade: Number,
    idSubFase: Number,
    idPeriodo: Number,
    idCurso: Number
  },
  components: {
    ModalCadastraAtividadeDisciplinaHabilidade,
    ModalCadastroHabilidade
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      niveis: [],
      dialog: false,
      atividadesDisciplinasHabilidades: [],
      loading: true,
      cadastroEfetuado: false,
      podeAddAvaliacaoDeDesempenho: false,
      atividadeDisciplinaHabilidade: new AtividadeDisciplinaHabilidade(),
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigoDireito"
        },
        {
          text: "Bncc",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Bimestre",
          align: "left",
          sortable: false,
          value: "siglaSubFase"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricaoDireito"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    idAtividade() {
      this.atualizar();
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.atualizar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    }
  },
  created () {
    this.atualizar();
  },
  methods: {
    async visualizar(atividadeDisciplinaHabilidade) {
      this.niveis = await baseCurricularService.comboNiveis();
      EventBus.$emit("visualizaHabilidade", atividadeDisciplinaHabilidade.idHabilidade);
    },
    excluir(atividadeDisciplinaHabilidade) {
      confirm("Você deseja realmente excluir Habilidade?") &&
        atividadeService.excluirAtividadeDisciplinaHabilidade(atividadeDisciplinaHabilidade).then(() => {
          this.atividadesDisciplinasHabilidades.splice(
            this.atividadesDisciplinasHabilidades.indexOf(atividadeDisciplinaHabilidade),
            1
          );
          this.atualizar();
        });
    },
    atualizar() {
      this.verificarSePodeAddAvaliacaoDeDesempenho();
      this.pesquisar();
    },
    async verificarSePodeAddAvaliacaoDeDesempenho() {
      this.podeAddAvaliacaoDeDesempenho = await atividadeService.verificarSePodeAddAvaliacaoDeDesempenho(
        this.idAtividade
      );
    },
    criarAvaliacao() {
      avaliacaoService
        .salvarAutomatica(this.idAtividade, this.idTurma, this.idDisciplina)
        .then(dados => {
          if (dados) {
            this.pesquisar();
            this.podeAddAvaliacaoDeDesempenho = false;
            EventBus.$emit("atualizaAtividade");
          }
      });
    },
    async pesquisar() {
      this.loading = true;
      this.atividadeDisciplinaHabilidade.idAtividade = this.idAtividade;
      this.atividadesDisciplinasHabilidades = await atividadeService.listarAtividadesDisciplinasHabilidades(
        this.atividadeDisciplinaHabilidade.idAtividade
      );
      this.loading = false;
    }
  }
};
</script>
