class Valor {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : true;
  }

  static validations(required, maxLength) {
    return {
      nome: { required, maxLength: maxLength(20) },
      codigo: { required, maxLength: maxLength(5) },
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 20 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        maxLength: "Código só aceita no máximo 5 caracteres"
      }
    };
  }
}
export default Valor;
