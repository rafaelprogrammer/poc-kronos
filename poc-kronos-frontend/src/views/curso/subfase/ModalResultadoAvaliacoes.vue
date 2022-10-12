<template>
      <v-dialog v-model="dialog" persistent width="50%">
        <v-card>
          <v-card-title>
            <span class="headline">Resultados Avaliações</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md3>
                  <v-text-field v-model="numeroRegistro" disabled label="Nr Registro"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md8>
                  <v-text-field v-model="nomeAluno" disabled label="Nome"></v-text-field>
                </v-flex>
              </v-layout>
              <v-data-table  :loading="loading" hide-actions :headers="headers" :items="resultadosAvaliacoes" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.dataPrevista }}</td>
                    <td>{{ props.item.tipo }}</td>
                    <td>{{ props.item.codigo }}</td>
                    <td>{{ props.item.mencao }}</td>
                  </template>
                <template slot="no-data">
                   Não existem resultados avaliações
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Fechar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import subFaseService from "@/service/curso/SubFaseService";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalResultadoAvaliacoes",
  data() {
    return {
      dialog: false,
      numeroRegistro: null,
      nomeAluno: null,
      resultadosAvaliacoes: [],
      loading: false,
      headers: [
        {
          text: "Data",
          align: "left",
          sortable: false,
          value: "dataPrevista"
        },
        {
          text: "Tipo",
          align: "left",
          sortable: false,
          value: "tipo"
        },
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Menção",
          align: "left",
          sortable: false,
          value: "mencao"
        }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("abrirResultadoAvaliacoes", async (idCredito, idTurma, idDisciplina, dataInicial, dataFinal, numeroRegistro, nomeAluno) => {
      this.dialog = true;
      this.loading = true;
      this.resultadosAvaliacoes = await subFaseService.listarAvaliacoesResultados(idTurma, idDisciplina, idCredito, dataInicial, dataFinal);
      this.numeroRegistro = numeroRegistro;
      this.nomeAluno = nomeAluno;
      this.loading = false;
    });
  },
  methods: {
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.resultadosAvaliacoes = [];
      }, 300);
    }
  }
};
</script>
