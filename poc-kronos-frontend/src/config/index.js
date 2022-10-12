// App Theme settings
/**
 * This setting is imported from src/data/json/AppColorScheme.json
 * Here we have choosen one of the theme among the list
 * Setting it's titled as Color Scheme
 * Ref: store/modules/swatch.js
 */
import schemes from "./schemes";

// Theme Base componet Config
/**
 * This is based config for theme UI.
 * Basically refers to different mod/visibility of 3 based navigation of an app.
 * Ref: store/modules/navigation.js
 */
import navigation from "./navigation";
import swatch from "./swatch";

export const configSchemes = schemes;
export const configNavigation = navigation;
export const configSwatch = swatch;

export default {
  schemes,
  navigation,
  swatch
};
