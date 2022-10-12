class CampoExperiencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.nivel = dados ? dados.nivel : null;
  }

  static validations(required, maxLength) {
    return {
      nome: { required, maxLength: maxLength(100) },
      idNivel: { required }
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 100 caracteres"
      },
      idNivel: {
        required: "Nível é obrigatório"
      }
    };
  }
}
export default CampoExperiencia;
