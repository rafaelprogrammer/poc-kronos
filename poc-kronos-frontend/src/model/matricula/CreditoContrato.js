class CreditoContrato {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idContrato = dados ? dados.idContrato : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idMatricula = dados ? dados.idMatricula : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.disciplinaObrigatoria = dados ? dados.disciplinaObrigatoria : null;
    this.nomeDisciplina = dados ? dados.nomeDisciplina : null;
    this.cargaHorariaTotal = dados ? dados.cargaHorariaTotal : null;
    this.cargaHorariaPresencial = dados ? dados.cargaHorariaPresencial : null;
    this.cargaHorariaDistancia = dados ? dados.cargaHorariaDistancia : null;
    this.obrigatoria = dados ? dados.obrigatoria : null;
    this.valor = dados ? dados.valor : null;
    this.siglaPeriodo = dados ? dados.siglaPeriodo : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.anoTurma = dados ? dados.anoTurma : null;
    this.siglaTurma = dados ? dados.siglaTurma : null;
    this.idTipoCredito = dados ? dados.idTipoCredito : null;
    this.nomeTipoCredito = dados ? dados.nomeTipoCredito : null;
    this.idTipoPrograma = dados ? dados.idTipoPrograma : null;
    this.nomeTipoPrograma = dados ? dados.nomeTipoPrograma : null;
    this.pendencia = dados ? dados.pendencia : null;
  }

}
export default CreditoContrato;
