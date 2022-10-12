class Avaliacao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.horaInicio = dados ? dados.horaInicio : null;
    this.grupo = dados ? dados.grupo : null;
    this.numeroMaxParticipante = dados ? dados.numeroMaxParticipante : null;
    this.peso = dados ? dados.peso : null;
    this.observacao = dados ? dados.observacao : null;
    this.anulada = dados ? dados.anulada : null;
    this.motivoAnulacao = dados ? dados.motivoAnulacao : null;
    this.idTipoFormato = dados ? dados.idTipoFormato : null;
    this.nomeTipoFormato = dados ? dados.nomeTipoFormato : null;
    this.idTipoRegistroNota = dados ? dados.idTipoRegistroNota : null;
    this.idTipoAbrangencia = dados ? dados.idTipoAbrangencia : null;
    this.nomeTipoAbrangencia = dados ? dados.nomeTipoAbrangencia : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idTipoAvaliacao = dados ? dados.idTipoAvaliacao : null;
    this.nomeTipoAvaliacao = dados ? dados.nomeTipoAvaliacao : null;
    this.dataPrevista = dados ? dados.dataPrevista : null;
    this.tempoDuracao = dados ? dados.tempoDuracao : null;
    this.qtdAvaliados = dados ? dados.qtdAvaliados : null;
    this.gruposAvaliacoes = dados ? dados.gruposAvaliacoes : [];
    this.avaliacoesHabilidades = dados ? dados.avaliacoesHabilidades : [];
    this.idTurma = dados ? dados.idTurma : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
  }

  static validations(required, maxLength, numeric) {
    return {
      idAtividade: { required },
      peso: { required },
      horaInicio: { required },
      numeroMaxParticipante: { required, numeric },
      idTipoFormato: { required },
      idTipoRegistroNota: { required },
      idTipoAbrangencia: { required },
      idTipoAvaliacao: { required },
      tempoDuracao: { required, numeric },
      observacao: { maxLength: maxLength(200) },
      motivoAnulacao: { maxLength: maxLength(200) },
      idTurma: { required },
      idDisciplina: { required }
    };
  }

  static validationMessages() {
    return {
      idAtividade: {
        required: "Atividade é obrigatória"
      },
      peso: {
        required: "Peso é obrigatório"
      },
      horaInicio: {
        required: "Hora Incío é obrigatória"
      },
      numeroMaxParticipante: {
        required: "Número máximo de participantes é obrigatório",
        numeric: "Somente números"
      },
      idTipoFormato: {
        required: "Formato é obrigatório"
      },
      idTipoRegistroNota: {
        required: "Registro de Nota é obrigatória"
      },
      idTipoAbrangencia: {
        required: "Abrangência é obrigatória"
      },
      idTipoAvaliacao: {
        required: "Avaliação é obrigatória"
      },
      tempoDuracao: {
        required: "Tempo de duração é obrigatória",
        numeric: "Somente números"
      },
      observacao: {
        maxLength: "Observação só aceita no máximo 200 caracteres"
      },
      motivoAnulacao: {
        maxLength: "Motivo Anulação só aceita no máximo 200 caracteres"
      },
      idTurma: {
        required: "Turma é obrigatória"
      },
      idDisciplina: {
        required: "Disciplina é obrigatória"
      }
    };
  }
}
export default Avaliacao;
