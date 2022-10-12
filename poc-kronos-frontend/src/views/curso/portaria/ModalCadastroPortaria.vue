<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Portaria" @click="abrirCadastro()" title="Incluir Portaria"><v-icon>add</v-icon>Incluir</v-btn>
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
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="portaria.descricao" :error-messages="fieldErrors('portaria.descricao')"
                  label="Nome"  @blur="$v.portaria.descricao.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Publicação" :v-model="portaria.dataPublicacao" @date="portaria.dataPublicacao = $event" 
                :error-messages="fieldErrors('portaria.dataPublicacao')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Inicio Vigência" :v-model="portaria.dataInicioVigencia" @date="portaria.dataInicioVigencia = $event" 
                :error-messages="fieldErrors('portaria.dataInicioVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <date-picker-custom label="Data Final Vigência" :v-model="portaria.dataFinalVigencia" @date="portaria.dataFinalVigencia = $event" 
                :error-messages="fieldErrors('portaria.dataFinalVigencia')"></date-picker-custom>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="portaria.idTipoPortaria" :error-messages="fieldErrors('portaria.idTipoPortaria')"
                  @blur="$v.portaria.idTipoPortaria.$touch()" item-text="nome" item-value="id" :items="tiposPortarias" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md12>
                <v-text-field v-model="portaria.documentoPublicacao" :error-messages="fieldErrors('portaria.documentoPublicacao')"
                  label="Documento da Publicação"  @blur="$v.portaria.documentoPublicacao.$touch()"></v-text-field>
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
import portariaService from "@/service/curso/PortariaService";
import Portaria from "@/model/curso/portaria/Portaria";
import DatePickerCustom from "@/components/Custom/DatePickerCustom";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import { dataFimMenor } from "@/utils/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
export default {
  name: "ModalCadastroPortaria",
  components: {
    DatePickerCustom
  },
  props: {
    idCurso: Number,
    nomeCurso: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    portaria: Portaria.validations(required, maxLength, numeric, dataFimMenor)
  },
  validationMessages: {
    portaria: Portaria.validationMessages()
  },
  data() {
    return {
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposPortarias: [],
      portaria: new Portaria()
    };
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    EventBus.$on("editaPortaria", async idPortaria => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Portaria";
      this.btnTitle = "Alterar";
      this.portaria = await portariaService.editar(idPortaria);
      this.dialog = true;
    });
  },
  methods: {
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.setaIdCurso();
      this.formTitle = "Cadastrar Portaria";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.portaria.$touch();
      if (this.$v.$invalid) {
        return;
      }
      portariaService.salvar(this.portaria).then(() => {
        this.$emit("portariaCadastrada", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.tiposPortarias = await portariaService.tipos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.portaria = new Portaria();
        EventBus.$emit("resetCustom");
      }, 300);
    },
    setaIdCurso() {
      this.portaria.idCurso = this.idCurso;
    }
  }
};
</script>
