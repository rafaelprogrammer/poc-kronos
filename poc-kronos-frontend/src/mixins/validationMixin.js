import { validationMixin } from "vuelidate";
import { get } from "lodash";

export default {
  mixins: [validationMixin],
  methods: {
    fieldErrorsText(name) {
      return this.fieldErrors(name)[0];
    },
    fieldErrors(name) {
      let rule;
      const errors = [];
      const field = get(this.$v, name);
      const messages = get(this.$options.validationMessages, name);
      if (field && !field.$dirty) return errors;

      if (messages) {
        for (rule in messages) {
          if (field[rule] === false && messages[rule]) {
            errors.push(messages[rule]);
          }
        }
      }
      return errors;
    }
  }
};
