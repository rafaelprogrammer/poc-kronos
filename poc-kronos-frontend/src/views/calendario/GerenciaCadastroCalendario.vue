<template>
  <div>
    <section-definition title="Calendários"></section-definition>
    <v-toolbar dark color="primary" class="text-xs-center">
      <v-toolbar-title>Gerenciar Cadastro de Calendários</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-btn color="primary" alt="Editar Calendário" v-show="verificarBotaoEditar()" @click="editar" title="Editar Calendário"><v-icon>edit</v-icon>Editar</v-btn>
        <v-btn color="primary" alt="Excluir Calendário" v-show="calendario && calendario.id" @click="excluir" title="Excluir Calendário"><v-icon>delete</v-icon>Excluir</v-btn>
        <modal-seleciona-calendario></modal-seleciona-calendario>
        <v-btn color="primary" alt="Concluir Calendário" v-show="verificarBotaoConcluir()" @click="concluir" title="Concluir Calendário"><v-icon>check</v-icon>Concluir</v-btn>
        <v-btn color="primary" alt="Aprovar Calendário" v-show="verificarBotaoAprovar()" @click="aprovar" title="Aprovar Calendário"><v-icon>check</v-icon>Aprovar</v-btn>
        <v-btn color="primary" alt="Liberar Edição Calendário" v-show="verificarBotaoLiberarEdicao()" @click="liberarEdicao" title="Liberar Edição Calendário"><v-icon>check</v-icon>Liberação Edição</v-btn>
        
        <v-spacer></v-spacer>
        <modal-cadastro-calendario />
    </v-toolbar>
            <v-container fluid>
                  <v-layout row>
                      <v-layout wrap justify-space-between>
                        <v-flex xs12 sm6 md4>
                          <v-text-field color="primary" disabled label="Instituição" v-model="this.$store.getters.user.instituicao"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Ano" v-model="calendario.ano"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Número" v-model="calendario.numero"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Data Início" v-model="calendario.dataInicio"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Início Ano Letivo" v-model="calendario.dataInicioAnoLetivo"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Data Início Atribuição Crédito" v-model="calendario.dataInicioAtribuicaoCredito"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Data Final Atribuição Crédito" v-model="calendario.dataFinalAtribuicaoCredito"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Data Final Ano Letivo" v-model="calendario.dataFinalAnoLetivo"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Final" v-model="calendario.dataFinal"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md3>
                          <v-text-field color="primary" disabled label="Responsável Aprovação" v-model="calendario.nomeFuncionarioAprovacao"></v-text-field>
                        </v-flex>
                         <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Conclusão" v-model="calendario.dataConclusao"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Data Aprovação" v-model="calendario.dataAprovacao"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md2>
                          <v-text-field color="primary" disabled label="Situação" v-model="calendario.nomeTipoSituacaoCalendario"></v-text-field>
                        </v-flex>
                      </v-layout>
                </v-layout>
                <v-layout wrap justify-space-between>
                      <v-flex xs12 sm6 md12>
                          <v-textarea disabled rows="3" cols="5" label="Descrição" v-model="calendario.descricao"></v-textarea>
                      </v-flex>
                      <v-flex xs12 sm6 md12>
                        <v-select disabled v-model="calendario.idsCursos" item-text="nome" item-value="id" :items="cursos" attach chips label="Cursos" 
                        multiple></v-select>
                      </v-flex>
                      <v-flex xs12 sm6 md12 v-if="abrirCalendario">
                        <modal-cadastro-evento :id-calendario="calendario.id"
                          @eventoCadastrado="cadastroEfetuado = $event"
                          :data-inicio-calendario="calendario.dataInicio"
                          :data-final-calendario="calendario.dataFinal"/>
                      </v-flex>
                      <v-flex xs12 sm6 md12 v-if="abrirCalendario">
                        <v-subheader>Legenda Categorias</v-subheader>
                        <v-layout wrap justify-space-between>
                          <template>
                            <v-flex v-for="categoriaEvento in categoriasEventos" xs12 sm2 md2 :key="categoriaEvento.id">
                              <v-chip :color="categoriaEvento.cor" text-color="white">{{categoriaEvento.nome}}</v-chip>
                            </v-flex>
                          </template>
                        </v-layout>
                      </v-flex>
                      <v-flex xs12 sm12 md12 v-if="abrirCalendario">
                          <iframe :src="urlCalendario" width="100%" height="800"></iframe>
                      </v-flex>
                </v-layout>
            </v-container>
     </div>
</template>
<script>
import eventoService from "@/service/calendario/EventoService";
import calendarioService from "@/service/calendario/CalendarioService";
import cursoService from "@/service/curso/CursoService";
import EventBus from "@/utils/EventBus";
// import ModalCadastroMatricula from "./ModalCadastroMatricula";
import ModalCadastroCalendario from "./ModalCadastroCalendario";
import ModalCadastroEvento from "./ModalCadastroEvento";
import ModalSelecionaCalendario from "./ModalSelecionaCalendario";
import Calendario from "@/model/calendario/Calendario";
import Evento from "@/model/calendario/Evento";
export default {
  name: "GerenciaCadastroCalendario",
  components: {
    ModalSelecionaCalendario,
    ModalCadastroCalendario,
    ModalCadastroEvento
  },
  data() {
    return {
      abrirCalendario: false,
      cadastroEfetuado: false,
      urlCalendario: null,
      cadastroEfetuado: false,
      tiposSituacoesCalendario: [],
      categoriasEventos: [],
      cursos: [],
      calendario: new Calendario()
    };
  },
  watch: {
    cadastroEfetuado(event) {
      if (event) {
        setTimeout(() => {
          this.atualizarComponenteCalendario(this.calendario);
          this.cadastroEfetuado = false;
        }, 500);
      }
    },
  },
  async created() {
    this.carregarDadosBasicos();
    EventBus.$on("selecionaCalendario", async calendario => {
      this.carregarDadosBasicos();
      this.atualizarComponenteCalendario(calendario);
      this.atualizar(calendario.id);
    });
  },
  methods: {
    atualizarComponenteCalendario(calendario) {
      this.abrirCalendario = false;
      this.urlCalendario = Evento.URL_CALENDARIO() + calendario.id;
      setTimeout(() => {
         this.abrirCalendario = true;
      }, 500);
    },
    excluir() {
      confirm("Você deseja realmente excluir o Calendário?") &&
        calendarioService.excluir(this.calendario).then(() => {
          this.calendario = new Calendario();
          this.abrirCalendario = false;
        });
    },
    concluir() {
      confirm("Você deseja realmente concluir o Calendário?") &&
        calendarioService.concluir(this.calendario).then(() => {
          this.atualizar(this.calendario.id);
        });
    },
    aprovar() {
      confirm("Você deseja realmente aprovar o Calendário?") &&
        calendarioService.aprovar(this.calendario).then(() => {
          this.atualizar(this.calendario.id);
        });
    },
    liberarEdicao() {
      confirm("Você deseja realmente liberar para edição o Calendário?") &&
        calendarioService.liberarEdicao(this.calendario).then(() => {
          this.atualizar(this.calendario.id);
        });
    },
    async atualizar(id) {
      this.calendario = await calendarioService.editar(id);
    },
    verificarBotaoEditar() {
      return (this.calendario && this.calendario.id) && Calendario.EM_ELABORACAO() === this.calendario.idTipoSituacaoCalendario;
    },
    verificarBotaoConcluir() {
      return (this.calendario && this.calendario.id) && Calendario.EM_ELABORACAO() === this.calendario.idTipoSituacaoCalendario;
    },
    verificarBotaoAprovar() {
      return (this.calendario && this.calendario.id) && Calendario.CONCLUIDO() === this.calendario.idTipoSituacaoCalendario;
    },
    verificarBotaoLiberarEdicao() {
      return (this.calendario && this.calendario.id) && Calendario.APROVADO() === this.calendario.idTipoSituacaoCalendario;
    },
    editar() {
      EventBus.$emit("editaCalendario", this.calendario.id);
    },
    async carregarDadosBasicos() {
      this.categoriasEventos = await eventoService.categorias();
      this.tiposSituacoesCalendario = await calendarioService.tiposSituacoes();
      this.cursos = await cursoService.combo();
    }
  }
};
</script>
