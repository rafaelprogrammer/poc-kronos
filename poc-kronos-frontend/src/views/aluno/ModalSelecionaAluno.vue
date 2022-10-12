<template>
      <v-dialog v-model="dialog" persistent width="65%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="abrir()"><v-icon>filter_list</v-icon>{{labelBotaoSelecionar}}</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Aluno</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center wrap fill-height> 
                      <v-flex xs12 sm6 md3>
                        <v-select v-model="idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-select v-model="ano"  :items="anos" label="Ano"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-select v-model="idTurma" item-text="sigla" item-value="id" :items="turmas" label="Turma"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md3>
                        <v-text-field color="primary" label="Nr Registro" v-model="pessoa.numeroRegistro"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" label="CPF" v-model="pessoa.cpf"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md5>
                        <v-text-field color="primary" label="Nome" v-model="pessoa.nome"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                      </v-flex>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="alunos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numeroRegistro }}</td>
                    <td>{{ props.item.cpf }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Alunos não localizados!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import turmaService from "@/service/turma/TurmaService";
import cursoService from "@/service/curso/CursoService";
import pessoaService from "@/service/pessoa/PessoaService";
import Pessoa from "@/model/pessoa/Pessoa";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaAluno",
  props: {
    labelBotaoSelecionar: {
      type: String,
      default: "Selecionar"
    }
  },
  data() {
    return {
      dialog: false,
      cursos: [],
      idCurso: null,
      anos: [],
      ano: null,
      turmas: [],
      idTurma: null,
      alunos: [],
      pessoa: new Pessoa(),
      pagination: new Pagination(1, 5),
      loading: true,
      liberaPesquisa: false,
      headers: [
        {
          text: "Nr Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpf"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    async idCurso() {
      if (this.idCurso) {
        this.anos = await turmaService.comboAnosPorCurso(this.idCurso);
      }
      this.ano = null;
    },
    async ano() {
      if (this.idCurso && this.ano) {
        this.turmas = await turmaService.comboParaOcorrenciaPorIdCursoEAno(this.idCurso, this.ano);
      }
      this.idTurma = null;
    },
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    EventBus.$on("abrirSelecionaAluno", () => {
      this.abrir();
    });
  },
  methods: {
    abrir() {
      this.carregarDadosBasicos();
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    selecionar(aluno) {
      this.dialog = false;
      EventBus.$emit("selecionaAluno", aluno);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await pessoaService.listarTodosAlunos(
          this.pessoa,
          this.idCurso,
          this.idTurma,
          this.ano,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.alunos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    limpar() {
      this.pessoa = new Pessoa();
      this.ano = null;
      this.idCurso = null;
      this.idTurma = null;
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.combo();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.limpar();
      }, 300);
    }
  }
};
</script>
