<template>
  <v-app :dark="colorScheme.dark" :class="colorScheme.scheme">
    <router-view name="header"/>
    <router-view name="sidebar"/>
    <v-content>
      <mensagem-global />
      <router-view/>
    </v-content>
    <router-view name="footer"/>
    <app-settings :drawer="subDrawer" @toggleSubDrawer="handleSubdrawer"></app-settings>
    <!-- Toggle settings button -->
    <v-btn fixed class="settings-btn" @click.stop="subDrawer = !subDrawer">
      <v-icon light>settings</v-icon>
    </v-btn>
  </v-app>
</template>
<script>
import { mapGetters } from "vuex";
import AppSettings from "@/components/AppSettings";
import MensagemGlobal from "@/components/Mensagens/MensagemGlobal";
export default {
  data() {
    return {
      subDrawer: false
    };
  },
  created() {
    // window.addEventListener('beforeunload', this.close());
  },
  computed: {
    ...mapGetters({
      colorScheme: "colorScheme"
    })
  },
  components: {
    AppSettings,
    MensagemGlobal
  },
  methods: {
    // close() {
    //   if(performance.navigation.type === 1) {
    //     console.info("TYPE_RELOAD");
    //   } else {
    //     this.$store.dispatch("logout").then(() => {
    //     this.$router.push("/login");
    //    });
    //   }
    // },
    handleSubdrawer(value) {
      this.subDrawer = value;
    }
  }
};
</script>
