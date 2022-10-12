<template>
  <v-layout wrap>
      <v-flex xs12 sm6 md12>
        <v-subheader inset>Filtro para Créditos Pendentes</v-subheader>
      </v-flex>
      <v-layout wrap>
          <v-flex xs12 sm6 md3>
            <v-select :disabled="!(numero && ano)" v-model="idPeriodoExecucaoPendente"
              @change="carregarTurma(idPeriodoExecucaoPendente)" item-text="sigla" item-value="id" :items="periodosPendentes" label="Período Pendente"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-select :disabled="turmaDesabilitada" v-model="idTurmaPendente"
              item-text="sigla" item-value="id" :items="turmasPendentes" label="Turma Pendente"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-select :disabled="turmaDesabilitada" v-model="idTipoCreditoPendente"
              item-text="nome" item-value="id" :items="tiposCreditosPendentes" label="Crédito Pendente"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-select :disabled="turmaDesabilitada" v-model="idTipoProgramaPendente"
              item-text="nome" item-value="id" :items="tiposProgramasPendentes" label="Programa Pendente"></v-select>
          </v-flex>
          <v-flex xs12 sm6 md11 class="text-xs-right" v-if="verificarVisualizaAcoes()">
            <v-btn color="primary" :disabled="gerarCreditoPendenteDesabilitado()" left direction="right" 
              alt="Gerar Créditos Pendentes" @click="gerarCreditosPendentes()" title="Gerar Créditos Pendentes">
              <v-icon>settings</v-icon>Gerar Créditos Pendentes
            </v-btn>
          </v-flex>
      </v-layout>
      <v-container grid-list-md>
          <v-data-table :loading="loading" :disabled="creditosContratosPendentes.length === 0"
              :headers="headers" :items="creditosContratosPendentes" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.siglaPeriodo }}</td>
                    <td>{{ props.item.nomeDisciplina }}</td>
                    <td>{{ props.item.disciplinaObrigatoria }}</td>
                    <td>{{ props.item.nomeTipoCredito }}</td>
                    <td>{{ props.item.nomeTipoPrograma }}</td>
                    <td>{{ props.item.siglaTurma }}</td>
                    <td>{{ props.item.anoTurma }}</td>
                    <td>
                      <v-btn  v-if="visualizaAcoes" color="primary" small dark @click="incluirContrato(props.item)">
                        <v-icon medium class="mr-2" >
                          add
                        </v-icon>
                        Incluir no Contrato
                      </v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Não existem créditos pendentes!
                </template>
             </v-data-table>
          </v-container>
  </v-layout>
</template>
<script>
import turmaService from "@/service/turma/TurmaService";
import periodoService from "@/service/curso/PeriodoService";
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
export default {
  name: "GeraCreditosPendentes",
  props: {
    tiposCreditosPendentes: Array,
    tiposProgramasPendentes: Array,
    numero: Number,
    ano: Number,
    idMatricula: Number,
    idCurso: Number,
    visualizaAcoes: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      turmaDesabilitada: true,
      idPeriodoExecucaoPendente: null,
      idTurmaPendente: null,
      idTipoCreditoPendente: null,
      idTipoProgramaPendente: null,
      loading: false,
      creditosContratosPendentes: [],
      periodosPendentes: [],
      turmasPendentes: [],
      headers: [
        {
          text: "Período",
          align: "left",
          sortable: false,
          value: "siglaPeriodo"
        },
        {
          text: "Disciplina",
          align: "left",
          sortable: false,
          value: "nomeDisciplina"
        },
        {
          text: "Obrigatória",
          align: "left",
          sortable: false,
          value: "disciplinaObrigatoria"
        },
        {
          text: "Crédito",
          align: "left",
          sortable: false,
          value: "nomeTipoCredito"
        },
        {
          text: "Programa",
          align: "left",
          sortable: false,
          value: "nomeTipoPrograma"
        },
        {
          text: "Turma",
          align: "left",
          sortable: false,
          value: "siglaTurma"
        },
        {
          text: "Ano",
          align: "left",
          sortable: false,
          value: "anoTurma"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    numero() {
      this.pesquisarPeriodosPendentes();
    },
    ano() {
      this.pesquisarPeriodosPendentes();
    }
  },
  methods: {
    async pesquisarPeriodosPendentes() {
      if (this.ano && this.numero) {
        this.periodosPendentes = await periodoService.comboPeriodoPendenticaContrato(this.idCurso, this.ano, this.idMatricula, this.numero);
      }
    },
    async carregarTurma(idPeriodoExecucaPendente) {
      this.turmasPendentes = await turmaService.combo(idPeriodoExecucaPendente);
      if (this.turmasPendentes && this.turmasPendentes.length > 0) {
        this.turmaDesabilitada = false;
      } else {
        this.turmaDesabilitada = true;
      }
    },
    incluirContrato(credito) {
      EventBus.$emit("incluiCreditoPendente", credito);
      this.creditosContratosPendentes.splice(this.creditosContratosPendentes.indexOf(credito), 1);
    },
    gerarCreditoPendenteDesabilitado() {
      return !(this.idPeriodoExecucaoPendente && this.idTipoCreditoPendente &&
        this.idTipoProgramaPendente && this.idTurmaPendente);
    },
    verificarVisualizaAcoes() {
      this.numero = null;
      this.ano = null;
      return this.visualizaAcoes;
    },
    async gerarCreditosPendentes() {
      this.creditosContratosPendentes = await matriculaService.gerarCreditosPendentes(this.idPeriodoExecucaoPendente, this.idMatricula,
       this.idTurmaPendente, this.idTipoCreditoPendente, this.idTipoCreditoPendente);
    }
  }
};
</script>
