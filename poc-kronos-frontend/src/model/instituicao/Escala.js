class Escala {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.idTipoAbrangencia = dados ? dados.idTipoAbrangencia : null;
    this.nomeTipoAbrangencia = dados ? dados.nomeTipoAbrangencia : null;
    this.idTipoAvaliacao = dados ? dados.idTipoAvaliacao : null;
    this.nomeTipoAvaliacao = dados ? dados.nomeTipoAvaliacao : null;
    this.idCurso = null;
  }

  static validations(required, maxLength, numeric, dataFimMenor) {
    return {
      nome: { required, maxLength: maxLength(40) },
      dataInicioVigencia: { required },
      dataFinalVigencia: { dataFimMenor: dataFimMenor("dataInicioVigencia") },
      idInstituicao: { required },
      idTipoAbrangencia: { required },
      idTipoAvaliacao: { required }
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 40 caracteres"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vigência é obrigatório"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vigência não pode ser menor que a Data Início de Vigência"
      },
      idInstituicao: {
        required: "Instituição é obrigatória"
      },
      idTipoAbrangencia: {
        required: "Tipo de abrangência é obrigatória"
      },
      idTipoAvaliacao: {
        required: "Tipo de avaliação é obrigatória"
      }
    };
  }
}
export default Escala;
