<template>
  <div></div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      miniVariant: "navMiniVarient"
    }),
    configMiniVariant: {
      get() {
        return this.miniVariant;
      },
      set(value) {
        return value;
        // this.$store.dispatch('toggleFixToolbar', value)
      }
    }
  }
};
</script>
