<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Filiação" @click="abrirCadastro()" title="Incluir Filiação"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md8>
                <v-text-field v-model="curso.nome" :error-messages="fieldErrors('curso.nome')"
                  label="Nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.sigla" :error-messages="fieldErrors('curso.sigla')"
                  label="Sigla"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="curso.idNivel" :error-messages="fieldErrors('curso.idNivel')"
                  @blur="$v.curso.idNivel.$touch()" item-text="nome" item-value="id" :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="curso.idTipoRegimeLetivo" :error-messages="fieldErrors('curso.idTipoRegimeLetivo')"
                  @blur="$v.curso.idTipoRegimeLetivo.$touch()" item-text="nome" item-value="id" :items="tiposRegimesLetivos" label="Regime Letivo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="curso.idTipoTurno" :error-messages="fieldErrors('curso.idTipoTurno')"
                  @blur="$v.curso.idTipoTurno.$touch()" item-text="nome" item-value="id" :items="tiposTurnos" label="Turno"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Inicio Vigência" :v-model="curso.dataInicioVigencia" @date="curso.dataInicioVigencia = $event" 
                :error-messages="fieldErrors('curso.dataInicioVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Final Vigência" :v-model="curso.dataFinalVigencia" @date="curso.dataFinalVigencia = $event" 
                :error-messages="fieldErrors('curso.dataFinalVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Limite Conclusão" :v-model="curso.dataLimiteConclusao" @date="curso.dataLimiteConclusao = $event" 
                :error-messages="fieldErrors('curso.dataLimiteConclusao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="curso.tituloMasculino" :error-messages="fieldErrors('curso.tituloMasculino')"
                  label="Título Masculino"  @blur="$v.curso.tituloMasculino.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="curso.tituloFeminino" :error-messages="fieldErrors('curso.tituloFeminino')"
                  label="Título Feminino"  @blur="$v.curso.tituloFeminino.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.tempoMaximoConclusao" :error-messages="fieldErrors('curso.tempoMaximoConclusao')"
                  label="Tempo Máximo Conclusão"  @blur="$v.curso.tempoMaximoConclusao.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.tempoMaximoDescontinuo" :error-messages="fieldErrors('curso.tempoMaximoDescontinuo')"
                  label="Tempo Máximo Descontínuo" @blur="$v.curso.tempoMaximoDescontinuo.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.cargaHoraria" :error-messages="fieldErrors('curso.cargaHoraria')"
                  label="Carga Horária" @blur="$v.curso.cargaHoraria.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.notaMinimaAprovacaoDireta" :error-messages="fieldErrors('curso.notaMinimaAprovacaoDireta')"
                  label="Nota Mínima Aprovação Direta" @blur="$v.curso.notaMinimaAprovacaoDireta.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="curso.notaMinimaAprovacaoRecuperacao" :error-messages="fieldErrors('curso.notaMinimaAprovacaoRecuperacao')"
                  label="Nota Mínima Aprovação Recuperação" @blur="$v.curso.notaMinimaAprovacaoRecuperacao.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="curso.idTipoMatrizHorario" :error-messages="fieldErrors('curso.idTipoMatrizHorario')"
                  @blur="$v.curso.idTipoMatrizHorario.$touch()" item-text="nome" item-value="id" :items="tiposMatrizesHorarios" label="Matriz Horário"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-spacer></v-spacer>
                <v-checkbox type="checkbox" label="Ativo" v-model="curso.ativo"></v-checkbox>
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
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Curso from "@/model/curso/Curso";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroCurso",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    curso: Curso.validations(required, maxLength, numeric, dataFimMenor)
  },
  validationMessages: {
    curso: Curso.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposTurnos: [],
      tiposRegimesLetivos: [],
      tiposMatrizesHorarios: [],
      niveis: [],
      curso: new Curso()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.carregarDadosBasicos();
    this.setarIdInstituicao();
    EventBus.$on("editaCurso", async idCurso => {
      this.formTitle = "Alterar Curso";
      this.btnTitle = "Alterar";
      this.curso = await cursoService.editar(idCurso);
      this.dialog = true;
    });
  },
  methods: {
    async abrirCadastro() {
      this.formTitle = "Cadastrar Curso";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.curso.$touch();
      if (this.$v.$invalid) {
        return;
      }
      cursoService.salvar(this.curso);
      this.$emit("cursoCadastrado", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.tiposTurnos = await cursoService.tiposTurnos();
      this.tiposRegimesLetivos = await cursoService.tiposRegimesLetivos();
      this.tiposMatrizesHorarios = await cursoService.comboParaTiposMatrizesHorarios();
      this.niveis = await baseCurricularService.comboNiveis();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.curso = new Curso();
        this.setarIdInstituicao();
        EventBus.$emit("resetCustom");
      }, 300);
    },
    setarIdInstituicao() {
      this.curso.idInstituicao = this.$store.getters.user.idInstituicao;
    }
  }
};
</script>
