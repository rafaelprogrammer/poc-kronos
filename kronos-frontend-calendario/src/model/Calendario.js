class Calendario {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFinal = dados ? dados.dataFinal : null;
  }
}
export default Calendario;
