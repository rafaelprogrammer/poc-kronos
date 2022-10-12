<template>
  <v-container fluid grid-list-xs class="theme-settings-container pa-0">
    <v-toolbar flat dense dark class="content-px5">
      <v-btn icon  v-show="stage == 2" @click="stage = 1">
        <v-icon>arrow_back</v-icon>
      </v-btn>
      <!-- <v-toolbar-title>Display</v-toolbar-title> -->
      <v-toolbar-title class="body-2 ml-0">{{ active }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon @click.native.stop="$emit('next', false)">
        <v-icon>close</v-icon>
      </v-btn>
    </v-toolbar>

    <v-layout row wrap grey darken-4>
      <v-stepper v-model="stage" class="no-box-shadow" dark>
        <v-stepper-header class="hide">
          <v-stepper-step step="1" :complete="stage > 1">1</v-stepper-step>
          <v-stepper-step step="2">2</v-stepper-step>
        </v-stepper-header>
        <!-- Base Colors -->
        <v-stepper-content step="1" class="pa-1">
          <base-colors @next="handleBaseColorSelection" :selected="active"></base-colors>
        </v-stepper-content>

        <!-- Select shades -->
        <v-stepper-content step="2" class="pa-1">
          <shades-colors :base="base" @next="handleShadeSelection" :selected="active"></shades-colors>
        </v-stepper-content>
      </v-stepper>
    </v-layout>
  </v-container>
</template>
<script>
import BaseColors from "./Scheme/Base";
import ShadesColors from "./Scheme/Shades";

export default {
  props: ["menu", "action", "selected"],
  components: {
    BaseColors,
    ShadesColors
  },
  data() {
    return {
      stage: 0,
      base: null,
      shade: null,
      active: this.selected
    };
  },
  computed: {
    currentView() {
      return BaseColors;
    }
  },
  methods: {
    handleBaseColorSelection(data) {
      this.stage = 2;
      this.base = data;
      this.$store.dispatch(this.action, data[500].classname);
      this.active = data[500].classname;
    },
    handleShadeSelection(data) {
      this.shade = data;
      this.$emit("next", false);
      this.active = data.classname;
      this.$store.dispatch(this.action, data.classname);
    }
  }
};
</script>
