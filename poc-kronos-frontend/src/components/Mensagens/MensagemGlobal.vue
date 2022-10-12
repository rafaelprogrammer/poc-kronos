<template>
  <div id="mensagemGlobal">
    <v-snackbar
      :timeout="timeout"
      :top="y === 'top'"
      :bottom="y === 'bottom'"
      :right="x === 'right'"
      :left="x === 'left'"
      :multi-line="mode === 'multi-line'"
      :vertical="mode === 'vertical'"
      :color="type"
      v-model="snackbar">
      <span> {{mensagem}}</span>
      <v-icon dark>check-circle</v-icon>
    </v-snackbar>
  </div>
</template>

<script>
import EventBus from "@/utils/EventBus.js";
export default {
  name: "MensagemGlobal",
  data: () => ({
    drawer: true,
    snackbar: false,
    y: "top",
    x: null,
    mode: "",
    timeout: 5000,
    mensagem: "",
    type: ""
  }),
  mounted: function() {
    EventBus.$on("mensagem", (msg, type) => {
      this.snackbar = true;
      this.mensagem = msg;
      this.type = type;
    });
  }
};
</script>
