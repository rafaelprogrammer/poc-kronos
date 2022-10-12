<template>
<div>
  <section-definition title="Plano Pedagógico" event-update="atualizar"></section-definition>
  <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro Plano Pedagógico</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
  </v-toolbar>
  <v-container fluid>
    <v-layout wrap>
      <v-flex xs12 sm6 md6>
        <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
      </v-flex>
       <v-flex xs12 sm6 md3>
        <v-select v-model="idCurso" @change="atualizarIdNivel(idCurso)" item-text="nome" item-value="id" :items="cursos" label="Curso"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md3>
        <v-select v-model="idPeriodo" @change="atualizarIdFaixaAno(idPeriodo)" :items="periodos" item-text="sigla" item-value="id" label="Período"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md7>
        <v-select v-model="idDisciplina" @change="atualizarIdComponente(idDisciplina)" item-text="nome" item-value="id" :items="disciplinas"
        label="Disciplina"></v-select>
      </v-flex>
      <v-flex xs12 sm6 md12 v-if="idDisciplina">
      <v-tabs color="primary" dark next-icon="mdi-arrow-right-bold-box-outline" prev-icon="mdi-arrow-left-bold-box-outline" show-arrows>
              <v-tabs-slider color="yellow"></v-tabs-slider>
              <v-tab>Direitos</v-tab>
              <v-tab>Objetivos</v-tab>
              <v-tab>Competências</v-tab>
              <v-tab>Habilidades</v-tab>
              <v-tabs-items>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-disciplina-direito :id-disciplina="idDisciplina" :id-nivel="idNivel" 
                          :id-curso="idCurso" :id-periodo="idPeriodo"/>
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-disciplina-objetivo :id-disciplina="idDisciplina" :id-faixa-ano="idFaixaAno" 
                          :id-curso="idCurso" :id-periodo="idPeriodo"/>
                        </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-disciplina-competencia :id-disciplina="idDisciplina" :id-nivel="idNivel" :id-componente="idComponente" 
                          :id-curso="idCurso" :id-periodo="idPeriodo"/>
                         </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item lazy>
                      <v-card flat>
                        <v-card-text>
                          <lista-disciplina-habilidade :id-disciplina="idDisciplina" :id-componente="idComponente" 
                          :id-curso="idCurso" :id-periodo="idPeriodo"/>
                        </v-card-text>
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
import cursoService from "@/service/curso/CursoService";
import periodoService from "@/service/curso/PeriodoService";
import disciplinaService from "@/service/disciplina/DisciplinaService";
import DisciplinaDireito from "@/model/disciplina/direito/DisciplinaDireito";
import Pagination from "@/utils/Pagination.js";
import ListaDisciplinaDireito from "./ListaDisciplinaDireito";
import ListaDisciplinaObjetivo from "./ListaDisciplinaObjetivo";
import ListaDisciplinaCompetencia from "./ListaDisciplinaCompetencia";
import ListaDisciplinaHabilidade from "./ListaDisciplinaHabilidade";
import EventBus from "@/utils/EventBus.js";

export default {
  name: "GerenciaCadastroPlanoPedagogico",
  components: {
    ListaDisciplinaDireito,
    ListaDisciplinaObjetivo,
    ListaDisciplinaCompetencia,
    ListaDisciplinaHabilidade
  },
  data() {
    return {
      idNivel: null,
      idFaixaAno: null,
      idComponente: null,
      anos: [],
      ano: null,
      idCurso: null,
      cursos: [],
      idPeriodo: null,
      periodos: [],
      idDisciplina: null,
      disciplinas: [],
    };
  },
  watch: {
    async idCurso() {
      if (this.idCurso) {
        this.periodos = await periodoService.combo(this.idCurso);
      }
      this.idDisciplina = null;
    },
    async idPeriodo() {
      if (this.idPeriodo) {
        this.disciplinas = await disciplinaService.combo(this.idPeriodo);
      }
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  created() {
    this.carregarDadosBasicos();
  },
  methods: {
    atualizarIdNivel(id) {
      const arrayCurso = this.cursos.filter(c => {
        return c.id === id;
      });
      this.idNivel = arrayCurso[0].idNivel;
    },
    atualizarIdFaixaAno(id) {
      const arrayPeriodo = this.periodos.filter(c => {
        return c.id === id;
      });
      this.idFaixaAno = arrayPeriodo[0].idFaixaAno;
    },
    atualizarIdComponente(id) {
      const arrayDisciplina = this.disciplinas.filter(c => {
        return c.id === id;
      });
      this.idComponente = arrayDisciplina[0].idComponente;
    },
    async carregarDadosBasicos() {
      this.cursos = await cursoService.combo();
    }
  }
};
</script>
