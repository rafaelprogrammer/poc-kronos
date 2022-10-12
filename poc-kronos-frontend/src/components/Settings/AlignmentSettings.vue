<template>
  <v-container fluid pt-0>
    <!-- <v-list-tile>
      <v-list-tile-content>
        <v-list-tile-title>Navigation Drawer:</v-list-tile-title>
        <v-list-tile-sub-title>
          <v-radio-group row v-model="configSidenav">
            <v-radio label="Left" value="left"></v-radio>
            <v-radio label="Right" value="right"></v-radio>
          </v-radio-group>
        </v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile> -->

    <!-- Toolbar -->
    <!-- <v-list-tile>
      <v-list-tile-content>
        <v-list-tile-title>Toolbar:</v-list-tile-title>
        <v-list-tile-sub-title>
          <v-radio-group row v-model="configToolbar">
            <v-radio label="Below" :value="false"></v-radio>
            <v-radio label="Above" :value="true"></v-radio>
          </v-radio-group>
        </v-list-tile-sub-title>
      </v-list-tile-content>
    </v-list-tile> -->

    <v-layout row wrap>
      <v-flex xs12>
        <v-subheader class="pl-0">Toolbar</v-subheader>
        <v-card class="elevation-0">
          <v-card-text class="pa-0">
            <v-radio-group row v-model="configToolbar" class="dense-input mt-0">
              <v-radio label="Below" :value="false" color="primary"></v-radio>
              <v-radio label="Above" :value="true" color="primary"></v-radio>
            </v-radio-group>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>

    <!-- Footer -->
    <v-layout row wrap>
      <v-flex xs12>
        <v-subheader class="pl-0">Footer</v-subheader>
        <v-card class="elevation-0">
          <v-card-text class="pa-0">
            <v-radio-group row v-model="configFooter" class="dense-input mt-0">
              <v-radio label="Below" :value="false" color="primary"></v-radio>
              <v-radio label="Above" :value="true" color="primary"></v-radio>
            </v-radio-group>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters({
      navClipped: "navClipped",
      fixedFooter: "fixedFooter",
      navDrawerPosition: "navDrawerPosition"
    }),
    configToolbar: {
      get() {
        return this.navClipped;
      },
      set(value) {
        this.$store.dispatch("toggleClipped", value);
      }
    },
    configFooter: {
      get() {
        return this.fixedFooter;
      },
      set(value) {
        this.$store.dispatch("toggleFixFooter", value);
      }
    }
    // configSidenav: {
    //   get () {
    //     return this.navDrawerPosition
    //   },
    //   set (value) {
    //     this.$store.dispatch('setSidenavPosition', value)
    //   }
    // },
  }
};
</script>
<style scoped>
.card__text,
.subheader {
  padding: 0;
}
.radio-group--row {
  padding-top: 0;
}
</style>
