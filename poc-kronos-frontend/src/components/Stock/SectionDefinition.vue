<template>
  <v-container fluid grid-list-xl class="pb-0">
    <v-toolbar flat class="transparent section-definition-toolbar">
      <v-avatar class="box-glow" :tile="tile">
        <v-icon dark v-html="icon" v-if="icon"></v-icon>
        <span v-else>{{ title | first2Char }}</span>
      </v-avatar>
      <v-toolbar-title class="primary--text">{{ title }}</v-toolbar-title>
      <!--<v-toolbar-title class="toobar-extension" slot="extension">
        <v-breadcrumbs v-if="breadcrumbs" :items="breadcrumbs" class="pl-0">
          <v-icon slot="divider">chevron_right</v-icon>
        </v-breadcrumbs>
      </v-toolbar-title>-->
      <v-btn icon @click="atualizar">
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
      <!--<v-btn icon>
        <v-icon color="primary">more_vert</v-icon>
      </v-btn>-->
    </v-toolbar>
  </v-container>
</template>

<script>
import EventBus from "@/utils/EventBus.js";
export default {
  props: {
    eventUpdate: String,
    title: String,
    breadcrumbs: Array,
    icon: String,
    tile: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    atualizar() {
      EventBus.$emit(this.eventUpdate);
    }
  }
};
</script>

<style lang="stylus" scoped>
.toobar-extension {
  margin-left: 5px !important;
}

.breadcrumb-section {
  margin-left: -25px !important;
}

.action-btn {
  margin-left: 5px !important;
}
</style>
