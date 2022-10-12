<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Período" @click="abrirCadastro()" title="Incluir Período"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="nomeCurso" disabled
                  label="Nome do Curso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="periodo.idTipoPeriodo" :error-messages="fieldErrors('periodo.idTipoPeriodo')"
                  @blur="$v.periodo.idTipoPeriodo.$touch()" item-text="nome" item-value="id" :items="tiposPeriodos" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="periodo.idFaixaAno" :error-messages="fieldErrors('periodo.idFaixaAno')"
                  @blur="$v.periodo.idFaixaAno.$touch()" item-text="nomeCombo" item-value="id" :items="faixasAnos" label="FaixaAno"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="periodo.nome" :error-messages="fieldErrors('periodo.nome')"
                  label="Nome"  @blur="$v.periodo.nome.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="periodo.sigla" :error-messages="fieldErrors('periodo.sigla')"
                  label="Sigla"  @blur="$v.periodo.sigla.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="periodo.numero" :error-messages="fieldErrors('periodo.numero')"
                  label="Número"  @blur="$v.periodo.numero.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="periodo.valor" :error-messages="fieldErrors('periodo.valor')"
                  label="Valor"  @blur="$v.periodo.valor.$touch()"></v-text-field>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import periodoService from "@/service/curso/PeriodoService";
import baseCurricularService from "@/service/basecurricular/BaseCurricularService";
import Periodo from "@/model/curso/periodo/Periodo";
import FaixaAno from "@/model/basecurricular/FaixaAno";
import { required, maxLength, numeric, decimal } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroPeriodo",
  props: {
    idCurso: Number,
    idNivel: Number,
    nomeCurso: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    periodo: Periodo.validations(required, maxLength, numeric, decimal)
  },
  validationMessages: {
    periodo: Periodo.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposPeriodos: [],
      faixasAnos: [],
      periodo: new Periodo(),
      faixaAno: new FaixaAno()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaPeriodo", async idPeriodo => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Período";
      this.btnTitle = "Alterar";
      this.periodo = await periodoService.editar(idPeriodo);
      this.faixaAno.idNivel = this.idNivel;
      this.carregarFaixasAnos();
      this.faixasAnos.map(f => {
        if (f.id === this.periodo.idFaixaAno) {
          this.periodo.idNivel = f.idNivel;
        }
      });
      this.dialog = true;
    });
  },
  methods: {
    async carregarFaixasAnos() {
      this.faixaAno.idNivel = this.idNivel;
      this.faixasAnos = await baseCurricularService.faixasAnos(this.faixaAno);
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIdCurso();
      this.formTitle = "Cadastrar Período";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.periodo.$touch();
      if (this.$v.$invalid) {
        return;
      }
      periodoService.salvar(this.periodo).then(() => {
        this.$emit("periodoCadastrado", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.carregarFaixasAnos();
      this.tiposPeriodos = await periodoService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.faixaAno = new FaixaAno();
        this.periodo = new Periodo();
      }, 300);
    },
    setaIdCurso() {
      this.periodo.idCurso = this.idCurso;
    }
  }
};
</script>
