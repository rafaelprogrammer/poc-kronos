class TurmaDiarioFrequencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.nome = dados ? dados.nome : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.idPeriodo = dados ? dados.idPeriodo : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.idFaixaAno = dados ? dados.idFaixaAno : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.anoTurma = dados ? dados.anoTurma : null;
  }
}
export default TurmaDiarioFrequencia;
