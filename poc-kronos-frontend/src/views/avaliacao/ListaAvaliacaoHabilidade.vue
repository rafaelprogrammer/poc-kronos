<template>
  <v-layout wrap justify-center>
       <v-flex xs12 sm6 md12>
        <v-subheader >Habilidades</v-subheader>
       </v-flex>
      <v-container grid-list-md>
          <v-data-table
              :headers="headers" :items="avaliacao.avaliacoesHabilidades" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.codigo }}</td>
                    <td width="50%">
                      <v-tooltip bottom>
                          <span slot="activator" class="">{{ props.item.descricao | limitCaracteres(100) }}</span>
                          <span>{{ props.item.descricao }}</span>
                      </v-tooltip>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem avaliações habilidades!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import EventBus from "@/utils/EventBus";
import ModalSelecionaDisciplinaHabilidade from "./ModalSelecionaDisciplinaHabilidade";
import { limitCaracteres } from "@/filters/Filters.js";

export default {
  name: "ListaAvaliacaoHabilidade",
  props: {
    avaliacao: Object
  },
  components: {
    ModalSelecionaDisciplinaHabilidade
  },
  filters: {
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      cadastroEfetuado: null,
      avaliacoesHabilidades: [],
      headers: [
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        }
      ]
    };
  }
};
</script>
