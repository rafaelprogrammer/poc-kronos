class Filiacao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataFiliacao = dados ? dados.dataFiliacao : null;
    this.filiacaoAtual = dados ? dados.filiacaoAtual : null;
    this.idPessoaPais = dados ? dados.idPessoaPais : null;
    this.nomePessoaPais = dados ? dados.nomePessoaPais : null;
    this.idPessoaFilho = dados ? dados.idPessoaFilho : null;
    this.nomePessoaFilho = dados ? dados.nomePessoaFilho : null;
    this.idTipoFiliacao = dados ? dados.idTipoFiliacao : null;
    this.nomeTipoFiliacao = dados ? dados.nomeTipoFiliacao : null;
  }

  static validations(required) {
    return {
      dataFiliacao: { required },
      idPessoaPais: { required },
      idPessoaFilho: { required },
      idTipoFiliacao: { required }
    };
  }

  static validationMessages() {
    return {
      dataFiliacao: {
        required: "Data Filiação é obrigatória"
      },
      idPessoaPais: {
        required: "Pessoa é obrigatória"
      },
      idPessoaFilho: {
        required: "Filho é obrigatório"
      },
      idTipoFiliacao: {
        required: "Tipo Filiação é obrigatória"
      }
    };
  }
}
export default Filiacao;
