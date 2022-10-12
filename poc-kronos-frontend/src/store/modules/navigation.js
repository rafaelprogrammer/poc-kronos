import config from "@/config/index";
import {
  TOGGLE_CLIPPED,
  TOOLBAR_LEFT_CLIPPED,
  TOGGLE_MINI_VARIANT,
  TOGGLE_MINIVARIENT_MODE,
  TOGGLE_FIXED_FOOTER,
  TOGGLE_DRAWER_VISIBILITY,
  SIDENAV_DRAWER_POSITION,
  TOGGLE_TOOLBAR,
  TOGGLE_FIXED_TOOLBAR,
  TOGGLE_FOOTER
} from "../mutation-types";

const state = config.navigation;

const getters = {
  navMiniVarient: state => state.sidebar.miniVariant,
  navClipped: state => state.sidebar.clipped,
  fixedFooter: state => state.footer.fixed,
  navDrawer: state => state.sidebar.show,
  miniview: state => state.sidebar.miniview,
  navDrawerPosition: state => state.sidebar.position,
  fixedToolbar: state => state.toolbar.fixed,
  toolbarVisibility: state => state.toolbar.show,
  showFooter: state => state.footer.show,
  toolbarClippedLeft: state => state.toolbar.left
};

const mutations = {
  [TOGGLE_CLIPPED]: (state, payload) => {
    state.sidebar.clipped = payload;
  },
  [TOOLBAR_LEFT_CLIPPED]: (state, payload) => {
    state.toolbar.left = payload;
  },
  [TOGGLE_MINI_VARIANT]: (state, payload) => {
    state.sidebar.miniVariant =
      payload !== undefined ? payload : !state.sidebar.miniVariant;
  },
  [TOGGLE_FIXED_FOOTER]: (state, payload) => {
    state.footer.fixed = payload;
  },
  [TOGGLE_DRAWER_VISIBILITY]: (state, payload) => {
    state.sidebar.show = payload !== undefined ? payload : !state.sidebar.show;
  },
  [SIDENAV_DRAWER_POSITION]: (state, payload) => {
    state.sidebar.position = payload;
  },
  [TOGGLE_FIXED_TOOLBAR]: (state, payload) => {
    state.toolbar.fixed =
      payload !== undefined ? payload : !state.toolbar.fixed;
  },
  [TOGGLE_TOOLBAR]: (state, payload) => {
    state.toolbar.show = payload !== undefined ? payload : !state.toolbar.show;
    if (state.toolbar.show === false) {
      state.toolbar.fixed = false;
      state.sidebar.clipped = false;
    } else {
      state.toolbar.fixed = true;
    }
  },
  [TOGGLE_FOOTER]: state => {
    state.footer.show = !state.footer.show;
  },
  [TOGGLE_MINIVARIENT_MODE]: (state, payload) => {
    state.sidebar.miniview =
      payload !== undefined ? payload : !state.sidebar.miniview;
  }
};

const actions = {
  toggleClipped: ({ commit }, payload) => {
    commit(TOGGLE_CLIPPED, payload);
    commit(TOOLBAR_LEFT_CLIPPED, payload);
  },
  toggleMiniVarient: ({ commit }, payload) => {
    commit(TOGGLE_MINI_VARIANT, payload);
  },
  toggleFixFooter: ({ commit }, payload) => {
    commit(TOGGLE_FIXED_FOOTER, payload);
  },
  toggleDrawer: ({ commit }, payload) => {
    commit(TOGGLE_DRAWER_VISIBILITY, payload);
  },
  setSidenavPosition: ({ commit }, payload) => {
    commit(SIDENAV_DRAWER_POSITION, payload);
  },
  toggleFixToolbar: ({ commit }, payload) => {
    commit(TOGGLE_FIXED_TOOLBAR, payload);
  },
  toggleToolbar: ({ commit }, payload) => {
    commit(TOGGLE_TOOLBAR, payload);
  },
  toggleFooter: ({ commit }) => {
    commit(TOGGLE_FOOTER);
  },
  toggleMiniVariantMode: ({ commit }, payload) => {
    commit(TOGGLE_MINIVARIENT_MODE, payload);
  }
};

export default {
  state,
  getters,
  mutations,
  actions
};
