<template>
  <v-footer
      :fixed="fixedFooter"
      :inset="!fixedFooter"
      :class="[!footer ? 'hide' : '', navFooterScheme]"
      app
    >
      <div class="pa-3">&copy; {{ new Date().getFullYear() }} Kronos Educacional. Todos os direitos reservados. Versão - {{versao}}</div>
      <v-spacer></v-spacer>
  </v-footer>
</template>
<script>
import { mapGetters } from "vuex";
import apoioService from "@/service/ApoioService";

export default {
  computed: {
    ...mapGetters({
      fixedFooter: "fixedFooter",
      footer: "showFooter",
      navFooterScheme: "navFooterScheme"
    })
  },
  data() {
    return {
      versao: ""
    }
  },
  async created() {
    this.versao = await apoioService.versaoDoSistema();
  },

};
</script>
