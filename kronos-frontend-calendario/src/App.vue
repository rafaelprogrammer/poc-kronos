<template>
  <v-app>
    <v-content>
      <router-view/>
      <mensagem-global />
    </v-content>
  </v-app>
</template>

<script>
import MensagemGlobal from '@/components/Mensagens/MensagemGlobal.vue';

export default {
  name: 'App',

  components: {
    MensagemGlobal,
  },

  data: () => ({
    //
  }),
};
</script>
