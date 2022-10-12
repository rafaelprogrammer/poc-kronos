<template>
  <v-img
    :src="backgroundImg"
    class="page-vimg"
  >
    <v-container fill-height pa-0>
      <v-layout align-center>
        <v-flex xs12>
          <v-card
            class="text-xs-center margin-auto border-radius6 box-white-500-glow elevation-10 auth-box"
          >
            <v-layout align-center justify-center row fill-height wrap>
              <v-flex xs12 class="text-xs-center mt-3">
                <img src="/static/logo/logo_kronos_ct_ft.png" alt="Vuse" class="text-xs-center" height="100">
                <div class="headline">Faça login em sua conta</div>
                <v-form @submit.prevent="$v.$invalid ? null : submit()" ref="form">
                  <v-layout wrap row pa-4>
                    <v-flex xs12 pa-0>
                      <v-text-field
                        color="primary"
                        label="CPF"
                        v-model="form.username"
                        required
                        :error-messages="fieldErrors('form.username')"
                        @blur="$v.form.username.$touch()"
                      ></v-text-field>

                      <v-text-field
                        color="primary"
                        label="Senha"
                        v-model="form.password"
                        type="password"
                        required
                        :error-messages="fieldErrors('form.password')"
                        @blur="$v.form.password.$touch()"
                      ></v-text-field>
                      <div class="width-150x margin-horiz-center">
                        <v-checkbox
                          color="primary"
                          v-model="form.remeberme"
                          required
                        >
                          <div slot="label" @click.stop="() => {}">
                            Lembrar-me
                          </div>
                        </v-checkbox>
                      </div>
                    </v-flex>

                    <v-flex xs12>
                      <v-layout row wrap text-xs-center>
                        <!-- Login form submit -->
                        <v-flex xs12 class="no-mrpd">
                          <v-btn
                            color="act"
                            type="submit"
                            :disabled="$v.$invalid"
                            block
                            :class="$v.$invalid ? '' : 'white--text'"
                          >Login</v-btn>
                        </v-flex>
                        <div class="margin-horiz-center">Versão - {{versao}}</div>
                        <!-- Forgot password -->
                        <!--<v-flex xs12>
                          <router-link :to="{ name: 'pages/authentication/ForgotPasswordPage' }" tag="div"
                            class="grey--text cursor-pointer"
                          >
                            <strong>Forgot Password ?</strong>
                          </router-link>
                        </v-flex>-->
                      </v-layout>
                    </v-flex>
                  </v-layout>
                </v-form>
              </v-flex>
            </v-layout>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <v-snackbar
      v-model="snackbar"
      absolute
      top
      right
      color="act"
    >
      <span>Sign in successful!</span>
      <v-icon dark>check_circle</v-icon>
    </v-snackbar>
  </v-img>
</template>

<script>
import { required, minLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import Login from "@/model/Login";
import apoioService from "@/service/ApoioService";

export default {
  mixins: [validationMixin, messageMixin],
  validations: {
    form: {
      username: { required },
      password: { required, minLength: minLength(4) }
    }
  },
  validationMessages: {
    form: {
      username: {
        required: "Usuário é obrigatório"
      },
      password: {
        required: "Senha é obrigatória",
        minLength: "Password must be of 6 characters"
      }
    }
  },
  data() {
    return {
      versao: null,
      form: Object.assign({}, Login),
      snackbar: false,
      backgroundImg: "/static/doc-images/HexesisMaterial01.png"
    };
  },
  components: {},
  async created() {
    this.versao = await apoioService.versaoDoSistema();
  },
  methods: {
    async submit() {
      this.$store.dispatch("login", this.form).then(() => {
        this.modal = false;
        this.$router.push("/");
      });
      this.resetForm();
      this.$v.$reset();
    },
    resetForm() {
      this.form = Object.assign({}, Login);
      this.$refs.form.reset();
    }
  }
};
</script>
