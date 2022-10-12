<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" alt="Ausentes" @click="abrir()" title="Ausentes"><v-icon>add</v-icon>Ausentes</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Geração Pontos Ausentes</span>
          </v-card-title>
          <modal-visualiza-ponto-tarefa />
          <v-card-text>
            <v-container grid-list-md>
                <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
                    <v-layout align-center row fill-height justify-space-between>
                        <v-flex xs12 sm6 md1>
                          <label>Suprimir:</label>
                        </v-flex>
                        <v-flex xs12 sm6 md2 >
                          <v-checkbox color="primary" label="Sábado" v-model="ausente.sabadoSuprimido"></v-checkbox>
                        </v-flex>
                        <v-flex xs12 sm6 md2 >
                          <v-checkbox color="primary" label="Domingo" v-model="ausente.domingoSuprimido"></v-checkbox>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-btn color="primary" alt="Listar" title="Listar" type="submit"> <v-icon>find_in_page</v-icon>Listar</v-btn>
                        </v-flex>
                  </v-flex>
                  </v-layout>
                </v-form>
              <v-data-table v-model="selected" item-key="dataGeracao" select-all :loading="loading" :headers="headers" :items="ausentes" class="elevation-1">
                <template slot="items" slot-scope="props">
                  <tr :active="props.selected" @click="props.selected = !props.selected">
                    <td>
                      <v-checkbox
                        :input-value="props.selected"
                        primary
                        hide-details
                      ></v-checkbox>
                    </td>
                    <td>{{ props.item.dataGeracao }}</td>
                    <td>{{ props.item.diaDaSemana }}</td>
                    <td>{{ props.item.nomeTipoPeriodoPonto }}</td>
                  </tr>
                </template>
                <template slot="no-data">
                  Não existem informações geradas!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Fechar</v-btn>
            <v-btn color="primary" dark @click.native="gerar" :disabled="selected.length === 0">Gerar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Ausente from "@/model/funcionario/Ausente";
import Funcionario from "@/model/funcionario/Funcionario";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalVisualizaPontoTarefa from "./ModalVisualizaPontoTarefa";

export default {
  name: "ModalCadastraAusentes",
  components: {
    ModalVisualizaPontoTarefa
  },
  props: {
    idFuncionario: Number,
    ano: Number,
    mes: Number
  },
  data() {
    return {
      dialog: false,
      ausente: new Ausente(),
      ausentes: [],
      loading: false,
      liberaPesquisa: false,
      selected: [],
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataGeracao"
        },
        {
          text: "Dia Semana",
          align: "left",
          sortable: false,
          value: "diaDaSemana"
        },
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "nomeTipoPeriodoPonto"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
  },
  created() {
    EventBus.$on("abrirCadastroAusentes", ponto => {
      this.ausente.idFuncionario = ponto.idFuncionario;
      this.ausente.ano = ponto.ano;
      this.ausente.mes = ponto.mes;
      this.abrir();
    });
  },
  methods: {
    abrir() {
      this.ausente.idFuncionario = this.idFuncionario;
      this.ausente.ano = this.ano;
      this.ausente.mes = this.mes;
      this.liberaPesquisa = true;
      this.dialog = true;
    },
    gerar() {
      funcionarioService.gerarPontosAusentes(this.selected).then(dados => {
        if(dados) {
          this.dialog = false;
        }
      });
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.ausentes = await funcionarioService.listarTodosPontosAusentes(
          this.ausente
        );
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.ausente = new Ausente();
        this.liberaPesquisa = false;
        this.selected = [];
        this.ausentes = [];
      }, 300);
    }
  }
};
</script>
