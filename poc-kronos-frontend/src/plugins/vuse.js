import Vue from "vue";
import Components from "@/components/Stock/index"; // Rolling useful components (PUG)
import Widgets from "@/components/Widgets/index";

import "material-design-icons-iconfont/dist/material-design-icons.css";
import "@mdi/font/css/materialdesignicons.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "font-awesome/css/font-awesome.min.css";
import "@/stylus/main.styl";

import Vuebar from "vuebars";
import Trend from "vuetrend";
import truncate from "lodash/truncate";

Vue.config.productionTip = false;
Vue.config.debug = true;
Vue.config.devtools = true;
Vue.config.performance = process.env.NODE_ENV === "development";

Vue.use(Vuebar);
Vue.use(Trend);

Object.keys(Components).forEach(key => {
  Vue.component(key, Components[key]);
});

Object.keys(Widgets).forEach(key => {
  Vue.component(key, Widgets[key]);
});

const emitter = new Vue();
Object.defineProperties(Vue.prototype, {
  $eventBus: {
    get: function() {
      return emitter;
    }
  }
});

Vue.filter("twoDigits", value => {
  if (value.toString().length <= 1) {
    return "0" + value.toString();
  }
  return value.toString();
});
Vue.filter("capitalize", function(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
});
Vue.filter("lowercase", function(str) {
  return str.charAt(0).toLowerCase() + str.slice(1);
});
Vue.filter("firstChar", function(str) {
  return str.charAt(0).toUpperCase();
});
Vue.filter("first2Char", function(str) {
  return str.substring(0, 2).toUpperCase();
});
Vue.filter("hljshtml", function(value) {
  return value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
});
Vue.filter("truncate", function(value, args) {
  return truncate(value, args);
});
