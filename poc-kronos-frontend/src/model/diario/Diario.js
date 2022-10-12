class Diario {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.procedimento = dados ? dados.procedimento : null;
    this.recurso = dados ? dados.recurso : null;
    this.observacao = dados ? dados.observacao : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idTipoPrograma = dados ? dados.idTipoPrograma : null;
    this.nomePrograma = dados ? dados.nomePrograma : null;
  }

  static validations(required, maxLength) {
    return {
      procedimento: { required, maxLength: maxLength(500) },
      recurso: { required, maxLength: maxLength(500) },
      observacao: { maxLength: maxLength(500) },
      idAtividade: { required },
      idTipoPrograma: { required }
    };
  }

  static validationMessages() {
    return {
      procedimento: {
        required: "Procedimento é obrigatório",
        maxLength: "Procedimento só aceita no máximo 500 caracteres"
      },
      recurso: {
        required: "Recurso é obrigatório",
        maxLength: "Recurso só aceita no máximo 500 caracteres"
      },
      observacao: {
        maxLength: "Observação só aceita no máximo 500 caracteres"
      },
      idAtividade: {
        required: "Atividade é obrigatória"
      },
      idTipoPrograma: {
        required: "Tipo Programa é obrigatório"
      }
    };
  }
}
export default Diario;
