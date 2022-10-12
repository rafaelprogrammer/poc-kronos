<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Fases (Semestres)</span>
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
                  <v-flex xs12 sm6 md4>
                    <v-select v-model="fase.idPeriodo" @change="carregarFases(fase.idPeriodo)"
                      item-value="id" item-text="sigla" :items="periodos" label="Período"></v-select>
                  </v-flex>
                  <v-flex xs12 sm2 md5 v-show="fase.idPeriodo">
                    <modal-cadastro-fase :id-periodo="periodo.id"
                      :nome-curso="nomeCurso" :sigla-periodo="periodo.sigla"
                      @faseCadastrada="cadastroEfetuado = $event"></modal-cadastro-fase>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="fases" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeTipoPeriodo }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>{{ props.item.sigla }}</td>
                    <td>{{ props.item.numero }}</td>
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
                    Fase não localizada!
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
import faseService from "@/service/curso/FaseService";
import EventBus from "@/utils/EventBus";
import Fase from "@/model/curso/fase/Fase";
import Periodo from "@/model/curso/periodo/Periodo";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroFase from "./ModalCadastroFase";
export default {
  name: "ModalListaFase",
  components: {
    ModalCadastroFase
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      fase: new Fase(),
      periodo: new Periodo(),
      fases: [],
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
    EventBus.$on("abrirFase", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.carregarPeriodos(idCurso);
    });
  },
  methods: {
    async carregarFases(idPeriodo) {
      this.periodo = await periodoService.editar(idPeriodo);
      this.fase.idPeriodo = idPeriodo;
      this.pesquisar();
    },
    async carregarPeriodos(idCurso) {
      this.periodo.idCurso = idCurso;
      this.periodos = await periodoService.combo(idCurso);
    },
    excluir(fase) {
      confirm("Você deseja realmente excluir a Fase?") &&
        faseService.excluir(fase).then(() => {
          this.fases.splice(this.fases.indexOf(fase), 1);
          this.pesquisar();
        });
    },
    editar(fase) {
      EventBus.$emit("editaFase", fase.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.fase.idPeriodo) {
        this.loading = true;
        const paginacaoResultado = await faseService.listarTodos(
          this.fase,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.fases = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
      }
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.fase = new Fase();
        this.fases = [];
        this.periodo = new Periodo();
        this.periodos = [];
      }, 300);
    }
  }
};
</script>
