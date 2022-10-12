// Theme Base componet Config
/**
 * This is based config for theme UI.
 * Basically refers to different mod/visibility of 3 based navigation of an app.
 * Ref: store/modules/navigation.js
 */
export default {
  sidebar: {
    show: null,
    miniview: false,
    miniVariant: false,
    clipped: false,
    position: "left"
  },
  toolbar: {
    show: true,
    fixed: true,
    left: false
  },
  footer: {
    show: true,
    fixed: false
  }
};
