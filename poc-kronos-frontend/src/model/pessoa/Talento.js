class Talento {
  constructor(dados) {
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idTipoTalento = dados ? dados.idTipoTalento : null;
    this.idsTiposTalentos = null;
    this.nomeTipoTalento = dados ? dados.nomeTipoTalento : null;
  }

  static validations(required) {
    return {
      idPessoa: { required },
      idsTiposTalentos: { required }
    };
  }

  static validationMessages() {
    return {
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idsTiposTalentos: {
        required: "Tipo Talento é obrigatório"
      }
    };
  }
}
export default Talento;
