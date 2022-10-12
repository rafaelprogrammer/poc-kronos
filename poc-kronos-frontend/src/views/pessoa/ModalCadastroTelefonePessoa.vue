<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="50%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Endereço" @click="abrirCadastro()" title="Incluir Pessoa"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <on-complete-custom id="estadosTelefone" label="Estado" :v-model="estado" item-text="sigla" v-bind:dados="estados" @select="idEstado = ($event ? $event.id : null)"
                :error-messages="fieldErrors('idEstado')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md2 v-show="idEstado">
                <on-complete-custom id="cidadesTelefone" label="Cidade" :v-model="cidade" item-text="nome" v-bind:dados="cidades" @select="selectCidade($event)"
                :error-messages="fieldErrors('telefonePessoa.idCidade')"></on-complete-custom>
              </v-flex>
              <v-flex xs12 sm6 md2>
               <v-text-field color="primary" disabled label="DDD" v-model="codAreaTel"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="telefonePessoa.idTipoTelefone" :error-messages="fieldErrors('telefonePessoa.idTipoTelefone')"
                  @blur="$v.telefonePessoa.idTipoTelefone.$touch()" item-text="nome" item-value="id" :items="tiposTelefones" label="Tipo"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="telefonePessoa.idTipoUso" :error-messages="fieldErrors('telefonePessoa.idTipoUso')"
                  @blur="$v.telefonePessoa.idTipoUso.$touch()" item-text="nome" item-value="id" :items="tiposUsos" label="Tipo Uso"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select v-model="telefonePessoa.idOperadora" :error-messages="fieldErrors('telefonePessoa.idOperadora')"
                  @blur="$v.telefonePessoa.idOperadora.$touch()" item-text="nome" item-value="id" :items="operadoras" label="Operadora"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="telefonePessoa.numero" :error-messages="fieldErrors('telefonePessoa.numero')"
                  @blur="$v.telefonePessoa.numero.$touch()" label="Número"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-spacer></v-spacer>
                <v-checkbox type="checkbox" label="Principal" v-model="telefonePessoa.principal"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-spacer></v-spacer>
                <v-checkbox type="checkbox" label="What-App" v-model="telefonePessoa.whatApp"></v-checkbox>
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
import apoioService from "@/service/ApoioService";
import telefonePessoaService from "@/service/pessoa/TelefonePessoaService";
import TelefonePessoa from "@/model/pessoa/TelefonePessoa";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import OnCompleteCustom from "@/components/Custom/OnCompleteCustom";
export default {
  name: "ModalCadastroTelefonePessoa",
  props: {
    idPessoa: Number
  },
  components: {
    OnCompleteCustom
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    telefonePessoa: TelefonePessoa.validations(required, maxLength, numeric)
  },
  validationMessages: {
    telefonePessoa: TelefonePessoa.validationMessages()
  },
  data() {
    return {
      estados: [],
      estado: null,
      cidade: null,
      cidades: [],
      codAreaTel: "",
      idEstado: "",
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      tiposTelefones: [],
      operadoras: [],
      tiposUsos: [],
      telefonePessoa: new TelefonePessoa()
    };
  },
  watch: {
    idPessoa() {
      this.telefonePessoa.idPessoa = this.idPessoa;
    },
    async idEstado(idEstado) {
      this.cidades = await apoioService.cidades(null, idEstado);
      this.idEstado = idEstado;
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    this.carregarDadosBasicos();
  },
  methods: {
    selectCidade(cidade) {
      if (cidade) {
        this.telefonePessoa.idCidade = cidade.id;
        this.codAreaTel = cidade.codAreaTel;
      }
    },
    async abrirCadastro() {
      this.formTitle = "Cadastrar Telefone";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.telefonePessoa.$touch();
      if (this.$v.$invalid) {
        return;
      }
      telefonePessoaService.salvar(this.telefonePessoa);
      this.$emit("telefonePessoaCadastrado", true);
      this.dialog = false;
    },
    async carregarDadosBasicos() {
      this.estados = await apoioService.estados();
      this.tiposTelefones = await telefonePessoaService.tipos();
      this.operadoras = await telefonePessoaService.operadoras();
      this.tiposUsos = await telefonePessoaService.tiposUsos();
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.telefonePessoa = new TelefonePessoa();
        this.telefonePessoa.idPessoa = this.idPessoa;
      }, 300);
    }
  }
};
</script>
