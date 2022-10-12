<template>
<div>
  <modal-cadastro-disciplina-pre-requisito/>
  <section-definition title="Disciplinas" event-update="atualizarDisciplina"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Disciplinas</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"
        @change="atualizarNomeCursoEIdNivel(idCurso)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idPeriodo" item-text="sigla" item-value="id" :items="periodos" label="Período"
        @change="atualizarSiglaPeriodo(idPeriodo)"></v-select>
      </v-flex>
    </v-layout>
    <v-layout wrap  v-if="idPeriodo">
      <v-flex xs12 sm2 md2>
        <v-subheader v-if="idPeriodo">Dados da Disciplina</v-subheader>
      </v-flex>
      <v-flex xs12 sm2 md1>
        <modal-cadastro-disciplina :id-periodo="idPeriodo" :sigla-periodo="siglaPeriodo"
        :nome-curso="nomeCurso" :id-nivel="idNivel" @disciplinaCadastrada="cadastroEfetuado = $event"></modal-cadastro-disciplina>
      </v-flex>
      <v-flex xs12 sm2 md12>
        <v-data-table :loading="loading" :headers="headers" :items="disciplinas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
          <template slot="items" slot-scope="props">
            <td>{{ props.item.codigo }}</td>
            <td>{{ props.item.nome }}</td>
            <td>{{ props.item.cargaHorariaTotal }}</td>
            <td>{{ props.item.nomeTipoDisciplina }}</td>
            <td>{{ props.item.nomeTipoRegimeLetivo }}</td>
            <td>{{ props.item.obrigatoria | booleanFilter }}</td>
            <td>{{ props.item.siglaDisciplinaUnificacao }}</td>
            <td>{{ props.item.ativa | booleanFilter }}</td>
            <td>
              <v-menu offset-y>
                <v-btn slot="activator" title="Opções da disciplina" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                <v-list>
                  <v-list-tile alt="Editar" @click="editar(props.item)">
                    <v-list-tile-title>
                      <v-icon medium class="mr-2">
                        edit
                      </v-icon>
                      Editar
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Excluir" @click="excluir(props.item)">
                    <v-list-tile-title>
                      <v-icon medium class="mr-2">
                        delete
                      </v-icon>
                      Excluir
                    </v-list-tile-title>
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile alt="Pré-Requisitos" v-if="props.item.preRequisitos" @click="abrirCadastroDisciplinaPreRequisito(props.item)">
                    <v-list-tile-title>
                      <v-icon medium class="mr-2">
                        playlist_add_check
                      </v-icon>
                      Pré-requisitos
                    </v-list-tile-title>
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
import periodoService from "@/service/curso/PeriodoService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import Disciplina from "@/model/disciplina/Disciplina";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroDisciplina from "./ModalCadastroDisciplina";
import ModalCadastroDisciplinaPreRequisito from "./ModalCadastroDisciplinaPreRequisito";
import EventBus from "@/utils/EventBus.js";
import { booleanFilter } from "@/filters/Filters.js";

export default {
  name: "GerenciaCadastroDisciplinas",
  components: {
    ModalCadastroDisciplina,
    ModalCadastroDisciplinaPreRequisito
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      idNivel: null,
      dialog: false,
      idPeriodo: null,
      siglaPeriodo: "",
      numeroPeriodo: 0,
      periodos: [],
      idCurso: null,
      nomeCurso: "",
      cursos: [],
      disciplinas: [],
      disciplina: new Disciplina(),
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Carga Horária",
          align: "left",
          sortable: false,
          value: "cargaHorariaTotal"
        },
        {
          text: "Tipo Disciplina",
          align: "left",
          sortable: false,
          value: "nomeTipoDisciplina"
        },
        {
          text: "Regime Letivo",
          align: "left",
          sortable: false,
          value: "nomeTipoRegimeLetivo"
        },
        {
          text: "Obrigatório",
          align: "left",
          sortable: false,
          value: "obrigatoria"
        },
        {
          text: "Disciplina Unificação",
          align: "left",
          sortable: false,
          value: "siglaDisciplinaUnificacao"
        },
        {
          text: "Ativo",
          align: "left",
          sortable: false,
          value: "ativa"
        },
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
    async idCurso() {
      this.periodos = await periodoService.combo(this.idCurso);
      this.idPeriodo = null;
    },
    idPeriodo() {
      this.pesquisar();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
    this.atualizar();
    EventBus.$on("atualizarDisciplina", async () => {
      this.atualizar();
    });
  },
  methods: {
    abrirCadastroDisciplinaPreRequisito(disciplina) {
      EventBus.$emit("abrirCadastroDisciplinaPreRequisito", this.idCurso, this.nomeCurso, this.numeroPeriodo, disciplina);
    },
    atualizarNomeCursoEIdNivel(id) {
      const arrayCurso = this.cursos.filter(c => {
        return c.id === id;
      });
      this.nomeCurso = arrayCurso[0].nome;
      this.idNivel = arrayCurso[0].idNivel;
    },
    atualizarSiglaPeriodo(id) {
      const arrayPeriodo = this.periodos.filter(p => {
        return p.id === id;
      });
      this.siglaPeriodo = arrayPeriodo[0].sigla;
      this.numeroPeriodo = arrayPeriodo[0].numero;
    },
    editar(disciplina) {
      EventBus.$emit("editaDisciplina", disciplina.id);
    },
    excluir(disciplina) {
      confirm("Você deseja realmente excluir o Curso?") &&
        disciplinaService.excluir(disciplina).then(() => {
          this.disciplinas.splice(this.disciplinas.indexOf(disciplina), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.idPeriodo) {
        this.disciplina.idPeriodo = this.idPeriodo;
        this.loading = true;
        const paginacaoResultado = await disciplinaService.listarTodos(
          this.disciplina,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.disciplinas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.combo();
    }
  }
};
</script>
