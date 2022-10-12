import Vue from 'vue';
// Lib imports
import axios from 'axios';
import NProgress from 'nprogress';
// eslint-disable-next-line import/no-unresolved
import '@/assets/nprogress.css';

Vue.use(NProgress);

Vue.prototype.$http = axios;

// axios.defaults.baseURL = process.env.VUE_APP_ROOT_API;

// if (process.env.NODE_ENV === 'production') {
//   axios.defaults.baseURL = 'https://escolafatima-kronoseducacional.herokuapp.com';
// } else {
//   axios.defaults.baseURL = 'http://localhost:8080';
// }

axios.interceptors.request.use(
  (config) => {
    // eslint-disable-next-line no-param-reassign
    config.headers['x-auth-token'] = localStorage.getItem('x-auth-token');
    NProgress.start();
    return config;
  },
  error => Promise.reject(error),
);
// before a response is returned stop nprogress
axios.interceptors.response.use((response) => {
  NProgress.done();
  return response;
});
