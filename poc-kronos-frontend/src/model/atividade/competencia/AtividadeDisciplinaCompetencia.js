class AtividadeDisciplinaCompetencia {
  constructor(dados) {
    this.idDisciplinaCompetencia = dados ? dados.idDisciplinaCompetencia : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idCompetencia = dados ? dados.idCompetencia : null;
    this.codigoCompetencia = dados ? dados.codigoCompetencia : null;
    this.descricaoCompetencia = dados ? dados.descricaoCompetencia : null;
    this.idsDisciplinaCompetencia = dados
      ? dados.idsDisciplinaCompetencia
      : null;
  }
}
export default AtividadeDisciplinaCompetencia;
