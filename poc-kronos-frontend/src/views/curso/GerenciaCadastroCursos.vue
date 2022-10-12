<template>
<div>
  <modal-lista-periodo></modal-lista-periodo>
  <modal-lista-fase></modal-lista-fase>
  <modal-lista-sub-fase></modal-lista-sub-fase>
  <modal-lista-portaria></modal-lista-portaria>
  <modal-lista-escala></modal-lista-escala>
  <modal-lista-curso-documento></modal-lista-curso-documento>
  <modal-lista-curso-funcionario></modal-lista-curso-funcionario>
  <modal-visualiza-estrutura></modal-visualiza-estrutura>
  <section-definition title="Cursos" event-update="atualizarCurso"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Cursos</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-checkbox type="checkbox" label="Mantenedora" disabled v-model="this.$store.getters.user.instituicaoMantenedora"></v-checkbox>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-checkbox type="checkbox" label="Ativa" disabled v-model="this.$store.getters.user.instituicaoAtiva"></v-checkbox>
      </v-flex>
    </v-layout>
    <v-subheader>Dados do Curso</v-subheader>
    <v-layout wrap>
      <v-flex xs12 sm2 md2>
        <modal-cadastro-curso @cursoCadastrado="cadastroEfetuado = $event"></modal-cadastro-curso>
      </v-flex>
      <v-flex xs12 sm2 md12>
        <v-data-table :loading="loading" :headers="headers" :items="cursos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
          <template slot="items" slot-scope="props">
            <td>{{ props.item.nome }}</td>
            <td>{{ props.item.sigla }}</td>
            <td>{{ props.item.nomeTipoTurno }}</td>
            <td>{{ props.item.nomeTipoRegimeLetivo }}</td>
            <td>{{ props.item.nomeTipoMatrizHorario }}</td>
            <td>
              <v-icon title="Editar Curso" medium class="mr-2" @click="editar(props.item)">
                edit
              </v-icon>
                <v-icon title="Excluir Curso" medium class="mr-2" @click="excluir(props.item)" >
                  delete
                </v-icon>
            </td>
            <td>
              <v-menu offset-y>
                <v-btn slot="activator" title="Opções para criação da estrutura do curso" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                <v-list>
                  <v-list-tile alt="Períodos" @click="abrirPeriodo(props.item)">
                    <v-list-tile-title>Períodos</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Fases" @click="abrirFase(props.item)">
                    <v-list-tile-title>Fases</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="SubFases" @click="abrirSubFase(props.item)">
                    <v-list-tile-title>Sub-Fases</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Portarias" @click="abrirPortaria(props.item)">
                    <v-list-tile-title>Portarias</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Escalas" @click="abrirEscala(props.item)">
                    <v-list-tile-title>Escalas</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Documentos Matrícula" @click="abrirCursoDocumento(props.item)">
                    <v-list-tile-title>Documentos Matrícula</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Funções" @click="abrirCursoFuncionario(props.item)">
                    <v-list-tile-title>Funções</v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Estrutura" @click="abrirEstrutura(props.item)">
                    <v-list-tile-title>Estrutura</v-list-tile-title>
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
import cursoService from "@/service/curso/CursoService";
import Curso from "@/model/curso/Curso";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroCurso from "./ModalCadastroCurso";
import ModalListaPeriodo from "./periodo/ModalListaPeriodo";
import ModalListaFase from "./fase/ModalListaFase";
import ModalListaSubFase from "./subfase/ModalListaSubFase";
import ModalListaPortaria from "./portaria/ModalListaPortaria";
import ModalListaEscala from "@/views/instituicao/ModalListaEscala";
import ModalListaCursoDocumento from "@/views/curso/curso-documento/ModalListaCursoDocumento";
import ModalListaCursoFuncionario from "@/views/curso/curso-funcionario/ModalListaCursoFuncionario";
import ModalVisualizaEstrutura from "@/views/curso/estrutura/ModalVisualizaEstrutura";
import EventBus from "@/utils/EventBus.js";
export default {
  name: "GerenciaCadastroCursos",
  components: {
    ModalCadastroCurso,
    ModalListaPeriodo,
    ModalListaFase,
    ModalListaSubFase,
    ModalListaPortaria,
    ModalListaEscala,
    ModalListaCursoDocumento,
    ModalListaCursoFuncionario,
    ModalVisualizaEstrutura
  },
  data() {
    return {
      dialog: false,
      cursos: [],
      curso: {},
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Sigla",
          align: "left",
          sortable: false,
          value: "sigla"
        },
        {
          text: "Turno",
          align: "left",
          sortable: false,
          value: "nomeTipoTurno"
        },
        {
          text: "Regime Letivo",
          align: "left",
          sortable: false,
          value: "nomeTipoRegimeLetivo"
        },
        {
          text: "Matriz Horário",
          align: "left",
          sortable: false,
          value: "nomeTipoMatrizHorario"
        },
        { text: "", value: "name", sortable: false },
        { text: "", value: "name", sortable: false }
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
    this.curso = Object.assign({}, Curso);
    this.atualizar();
    EventBus.$on("atualizarCurso", async () => {
      this.atualizar();
    });
  },
  methods: {
    abrirEstrutura(curso) {
      EventBus.$emit("visualizaEstrutura", curso.id, curso.nome);
    },
    abrirCursoFuncionario(curso) {
      EventBus.$emit("abrirCursoFuncionario", curso.id, curso.nome);
    },
    abrirCursoDocumento(curso) {
      EventBus.$emit("abrirCursoDocumento", curso.id, curso.nome);
    },
    abrirEscala(curso) {
      EventBus.$emit("abrirEscala", curso.id, curso.nome);
    },
    abrirPortaria(curso) {
      EventBus.$emit("abrirPortaria", curso.id, curso.nome);
    },
    abrirSubFase(curso) {
      EventBus.$emit("abrirSubFase", curso.id, curso.nome);
    },
    abrirFase(curso) {
      EventBus.$emit("abrirFase", curso.id, curso.nome);
    },
    abrirPeriodo(curso) {
      EventBus.$emit("abrirPeriodo", curso.id, curso.nome, curso.idNivel);
    },
    editar(curso) {
      EventBus.$emit("editaCurso", curso.id);
    },
    excluir(curso) {
      confirm("Você deseja realmente excluir o Curso?") &&
        cursoService.excluir(curso).then(() => {
          this.cursos.splice(this.cursos.indexOf(curso), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await cursoService.listarTodos(
        this.curso,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.cursos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    }
  }
};
</script>
