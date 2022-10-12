import NProgress from 'nprogress';
import EventBus from '@/utils/EventBus';

export default {
  methods: {
    snackHandler() {},
    default(msg, text = 'Close', timeout = 6000) {
      this.message(null, msg, text, timeout);
    },
    error(msg, text = 'Close', timeout = 6000) {
      this.message('error', msg, text, timeout);
    },
    success(msg, text = 'Close', timeout = 6000) {
      this.message('success', msg, text, timeout);
    },
    warning(msg, text = 'Close', timeout = 6000) {
      this.message('warning', msg, text, timeout);
    },
    message(color, msg, text, timeout) {
      let msgAlterada = msg;
      if (msg.response && msg.response.data) {
        msgAlterada = msg.response.data;
      }
      EventBus.$emit('mensagem', msgAlterada, color, text, timeout);
      NProgress.done();
    },
  },
};
