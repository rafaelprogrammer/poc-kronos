<template>
  <div>
    <modal-analise-contrato @analiseConfirmada="mudancaSituacao = $event"></modal-analise-contrato>
    <section-definition title="Contratos"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Contratos</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
        <modal-seleciona-matricula label-botao-selecionar="Selecionar Matrículas"></modal-seleciona-matricula>
    </v-toolbar>
        <v-container fluid>
            <v-layout row justify-space-between>
                <v-flex xs12 sm12 md12>
                  <matriculas-pendentes-contrato></matriculas-pendentes-contrato>
                </v-flex>
            </v-layout>
            <v-subheader>Dados Matrícula Selecionada</v-subheader>
              <v-layout row>
                  <v-layout wrap justify-space-between>
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" disabled label="Nr Registro" v-model="matricula.numeroRegistroPessoa"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" disabled label="CPF" v-model="matricula.cpfPessoa"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" disabled label="Data Nascimento" v-model="matricula.dataNascimentoPessoa"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md1>
                      <v-select v-model="matricula.idGeneroPessoa" disabled item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                      <v-text-field color="primary" disabled label="Nome" v-model="matricula.nomePessoa"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md3>
                      <v-text-field color="primary" disabled label="Instituição de Origem" v-model="matricula.nomeInstituicaoOrigem"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                      <v-text-field color="primary" disabled label="Curso" v-model="matricula.nomeCurso"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" disabled label="Data Matrícula" v-model="matricula.data"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md1>
                      <v-text-field color="primary" disabled label="Ano Início" v-model="matricula.anoInicioCurso"></v-text-field>
                    </v-flex>
                  </v-layout>
                  <v-flex xs12 sm6 md3 class="text-md-center">
                    <img :src="arquivo && arquivo.dadosBase64 ? arquivo.dadosBase64 : '/static/doc-images/lists/avatar-default.jpg'" width="80%" :alt="arquivo && arquivo.legenda ? arquivo.legenda : ''" />
                    <modal-visualiza-matricula :tipos-generos="tiposGeneros" :id-matricula="matricula.id"></modal-visualiza-matricula>
                  </v-flex>
              </v-layout>
              <v-layout wrap justify-space-between>  
                    <v-flex xs12 sm6 md2>
                      <v-text-field color="primary" disabled label="Situação" v-model="matricula.nomeTipoSituacaoMatricula"></v-text-field>
                    </v-flex>
              </v-layout>
                <v-flex xs12 sm2 md12>
                  <v-data-table :loading="loading" v-if="matricula && matricula.id" :headers="headers" :items="contratos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                    <template slot="items" slot-scope="props">
                      <td>{{ props.item.nomePeriodo }}</td>
                      <td>{{ props.item.numero }}</td>
                      <td>{{ props.item.data }}</td>
                      <td>{{ props.item.ano }}</td>
                      <td>{{ props.item.nomeTipoFormaPagamento }}</td>
                      <td>{{ props.item.nomeTipoSituacaoContrato }}</td>
                      <td>
                        <v-menu offset-y>
                          <v-btn slot="activator" title="Opções para criação da estrutura do curso" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                          <v-list>
                            <v-list-tile alt="Analisar" v-if="verificarSituacaoAnalisar(props.item)" @click="analisar(props.item)">
                              <v-list-tile-title>Analisar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Aprovar" v-if="verificarSituacaoAprovar(props.item)" @click="aprovar(props.item)">
                              <v-list-tile-title>Aprovar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Confirmar Assinatura" v-if="verificarSituacaoConfirmarAssinatura(props.item)" @click="confirmarAssinatura(props.item)">
                              <v-list-tile-title>Confirmar Assinatura</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Visualizar" @click="visualizar(props.item)">
                              <v-list-tile-title>Visualizar</v-list-tile-title>
                            </v-list-tile>
                          </v-list>
                        </v-menu>
                      </td>
                    </template>
                    <template slot="no-data">
                      Não existem informações cadastradas!
                    </template>
                  </v-data-table>
              </v-flex>
            </v-container>
     </div>
</template>
<script>
import pessoaService from "@/service/pessoa/PessoaService";
import contratoService from "@/service/contrato/ContratoService";
import arquivoService from "@/service/arquivo/ArquivoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import ModalSelecionaMatricula from "../matricula/ModalSelecionaMatricula";
import MatriculasPendentesContrato from "./MatriculasPendentesContrato";
import ModalVisualizaMatricula from "../matricula/ModalVisualizaMatricula";
import ModalAnaliseContrato from "./ModalAnaliseContrato"
import Arquivo from "@/model/arquivo/Arquivo";
import Matricula from "@/model/matricula/Matricula";
import Contrato from "@/model/contrato/Contrato";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "GerenciaCadastroContratos",
  components: {
    ModalSelecionaMatricula,
    MatriculasPendentesContrato,
    ModalVisualizaMatricula,
    ModalAnaliseContrato
  },
  data() {
    return {
      tiposGeneros: [],
      mudancaSituacao: false,
      matricula: new Matricula(),
      contrato: new Contrato(),
      contratos: [],
      arquivo: null,
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "nomePeriodo"
        },
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "ano"
        },
        {
          text: "Forma Pagamento",
          align: "left",
          sortable: false,
          value: "nomeTipoFormaPagamento"
        },
        {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "nomeTipoSituacaoContrato"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    pagination: {
      handler() {
        this.pesquisarContratos();
      }
    },
    mudancaSituacao(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisarContratos();
          this.mudancaSituacao = false;
        }, 500);
      }
    }
  },
  async created() {
    EventBus.$on("selecionaMatricula", async matricula => {
      this.carregarDadosBasicos();
      this.pesquisarMatricula(matricula.id).then( async () => {
          if (
            this.matricula.idArqAnexoPessoa ||
            this.matricula.idArqAnexoPessoa > 0
          ) {
            this.arquivo = await arquivoService.buscarPorId(
              this.matricula.idArqAnexoPessoa
            );
          } else {
            this.arquivo = new Arquivo();
          }
          this.pesquisarContratos();
      });
    });
  },
  methods: {
    visualizar(contrato) {
      EventBus.$emit("analiseContrato", contrato.id, false);
    },
    confirmarAssinatura(contrato) {
      confirm("Você deseja realmente confirmar a Assinatura ?") &&
        contratoService.confirmarAssinatura(contrato).then(() => {
          this.mudancaSituacao = true;
        });
    },
    verificarSituacaoConfirmarAssinatura(contrato) {
      return Contrato.CONFIRMAR_ASSINATURA() === contrato.idTipoSituacaoContrato;
    },
    aprovar(contrato) {
      confirm("Você deseja realmente aprovar o Contrato ?") &&
        contratoService.aprovar(contrato).then((dados) => {
          this.mudancaSituacao = true;
        });
    },
    verificarSituacaoAprovar(contrato) {
      return Contrato.AGUARDANDO_APROVACAO() === contrato.idTipoSituacaoContrato;
    },
    analisar(contrato) {
      EventBus.$emit("analiseContrato", contrato.id, true);
    },
    verificarSituacaoAnalisar(contrato) {
      return Contrato.ANALISE_FINANCEIRA() === contrato.idTipoSituacaoContrato;
    },
    async pesquisarMatricula(id) {
      this.matricula = await matriculaService.editar(id);
    },
    async carregarDadosBasicos() {
      this.tiposGeneros = await pessoaService.tiposGeneros();
    },
    async pesquisarContratos() {
      if (this.matricula.id) {
        this.loading = true;
        this.contrato.idMatricula = this.matricula.id;
        const paginacaoResultado = await contratoService.listarTodos(
          this.contrato,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.contratos = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
