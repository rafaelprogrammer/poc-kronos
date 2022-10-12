class FuncionarioFuncao {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.funcaoRegistro = dados ? dados.funcaoRegistro : null;
    this.idTipoCategoriaFuncao = dados ? dados.idTipoCategoriaFuncao : null;
    this.idTipoFuncao = dados ? dados.idTipoFuncao : null;
    this.funcao = dados ? dados.funcao : null;
    this.categoria = dados ? dados.categoria : null;
    this.ativo = dados ? dados.ativo : null;
    this.nomeTipoFuncao = dados ? dados.nomeTipoFuncao : null;
    this.funcaoSelecionada = dados
      ? dados.idFuncionario
        ? true
        : false
      : false;
  }
}
export default FuncionarioFuncao;
