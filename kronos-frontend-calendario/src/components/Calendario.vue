<template>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="64">
        <v-toolbar flat color="white">
          <v-btn outlined class="mr-4" @click="setToday">
            Hoje
          </v-btn>
          <v-btn fab text small @click="prev">
            <v-icon small>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn fab text small @click="next">
            <v-icon small>mdi-chevron-right</v-icon>
          </v-btn>
          <v-toolbar-title>{{ title }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-menu bottom right>
            <template v-slot:activator="{ on }">
              <v-btn
                outlined
                v-on="on"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon right>mdi-menu-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day'">
                <v-list-item-title>Dia</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week'">
                <v-list-item-title>Semana</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month'">
                <v-list-item-title>Mês</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = '4day'">
                <v-list-item-title>4 dias</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar locale="pt-br" ref="calendar" v-model="focus" color="primary" :events="events"
        :event-color="getEventColor" :event-margin-bottom="3" :now="today" :type="type"
        @click:event="showEvent" @click:more="viewDay" @click:date="viewDay" @change="updateRange"
        :interval-format="intervalFormat"></v-calendar>
        <v-menu v-model="selectedOpen" :close-on-content-click="false" :activator="selectedElement"
        full-width offset-x>
          <v-card color="grey lighten-4" flat>
            <v-toolbar :color="selectedEvent.color" dark>
              <v-toolbar-title>Edita Evento</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form ref="form">
                <v-container class="grey lighten-5">
                  <v-row no-gutters>
                    <v-col cols="3">
                      <date-picker-custom label="Data Início" :v-model="selectedEvent.dataInicio"
                      @date="selectedEvent.dataInicio = $event"
                      :error-messages="fieldErrors('selectedEvent.dataInicio')"
                      ></date-picker-custom>
                    </v-col>
                    <v-col cols="3">
                      <date-picker-custom label="Data Final" :v-model="selectedEvent.dataFinal"
                      @date="selectedEvent.dataFinal = $event"
                      :error-messages="fieldErrors('selectedEvent.dataFinal')"></date-picker-custom>
                    </v-col>
                    <v-col cols="4">
                      <time-picker-custom label="Hora Início" :v-model="selectedEvent.horaInicio"
                      @time="selectedEvent.horaInicio = $event"
                      :error-messages="fieldErrors('selectedEvent.horaInicio')"/>
                    </v-col>
                    <v-col cols="4">
                      <time-picker-custom label="Hora Fim" :v-model="selectedEvent.horaFinal"
                      @time="selectedEvent.horaFinal = $event"
                      :error-messages="fieldErrors('selectedEvent.horaFinal')"/>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field v-model="selectedEvent.descricao"
                      :error-messages="fieldErrors('selectedEvent.descricao')"
                      @blur="$v.selectedEvent.descricao.$touch()"
                      label="Descrição"></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-select v-model="selectedEvent.idCategoriaEvento"
                      item-text="nome" item-value="id"
                      :items="categorias" label="Categorias"></v-select>
                    </v-col>
                    <v-col cols="3">
                      <v-checkbox v-model="selectedEvent.diaTodo" label="Dia todo"></v-checkbox>
                    </v-col>
                    <v-col cols="3">
                      <v-checkbox v-model="selectedEvent.diaLetivo" label="Dia letivo"></v-checkbox>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-btn color="secondary" type="submit" @click.native="excluir" dark>Excluir</v-btn>
              <v-btn text color="secondary" @click="close()">
                Cancel
              </v-btn>
              <v-btn color="primary" type="submit" @click.native="alterar" dark>Alterar</v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </v-sheet>
    </v-col>
  </v-row>
</template>
<script>
import { required, maxLength } from 'vuelidate/lib/validators';
import { dataFimMenor, horaFinalMenor } from '@/utils/validators';
import eventoService from '@/service/EventoService';
import calendarioService from '@/service/CalendarioService';
import EventoCalendario from '@/model/EventoCalendario';
import validationMixin from '@/mixins/validationMixin';
import messageMixin from '@/mixins/message';
import DatePickerCustom from '@/components/Custom/DatePickerCustom.vue';
import TimePickerCustom from '@/components/Custom/TimePickerCustom.vue';
import EventBus from '@/utils/EventBus';

export default {
  props: {
    id: Object,
  },
  components: {
    DatePickerCustom,
    TimePickerCustom,
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    selectedEvent: EventoCalendario.validations(required, horaFinalMenor, maxLength, dataFimMenor),
  },
  validationMessages: {
    selectedEvent: EventoCalendario.validationMessages(),
  },
  data: () => ({
    evento: new EventoCalendario(),
    categorias: [],
    today: null,
    focus: null,
    type: 'month',
    typeToLabel: {
      month: 'Mês',
      week: 'Semana',
      day: 'Dia',
      '4day': '4 Dias',
    },
    start: null,
    end: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
  }),
  computed: {
    title() {
      const { start, end } = this;
      if (!start || !end) {
        return '';
      }
      const startMonth = this.monthFormatter(start);
      const endMonth = this.monthFormatter(end);
      const suffixMonth = startMonth === endMonth ? '' : endMonth;

      const startYear = start.year;
      const endYear = end.year;
      const suffixYear = startYear === endYear ? '' : endYear;

      const startDay = start.day + this.nth(start.day);
      const endDay = end.day + this.nth(end.day);

      switch (this.type) {
        case 'month':
          return `${startMonth} ${startYear}`;
        case 'week':
        case '4day':
          return `${startMonth} ${startDay} ${startYear} - ${suffixMonth} ${endDay} ${suffixYear}`;
        case 'day':
          return `${startMonth} ${startDay} ${startYear}`;
        default:
          break;
      }
      return '';
    },
    monthFormatter() {
      return this.$refs.calendar.getFormatter({
        timeZone: 'UTC', month: 'long',
      });
    },
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },
  async created() {
    this.evento.idCalendario = this.$route.params.id;
    this.events = await eventoService.listarTodos(this.evento);
    this.categorias = await eventoService.categorias();
  },
  methods: {
    excluir() {
      // eslint-disable-next-line no-restricted-globals
      // eslint-disable-next-line
      confirm('Você deseja realmente excluir o Evento?')
      && eventoService.excluir(this.selectedEvent).then(() => {
        this.close();
        setTimeout(async () => {
          this.events = await eventoService.listarTodos(this.evento);
        }, 500);
      });
    },
    async alterar() {
      this.$v.selectedEvent.$touch();
      if (this.$v.$invalid) {
        return;
      }
      const calendario = await calendarioService.editar(this.evento.idCalendario);
      if (!this.selectedEvent.validarIntervaloCalendario(
        calendario.dataInicio,
        calendario.dataFinal,
      )) {
        this.error('O período do evento deve ser de acordo com o período do calendário');
        return;
      }
      this.selectedEvent.converterEntradaDataHora();
      eventoService.salvar(this.selectedEvent).then(async () => {
        this.close();
        this.events = await eventoService.listarTodos(this.evento);
        // Caso o intervalo ultrapasse o mes, e necessario fazer um reload da pagina
        if (!EventoCalendario.intervaloMesmoMes(this.selectedEvent.dataInicio,
          this.selectedEvent.dataFinal)) {
          this.$router.go(0);
        }
      });
    },
    close() {
      this.selectedOpen = false;
      setTimeout(() => {
        this.$v.$reset();
        EventBus.$emit('resetCustom');
      }, 300);
    },
    intervalFormat(interval) {
      return interval.time;
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = 'day';
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = this.today;
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = async () => {
        this.selectedEvent = await eventoService.editar(event.id);
        this.selectedElement = nativeEvent.target;
        setTimeout(() => {
          this.selectedOpen = true;
        }, 10);
      };
      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }
      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      this.start = start;
      this.end = end;
    },
    nth(d) {
      return d > 3 && d < 21
        ? 'th'
        : ['th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th'][d % 10];
    },
  },
};
</script>
