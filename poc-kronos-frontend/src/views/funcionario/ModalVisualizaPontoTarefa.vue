<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field label="Período"   v-model="ponto.nomeTipoPeriodoPonto" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Início Prevista"   v-model="ponto.horaInicialPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Hora Final Prevista"   v-model="ponto.horaFinalPrevista" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Hora Início Informado"   v-model="ponto.horaInicialInformado" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Hora Final Informado"   v-model="ponto.horaFinalInformado" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Data Liberação"   v-model="ponto.dataLiberacao" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Data Homologação"   v-model="ponto.dataHomologacao" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Funcionário Liberação"   v-model="ponto.funcionarioLiberacao" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm2 md6>
                <v-text-field label="Funcionário Homologação"   v-model="ponto.funcionarioHomologacao" disabled></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Descrição" v-model="ponto.descricao" disabled></v-textarea>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import funcionarioService from "@/service/funcionario/FuncionarioService";
import EventBus from "@/utils/EventBus";
import Ponto from "@/model/funcionario/Ponto";
export default {
  name: "ModalVisualizaPontoTarefa",
  data() {
    return {
      dialog: false,
      formTitle: "",
      ponto: new Ponto(),
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaPontoTarefa", async ponto => {
      this.formTitle = "Visualiza Ponto / Tarefa";
      this.ponto = ponto;
      this.recuperarHorasPrevistas(this.ponto.idTipoPeriodoPonto);
      this.dialog = true;
    });
  },
  methods: {
    async recuperarHorasPrevistas(idTipoPeriodoPonto) {
      if (!this.ponto.tarefaOnline) {
        const ponto = await funcionarioService.horasPrevistasPonto(
          idTipoPeriodoPonto
        );
        this.ponto.horaInicialPrevista = ponto.horaInicialPrevista;
        this.ponto.horaFinalPrevista = ponto.horaFinalPrevista;
      }
    },
    close() {
      this.dialog = false;
    }
  }
};
</script>
