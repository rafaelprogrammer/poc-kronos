<template>
  <div>
    <div class="input-info-btn-popover">
      <v-text-field
        ref="input"
        :name="name"
        :hint="hint"
        :label="label"
        v-model="password"
        v-bind:value="value"
        v-on:input="emitValue($event)"
        :error-messages="fieldErrors('password')"
        @input="$v.password.$touch()"
        @blur="$v.password.$touch()"
        :required="required"
        class="box-input white-bg box-z-index"
        :class="[defaultClass]"
        :placeholder="placeholder"
        :type="showpass ? 'password' : 'text'"
        :error="error"
      ></v-text-field>
    </div>

    <div
      class="Password__badge"
      v-bind:class="passwordCountClass"
      v-cloak
      v-if="badge"
      >
      {{ passwordAccesive }}/32
    </div>

    <div class="password-progress mb-3">
      <v-progress-linear
        class="progress-bar"
        v-bind:indeterminate="query"
        v-bind:query="true"
        v-model="passwordStrength"
        v-bind:active="show"
        :color="progreeClass"
      ></v-progress-linear>

      <v-layout progress-meter-separator>
        <v-flex xs3 v-for="i in 4" :key="i" class="no-mrpd">&nbsp;</v-flex>
      </v-layout>

    </div>
  </div>
</template>

<script>
import { required, minLength, maxLength } from "vuelidate/lib/validators";
import validationLangMixin from "@/mixins/validationLangMixin";
import { validPassword } from "@/utils/validators";
import zxcvbn from "zxcvbn";

export default {
  props: {
    label: {
      type: String,
      default: ""
    },
    hint: {
      type: String,
      default: "At least 8 characters"
    },
    id: {
      type: String,
      default: "password"
    },
    placeholder: {
      type: String,
      default: "Please enter your Password"
    },
    value: {
      type: String
    },
    name: {
      type: String,
      default: "password"
    },
    required: {
      type: Boolean,
      default: true
    },
    secureLength: {
      type: Number,
      default: 8
    },
    badge: {
      type: Boolean,
      default: true
    },
    defaultClass: {
      type: String,
      default: ""
    },
    errorClass: {
      type: String,
      default: "has-error"
    },
    successClass: {
      type: String,
      default: "is-success"
    }
  },
  mixins: [validationLangMixin],
  validations: {
    password: {
      required,
      minLength: minLength(8),
      validPassword,
      maxLength: maxLength(32)
    }
  },
  validationMessages: {
    password: {
      required: "validation.password.required",
      minLength: "validation.password.min",
      maxLength: "validation.password.max",
      validPassword: "validation.password.validPassword"
    }
  },
  data() {
    return {
      popover: false,
      error: false,
      showpass: true,
      password: null,
      query: false,
      show: true,
      progress: 0,
      maxLimit: 32
    };
  },
  methods: {
    emitValue(value) {
      this.password = value;
      this.$emit("input", value);
    }
  },
  computed: {
    passwordStrength() {
      if (this.password) {
        let score = zxcvbn(this.password).score * 25;
        let pwdRegex = /^(?=.*[\d])(?=.*[A-Z])(?=.*[!@#$%^&*()?.'"])[\w!@#$%^&*()?.'"]{8,16}$/;
        var ispassPwd = this.password.match(pwdRegex);
        if (ispassPwd == null || ispassPwd === false) {
          this.$emit("next", { score: 25 });
          return 25;
        }
        this.$emit("next", { score });
        return score;
      }
      return 0;
    },
    isSecure() {
      return this.password ? this.password.length >= this.secureLength : null;
    },
    isActive() {
      return this.password && this.password.length > 0;
    },
    passwordCount() {
      return (
        this.password &&
        (this.password.length > this.secureLength
          ? `${this.secureLength}+`
          : this.password.length)
      );
    },
    passwordAccesive() {
      if (!this.password) return 0;
      return (
        this.password &&
        (this.password.length > this.maxLimit
          ? this.maxLimit
          : this.password.length)
      );
    },
    passwordCountClass() {
      return {
        "green--text": this.password && this.password.length <= this.maxLimit,
        "red--text": this.password && this.password.length > this.maxLimit
      };
    },
    progreeClass() {
      let result;
      switch (this.passwordStrength) {
        case 25:
          result = "red";
          break;
        case 50:
          result = "warning";
          break;
        case 75:
          result = "success";
          break;
        case 100:
          result = "success";
          break;
        default:
          result = "";
      }
      return result;
    }
  }
};
</script>

<style lang="stylus">
  [v-cloak] {
    display: none;
  }

  .Password {
    max-width:  400px;
    margin:  0 auto;
  }

  .Password__badge {
    float: right;
    position: relative;
    bottom: 20px;
    right: 10px;
    color: #000;
    padding: 3px;
    height: 15px;
    font-size: 14px;
    line-height: 1.1;
  }

  .has-error {
    background: red;
  }

  .is-success {
    background: #1bbf1b;
  }
  .password-progress {
    position: relative;
    width: 100%;
    margin-bottom: 0px;
  }
  .progress-bar{
    width: 100%;
    position: absolute;
    height: 3px;
    background: #E0E7EF;
    margin: 10px auto 20px;
    border-radius: 3px;
    z-index: 1;
    margin: 0;
  }
  .progress-meter-separator{
    position: absolute;
    height: 7px;
    min-height: 7px !important;
    max-height: 7px;
    z-index: 10;
    background: transparent;
    width: 100%;
  }
  .progress-meter-separator .flex{
    border-right: 5px solid #FFF;
    /*border-left: 5px solid #FFF;*/
  }
  .progress-meter-separator .flex:last-child{
    border:none;
  }
  ul {
    padding-left: 0
  }
  li {
    list-style: none
  }
</style>
