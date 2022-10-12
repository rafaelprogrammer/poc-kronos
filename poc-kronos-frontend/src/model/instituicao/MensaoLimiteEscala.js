class MensaoLimiteEscala {
  constructor(dados) {
    this.idLimite = dados ? dados.idLimite : null;
    this.idMensao = dados ? dados.idMensao : null;
    this.minimo = dados ? dados.minimo : null;
    this.maximo = dados ? dados.maximo : null;
    this.nome = dados ? dados.nome : null;
    this.simbolo = dados ? dados.simbolo : null;
  }
}
export default MensaoLimiteEscala;
