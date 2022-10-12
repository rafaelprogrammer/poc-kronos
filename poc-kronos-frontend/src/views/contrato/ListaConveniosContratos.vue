<template>
  <v-layout wrap justify-center>
       <v-flex xs12 sm6 md12>
        <v-subheader inset>Convênio</v-subheader>
        <modal-seleciona-convenio-contrato
        :visualiza-acoes="visualizaAcoes"
        :convenios-contratos-selecionados="conveniosContratos" :data-contrato="contrato.data"/>
       </v-flex>
      <v-container grid-list-md>
        <v-layout wrap justify-center>
            <v-flex xs12 sm6 md4>
                <v-text-field disabled v-model="contrato.percentualDescontoConvenio" label="% Desconto"></v-text-field>
            </v-flex>
            <v-flex xs12 sm6 md4>
                <v-text-field disabled v-model="contrato.valorDescontoConvenio" label="Valor Total Desconto"></v-text-field>
            </v-flex>
          </v-layout>
          <v-data-table :loading="loading"
              :headers="headers" :items="conveniosContratos" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.cnpj }}</td>
                    <td>{{ props.item.empresa }}</td>
                    <td>{{ props.item.tipoDesconto }}</td>
                    <td>{{ props.item.percentualDesconto }}</td>
                    <td>
                      <v-icon v-show="visualizaAcoes" medium class="mr-2" @click="excluir(props.item)" >
                        delete
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem convênios!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import contratoService from "@/service/contrato/ContratoService";
import ModalSelecionaConvenioContrato from "./ModalSelecionaConvenioContrato";
import EventBus from "@/utils/EventBus";
export default {
  name: "ListaConveniosContratos",
  components: {
    ModalSelecionaConvenioContrato
  },
  props: {
    contrato: Object,
    visualizaAcoes: Boolean
  },
  watch: {
    contrato() {
      this.conveniosContratos = this.contrato.conveniosContratos;
    }
  },
  data() {
    return {
      loading: false,
      conveniosContratos: [],
      headers: [
        {
          text: "CNPJ",
          align: "left",
          sortable: false,
          value: "cnpj"
        },
        {
          text: "Empresa",
          align: "left",
          sortable: false,
          value: "empresa"
        },
        {
          text: "Tipos Desconto",
          align: "left",
          sortable: false,
          value: "tipoDesconto"
        },
        {
          text: "Percentual Desconto",
          align: "left",
          sortable: false,
          value: "percentualDesconto"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  created() {
    EventBus.$on("selecionaConvenioContrato", async convenioContrato => {
      this.conveniosContratos.push(convenioContrato);
      this.emitir();
    });
  },
  methods: {
    async excluir(convenioContrato) {
      confirm("Você deseja realmente excluir o Contrato Convênio ?") &&
        await contratoService.excluirContratoConvenio(this.contrato.id, convenioContrato.id);
        this.conveniosContratos.splice(this.conveniosContratos.indexOf(convenioContrato), 1);
        this.emitir();
    },
    emitir() {
      this.$emit("conveniosContratosSelecionados", this.conveniosContratos);
    }
  }
};
</script>
