<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
	  <v-toolbar dark color="primary">
          <v-btn color="primary" dark @click.native="dialog = false">Fechar</v-btn>
          <v-toolbar-title>Visualiza</v-toolbar-title>
          <v-spacer></v-spacer>
      </v-toolbar>
      <v-card>
        <v-card-text>
            <v-layout row >
              <v-flex xs12 sm12 md12 v-if="filePdf">
			  	<v-layout wrap>
					<v-flex xs12 sm12 md12>
						<iframe
							:src="dadosPdf"
							width="100%"
							height="800"
							style="border: none;" />
					</v-flex>
				</v-layout>
			  </v-flex>
			  <v-flex xs12 sm12 md12 v-if="imagem">
			  	<v-hover>
					<v-card
					slot-scope="{ hover }"
					:class="`elevation-${hover ? 12 : 2}`"
					class="mx-auto"
					width="20%"
					>
					<v-img :src="dadosImagem"></v-img>
    			</v-card>
  				</v-hover>
			  </v-flex>
            </v-layout>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import arquivoService from "@/service/arquivo/ArquivoService";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalPreview",
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  data() {
    return {
      imagem: false,
      filePdf: false,
      dialog: false,
      dadosPdf: "",
      dadosImagem: "",
      loadedRatio: 0,
      page: 1,
      numPages: 0,
      rotate: 0
    };
  },
  created() {
    EventBus.$on("abrirPreview", async id => {
      this.abrir(id);
    });
  },
  methods: {
    async abrir(id) {
      const arquivo = await arquivoService.buscarPorId(id);
      if (arquivo.contentType === "application/pdf") {
        this.dadosPdf = arquivo.dadosBase64;
        this.dadosImagem = "";
        this.filePdf = true;
        this.imagem = false;
      } else {
        this.dadosImagem = arquivo.dadosBase64;
        this.filePdf = false;
        this.dadosPdf = "";
        this.imagem = true;
      }
      this.dialog = true;
    },
    close() {
      this.dialog = false;
      this.filePdf = false;
      this.imagem = false;
    }
  }
};
</script>
