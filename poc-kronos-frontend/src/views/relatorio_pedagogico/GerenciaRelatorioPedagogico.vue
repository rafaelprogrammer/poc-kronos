<template>
<div>
  <section-definition title="Estrutura Pedagógica" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Relatórios Pedagógico</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-tabs color="primary" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Diário</v-tab>
              <v-tab>Frequência</v-tab>
              <v-tab>Resultados Bimestrais</v-tab>
              <v-tab>Resultados Semestrais</v-tab>
              <v-tab>Resultados Finais</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-diario /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-frequencia /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-resultado-bimestre /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text></v-card-text>
                      </v-card>
                    </v-tab-item>
              </v-tabs-items>
            </v-tabs>
    </v-layout>
  </v-container>
</div>
</template>
<script>
import EventBus from "@/utils/EventBus.js";
import ListaDiario from "./ListaDiario";
import ListaFrequencia from "./ListaFrequencia";
import ListaResultadoBimestre from "./ListaResultadoBimestre";

export default {
  name: "GerenciaRelatorioPedagogico",
  components: {
    ListaDiario,
    ListaFrequencia,
    ListaResultadoBimestre
  }
};
</script>
