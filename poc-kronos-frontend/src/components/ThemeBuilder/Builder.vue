<template>
  <v-list-tile avatar>
    <v-list-tile-action>
      <v-menu
        top
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
          <v-icon>{{icon}}</v-icon>
        </v-btn>
        <!-- Swatch -->
        <swatch :menu="menu" :action="action" @next="handleMenuStatus" :selected="btnClass"></swatch>
      </v-menu>
    </v-list-tile-action>
    <v-list-tile-content>
      <v-list-tile-title><slot></slot></v-list-tile-title>
    </v-list-tile-content>
  </v-list-tile>
</template>
<script>
import swatch from "./swatch";

export default {
  props: ["icon", "action", "btnClass"],
  components: {
    swatch
  },
  data() {
    return {
      menu: false
    };
  },
  methods: {
    handleMenuStatus(data) {
      this.menu = data;
    }
  }
};
</script>
