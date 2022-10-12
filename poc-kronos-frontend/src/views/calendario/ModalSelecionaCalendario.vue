<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" @click="abrir()"><v-icon>filter_list</v-icon>Selecionar</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Calendário</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-layout align-center wrap fill-height> 
                      <v-flex xs12 sm6 md6>
                        <v-select v-model="calendario.ano" :items="anos" label="Ano"
                        @change="atualizarComboNumero(calendario.ano)"></v-select>
                      </v-flex>
                       <v-flex xs12 sm6 md6>
                        <v-select v-model="calendario.numero" :items="numeros" label="Número"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" label="Descrição" v-model="calendario.descricao"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md3>
                        <v-select v-model="calendario.idTipoSituacaoCalendario" item-text="nome" item-value="id" :items="tiposSituacoesCalendarios" label="Situação Matrícula"></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                      </v-flex>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="calendarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.ano }}</td>
                    <td>{{ props.item.numero }}</td>
                    <td>{{ props.item.descricao }}</td>
                    <td>{{ props.item.nomeTipoSituacaoCalendario }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem informações!
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
import calendarioService from "@/service/calendario/CalendarioService";
import EventBus from "@/utils/EventBus";
import Calendario from "@/model/calendario/Calendario";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaCalendario",
  data() {
    return {
      dialog: false,
      tiposSituacoesCalendarios: [],
      calendario: new Calendario(),
      calendarios: [],
      anos: [],
      numeros: [],
      pagination: new Pagination(1, 5),
      loading: true,
      liberaPesquisa: false,
      headers: [
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "ano"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        },
        {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "nomeTipoSituacaoCalendario"
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
    selecionar(calendario) {
      this.dialog = false;
      EventBus.$emit("selecionaCalendario", calendario);
    },
    async atualizarComboNumero(ano) {
      this.numeros = await calendarioService.comboNumeros(ano);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        const paginacaoResultado = await calendarioService.listarTodos(
          this.calendario,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.calendarios = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    limpar() {
      this.calendario = new Calendario();
      this.numeros = [];
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.tiposSituacoesCalendarios = await calendarioService.tiposSituacoes();
      this.anos = await calendarioService.comboAnos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.calendario = new Calendario();
        this.liberaPesquisa = false;
      }, 300);
    }
  }
};
</script>
