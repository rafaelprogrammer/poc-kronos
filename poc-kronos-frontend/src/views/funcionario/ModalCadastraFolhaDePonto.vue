<template>
      <v-dialog v-model="dialog" persistent fullscreen>
        <v-card>
          <v-card-title>
            <span class="headline">Folha de Ponto</span>
          </v-card-title>
          <modal-visualiza-ponto-tarefa />
          <v-card-text>
            <v-container grid-list-md>
                <v-layout align-center row fill-height justify-space-between>
                  <v-flex xs12 sm6 md2>
                    <v-text-field label="Nr Registro"   v-model="this.funcionario.numeroRegistro" disabled></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-text-field label="CPF"  v-model="this.funcionario.cpf" disabled></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field label="Nome"  v-model="this.funcionario.nome" disabled></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field label="Data Nascimento"   v-model="this.funcionario.dataNascimento" disabled></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field label="Data Admissão"   v-model="this.funcionario.dataAdmissao" disabled></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-text-field label="Data Demissão"   v-model="this.funcionario.dataDemissao" disabled></v-text-field>
                  </v-flex>
                </v-layout>
                <v-form @submit.prevent="pesquisar()" ref="form" class="hidden-sm-and-down">
                    <v-layout align-center row fill-height justify-space-between>
                        <v-flex xs12 sm6 md2>
                          <v-select v-model="ponto.ano" :items="anos" item-text="nome" item-value="id" label="Ano"
                          @change="carregarMeses(ponto.ano)"></v-select>
                        </v-flex>
                        <v-flex xs12 sm6 md1>
                          <v-select v-model="ponto.mes" :items="meses" item-text="nome" item-value="numero" label="Mês"></v-select>
                        </v-flex>
                        <v-flex xs12 sm6 md2 >
                          <v-checkbox color="primary" label="Pendentes" v-model="ponto.pendente"></v-checkbox>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                          <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                        </v-flex>
                  </v-flex>
                  </v-layout>
                </v-form>
                <v-layout align-center row fill-height>
                  <v-flex xs12 sm2 md1>
                      <v-btn icon @click="pesquisar">
                        <v-icon color="primary">refresh</v-icon>
                  </v-btn>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-btn color="primary" alt="Liberar" title="Liberar" type="submit" @click="liberar()"><v-icon>settings</v-icon>Liberar</v-btn>
                    <v-btn color="primary" alt="Cancelar Liberação" title="Cancelar Liberação" type="submit" @click="cancelarLiberacao()"><v-icon>settings</v-icon>Cancelar Liberação</v-btn>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-btn color="primary" alt="Homologar" title="Homologar" type="submit" @click="homologar()"><v-icon>settings</v-icon>Homologar</v-btn>
                     <v-btn color="primary" alt="Cancelar Homologação" title="Cancelar Homologação" type="submit" @click="cancelarHomologacao()"><v-icon>settings</v-icon>Cancelar Homologação</v-btn>
                  </v-flex>
                  <v-flex xs12 sm6 md3 v-if="ponto.ano && ponto.mes">
                    <modal-cadastra-ausentes :id-funcionario="funcionario.id" :ano="ponto.ano" :mes="ponto.mes" />
                  </v-flex>
                  <v-flex xs12 sm6 md1 v-if="ponto.ano && ponto.mes">
                    <v-btn color="primary" alt="Folha de Ponto PDF" fab small title="Folha de Ponto PDF" type="submit" @click="gerarPDF()"><v-icon>picture_as_pdf</v-icon></v-btn>
                  </v-flex>
                </v-layout>
              <v-data-table v-model="selected" select-all :loading="loading" :headers="headers" :items="pontos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                <template slot="items" slot-scope="props">
                  <tr :active="props.selected" @click="props.selected = !props.selected">
                    <td>
                      <v-checkbox
                        :input-value="props.selected"
                        primary
                        hide-details
                      ></v-checkbox>
                    </td>
                    <td>{{ props.item.data }}</td>
                    <td>{{ props.item.diaSemana }}</td>
                    <td>{{ props.item.nomeTipoPeriodoPonto }}</td>
                    <td>{{ props.item.horaInicialRegistro }}</td>
                    <td>{{ props.item.horaInicialInformado }}</td>
                    <td>{{ props.item.horaFinalRegistro }}</td>
                    <td>{{ props.item.horaFinalInformado }}</td>
                    <td>{{ props.item.dataLiberacao }}</td>
                    <td>{{ props.item.dataHomologacao }}</td>
                    <td>{{ props.item.descricao }}</td>
                    <td>
                      <v-menu offset-y>
                        <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                        <v-list>
                          <v-list-tile alt="Visualizar" @click="visualizarPonto(props.item)">
                            <v-list-tile-title>Visualizar</v-list-tile-title>
                          </v-list-tile>
                        </v-list>
                      </v-menu>
                    </td>
                  </tr>
                </template>
                <template slot="no-data">
                  Não existem informações cadastradas!
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
import relatorioService from "@/service/relatorio/RelatorioService";
import funcionarioService from "@/service/funcionario/FuncionarioService";
import Ponto from "@/model/funcionario/Ponto";
import Funcionario from "@/model/funcionario/Funcionario";
import EventBus from "@/utils/EventBus";
import Pagination from "@/utils/Pagination.js";
import ModalVisualizaPontoTarefa from "./ModalVisualizaPontoTarefa";
import ModalCadastraAusentes from "./ModalCadastraAusentes"

export default {
  name: "ModalCadastraFolhaDePonto",
  components: {
    ModalVisualizaPontoTarefa,
    ModalCadastraAusentes
  },
  data() {
    return {
      anos: [],
      meses: [],
      dialog: false,
      funcionario: new Funcionario(),
      ponto: new Ponto(),
      pontos: [],
      pagination: new Pagination(1, 10),
      loading: true,
      liberaPesquisa: false,
      selected: [],
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Dia Semana",
          align: "left",
          sortable: false,
          value: "diaSemana"
        },
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "nomeTipoPeriodoPonto"
        },
        {
          text: "Reg. Hora Início",
          align: "left",
          sortable: false,
          value: "horaInicialRegistro"
        },
        {
          text: "Hora Início Informado",
          align: "left",
          sortable: false,
          value: "horaInicialInformado"
        },
        {
          text: "Reg. Hora Final ",
          align: "left",
          sortable: false,
          value: "horaFinalRegistro"
        },
        {
          text: "Hora Final Informado",
          align: "left",
          sortable: false,
          value: "horaFinalInformado"
        },
        {
          text: "Data Liberação",
          align: "left",
          sortable: false,
          value: "dataLiberacao"
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
        { text: "", value: "name", sortable: false }
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
    EventBus.$on("abrirCadastroFolhaDePonto", funcionario => {
      this.funcionario = funcionario;
      this.abrir();
    });
  },
  methods: {
    gerarPDF() {
      relatorioService.gerarFolhaPontoPdf(this.funcionario.id, this.ponto.ano, this.ponto.mes);
    },
    abrir() {
      this.carregarDadosBasicos();
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    liberar() {
      this.adicionarIds();
      funcionarioService.liberarPonto(this.ponto).then(dados => {
        if (dados) {
          this.pesquisar();
        }
      });
    },
    cancelarLiberacao() {
      this.adicionarIds();
      funcionarioService.cancelarLiberacaoPonto(this.ponto).then(dados => {
        if (dados) {
          this.pesquisar();
        }
      });
    },
    homologar() {
      this.adicionarIds();
      funcionarioService.homologarPonto(this.ponto).then(dados => {
        if (dados) {
          this.pesquisar();
        }
      });
    },
    cancelarHomologacao() {
      this.adicionarIds();
      funcionarioService.cancelarHomologacaoPonto(this.ponto).then(dados => {
        if (dados) {
          this.pesquisar();
        }
      });
    },
    adicionarIds() {
      this.ponto.idsPontos = this.selected.map(s => s.id);
    },
    async carregarDadosBasicos() {
      this.anos = await funcionarioService.anosPontosCadastrados();
    },
    async carregarMeses(ano) {
      this.meses = await funcionarioService.mesesPontosCadastrados(
        this.funcionario.id,
        ano
      );
    },
    visualizarPonto(ponto) {
      EventBus.$emit("visualizaPontoTarefa", ponto);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.ponto.idFuncionario = this.funcionario.id;
        const paginacaoResultado = await funcionarioService.listarTodosPontosParaHomologacao(
          this.ponto,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.pontos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    },
    limpar() {
      this.ponto = new Ponto();
      this.pesquisar();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.ponto = new Ponto();
        this.liberaPesquisa = false;
        this.selected = [];
      }, 300);
    }
  }
};
</script>
