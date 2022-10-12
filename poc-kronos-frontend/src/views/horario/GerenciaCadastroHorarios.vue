<template>
<div>
  <modal-lista-substituto />
  <section-definition title="Horários" event-update="atualizarHorario"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Horários Regular</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="ano" :items="anos" label="Ano"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"
        @change="atualizarNomeCurso(idCurso)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idPeriodo" item-text="sigla" item-value="id" :items="periodos" label="Período"
        @change="atualizarSiglaPeriodo(idPeriodo)"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idTurma" item-text="sigla" item-value="id" :items="turmas" label="Turma"
        @change="atualizarSiglaTurma(idTurma)"></v-select>
      </v-flex>
    </v-layout>
    <v-layout wrap  v-if="idTurma">
      <v-flex xs12 sm2 md2>
        <v-subheader v-if="idTurma">Dados do Horário</v-subheader>
      </v-flex>
      <v-flex xs12 sm2 md2>
        <modal-cadastro-horario :ano="ano" :nome-curso="nomeCurso"
        :id-periodo-execucao="idPeriodo" :sigla-periodo="siglaPeriodo" :id-turma="idTurma"
        :id-tipo-matriz-horario="idTipoMatrizHorario"
        :sigla-turma="siglaTurma" @horarioCadastrado="cadastroEfetuado = $event"/>
      </v-flex>
      <v-flex xs12 sm2 md12>
        <v-data-table :loading="loading" item-key="disciplina + horaInicial" :headers="headers" :items="horarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
          <template slot="items" slot-scope="props">
            <td>{{ props.item.diaSemana }}</td>
            <td>{{ props.item.regular }}</td>
            <td>{{ props.item.dataInicial }}</td>
            <td>{{ props.item.dataFinal }}</td>
            <td>{{ props.item.horaInicial }}</td>
            <td>{{ props.item.horaFinal }}</td>
            <td>{{ props.item.professor }}</td>
            <td>{{ props.item.disciplina }}</td>
            <td>
              <v-menu offset-y>
                <v-btn slot="activator" title="Opções do Horário" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
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
                  <v-list-tile alt="Substituição" @click="abrirSubstituto(props.item)">
                    <v-list-tile-title>
                      <v-icon medium class="mr-2">
                        find_replace
                      </v-icon>
                      Substituição
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
import turmaService from "@/service/turma/TurmaService";
import horarioService from "@/service/horario/HorarioService";
import Horario from "@/model/horario/Horario";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroHorario from "./ModalCadastroHorario";
import ModalListaSubstituto from "./ModalListaSubstituto";
import EventBus from "@/utils/EventBus.js";
export default {
  name: "GerenciaCadastroHorarios",
  components: {
    ModalCadastroHorario,
    ModalListaSubstituto
  },
  data() {
    return {
      anos: [],
      ano: null,
      dialog: false,
      idTurma: null,
      siglaTurma: null,
      idPeriodo: null,
      siglaPeriodo: "",
      numeroPeriodo: 0,
      periodos: [],
      idCurso: null,
      nomeCurso: "",
      idTipoMatrizHorario: null,
      cursos: [],
      turmas: [],
      horarios: [],
      horario: new Horario(),
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      headers: [
        {
          text: "Dia Semana",
          align: "left",
          sortable: false,
          value: "diaSemana"
        },
        {
          text: "Regular",
          align: "left",
          sortable: false,
          value: "regular"
        },
        {
          text: "Data Inicial",
          align: "left",
          sortable: false,
          value: "dataInicial"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "dataFinal"
        },
        {
          text: "Hora Início",
          align: "left",
          sortable: false,
          value: "horaInicial"
        },
        {
          text: "Hora Final",
          align: "left",
          sortable: false,
          value: "horaFinal"
        },
        {
          text: "Professor",
          align: "left",
          sortable: false,
          value: "professor"
        },
        {
          text: "Disciplina",
          align: "left",
          sortable: false,
          value: "disciplina"
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
    async ano() {
      if (this.ano && this.ano > 0) {
        this.cursos = await cursoService.comboCursoHorarioPorAno(this.ano);
      }
      this.idCurso = null;
    },
    async idCurso() {
      if (this.ano && this.idCurso) {
        this.periodos = await periodoService.comboPeriodoHorario(this.idCurso, this.ano);
      }
      this.idPeriodo = null;
    },
    async idPeriodo() {
      if (this.idPeriodo) {
        this.turmas = await turmaService.comboTurmaHorario(this.idPeriodo);
      }
      this.idTurma = null;
    },
    idTurma() {
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
    EventBus.$on("atualizarHorario", async () => {
      this.atualizar();
    });
  },
  methods: {
    abrirSubstituto(horario) {
      EventBus.$emit(
        "abrirSubstituto",
        this.ano,
        this.nomeCurso,
        this.siglaPeriodo,
        this.siglaTurma,
        horario
      );
    },
    atualizarSiglaTurma(id) {
      const arrayTurma = this.turmas.filter(t => {
        return t.id === id;
      });
      this.siglaTurma = arrayTurma[0].sigla;
    },
    atualizarNomeCurso(id) {
      const arrayCurso = this.cursos.filter(c => {
        return c.id === id;
      });
      this.nomeCurso = arrayCurso[0].nome;
      this.idTipoMatrizHorario = arrayCurso[0].idTipoMatrizHorario;
    },
    atualizarSiglaPeriodo(id) {
      const arrayPeriodo = this.periodos.filter(p => {
        return p.id === id;
      });
      this.siglaPeriodo = arrayPeriodo[0].sigla;
      this.numeroPeriodo = arrayPeriodo[0].numero;
    },
    editar(horario) {
      EventBus.$emit("editaHorario", horario);
    },
    excluir(horario) {
      confirm("Você deseja realmente excluir o Horário?") &&
        horarioService.excluir(horario).then(() => {
          this.horarios.splice(this.horarios.indexOf(horario), 1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.idTurma) {
        this.loading = true;
        this.horario.idTurma = this.idTurma;
        const paginacaoResultado = await horarioService.listarTodos(
          this.horario,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.horarios = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    async carregarDadosBasicos() {
      this.anos = await turmaService.comboAnosHorario();
    }
  }
};
</script>
