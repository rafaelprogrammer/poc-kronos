// eslint-disable-next-line import/no-extraneous-dependencies
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import pt from 'vuetify/es5/locale/pt';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    options: {
      customProperties: true,
    },
    themes: {
      light: {
        primary: '#ee44aa',
        secondary: '#424242',
        accent: '#82B1FF',
        error: '#FF5252',
        info: '#2196F3',
        success: '#4CAF50',
        warning: '#FFC107',
      },
    },
  },
  icons: {
    iconfont: 'md', // default - only for display purposes
  },
  lang: {
    locales: { pt },
    current: 'pt',
  },
});
