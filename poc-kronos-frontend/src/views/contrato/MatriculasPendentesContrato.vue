<template>
            <v-container grid-list-md>
              <v-subheader>Matrículas Pendentes</v-subheader>
              <v-data-table id="matriculasPendentesContrato" :loading="loading" :headers="headers" :items="matriculasPendentesContrato" class="elevation-1">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.numeroRegistroPessoa }}</td>
                    <td>{{ props.item.cpfPessoa }}</td>
                    <td>{{ props.item.nomePessoa }}</td>
                    <td>
                      <v-btn color="primary" dark @click="selecionar(props.item)">Selecionar</v-btn>
                    </td>
                  </template>
                <template slot="no-data">
                    Matrícula não localizada!
                </template>
              </v-data-table>
            </v-container>
</template>
<script>
import matriculaService from "@/service/matricula/MatriculaService";
import EventBus from "@/utils/EventBus";
import Matricula from "@/model/matricula/Matricula";
export default {
  name: "MatriculasPendentesContrato",
  data() {
    return {
      matriculasPendentesContrato: [],
      loading: true,
      headers: [
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistroPessoa"
        },
        {
          text: "CPF",
          align: "left",
          sortable: false,
          value: "cpfPessoa"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomePessoa"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  created() {
    this.pesquisar();
  },
  methods: {
    selecionar(matricula) {
      this.dialog = false;
      EventBus.$emit("selecionaMatricula", matricula);
    },
    async pesquisar() {
        this.loading = true;
        this.matriculasPendentesContrato = await matriculaService.listarPendentesContrato(this.$store.getters.user.idInstituicao);
        this.loading = false;
    }
  }
};
</script>
