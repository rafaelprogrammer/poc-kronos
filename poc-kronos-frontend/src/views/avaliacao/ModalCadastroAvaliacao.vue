<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" fullscreen hide-overlay persistent width="80%">
      <v-btn class="hidden-sm-and-down" slot="activator" color="primary" dark alt="Incluir Avaliação" @click="abrirCadastro()" title="Incluir Avaliação"><v-icon>add</v-icon>Incluir</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{formTitle}}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-form ref="form">
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-select v-model="idAtividade" :error-messages="fieldErrors('avaliacao.idAtividade')"
                  @blur="$v.avaliacao.idAtividade.$touch()" item-text="dataPrevista" item-value="id" :items="datasAtividades" label="Data Atividade"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="siglaTurma" label="Turma" ></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field disabled v-model="siglaDisciplina" label="Disciplina" ></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="avaliacao.horaInicio" :error-messages="fieldErrors('avaliacao.horaInicio')"
                  @blur="$v.avaliacao.horaInicio.$touch()" :items="horasInicios" label="Hora Início"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" :disabled="existeGrupos" label="Grupo" v-model="avaliacao.grupo"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md1>
                <v-text-field v-model="avaliacao.peso" :error-messages="fieldErrors('avaliacao.peso')"
                  label="Peso"  @blur="$v.avaliacao.peso.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field v-model="avaliacao.numeroMaxParticipante" :error-messages="fieldErrors('avaliacao.numeroMaxParticipante')"
                  label="Nr Máximo Participantes"  @blur="$v.avaliacao.numeroMaxParticipante.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field v-model="avaliacao.tempoDuracao" :error-messages="fieldErrors('avaliacao.tempoDuracao')"
                  label="Tempo Duração"  @blur="$v.avaliacao.tempoDuracao.$touch()"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="avaliacao.idTipoRegistroNota" :error-messages="fieldErrors('avaliacao.idTipoRegistroNota')"
                  @blur="$v.avaliacao.idTipoRegistroNota.$touch()" item-text="nome" item-value="id" :items="tiposRegistrosNotas" label="Registro Nota"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="avaliacao.idTipoFormato" :error-messages="fieldErrors('avaliacao.idTipoFormato')"
                  @blur="$v.avaliacao.idTipoFormato.$touch()" item-text="nome" item-value="id" :items="tiposFormatos" label="Formato"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select v-model="avaliacao.idTipoAbrangencia" :error-messages="fieldErrors('avaliacao.idTipoAbrangencia')"
                  @blur="$v.avaliacao.idTipoAbrangencia.$touch()" item-text="nome" item-value="id" :items="tiposAbrangencias" label="Abrangência"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select v-model="avaliacao.idTipoAvaliacao" :error-messages="fieldErrors('avaliacao.idTipoAvaliacao')"
                  @blur="$v.avaliacao.idTipoAvaliacao.$touch()" item-text="nome" item-value="id" :items="tiposAvaliacoes" label="Avaliação"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md12>
                  <v-textarea rows="2" cols="5" label="Observação" v-model="avaliacao.observacao"
                  :error-messages="fieldErrors('avaliacao.observacao')" @blur="$v.avaliacao.observacao.$touch()"></v-textarea>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-checkbox color="primary" label="Anulada" v-model="avaliacao.anulada"></v-checkbox>
              </v-flex>
              <v-flex xs12 sm6 md10>
                  <v-textarea rows="2" cols="5" label="Motivo Anulação" v-model="avaliacao.motivoAnulacao"
                  :error-messages="fieldErrors('avaliacao.motivoAnulacao')" @blur="$v.avaliacao.motivoAnulacao.$touch()"></v-textarea>
              </v-flex>

              <v-flex xs12 sm6 md12>
                <lista-grupo-avaliacao :avaliacao="avaliacao" />
              </v-flex>
              <v-flex xs12 sm6 md12>
                <lista-avaliacao-habilidade :avaliacao="avaliacao" :id-disciplina="idDisciplina" />
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
import avaliacaoService from "@/service/avaliacao/AvaliacaoService";
import atividadeService from "@/service/atividade/AtividadeService";
import Avaliacao from "@/model/avaliacao/Avaliacao";
import { required, maxLength, numeric } from "vuelidate/lib/validators";
import validationMixin from "@/mixins/validationMixin";
import messageMixin from "@/mixins/message";
import EventBus from "@/utils/EventBus";
import ListaGrupoAvaliacao from "./ListaGrupoAvaliacao";
import ListaAvaliacaoHabilidade from "./ListaAvaliacaoHabilidade";
export default {
  name: "ModalCadastroAvaliacao",
  components: {
    ListaGrupoAvaliacao,
    ListaAvaliacaoHabilidade
  },
  props: {
    idTurma: Number,
    siglaTurma: String,
    idDisciplina: Number,
    siglaDisciplina: String
  },
  mixins: [validationMixin, messageMixin],
  validations: {
    avaliacao: Avaliacao.validations( required, maxLength, numeric )
  },
  validationMessages: {
    avaliacao: Avaliacao.validationMessages()
  },
  data() {
    return {
      existeGrupos: false,
      loading: false,
      btnTitle: "",
      dialog: false,
      formTitle: "",
      idAtividade: null,
      tiposAvaliacoes: [],
      tiposFormatos: [],
      tiposAbrangencias: [],
      tiposRegistrosNotas: [],
      datasAtividades: [],
      horasInicios: [],
      avaliacao: new Avaliacao()
    };
  },
  watch: {
    async idAtividade() {
      this.avaliacao.idAtividade = this.idAtividade;
      if (this.idAtividade) {
        this.horasInicios = await atividadeService.listarHorasIniciaisAtividades(this.idAtividade);
      } else {
        this.horasInicios = [];
        this.avaliacao.horaInicio = null;
      }
    },
    dialog(val) {
      val || this.close();
    }
  },
  async created() {
    // EventBus.$on("incluiCreditoPendente", async credito => {
    //   this.contrato.creditosContratos.push(credito);
    // });
    EventBus.$on("editaAvaliacao", async idAvaliacao => {
      this.carregarDadosBasicos();
      this.formTitle = "Alterar Avaliação";
      this.btnTitle = "Alterar";
      this.avaliacao = await avaliacaoService.editar(idAvaliacao);
      this.idAtividade = this.avaliacao.idAtividade;
      this.existeGrupos = await avaliacaoService.existeGrupoAvaliacao(this.avaliacao.id);
      this.dialog = true;
    });
  },
  methods: {
    // verificarErroCreditosObrigatorios() {
    //   if (this.$v.contrato.creditosContratos.$error) {
    //     return true;
    //   } else {
    //     return false;
    //   }
    // },
    abrirCadastro() {
      this.carregarDadosBasicos();
      this.carregarIds();
      this.formTitle = "Incluir Avaliação";
      this.btnTitle = "Salvar";
      this.dialog = true;
    },
    salvar() {
      this.$v.avaliacao.$touch();
      if (this.$v.$invalid) {
        return;
      }
      avaliacaoService.salvar(this.avaliacao).then(() => {
        this.$emit("avaliacaoCadastrada", true);
        this.dialog = false;
      });
    },
    async carregarDadosBasicos() {
      this.tiposAvaliacoes = await avaliacaoService.tipos();
      this.tiposFormatos = await avaliacaoService.tiposFormatos();
      this.tiposAbrangencias = await avaliacaoService.tiposAbrangencias();
      this.tiposRegistrosNotas = await avaliacaoService.tiposRegistrosNotas();
      this.datasAtividades = await atividadeService.listarDatasAtividades(this.idTurma, this.idDisciplina);
    },
    carregarIds() {
      this.avaliacao.idTurma = this.idTurma;
      this.avaliacao.idDisciplina = this.idDisciplina;
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.$v.$reset();
        this.avaliacao = new Avaliacao();
      }, 300);
    }
  }
};
</script>
