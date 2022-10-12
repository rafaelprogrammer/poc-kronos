class Substituto {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicial = dados ? dados.dataInicial : null;
    this.dataFinal = dados ? dados.dataFinal : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.nomeFuncionario = dados ? dados.nomeFuncionario : null;
    this.idHorario = dados ? dados.idHorario : null;
  }

  static validations(required, dataFimMenor) {
    return {
      dataInicial: { required },
      dataFinal: { required, dataFimMenor: dataFimMenor("dataInicial") },
      idFuncionario: { required },
      idHorario: { required }
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
      idFuncionario: {
        required: "Funcionário é obrigatório"
      },
      idHorario: {
        required: "Horário é obrigatório"
      }
    };
  }
}
export default Substituto;
