class ContratoMatricula {
  static PRE_MATRICULA() {
    return 1;
  }
  static VALIDADO() {
    return 2;
  }
  static ANALISE_FINANCEIRA() {
    return 3;
  }
  static CANCELADO() {
    return 9;
  }
  static TRANSFERIDO() {
    return 8;
  }
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.ano = dados ? dados.ano : null;
    this.data = dados ? dados.data : null;
    this.idTipoSituacaoContrato = dados ? dados.idTipoSituacaoContrato : null;
    this.nomeTipoSituacaoContrato = dados
      ? dados.nomeTipoSituacaoContrato
      : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.nomePeriodo = dados ? dados.nomePeriodo : null;
    this.idMatricula = dados ? dados.idMatricula : null;
    this.creditosContratos = dados ? dados.creditosContratos : [];
  }

  static validations(required, numeric) {
    return {
      ano: { required, numeric },
      data: { required },
      idInstituicao: { required },
      idPeriodoExecucao: { required },
      idMatricula: { required },
      creditosContratos: { required }
    };
  }

  static validationMessages() {
    return {
      ano: {
        required: "Ano é obrigatório",
        numeric: "Somente números"
      },
      data: {
        required: "Data é obrigatória"
      },
      idTipoSituacaoContrato: {
        required: "Tipo de Situação do Contrato é obrigatório"
      },
      idInstituicao: {
        required: "Instituição é obrigatória"
      },
      idPeriodo: {
        required: "Período é obrigatório"
      },
      idMatricula: {
        required: "Matrícula é obrigatória"
      },
      creditosContratos: {
        required: "Créditos são obrigatórios para salvar o contrato"
      }
    };
  }
}
export default ContratoMatricula;
