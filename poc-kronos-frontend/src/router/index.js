import Vue from "vue";
import Router from "vue-router";
import { routes } from "./routes";

Vue.use(Router);

export default new Router({
  routes,
  // mode: "history",
  scrollBehviour(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    }
    if (to.hash) {
      return {
        selector: to.hash
      };
    }
  }
});
