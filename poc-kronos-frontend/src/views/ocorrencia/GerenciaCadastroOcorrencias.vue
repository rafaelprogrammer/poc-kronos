<template>
  <div>
    <section-definition title="Ocorrências"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Ocorrências</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <modal-seleciona-aluno></modal-seleciona-aluno>
        <v-spacer></v-spacer>
    </v-toolbar>
            <v-container fluid>
                  <v-layout row>
                      <v-layout wrap justify-space-between>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Nr Registro" v-model="pessoaResumo.numeroRegistro"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="CPF" v-model="pessoaResumo.cpf"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Nascimento" v-model="pessoaResumo.dataNascimento"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md1>
                          <v-select v-model="pessoaResumo.idGenero" disabled item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                          <v-tooltip color="transparent" top>
                            <span slot="activator" @mouseover="buscarFoto(pessoaResumo.id)"> <v-text-field color="primary" disabled label="Nome" v-model="pessoaResumo.nome"></v-text-field></span>
                            <v-avatar :size="100">
                            <img :src="foto && foto.dadosBase64 ? foto.dadosBase64 : '/static/doc-images/lists/avatar-default.jpg'" :alt="foto && foto.legenda ? foto.legenda : ''" />
                            </v-avatar>
                          </v-tooltip>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-chip :color="selecionarCor(pessoaResumo.grauComportamento)" text-color="white">
                              <v-avatar>{{pessoaResumo.grauComportamento}}</v-avatar>
                              Grau Comp.
                           </v-chip>
                        </v-flex>
                        <v-flex xs12 sm6 md4>
                          <v-text-field color="primary" disabled label="Instituição de Origem" v-model="matriculaOcorrencia.instituicaoOrigem"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Curso Atual" v-model="matriculaOcorrencia.cursoAtual"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Matrícula" v-model="matriculaOcorrencia.dataMatricula"></v-text-field>
                        </v-flex>
                      </v-layout>
                </v-layout>
                <v-layout wrap>
                  <v-flex xs12 sm2 md2 v-if="matriculaOcorrencia && matriculaOcorrencia.idMatricula">
                    <modal-cadastro-ocorrencia :id-pessoa="pessoaResumo.id"
                    @ocorrenciaCadastrada="cadastroEfetuado = $event"></modal-cadastro-ocorrencia>
                  </v-flex>
                  <v-flex xs12 sm2 md1>
                      <v-btn icon @click="pesquisar" v-if="matriculaOcorrencia && matriculaOcorrencia.idMatricula">
                        <v-icon color="primary">refresh</v-icon>
                      </v-btn>
                  </v-flex>
                </v-layout>
                <v-flex xs12 sm2 md12>
                  <v-data-table :loading="loading" v-if="matriculaOcorrencia && matriculaOcorrencia.idMatricula" :headers="headers" :items="ocorrencias" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                    <template slot="items" slot-scope="props">
                      <td>{{ props.item.data }}</td>
                      <td>{{ props.item.nomeFator }}</td>
                      <td>{{ props.item.valor }}</td>
                      <td width="30%">
                        <v-tooltip bottom>
                            <span slot="activator" class="">{{ props.item.registro | limitCaracteres(100) }}</span>
                            <span>{{ props.item.registro }}</span>
                        </v-tooltip>
                      </td>
                      <td>{{ props.item.dataCiencia }}</td>
                      <td>{{ props.item.anulado | booleanFilter }}</td>
                      <td>
                        <v-menu offset-y>
                          <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                          <v-list>
                            <v-list-tile alt="Editar" @click="editar(props.item)">
                              <v-list-tile-title>Editar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Anular" @click="anular(props.item)">
                              <v-list-tile-title>Anular</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Registrar Ciência" @click="registrarCienciaOcorrencia(props.item)">
                              <v-list-tile-title>Registrar Ciência</v-list-tile-title>
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
import ocorrenciaService from "@/service/ocorrencia/OcorrenciaService";
import pessoaService from "@/service/pessoa/PessoaService";
import arquivoService from "@/service/arquivo/ArquivoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import ModalCadastroOcorrencia from "./ModalCadastroOcorrencia";
import ModalSelecionaAluno from "../aluno/ModalSelecionaAluno";
import Arquivo from "@/model/arquivo/Arquivo";
import Matricula from "@/model/matricula/Matricula";
import PessoaResumo from "@/model/pessoa/PessoaResumo";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "GerenciaCadastroOcorrencias",
  components: {
    ModalSelecionaAluno,
    ModalCadastroOcorrencia
  },
  filters: {
    booleanFilter: booleanFilter,
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      visualizaFoto: false,
      foto: null,
      cadastroEfetuado: false,
      tiposGeneros: [],
      pessoaResumo: {},
      matriculaOcorrencia: {},
      ocorrencias: [],
      arquivo: null,
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "data"
        },
        {
          text: "Fator",
          align: "left",
          sortable: false,
          value: "nomeFator"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valor"
        },
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "registro"
        },
        {
          text: "Data Ciente",
          align: "left",
          sortable: false,
          value: "dataCiencia"
        },
        {
          text: "Anulado",
          align: "left",
          sortable: false,
          value: "anulado"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.atualizarGrauComportamento();
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
    EventBus.$on("selecionaAluno", async aluno => {
      this.carregarDadosBasicos();
      this.pessoaResumo = new PessoaResumo(aluno);
      this.matriculaOcorrencia = await matriculaService.buscarParaOcorrencia(this.pessoaResumo.id);
    });
  },
  methods: {
    selecionarCor(grauComportamento) {
      if (grauComportamento < 5) {
        return "red accent-3";
      }
      if (grauComportamento >= 5 && grauComportamento <= 7.49) {
        return "orange accent-3";
      }
      if (grauComportamento > 7.49) {
        return "green accent-3";
      }
    },
    async atualizarGrauComportamento() {
      this.pessoaResumo.grauComportamento = await ocorrenciaService.retornarGrauComportamento(this.pessoaResumo.id);
    },
    async buscarFoto(idPessoa) {
      if (idPessoa) {
        this.foto = await arquivoService.buscarFoto(idPessoa);
        this.visualizaFoto = true;
      }
    },
    registrarCienciaOcorrencia(ocorrencia) {
      EventBus.$emit("registraCienciaOcorrencia", ocorrencia.id, ocorrencia.data);
    },
    anular(ocorrencia) {
      EventBus.$emit("anulaOcorrencia", ocorrencia.id, ocorrencia.data);
    },
    editar(ocorrencia) {
      EventBus.$emit("editaOcorrencia", ocorrencia.id);
    },
    async carregarDadosBasicos() {
      this.tiposGeneros = await pessoaService.tiposGeneros();
    },
    async pesquisar() {
      if (this.matriculaOcorrencia.idMatricula) {
        this.loading = true;
        const paginacaoResultado = await ocorrenciaService.listarTodos(
          null,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.ocorrencias = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
