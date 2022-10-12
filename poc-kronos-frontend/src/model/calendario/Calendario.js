class Calendario {
  static EM_ELABORACAO() {
    return 1;
  }
  static CONCLUIDO() {
    return 2;
  }
  static APROVADO() {
    return 3;
  }
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.ano = dados ? dados.ano : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFinal = dados ? dados.dataFinal : null;
    this.dataInicioAtribuicaoCredito = dados
      ? dados.dataInicioAtribuicaoCredito
      : null;
    this.dataFinalAtribuicaoCredito = dados
      ? dados.dataFinalAtribuicaoCredito
      : null;
    this.dataInicioAnoLetivo = dados ? dados.dataInicioAnoLetivo : null;
    this.dataFinalAnoLetivo = dados ? dados.dataFinalAnoLetivo : null;
    this.descricao = dados ? dados.descricao : null;
    this.dataAprovacao = dados ? dados.dataAprovacao : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.idFuncionarioAprovacao = dados ? dados.idFuncionarioAprovacao : null;
    this.nomeFuncionarioAprovacao = dados
      ? dados.nomeFuncionarioAprovacao
      : null;
    this.idTipoSituacaoCalendario = dados
      ? dados.idTipoSituacaoCalendario
      : null;
    this.nomeTipoSituacaoCalendario = dados
      ? dados.nomeTipoSituacaoCalendario
      : null;
    this.dataConclusao = dados ? dados.dataConclusao : null;
    this.idFuncionarioElaboracao = dados ? dados.idFuncionarioElaboracao : null;
    this.nomeFuncionarioElaboracao = dados
      ? dados.nomeFuncionarioElaboracao
      : null;
    this.idsCursos = dados ? dados.idsCursos : null;
  }

  static validations(required, numeric, maxLength, dataFimMenor) {
    return {
      ano: { required, numeric },
      dataInicio: { required },
      dataFinal: { required, dataFimMenor: dataFimMenor("dataInicio") },
      dataInicioAtribuicaoCredito: { required },
      dataFinalAtribuicaoCredito: {
        required,
        dataFimMenor: dataFimMenor("dataInicioAtribuicaoCredito")
      },
      dataInicioAnoLetivo: { required },
      dataFinalAnoLetivo: {
        required,
        dataFimMenor: dataFimMenor("dataInicioAnoLetivo")
      },
      descricao: { required, maxLength: maxLength(500) },
      idsCursos: { required }
    };
  }

  static validationMessages() {
    return {
      ano: {
        required: "Ano é obrigatório",
        numeric: "Somente números"
      },
      dataInicio: {
        required: "Data Início é obrigatório"
      },
      dataFinal: {
        required: "Data Final é obrigatória",
        dataFimMenor: "Data Final não pode ser menor que a Data Inicial"
      },
      dataInicioAtribuicaoCredito: {
        required: "Data Início Atribuição Crédito é obrigatório"
      },
      dataFinalAtribuicaoCredito: {
        required: "Data Final Atribuição Crédito é obrigatória",
        dataFimMenor:
          "Data Final Atribuição Crédito não pode ser menor que a Data Início Atribuição Crédito"
      },
      dataInicioAnoLetivo: {
        required: "Data Início Ano Letivo é obrigatório"
      },
      dataFinalAnoLetivo: {
        required: "Data Final Ano Letivo é obrigatória",
        dataFimMenor:
          "Data Final Ano Letivo não pode ser menor que a Data Início Ano Letivo"
      },
      descricao: {
        required: "Descrição é obrigatória"
      },
      idsCursos: {
        required: "Curso é obrigatório"
      }
    };
  }
}
export default Calendario;
