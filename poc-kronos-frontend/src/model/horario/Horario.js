class Horario {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idHoraAtividade = dados ? dados.idHoraAtividade : null;
    this.dataInicial = dados ? dados.dataInicial : null;
    this.dataFinal = dados ? dados.dataFinal : null;
    this.horaInicial = dados ? dados.horaInicial : null;
    this.horaFinal = dados ? dados.horaFinal : null;
    this.regular = dados ? dados.regular : true;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.professor = dados ? dados.professor : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.disciplina = dados ? dados.disciplina : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.idTipoDiaSemana = dados ? dados.idTipoDiaSemana : null;
    this.diaSemana = dados ? dados.diaSemana : null;
    this.idTipoRegimeLetivo = dados ? dados.idTipoRegimeLetivo : null;
    this.nomeTipoRegimeLetivo = dados ? dados.nomeTipoRegimeLetivo : null;
    this.idTipoMatrizHorario = null;
  }

  static validations(required, horaFinalMenor, dataFimMenor) {
    return {
      dataInicial: { required },
      dataFinal: { required, dataFimMenor: dataFimMenor("dataInicial") },
      // horaInicio: { required },
      // horaFinal: { required, horaFinalMenor: horaFinalMenor("horaInicio") },
      idFuncionario: { required },
      idDisciplina: { required },
      idTurma: { required },
      idTipoRegimeLetivo: { required }
    };
  }

  static validationMessages() {
    return {
      dataInicial: {
        required: "Data Início é obrigatório"
      },
      dataFinal: {
        required: "Data Final é obrigatória",
        dataFimMenor: "Data Final não pode ser menor que a Data Inicial"
      },
      // horaInicio: {
      //   required: "Hora Início obrigatório"
      // },
      // horaFinal: {
      //   required: "Hora Final é obrigatório",
      //   horaFinalMenor: "Hora final não pode ser menor que a Hora Inicial"
      // },
      idFuncionario: {
        required: "Funcionário é obrigatório"
      },
      idDisciplina: {
        required: "Disciplina é obrigatória"
      },
      idTurma: {
        required: "Turma é obrigatória"
      },
      idTipoRegimeLetivo: {
        required: "Regime Letivo é obrigatório"
      }
    };
  }
}
export default Horario;
