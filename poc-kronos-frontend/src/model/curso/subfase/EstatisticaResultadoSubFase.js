class EstatisticaResultadoSubFase {
  constructor(dados) {
    this.qtdAulasDadas = dados ? dados.qtdAulasDadas : null;
    this.nrHabilidadesTrabalhadas = dados
      ? dados.nrHabilidadesTrabalhadas
      : null;
    this.nrHabilidadesAvaliadas = dados ? dados.nrHabilidadesAvaliadas : null;
  }
}
export default EstatisticaResultadoSubFase;
