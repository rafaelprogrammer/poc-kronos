<template>
  <div></div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      fixedToolbar: "fixedToolbar"
    }),
    configFixToolbar: {
      get() {
        return this.fixedToolbar;
      },
      set(value) {
        this.$store.dispatch("toggleFixToolbar", value);
      }
    }
  }
};
</script>
