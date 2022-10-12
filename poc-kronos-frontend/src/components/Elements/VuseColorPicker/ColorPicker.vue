<template>
  <v-menu
    offset-x
    :close-on-content-click="false"
    :nudge-width="200"
    origin="center center"
    transition="scale-transition"
    v-model="menu"
  >
    <v-btn
      dark
      fab
      small
      :class="btnClass"
      slot="activator"
    >
      <v-icon>{{ icon }}</v-icon>
    </v-btn>
    <!-- Swatch -->
    <vuse-swatch
      @success="successHandler"
      @next="handleMenuStatus"
      :selected="pickedColor"
    ></vuse-swatch>
  </v-menu>
</template>
<script>
import VuseSwatch from "./Swatch";

export default {
  props: ["icon", "result"],
  components: {
    VuseSwatch
  },
  data() {
    return {
      menu: false,
      pickedColor: {
        classname: null,
        hex: null
      }
    };
  },
  methods: {
    handleMenuStatus(data) {
      this.menu = data;
    },
    successHandler(data) {
      this.pickedColor = data;
      let result = this.pickedColor;

      switch (this.result) {
        case "classname":
          result = this.pickedColor.classname;
          break;
        case "hex":
          result = this.pickedColor.hex;
          break;
        default:
          result = this.pickedColor;
      }

      this.$emit("success", result);
    }
  },
  computed: {
    btnClass() {
      return this.pickedColor.classname;
    }
  }
};
</script>
