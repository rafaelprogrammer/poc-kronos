import loginService from "@/service/LoginService";
import Login from "@/model/Login";
import Vue from "vue";

export default {
  state: {
    status: "",
    token: localStorage.getItem("auth_token") || "",
    user: JSON.parse(localStorage.getItem("user")) || ""
  },
  mutations: {
    auth_request(state) {
      state.status = "loading";
    },
    auth_success(state, data) {
      state.status = "success";
      state.user = data;
    },
    auth_error(state) {
      state.status = "error";
    },
    logout(state) {
      state.status = "";
      state.token = "";
    }
  },
  actions: {
    login({ commit }, user) {
      return new Promise(async (resolve, reject) => {
        commit("auth_request");
        loginService
          .logar(user)
          .then(resp => {
            const login = new Login(Vue.$jwt.decode(resp.token).user);
            localStorage.setItem("auth_token", resp.token);
            localStorage.setItem("user", JSON.stringify(login));
            commit("auth_success", login);
            resolve(resp);
          })
          .catch(err => {
            commit("auth_error");
            localStorage.removeItem("auth_token");
            localStorage.removeItem("user");
            reject(err);
          });
      });
    },
    logout({ commit }) {
      return new Promise(resolve => {
        commit("logout");
        localStorage.removeItem("auth_token");
        localStorage.removeItem("user");
        resolve();
      });
    }
  },
  getters: {
    isLoggedIn: state => !!state.token,
    authStatus: state => state.status,
    user: state => state.user
  }
};
