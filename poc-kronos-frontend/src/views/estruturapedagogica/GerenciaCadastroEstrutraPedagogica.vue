<template>
<div>
  <section-definition title="Estrutura Pedagógica" event-update="atualizarCurso"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Estrutura Pedagógica</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-checkbox type="checkbox" label="Mantenedora" disabled v-model="this.$store.getters.user.instituicaoMantenedora"></v-checkbox>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-checkbox type="checkbox" label="Ativa" disabled v-model="this.$store.getters.user.instituicaoAtiva"></v-checkbox>
      </v-flex>
    </v-layout>
    <v-layout wrap>
      <v-tabs color="primary" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Níveis</v-tab>
              <v-tab>Faixas/Anos</v-tab>
              <v-tab>Componentes</v-tab>
              <v-tab>Campos Experiência</v-tab>
              <v-tab>Direitos</v-tab>
              <v-tab>Objetivos</v-tab>
              <v-tab>Competências</v-tab>
              <v-tab>Habilidades</v-tab>
              <v-tab>Valores</v-tab>
              <v-tab>Atitudes</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-nivel /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-faixa-ano /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-componente /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-campo-experiencia /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-direito /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                         <v-card-text><lista-objetivo /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-competencia /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-habilidade /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-valor /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-atitude /></v-card-text>
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
import ListaNivel from "./ListaNivel";
import ListaFaixaAno from "./ListaFaixaAno";
import ListaComponente from "./ListaComponente";
import ListaCampoExperiencia from "./ListaCampoExperiencia";
import ListaDireito from "./ListaDireito";
import ListaObjetivo from "./ListaObjetivo";
import ListaCompetencia from "./ListaCompetencia";
import ListaHabilidade from "./ListaHabilidade";
import ListaValor from "./ListaValor";
import ListaAtitude from "./ListaAtitude";

export default {
  name: "GerenciaCadastroEstrutraPedagogica",
  components: {
    ListaNivel,
    ListaFaixaAno,
    ListaComponente,
    ListaCampoExperiencia,
    ListaDireito,
    ListaObjetivo,
    ListaCompetencia,
    ListaHabilidade,
    ListaValor,
    ListaAtitude
  }
};
</script>
