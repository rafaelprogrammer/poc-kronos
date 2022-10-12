class Frequencia {

  static PRESENCA() {
    return "P";
  }
  static ATIVIDADE_DOMICILIAR() {
    return "AD";
  }
  static FALTA() {
    return "F";
  }
  static FALTA_JUSTIFICADA() {
    return "FJ";
  }
  static INDEFINIDO() {
    return "I";
  }
  static TRANSFERENCIA() {
    return "T";
  }
  static CANCELADA() {
    return "C";
  }

  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataReposicao = dados ? dados.dataReposicao : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idCredito = dados ? dados.idCredito : null;
    this.numeroAtividade = dados ? dados.numeroAtividade : null;
    this.numeroFaltas = dados ? dados.numeroFaltas : null;
    this.frequencia = dados ? dados.frequencia : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.tipoFrequenciaAtestado = dados ? dados.tipoFrequenciaAtestado : null;
  }

  static validations(required) {
    return {
      dataReposicao: { required }
    };
  }

  static validationMessages() {
    return {
      dataReposicao: {
        required: "Data Reposição é obrigatória"
      }
    };
  }
}
export default Frequencia;
