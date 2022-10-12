class AtividadeDisciplinaHabilidade {
  constructor(dados) {
    this.idDisciplinaHabilidade = dados ? dados.idDisciplinaHabilidade : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idHabilidade = dados ? dados.idHabilidade : null;
    this.codigoHabilidade = dados ? dados.codigoHabilidade : null;
    this.descricaoHabilidade = dados ? dados.descricaoHabilidade : null;
    this.idsDisciplinaCompetencia = dados
      ? dados.idsDisciplinaHabilidade
      : null;
  }
}
export default AtividadeDisciplinaHabilidade;
