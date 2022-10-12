import Vue from "vue";
import router from "@/router";
// Lib imports
import axios from "axios";
import NProgress from "nprogress";
import "@/assets/nprogress.css";
Vue.use(NProgress);

Vue.prototype.$http = axios;

// axios.defaults.baseURL = process.env.VUE_APP_ROOT_API;

// if (process.env.NODE_ENV === "production") {
//   axios.defaults.baseURL = "https://web-kronos.herokuapp.com/";
// } else {
//   axios.defaults.baseURL = process.env.VUE_APP_ROOT_API;
// }

axios.interceptors.request.use(
  config => {
    if (!localStorage.getItem("auth_token")) {
      router.push({ name: "LoginPage" });
    }
    config.headers["auth_token"] = localStorage.getItem("auth_token");
    NProgress.start();
    return config;
  },
  error => Promise.reject(error)
);
// before a response is returned stop nprogress
axios.interceptors.response.use(response => {
  NProgress.done();
  return response;
});
