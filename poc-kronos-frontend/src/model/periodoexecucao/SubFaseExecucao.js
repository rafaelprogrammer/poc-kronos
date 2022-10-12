class SubFaseExecucao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFim = dados ? dados.dataFim : null;
    this.idFaseExecucao = dados ? dados.idFaseExecucao : null;
    this.tipoPeriodo = dados ? dados.tipoPeriodo : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.idSubFase = dados ? dados.idSubFase : null;
    this.siglaSubFase = dados ? dados.siglaSubFase : null;
    this.numeroSubFase = dados ? dados.numeroSubFase : null;
    this.anoPeriodoExecucao = dados ? dados.anoPeriodoExecucao : null;
  }

  static validations(required, dataFimMenor) {
    return {
      idFaseExecucao: { required },
      idSubFase: { required },
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
      idFaseExecucao: {
        required: "Fase Execução é obrigatória"
      },
      idSubFase: {
        required: "Sub-Fase é obrigatória"
      }
    };
  }
}
export default SubFaseExecucao;
