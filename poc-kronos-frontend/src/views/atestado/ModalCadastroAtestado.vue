<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" dark color="primary" alt="Incluir Atestado" @click="abrirCadastro()" title="Incluir Atestado"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Entrega" :disabled-date="visualiza" :v-model="atestado.dataEntrega" @date="atestado.dataEntrega = $event" 
                :error-messages="fieldErrors('atestado.dataEntrega')"></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Início Vigência" :disabled-date="visualiza" :v-model="atestado.dataInicioVigencia" @date="dataInicioVigencia = $event" 
                :error-messages="fieldErrors('atestado.dataInicioVigencia')" ></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm6 md4>
                <date-picker-custom label="Data Final Vigência" :disabled-date="visualiza" :v-model="atestado.dataFinalVigencia" @date="dataFinalVigencia = $event" 
                :error-messages="fieldErrors('atestado.dataFinalVigencia')"></date-picker-custom>
              </v-flex>

              <v-flex xs12 sm2 md2>
                <v-text-field color="primary" disabled label="Nr Dias" v-model="atestado.numeroDias"></v-text-field>
              </v-flex>

              <v-flex xs12 sm6 md4>
                <v-select v-model="atestado.idTipoFalta" :disabled="visualiza" :error-messages="fieldErrors('atestado.idTipoFalta')" 
                @blur="$v.atestado.idTipoFalta.$touch()" item-text="nome" item-value="valor" :items="tiposFaltas"
                label="Tipo de Falta"></v-select>
              </v-flex>
            </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click.native="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" type="submit" :disabled="visualiza" @click.native="salvar" dark>{{btnTitle}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
import atestadoService from "@/service/atestado/AtestadoService";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import Atestado from "@/model/atestado/Atestado";
import { dataFimMenor } from "@/utils/validators";

export default {
  name: "ModalCadastroAtestado",
  props: {
    idPessoa: Number
  },
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    atestado: Atestado.validations(required, dataFimMenor)
  },
  validationMessages: {
    atestado: Atestado.validationMessages()
  },
  data() {
    return {
      visualiza: false,
      dataInicioVigencia: null,
      dataFinalVigencia: null,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposFaltas: [],
      atestado: new Atestado()
    };
  },
  watch: {
    dataInicioVigencia() {
      this.atestado.dataInicioVigencia = this.dataInicioVigencia;
      this.calcularNumeroDias();
    },
    dataFinalVigencia() {
      this.atestado.dataFinalVigencia = this.dataFinalVigencia;
      this.calcularNumeroDias();
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("visualizaAtestado", async idAtestado => {
      this.visualiza = true;
      this.editar(idAtestado);
    });
    
    EventBus.$on("editaAtestado", async idAtestado => {
      this.editar(idAtestado);
    });
  },
  methods: {
    calcularNumeroDias() {
      if (this.atestado.dataInicioVigencia && this.atestado.dataFinalVigencia) {
        const arrayDataInicioVigencia = this.atestado.dataInicioVigencia.split("/");
        const arrayDataFinalVigencia = this.atestado.dataFinalVigencia.split("/");
        let dataInicioVigencia = new Date(arrayDataInicioVigencia[2] + "-" + arrayDataInicioVigencia[1] + "-" + arrayDataInicioVigencia[0]); 
        const dataFinalVigencia = new Date(arrayDataFinalVigencia[2] + "-" + arrayDataFinalVigencia[1] + "-" + arrayDataFinalVigencia[0]);  
        let numeroDias = 0;
        while (dataInicioVigencia.getTime() <= dataFinalVigencia.getTime()) {
          dataInicioVigencia = new Date(dataInicioVigencia.getTime() + 86400000);
          numeroDias = numeroDias + 1;
        }
        this.atestado.numeroDias = numeroDias;
      } else {
        this.atestado.numeroDias = null;
      }
    },
    async editar(idAtestado) {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Atestado";
      this.btnTitle = "Alterar";
      this.atestado = await atestadoService.editar(idAtestado);
      this.dialog = true;
    },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.formTitle = "Cadastrar Atestado";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.atestado.idPessoa = this.idPessoa;
      this.$v.atestado.$touch();
      if (this.$v.$invalid) {
        return;
      }
      atestadoService.salvar(this.atestado).then((dados) => {
        if(dados) {
          this.dialog = false;
          this.$emit("atestadoCadastrado", true);
        }
      });
    },
    async carregarDadosBasicos() {
      this.tiposFaltas = await atestadoService.comboTipoFalta(this.idPessoa);
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.atestado = new Atestado();
        this.tiposFaltas = [];
        this.visualiza = false;
        EventBus.$emit("resetCustom");
      }, 300);
    }
  }
};
</script>
