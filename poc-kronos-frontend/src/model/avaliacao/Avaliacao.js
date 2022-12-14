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
        required: "Atividade ?? obrigat??ria"
      },
      peso: {
        required: "Peso ?? obrigat??rio"
      },
      horaInicio: {
        required: "Hora Inc??o ?? obrigat??ria"
      },
      numeroMaxParticipante: {
        required: "N??mero m??ximo de participantes ?? obrigat??rio",
        numeric: "Somente n??meros"
      },
      idTipoFormato: {
        required: "Formato ?? obrigat??rio"
      },
      idTipoRegistroNota: {
        required: "Registro de Nota ?? obrigat??ria"
      },
      idTipoAbrangencia: {
        required: "Abrang??ncia ?? obrigat??ria"
      },
      idTipoAvaliacao: {
        required: "Avalia????o ?? obrigat??ria"
      },
      tempoDuracao: {
        required: "Tempo de dura????o ?? obrigat??ria",
        numeric: "Somente n??meros"
      },
      observacao: {
        maxLength: "Observa????o s?? aceita no m??ximo 200 caracteres"
      },
      motivoAnulacao: {
        maxLength: "Motivo Anula????o s?? aceita no m??ximo 200 caracteres"
      },
      idTurma: {
        required: "Turma ?? obrigat??ria"
      },
      idDisciplina: {
        required: "Disciplina ?? obrigat??ria"
      }
    };
  }
}
export default Avaliacao;
