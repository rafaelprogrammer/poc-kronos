<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Disciplina" @click="abrirCadastro()" title="Incluir Disciplina"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md9>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="siglaPeriodo" disabled
                  label="Sigla do Período"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="disciplina.codigo" :error-messages="fieldErrors('disciplina.codigo')"
                  label="Código"  @blur="$v.disciplina.codigo.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="disciplina.nome" :error-messages="fieldErrors('disciplina.nome')"
                  label="Nome"  @blur="$v.disciplina.nome.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="disciplina.sigla" :error-messages="fieldErrors('disciplina.sigla')"
                  label="Sigla"  @blur="$v.disciplina.sigla.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="disciplina.idTipoDisciplina" :error-messages="fieldErrors('disciplina.idTipoDisciplina')"
                  @blur="$v.disciplina.idTipoDisciplina.$touch()" item-text="nome" item-value="id" :items="tiposDisciplinas" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="disciplina.idTipoRegimeLetivo" :error-messages="fieldErrors('disciplina.idTipoRegimeLetivo')"
                  @blur="$v.disciplina.idTipoRegimeLetivo.$touch()" item-text="nome" item-value="id" :items="tiposRegimesLetivos" label="Regime Letivo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-select v-model="disciplina.idComponente" item-text="nome" item-value="id" :items="componentes" label="Componente"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="disciplina.cargaHorariaTotal" :error-messages="fieldErrors('disciplina.cargaHorariaTotal')"
                  label="Carga Horária Total"  @blur="$v.disciplina.cargaHorariaTotal.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="disciplina.cargaHorariaPresencial" :error-messages="fieldErrors('disciplina.cargaHorariaPresencial')"
                  label="Carga Horária Presencial"  @blur="$v.disciplina.cargaHorariaPresencial.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="disciplina.cargaHorariaDistancia" :error-messages="fieldErrors('disciplina.cargaHorariaDistancia')"
                  label="Carga Horária Distância"  @blur="$v.disciplina.cargaHorariaDistancia.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Inicio Vigência" :v-model="disciplina.dataInicioVigencia" @date="disciplina.dataInicioVigencia = $event" 
                :error-messages="fieldErrors('disciplina.dataInicioVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Final Vigência" :v-model="disciplina.dataFinalVigencia" @date="disciplina.dataFinalVigencia = $event" 
                :error-messages="fieldErrors('disciplina.dataFinalVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="disciplina.valor" :error-messages="fieldErrors('disciplina.valor')"
                  label="Valor"  @blur="$v.disciplina.valor.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-checkbox type="checkbox" label="Pré-Requisitos" v-model="disciplina.preRequisitos"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox type="checkbox" label="Obrigatória" v-model="disciplina.obrigatoria"></v-checkbox>
              </v-flex>
               <v-flex xs12 sm6 md2>
                <v-checkbox type="checkbox" label="Ativa" v-model="disciplina.ativa"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select v-model="disciplina.idDisciplinaUnificacao" item-text="nome" item-value="id" :items="disciplinasUnificacoes" label="Disciplina Unificação Diário"></v-select>
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
import baseCurricularService from "@/service/basecurricular/BaseCurricularService"
import cursoService from "@/service/curso/CursoService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import Disciplina from "@/model/disciplina/Disciplina";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import {
  required,
  maxLength,
  numeric,
  decimal
} from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroDisciplina",
  components: {
    DatePickerCustom
  },
  props: {
    idPeriodo: Number,
    nomeCurso: String,
    siglaPeriodo: String,
    idNivel: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    disciplina: Disciplina.validations(
      required,
      maxLength,
      numeric,
      decimal,
      dataFimMenor
    )
  },
  validationMessages: {
    disciplina: Disciplina.validationMessages()
  },
  data() {
    return {
      componentes: [],
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposDisciplinas: [],
      tiposRegimesLetivos: [],
      disciplinasUnificacoes: [],
      disciplina: new Disciplina()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaDisciplina", async idDisciplina => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Disciplina";
      this.btnTitle = "Alterar";
      this.disciplina = await disciplinaService.editar(idDisciplina);
      this.carregarDisciplinasUnificacoes(this.disciplina.id, this.disciplina.idPeriodo);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.carregarDisciplinasUnificacoes(null, this.idPeriodo);
      this.setaIdPeriodo();
      this.formTitle = "Cadastrar Disciplina";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.disciplina.$touch();
      if (this.$v.$invalid) {
        return;
      }
      disciplinaService.salvar(this.disciplina).then(() => {
        this.$emit("disciplinaCadastrada", true);
        this.dialog = false;
      });
    },
    async carregarDisciplinasUnificacoes(id, idPeriodo) {
      this.disciplinasUnificacoes = await disciplinaService.comboUnificacoes(id, idPeriodo);
      if (this.disciplinasUnificacoes && this.disciplinasUnificacoes.length > 0) {
        this.disciplinasUnificacoes.unshift({id: null, nome: "", idDisciplinaUnificacao: null})
      }
     },
    async carregarDadosBasicos() {
      this.tiposDisciplinas = await disciplinaService.tipos();
      this.tiposRegimesLetivos = await cursoService.tiposRegimesLetivos();
      this.componentes = await baseCurricularService.comboComponentes(this.idNivel);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.disciplina = new Disciplina();
        EventBus.$emit("resetCustom");
      }, 300);
    },
    setaIdPeriodo() {
      this.disciplina.idPeriodo = this.idPeriodo;
    }
  }
};
</script>
