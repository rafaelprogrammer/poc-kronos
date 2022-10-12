<template>
      <v-menu
        ref="menu"
        v-model="menu2"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="time"
        transition="scale-transition"
        offset-y
        max-width="290px"
        min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            :error-messages="errorMessages"
            v-model="computedTimeFormatted"
            prepend-icon="access_time"
            v-on="on"
            :label="label"
            readonly
          ></v-text-field>
        </template>
        <v-time-picker
          v-if="menu2"
          v-model="time"
          full-width
          format="24hr"
          @click:minute="$refs.menu.save(time)"
        ></v-time-picker>
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
  data() {
    return {
      time: null,
      menu2: false,
      modal2: false,
    };
  },
  computed: {
    computedTimeFormatted() {
      if (this.vModel) {
        return this.vModel;
      }
      return null;
    },
  },
  watch: {
    vModel() {
      this.time = this.vModel;
    },
    time() {
      this.$emit('time', this.time);
    },
  },
  created() {
    EventBus.$on('resetCustom', () => {
      this.time = null;
    });
  },
};
</script>
