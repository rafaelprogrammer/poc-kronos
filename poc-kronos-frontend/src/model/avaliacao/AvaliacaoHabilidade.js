class AvaliacaoHabilidade {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idAvaliacao = dados ? dados.idAvaliacao : null;
    this.idDisciplinaHabilidade = dados ? dados.idDisciplinaHabilidade : null;
    this.codigo = dados ? dados.codigo : null;
    this.descricao = dados ? dados.descricao : null;
    this.qtdResultado = dados ? dados.qtdResultado : null;
    this.idMencao = null;
  }

  static validations(required) {
    return {
      idAvaliacao: { required },
      idDisciplinaHabilidade: { required }
    };
  }

  static validationMessages() {
    return {
      idDisciplinaHabilidade: {
        required: "Habilidade é obrigatória"
      },
      idAvaliacao: {
        required: "Avaliação é obrigatória"
      }
    };
  }
}
export default AvaliacaoHabilidade;
