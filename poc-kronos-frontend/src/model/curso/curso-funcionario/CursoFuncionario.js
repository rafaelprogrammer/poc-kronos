class CursoFuncionario {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFinal = dados ? dados.dataFinal : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.nomeFuncionario = dados ? dados.nomeFuncionario : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.idTipoFuncao = dados ? dados.idTipoFuncao : null;
    this.nomeTipoFuncao = dados ? dados.nomeTipoFuncao : null;
  }

  static validations(required, dataFimMenor) {
    return {
      dataInicio: { required },
      dataFinal: { dataFimMenor: dataFimMenor("dataInicio") },
      idFuncionario: { required },
      idCurso: { required },
      idTipoFuncao: { required }
    };
  }

  static validationMessages() {
    return {
      dataInicio: {
        required: "Data Início é obrigatório"
      },
      dataFinal: {
        dataFimMenor: "Data Final não pode ser menor que a Data Início"
      },
      idFuncionario: {
        required: "Funcionário é obrigatório"
      },
      idCurso: {
        required: "Curso é obrigatório"
      },
      idTipoFuncao: {
        required: "Tipo de Função é obrigatória"
      }
    };
  }
}
export default CursoFuncionario;
