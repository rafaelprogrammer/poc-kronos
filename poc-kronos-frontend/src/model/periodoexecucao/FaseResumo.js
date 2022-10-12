class FaseResumo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.sigla = dados ? dados.sigla : null;
    this.dataInicio = null;
    this.dataFim = null;
  }
}
export default FaseResumo;
