class SubFase {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.sigla = dados ? dados.sigla : null;
    this.numero = dados ? dados.numero : null;
    this.idTipoPeriodo = dados ? dados.idTipoPeriodo : null;
    this.nomeTipoPeriodo = dados ? dados.nomeTipoPeriodo : null;
    this.idFase = dados ? dados.idFase : null;
  }

  static validations(required, maxLength, numeric) {
    return {
      nome: { required, maxLength: maxLength(30) },
      sigla: { required, maxLength: maxLength(10) },
      numero: { required, numeric },
      idTipoPeriodo: { required },
      idFase: { required }
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 30 caracteres"
      },
      sigla: {
        required: "Sigla é obrigatória",
        maxLength: "Sigla só aceita no máximo 10 caracteres"
      },
      numero: {
        required: "Número é obrigatório",
        numeric: "Somente número"
      },
      idTipoPeriodo: {
        required: "Tipo de Período é obrigatório"
      },
      idFase: {
        required: "Fase é obrigatória"
      }
    };
  }
}
export default SubFase;
