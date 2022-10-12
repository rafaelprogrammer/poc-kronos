<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent fullscreen>
      <v-btn class="hidden-sm-and-down" v-if="!desabilitaIncluir" slot="activator" color="primary" dark alt="Incluir Habilidade" @click="abrirCadastro()" title="Incluir Habilidade"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field color="primary" label="Instituição" disabled v-model="instituicao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-select :disabled="somenteLeitura" v-model="habilidade.idNivel" :error-messages="fieldErrors('habilidade.idNivel')"
                  @blur="$v.habilidade.idNivel.$touch()" item-text="nome" item-value="id"
                  @change="carregarComponentesEFaixasAnos(habilidade.idNivel)"
                  :items="niveis" label="Nível"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="BNCC" @change="atualizarInstituicao(habilidade.bncc)" v-model="habilidade.bncc"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="somenteLeitura" label="Ativo" v-model="habilidade.ativo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field :disabled="somenteLeitura" v-model="habilidade.codigo" :error-messages="fieldErrors('habilidade.codigo')"
                  @blur="$v.habilidade.codigo.$touch()" label="Código"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" :disabled="somenteLeitura" label="Descrição" v-model="habilidade.descricao"
                :error-messages="fieldErrors('habilidade.descricao')" @blur="$v.habilidade.descricao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="habilidade.idComponente" :disabled="somenteLeitura" item-text="nome"
                  item-value="id" :items="componentes" label="Componente"
                  ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="habilidade.idsFaixasAnos" :disabled="somenteLeitura" item-text="nomeCombo" 
                  item-value="id" :items="faixasAnos" attach chips label="Faixas/Anos"
                  :error-messages="fieldErrors('habilidade.idsFaixasAnos')"
                  @blur="$v.habilidade.idsFaixasAnos.$touch()" multiple></v-select>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" label="Eixo" :disabled="somenteLeitura" v-model="habilidade.eixo"
                :error-messages="fieldErrors('habilidade.eixo')" @blur="$v.habilidade.eixo.$touch()"></v-textarea>
              </v-flex>

              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" label="Campo Atuação" :disabled="somenteLeitura" v-model="habilidade.campoAtuacao"
                :error-messages="fieldErrors('habilidade.campoAtuacao')" @blur="$v.habilidade.campoAtuacao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" label="Prática Linguagem" :disabled="somenteLeitura" v-model="habilidade.praticaLinguagem"
                :error-messages="fieldErrors('habilidade.praticaLinguagem')" @blur="$v.habilidade.praticaLinguagem.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" label="Objeto Conhecimento" :disabled="somenteLeitura" v-model="habilidade.objetoConhecimento"
                :error-messages="fieldErrors('habilidade.objetoConhecimento')" @blur="$v.habilidade.objetoConhecimento.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="2" cols="5" label="Unidade Temática" :disabled="somenteLeitura" v-model="habilidade.unidadeTematica"
                :error-messages="fieldErrors('habilidade.unidadeTematica')" @blur="$v.habilidade.unidadeTematica.$touch()"></v-textarea>
              </v-flex>
            </v-layout>
            </v-form>
            <v-subheader inset>Competências</v-subheader>
            <modal-seleciona-competencia :desabilita-acao="true" />
            <v-data-table :headers="headersCompetencias" :items="competencias" class="elevation-1">
              <template slot="items" slot-scope="props">
                <td width="10%">{{ props.item.nivel }}</td>
                <td>{{ props.item.bncc | booleanFilter }}</td>
                <td>{{ props.item.geral | booleanFilter }}</td>
                <td>{{ props.item.codigo }}</td>
                <td>{{ props.item.componente }}</td>
                <td width="30%">{{ props.item.descricao }}</td>
                <td>{{ props.item.ativo | booleanFilter }}</td>
                <td>
                  <v-icon title="Excluir" v-if="!desabilitaIncluir"  medium class="mr-2" @click="excluir(props.item)" >
                    delete
                  </v-icon>
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
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" v-if="!somenteLeitura" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Habilidade from "@/model/basecurricular/Habilidade";
import FaixaAno from "@/model/basecurricular/FaixaAno";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import ModalSelecionaCompetencia from "./ModalSelecionaCompetencia";
import { booleanFilter } from "@/filters/Filters.js";

export default {
  name: "ModalCadastroHabilidade",
  filters: {
    booleanFilter: booleanFilter
  },
  props: {
    desabilitaIncluir: {
      type: Boolean,
      default: false
    },
    niveis: Array
  },
  components: {
    ModalSelecionaCompetencia
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    habilidade: Habilidade.validations(required, maxLength)
  },
  validationMessages: {
    habilidade: Habilidade.validationMessages()
  },
  data() {
    return {
      somenteLeitura: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      instituicao: null,
      componentes: [],
      competencias: [],
      faixasAnos: [],
      faixaAno: new FaixaAno(),
      habilidade: new Habilidade(),
      headersCompetencias: [
        {
          text: "Nível",
          align: "left",
          sortable: false,
          value: "nivel"
        },
        {
          text: "BNCC",
          align: "left",
          sortable: false,
          value: "bncc"
        },
        {
          text: "Geral",
          align: "left",
          sortable: false,
          value: "geral"
        },
        {
          text: "Código",
          align: "left",
          sortable: false,
          value: "codigo"
        },
        {
          text: "Componente",
          align: "left",
          sortable: false,
          value: "componente"
        },
        {
          text: "Descrição",
          align: "left",
          sortable: false,
          value: "descricao"
        },
        {
          text: "Ativo",
          align: "left",
          sortable: false,
          value: "ativo"
        },
        { text: "", value: "name", sortable: false }
      ]
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("visualizaHabilidade", async (id) => {
      this.formTitle = "Visualizar Habilidade";
      this.somenteLeitura = true;
      this.abrirEditar(id);
    });
    EventBus.$on("editaHabilidade", async (id) => {
      this.formTitle = "Alterar Habilidade";
      this.btnTitle = "Alterar";
      this.abrirEditar(id);
    });
    EventBus.$on("selecionaCompetencia", async (competencia) => {
      if (this.competencias.filter(c => c.id === competencia.id).length === 0) {
        this.competencias.push(competencia);
        this.recuperarIdsCompetencias();
      }
    });
  },
  methods: {
    async abrirEditar(id) {
      this.habilidade = await baseCurricularService.editarHabilidade(id);
      this.competencias = await baseCurricularService.listarCompetenciasPorIds(this.habilidade.idsCompetencias);
      this.recuperarIdsCompetencias();
      this.carregarComponentesEFaixasAnos(this.habilidade.idNivel);
      this.atualizarInstituicao(this.habilidade.bncc);
      this.dialog = true;
    },
    recuperarIdsCompetencias() {
      this.habilidade.idsCompetencias = this.competencias.map(c => c.id);
    },
    abrirSelecionaCompetencia() {
      EventBus.$emit("abrirSelecionaCompetencia");
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Habilidade";
      this.btnTitle = "Salvar";
      this.dialog = true;
      this.habilidade.ativo = true;
      this.instituicao = this.$store.getters.user.instituicao;
    },
    excluir(competencia) {
      this.competencias.splice(
        this.competencias.indexOf(competencia),
        1
      );
      this.recuperarIdsCompetencias();
    },
    salvar() {
      this.$v.habilidade.$touch();
      if (this.$v.$invalid) {
        return;
      }
      baseCurricularService.salvarHabilidade(this.habilidade);
      this.$emit("habilidadeCadastrada", true);
      this.dialog = false;
    },
    atualizarInstituicao(bncc) {
      this.instituicao = bncc ? null : this.$store.getters.user.instituicao;
    },
    async carregarComponentesEFaixasAnos(idNivel) {
      this.faixaAno.idNivel = idNivel;
      this.faixasAnos = await baseCurricularService.faixasAnos(idNivel);
      this.componentes = await baseCurricularService.comboComponentes(idNivel);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.habilidade = new Habilidade();
        this.somenteLeitura = false;
      }, 300);
    }
  }
};
</script>
