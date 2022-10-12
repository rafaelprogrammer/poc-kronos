<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Vincular Escala" @click="abrirCadastro()" title="Vincular Escala"><v-icon>add</v-icon>Vincular</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Escala</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-form @submit.prevent="pesquisar()" ref="form">
                  <v-text-field color="primary" disabled v-model="this.$store.getters.user.instituicao"></v-text-field>
                  <v-layout align-center row fill-height justify-space-between>  
                      
                      <v-select v-model="escala.idTipoAbrangencia"
                        item-text="nome" item-value="id" :items="tiposAbrangencias" label="Tipo Abrangências"></v-select>
                      <v-select v-model="escala.idTipoAvaliacao"
                        item-text="nome" item-value="id" :items="tiposAvaliacoes" label="Tipo Avaliações"></v-select>
                      <v-btn color="primary" alt="Limpar" title="Limpar" @click="limpar()"> <v-icon>clear</v-icon></v-btn>
                      <v-btn color="primary" alt="Pesquisar" title="Pesquisar" type="submit"> <v-icon>find_in_page</v-icon></v-btn>
                </v-layout>
              </v-form>
              <v-data-table :loading="loading" :headers="headers" :items="escalas" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
                  <template slot="items" slot-scope="props">
                    <td>{{ props.item.nome }}</td>
                    <td>{{ props.item.nomeTipoAbrangencia }}</td>
                    <td>{{ props.item.nomeTipoAvaliacao }}</td>
                    <td>{{ props.item.dataInicialVigencia }}</td>
                    <td>{{ props.item.dataFinalVigencia }}</td>
                    <td>
                      <v-icon title="Selecionar" medium class="mr-2" @click="selecionar(props.item)" >
                          check_circle
                      </v-icon>
                    </td>
                  </template>
                <template slot="no-data">
                    Empresa não localizada!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import cursoService from "@/service/curso/CursoService";
import escalaService from "@/service/instituicao/EscalaService";
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import Escala from "@/model/instituicao/Escala";
import CursoEscala from "@/model/curso/CursoEscala";
import Pagination from "@/utils/Pagination.js";
export default {
  name: "ModalSelecionaEscala",
  props: {
    idCurso: Number
  },
  data() {
    return {
      dialog: false,
      tiposAbrangencias: [],
      tiposAvaliacoes: [],
      escala: new Escala(),
      cursoEscala: new CursoEscala(),
      escalas: [],
      pagination: new Pagination(1, 5),
      loading: true,
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nome"
        },
        {
          text: "Tipo Abrangência",
          align: "left",
          sortable: false,
          value: "nomeTipoAbrangencia"
        },
        {
          text: "Tipo Avaliação",
          align: "left",
          sortable: false,
          value: "nomeTipoAvaliacao"
        },
        {
          text: "Data Inicial Vigência",
          align: "left",
          sortable: false,
          value: "dataInicialVigencia"
        },
        {
          text: "Data Final Vigência",
          align: "left",
          sortable: false,
          value: "dataFinalVigencia"
        },
        { text: "Ações", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  methods: {
    async abrirCadastro() {
      this.dialog = true;
      this.carregarDadosBasicos();
      this.pesquisar();
    },
    async selecionar(escala) {
      this.cursoEscala.idCurso = this.idCurso;
      this.cursoEscala.idEscala = escala.id;
      const naoExisteVinculo = await cursoService.naoExisteVinculoCursoEscala(
        this.cursoEscala
      );
      if (naoExisteVinculo) {
        cursoService.vincularCursoEscala(this.cursoEscala).then(() => {
          this.dialog = false;
          this.$emit("escalaVinculada", true);
        });
      }
    },
    limpar() {
      this.escala = new Escala();
      this.pesquisar();
    },
    async pesquisar() {
      this.loading = true;
      const paginacaoResultado = await escalaService.listarTodos(
        this.escala,
        this.pagination.page,
        this.pagination.rowsPerPage
      );
      this.escalas = paginacaoResultado.dados;
      this.pagination.totalItems = paginacaoResultado.total;
      this.loading = false;
    },
    async carregarDadosBasicos() {
      this.tiposAvaliacoes = await avaliacaoService.tipos();
      this.tiposAbrangencias = await avaliacaoService.tiposAbrangencias();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.escala = new Escala();
      }, 300);
    }
  }
};
</script>
