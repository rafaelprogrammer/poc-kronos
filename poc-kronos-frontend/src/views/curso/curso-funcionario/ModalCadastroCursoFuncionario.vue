<template>
  <v-layout row justify-center>
    <modal-seleciona-funcionario></modal-seleciona-funcionario>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Curso do Funcionário" @click="abrirCadastro()" title="Incluir Curso do Funcionário"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="cursoFuncionario.idTipoFuncao" :error-messages="fieldErrors('cursoFuncionario.idTipoFuncao')"
                  @blur="$v.cursoFuncionario.idTipoFuncao.$touch()" item-text="nome" item-value="id" :items="tiposFuncoes" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Início" :v-model="cursoFuncionario.dataInicio" @date="cursoFuncionario.dataInicio = $event" 
                :error-messages="fieldErrors('cursoFuncionario.dataInicio')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final" :v-model="cursoFuncionario.dataFinal" @date="cursoFuncionario.dataFinal = $event" 
                :error-messages="fieldErrors('cursoFuncionario.dataFinal')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md10>
                <v-text-field v-model="cursoFuncionario.nomeFuncionario" disabled label="Funcionário" :error-messages="fieldErrors('cursoFuncionario.idFuncionario')"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-btn color="primary" fab small dark alt="Selecionar Funcionário" :loading="loading" @click="selecionarFuncionario()" title="Selecionar Funcionário"><v-icon>find_in_page</v-icon></v-btn>
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
import cursoFuncionarioService from "@/service/curso/CursoFuncionarioService";
import CursoFuncionario from "@/model/curso/curso-funcionario/CursoFuncionario";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import ModalSelecionaFuncionario from "@/views/funcionario/ModalSelecionaFuncionario";
export default {
  name: "ModalCadastroCursoFuncionario",
  components: {
    DatePickerCustom,
    ModalSelecionaFuncionario
  },
  props: {
    idCurso: Number,
    nomeCurso: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    cursoFuncionario: CursoFuncionario.validations(required, dataFimMenor)
  },
  validationMessages: {
    cursoFuncionario: CursoFuncionario.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposFuncoes: [],
      cursoFuncionario: new CursoFuncionario()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaCursoFuncionario", async idCursoFuncionario => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Função do Curso";
      this.btnTitle = "Alterar";
      this.cursoFuncionario = await cursoFuncionarioService.editar(
        idCursoFuncionario
      );
      this.dialog = true;
    });
    EventBus.$on("selecionaFuncionario", funcionario => {
      this.cursoFuncionario.idFuncionario = funcionario.id;
      this.cursoFuncionario.nomeFuncionario = funcionario.nome;
    });
  },
  methods: {
    selecionarFuncionario() {
      EventBus.$emit("abrirSelecionaFuncionario");
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIdCurso();
      this.formTitle = "Cadastrar Função do Curso";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.cursoFuncionario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      cursoFuncionarioService.salvar(this.cursoFuncionario).then(() => {
        this.$emit("cursoFuncionarioCadastrado", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.tiposFuncoes = await cursoFuncionarioService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.cursoFuncionario = new CursoFuncionario();
        EventBus.$emit("resetCustom");
      }, 300);
    },
    setaIdCurso() {
      this.cursoFuncionario.idCurso = this.idCurso;
    }
  }
};
</script>
