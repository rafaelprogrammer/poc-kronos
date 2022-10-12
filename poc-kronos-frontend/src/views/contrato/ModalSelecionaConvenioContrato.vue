<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" v-show="visualizaAcoes" slot="activator" color="primary" dark alt="Incluir Convênio" @click="abrirCadastro()" title="Incluir Convênio"><v-icon>add</v-icon>Incluir</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Convênio/Contrato</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-data-table :loading="loading" :headers="headers" :items="conveniosContratos" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.cnpj }}</td>
                    <td>{{ props.item.empresa }}</td>
                    <td>{{ props.item.tipoDesconto }}</td>
                    <td>{{ props.item.percentualDesconto }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Convênio não localizado!
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
import contratoService from "@/service/contrato/ContratoService";
import EventBus from "@/utils/EventBus";
import ConvenioContrato from "@/model/contrato/ConvenioContrato";
export default {
  name: "ModalSelecionaConvenioContrato",
  props: {
    dataContrato: String,
    conveniosContratosSelecionados: Array,
    visualizaAcoes: Boolean
  },
  data() {
    return {
      dialog: false,
      convenioContrato: new ConvenioContrato(),
      conveniosContratos: [],
      liberaPesquisa: false,
      loading: true,
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
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    abrirCadastro() {
      this.liberaPesquisa = true;
      this.pesquisar();
      this.dialog = true;
    },
    selecionar(convenioContrato) {
      this.dialog = false;
      EventBus.$emit("selecionaConvenioContrato", convenioContrato);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.conveniosContratos = await contratoService.listarConveniosContrato(
          null,
          this.dataContrato
        );
        if(this.conveniosContratosSelecionados) {
          this.conveniosContratosSelecionados.map(c => {
            this.conveniosContratos = this.conveniosContratos.filter(cc => {
              return c.id !== cc.id;
            });
          });
          this.loading = false;
        }
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.convenioContrato = new ConvenioContrato();
        this.liberaPesquisa = false;
      }, 300);
    }
  }
};
</script>
