class Ponto {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.tarefaOnline = dados ? dados.tarefaOnline : null;
    this.horaInicialRegistro = dados ? dados.horaInicialRegistro : null;
    this.horaFinalRegistro = dados ? dados.horaFinalRegistro : null;
    this.horaInicialInformado = dados ? dados.horaInicialInformado : null;
    this.horaFinalInformado = dados ? dados.horaFinalInformado : null;
    this.data = dados ? dados.data : null;
    this.idTipoPeriodoPonto = dados ? dados.idTipoPeriodoPonto : null;
    this.nomeTipoPeriodoPonto = dados ? dados.nomeTipoPeriodoPonto : null;
    this.idConfiguracaoPonto = dados ? dados.idConfiguracaoPonto : null;
    this.idFuncionarioLiberacao = dados ? dados.idFuncionarioLiberacao : null;
    this.dataLiberacao = dados ? dados.dataLiberacao : null;
    this.dataHomologacao = dados ? dados.dataHomologacao : null;
    this.idFuncionarioHomologacao = dados
      ? dados.idFuncionarioHomologacao
      : null;
    this.descricao = dados ? dados.descricao : null;
    this.horaInicialPrevista = dados ? dados.horaInicialPrevista : null;
    this.horaFinalPrevista = dados ? dados.horaFinalPrevista : null;
    this.toleranciaEntrada = dados ? dados.toleranciaEntrada : null;
    this.toleranciaSaida = dados ? dados.toleranciaSaida : null;
    this.dataHoje = dados ? dados.dataHoje : null;
    this.pendente = false;
    this.idFuncionario = null;
    this.ano = null;
    this.mes = null;
    this.diaSemana = dados ? dados.diaSemana : null;
    this.funcionarioLiberacao = dados ? dados.funcionarioLiberacao : null;
    this.funcionarioHomologacao = dados ? dados.funcionarioHomologacao : null;
    this.idsPontos = [];
  }

  static validations(required, maxLength, dataFimMenor, horaFinalMenor) {
    return {
      descricao: { maxLength: maxLength(200) },
      idTipoPeriodoPonto: { required },
      horaFinalInformado: {
        horaFinalMenor: horaFinalMenor("horaInicialInformado", true)
      }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        maxLength: "Nome só aceita no máximo 200 caracteres"
      },
      idTipoPeriodoPonto: {
        required: "Tipo Período é Obrigatório"
      },
      horaFinalInformado: {
        horaFinalMenor:
          "Hora Final Informado não pode ser menor ou igual a Hora Inicial Informado"
      }
    };
  }
}
export default Ponto;
