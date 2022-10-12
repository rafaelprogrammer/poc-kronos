class FaseExecucaoResumo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.siglaFase = dados ? dados.siglaFase : null;
    this.numeroFase = dados ? dados.numeroFase : null;
  }

}
export default FaseExecucaoResumo;
