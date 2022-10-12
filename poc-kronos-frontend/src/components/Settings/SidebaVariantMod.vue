<template>
  <v-list two-line>
    <v-list-tile avatar>
      <v-list-tile-action>
        <v-switch v-model="drawerVariant" color="primary"></v-switch>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title>Sidebar Variant</v-list-tile-title>
        <v-list-tile-sub-title>Another Variant for active menu</v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile>
  </v-list>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      navDrawerVariant: "navDrawerVariant"
    }),
    drawerVariant: {
      get() {
        return this.navDrawerVariant;
      },
      set(value) {
        this.$store.dispatch("toggleDrawerVariant", value);
      }
    }
  }
};
</script>
