<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <modal-visualiza-menoes-e-limites-da-escala></modal-visualiza-menoes-e-limites-da-escala>
        <v-card>
          <v-card-title>
            <span class="headline">Escalas</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout align-center row fill-height justify-space-between>  
                  <v-flex xs12 sm2 md6>
                  <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
                  </v-flex>
                   <v-flex xs12 sm2 md1>
                    <v-btn icon @click="atualizar">
                      <v-icon color="primary">refresh</v-icon>
                    </v-btn>
                  </v-flex>
                  <v-flex xs12 sm2 md5>
                    <modal-seleciona-escala :id-curso="idCurso"
                      @escalaVinculada="cadastroEfetuado = $event"></modal-seleciona-escala>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="escalas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nome }}</td>
                    <td>{{ props.item.nomeTipoAbrangencia }}</td>
                    <td>{{ props.item.nomeTipoAvaliacao }}</td>
                    <td>{{ props.item.dataInicialVigencia }}</td>
                    <td>{{ props.item.dataFinalVigencia }}</td>
                    <td>
                      <v-icon title="Desvincular" medium class="mr-2" @click="desvincular(props.item)" >
                          do_not_disturb_on
                      </v-icon>
                      <v-icon title="Visualizar" medium class="mr-2" @click="visualizar(props.item)" >
                          pageview
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Escala não localizada!
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
</template>
<script>
import cursoService from "@/service/curso/CursoService";
import escalaService from "@/service/instituicao/EscalaService";
import EventBus from "@/utils/EventBus";
import Escala from "@/model/instituicao/Escala";
import CursoEscala from "@/model/curso/CursoEscala";
import Pagination from "@/utils/Pagination.js";
import ModalSelecionaEscala from "./ModalSelecionaEscala";
import ModalVisualizaMenoesELimitesDaEscala from "./ModalVisualizaMenoesELimitesDaEscala";
export default {
  name: "ModalListaEscala",
  components: {
    ModalSelecionaEscala,
    ModalVisualizaMenoesELimitesDaEscala
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      cursoEscala: new CursoEscala(),
      escala: new Escala(),
      escalas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Tipo Abrangência",
          align: "left",
          sortable: false,
          value: "nomeTipoAbrangencia"
        },
        {
          text: "Tipo Avaliação",
          align: "left",
          sortable: false,
          value: "nomeTipoAvaliacao"
        },
        {
          text: "Data Inicial Vigência",
          align: "left",
          sortable: false,
          value: "dataInicialVigencia"
        },
        {
          text: "Data Final Vigência",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
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
    EventBus.$on("abrirEscala", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    visualizar(escala) {
      escala.idCurso = this.idCurso;
      EventBus.$emit("visualizaEscala", escala);
    },
    desvincular(escala) {
      this.cursoEscala.idCurso = this.idCurso;
      this.cursoEscala.idEscala = escala.id;
      confirm("Você deseja realmente desvincular a Escala do Curso?") &&
        cursoService.desvincularCursoEscala(this.cursoEscala).then(() => {
          this.escalas.splice(this.escalas.indexOf(escala), 1);
          this.pesquisar();
        });
    },
    editar(escala) {
      EventBus.$emit("editaEscala", escala.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIds();
      this.loading = true;
      const paginacaoResultado = await escalaService.listarTodos(
        this.escala,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.escalas = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    atualizarIds() {
      this.escala.idCurso = this.idCurso;
      this.escala.idInstituicao = this.$store.getters.user.idInstituicao;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.escala = new Escala();
        this.atualizarIds();
      }, 300);
    }
  }
};
</script>
