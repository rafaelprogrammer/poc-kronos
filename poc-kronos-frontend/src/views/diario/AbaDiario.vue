<template>
  <v-layout wrap>
          <v-flex xs12 sm12 md12 >
            <v-tabs dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Direitos</v-tab>
              <v-tab>Objetivos</v-tab>
              <v-tab>Competências</v-tab>
              <v-tab>Habilidades</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-atividade-disciplina-direito :id-disciplina="idDisciplina"
                          :id-curso="idCurso" :id-periodo="idPeriodo" :id-sub-fase="idSubFase"
                          :id-atividade="idAtividade" />
                          </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-atividade-disciplina-objetivo :id-disciplina="idDisciplina"
                          :id-curso="idCurso" :id-periodo="idPeriodo"
                          :id-atividade="idAtividade" :id-sub-fase="idSubFase" />
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-atividade-disciplina-competencia :id-disciplina="idDisciplina"
                          :id-curso="idCurso" :id-periodo="idPeriodo" :id-sub-fase="idSubFase"
                          :id-atividade="idAtividade" />
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-atividade-disciplina-habilidade :id-disciplina="idDisciplina"
                          :id-curso="idCurso" :id-periodo="idPeriodo" :id-sub-fase="idSubFase"
                          :id-atividade="idAtividade" :id-turma="idTurma" />
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
              </v-tabs-items>
            </v-tabs>
          </v-flex>
          <v-flex xs12 sm12 md12 v-if="idDisciplina && idAtividade">
            <lista-diario :id-atividade="idAtividade" />
          </v-flex>
        </v-layout>
</template>
<script>
import ListaDiario from "./ListaDiario";
import ListaAtividadeDisciplinaDireito from "./ListaAtividadeDisciplinaDireito";
import ListaAtividadeDisciplinaObjetivo from "./ListaAtividadeDisciplinaObjetivo";
import ListaAtividadeDisciplinaCompetencia from "./ListaAtividadeDisciplinaCompetencia";
import ListaAtividadeDisciplinaHabilidade from "./ListaAtividadeDisciplinaHabilidade";

export default {
  name: "AbaDiario",
  props: {
    idPeriodo: Number,
    idCurso: Number,
    idSubFase: Number,
    idDisciplina: Number,
    idAtividade: Number,
    idTurma: Number
  },
  components: {
    ListaDiario,
    ListaAtividadeDisciplinaDireito,
    ListaAtividadeDisciplinaObjetivo,
    ListaAtividadeDisciplinaCompetencia,
    ListaAtividadeDisciplinaHabilidade
  }
};
</script>
