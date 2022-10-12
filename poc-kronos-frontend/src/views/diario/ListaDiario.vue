<template>
<div>
  <v-layout wrap>
    <v-flex xs12 sm2 md3>
      <modal-cadastro-diario :id-atividade="idAtividade"  @diarioCadastrado="cadastroEfetuado = $event" />
    </v-flex>
    <v-flex xs12 sm2 md1>
      <v-btn icon @click="atualizar" small>
        <v-icon color="primary">refresh</v-icon>
      </v-btn>
    </v-flex>
    <v-flex xs12 sm2 md12>
      <v-data-table :loading="loading" :items="diarios" class="elevation-1" :total-items="pagination.totalItems" :pagination.sync="pagination">
        <template slot="items" slot-scope="props">
          <td>
            <v-layout wrap>
                <v-flex xs12 sm6 md11>
                  <v-text-field color="primary" label="Tipo Programa" disabled v-model="props.item.nomePrograma"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md1>
                  <v-menu offset-y>
                    <v-btn slot="activator" fab small title="Opções para diário" color="primary" dark><v-icon>more_vert</v-icon></v-btn>
                    <v-list>
                      <v-list-tile alt="Editar" @click="editar(props.item)">
                        <v-list-tile-title>Editar</v-list-tile-title>
                      </v-list-tile>
                      <v-divider></v-divider>
                      <v-list-tile alt="Excluir" @click="excluir(props.item)">
                        <v-list-tile-title>Excluir</v-list-tile-title>
                      </v-list-tile>
                    </v-list>
                  </v-menu>
                </v-flex>
                <v-flex xs12 sm6 md12>
                  <v-tooltip bottom>
                    <span slot="activator" class=""><v-textarea rows="2" cols="5" label="Observação" disabled :value="props.item.observacao | limitCaracteres(100)"></v-textarea></span>
                    <span>{{ props.item.observacao }}</span>
                  </v-tooltip>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-tooltip bottom>
                    <span slot="activator" class=""><v-textarea rows="2" cols="5" label="Procedimento" disabled :value="props.item.procedimento | limitCaracteres(100)"></v-textarea></span>
                    <span>{{ props.item.procedimento }}</span>
                  </v-tooltip>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-tooltip bottom>
                    <span slot="activator" class=""><v-textarea rows="2" cols="5" label="Recurso" disabled :value="props.item.recurso | limitCaracteres(100)"></v-textarea></span>
                    <span>{{ props.item.recurso }}</span>
                  </v-tooltip>
                </v-flex>
              </v-layout>
          </td>
        </template>
        <template slot="no-data">
          Não existem informações cadastradas!
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</div>
</template>
<script>
import diarioService from "@/service/diario/DiarioService";
import Diario from "@/model/diario/Diario";
import ModalCadastroDiario from "./ModalCadastroDiario";
import Pagination from "@/utils/Pagination.js";
import EventBus from "@/utils/EventBus";
import { limitCaracteres } from "@/filters/Filters.js";

export default {
  name: "ListaAtividadeDisciplinaDireito",
  props: {
    idAtividade: Number
  },
  components: {
    ModalCadastroDiario
  },
  filters: {
    limitCaracteres: limitCaracteres
  },
  data() {
    return {
      niveis: [],
      dialog: false,
      diarios: [],
      pagination: new Pagination(1, 5),
      loading: true,
      cadastroEfetuado: false,
      diario: new Diario()
    };
  },
  watch: {
    idAtividade() {
      this.atualizar();
    },
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.pesquisar();
          this.cadastroEfetuado = false;
        }, 500);
      }
    },
    pagination: {
      handler() {
        this.pesquisar();
      }
    }
  },
  methods: {
    editar(diario) {
      EventBus.$emit("editaDiario", diario.id);
    },
    excluir(diario) {
      confirm("Você deseja realmente excluir Diário?") &&
        diarioService.excluir(diario).then(() => {
          this.diarios.splice(this.diarios.indexOf(diario),1);
          this.atualizar();
        });
    },
    atualizar() {
      this.pesquisar();
    },
    async pesquisar() {
      if (this.idAtividade) {
        this.diario.idAtividade = this.idAtividade;
        const paginacaoResultado = await diarioService.listarTodos(
          this.diario,
          this.pagination.page,
          this.pagination.rowsPerPage
        );
        this.diarios = paginacaoResultado.dados;
        this.pagination.totalItems = paginacaoResultado.total;
        this.loading = false;
      }
    }
  }
};
</script>
