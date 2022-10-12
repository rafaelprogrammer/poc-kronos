<template>
      <v-dialog v-model="dialog" persistent width="80%">
        <v-card>
          <v-card-title>
            <span class="headline">Selecionar Avaliados</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-data-table v-model="selected" hide-actions :select-all="!avaliacaoGrupo" item-key="numeroRegistro" :loading="loading" :headers="headers" :items="avaliados" class="elevation-1">
                <template slot="items" slot-scope="props">
                    <td :active="props.selected" @click="props.selected = !props.selected">
                      <v-checkbox v-if="props.item.id ? true : (avaliacaoGrupo && props.item.idGrupoAvaliacao ? true : (avaliacaoGrupo ? false : true))"
                        :input-value="props.selected"
                        primary
                        hide-details
                      ></v-checkbox>
                    </td>
                    <td>{{ props.item.numeroRegistro }}</td>
                    <td>{{ props.item.nomeAvaliado }}</td>
                    <td>
                      <v-select v-model="props.item.idGrupoAvaliacao" v-if="avaliacaoGrupo"  item-text="numero" item-value="id" :items="gruposAvaliacoes" label="Grupo"></v-select>
                    </td>
                </template>
                <template slot="no-data">
                  Não existem informações cadastradas!
                </template>
              </v-data-table>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" dark @click.native="close">Cancel</v-btn>
            <v-btn color="primary" type="submit" v-if="this.selected && this.selected.length > 0" @click.native="salvar" dark>Selecionar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>
<script>
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import Avaliado from "@/model/avaliacao/Avaliado";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastraAvaliado",
  props: {
    idTurma: Number,
    idDisciplina: Number
  },
  data() {
    return {
      avaliacaoGrupo: false,
      dialog: false,
      gruposAvaliacoes: [],
      idAvaliacao: null,
      avaliado: new Avaliado(),
      avaliados: [],
      loading: false,
      liberaPesquisa: false,
      selected: [],
      headers: [
        {
          text: "Registro",
          align: "left",
          sortable: false,
          value: "numeroRegistro"
        },
        {
          text: "Nome",
          align: "left",
          sortable: false,
          value: "nomeAvaliado"
        },
        {
          text: "Grupo",
          align: "left",
          sortable: false,
          value: ""
        }
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
  created() {
    EventBus.$on("abrirAvaliados", (idAvaliacao, grupo, anoTurma) => {
      this.idAvaliacao = idAvaliacao;
      this.carregarDadosBasicos();
      this.liberaPesquisa = true;
      this.avaliacaoGrupo = grupo;
      this.anoTurma = anoTurma;
      this.pesquisar();
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.selected.map(a => {
        a.idAvaliacao = this.idAvaliacao;
      });
      avaliacaoService.salvarAvaliados(this.selected).then(() => {
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.gruposAvaliacoes = await avaliacaoService.listarGruposAvaliacoes(this.idAvaliacao);
    },
    async pesquisar() {
      if (this.liberaPesquisa) {
        this.loading = true;
        this.avaliados = await avaliacaoService.listarTodosAvaliados(
          this.idAvaliacao,
          this.idTurma,
          this.idDisciplina,
          this.anoTurma
        );
        if (this.avaliados && this.avaliados.length > 0) {
          this.avaliados.map(a => {
            if (a.id) {
              this.selected.push(a);
            }
          });
        }
        this.loading = false;
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.avaliado = new Avaliado();
        this.liberaPesquisa = false;
        this.gruposAvaliacoes = [];
        this.selected = [];
      }, 300);
    }
  }
};
</script>
