class Funcionario {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataAdmissao = dados ? dados.dataAdmissao : null;
    this.dataDemissao = dados ? dados.dataDemissao : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.idTipoCategoriaFuncao = dados ? dados.idTipoCategoriaFuncao : null;
    this.idTipoFuncao = dados ? dados.idTipoFuncao : null;
    this.funcao = dados ? dados.funcao : null;
    this.email = dados ? dados.email : null;
    this.nome = dados ? dados.nome : null;
    this.cpf = dados ? dados.cpf : null;
    this.numeroRegistro = dados ? dados.numeroRegistro : null;
    this.ativo = null;
  }

  static validations(required) {
    return {
      dataAdmissao: { required }
    };
  }

  static validationMessages() {
    return {
      dataAdmissao: {
        required: "Data Admissão é obrigatória"
      }
    };
  }
}
export default Funcionario;
