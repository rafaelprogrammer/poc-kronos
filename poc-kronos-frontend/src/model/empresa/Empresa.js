class Empresa {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.cnpj = dados ? dados.cnpj : null;
    this.inscricaoEstadual = dados ? dados.inscricaoEstadual : null;
    this.emailContato = dados ? dados.emailContato : null;
    this.nomeContato = dados ? dados.nomeContato : null;
    this.nomeFantasia = dados ? dados.nomeFantasia : null;
    this.razaoSocial = dados ? dados.razaoSocial : null;
    this.site = dados ? dados.site : null;
  }
}
export default Empresa;
