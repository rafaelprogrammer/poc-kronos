<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Períodos(Anos)</span>
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
                    <modal-cadastro-periodo :id-curso="this.periodo.idCurso" :id-nivel="idNivel" :nome-curso="nomeCurso"
                      @periodoCadastrado="cadastroEfetuado = $event"></modal-cadastro-periodo>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="periodos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoPeriodo }}</td>
                    <td>{{ props.item.nomeFaixaAno }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>{{ props.item.sigla }}</td>
                    <td>{{ props.item.numero }}</td>
                    <td>{{ props.item.valor }}</td>
                    <td>
                      <v-icon title="Editar" medium class="mr-2" @click="editar(props.item)">
                        edit
                      </v-icon>
                      <v-icon title="Excluir" medium class="mr-2" @click="excluir(props.item)" >
                          delete
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Período não localizado!
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
import periodoService from "@/service/curso/PeriodoService";
import EventBus from "@/utils/EventBus";
import Periodo from "@/model/curso/periodo/Periodo";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroPeriodo from "./ModalCadastroPeriodo";
export default {
  name: "ModalListaPeriodo",
  components: {
    ModalCadastroPeriodo
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      idNivel: null,
      dialog: false,
      cadastroEfetuado: false,
      periodo: new Periodo(),
      periodos: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "nomeTipoPeriodo"
        },
        {
          text: "Faixa Ano",
          align: "left",
          sortable: false,
          value: "nomeFaixaAno"
        },
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
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valor"
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
    EventBus.$on("abrirPeriodo", (idCurso, nomeCurso, idNivel) => {
      this.idCurso = idCurso;
      this.idNivel = idNivel
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    excluir(periodo) {
      confirm("Você deseja realmente excluir o Período?") &&
        periodoService.excluir(periodo).then(() => {
          this.periodos.splice(this.periodos.indexOf(periodo), 1);
          this.pesquisar();
        });
    },
    editar(periodo) {
      EventBus.$emit("editaPeriodo", periodo.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.atualizarIdCurso();
      this.loading = true;
      const paginacaoResultado = await periodoService.listarTodos(
        this.periodo,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.periodos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    atualizarIdCurso() {
      this.periodo.idCurso = this.idCurso;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.periodo = new Periodo();
        this.atualizarIdCurso();
      }, 300);
    }
  }
};
</script>
