class ResultadoPesquisa {
  constructor(dadosPaginacao) {
    this.total = dadosPaginacao ? dadosPaginacao.total : null;
    this.dados = dadosPaginacao ? dadosPaginacao.dados : null;
  }
}
export default ResultadoPesquisa;
