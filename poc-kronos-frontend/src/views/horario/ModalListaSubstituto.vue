<template>
      <v-dialog v-model="dialog" persistent width="50%">
        <v-card>
          <v-card-title>
            <span class="headline">Substitutos</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap justify-space-between>
                  <v-flex xs12 sm6 md1>
                    <v-text-field color="primary" label="Ano" disabled v-model="ano"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md4>
                    <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md1>
                    <v-text-field color="primary" label="Período" disabled v-model="siglaPeriodo"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md1>
                    <v-text-field color="primary" label="Turma" disabled v-model="siglaTurma"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-checkbox disabled color="primary" label="Regular" v-model="horario.regular"></v-checkbox>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-text-field color="primary" label="Data Inicial" disabled v-model="horario.dataInicial"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-text-field color="primary" label="Data Final" disabled v-model="horario.dataFinal"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field color="primary" label="Disciplina" disabled v-model="horario.disciplina"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field color="primary" label="Professor" disabled v-model="horario.professor"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field color="primary" label="Regime Letivo" disabled v-model="horario.nomeTipoRegimeLetivo"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm2 md1>
                    <v-btn icon @click="atualizar">
                      <v-icon color="primary">refresh</v-icon>
                    </v-btn>
                  </v-flex>
                  <v-flex xs12 sm2 md5>
                    <modal-cadastro-substituto :ano="ano" :nome-curso="nomeCurso" :sigla-periodo="siglaPeriodo"
                    :siglaTurma="siglaTurma" :horario="horario" 
                    @substitutoCadastrado="cadastroEfetuado = $event"></modal-cadastro-substituto>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="substitutos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nomeFuncionario }}</td>
                    <td>{{ props.item.dataInicial }}</td>
                    <td>{{ props.item.dataFinal }}</td>
                    <td>
                      <v-icon title="Excluir" medium class="mr-2" @click="excluir(props.item)" >
                          delete
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Substitutos não localizados!
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
import substitutoService from "@/service/horario/SubstitutoService";
import EventBus from "@/utils/EventBus";
import Substituto from "@/model/horario/Substituto";
import DadosExcluiSubstituto from "@/model/horario/DadosExcluiSubstituto";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroSubstituto from "./ModalCadastroSubstituto";
export default {
  name: "ModalListaSubstituto",
  components: {
    ModalCadastroSubstituto
  },
  data() {
    return {
      nomeCurso: "",
      ano: 0,
      siglaPeriodo: "",
      siglaTurma: "",
      horario: {},
      dialog: false,
      cadastroEfetuado: false,
      substituto: new Substituto(),
      substitutos: [],
      dadosExcluiSubstituto: new DadosExcluiSubstituto(),
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Professor",
          align: "left",
          sortable: false,
          value: "nomeFuncionario"
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
    EventBus.$on("abrirSubstituto", (ano, nomeCurso, siglaPeriodo, siglaTurma, horario) => {
      this.ano = ano;
      this.nomeCurso = nomeCurso;
      this.siglaPeriodo = siglaPeriodo;
      this.siglaTurma = siglaTurma;
      this.horario = horario;
      this.substituto.idHorario = horario.id;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    excluir(substituto) {
      this.dadosExcluiSubstituto.idFuncionario = substituto.idFuncionario;
      this.dadosExcluiSubstituto.dataInicialSubstituto = substituto.dataInicial;
      this.dadosExcluiSubstituto.dataFinalSubstituto = substituto.dataFinal;
      this.dadosExcluiSubstituto.idDisciplina = this.horario.idDisciplina;
      this.dadosExcluiSubstituto.idTurma = this.horario.idTurma;
      this.dadosExcluiSubstituto.regular = this.horario.regular;
      this.dadosExcluiSubstituto.idTipoRegimeLetivo = this.horario.idTipoRegimeLetivo;
      this.dadosExcluiSubstituto.dataInicialHorario = this.horario.dataInicial;
      this.dadosExcluiSubstituto.dataFinalHorario = this.horario.dataFinal;
      this.dadosExcluiSubstituto.idFuncionarioHorario = this.horario.idFuncionario;
      confirm("Você deseja realmente excluir o Substituto?") &&
        substitutoService.excluir(this.dadosExcluiSubstituto).then(() => {
          this.substitutos.splice(this.substitutos.indexOf(substituto), 1);
          this.pesquisar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await substitutoService.listarTodos(
        this.substituto,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.substitutos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.substituto = new Substituto();
      }, 300);
    }
  }
};
</script>
