<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="parcela.numero" disabled
                  label="Número"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Vencimento" :v-model="parcela.dataVencimento"
                @date="parcela.dataVencimento = $event"
                :error-messages="fieldErrors('parcela.dataVencimento')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md8>
                <v-select v-model="parcela.idTipoFormaPagamento" :error-messages="fieldErrors('parcela.idTipoFormaPagamento')"
                  @blur="$v.parcela.idTipoFormaPagamento.$touch()" item-text="nome" item-value="id" :items="tiposFormasPagamento" label="Forma Pagamento"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="parcela.valorOriginal"
                  label="Valor Original"  @blur="$v.parcela.valorOriginal.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="parcela.valorJuros"
                  label="Valor Juros"  @blur="$v.parcela.valorJuros.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="parcela.valorMulta"
                  label="Valor Multa"  @blur="$v.parcela.valorMulta.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="parcela.valorDesconto"
                  label="Valor Desconto"  @blur="$v.parcela.valorDesconto.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="parcela.valorPagamento" 
                  label="Valor Pagamento"  @blur="$v.parcela.valorPagamento.$touch()"></v-text-field>
              </v-flex> 
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Pagamento" :v-model="parcela.dataPagamento"
                @date="$event ? parcela.dataPagamento = $event : null"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-textarea rows="5" cols="5" label="Observações" v-model="parcela.observacao"></v-textarea>
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
import parcelaService from "@/service/parcela/ParcelaService";
import contratoService from "@/service/contrato/ContratoService";
import Parcela from "@/model/parcela/Parcela";
import { required, numeric, decimal } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
export default {
  name: "ModalEditaParcela",
  components: {
    DatePickerCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    parcela: Parcela.validations(required, numeric, decimal)
  },
  validationMessages: {
    parcela: Parcela.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposFormasPagamento: [],
      parcela: new Parcela()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaParcela", async idParcela => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Parcela";
      this.btnTitle = "Alterar";
      this.parcela = await parcelaService.editar(idParcela);
      this.dialog = true;
    });
  },
  methods: {
    salvar() {
      this.$v.parcela.$touch();
      if (this.$v.$invalid) {
        return;
      }
      parcelaService.alterar(this.parcela).then(() => {
        this.$emit("parcelaCadastrada", true);
        this.dialog = false;
      });
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.parcela = new Parcela();
        EventBus.$emit("resetCustom");
      }, 300);
    },
    async carregarDadosBasicos() {
      this.tiposFormasPagamento = await contratoService.tiposFormasPagamento();
    }
  }
};
</script>
