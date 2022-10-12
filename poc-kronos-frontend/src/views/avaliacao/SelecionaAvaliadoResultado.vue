<template>
    <div>
      <v-subheader>Resultados</v-subheader>
      <v-data-table v-model="selected" select-all :loading="loading" hide-actions :headers="headers" :items="resultadosHabilidades" class="elevation-1">
        <template slot="items" slot-scope="props">
          <tr :active="props.selected" @click="props.selected = !props.selected">
            <td>
              <v-checkbox
                :input-value="props.selected"
                primary
                hide-details
              ></v-checkbox>
            </td>
            <td>{{ props.item.numeroRegistro }}</td>
            <td>{{ props.item.nomeAvaliado }}</td>
            <td>{{ props.item.situacao }}</td>
            <td>{{ props.item.mencoes }}</td>
          </tr>
        </template>
        <template slot="no-data">
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </div>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import EventBus from "@/utils/EventBus.js";
import Avaliado from "@/model/avaliacao/Avaliado";

export default {
  name: "SelecionaAvaliadoResultado",
  props: {
    idAvaliacao: Number,
    dataAvaliacao: String
  },
  data() {
    return {
      loading: false,
      resultadosHabilidades: [],
      liberaPesquisa: false,
      selected: [],
      headers: [
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomeAvaliado"
        },
        {
          text: "Situação",
          align: "left",
          sortable: false,
          value: "situacao"
        },
        {
          text: "Menções",
          align: "left",
          sortable: false,
          value: "mencoes"
        }
      ]
    };
  },
  watch: {
    // idAvaliacao() {
    //   console.log(this.idAvaliacao);
    //   this.pesquisar();
    // },
    selected() {
      this.enviarIdsAvaliados();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    EventBus.$on("limpaSelecao", () => {
      this.selected = [];
    });
    EventBus.$on("pesquisaResultados", (anoTurma) => {
      this.anoTurma = anoTurma;
      this.pesquisar();
    });
  },
  methods: {
    enviarIdsAvaliados() {
      setTimeout(() => {
        const idsAvaliados = this.selected.map(s => s.id);
        const idsMencoes = this.selected.map(s => s.idMencao);
        EventBus.$emit("idsAvaliadosSelecionados", idsAvaliados, idsMencoes);
      }, 500);
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await avaliacaoService.listarResultadoHabiliadeDoAvaliado(
        this.idAvaliacao,
        this.dataAvaliacao,
        this.anoTurma
      );
      this.resultadosHabilidades = paginacaoResultado.map(d => new Avaliado(d));
      this.loading = false;
    }
  }
};
</script>
