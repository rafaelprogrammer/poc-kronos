<template>
    <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" v-show="idMatricula" small slot="activator" color="primary" @click="abrir()">Detalhes</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Visualizar Matrícula</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
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
                        <v-flex xs12 sm6 md2>
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
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Ano Conclusão" v-model="matricula.anoConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Sem Conclusão" v-model="matricula.semestreConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Data Conclusão" v-model="matricula.dataConclusaoCurso"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Resultado" v-model="matricula.nomeTipoResultado"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Data Colação" v-model="matricula.dataColacaoGrau"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md2>
                        <v-text-field color="primary" disabled label="Situação" v-model="matricula.nomeTipoSituacaoMatricula"></v-text-field>
                      </v-flex>
                </v-layout>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import pessoaService from "@/service/pessoa/PessoaService";
import arquivoService from "@/service/arquivo/ArquivoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import Arquivo from "@/model/arquivo/Arquivo";
import Matricula from "@/model/matricula/Matricula";
export default {
  name: "ModalVisualizaMatricula",
  props: {
    idMatricula: Number,
    tiposGeneros: Array
  },
  data() {
    return {
      dialog: false,
      matricula: new Matricula(),
      arquivo: new Arquivo()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaMatricula", async matricula => {
    });
  },
  methods: {
    async abrir() {
      // this.carregarDadosBasicos();
      this.pesquisarMatricula(this.idMatricula).then( async () => {
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
      });
    },
    async pesquisarMatricula(id) {
      this.matricula = await matriculaService.editar(id);
    },
    async carregarDadosBasicos() {
      this.tiposGeneros = await pessoaService.tiposGeneros();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.matricula = new Matricula();
        this.arquivo = new Arquivo();
      }, 300);
    }
  }
};
</script>
