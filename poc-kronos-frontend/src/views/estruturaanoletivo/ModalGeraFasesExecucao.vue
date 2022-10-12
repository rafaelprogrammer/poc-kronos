<template>
      <v-dialog v-model="dialog" persistent fullscreen width="50%">
        <v-card>
          <v-card-title>
            <span class="headline">Gerar Fases Execução (Semestres)</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap justify-space-between>
                <v-flex xs12 sm2 md12>
                  <v-subheader>Curso / Ano</v-subheader>
                </v-flex>
                <v-flex xs12 sm6 md10>
                  <v-text-field color="primary" disabled label="Curso" v-model="nomeCurso"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" disabled label="Ano" v-model="ano"></v-text-field>
                </v-flex>
                <v-flex xs12 sm2 md12>
                  <v-subheader>Fases Previstas (Semestres)</v-subheader>
                  <v-alert :value="true" type="error" v-if="obrigatorio">
                      {{mensagerErroObrigatorio}}
                  </v-alert>
                </v-flex>
              </v-layout>
              <v-data-table :loading="loading" :headers="headers" :items="fasesResumos" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numero }}</td>
                    <td>{{ props.item.sigla }}</td>
                    <td>
                      <date-picker-custom label="Data Inicial" :v-model="props.item.dataInicio" @date="props.item.dataInicio = $event"></date-picker-custom>
                    </td>
                    <td>
                      <date-picker-custom label="Data Final" :v-model="props.item.dataFim" @date="props.item.dataFim = $event"></date-picker-custom>
                    </td>
                  </template>
                <template slot="no-data">
                    Dados não localizados!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
            <v-btn color="primary" type="submit" @click.native="gerar" dark>Gerar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";

export default {
  name: "ModalGeraFasesExecucao",
  components: {
    DatePickerCustom
  },
  data() {
    return {
      dialog: false,
      idCurso: null,
      nomeCurso: null,
      ano: null,
      fasesResumos: [],
      liberaPesquisa: false,
      obrigatorio: false,
      loading: true,
      mensagerErroObrigatorio: "Data Inicio e Final de todas as fases são obrigatórios",
      headers: [
        {
          text: "Numero",
          align: "left",
          sortable: false,
          value: "numero"
        },
        {
          text: "Sigla",
          align: "left",
          sortable: false,
          value: "sigla"
        },
        {
          text: "Data Inicial",
          align: "left",
          sortable: false,
          value: "dataInicio"
        },
        {
          text: "Data Final",
          align: "left",
          sortable: false,
          value: "dataFim"
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
    EventBus.$on("abrirGeraFases", (idCurso, nomeCurso, ano) => {
      this.liberaPesquisa = true;
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.ano = ano;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    gerar() {
      const datasNulas = this.fasesResumos.filter(fase => !fase.dataInicio || !fase.dataFim );
      if (datasNulas && datasNulas.length > 0) {
        this.obrigatorio = true;
        return;
      }
      periodoExecucaoService.salvarFasesExecucoes(this.idCurso, this.ano, this.fasesResumos).then((dados) => {
        if (dados) {
          this.dialog = false;
          this.$emit("fasesExecucoesGeradasSucesso", true);
        }
      });
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.fasesResumos = await periodoExecucaoService.listarParaGeracaoFaseExecucao(this.idCurso);
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.liberaPesquisa = false;
        this.obrigatorio = false;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
