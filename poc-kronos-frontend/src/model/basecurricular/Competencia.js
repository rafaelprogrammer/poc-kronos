class Competencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.nivel = dados ? dados.nivel : null;
    this.idComponente = dados ? dados.idComponente : null;
    this.componente = dados ? dados.componente : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : null;
    this.bncc = dados ? dados.bncc : null;
    this.geral = dados ? dados.geral : null;
  }

  static validations(required, maxLength) {
    return {
      descricao: { required, maxLength: maxLength(500) },
      codigo: { required, maxLength: maxLength(10) },
      idNivel: { required }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        required: "Descrição é obrigatória",
        maxLength: "Descrição só aceita no máximo 500 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        maxLength: "Código só aceita no máximo 10 caracteres"
      },
      idNivel: {
        required: "Nível é obrigatório"
      }
    };
  }
}
export default Competencia;
