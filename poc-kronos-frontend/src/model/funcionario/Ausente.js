class Ausente {
  constructor(dados) {
    this.idTipoPeriodoPonto = dados ? dados.idTipoPeriodoPonto : null;
    this.nomeTipoPeriodoPonto = dados ? dados.nomeTipoPeriodoPonto : null;
    this.dataGeracao = dados ? dados.dataGeracao : null;
    this.horaInicialPrevista = dados ? dados.horaInicialPrevista : null;
    this.diaDaSemana = dados ? dados.diaDaSemana : null;
    this.idFuncionario = null;
    this.ano = null;
    this.mes = null;
    this.sabadoSuprimido = false;
    this.domingoSuprimido = false;
  }
}
export default Ausente;
