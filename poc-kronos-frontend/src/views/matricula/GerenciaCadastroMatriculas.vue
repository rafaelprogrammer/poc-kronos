<template>
  <div>
    <section-definition title="Matrículas"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Matrículas</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <modal-seleciona-matricula></modal-seleciona-matricula>
        <v-spacer></v-spacer>
        <modal-cadastro-matricula :tipos-generos="tiposGeneros"></modal-cadastro-matricula>
    </v-toolbar>
            <v-container fluid>
                  <v-layout wrap>
                    <v-btn color="primary" alt="Alterar Matrícula" v-show="matricula && matricula.id" @click="editar" title="Alterar Matrícula"><v-icon>edit</v-icon>Editar</v-btn>
                    <v-btn color="primary" alt="Reativar Matrícula" v-show="visualizaBotaoReativar()" @click="reativar" title="Reativar Matrícula"><v-icon>settings</v-icon>Reativar</v-btn>
                    <v-btn color="primary" alt="Validar Matrícula" v-show="visualizaBotaoValidar()" @click="validar" title="Validar Matrícula"><v-icon>check</v-icon>Validar</v-btn>
                    <v-flex xs12 sm6 md3 v-if="visualizaBotaoCancelar()">
                      <modal-cancela-matricula :id-matricula="matricula.id" @matriculaCancelada="matriculaCanceladaOuTransferida = $event"/>
                    </v-flex>
                    <v-flex xs12 sm6 md3 v-if="visualizaBotaoTransferir()">
                      <modal-transfere-matricula :id-matricula="matricula.id" @matriculaTransferida="matriculaCanceladaOuTransferida = $event"/>
                    </v-flex>
                  </v-layout>
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
                        <v-flex xs12 sm6 md1>
                          <v-text-field color="primary" disabled label="Sem. Início" v-model="matricula.semestreInicioCurso"></v-text-field>
                        </v-flex>
                      </v-layout>
                      <v-flex xs12 sm6 md3 class="text-md-center">
                        <img :src="arquivo && arquivo.dadosBase64 ? arquivo.dadosBase64 : '/static/doc-images/lists/avatar-default.jpg'" width="80%" :alt="arquivo && arquivo.legenda ? arquivo.legenda : ''" />
                      </v-flex>
                </v-layout>
                <v-layout wrap justify-space-between>  
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Data Início" v-model="matricula.dataInicioCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-text-field color="primary" disabled label="Ano Conclusão" v-model="matricula.anoConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Sem Conclusão" v-model="matricula.semestreConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-text-field color="primary" disabled label="Data Conclusão" v-model="matricula.dataConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Resultado" v-model="matricula.nomeTipoResultado"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md1>
                        <v-text-field color="primary" disabled label="Data Colação" v-model="matricula.dataColacaoGrau"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Situação" v-model="matricula.nomeTipoSituacaoMatricula"></v-text-field>
                      </v-flex>
                </v-layout>
                <v-flex xs12 sm2 md2 v-if="matricula && matricula.id">
                  <modal-cadastro-contrato :id-curso="this.matricula.idCurso" :id-matricula="this.matricula.id"
                    :numeroRegistroPessoa="matricula.numeroRegistroPessoa" :cpfPessoa="matricula.cpfPessoa"
                    :nomePessoa="matricula.nomePessoa"
                    @contratoCadastrado="cadastroEfetuado = $event"></modal-cadastro-contrato>
                  <v-spacer></v-spacer>
                </v-flex>
                <v-flex xs12 sm2 md12>
                  <v-data-table :loading="loading" v-if="matricula && matricula.id" :headers="headers" :items="contratos" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                    <template slot="items" slot-scope="props">
                      <td>{{ props.item.nomePeriodo }}</td>
                      <td>{{ props.item.numero }}</td>
                      <td>{{ props.item.ano }}</td>
                      <td>{{ props.item.nomeTipoSituacaoContrato }}</td>
                      <td>
                        <v-menu offset-y>
                          <v-btn slot="activator" title="Opções para criação da estrutura do curso" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                          <v-list>
                            <v-list-tile alt="Editar" v-if="verificarPreMatricula(props.item)" @click="editarContrato(props.item)">
                              <v-list-tile-title>Editar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Excluir" v-if="verificarPreMatricula(props.item) || verificarValidado(props.item)" @click="excluir(props.item)">
                              <v-list-tile-title>Excluir</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Validar" v-if="verificarPreMatricula(props.item)" @click="validarContrato(props.item)">
                              <v-list-tile-title>Validar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Enviar Financeiro" v-if="verificarValidado(props.item)" @click="enviarFinanceiro(props.item)">
                              <v-list-tile-title>Enviar Financeiro</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-divider></v-divider>
                            <v-list-tile alt="Reativar" v-if="verificarCanceladoOuTransferido(props.item)" @click="reativarContrato(props.item)">
                              <v-list-tile-title>Reativar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Visualizar" v-if="verificarAnaliseFinanceiro(props.item)" @click="visualizarContrato(props.item)">
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
import contratoService from "@/service/contrato/ContratoService";
import pessoaService from "@/service/pessoa/PessoaService";
import arquivoService from "@/service/arquivo/ArquivoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import ModalCadastroMatricula from "./ModalCadastroMatricula";
import ModalCadastroContrato from "../contrato/ModalCadastroContrato";
import ModalSelecionaMatricula from "./ModalSelecionaMatricula";
import ModalCancelaMatricula from "./ModalCancelaMatricula";
import ModalTransfereMatricula from "./ModalTransfereMatricula";
import Arquivo from "@/model/arquivo/Arquivo";
import Matricula from "@/model/matricula/Matricula";
import ContratoMatricula from "@/model/contrato/ContratoMatricula";
// import Pessoa from "@/model/pessoa/Pessoa";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "GerenciaCadastroMatriculas",
  components: {
    ModalSelecionaMatricula,
    ModalCadastroMatricula,
    ModalCadastroContrato,
    ModalCancelaMatricula,
    ModalTransfereMatricula
  },
  data() {
    return {
      cadastroEfetuado: false,
      matriculaCanceladaOuTransferida: false,
      tiposGeneros: [],
      matricula: new Matricula(),
      contrato: new ContratoMatricula(),
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
          text: "Ano",
          align: "left",
          sortable: false,
          value: "ano"
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
    matriculaCanceladaOuTransferida(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisarMatricula(this.matricula.id);
          this.pesquisarContratos();
          this.matriculaCanceladaOuTransferida = false;
        }, 500);
      }
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisarContratos();
          this.cadastroEfetuado = false;
        }, 500);
      }
    },
    pagination: {
      handler() {
        this.pesquisarContratos();
      }
    }
  },
  async created() {
    this.carregarDadosBasicos();
    EventBus.$on("selecionaMatricula", matricula => {
      this.carregarDadosBasicos();
      this.matricula = matricula;
      matriculaService.editar(this.matricula.id).then(async () => {
        if (this.matricula.idArqAnexoPessoa || this.matricula.idArqAnexoPessoa > 0) {
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
    verificarCanceladoOuTransferido(contrato) {
      return ContratoMatricula.CANCELADO() === contrato.idTipoSituacaoContrato ||
      ContratoMatricula.TRANSFERIDO() === contrato.idTipoSituacaoContrato;
    },
    reativar() {
      confirm("Você deseja realmente reativar a matrícula?") &&
      matriculaService.reativar(this.matricula).then((dados) => {
        if (dados) {
          this.pesquisarMatricula(this.matricula.id);
        }
      });
    },
    reativarContrato(contrato) {
      confirm("Você deseja realmente reativar o contrato?") &&
      contratoService.reativar(contrato).then((dados) => {
        if (dados) {
          this.pesquisarContratos();
        }
      });
    },
    visualizaBotaoCancelar() {
      return this.matricula && Matricula.VALIDADA() === this.matricula.idTipoSituacaoMatricula;
    },
    visualizaBotaoTransferir() {
      return this.matricula && Matricula.VALIDADA() === this.matricula.idTipoSituacaoMatricula;
    },
    visualizaBotaoValidar() {
      return this.matricula && Matricula.PRE_MATRICULA() === this.matricula.idTipoSituacaoMatricula;
    },
    visualizaBotaoReativar() {
      return this.matricula && (Matricula.CANCELADA() === this.matricula.idTipoSituacaoMatricula || Matricula.TRANSFERIDA() === this.matricula.idTipoSituacaoMatricula);
    },
    visualizarContrato(contrato) {
      EventBus.$emit("visualizaContrato", contrato.id);
    },
    enviarFinanceiro(contrato) {
      confirm("Você deseja realmente enviar para o Financeiro ?") &&
        contratoService.enviarFinanceiro(contrato).then(() => {
          this.pesquisarContratos();
        });
    },
    validarContrato(contrato) {
      confirm("Você deseja realmente validar o Contrato ?") &&
        contratoService.validar(contrato).then(() => {
          this.pesquisarContratos();
        });
    },
    verificarPreMatricula(contrato) {
      return ContratoMatricula.PRE_MATRICULA() === contrato.idTipoSituacaoContrato;
    },
    verificarValidado(contrato) {
      return ContratoMatricula.VALIDADO() === contrato.idTipoSituacaoContrato;
    },
    verificarAnaliseFinanceiro(contrato) {
      return ContratoMatricula.VALIDADO() === contrato.idTipoSituacaoContrato 
      || ContratoMatricula.ANALISE_FINANCEIRA() === contrato.idTipoSituacaoContrato;
    },
    validar() {
      confirm("Você deseja realmente validar a matrícula?") &&
        matriculaService.validar(this.matricula).then((dados) => {
          if (dados) {
            this.pesquisarMatricula(this.matricula.id);
          }
        });
    },
    editarContrato(contrato) {
      EventBus.$emit("editaContrato", contrato.id);
    },
    excluir(contrato) {
      confirm("Você deseja realmente excluir o Contrato ?") &&
        contratoService.excluir(contrato).then((dados) => {
          if (dados) {
            this.contratos.splice(this.contratos.indexOf(contrato), 1);
          }
        });
    },
    editar() {
      EventBus.$emit("editaMatricula", this.matricula.id);
    },
    pesquisarMatricula(id) {
      setTimeout(async () => {
        this.matricula = await matriculaService.editar(id);
      }, 500);
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
