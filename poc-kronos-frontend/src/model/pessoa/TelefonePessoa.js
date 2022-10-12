class TelefonePessoa {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.principal = dados ? dados.principal : null;
    this.whatApp = dados ? dados.whatApp : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idCidade = dados ? dados.idCidade : null;
    this.nomeCidade = dados ? dados.nomeCidade : null;
    this.ddd = dados ? dados.ddd : null;
    this.idOperadora = dados ? dados.idOperadora : null;
    this.nomeOperadora = dados ? dados.nomeOperadora : null;
    this.idTipoUso = dados ? dados.idTipoUso : null;
    this.nomeTipoUso = dados ? dados.nomeTipoUso : null;
    this.idTipoTelefone = dados ? dados.idTipoTelefone : null;
    this.nomeTipoTelefone = dados ? dados.nomeTipoTelefone : null;
  }

  static validations(required, maxLength, numeric) {
    return {
      numero: {
        required,
        maxLength: maxLength(10),
        numeric
      },
      idPessoa: { required },
      idCidade: { required },
      idOperadora: { required },
      idTipoUso: { required },
      idTipoTelefone: { required }
    };
  }

  static validationMessages() {
    return {
      numero: {
        required: "Número é obrigatório",
        maxLength: "Número deve ter no máximo 10 digítos",
        numeric: "Número só aceita digítos"
      },
      idCidade: {
        required: "Cidade é obrigatória"
      },
      idOperadora: {
        required: "Operadora é obrigatória"
      },
      idTipoUso: {
        required: "Uso é obrigatório"
      },
      idTipoTelefone: {
        required: "Tipo de Telefone é obrigatório"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      }
    };
  }
}
export default TelefonePessoa;
