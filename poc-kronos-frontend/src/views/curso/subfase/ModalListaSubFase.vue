<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Sub-Fases (Bimestres)</span>
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
                  <v-flex xs12 sm6 md4>
                    <v-select v-model="subFase.idFase" @change="carregarSubFases(subFase.idFase)"
                      item-value="id" item-text="sigla" :items="fases" label="Fases"></v-select>
                  </v-flex>
                  <v-flex xs12 sm2 md5 v-show="subFase.idFase">
                    <modal-cadastro-sub-fase :id-periodo="periodo.id"
                      :nome-curso="nomeCurso" :sigla-periodo="periodo.sigla"
                      :sigla-fase="fase.sigla" :id-fase="fase.id"
                      @subFaseCadastrada="cadastroEfetuado = $event"></modal-cadastro-sub-fase>
                  </v-flex>
              </v-layout>
              <v-data-table  :loading="loading" :headers="headers" :items="subFases" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
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
                    Sub-Fases não localizadas!
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
import subFaseService from "@/service/curso/SubFaseService";
import EventBus from "@/utils/EventBus";
import Fase from "@/model/curso/fase/Fase";
import SubFase from "@/model/curso/subfase/SubFase";
import Periodo from "@/model/curso/periodo/Periodo";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroSubFase from "./ModalCadastroSubFase";
export default {
  name: "ModalListaSubFase",
  components: {
    ModalCadastroSubFase
  },
  data() {
    return {
      idCurso: 0,
      nomeCurso: "",
      dialog: false,
      cadastroEfetuado: false,
      subFase: new SubFase(),
      fase: new Fase(),
      periodo: new Periodo(),
      fases: [],
      periodos: [],
      subFases: [],
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
    EventBus.$on("abrirSubFase", (idCurso, nomeCurso) => {
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.dialog = true;
      this.carregarPeriodos(idCurso);
    });
  },
  methods: {
    async carregarSubFases(idFase) {
      this.fase = await faseService.editar(idFase);
      this.subFase.idFase = idFase;
      this.pesquisar();
    },
    async carregarFases(idPeriodo) {
      this.subFases = [];
      this.periodo = await periodoService.editar(idPeriodo);
      this.fase.idPeriodo = idPeriodo;
      this.fases = await faseService.combo(idPeriodo);
    },
    async carregarPeriodos(idCurso) {
      this.periodo.idCurso = idCurso;
      this.periodos = await periodoService.combo(idCurso);
    },
    excluir(subFase) {
      confirm("Você deseja realmente excluir a Sub-Fase?") &&
        subFaseService.excluir(subFase).then(() => {
          this.subFases.splice(this.subFases.indexOf(subFase), 1);
          this.pesquisar();
        });
    },
    editar(subFase) {
      EventBus.$emit("editaSubFase", subFase.id);
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.subFase.idFase) {
        this.loading = true;
        const paginacaoResultado = await subFaseService.listarTodos(
          this.subFase,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.subFases = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
      }
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.subFase = new SubFase();
        this.subFases = [];
        this.fase = new Fase();
        this.fases = [];
        this.periodo = new Periodo();
        this.periodos = [];
      }, 300);
    }
  }
};
</script>
