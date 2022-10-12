<template>
  <div>
    <section-definition title="Homologação Ponto" event-update="atualizarHomologacaoPonto"></section-definition>
    <v-toolbar dark color="primary">
      <v-toolbar-title>Gerenciar Homologação Ponto / Tarefa</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
      <v-spacer></v-spacer>
      <modal-cadastra-folha-de-ponto />
    </v-toolbar>
     <v-container fluid>
       <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
          <v-layout align-center row fill-height justify-space-between>
              <v-flex xs12 sm6 md2>
                <check-box-state label="Todos/Ativos/Inativos" @checkBoxStateAtualizado="funcionario.ativo = $event"/>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="funcionario.idTipoCategoriaFuncao" :items="tiposFuncoesCategorias" item-text="nome" item-value="id" label="Categoria"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="funcionario.idTipoFuncao" :items="tiposFuncoes" item-text="nome" item-value="id" label="Função"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Nr Registro" v-model="funcionario.numeroRegistro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="CPF" v-model="funcionario.cpf"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Nome" v-model="funcionario.nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
        </v-layout>
      </v-form>
      <v-data-table :loading="loading" :headers="headers" :items="funcionarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.cpf }}</td>
          <td>{{ props.item.numeroRegistro }}</td>
          <td>{{ props.item.nome }}</td>
          <td>{{ props.item.dataNascimento }}</td>
          <td>{{ props.item.email }}</td>
          <td>{{ props.item.dataAdmissao }}</td>
          <td>{{ props.item.dataDemissao }}</td>
          <td>
            <v-menu offset-y>
              <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
              <v-list>
                <v-list-tile alt="Folha de Ponto" @click="registrarFolhaDePonto(props.item)">
                  <v-list-tile-title>Folha de Ponto</v-list-tile-title>
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
import Funcionario from "@/model/funcionario/Funcionario";
import EventBus from "@/utils/EventBus.js";
import Pagination from "@/utils/Pagination.js";
import CheckBoxState from "@/components/Custom/CheckBoxState";
import ModalCadastraFolhaDePonto from "./ModalCadastraFolhaDePonto";
// import ModalCadastraFuncoes from "./ModalCadastraFuncoes";
// import ModalListaConfiguracaoPonto from "./ModalListaConfiguracaoPonto";

export default {
  components: {
    ModalCadastraFolhaDePonto,
    CheckBoxState
  },
  data() {
    return {
      dialog: false,
      loading: true,
      pagination: new Pagination(1, 10),
      cadastroEfetuado: false,
      funcionario: new Funcionario(),
      funcionarios: [],
      tiposFuncoesCategorias: [],
      tiposFuncoes: [],
      headers: [
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpf"
        },
        {
          text: "Nr Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Data Nasc",
          align: "left",
          sortable: false,
          value: "dataNascimento"
        },
        {
          text: "Email",
          align: "left",
          sortable: false,
          value: "email"
        },
        {
          text: "Data Admissão",
          align: "left",
          sortable: false,
          value: "dataAdmissao"
        },
        {
          text: "Data Demissão",
          align: "left",
          sortable: false,
          value: "dataDemissao"
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
  created() {
    this.carregarDadosBasicos();
    this.atualizar();
    EventBus.$on("atualizarHomologacaoPonto", () => {
      this.atualizar();
    });
  },
  methods: {
    liberar() {
      // this.idsPontos = this.selected.map(s => s.id);
    },
    registrarFolhaDePonto(funcionario) {
      EventBus.$emit("abrirCadastroFolhaDePonto", funcionario);
    },
    // registrarConfiguracaoPonto(funcionario) {
    //   EventBus.$emit("abrirCadastroConfiguracaoPonto", funcionario);
    // },
    // registrarFuncoes(funcionario) {
    //   EventBus.$emit("abrirCadastroFuncoes", funcionario);
    // },
    limpar() {
      this.funcionario = new Funcionario();
      this.pesquisar();
    },
    async carregarDadosBasicos() {
      this.tiposFuncoesCategorias = await funcionarioService.tiposCategorias();
      this.tiposFuncoes = await funcionarioService.tiposFuncoes();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await funcionarioService.listarTodos(
        this.funcionario,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.funcionarios = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async atualizar() {
      this.pesquisar();
    }
    // editItem(item) {
    //   EventBus.$emit("editaFuncionario", item.id);
    // }
  }
};
</script>
