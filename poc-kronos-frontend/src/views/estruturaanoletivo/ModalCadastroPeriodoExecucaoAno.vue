<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md4>
                <v-text-field color="primary" label="Curso" disabled v-model="nomeCurso"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field color="primary" label="Ano" disabled v-model="periodoExecucao.ano"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Período" disabled v-model="periodoExecucao.siglaPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Nr Período" disabled v-model="periodoExecucao.numeroPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field color="primary" label="Tipo Período" disabled v-model="periodoExecucao.tipoPeriodo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-select v-model="periodoExecucao.idCalendario" :error-messages="fieldErrors('periodoExecucao.idCalendario')"
                  @blur="$v.periodoExecucao.idCalendario.$touch()" item-text="descricao" item-value="id" :items="calendarios" label="Calendário"></v-select>
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
import calendarioService from "@/service/calendario/CalendarioService";
import periodoExecucaoService from "@/service/periodoexecucao/PeriodoExecucaoService";
import PeriodoExecucaoEstruturaAnoLetivo from "@/model/periodoexecucao/PeriodoExecucaoEstruturaAnoLetivo";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";

export default {
  name: "ModalCadastroPeriodoExecucaoAno",
  mixins: [validationMixin, messageMixin],
  validations: {
    periodoExecucao: PeriodoExecucaoEstruturaAnoLetivo.validations(required)
  },
  validationMessages: {
    periodoExecucao: PeriodoExecucaoEstruturaAnoLetivo.validationMessages()
  },
  data() {
    return {
      idCurso: null,
      nomeCurso: "",
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      calendarios: [],
      periodoExecucao: new PeriodoExecucaoEstruturaAnoLetivo()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  created() {
    EventBus.$on("editaPeriodoExecucaoEstruturaAnoLetivo", async (periodoExecucao, idCurso, nomeCurso) => {
      this.formTitle = "Alterar Período Execução (Ano)";
      this.btnTitle = "Alterar";
      this.idCurso = idCurso;
      this.nomeCurso = nomeCurso;
      this.periodoExecucao = periodoExecucao;
      this.carregarDadosBasicos();
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.$v.periodoExecucao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      periodoExecucaoService.salvar(this.periodoExecucao).then((dados) => {
        if (dados) {
          this.$emit("periodoExecucaoEstruturaAnoLetivoCadastrado", true);
          this.dialog = false;
        }
      });
    },
    async carregarDadosBasicos() {
      this.calendarios = await calendarioService.combo(
        this.idCurso,
        this.periodoExecucao.ano
      );
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.periodoExecucao = new PeriodoExecucaoEstruturaAnoLetivo();
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
