<template>
  <v-menu
    ref="menu"
    :close-on-content-click="false"
    v-model="menu"
    :nudge-right="40"
    lazy
    transition="scale-transition"
    offset-y
    full-width
    :disabled="disabledDate"
    min-width="290px"
  >
    <v-text-field
      :error-messages="errorMessages"
      slot="activator"
      :disabled="disabledDate"
      v-model="dateFormatted"
      :label="label"
      prepend-icon="event"
      @blur="date = parseDate(dateFormatted)"
    ></v-text-field>
    <v-date-picker
      ref="picker"
      v-model="date"
      :max="birthday ? new Date().toISOString().substr(0, 10) : null"
      min="1950-01-01"
      @change="save"
    ></v-date-picker>
  </v-menu>
</template>
<script>
import EventBus from "@/utils/EventBus";
export default {
  props: {
    id: String,
    label: String,
    required: String,
    errorMessages: Array,
    vModel: String,
    birthday: {
      type: Boolean,
      default: false
    },
    disabledDate: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    date: null,
    dateFormatted: null,
    menu: false,
    mensagem: ""
  }),
  created() {
    EventBus.$on("resetCustom", () => {
      this.date = null;
      this.dateFormatted = null;
    });
  },
  watch: {
    vModel() {
      if (this.vModel) {
        this.dateFormatted = this.vModel;
      }
    },
    menu(val) {
      if (this.birthday) {
        val && this.$nextTick(() => (this.$refs.picker.activePicker = "YEAR"));
      }
    },
    date() {
      this.dateFormatted = this.formatDate(this.date);
      this.$emit("date", this.dateFormatted);
    }
  },
  methods: {
    save(date) {
      this.$refs.menu.save(date);
    },
    formatDate(date) {
      if (!date) return null;
      const [year, month, day] = date.split("-");
      return `${day}/${month}/${year}`;
    },
    parseDate(date) {
      if (!date) return null;

      const [day, month, year] = date.split("/");
      return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
    }
  }
};
</script>
