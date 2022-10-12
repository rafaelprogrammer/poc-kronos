<template>
  <v-layout wrap justify-center>
       <v-flex xs12 sm6 md12>
        <v-subheader >Grupos</v-subheader>
       </v-flex>
      <v-container grid-list-md>
          <v-data-table
              :headers="headers" :items="avaliacao.gruposAvaliacoes" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numero }}</td>
                    <td>{{ props.item.tema }}</td>
                  </template>
                <template slot="no-data">
                    Não existem créditos!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import EventBus from "@/utils/EventBus";

export default {
  name: "ListaGrupoAvaliacao",
  props: {
    avaliacao: Object
  },
  data() {
    return {
      cadastroEfetuado: null,
      gruposAvaliacoes: [],
      headers: [
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Tema",
          align: "left",
          sortable: false,
          value: "tema"
        }
      ]
    };
  }
};
</script>
