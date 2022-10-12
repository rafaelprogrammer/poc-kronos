<template>
      <v-dialog v-model="dialog" persistent width="50%">
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Funcionário</span>
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
              </v-layout>
              <v-data-table :loading="loading" item-key="categoria + funcao" :headers="headers" :items="funcoes" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.categoria }}</td>
                    <td><v-checkbox color="primary" v-model="props.item.funcaoSelecionada" :label="props.item.funcao" @change="selecionarFuncao(props.item)"></v-checkbox></td>
                    <td><v-checkbox color="primary" v-model="props.item.funcaoRegistro"></v-checkbox></td>
                  </template>
                <template slot="no-data">
                    Não existem informações!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancelar</v-btn>
            <v-btn color="primary" dark @click.native="salvar">Salvar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import EventBus from "@/utils/EventBus";
import Funcionario from "@/model/funcionario/Funcionario";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalCadastraFuncoes",
  data() {
    return {
      dialog: false,
      funcoes: [],
      funcionario: new Funcionario(),
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Categoria",
          align: "left",
          sortable: false,
          value: "categoria"
        },
        {
          text: "Função",
          align: "left",
          sortable: false,
          value: "funcao"
        },
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "funcaoRegistro"
        }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("abrirCadastroFuncoes", funcionario => {
      this.funcionario = funcionario;
      this.dialog = true;
      this.pesquisar();
    });
  },
  methods: {
    selecionarFuncao(funcionarioFuncao) {
      if (funcionarioFuncao.funcaoSelecionada) {
        funcionarioFuncao.idFuncionario = this.funcionario.id;
      } else {
        funcionarioFuncao.idFuncionario = null;
      }
    },
    salvar() {
      funcionarioService.salvarFuncoes(this.funcoes).then(resposta => {
        if (resposta) {
          this.dialog = false;
        }
      });
    },
    async pesquisar() {
      this.loading = true;
      this.funcoes = await funcionarioService.listarFuncoes(
        this.funcionario.id
      );
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.funcoes = [];
      }, 300);
    }
  }
};
</script>
