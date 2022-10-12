<template>
  <v-container fluid pt-0>
    <v-layout row wrap>
      <v-flex xs12>
        <v-card class="elevation-0 mt-3">
          <v-card-text class="pa-0">
            <v-radio-group v-model="configToolbar" class="dense-input mt-0">
              <template v-for="(scheme, index) in appColorSchemes">
                <div :key="`appscheme-${index}`" class="pos-relative">
                  <v-radio
                    :value="scheme"
                    color="primary"
                    persistent-hint="rock"
                  ></v-radio>
                  <div class="themeMaker">
                    <v-btn :color="scheme.primary"></v-btn>
                    <v-btn :class="scheme.scheme"></v-btn>
                    <v-btn :class="[scheme.dark ? 'box-black-500-glow' : 'box-white-500-glow']"></v-btn>
                  </div>
                </div>
              </template>
            </v-radio-group>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import AppColorScheme from "@/data/AppColorScheme";
import { mapGetters } from "vuex";
export default {
  computed: {
    appColorSchemes() {
      return AppColorScheme;
    },
    ...mapGetters({
      colorScheme: "colorScheme"
    }),
    configToolbar: {
      get() {
        return this.colorScheme;
      },
      set(value) {
        this.$store.dispatch("setColorScheme", value);
        this.$vuetify.theme.primary = value.primary;
      }
    }
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
.themeMaker {
  position: absolute;
  top: -1px;
  left: 30px;
  width: 110px;
}
.themeMaker button {
  height: 12px;
  width: 24px;
  min-width: 24px;
  margin: 0;
  cursor: initial;
  margin: 0 2px;
}
</style>
