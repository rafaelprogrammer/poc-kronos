class Parcela {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.dataVencimento = dados ? dados.dataVencimento : null;
    this.dataPagamento = dados ? dados.dataPagamento : null;
    this.valorJuros = dados ? dados.valorJuros : null;
    this.valorMulta = dados ? dados.valorMulta : null;
    this.valorDesconto = dados ? dados.valorDesconto : null;
    this.valorPagamento = dados ? dados.valorPagamento : null;
    this.valorOriginal = dados ? dados.valorOriginal : null;
    this.observacao = dados ? dados.observacao : null;
    this.idTipoFormaPagamento = dados ? dados.idTipoFormaPagamento : null;
    this.nomeTipoFormaPagamento = dados ? dados.nomeTipoFormaPagamento : null;
    this.idContrato = dados ? dados.idContrato : null;
    this.numeroParcelas = dados ? dados.numeroParcelas : null;
  }

  static validations(required, numeric, decimal) {
    return {
      numero: { required, numeric },
      dataVencimento: { required },
      valorOriginal: { required, decimal },
      valorJuros: { required, decimal },
      valorMulta: { required, decimal },
      valorDesconto: { required, decimal },
      valorPagamento: { required, decimal },
      idTipoFormaPagamento: { required },
      idContrato: { required }
    };
  }

  static validationMessages() {
    return {
      numero: {
        required: "Número é obrigatório",
        numeric: "Somente números"
      },
      dataVencimento: {
        required: "Data Vencimento é obrigatória"
      },
      valorOriginal: {
        required: "Valor Original é obrigatório",
        decimal: "Somente valor decimal"
      },
      valorJuros: {
        decimal: "Somente valor decimal"
      },
      valorMulta: {
        decimal: "Somente valor decimal"
      },
      valorDesconto: {
        decimal: "Somente valor decimal"
      },
      valorPagamento: {
        decimal: "Somente valor decimal"
      },
      idTipoFormaPagamento: {
        required: "Forma de pagamento é obrigatória"
      },
      idContrato: {
        required: "Contrato é obrigatório"
      }
    };
  }
}
export default Parcela;
