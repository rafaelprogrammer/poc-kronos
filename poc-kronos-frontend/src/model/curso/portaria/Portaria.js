class Portaria {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataPublicacao = dados ? dados.dataPublicacao : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.descricao = dados ? dados.descricao : null;
    this.documentoPublicacao = dados ? dados.documentoPublicacao : null;
    this.idTipoPortaria = dados ? dados.idTipoPortaria : null;
    this.nomeTipoPortaria = dados ? dados.nomeTipoPortaria : null;
    this.idCurso = dados ? dados.idCurso : null;
  }

  static validations(required, maxLength, numeric, dataFimMenor) {
    return {
      documentoPublicacao: { required, maxLength: maxLength(200) },
      descricao: { required, maxLength: maxLength(200) },
      dataPublicacao: { required },
      dataInicioVigencia: { required },
      dataFinalVigencia: { dataFimMenor: dataFimMenor("dataInicioVigencia") },
      idTipoPortaria: { required },
      idCurso: { required }
    };
  }

  static validationMessages() {
    return {
      documentoPublicacao: {
        required: "Documento é obrigatório",
        maxLength: "Documento só aceita no máximo 200 caracteres"
      },
      descricao: {
        required: "Descrição é obrigatória",
        maxLength: "Descrição só aceita no máximo 200 caracteres"
      },
      dataPublicacao: {
        required: "Data Publicação é obrigatório"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vigência é obrigatório"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vigência não pode ser menor que a Data Início de Vigência"
      },
      idTipoPortaria: {
        required: "Tipo de Portaria é obrigatória"
      },
      idCurso: {
        required: "Curso é obrigatório"
      }
    };
  }
}
export default Portaria;
