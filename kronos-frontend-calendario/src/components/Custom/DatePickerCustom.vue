<template>
        <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          full-width
          max-width="290px"
          min-width="290px"
        >
          <template v-slot:activator="{ on }">
            <v-text-field
             :error-messages="errorMessages"
              v-model="computedDateFormatted"
              persistent-hint
              prepend-icon="event"
              :label="label"
              @blur="date = parseDate(dateFormatted)"
              readonly
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker v-model="date" no-title @input="menu2 = false"></v-date-picker>
        </v-menu>
</template>
<script>
import EventBus from '@/utils/EventBus';

export default {
  props: {
    label: String,
    errorMessages: Array,
    vModel: String,
  },
  data: () => ({
    date: null,
    dateFormatted: null,
    menu2: false,
  }),
  computed: {
    computedDateFormatted() {
      this.atualizaData();
      return this.vModel;
    },
  },
  watch: {
    vModel() {
      this.dateFormatted = this.vModel;
    },
    date() {
      this.dateFormatted = this.formatDate(this.date);
      this.$emit('date', this.dateFormatted);
    },
  },
  created() {
    EventBus.$on('resetCustom', () => {
      this.date = null;
      this.dateFormatted = null;
    });
  },
  methods: {
    atualizaData() {
      this.dateFormatted = this.vModel;
    },
    formatDate(date) {
      if (!date) return null;
      const [year, month, day] = date.split('-');
      return `${day}/${month}/${year}`;
    },
    parseDate(date) {
      if (!date) return null;
      const [day, month, year] = date.split('/');
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    },
  },
};
</script>
