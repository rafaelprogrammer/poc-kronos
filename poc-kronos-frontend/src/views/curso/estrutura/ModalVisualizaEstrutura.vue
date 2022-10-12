<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">Visualiza Estrutura</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12 v-if="dialog">
                <v-treeview :items="items" item-key="id" item-text="nome" :item-active="items.length > 0" item-children="filhos"></v-treeview>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import cursoService from "@/service/curso/CursoService";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalVisualizaEstrutura",
  data() {
    return {
      nomeCurso: "",
      items: [],
      loading: false,
      dialog: false
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaEstrutura", async (idCurso, nomeCurso) => {
      this.nomeCurso = nomeCurso;
      this.items = await cursoService.visualizarEstrutura(idCurso);
      this.dialog = true;
    });
  },
  methods: {
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.items = [];
      }, 300);
    }
  }
};
</script>
