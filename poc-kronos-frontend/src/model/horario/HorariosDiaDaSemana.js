import HoraAtividadeDiaDaSemana from "./HoraAtividadeDiaDaSemana";
class HorariosDiaDaSemana {
  constructor(dados = "") {
    this.domingo = this.criar(dados.domingo);
    this.segunda = this.criar(dados.segunda);
    this.terca = this.criar(dados.terca);
    this.quarta = this.criar(dados.quarta);
    this.quinta = this.criar(dados.quinta);
    this.sexta = this.criar(dados.sexta);
    this.sabado = this.criar(dados.sabado);
  }

  isEmpty() {
    return (
      this.domingo.length === 0 &&
      this.segunda.length === 0 &&
      this.terca.length === 0 &&
      this.quarta.length === 0 &&
      this.quinta.length === 0 &&
      this.sexta.length === 0 &&
      this.sabado.length === 0
    );
  }

  criar(listaDoDiaDaSemana) {
    let horasAtividadeDiaDaSemana = [];
    if (listaDoDiaDaSemana && listaDoDiaDaSemana.map) {
      listaDoDiaDaSemana.map(s => {
        horasAtividadeDiaDaSemana.push(new HoraAtividadeDiaDaSemana(s));
      });
    }
    return horasAtividadeDiaDaSemana;
  }
}
export default HorariosDiaDaSemana;
