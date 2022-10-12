<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary"  dark alt="Incluir Evento" @click="abrirCadastro()" title="Incluir Evento"><v-icon>add</v-icon>Evento</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Início" :v-model="evento.dataInicio" @date="evento.dataInicio = $event" 
                :error-messages="fieldErrors('evento.dataInicio')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Final" :v-model="evento.dataFinal" @date="evento.dataFinal = $event" 
                :error-messages="fieldErrors('evento.dataFinal')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Dia Todo" v-model="evento.diaTodo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Dia Letivo" v-model="evento.diaLetivo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm2 md3>
                <time-picker-custom label="Hora Início" :v-model="evento.horaInicio" @time="evento.horaInicio = $event" 
                :error-messages="fieldErrors('evento.horaInicio')"/>
              </v-flex>
              <v-flex xs12 sm2 md4>
                <time-picker-custom label="Hora Final" :v-model="evento.horaFinal" @time="evento.horaFinal = $event" 
                :error-messages="fieldErrors('evento.horaFinal')"/>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select v-model="evento.idCategoriaEvento" item-text="nome" 
                item-value="id" :items="categorias" label="Categorias"
                :error-messages="fieldErrors('evento.idCategoriaEvento')" @blur="$v.evento.idCategoriaEvento.$touch()"></v-select>
              </v-flex>
              <v-flex xs12 sm2 md12>
                <v-text-field color="primary" label="Descrição" v-model="evento.descricao"
                :error-messages="fieldErrors('evento.descricao')" @blur="$v.evento.descricao.$touch()"></v-text-field>
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
import eventoService from "@/service/calendario/EventoService";
import Evento from "@/model/calendario/Evento";
import { required, maxLength } from "vuelidate/lib/validators";
import { dataFimMenor, horaFinalMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import TimePickerCustom from "@/components/Custom/TimePickerCustom";
export default {
  name: "ModalCadastroEvento",
  props: {
    idCalendario: Number,
    dataInicioCalendario: String,
    dataFinalCalendario: String
  },
  components: {
    DatePickerCustom,
    TimePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    evento: Evento.validations(required, horaFinalMenor, maxLength, dataFimMenor)
  },
  validationMessages: {
    evento: Evento.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      categorias: [],
      evento: new Evento()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Evento";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.evento.idCalendario = this.idCalendario;
      this.$v.evento.$touch();
      if (this.$v.$invalid) {
        return;
      }
      if (!this.evento.validarIntervaloCalendario(this.dataInicioCalendario, this.dataFinalCalendario)) {
          this.error("O período do evento deve ser de acordo com o período do calendário");
          return;
      }
      this.evento.converterEntradaDataHora();
      eventoService.salvar(this.evento).then((dados) => {
        if(dados) {
          this.dialog = false;
          this.$emit("eventoCadastrado", true);
        }
      });
    },
    async carregarDadosBasicos() {
      this.categorias = await eventoService.categorias();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.evento = new Evento();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
