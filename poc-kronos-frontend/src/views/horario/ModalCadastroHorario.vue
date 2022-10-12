<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent fullscreen>
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Horário" @click="abrirCadastro()" title="Incluir Horário"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
          <span class="headline">{{formTitle}}</span>
          <v-container fluid>
            <v-form ref="form">
              <v-layout wrap justify-space-between>
                <v-flex xs12 sm6 md1>
                  <v-text-field v-model="ano" disabled
                    label="Ano"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="nomeCurso" disabled
                    label="Curso"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field v-model="siglaPeriodo" disabled
                    label="Período"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field v-model="siglaTurma" disabled
                    label="Turma"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <date-picker-custom :disabled-date="edicao" label="Data Início" :v-model="horario.dataInicial" @date="horario.dataInicial = $event" 
                  :error-messages="fieldErrors('horario.dataInicial')"></date-picker-custom>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <date-picker-custom :disabled-date="edicao" label="Data Final" :v-model="horario.dataFinal" @date="horario.dataFinal = $event" 
                  :error-messages="fieldErrors('horario.dataFinal')"></date-picker-custom>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-select :disabled="edicao" v-model="horario.idDisciplina" :error-messages="fieldErrors('horario.idDisciplina')"
                    @blur="$v.horario.idDisciplina.$touch()" item-text="sigla" item-value="id"
                    :items="disciplinas" label="Disciplina"
                    @change="carregarProfessores(horario.idDisciplina, false)"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-select :disabled="edicao" v-model="horario.idFuncionario" :error-messages="fieldErrors('horario.idFuncionario')"
                    @blur="$v.horario.idFuncionario.$touch()" item-text="nome" item-value="id" :items="professores" label="Professor"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-select :disabled="edicao" v-model="horario.idTipoRegimeLetivo" :error-messages="fieldErrors('horario.idTipoRegimeLetivo')"
                    @blur="$v.horario.idTipoRegimeLetivo.$touch()" item-text="nome" item-value="id" :items="tiposRegimesLetivos" label="Regime Letivo"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2 class="text-xs-right">
                  <v-btn :disabled="edicao" color="primary" left direction="right" alt="Listar Horários" @click="listarHorarios()" title="Listar Horários"><v-icon>settings</v-icon>Listar Horários</v-btn>
                </v-flex>
              </v-layout>
              <v-layout>
                <v-flex xs12 sm6 md2>
                  <v-chip color="primary" text-color="white">Domigo</v-chip>
                  <v-list-tile v-for="domingo in horariosDiaDaSemana.domingo" :key="domingo.idAtividade">
                    <v-checkbox color="primary" :label="domingo.horaInicial + ' - ' + domingo.horaFinal + domingo.situacao" :disabled="domingo.bloqueado" v-model="domingo.selecionado"></v-checkbox>
                  </v-list-tile>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-chip color="primary" text-color="white">Segunda</v-chip>
                  <v-list-tile v-for="segunda in horariosDiaDaSemana.segunda" :key="segunda.idAtividade">
                    <v-checkbox color="primary" :label="segunda.horaInicial + ' - ' + segunda.horaFinal + segunda.situacao" :disabled="segunda.bloqueado" v-model="segunda.selecionado"></v-checkbox>
                  </v-list-tile>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <div>
                    <v-chip color="primary" text-color="white">Terça</v-chip>
                    <v-list-tile v-for="terca in horariosDiaDaSemana.terca" :key="terca.idAtividade">
                      <v-checkbox color="primary" :label="terca.horaInicial + ' - ' + terca.horaFinal + terca.situacao" :disabled="terca.bloqueado" v-model="terca.selecionado"></v-checkbox>
                    </v-list-tile>
                  </div>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-chip color="primary" text-color="white">Quarta</v-chip>
                  <v-list-tile v-for="quarta in horariosDiaDaSemana.quarta" :key="quarta.idAtividade">
                    <v-checkbox color="primary" :label="quarta.horaInicial + ' - ' + quarta.horaFinal + quarta.situacao" :disabled="quarta.bloqueado" v-model="quarta.selecionado"></v-checkbox>
                  </v-list-tile>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <div>
                    <v-chip color="primary" text-color="white">Quinta</v-chip>
                    <v-list-tile v-for="quinta in horariosDiaDaSemana.quinta" :key="quinta.idAtividade">
                      <v-checkbox color="primary" :label="quinta.horaInicial + ' - ' + quinta.horaFinal + quinta.situacao" :disabled="quinta.bloqueado" v-model="quinta.selecionado"></v-checkbox>
                    </v-list-tile>
                  </div>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <div>
                    <v-chip color="primary" text-color="white">Sexta</v-chip>
                    <v-list-tile v-for="sexta in horariosDiaDaSemana.sexta" :key="sexta.idAtividade">
                      <v-checkbox color="primary" :label="sexta.horaInicial + ' - ' + sexta.horaFinal + sexta.situacao" :disabled="sexta.bloqueado" v-model="sexta.selecionado"></v-checkbox>
                    </v-list-tile>
                  </div>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <div>
                    <v-chip color="primary" text-color="white">Sábado</v-chip>
                    <v-list-tile v-for="sabado in horariosDiaDaSemana.sabado" :key="sabado.idAtividade">
                      <v-checkbox color="primary" :label="sabado.horaInicial + ' - ' + sabado.horaFinal + sabado.situacao" :disabled="sabado.bloqueado" v-model="sabado.selecionado"></v-checkbox>
                    </v-list-tile>
                  </div>
                </v-flex>
              </v-layout>
            </v-form>
          </v-container>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" v-show="!this.horariosDiaDaSemana.isEmpty()" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import horarioService from "@/service/horario/HorarioService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import funcionarioService from "@/service/funcionario/FuncionarioService";
import cursoService from "@/service/curso/CursoService";
import Horario from "@/model/horario/Horario";
import { required } from "vuelidate/lib/validators";
import { dataFimMenor, horaFinalMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import HorariosDiaDaSemana from "@/model/horario/HorariosDiaDaSemana";
export default {
  name: "ModalCadastroHorario",
  components: {
    DatePickerCustom
  },
  props: {
    ano: Number,
    nomeCurso: String,
    idTipoMatrizHorario: Number,
    idPeriodoExecucao: Number,
    siglaPeriodo: String,
    idTurma: Number,
    siglaTurma: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    horario: Horario.validations(required, horaFinalMenor, dataFimMenor)
  },
  validationMessages: {
    horario: Horario.validationMessages()
  },
  data() {
    return {
      inclusao: true,
      edicao: false,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      disciplinas: [],
      professores: [],
      tiposRegimesLetivos: [],
      horario: new Horario(),
      horariosDiaDaSemana: new HorariosDiaDaSemana()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaHorario", async horario => {
      this.carregarDadosBasicos();
      this.inclusao = false;
      this.formTitle = "Alterar Horário";
      this.btnTitle = "Alterar";
      this.carregarProfessores(horario.idDisciplina, true);
      this.horario = horario;
      this.listarHorarios();
      this.edicao = true;
      setTimeout(() => {
        this.dialog = true;
      }, 500);
    });
  },
  methods: {
    async carregarProfessores(idDisciplina, edicao) {
      this.professores = await funcionarioService.comboListarPorDisciplina(idDisciplina);
      if (!edicao) {
        this.horario.idFuncionario = null;
      }
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Horário";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    async listarHorarios() {
      this.horario.idTipoMatrizHorario = this.idTipoMatrizHorario;
      this.horario.idTurma = this.idTurma;
      this.$v.horario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.horariosDiaDaSemana = await horarioService.listarHorasAtividadesDiaDaSemana(this.horario, this.inclusao);
    },
    salvar() {
      this.$v.horario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      horarioService.salvar(this.horario, this.horariosDiaDaSemana).then(() => {
        this.$emit("horarioCadastrado", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.disciplinas = await disciplinaService.comboListarPorPeriodoExecucao(this.idPeriodoExecucao);
      this.tiposRegimesLetivos = await cursoService.tiposRegimesLetivos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.horario = new Horario();
        this.horariosDiaDaSemana = new HorariosDiaDaSemana();
        this.edicao = false;
        this.inclusao = false;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
