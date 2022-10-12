<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Visualizar Escala</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>  
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Instiuição" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Nome" disabled v-model="this.escala.nome"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Tipo Abrangência" disabled v-model="this.escala.nomeTipoAbrangencia"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Tipo Avaliação" disabled v-model="this.escala.nomeTipoAvaliacao"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Data Inicial Vigência" disabled v-model="this.escala.dataInicialVigencia"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md6>
                    <v-text-field color="primary" label="Data Final Vigência" disabled v-model="this.escala.dataFinalVigencia"></v-text-field>
                  </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="mensoesELimites" hide-actions class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.minimo }}</td>
                    <td>{{ props.item.maximo }}</td>
                    <td>{{ props.item.nome }}</td>
                    <td>{{ props.item.simbolo }}</td>
                  </template>
                <template slot="no-data">
                    Não existem informações!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import escalaService from "@/service/instituicao/EscalaService";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalVisualizaMenoesELimitesDaEscala",
  data() {
    return {
      dialog: false,
      escala: {},
      mensoesELimites: [],
      loading: true,
      headers: [
        {
          text: "Mínimo",
          align: "left",
          sortable: false,
          value: "minimo"
        },
        {
          text: "Máximo",
          align: "left",
          sortable: false,
          value: "maximo"
        },
        {
          text: "Mensão",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Símbolo",
          align: "left",
          sortable: false,
          value: "simbolo"
        }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaEscala", async escala => {
      this.escala = escala;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    async visualizar() {
      this.dialog = true;
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await escalaService.listarMensoesELimites(
        this.escala
      );
      this.mensoesELimites = paginacaoResultado;
      this.loading = false;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.mensoesELimites = [];
      }, 300);
    }
  }
};
</script>
