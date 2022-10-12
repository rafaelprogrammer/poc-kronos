class HoraAtividadeDiaDaSemana {
  constructor(dados) {
    this.idHorario = dados ? dados.idHorario : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.horaInicial = dados ? dados.horaInicial : null;
    this.horaFinal = dados ? dados.horaFinal : null;
    this.bloqueado = dados ? dados.bloqueado : true;
    this.selecionado = dados ? dados.selecionado : false;
    this.situacao = dados ? dados.situacao : null;
  }
}
export default HoraAtividadeDiaDaSemana;
