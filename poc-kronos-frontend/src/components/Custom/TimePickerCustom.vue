<template>
      <v-menu
        ref="menu"
        :close-on-content-click="false"
        v-model="menu2"
        :nudge-right="40"
        :return-value.sync="time"
        lazy
        transition="scale-transition"
        offset-y
        full-width
        max-width="290px"
        min-width="290px"
        :disabled="disabledDate"
      >
        <v-text-field
          slot="activator"
          :error-messages="errorMessages"
          v-model="time"
          :label="label"
          :disabled="disabledDate"
          prepend-icon="access_time"
        ></v-text-field>
        <v-time-picker
          v-if="menu2"
          v-model="time"
          full-width
          format="24hr"
          @change="$refs.menu.save(time)"
        ></v-time-picker>
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
    disabledDate: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      time: null,
      menu2: false,
      modal2: false
    };
  },
  watch: {
    vModel() {
      if (this.vModel) {
        this.time = this.vModel;
      }
    },
    time() {
      this.$emit("time", this.time);
    }
  },
  created() {
    EventBus.$on("resetCustom", () => {
      this.time = null;
    });
  },
};
</script>
