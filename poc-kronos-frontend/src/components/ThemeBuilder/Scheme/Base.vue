<template>
  <v-container fluid pa-0>
    <v-layout wrap row ma-0>
      <v-flex xs2
        v-for="(color, i) in colors"
        :key="i"
        @click.stop="$emit('next', color)"
        transition="slide-x-reverse-transition"
        d-flex
        class="flex-fix"
      >
        <!-- <v-card flat tile :class="color['500'].classname" class="text-xs-center circle" height="40"> -->
          <!-- <v-layout align-center justify-center row fill-height>
            <v-flex text-xs-center>{{i}}</v-flex>
          </v-layout> -->
        <!-- </v-card> -->
        <v-avatar
          :class="[color['500'].classname, 'cursor-pointer']"
          size="40"
        >
          <v-icon large v-if="hasActive && active.classname.startsWith(`md-${i.split('-').join('-')}`)">panorama_fish_eye</v-icon>
        </v-avatar>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import { has } from "lodash";
import colors from "@/data/json/colors.json";
export default {
  props: ["selected"],
  data() {
    return {
      colors
    };
  },
  computed: {
    active() {
      if (typeof this.selected === "string") {
        return {
          classname: this.selected
        };
      }
      return this.selected;
    },
    hasActive() {
      if (typeof this.selected === "string") {
        return this.selected !== null || this.selected !== "";
      } else {
        return (
          has(this.selected, "classname") && this.selected.classname !== null
        );
      }
    }
  }
};
</script>
