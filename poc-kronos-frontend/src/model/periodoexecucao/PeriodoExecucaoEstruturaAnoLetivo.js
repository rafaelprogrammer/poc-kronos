class PeriodoExecucaoEstruturaAnoLetivo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.ano = dados ? dados.ano : null;
    this.idPeriodo = dados ? dados.idPeriodo : null;
    this.idCalendario = dados ? dados.idCalendario : null;
    this.tipoPeriodo = dados ? dados.tipoPeriodo : null;
    this.siglaPeriodo = dados ? dados.siglaPeriodo : null;
    this.numeroPeriodo = dados ? dados.numeroPeriodo : null;
    this.calendario = dados ? dados.calendario : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFim = dados ? dados.dataFim : null;
    this.numeroFases = dados ? dados.numeroFases : null;
  }

  static validations(required) {
    return {
      idPeriodo: { required },
      idCalendario: { required }
    };
  }

  static validationMessages() {
    return {
      idPeriodo: {
        required: "Período é obrigatório"
      },
      idCalendario: {
        required: "Calendário é obrigatório"
      }
    };
  }
}
export default PeriodoExecucaoEstruturaAnoLetivo;
