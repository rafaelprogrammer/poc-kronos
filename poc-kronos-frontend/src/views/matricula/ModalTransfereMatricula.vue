<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" dark color="primary" alt="Transferir Matrícula" @click="abrirCadastro()" title="Transferir Matrícula"><v-icon>settings</v-icon>Registrar Transferência</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="NR Registro" v-model="matriculaCanceladaTransferida.numeroRegistroPessoa"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="CPF" v-model="matriculaCanceladaTransferida.cpfPessoa"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md4>
                <v-text-field color="primary" disabled label="Nome" v-model="matriculaCanceladaTransferida.nomePessoa"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="NR Contrato" v-model="matriculaCanceladaTransferida.numeroContrato"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Ano Contrato" v-model="matriculaCanceladaTransferida.anoContrato"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Data Contrato" v-model="matriculaCanceladaTransferida.dataContrato"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md4>
                <v-text-field color="primary" disabled label="Curso" v-model="matriculaCanceladaTransferida.nomeCurso"></v-text-field>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Período" v-model="matriculaCanceladaTransferida.nomePeriodo"></v-text-field>
              </v-flex>

              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Transferência" :v-model="matriculaCanceladaTransferida.dataInformada" @date="matriculaCanceladaTransferida.dataInformada = $event"></date-picker-custom>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" v-if="matriculaCanceladaTransferida.dataInformada" @click.native="transferir" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import matriculaService from "@/service/matricula/MatriculaService";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import MatriculaCanceladaTransferida from "@/model/matricula/MatriculaCanceladaTransferida";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalTransfereMatricula",
  props: {
    idMatricula: Number
  },
  components: {
    DatePickerCustom
  },
  data() {
    return {
      btnTitle: "",
      dialog: false,
      formTitle: "",
      matriculaCanceladaTransferida: new MatriculaCanceladaTransferida()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    async abrirCadastro() {
      this.matriculaCanceladaTransferida = await matriculaService.editarParaTransferenciaOuCancelamento(this.idMatricula);
      this.formTitle = "Registrar Transferência";
      this.btnTitle = "Confirmar";
      this.dialog = true;
    },
    transferir() {
      matriculaService.transferir(this.matriculaCanceladaTransferida).then((dados) => {
        if(dados) {
          this.dialog = false;
          this.$emit("matriculaTransferida", true);
        }
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.matriculaCanceladaTransferida = new MatriculaCanceladaTransferida();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
