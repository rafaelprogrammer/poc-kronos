class Arquivo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.legenda = dados ? dados.legenda : null;
    this.contentType = dados ? dados.contentType : null;
    this.nome = dados ? dados.nome : null;
    this.idTipoArquivo = dados ? dados.idTipoArquivo : null;
    this.nomeTipoArquivo = dados ? dados.nomeTipoArquivo : null;
    this.idTipoConteudo = dados ? dados.idTipoConteudo : null;
    this.nomeTipoConteudo = dados ? dados.nomeTipoConteudo : null;
    this.dadosBase64 = dados ? dados.dadosBase64 : null;
    this.dados = null;
  }

  static validations(required, sizeFile) {
    return {
      //SizeFile = 5MB
      dados: { required, sizeFile: sizeFile(5242880) },
      // legenda: { required },
      idPessoa: { required }
      // nome: { required },
      // idTipoArquivo: { required },
      // idTipoConteudo: { required }
    };
  }

  static validationMessages() {
    return {
      dados: {
        required: "Upload é obrigatório",
        sizeFile: "O tamanho máximo do arquivo deve ser de 5MB"
      },
      // nome: {
      //   required: "Nome é obrigatório"
      // },
      // legenda: {
      //   required: "Legenda é obrigatória"
      // },
      // idTipoArquivo: {
      //   required: "Tipo de Arquivo é obrigatório"
      // },
      // idTipoConteudo: {
      //   required: "Tipo de Conteúdo é obrigatório"
      // },
      idPessoa: {
        required: "Pessoa é obrigatória"
      }
    };
  }
}
export default Arquivo;
