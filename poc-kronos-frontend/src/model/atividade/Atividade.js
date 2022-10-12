class Atividade {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataPrevista = dados ? dados.dataPrevista : null;
    this.dataRealizacao = dados ? dados.dataRealizacao : null;
    this.idHorario = dados ? dados.idHorario : null;
    this.idSubFaseExecucao = dados ? dados.idSubFaseExecucao : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.diaDaSemana = dados ? dados.diaDaSemana : null;
    this.regimeLetivo = dados ? dados.regimeLetivo : null;
    this.professor = dados ? dados.professor : null;
    this.bimestre = dados ? dados.bimestre : null;
    this.horasAtividades = dados ? dados.horasAtividades : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.selecionado = null;
    this.anoTurma = dados ? dados.anoTurma : null;
  }

  static validations(required, dataFimMenor) {
    return {
      dataRealizacao: { required, dataFimMenor: dataFimMenor("dataPrevista") }
    };
  }

  static validationMessages() {
    return {
      dataRealizacao: {
        required: "Data Realização é obrigatória",
        dataFimMenor: "Data Realização não pode ser menor que a Data Prevista"
      }
    };
  }
}
export default Atividade;
