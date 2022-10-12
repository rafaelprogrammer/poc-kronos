import axios from "axios";

export default {
  get(url, params) {
    return axios.get(this.baseUrl() + url, params);
  },

  put(url, body) {
    return axios.put(this.baseUrl() + url, body);
  },

  post(url, body) {
    return axios.post(this.baseUrl() + url, body);
  },

  delete(url) {
    return axios.delete(this.baseUrl() + url);
  },

  baseUrl() {
    return process.env.NODE_ENV === "development"
      ? "http://localhost:8080"
      : "";
  }
};
