import EventBus from "@/utils/EventBus";
import NProgress from "nprogress";
export default {
  methods: {
    snackHandler() {},
    default(msg, text = "Close", timeout = 6000) {
      this.message(null, msg, text, null, timeout);
    },
    error(msg, error = null, text = "Close", timeout = 6000) {
      this.message("error", msg, text, error, timeout);
    },
    success(msg, text = "Close", timeout = 6000) {
      this.message("success", msg, text, null, timeout);
    },
    warning(msg, text = "Close", timeout = 6000) {
      this.message("warning", msg, text, null, timeout);
    },
    message(color, msg, text, error, timeout) {
      msg = this._tratarErro(msg, error);
      EventBus.$emit("mensagem", msg, color, text, timeout);
      NProgress.done();
    },
    _tratarErro(msg, error) {
      if (error && error.response) {
        if (error.response.status === 403) {
          msg = "Usuário não possui autorização!!";
        } else {
          if (error.response.data) {
            msg = error.response.data;
          }
        }
      }
      return msg;
    }
  }
};
