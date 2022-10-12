<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" dark color="primary" alt="Incluir Ocorrência" @click="abrirCadastro()" title="Incluir Ocorrência"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>

              <v-flex xs12 sm6 md4>
                <v-select v-model="ocorrencia.idMatricula" :disabled="anulacao || registraCiencia" :error-messages="fieldErrors('ocorrencia.idMatricula')" 
                @blur="$v.ocorrencia.idMatricula.$touch()" item-text="cursoAtual" item-value="idMatricula" :items="matriculasCursos"
                @change="carregarTurmas(ocorrencia.idMatricula)" label="Curso/Matrícula"></v-select>
              </v-flex>

              <v-flex xs12 sm6 md3>
                <v-select v-model="ocorrencia.idTurma" :disabled="anulacao || registraCiencia" :error-messages="fieldErrors('ocorrencia.idTurma')" 
                @blur="$v.ocorrencia.idTurma.$touch()" item-text="sigla" item-value="id" :items="turmas" label="Turma"></v-select>
              </v-flex>

              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data" :disabled-date="anulacao || registraCiencia" :v-model="ocorrencia.data" @date="dataOcorrencia = $event" 
                :error-messages="fieldErrors('ocorrencia.data')"></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <time-picker-custom label="Hora" :disabled-date="anulacao || registraCiencia" :v-model="ocorrencia.hora" @time="ocorrencia.hora = $event" 
                :error-messages="fieldErrors('ocorrencia.hora')"/>
              </v-flex>

              <v-flex xs12 sm6 md12>
                <v-select v-model="ocorrencia.idFuncionarioRelator" :disabled="anulacao || registraCiencia" :error-messages="fieldErrors('ocorrencia.idFuncionarioRelator')" 
                @blur="$v.ocorrencia.idFuncionarioRelator.$touch()" item-text="nome" item-value="id" :items="funcionarios" label="Relator"></v-select>
              </v-flex>

              <v-flex xs12 sm6 md8>
                <v-select v-model="ocorrencia.idTipoOcorrencia" :disabled="anulacao || registraCiencia" :error-messages="fieldErrors('ocorrencia.idTipoOcorrencia')" 
                @blur="$v.ocorrencia.idTipoOcorrencia.$touch()" item-text="descricao" item-value="id" :items="tiposOcorrencias" 
                @change="carregarDadosTipoOcorrencia(ocorrencia.idTipoOcorrencia)" label="Tipos Ocorrências"></v-select>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Fator" v-model="tipoOcorrencia.nomeFator"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Valor" v-model="tipoOcorrencia.valor"></v-text-field>
              </v-flex>

              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Registro" :disabled="anulacao || registraCiencia" v-model="ocorrencia.registro"
                  :error-messages="fieldErrors('ocorrencia.registro')" @blur="$v.ocorrencia.registro.$touch()"></v-textarea>
              </v-flex>

              <v-flex xs12 sm6 md3 v-if="anulacao || registraCiencia">
                <date-picker-custom label="Dt Ciência" :disabled-date="anulacao"
                :error-messages="fieldErrors('ocorrencia.dataCiencia')" 
                @blur="$v.ocorrencia.dataCiencia.$touch()"
                :v-model="ocorrencia.dataCiencia" @date="dataCiencia = $event"></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm6 md9 v-if="anulacao || registraCiencia">
                <v-select v-model="ocorrencia.idResponsavelCiencia"
                @change="desabilitarCamposEAcoes()" :disabled="anulacao"
                item-text="nomePessoaResponsavel" item-value="id" :items="responsaveisCiencia" label="Resp. Ciência"></v-select>
              </v-flex>

              <v-flex xs12 sm6 md2 v-if="anulacao || registraCiencia">
                <v-checkbox color="primary" disabled label="Anulado" v-model="ocorrencia.anulado"></v-checkbox>
              </v-flex>

              <v-flex xs12 sm6 md3 v-if="anulacao || registraCiencia">
                <date-picker-custom label="Dt Anulação" :disabled-date="registraCiencia" 
                :error-messages="fieldErrors('ocorrencia.dataAnulacao')" 
                @blur="$v.ocorrencia.dataAnulacao.$touch()"
                :v-model="ocorrencia.dataAnulacao" @date="dataAnulacao = $event"></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm6 md7 v-if="anulacao || registraCiencia">
                <v-select v-model="ocorrencia.idFuncionarioAnulacao"
                @change="desabilitarCamposEAcoes()" :disabled="registraCiencia"
                item-text="nome" item-value="id" :items="responsaveisAnulacao" label="Resp. Anulação"></v-select>
              </v-flex>

              <v-flex xs12 sm6 md12 v-if="anulacao || registraCiencia">
                <v-textarea rows="2" :disabled="registraCiencia" cols="5" @change="desabilitarCamposEAcoes()" label="Motivo Anulação" v-model="ocorrencia.motivoAnulacao"></v-textarea>
              </v-flex>

            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" :disabled="bloqueiaCamposEAcoes" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import matriculaService from "@/service/matricula/MatriculaService";
import turmaService from "@/service/turma/TurmaService";
import funcionarioService from "@/service/funcionario/FuncionarioService";
import ocorrenciaService from "@/service/ocorrencia/OcorrenciaService";
import responsavelService from "@/service/pessoa/ResponsavelService";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import TimePickerCustom from "@/components/Custom/TimePickerCustom";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import Ocorrencia from "@/model/ocorrencia/Ocorrencia";
import TipoOcorrencia from "@/model/ocorrencia/TipoOcorrencia";
import { dataFimMenor } from "@/utils/validators";

export default {
  name: "ModalCadastroOcorrencia",
  props: {
    idPessoa: Number
  },
  components: {
    DatePickerCustom,
    TimePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    ocorrencia: Ocorrencia.validations(required, dataFimMenor)
  },
  validationMessages: {
    ocorrencia: Ocorrencia.validationMessages()
  },
  data() {
    return {
      dataCiencia: null,
      bloqueiaCamposEAcoes: false,
      anulacao: false,
      registraCiencia: false,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      dataOcorrencia: null,
      dataAnulacao: null,
      matriculasCursos: [],
      turmas: [],
      funcionarios: [],
      tiposOcorrencias: [],
      responsaveisAnulacao: [],
      responsaveisCiencia: [],
      ocorrencia: new Ocorrencia(),
      tipoOcorrencia: new TipoOcorrencia(),
    };
  },
  watch: {
    dataCiencia() {
      this.ocorrencia.dataCiencia = this.dataCiencia;
      this.desabilitarCamposEAcoes();
    },
    dataAnulacao() {
      this.ocorrencia.dataAnulacao = this.dataAnulacao;
      this.desabilitarCamposEAcoes();
    },
    dataOcorrencia() {
      this.ocorrencia.data = this.dataOcorrencia;
      this.carregarRelatoresETiposOcorrencias(this.ocorrencia.data);
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("registraCienciaOcorrencia", async (idOcorrencia, dataOcorrencia) => {
      this.carregarDadosBasicos();
      this.formTitle = "Registrar Ciência Ocorrência";
      this.btnTitle = "Registrar";
      this.editar(idOcorrencia).then(() => {
        this.desabilitarCamposEAcoes();
      });
      this.carregarResponsaveisAnulacao(dataOcorrencia);
      this.carregarResponsaveisCiencia(dataOcorrencia);
      this.registraCiencia = true;
      this.dialog = true;
    });
    EventBus.$on("anulaOcorrencia", async (idOcorrencia, dataOcorrencia) => {
      this.carregarDadosBasicos();
      this.formTitle = "Anular Ocorrência";
      this.btnTitle = "Anular";
      this.editar(idOcorrencia).then(() => {
        this.desabilitarCamposEAcoes();
      });
      this.carregarResponsaveisAnulacao(dataOcorrencia);
      this.carregarResponsaveisCiencia(dataOcorrencia);
      this.anulacao = true;
      this.dialog = true;
    });
    EventBus.$on("editaOcorrencia", async idOcorrencia => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Ocorrência";
      this.btnTitle = "Alterar";
      this.editar(idOcorrencia);
      this.dialog = true;
    });
  },
  methods: {
    async editar(idOcorrencia) {
      this.ocorrencia = await ocorrenciaService.editar(idOcorrencia);
      this.carregarTurmas(this.ocorrencia.idMatricula);
      this.carregarRelatoresETiposOcorrencias(this.ocorrencia.data);
      this.carregarDadosTipoOcorrencia(this.ocorrencia.idTipoOcorrencia);
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Ocorrências";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    async carregarDadosTipoOcorrencia(idTipoOcorrencia) {
      this.tipoOcorrencia = await ocorrenciaService.editarTipoOcorrencia(idTipoOcorrencia);
    },
    async carregarTurmas(idMatricula) {
      this.turmas = await turmaService.comboParaOcorrenciaPorMatricula(idMatricula);
    },
    async carregarRelatoresETiposOcorrencias(dataOcorrencia) {
      if (dataOcorrencia) {
        this.funcionarios = await funcionarioService.comboParaOcorrenciaRelatores(dataOcorrencia);
        this.tiposOcorrencias = await ocorrenciaService.comboTipoOcorrencia(dataOcorrencia);
      }
    },
    async carregarResponsaveisAnulacao(dataOcorrencia) {
      this.responsaveisAnulacao = await funcionarioService.comboParaOcorrenciaResponsaveisAnulacao(dataOcorrencia);
    },
    async carregarResponsaveisCiencia(dataOcorrencia) {
      this.responsaveisCiencia = await responsavelService.comboParaOcorrenciaResponsaveisCiencia(dataOcorrencia, this.idPessoa);
    },
    salvar() {
      this.$v.ocorrencia.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if (this.anulacao) {
        this.ocorrencia.anulado = true;
      }
      this.ocorrencia.idPessoaSelecionada = this.idPessoa;
      ocorrenciaService.salvar(this.ocorrencia).then((dados) => {
        if(dados) {
          this.dialog = false;
          this.$emit("ocorrenciaCadastrada", true);
        }
      });
    },
    async carregarDadosBasicos() {
      this.matriculasCursos = await matriculaService.comboParaOcorrencia(this.idPessoa);
    },
    desabilitarCamposEAcoes() {
      if(this.anulacao) {
        if(!this.ocorrencia.dataAnulacao
         || !this.ocorrencia.motivoAnulacao
         || !this.ocorrencia.idFuncionarioAnulacao) {
          this.bloqueiaCamposEAcoes = true;
        } else {
          this.bloqueiaCamposEAcoes = false;
        }
      }
      if (this.registraCiencia) {
        if(!this.ocorrencia.dataCiencia
         || !this.ocorrencia.idResponsavelCiencia) {
          this.bloqueiaCamposEAcoes = true;
        } else {
          this.bloqueiaCamposEAcoes = false;
        }
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.ocorrencia = new Ocorrencia();
        this.tipoOcorrencia = new TipoOcorrencia();
        this.turmas = [];
        this.matriculasCursos = [];
        this.funcionarios = [];
        this.tiposOcorrencias = [];
        this.anulacao = false;
        this.bloqueiaCamposEAcoes = false;
        this.responsaveisCiencia = [];
        this.registraCiencia = false;
        this.dataAnulacao = null;
        this.dataCiencia = null;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
