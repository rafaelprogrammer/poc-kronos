class Avaliado {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idAvaliacao = dados ? dados.idAvaliacao : null;
    this.idCredito = dados ? dados.idCredito : null;
    this.idGrupoAvaliacao = dados ? dados.idGrupoAvaliacao : null;
    this.nota = dados ? dados.nota : null;
    this.idMencao = dados ? dados.idMencao : null;
    this.descarte = dados ? dados.descarte : null;
    this.motivoDescarte = dados ? dados.motivoDescarte : null;
    this.numeroRegistro = dados ? dados.numeroRegistro : null;
    this.nomeAvaliado = dados ? dados.nomeAvaliado : null;
    this.convertMencoes(dados ? dados.mencoes : []);
    this.situacao = dados ? dados.situacao : null;
  }

  convertMencoes(mencoes) {
    this.mencoes = "";
    mencoes.map(m => {
      this.mencoes += m + ", ";
    });
    this.mencoes = this.mencoes.substring(0, this.mencoes.length - 2);
  }

}
export default Avaliado;
