<template>
<div>
  <section-definition title="Avaliação" event-update="atualizar"></section-definition>
  <modal-cadastra-avaliado :id-disciplina="idDisciplina" :id-turma="idTurma" />
  <modal-lista-grupo-avaliacao />
  <modal-lista-avaliacao-habilidade :id-disciplina="idDisciplina"/>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Avaliação</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
       <v-flex xs12 sm6 md4>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idPeriodoExecucao" :items="periodosExecucoes" item-text="nome" item-value="id" label="Período"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2>
        <v-select v-model="idTurma" @change="atualizarSiglaTurma(idTurma)" :items="turmas" item-text="sigla" item-value="id" label="Turma"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md4>
        <v-select v-model="idDisciplina" @change="atualizarSiglaDisciplina(idDisciplina)" item-text="sigla" item-value="id" :items="disciplinas"
        label="Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md2 v-if="idDisciplina">
        <modal-cadastro-avaliacao :id-turma="idTurma" :sigla-turma="siglaTurma"
        :id-disciplina="idDisciplina" :sigla-disciplina="siglaDisciplina" @avaliacaoCadastrada="cadastroEfetuado = $event" />
      </v-flex>
      <v-flex xs12 sm6 md1 v-if="idDisciplina">
        <v-btn icon @click="atualizar" small>
          <v-icon color="primary">refresh</v-icon>
        </v-btn>
      </v-flex>
    </v-layout>
    <v-layout wrap v-if="idDisciplina">
      <v-flex xs12 sm2 md12>
          <v-data-table :loading="loading" :headers="headers" :items="avaliacoes" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
            <template slot="items" slot-scope="props">
              <td>{{ props.item.dataPrevista }}</td>
              <td>{{ props.item.horaInicio }}</td>
              <td>{{ props.item.tempoDuracao }}</td>
              <td>{{ props.item.nomeTipoAbrangencia }}</td>
              <td>{{ props.item.nomeTipoAvaliacao }}</td>
              <td>{{ props.item.nomeTipoFormato }}</td>
              <td>{{ props.item.grupo | booleanFilter }}</td>
              <td>{{ props.item.peso }}</td>
              <td>{{ props.item.anulada | booleanFilter }}</td>
              <td>
                <v-menu offset-y>
                  <v-btn slot="activator" title="Opções para criação da estrutura do curso" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                  <v-list>
                    <v-list-tile alt="Editar" @click="editar(props.item)">
                      <v-list-tile-title>Editar</v-list-tile-title>
                    </v-list-tile>
                    <v-divider></v-divider>
                    <v-list-tile alt="Excluir" @click="excluir(props.item)">
                      <v-list-tile-title>Excluir</v-list-tile-title>
                    </v-list-tile>
                    <v-divider></v-divider>
                    <v-list-tile alt="Grupos Avaliações" v-if="props.item.grupo" @click="abrirGruposAvaliados(props.item)">
                      <v-list-tile-title>Grupos</v-list-tile-title>
                    </v-list-tile>
                    <v-divider></v-divider>
                    <v-list-tile alt="Avaliações Habilidades" @click="abrirAvaliacoesHabilidades(props.item)">
                      <v-list-tile-title>Habilidades</v-list-tile-title>
                    </v-list-tile>
                    <v-divider></v-divider>
                    <v-list-tile alt="Avaliados" @click="abrirAvaliados(props.item)">
                      <v-list-tile-title>Avaliados</v-list-tile-title>
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
  </v-container>
</div>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import cursoService from "@/service/curso/CursoService";
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import turmaService from "@/service/turma/TurmaService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroAvaliacao from "./ModalCadastroAvaliacao";
import ModalCadastraAvaliado from "./ModalCadastraAvaliado";
import EventBus from "@/utils/EventBus.js";
import { booleanFilter } from "@/filters/Filters.js";
import ModalListaGrupoAvaliacao from "./ModalListaGrupoAvaliacao";
import ModalListaAvaliacaoHabilidade from "./ModalListaAvaliacaoHabilidade";

export default {
  name: "GerenciaCadastroAvaliacao",
  components: {
    ModalCadastroAvaliacao,
    ModalCadastraAvaliado,
    ModalListaGrupoAvaliacao,
    ModalListaAvaliacaoHabilidade
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      pagination: new Pagination(1, 5),
      loading: true,
      idCurso: null,
      cursos: [],
      idPeriodoExecucao: null,
      periodosExecucoes: [],
      idDisciplina: null,
      siglaDisciplina: null,
      disciplinas: [],
      idTurma: null,
      siglaTurma: null,
      turmas: [],
      avaliacoes: [],
      cadastroEfetuado: false,
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataPrevista"
        },
        {
          text: "Hora/Início",
          align: "left",
          sortable: false,
          value: "horaInicio"
        },
        {
          text: "Duração",
          align: "left",
          sortable: false,
          value: "tempoDuracao"
        },
        {
          text: "Abrangência",
          align: "left",
          sortable: false,
          value: "nomeTipoAbrangencia"
        },
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoAvaliacao"
        },
        {
          text: "Formato",
          align: "left",
          sortable: false,
          value: "nomeTipoFormato"
        },
        {
          text: "Grupo",
          align: "left",
          sortable: false,
          value: "grupo"
        },
        {
          text: "Peso",
          align: "left",
          sortable: false,
          value: "peso"
        },
        {
          text: "Anulada",
          align: "left",
          sortable: false,
          value: "anulada"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    async idCurso() {
      if (this.idCurso) {
        this.periodosExecucoes = await periodoExecucaoService.comboParaModulosDosProfessores(this.idCurso);
      }
      this.idPeriodoExecucao = null;
      this.idTurma = null;
      this.idDisciplina = null;
      this.idAtividade = null;
    },
    async idPeriodoExecucao() {
      if (this.idPeriodoExecucao) {
        this.turmas = await turmaService.comboParaModulosDosProfessores(this.idPeriodoExecucao);
        this.idTurma = null;
        this.idDisciplina = null;
        this.idAtividade = null;
      }
    },
    async idTurma() {
      if (this.idTurma) {
        this.disciplinas = await disciplinaService.comboParaModulosDosProfessores(this.idTurma);
        this.idDisciplina = null;
        this.idAtividade = null;
      }
    },
    idDisciplina() {
      this.atualizar();
    },
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
    }
  },
  created() {
    this.carregarDadosBasicos();
    //  EventBus.$on("selecionaAtividade", async (idAtividade) => {
    //   this.idAtividade = idAtividade;
    // });
  },
  methods: {
    abrirAvaliacoesHabilidades(avaliacao) {
      console.log(avaliacao);
      EventBus.$emit("abrirListaAvaliacaoHabilidade", avaliacao.id);
    },
    abrirGruposAvaliados(avaliacao) {
      EventBus.$emit("abrirListaGrupoAvaliacao", avaliacao.id);
    },
    abrirAvaliados(avaliacao) {
      EventBus.$emit("abrirAvaliados", avaliacao.id, avaliacao.grupo, this.anoTurma);
    },
    excluir(avaliacao) {
      confirm("Você deseja realmente excluir a Avaliação?") &&
        avaliacaoService.excluir(avaliacao).then(() => {
          this.avaliacoes.splice(this.avaliacoes.indexOf(avaliacao), 1);
          this.pesquisar();
        });
    },
    editar(avaliacao) {
      EventBus.$emit("editaAvaliacao", avaliacao.id);
    },
    atualizarSiglaTurma(id) {
      const arrayTurma = this.turmas.filter(t => {
        return t.id === id;
      });
      this.siglaTurma = arrayTurma[0].sigla;
      this.anoTurma = arrayTurma[0].ano;
    },
    atualizarSiglaDisciplina(id) {
      const arrayDisciplina = this.disciplinas.filter(t => {
        return t.id === id;
      });
      this.siglaDisciplina = arrayDisciplina[0].sigla;
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.comboParaModulosDosProfessores();
    },
    atualizar() {
      setTimeout(() => {
        this.pesquisar();
        this.cadastroEfetuado = false;
      }, 500);
    },
    async pesquisar() {
      if (this.idDisciplina) {
        this.loading = true;
        const paginacaoResultado = await avaliacaoService.listarTodos(
          this.idTurma,
          this.idDisciplina,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.avaliacoes = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
