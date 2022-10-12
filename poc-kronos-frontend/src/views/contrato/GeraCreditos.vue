<template>
  <v-layout wrap justify-center>
       <v-flex xs12 sm6 md12>
        <v-subheader inset>Créditos Contratados</v-subheader>
       </v-flex>
      <v-layout wrap v-if="verificarVisualizaAcoes()">
          <v-flex xs12 sm6 md11 class="text-xs-right">
            <v-btn color="primary" :disabled="gerarCreditoDesabilitado()" left direction="right" alt="Gerar Créditos" @click="gerarCreditos()" title="Gerar Créditos"><v-icon>settings</v-icon>Gerar Créditos</v-btn>
          </v-flex>
      </v-layout>
      <v-container grid-list-md>
          <v-data-table :loading="loading" :disabled="creditosContratos.length === 0"
              :headers="headers" :items="creditosContratos" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.siglaPeriodo }}</td>
                    <td>{{ props.item.nomeDisciplina }}</td>
                    <td>{{ props.item.disciplinaObrigatoria | booleanFilter }}</td>
                    <td>{{ props.item.nomeTipoCredito }}</td>
                    <td>{{ props.item.nomeTipoPrograma }}</td>
                    <td>{{ props.item.siglaTurma }}</td>
                    <td>{{ props.item.anoTurma }}</td>
                    <td>
                      <v-icon v-if="visualizaAcoes" medium class="mr-2" @click="excluirItem(props.item)" >
                        delete
                      </v-icon>
                    </td>
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
import { booleanFilter } from "@/filters/Filters.js";

export default {
  name: "GeraCreditos",
  props: {
    contrato: Object,
    idTurma: Number,
    creditosContratosSalvos: Array,
    visualizaAcoes: {
      type: Boolean,
      default: true
    }
  },
  filters: {
    booleanFilter: booleanFilter
  },
  data() {
    return {
      loading: false,
      creditosContratos: [],
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
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  created() {
    EventBus.$on("limpaCreditos", async () => {
      this.creditosContratos = [];
    });
  },
  methods: {
    verificarVisualizaAcoes() {
      this.creditosContratos = this.creditosContratosSalvos;
      return this.visualizaAcoes;
    },
    async excluirItem(creditoContrato) {
      confirm("Você deseja realmente excluir o Crédito ?") &&
        await creditoService.excluir(creditoContrato);
        this.creditosContratos.splice(this.creditosContratos.indexOf(creditoContrato), 1);
    },
    gerarCreditoDesabilitado() {
      if (this.contrato.id !== null && this.creditosContratosSalvos && this.creditosContratosSalvos.length > 0) {
        this.creditosContratos = this.creditosContratosSalvos;
        return false;
      }
      return !(this.contrato.idPeriodoExecucao && this.contrato.idTipoCredito &&
        this.contrato.idTipoPrograma && this.idTurma);
    },
    async gerarCreditos() {
      this.creditosContratos = await matriculaService.gerarCreditos(this.contrato, this.idTurma);
      if (this.contrato.id !== null && this.creditosContratosSalvos && this.creditosContratosSalvos.length > 0) {
        this.creditosContratos.push(...this.creditosContratosSalvos);
      }  
      this.$emit("creditosGerados", this.creditosContratos);
    }
  }
};
</script>
