<template>
  <div>
    <section-definition title="Pontos" event-update="atualizarPonto"></section-definition>
    <v-toolbar dark color="primary">
      <v-toolbar-title>Gerenciar Pontos</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
      <v-spacer></v-spacer>
      <v-layout align-center row fill-height justify-space-between>
        <v-flex xs12 sm6 md4 v-if="registraEntrada">
          <modal-cadastro-registra-entrada @pontoCadastrado="cadastroEfetuado = $event" :registra-entrada="registraEntrada"/>
        </v-flex>
        <v-flex xs12 sm6 md4 v-if="registraTarefaOnline">
          <modal-cadastro-tarefa-online @tarefaOnlineCadastrada="cadastroEfetuado = $event"/>
        </v-flex>
        <modal-cadastro-registra-saida @pontoSaidaCadastrado="cadastroEfetuado = $event"/>
      </v-layout>
    </v-toolbar>
     <v-container fluid>
       <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
          <v-layout align-center row fill-height justify-space-between>
              <v-flex xs12 sm6 md2>
                <v-select v-model="ponto.ano" :items="anos" item-text="nome" item-value="id" label="Ano"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-select v-model="ponto.mes" :items="meses" item-text="nome" item-value="id" label="Mês"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2 >
                <v-checkbox color="primary" label="Pendentes" v-model="ponto.pendente"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
        </v-layout>
      </v-form>
      <v-data-table :loading="loading" :headers="headers" :items="pontos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.data }}</td>
          <td>{{ props.item.nomeTipoPeriodoPonto }}</td>
          <td>{{ props.item.horaInicialRegistro }}</td>
          <td>{{ props.item.horaInicialInformado }}</td>
          <td>{{ props.item.horaFinalRegistro }}</td>
          <td>{{ props.item.horaFinalInformado }}</td>
          <td>{{ props.item.dataHomologacao }}</td>
          <td>{{ props.item.descricao }}</td>
          <td>
            <v-menu offset-y v-if="!props.item.tarefaOnline">
              <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Editar" @click="registrarEntrada(props.item)" v-if="props.item.toleranciaEntrada">
                  <v-list-tile-title>Registrar Entrada</v-list-tile-title>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile alt="Funções" @click="registrarSaida(props.item)" v-if="props.item.toleranciaSaida">
                  <v-list-tile-title>Registrar Saida</v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu>
          </td>
        </template>
        <template slot="no-data">
            Não existem informações cadastradas!
        </template>
      </v-data-table>
     </v-container>
  </div>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Ponto from "@/model/funcionario/Ponto";
import EventBus from "@/utils/EventBus.js";
import Pagination from "@/utils/Pagination.js";
import ModalCadastroRegistraEntrada from "./ModalCadastroRegistraEntrada";
import ModalCadastroRegistraSaida from "./ModalCadastroRegistraSaida";
import ModalCadastroTarefaOnline from "./ModalCadastroTarefaOnline";

export default {
  components: {
    ModalCadastroRegistraEntrada,
    ModalCadastroRegistraSaida,
    ModalCadastroTarefaOnline
  },
  data() {
    return {
      registraEntrada: false,
      registraTarefaOnline: false,
      dialog: false,
      loading: true,
      pagination: new Pagination(1, 10),
      cadastroEfetuado: false,
      ponto: new Ponto(),
      pontos: [],
      meses: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12],
      anos: [],
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "nomeTipoPeriodoPonto"
        },
        {
          text: "Reg Hora Inicio ",
          align: "left",
          sortable: false,
          value: "horaInicialRegistro"
        },
        {
          text: "Hora Inicio Info",
          align: "left",
          sortable: false,
          value: "horaInicialInformado"
        },
        {
          text: "Reg Hora Final",
          align: "left",
          sortable: false,
          value: "horaFinalRegistro"
        },
        {
          text: "Hora Final Info",
          align: "left",
          sortable: false,
          value: "horaFinalInformado"
        },
        {
          text: "Data Homologação",
          align: "left",
          sortable: false,
          value: "dataHomologacao"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
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
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  async created() {
    this.carregarDadosBasicos();
    this.registraEntrada = await funcionarioService.verificarRegistraEntrada();
    console.log(this.registraEntrada);
    this.registraTarefaOnline = await funcionarioService.verificarRegistraTarefaOnline();
    this.atualizar();
    EventBus.$on("atualizarPonto", () => {
      this.atualizar();
    });
  },
  methods: {
    registrarEntrada(ponto) {
      EventBus.$emit("editaRegistraEntrada", ponto.id);
    },
    registrarSaida(ponto) {
      EventBus.$emit("editaRegistraSaida", ponto.id);
    },
    limpar() {
      this.ponto = new Ponto();
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await funcionarioService.listarTodosPontos(
        this.ponto,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.pontos = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async carregarDadosBasicos() {
      this.anos = await funcionarioService.anosPontosCadastrados();
    },
    atualizar() {
      this.pesquisar();
    }
  }
};
</script>
