class Direito {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.nivel = dados ? dados.nivel : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : null;
    this.bncc = dados ? dados.bncc : null;
    this.idsCamposExperiencias = dados ? dados.idsCamposExperiencias : null;
  }

  static validations(required, maxLength) {
    return {
      descricao: { required, maxLength: maxLength(400) },
      codigo: { required, maxLength: maxLength(5) },
      idNivel: { required },
      idsCamposExperiencias: { required }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        required: "Descrição é obrigatória",
        maxLength: "Descrição só aceita no máximo 400 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        maxLength: "Código só aceita no máximo 5 caracteres"
      },
      idNivel: {
        required: "Nível é obrigatório"
      },
      idsCamposExperiencias: {
        required: "Campos Experiências é obrigatório"
      }
    };
  }
}
export default Direito;
