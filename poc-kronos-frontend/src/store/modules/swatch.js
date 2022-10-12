import config from "@/config";

import {
  NAVIGATION_DRAWER_SCHEME,
  NAVIGATION_TOOLBAR_SCHEME,
  FOOTER_SCHEME,
  NAV_DRAWER_ALPHA_MOD,
  SET_DRAWER_ALPHA_IMG,
  APP_COLOR_SCHEME,
  NAV_DRAWER_VARIANT_MOD
} from "../mutation-types";

const state = {
  ...config.swatch,
  colorScheme: config.schemes
};

const getters = {
  navDrawerScheme: state => state.navDrawerScheme,
  navToolbarScheme: state => state.navToolbarScheme,
  navFooterScheme: state => state.navFooterScheme,
  navDrawerAlphaMod: state => state.alpha,
  navDrawerAlphaImg: state => state.navDrawerAlphaImage,
  colorScheme: state => state.colorScheme,
  navDrawerVariant: state => state.variant
};

const mutations = {
  [NAVIGATION_DRAWER_SCHEME]: (state, payload) => {
    state.navDrawerScheme = payload;
  },
  [NAVIGATION_TOOLBAR_SCHEME]: (state, payload) => {
    state.navToolbarScheme = payload;
  },
  [FOOTER_SCHEME]: (state, payload) => {
    state.navFooterScheme = payload;
  },
  [NAV_DRAWER_ALPHA_MOD]: (state, payload) => {
    state.alpha = payload;
  },
  [SET_DRAWER_ALPHA_IMG]: (state, payload) => {
    state.navDrawerAlphaImage = payload;
  },
  [APP_COLOR_SCHEME]: (state, payload) => {
    state.colorScheme = payload;
  },
  [NAV_DRAWER_VARIANT_MOD]: (state, payload) => {
    state.variant = payload;
  }
};

const actions = {
  setNavDrawerScheme: ({ commit }, payload) => {
    commit(NAVIGATION_DRAWER_SCHEME, payload);
  },
  setNavToolbarScheme: ({ commit }, payload) => {
    commit(NAVIGATION_TOOLBAR_SCHEME, payload);
  },
  setFooterScheme: ({ commit }, payload) => {
    commit(FOOTER_SCHEME, payload);
  },
  toggleDrawerAlphaMod: ({ commit }, payload) => {
    commit(NAV_DRAWER_ALPHA_MOD, payload);
  },
  setNavDrawerAlphaImg: ({ commit }, payload) => {
    commit(SET_DRAWER_ALPHA_IMG, payload);
  },
  setColorScheme: ({ commit }, payload) => {
    commit(APP_COLOR_SCHEME, payload);
  },
  toggleDrawerVariant: ({ commit }, payload) => {
    commit(NAV_DRAWER_VARIANT_MOD, payload);
  }
};

export default {
  state,
  getters,
  mutations,
  actions
};
