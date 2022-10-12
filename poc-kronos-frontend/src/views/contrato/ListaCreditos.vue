<template>
  <v-layout wrap justify-center>
       <v-flex xs12 sm6 md12>
        <v-subheader inset>Créditos</v-subheader>
       </v-flex>
      <v-container grid-list-md>
          <v-layout wrap justify-center>
            <v-flex xs12 sm6 md4>
                <v-text-field disabled v-model="contrato.valorTotalPadrao" label="Valor Total Padrão"></v-text-field>
            </v-flex>
            <v-flex xs12 sm6 md4>
                <v-text-field disabled v-model="contrato.valorTotalPendencia" label="Valor Total Pendência"></v-text-field>
            </v-flex>
            <v-flex xs12 sm6 md4>
                <v-text-field disabled v-model="contrato.valorTotalOriginal" label="Valor Total Original"></v-text-field>
            </v-flex>
          </v-layout>
          <v-data-table :loading="loading"
              :headers="headers" :items="creditosContratos" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.siglaPeriodo }}</td>
                    <td>{{ props.item.nomeDisciplina }}</td>
                    <td>{{ props.item.disciplinaObrigatoria }}</td>
                    <td>{{ props.item.nomeTipoCredito }}</td>
                    <td>{{ props.item.nomeTipoPrograma }}</td>
                    <td>{{ props.item.siglaTurma }}</td>
                    <td>{{ props.item.anoTurma }}</td>
                    <td>{{ props.item.cargaHorariaTotal }}</td>
                    <td>{{ props.item.cargaHorariaPresencial }}</td>
                    <td>{{ props.item.cargaHorariaDistancia }}</td>
                    <td>{{ props.item.valor }}</td>
                  </template>
                <template slot="no-data">
                    Não existem créditos!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import matriculaService from "@/service/matricula/MatriculaService";
import creditoService from "@/service/credito/CreditoService";
import EventBus from "@/utils/EventBus";
export default {
  name: "ListaCreditos",
  props: {
    contrato: Object,
    creditosContratos: Array
  },
  data() {
    return {
      loading: false,
      headers: [
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "siglaPeriodo"
        },
        {
          text: "Disciplina",
          align: "left",
          sortable: false,
          value: "nomeDisciplina"
        },
        {
          text: "Obrigatória",
          align: "left",
          sortable: false,
          value: "disciplinaObrigatoria"
        },
        {
          text: "Crédito",
          align: "left",
          sortable: false,
          value: "nomeTipoCredito"
        },
        {
          text: "Programa",
          align: "left",
          sortable: false,
          value: "nomeTipoPrograma"
        },
        {
          text: "Turma",
          align: "left",
          sortable: false,
          value: "siglaTurma"
        },
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "anoTurma"
        },
        {
          text: "CHT",
          align: "left",
          sortable: false,
          value: "cargaHorariaTotal"
        },
        {
          text: "CHP",
          align: "left",
          sortable: false,
          value: "cargaHorariaPresencial"
        },
        {
          text: "CHD",
          align: "left",
          sortable: false,
          value: "cargaHorariaDistancia"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valor"
        }
      ]
    };
  },
  created() {
    EventBus.$on("limpaCreditos", async () => {
      this.creditosContratos = [];
    });
  },
  methods: {
  }
};
</script>
