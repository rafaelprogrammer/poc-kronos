class Cidade {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.area = dados ? dados.area : null;
    this.codAreaTel = dados ? dados.codAreaTel : null;
    this.codigoIbge = dados ? dados.codigoIbge : null;
    this.idEstado = dados ? dados.idEstado : null;
  }
}
export default Cidade;
