class ConvenioContrato {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.cnpj = dados ? dados.cnpj : null;
    this.empresa = dados ? dados.empresa : null;
    this.tipoDesconto = dados ? dados.tipoDesconto : null;
    this.idEmpresa = dados ? dados.idEmpresa : null;
    this.idInstitucicao = dados ? dados.idInstitucicao : null;
    this.percentualDesconto = dados ? dados.percentualDesconto : null;
  }
}
export default ConvenioContrato;
