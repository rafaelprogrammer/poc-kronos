class Titulacao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.data = dados ? dados.data : null;
    this.nomeTitulo = dados ? dados.nomeTitulo : null;
    this.idArqAnexo = dados ? dados.idArqAnexo : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idEmpresa = dados ? dados.idEmpresa : null;
    this.nomeEmpresa = dados ? dados.nomeEmpresa : null;
    this.idTipoTitulo = dados ? dados.idTipoTitulo : null;
    this.nomeTipoTitulo = dados ? dados.nomeTipoTitulo : null;
  }

  static validations(required, maxLength) {
    return {
      data: { required },
      nomeTitulo: { required, maxLength: maxLength(100) },
      idPessoa: { required },
      idEmpresa: { required },
      idTipoTitulo: { required }
    };
  }

  static validationMessages() {
    return {
      data: {
        required: "Data é obrigatória"
      },
      nomeTitulo: {
        required: "Nome Título é obrigatório",
        maxLength: "Nome Título só aceita no máximo 100 caracteres"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idEmpresa: {
        required: "Empresa é obrigatório"
      },
      idTipoTitulo: {
        required: "Tipo de Título é obrigatório"
      }
    };
  }
}
export default Titulacao;
