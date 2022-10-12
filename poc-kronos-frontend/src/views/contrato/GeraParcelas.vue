<template>
  <v-layout wrap justify-center>
       <modal-edita-parcela @parcelaCadastrada="cadastroEfetuado = $event"/>
       <v-flex xs12 sm6 md12>
        <v-subheader inset>Parcelas</v-subheader>
       </v-flex>
      <v-layout wrap>
          <v-flex xs12 sm6 md11 class="text-xs-right">
            <v-btn color="primary" v-show="visualizaAcoes && contrato.valorTotalFinal > 0.0" left direction="right" alt="Gerar Créditos" @click="gerarParcelas()" title="Gerar Parcelas"><v-icon>settings</v-icon>Gerar Parcelas</v-btn>
          </v-flex>
      </v-layout>
      <v-container grid-list-md>
          <v-data-table :loading="loading"
              :headers="headers" :items="parcelas" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numero }}</td>
                    <td>{{ props.item.nomeTipoFormaPagamento }}</td>
                    <td>{{ props.item.dataVencimento }}</td>
                    <td>{{ props.item.valorOriginal }}</td>
                    <td>{{ props.item.valorJuros }}</td>
                    <td>{{ props.item.valorMulta }}</td>
                    <td>{{ props.item.valorDesconto }}</td>
                    <td>{{ props.item.valorPagamento }}</td>
                    <td>
                      <v-icon v-show="visualizaAcoes" medium class="mr-2" @click="editar(props.item)" >
                        edit
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem parcelas geradas!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import parcelaService from "@/service/parcela/ParcelaService";
import Parcela from "@/model/parcela/Parcela";
import EventBus from "@/utils/EventBus";
import ModalEditaParcela from "./ModalEditaParcela";
export default {
  name: "GeraParcelas",
  components: {
    ModalEditaParcela
  },
  props: {
    contrato: Object,
    visualizaAcoes: Boolean
  },
  watch: {
    contrato() {
      this.parcelas = this.contrato.parcelas ? this.contrato.parcelas : [];
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    }
  },
  data() {
    return {
      cadastroEfetuado: false,
      loading: false,
      parcela: new Parcela(),
      parcelas: [],
      headers: [
        {
          text: "Número",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Forma Pagamento",
          align: "left",
          sortable: false,
          value: "nomeTipoFormaPagamento"
        },
        {
          text: "Data Vencimento",
          align: "left",
          sortable: false,
          value: "dataVencimento"
        },
        {
          text: "Valor",
          align: "left",
          sortable: false,
          value: "valorOriginal"
        },
        {
          text: "Valor Juros",
          align: "left",
          sortable: false,
          value: "valorJuros"
        },
        {
          text: "Valor Multa",
          align: "left",
          sortable: false,
          value: "valorMulta"
        },
        {
          text: "Valor Desconto",
          align: "left",
          sortable: false,
          value: "valorDesconto"
        },
        {
          text: "Valor Pagamento",
          align: "left",
          sortable: false,
          value: "valorPagamento"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  methods: {
    async editar(parcela) {
      EventBus.$emit("editaParcela", parcela.id);
    },
    gerarParcelas() {
      this.parcela.idContrato = this.contrato.id;
      this.parcela.numeroParcelas = this.contrato.numeroParcelas;
      parcelaService.gerarParcelas(this.parcela).then(() => {
        setTimeout(async () => {
          this.pesquisar();
          this.$emit("parcelasGeradas", this.parcelas);
        }, 500);
      });
    },
    async pesquisar() {
      this.parcelas = await parcelaService.listarTodos(this.parcela);
    }
  }
};
</script>
