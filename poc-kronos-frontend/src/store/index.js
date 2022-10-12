import Vue from "vue";
import Vuex from "vuex";
import actions from "./actions";
import mutations from "./mutations";
import getters from "./getters";
import navigation from "./modules/navigation";
import swatch from "./modules/swatch";
import login from "./modules/login";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {},
  getters,
  mutations,
  actions,
  modules: {
    navigation,
    swatch,
    login
  }
});
