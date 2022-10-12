<template>
  <v-layout row justify-center>
    <modal-seleciona-pessoa v-bind:botao-seleciona="false"></modal-seleciona-pessoa>
    <modal-seleciona-empresa></modal-seleciona-empresa>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" absolute dark fab top right rightclass="mb-2" alt="Incluir Disciplina" @click="abrirCadastro()" title="Incluir Disciplina"><v-icon>add</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm2 md4>
                <v-text-field color="primary" disabled label="Nr Registro" v-model="pessoa.numeroRegistro" :error-messages="fieldErrors('matricula.idPessoa')"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-btn color="primary" fab small dark alt="Selecionar Pessoa" @click="abrirSelecionaPessoa()" title="Selecionar Pessoa"><v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm2 md3>
                <v-text-field color="primary" disabled label="CPF" v-model="pessoa.cpf"></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Data Nascimento" v-model="pessoa.dataNascimento"></v-text-field>
              </v-flex>
              <v-flex xs12 sm1 md2>
                <v-select v-model="pessoa.idGenero" disabled item-text="sigla" item-value="id" :items="tiposGeneros" label="Gênero"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field v-model="empresa.nomeFantasia" disabled :error-messages="fieldErrors('matricula.idEmpresaOrigem')"
                  label="Instituição de Origem"  @blur="$v.matricula.idEmpresaOrigem.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-btn color="primary" fab small dark alt="Selecionar Instituição de Origem" @click="abrirSelecionaEmpresa()" title="Selecionar Instituição de Origem"><v-icon>find_in_page</v-icon></v-btn>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-select v-model="matricula.idCurso" :error-messages="fieldErrors('matricula.idCurso')"
                  @blur="$v.matricula.idCurso.$touch()" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Matrícula" :v-model="matricula.data" @date="matricula.data = $event" 
                :error-messages="fieldErrors('matricula.data')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="matricula.anoInicioCurso" :error-messages="fieldErrors('matricula.anoInicioCurso')"
                  label="Ano Início"  @blur="$v.matricula.anoInicioCurso.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="matricula.semestreInicioCurso" :error-messages="fieldErrors('matricula.semestreInicioCurso')"
                  label="Semestre Início"  @blur="$v.matricula.semestreInicioCurso.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Início Curso" :v-model="matricula.dataInicioCurso" @date="matricula.dataInicioCurso = $event" 
                :error-messages="fieldErrors('matricula.dataInicioCurso')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Conclusão Curso" :v-model="matricula.dataConclusaoCurso" @date="matricula.dataConclusaoCurso = $event" 
                :error-messages="fieldErrors('matricula.dataConclusaoCurso')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="matricula.anoConclusaoCurso" :error-messages="fieldErrors('matricula.anoConclusaoCurso')"
                  label="Ano Conclusão"  @blur="$v.matricula.anoConclusaoCurso.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="matricula.semestreConclusaoCurso" :error-messages="fieldErrors('matricula.semestreConclusaoCurso')"
                  label="Semestre Conclusão"  @blur="$v.matricula.semestreConclusaoCurso.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="matricula.idTipoResultado" :error-messages="fieldErrors('matricula.idTipoResultado')"
                  @blur="$v.matricula.idTipoResultado.$touch()" item-text="nome" item-value="id" :items="tiposResultados" label="Resultado"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Colação Grau" :v-model="matricula.dataColacaoGrau" @date="matricula.dataColacaoGrau = $event" 
                :error-messages="fieldErrors('matricula.dataColacaoGrau')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-select :disabled="matricula.id" v-model="matricula.idTipoSituacaoMatricula" item-text="nome" item-value="id" :items="tiposSituacoesMatriculas" label="Situação Matrícula"></v-select>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import cursoService from "@/service/curso/CursoService";
import matriculaService from "@/service/matricula/MatriculaService";
import Matricula from "@/model/matricula/Matricula";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, numeric } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import ModalSelecionaPessoa from "../pessoa/ModalSelecionaPessoa";
import ModalSelecionaEmpresa from "../empresa/ModalSelecionaEmpresa";
import Pessoa from "@/model/pessoa/Pessoa";
import Empresa from "@/model/empresa/Empresa";
export default {
  name: "ModalCadastroMatricula",
  props: {
    tiposGeneros: Array
  },
  components: {
    DatePickerCustom,
    ModalSelecionaPessoa,
    ModalSelecionaEmpresa
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    matricula: Matricula.validations(required, numeric, dataFimMenor)
  },
  validationMessages: {
    matricula: Matricula.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      editaMatricula: false,
      pessoa: {},
      empresa: {},
      cursos: [],
      tiposSituacoesMatriculas: [],
      tiposResultados: [],
      matricula: new Matricula()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("selecionaPessoa", dados => {
      this.pessoa = new Pessoa(dados);
      this.matricula.idPessoa = this.pessoa.id;
    });
    EventBus.$on("selecionaEmpresa", dados => {
      this.empresa = new Empresa(dados);
      this.matricula.idEmpresaOrigem = this.empresa.id;
    });
    EventBus.$on("editaMatricula", async idMatricula => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Matrícula";
      this.btnTitle = "Alterar";
      this.matricula = await matriculaService.editar(idMatricula);
      this.pessoa.cpf = this.matricula.cpfPessoa;
      this.pessoa.numeroRegistro = this.matricula.numeroRegistroPessoa;
      this.pessoa.idGenero = this.matricula.idGeneroPessoa;
      this.empresa.nomeFantasia = this.matricula.nomeInstituicaoOrigem;
      this.editaMatricula = true;
      this.dialog = true;
    });
  },
  methods: {
    abrirSelecionaEmpresa() {
      EventBus.$emit("abrirSelecionaEmpresa");
    },
    abrirSelecionaPessoa() {
      EventBus.$emit("abrirSelecionaPessoa");
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Matrícula";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.matricula.$touch();
      if (this.$v.$invalid) {
        return;
      }
      matriculaService.salvar(this.matricula).then((dados) => {
        if(dados) {
          this.dialog = false;
          if (this.editaMatricula) {
            EventBus.$emit("selecionaMatricula", this.matricula);
          }
        }
      });
    },
    async carregarDadosBasicos() {
      this.tiposSituacoesMatriculas = await matriculaService.tiposSituacoesMatriculas();
      this.tiposResultados = await matriculaService.tiposResultados();
      this.cursos = await cursoService.combo();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.matricula = new Matricula();
        this.pessoa = {};
        this.empresa = {};
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
