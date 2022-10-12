import Vue from "vue";
import Vuetify from "vuetify";
import "vuetify/src/stylus/app.styl";
import colors from "vuetify/es5/util/colors";
import { store } from "@/store";

Vue.use(Vuetify, {
  iconfont: "md",
  theme: {
    primary: store.state.swatch.colorScheme.primary,
    secondary: colors.pink.base,
    accent: colors.deepPurple.accent2,
    error: colors.red.accent4,
    info: colors.blue.lighten1,
    success: colors.green.accent4,
    warning: colors.amber.darken2
  },
  options: {
    themeVariations: ["primary", "secondary"]
  }
});
