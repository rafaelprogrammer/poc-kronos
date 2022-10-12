<template>
  <v-list two-line>
    <!-- Sidebar -->
    <v-list-tile avatar>
      <v-list-tile-action>
        <v-switch v-model="sidebarVisibility" color="primary"></v-switch>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title>Navigation Drawer</v-list-tile-title>
        <v-list-tile-sub-title>Show sidebar navigation</v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile>

    <!-- Toolbar -->
    <v-list-tile avatar>
      <v-list-tile-action>
        <v-switch v-model="toolbarVisibility" color="primary"></v-switch>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title>Toolbar</v-list-tile-title>
        <v-list-tile-sub-title>Show toolbar</v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile>

    <!-- Footer -->
    <v-list-tile avatar>
      <v-list-tile-action>
        <v-switch v-model="footerVisibility" color="primary"></v-switch>
      </v-list-tile-action>
      <v-list-tile-content>
        <v-list-tile-title>Footer</v-list-tile-title>
        <v-list-tile-sub-title>Show footer</v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile>
  </v-list>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      navDrawer: "navDrawer",
      showFooter: "showFooter",
      toolbar: "toolbarVisibility"
    }),
    sidebarVisibility: {
      get() {
        return this.navDrawer;
      },
      set(value) {
        this.$store.dispatch("toggleDrawer", value);
      }
    },
    toolbarVisibility: {
      get() {
        return this.toolbar;
      },
      set(value) {
        this.$store.dispatch("toggleToolbar", value);
      }
    },
    footerVisibility: {
      get() {
        return this.showFooter;
      },
      set() {
        this.$store.dispatch("toggleFooter");
      }
    }
  }
};
</script>
