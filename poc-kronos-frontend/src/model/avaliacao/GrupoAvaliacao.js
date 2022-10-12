class GrupoAvaliacao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.idAvaliacao = dados ? dados.idAvaliacao : null;
    this.tema = dados ? dados.tema : null;
    this.qtdAvaliados = dados ? dados.qtdAvaliados : null;
  }

  static validations(required, maxLength, numeric) {
    return {
      idAvaliacao: { required },
      numero: { required, numeric },
      tema: { required, maxLength: maxLength(100) }
    };
  }

  static validationMessages() {
    return {
      idAvaliacao: {
        required: "Avaliação é obrigatória"
      },
      numero: {
        required: "Número é obrigatório",
        numeric: "Somente números"
      },
      tema: {
        required: "Tema é obrigatório",
        maxLength: "Tema só aceita no máximo 100 caracteres"
      }
    };
  }
}
export default GrupoAvaliacao;
