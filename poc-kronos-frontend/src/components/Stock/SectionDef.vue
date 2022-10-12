<template>
  <v-container fluid grid-list-xl class="pb-0">
    <v-img
      :src="backgroundImg"
      dark
      height="128px"
      class="section-definition"
      :gradient="gradient"
    >
      <v-toolbar flat extended class="transparent">
        <!-- <v-avatar class="box-white-500-glow" :tile="tile">
          <v-icon dark v-html="icon" v-if="icon"></v-icon>
          <span v-else>{{ title | first2Char }}</span>
        </v-avatar> -->
        <v-toolbar-title class="white--text">{{ title }}</v-toolbar-title>
        <v-toolbar-title class="toobar-extension" slot="extension">
          <v-breadcrumbs
            v-if="breadcrumbs"
            class="pl-0"
          >
            <v-icon slot="divider" color="white">chevron_right</v-icon>
            <v-breadcrumbs-item
              v-for="item in breadcrumbs"
              :key="item.text"
              :disabled="item.disabled"
            >
              {{ item.text }}
            </v-breadcrumbs-item>
          </v-breadcrumbs>
          <slot></slot>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- <v-btn icon>
          <v-icon>more_vert</v-icon>
        </v-btn> -->
      </v-toolbar>
    </v-img>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import { rgba as rgbaGradients } from "@/data/gradients";

export default {
  props: {
    title: String,
    breadcrumbs: Array,
    icon: String,
    img: String,
    tile: {
      type: Boolean,
      default: true
    }
  },

  data() {
    return {
      dd: rgbaGradients[9].gradient
    };
  },
  computed: {
    ...mapGetters({
      colorScheme: "colorScheme"
    }),
    backgroundImg() {
      return this.img ? this.img : "/static/toolbar/mountain-2813667.png";
    },
    gradient() {
      return `to right top, ${
        this.colorScheme.primary
      }, rgba(172, 50, 228, 0.7)`;
    }
  }
};
</script>

<style lang="stylus" scoped>
  .section-def
    margin-bottom: 50px
  .toobar-extension
    margin-left: 5px !important
  .breadcrumb-section
    margin-left: -25px !important
  .action-btn
    margin-left: 5px !important
</style>
