class ResultadoHabilidade {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idAvaliado = dados ? dados.idAvaliado : null;
    this.idAvaliacaoHabilidade = dados ? dados.idAvaliacaoHabilidade : null;
    this.nota = dados ? dados.nota : null;
    this.idMencao = dados ? dados.idMencao : null;
    this.descarte = dados ? dados.descarte : null;
    this.motivoDescarte = dados ? dados.motivoDescarte : null;
    this.idsAvaliados = dados ? dados.idsAvaliados : [];
    this.idsMencoes = dados ? dados.idsMencoes : [];
  }
  static validations(required) {
    return {
      idAvaliacaoHabilidade: { required },
      idMencao: { required }
    };
  }

  static validationMessages() {
    return {
      idAvaliacaoHabilidade: {
        required: "Avaliação e Habilidade é obrigatória"
      },
      idMencao: {
        required: "Menção é obrigatória"
      }
    };
  }
}
export default ResultadoHabilidade;
