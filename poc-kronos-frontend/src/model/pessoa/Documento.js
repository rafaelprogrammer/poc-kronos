class Documento {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataExpedicao = dados ? dados.dataExpedicao : null;
    this.folha = dados ? dados.folha : null;
    this.livro = dados ? dados.livro : null;
    this.nomeCartorio = dados ? dados.nomeCartorio : null;
    this.orgaoExpeditor = dados ? dados.orgaoExpeditor : null;
    this.numero = dados ? dados.numero : null;
    this.secao = dados ? dados.secao : null;
    this.serie = dados ? dados.serie : null;
    this.zona = dados ? dados.zona : null;
    this.idArqAnexo = dados ? dados.idArqAnexo : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idCidade = dados ? dados.idCidade : null;
    this.nomeCidade = dados ? dados.nomeCidade : null;
    this.idTipo = dados ? dados.idTipo : null;
    this.nomeTipo = dados ? dados.nomeTipo : null;
    this.idEstado = null;
  }

  static validations(required) {
    return {
      idEstado: { required },
      dataExpedicao: { required },
      idPessoa: { required },
      idCidade: { required },
      idTipo: { required }
    };
  }

  static validationMessages() {
    return {
      idEstado: {
        required: "Estado é obrigatório"
      },
      dataExpedicao: {
        required: "Data de Expedição é obrigatória"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idCidade: {
        required: "Cidade do Cartório é obrigatório"
      },
      idTipo: {
        required: "Tipo de Documento é obrigatório"
      }
    };
  }
}
export default Documento;
