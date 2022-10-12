<template>
  <v-list two-line>
    <v-list-tile avatar>
      <v-list-tile-action>
        <v-switch v-model="drawerAlphaMod" color="primary"></v-switch>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title>Tranperent Mod</v-list-tile-title>
        <v-list-tile-sub-title>Show tranperent background</v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile>
  </v-list>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      navDrawerAlphaMod: "navDrawerAlphaMod"
    }),
    drawerAlphaMod: {
      get() {
        return this.navDrawerAlphaMod;
      },
      set(value) {
        this.$store.dispatch("toggleDrawerAlphaMod", value);
      }
    }
  }
};
</script>
