<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Diário" @click="abrirCadastro()" title="Incluir Diário"><v-icon>add</v-icon>Incluir Diário</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
              <v-layout wrap>
                <v-flex xs12 sm6 md12>
                  <v-select v-model="atividade.horasAtividades" disabled multiple :items="atividade.horasAtividades" attach chips label="Horas Atividades"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" label="Data Prevista" disabled v-model="atividade.dataPrevista"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" label="Dia da Semana" disabled v-model="atividade.diaDaSemana"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" label="Regime Letivo" disabled v-model="atividade.regimeLetivo"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" label="Data Realização" disabled v-model="atividade.dataRealizacao"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field color="primary" label="Bimestre" disabled v-model="atividade.bimestre"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-select v-model="diario.idTipoPrograma" :error-messages="fieldErrors('diario.idTipoPrograma')"
                    @blur="$v.diario.idTipoPrograma.$touch()" item-text="nome" item-value="id" :items="tiposProgramas" label="Tipo Programa"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Procedimento" v-model="diario.procedimento"
                  :error-messages="fieldErrors('diario.procedimento')" @blur="$v.diario.procedimento.$touch()"></v-textarea>
                </v-flex>
                <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Recurso" v-model="diario.recurso"
                  :error-messages="fieldErrors('diario.recurso')" @blur="$v.diario.recurso.$touch()"></v-textarea>
                </v-flex>
                <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Observação" v-model="diario.observacao"
                  :error-messages="fieldErrors('diario.observacao')" @blur="$v.diario.observacao.$touch()"></v-textarea>
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
import atividadeService from "@/service/atividade/AtividadeService";
import diarioService from "@/service/diario/DiarioService";
import matriculaService from "@/service/matricula/MatriculaService";
import Diario from "@/model/diario/Diario";
import Atividade from "@/model/atividade/Atividade";
import { required, maxLength } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroDiario",
  props: {
    idAtividade: Number
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    diario: Diario.validations(required, maxLength)
  },
  validationMessages: {
    diario: Diario.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposProgramas: [],
      atividade: new Atividade(),
      diario: new Diario()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaDiario", async idDiario => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Diário";
      this.btnTitle = "Alterar";
      this.diario = await diarioService.editar(idDiario);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIdAtividade();
      this.formTitle = "Cadastrar Diário";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.diario.$touch();
      if (this.$v.$invalid) {
        return;
      }
      diarioService.salvar(this.diario).then((dados) => {
        if (dados) {
          this.$emit("diarioCadastrado", true);
          this.dialog = false;
        }
      });
    },
    async carregarDadosBasicos() {
      this.tiposProgramas = await matriculaService.tiposProgramas();
      this.atividade = await atividadeService.editar(this.idAtividade);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.diario = new Diario();
      }, 300);
    },
    setaIdAtividade() {
      this.diario.idAtividade = this.idAtividade;
    }
  }
};
</script>
