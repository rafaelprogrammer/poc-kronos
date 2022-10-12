class CursoDocumento {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.original = dados ? dados.original : null;
    this.autenticacao = dados ? dados.autenticacao : null;
    this.numeroCopia = dados ? dados.numeroCopia : null;
    this.idTipoDocumento = dados ? dados.idTipoDocumento : null;
    this.nomeTipoDocumento = dados ? dados.nomeTipoDocumento : null;
    this.idCurso = dados ? dados.idCurso : null;
  }

  static validations(required, numeric) {
    return {
      numeroCopia: { required, numeric },
      idTipoDocumento: { required },
      idCurso: { required }
    };
  }

  static validationMessages() {
    return {
      numeroCopia: {
        required: "Número de Cópia é obrigatório",
        numeric: "Somente número"
      },
      idTipoDocumento: {
        required: "Tipo Documento é obrigatório"
      },
      idCurso: {
        required: "Curso é obrigatório"
      }
    };
  }
}
export default CursoDocumento;
