class FaseExecucao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFim = dados ? dados.dataFim : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.tipoPeriodo = dados ? dados.tipoPeriodo : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.idFase = dados ? dados.idFase : null;
    this.siglaFase = dados ? dados.siglaFase : null;
    this.numeroFase = dados ? dados.numeroFase : null;
    this.numeroSubFases = dados ? dados.numeroSubFases : null;
    this.idCurso = dados ? dados.idCurso : null;
  }

  static validations(required, dataFimMenor) {
    return {
      idPeriodoExecucao: { required },
      idFase: { required },
      dataInicio: { required },
      dataFim: { required, dataFimMenor: dataFimMenor("dataInicio") },
    };
  }

  static validationMessages() {
    return {
      dataInicio: {
        required: "Data Início é obrigatório"
      },
      dataFim: {
        required: "Data Final é obrigatória",
        dataFimMenor: "Data Final não pode ser menor que a Data Inicial"
      },
      idPeriodoExecucao: {
        required: "Período Execução é obrigatório"
      },
      idFase: {
        required: "Fase é obrigatória"
      }
    };
  }
}
export default FaseExecucao;
