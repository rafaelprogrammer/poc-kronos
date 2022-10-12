<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="abrir()"><v-icon>filter_list</v-icon>{{labelBotaoSelecionar}}</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Matrícula</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center wrap fill-height> 
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" label="Nr Registro" v-model="matricula.numeroRegistroPessoa"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md3>
                        <v-text-field color="primary" label="CPF" v-model="matricula.cpfPessoa"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md5>
                        <v-text-field color="primary" label="Nome" v-model="matricula.nomePessoa"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" label="Ano Início" v-model="matricula.anoInicioCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md6>
                        <v-select v-model="matricula.idCurso" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md3>
                        <v-select v-model="matricula.idTipoSituacaoMatricula" item-text="nome" item-value="id" :items="tiposSituacoesMatriculas" label="Situação Matrícula"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                      </v-flex>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="matriculas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeCurso }}</td>
                    <td>{{ props.item.anoInicioCurso }}</td>
                    <td>{{ props.item.numeroRegistroPessoa }}</td>
                    <td>{{ props.item.cpfPessoa }}</td>
                    <td>{{ props.item.nomePessoa }}</td>
                    <td>{{ props.item.data }}</td>
                    <td>{{ props.item.nomeTipoSituacaoMatricula }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Matrícula não localizada!
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
import cursoService from "@/service/curso/CursoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import Matricula from "@/model/matricula/Matricula";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaMatricula",
  props: {
    labelBotaoSelecionar: {
      type: String,
      default: "Selecionar"
    }
  },
  data() {
    return {
      dialog: false,
      tiposSituacoesMatriculas: [],
      cursos: [],
      matricula: new Matricula(),
      matriculas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      liberaPesquisa: false,
      headers: [
        {
          text: "Curso",
          align: "left",
          sortable: false,
          value: "nomeCurso"
        },
        {
          text: "Ano Início",
          align: "left",
          sortable: false,
          value: "anoInicioCurso"
        },
        {
          text: "Nr Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistroPessoa"
        },
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpfPessoa"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomePessoa"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "nomeTipoSituacaoMatricula"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
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
    EventBus.$on("abrirSelecionaMatricula", () => {
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
    selecionar(matricula) {
      this.dialog = false;
      EventBus.$emit("selecionaMatricula", matricula);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await matriculaService.listarTodos(
          this.matricula,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.matriculas = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    limpar() {
      this.matricula = new Matricula();
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.tiposSituacoesMatriculas = await matriculaService.tiposSituacoesMatriculas();
      this.cursos = await cursoService.combo();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.matricula = new Matricula();
        this.liberaPesquisa = false;
      }, 300);
    }
  }
};
</script>
