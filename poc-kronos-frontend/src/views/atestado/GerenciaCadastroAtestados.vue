<template>
  <div>
    <modal-upload-arquivo></modal-upload-arquivo>
     <modal-preview></modal-preview>
    <section-definition title="Atestados"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Atestados</v-toolbar-title>
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
                          <v-chip :color="selecionarCor(pessoaResumo.grauComportamento)" text-color="primary">
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
                    <modal-cadastro-atestado :id-pessoa="pessoaResumo.id"
                    @atestadoCadastrado="cadastroEfetuado = $event"></modal-cadastro-atestado>
                  </v-flex>
                  <v-flex xs12 sm2 md1>
                      <v-btn icon @click="pesquisar" v-if="matriculaOcorrencia && matriculaOcorrencia.idMatricula">
                        <v-icon color="primary">refresh</v-icon>
                      </v-btn>
                  </v-flex>
                </v-layout>
                <v-flex xs12 sm2 md12>
                  <v-data-table :loading="loading" v-if="matriculaOcorrencia && matriculaOcorrencia.idMatricula" :headers="headers" :items="atestados" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                    <template slot="items" slot-scope="props">
                      <td>{{ props.item.dataEntrega }}</td>
                      <td>{{ props.item.dataInicioVigencia }}</td>
                      <td>{{ props.item.dataFinalVigencia }}</td>
                      <td>{{ props.item.numeroDias }}</td>
                      <td>{{ props.item.nomeTipoFalta }}</td>
                      <td>
                        <v-menu offset-y>
                          <v-btn slot="activator" title="Opções" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Opções</v-btn>
                          <v-list>
                            <v-list-tile alt="Editar" @click="editar(props.item)">
                              <v-list-tile-title>Editar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Excluir" @click="excluir(props.item)">
                              <v-list-tile-title>Excluir</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Visualizar" @click="visualizar(props.item)">
                              <v-list-tile-title>Visualizar</v-list-tile-title>
                            </v-list-tile>
                          </v-list>
                        </v-menu>
                      </td>
                      <td>
                        <v-menu offset-y>
                          <v-btn slot="activator" title="Anexo" color="primary" dark><v-icon>keyboard_arrow_down</v-icon>Anexo</v-btn>
                          <v-list>
                            <v-list-tile alt="Anexar" v-if="!props.item.idArqAnexo" @click="abrirUpload(props.item)">
                              <v-list-tile-title>Anexar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Visualizar" v-if="props.item.idArqAnexo" @click="abrirPreview(props.item.idArqAnexo)">
                              <v-list-tile-title>Visualizar</v-list-tile-title>
                            </v-list-tile>
                            <v-divider></v-divider>
                            <v-list-tile alt="Excluir" v-if="props.item.idArqAnexo" @click="excluirArquivo(props.item)">
                              <v-list-tile-title>Excluir</v-list-tile-title>
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
import atestadoService from "@/service/atestado/AtestadoService";
import pessoaService from "@/service/pessoa/PessoaService";
import arquivoService from "@/service/arquivo/ArquivoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import ModalUploadArquivo from "../arquivo/ModalUploadArquivo";
import ModalPreview from "../arquivo/ModalPreview";
import ModalCadastroAtestado from "./ModalCadastroAtestado";
import ModalSelecionaAluno from "../aluno/ModalSelecionaAluno";
import Arquivo from "@/model/arquivo/Arquivo";
import Matricula from "@/model/matricula/Matricula";
import Atestado from "@/model/atestado/Atestado";
import PessoaResumo from "@/model/pessoa/PessoaResumo";
import { booleanFilter, limitCaracteres } from "@/filters/Filters.js";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "GerenciaCadastroAtestados",
  components: {
    ModalSelecionaAluno,
    ModalCadastroAtestado,
    ModalUploadArquivo,
    ModalPreview
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
      atestados: [],
      arquivo: null,
      atestado: new Atestado(),
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Data Entrega",
          align: "left",
          sortable: false,
          value: "dataEntrega"
        },
        {
          text: "Data Inicio",
          align: "left",
          sortable: false,
          value: "dataInicioVigencia"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
        },
        {
          text: "Nr Dias",
          align: "left",
          sortable: false,
          value: "numeroDias"
        },
        {
          text: "Tipo Falta",
          align: "left",
          sortable: false,
          value: "nomeTipoFalta"
        },
        { text: "", value: "name", sortable: false },
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
    EventBus.$on("uploadArquivoAtestado", async arquivo => {
      atestadoService.arquivo(arquivo, this.atestado).then(() => {
        setTimeout(() => {
          this.pesquisar();
        }, 500);
      });
    });
    EventBus.$on("selecionaAluno", async aluno => {
      this.carregarDadosBasicos();
      this.pessoaResumo = new PessoaResumo(aluno);
      this.matriculaOcorrencia = await matriculaService.buscarParaOcorrencia(this.pessoaResumo.id);
      this.pesquisar();
    });
  },
  methods: {
    abrirPreview(idArqAnexo) {
      EventBus.$emit("abrirPreview", idArqAnexo);
    },
    abrirUpload(atestado) {
      this.atestado = atestado;
      console.log(this.atestado);
      EventBus.$emit(
        "abrirUpload",
        "uploadArquivoAtestado",
        atestado.idPessoa,
        atestado.nomeTipoFalta
      );
    },
    excluirArquivo(atestado) {
      atestado.idArqAnexo = null;
      confirm("Você deseja realmente excluir o anexo?") &&
        atestadoService.salvar(atestado).then(() => {
          this.pesquisar();
        });
    },
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
    excluir(atestado) {
      confirm("Você deseja realmente excluir o Atestado?") &&
        atestadoService.excluir(atestado).then(() => {
          this.atestados.splice(this.atestados.indexOf(atestado), 1);
          this.pesquisar();
        });
    },
    visualizar(atestado) {
      EventBus.$emit("visualizaAtestado", atestado.id);
    },
    editar(atestado) {
      EventBus.$emit("editaAtestado", atestado.id);
    },
    async carregarDadosBasicos() {
      this.tiposGeneros = await pessoaService.tiposGeneros();
    },
    async pesquisar() {
      if (this.matriculaOcorrencia.idMatricula) {
        this.loading = true;
        this.atestado.idPessoa = this.pessoaResumo.id;
        const paginacaoResultado = await atestadoService.listarTodos(
          this.atestado,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.atestados = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
