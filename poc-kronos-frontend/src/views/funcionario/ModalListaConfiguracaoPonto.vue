<template>
      <v-dialog v-model="dialog" persistent fullscreen>
        <v-card>
          <v-card-title>
            <span class="headline">Configuração de Ponto de Funcionário</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout align-center row fill-height justify-space-between>
                <v-flex xs12 sm6 md3>
                  <v-text-field color="primary" label="Nr Registro" v-model="funcionario.numeroRegistro" disabled></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md3>
                  <v-text-field color="primary" label="CPF" v-model="funcionario.cpf"  disabled></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-text-field color="primary" label="Nome" v-model="funcionario.nome" disabled></v-text-field>
                </v-flex>
                <v-flex xs12 sm2 md3>
                    <modal-cadastro-configuracao-ponto :id-funcionario="funcionario.id"
                      @configuracaoPontoCadastrada="cadastroEfetuado = $event"/>
                </v-flex>
                <v-flex xs12 sm2 md1>
                    <v-btn icon @click="atualizar">
                      <v-icon color="primary">refresh</v-icon>
                    </v-btn>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="configuracoesPonto" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.dataInicialVigencia }}</td>
                    <td>{{ props.item.dataFinalVigencia }}</td>
                    <td>{{ props.item.cargaHorariaSemanal }}</td>
                    <td>{{ props.item.tarefaOnline | booleanFilter }}</td>
                    <td>{{ props.item.horaInicialP1 }}</td>
                    <td>{{ props.item.horaFinalP1 }}</td>
                    <td>{{ props.item.horaInicialP2 }}</td>
                    <td>{{ props.item.horaFinalP2 }}</td>
                    <td>{{ props.item.horaInicialP3 }}</td>
                    <td>{{ props.item.horaFinalP3 }}</td>
                    <td>
                      <v-icon title="Editar" medium class="mr-2" @click="editar(props.item)">
                        edit
                      </v-icon>
                      <v-icon title="Excluir" v-if="props.item.qtdPonto === 0" medium class="mr-2" @click="excluir(props.item)" >
                          delete
                      </v-icon>
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
            <v-btn color="primary" dark @click.native="close">Fechar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import EventBus from "@/utils/EventBus";
import Funcionario from "@/model/funcionario/Funcionario";
import Pagination from "@/utils/Pagination.js";
import { booleanFilter } from "@/filters/Filters.js";
import ModalCadastroConfiguracaoPonto from "./ModalCadastroConfiguracaoPonto";

export default {
  name: "ModalListaConfiguracaoPonto",
  components: {
    ModalCadastroConfiguracaoPonto
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      cadastroEfetuado: false,
      dialog: false,
      configuracoesPonto: [],
      funcionario: new Funcionario(),
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Dt Inicio Vigência",
          align: "left",
          sortable: false,
          value: "dataInicialVigencia"
        },
        {
          text: "Dt Final Vigência",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
        },
        {
          text: "CH Semana",
          align: "left",
          sortable: false,
          value: "cargaHorariaSemanal"
        },
        {
          text: "Tarefa Online",
          align: "left",
          sortable: false,
          value: "tarefaOnline"
        },
        {
          text: "Hora Inicial P1",
          align: "left",
          sortable: false,
          value: "horaInicialP1"
        },
        {
          text: "Hora Final P1",
          align: "left",
          sortable: false,
          value: "horaFinalP1"
        },
        {
          text: "Hora Inicial P2",
          align: "left",
          sortable: false,
          value: "horaInicialP2"
        },
        {
          text: "Hora Final P2",
          align: "left",
          sortable: false,
          value: "horaFinalP2"
        },
        {
          text: "Hora Inicial P3",
          align: "left",
          sortable: false,
          value: "horaInicialP3"
        },
        {
          text: "Hora Final P3",
          align: "left",
          sortable: false,
          value: "horaFinalP3"
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
    }
  },
  created() {
    EventBus.$on("abrirCadastroConfiguracaoPonto", funcionario => {
      this.funcionario = funcionario;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    atualizar() {
      this.pesquisar();
    },
    excluir(configuracaoPonto) {
      confirm("Você deseja realmente excluir a Configuração do Ponto?") &&
        funcionarioService
          .excluirConfiguracaoPonto(configuracaoPonto)
          .then(() => {
            this.configuracoesPonto.splice(
              this.configuracoesPonto.indexOf(configuracaoPonto),
              1
            );
            this.pesquisar();
          });
    },
    editar(configuracaoPonto) {
      EventBus.$emit("editaConfiguracaoPonto", configuracaoPonto.id);
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await funcionarioService.listarTodasConfiguracoesPonto(
        this.funcionario.id
      );
      this.configuracoesPonto = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.configuracoesPonto = [];
      }, 300);
    }
  }
};
</script>
