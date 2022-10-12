<template>
  <v-checkbox :label="label" :color="color" @click="atualizar" :input-value="inputValue" :indeterminate="indeterminate"  ></v-checkbox>
</template>
<script>
import EventBus from "@/utils/EventBus";
export default {
  props: {
    label: String,
    color: {
      type: String,
      default: "primary"
    },
  },
  data: () => ({
    indeterminate: "indeterminate",
    inputValue: null
  }),
  created() {
    EventBus.$on("resetCheckBoxState", () => {
      this.inputValue = null;
      this.indeterminate = "indeterminate";
    });
  },
  methods: {
    atualizar() {
      if (this.indeterminate) {
        this.inputValue = true;
        this.indeterminate = null;
        this.emitirEvento(true);
        return;
      }
      if (this.inputValue) {
        this.inputValue = false;
        this.indeterminate = null;
        this.emitirEvento(false);
        return;
      }
      if (!this.inputValue) {
        this.inputValue = false;
        this.indeterminate = "indeterminate";
        this.emitirEvento(null);
        return;
      }
    },
    emitirEvento(value) {
      this.$emit("checkBoxStateAtualizado", value);
    }

  }
};
</script>
