class AtividadeDisciplinaObjetivo {
  constructor(dados) {
    this.idDisciplinaObjetivo = dados ? dados.idDisciplinaObjetivo : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idObjetivo = dados ? dados.idObjetivo : null;
    this.codigoObjetivo = dados ? dados.codigoObjetivo : null;
    this.descricaoObjetivo = dados ? dados.descricaoObjetivo : null;
    this.idsDisciplinaObjetivo = dados ? dados.idsDisciplinaObjetivo : null;
  }
}
export default AtividadeDisciplinaObjetivo;
