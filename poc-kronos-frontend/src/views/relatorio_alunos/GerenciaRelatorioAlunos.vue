<template>
<div>
  <section-definition title="Relatórios de Alunos" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Relatórios de Alunos</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md12>
      <v-tabs color="primary" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Alunos/Turma(R)</v-tab>
              <v-tab>Alunos/Período(R)</v-tab>
              <v-tab>Alunos/Curso(R)</v-tab>
              <v-tab>Alunos/Turma(P)</v-tab>
              <v-tab>Alunos/Período(P)</v-tab>
              <v-tab>Alunos/Curso(P)</v-tab>
              <v-tab>Pais/Alunos(P)</v-tab>
              <v-tab>Responsáveis/Alunos(P)</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text><lista-alunos-por-turma-r-situacao /></v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                         <v-card-text><lista-alunos-por-periodo-r-situacao /></v-card-text>
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
            </v-flex>
    </v-layout>
  </v-container>
</div>
</template>
<script>
import ListaAlunosPorTurmaRSituacao from "./ListaAlunosPorTurmaRSituacao";
import ListaAlunosPorPeriodoRSituacao from "./ListaAlunosPorPeriodoRSituacao";

export default {
  name: "GerenciaRelatorioAlunos",
  components: {
    ListaAlunosPorTurmaRSituacao,
    ListaAlunosPorPeriodoRSituacao
  }
};
</script>
