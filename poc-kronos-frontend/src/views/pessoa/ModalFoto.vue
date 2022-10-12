<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn slot="activator" small color="primary" v-show="pessoa && pessoa.id ? true : false" dark alt="Alterar Foto" @click="abrirCadastro()" title="Alterar Foto"><v-icon>add_a_photo</v-icon></v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md6 v-if="dialog">
                <v-spacer></v-spacer>
                <v-image-input  v-model="foto" :image-height="150" :image-width="150" :image-quality="0.90" clearable />
              </v-flex>
              <v-flex xs12 sm6 md6>
                  <v-img :src="foto ? foto : ''" aspect-ratio="1" width="50%">
                    <span class="headline white--text pl-3 pt-3"></span>
                  </v-img>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" :disabled="foto ? false : true" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import pessoaService from "@/service/pessoa/PessoaService";
export default {
  name: "ModalFoto",
  props: {
    pessoa: Object
  },
  data() {
    return {
      loading: false,
      btnTitle: "Alterar Foto",
      dialog: false,
      formTitle: "",
      foto: null
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    async abrirCadastro() {
      this.btnTitle = "Alterar";
      this.dialog = true;
    },
    salvar() {
      pessoaService.foto(this.foto, this.pessoa.id).then(() => {
        this.$emit("fotoCadastrada", this.foto);
        this.dialog = false;
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.foto = null;
      }, 300);
    }
  }
};
</script>
