class SubFaseResumo {
  constructor(dados) {
    this.numero = dados ? dados.numero : null;
    this.sigla = dados ? dados.sigla : null;
    this.siglaFase = dados ? dados.siglaFase : null;
    this.numeroFase = dados ? dados.numeroFase : null;
    this.dataInicio = null;
    this.dataFim = null;
  }
}
export default SubFaseResumo;
