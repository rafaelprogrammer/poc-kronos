class Ocorrencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.data = dados ? dados.data : null;
    this.hora = dados ? dados.hora : null;
    this.registro = dados ? dados.registro : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.idTipoOcorrencia = dados ? dados.idTipoOcorrencia : null;
    this.idMatricula = dados ? dados.idMatricula : null;
    this.idFuncionarioRelator = dados ? dados.idFuncionarioRelator : null;
    this.idFuncionarioRegistro = dados ? dados.idFuncionarioRegistro : null;
    this.dataCiencia = dados ? dados.dataCiencia : null;
    this.idResponsavelCiencia = dados ? dados.idResponsavelCiencia : null;
    this.anulado = dados ? dados.anulado : null;
    this.dataAnulacao = dados ? dados.dataAnulacao : null;
    this.motivoAnulacao = dados ? dados.motivoAnulacao : null;
    this.idFuncionarioAnulacao = dados ? dados.idFuncionarioAnulacao : null;
    this.fator = dados ? dados.fator : null;
    this.nomeFator = dados ? dados.nomeFator : null;
    this.valor = dados ? dados.valor : null;
    this.idPessoaSelecionada = null;
  }

  static validations(required, dataFimMenor) {
    return {
      data: { required },
      hora: { required },
      registro: { required },
      idTurma: { required },
      idTipoOcorrencia: { required },
      idMatricula: { required },
      idFuncionarioRelator: { required },
      dataAnulacao: { dataFimMenor: dataFimMenor("data") },
      dataCiencia: { dataFimMenor: dataFimMenor("data") }
    };
  }

  static validationMessages() {
    return {
      data: {
        required: "Data é obrigatória"
      },
      hora: {
        required: "Hora é obrigatório"
      },
      registro: {
        required: "Registro é obrigatório"
      },
      idTurma: {
        required: "Turma é obrigatório"
      },
      idTipoOcorrencia: {
        required: "Tipo Ocorrência é obrigatório"
      },
      idMatricula: {
        required: "Matrícula é obrigatória"
      },
      idFuncionarioRelator: {
        required: "Relator é obrigatório"
      },
      dataAnulacao: {
        dataFimMenor: "Data Anulação não pode ser menor que a Data da Ocorrência"
      },
      dataCiencia: {
        dataFimMenor: "Data Ciência não pode ser menor que a Data da Ocorrência"
      }
    };
  }
}
export default Ocorrencia;
