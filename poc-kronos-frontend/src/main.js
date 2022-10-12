import Vue from "vue";
import "./plugins/vuetify";
import "./plugins/vuse";
import "./plugins/axios";
import App from "./App.vue";
import router from "./router";
import { store } from "./store";
import { createI18n } from "./i18n/index";
import VueMoment from "vue-moment";
import VImageInput from "vuetify-image-input";
import VueFriendlyIframe from "vue-friendly-iframe";
import VueJWT from "vuejs-jwt";
 
Vue.use(VueJWT);
Vue.component("vue-friendly-iframe", VueFriendlyIframe);

Vue.component(VImageInput.name, VImageInput);
Vue.use(VueMoment);

Vue.config.productionTip = false;

const i18n = createI18n();

router.beforeEach((route, redirect, next) => {
  if (!localStorage.getItem("auth_token") && route.name !== "LoginPage") {
    router.push({ name: "LoginPage" });
  } else {
    next();
  }
});

// let count = 0;
// Vue.mixin({
//   created() {
//     console.log(++count);
//   },
//   destroyed() {
//     console.log(--count);
//   }
// });

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount("#app");
