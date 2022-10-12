import Vue from "vue";

class DisciplinaCompetencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idCompetencia = dados ? dados.idCompetencia : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : null;
    this.bncc = dados ? dados.bncc : null;
    this.geral = dados ? dados.geral : null;
    this.idsCompetencias = dados ? dados.idsCompetencias : null;
    this.idSubFase = dados ? dados.idSubFase : null;
    this.siglaSubFase = dados ? dados.siglaSubFase : null;
  }

  static validarDataFinalVigencia(dataInicioVigencia, dataFinalVigencia) {
    const dtInicio = new Date(
      dataInicioVigencia
        .split("/")
        .reverse()
        .join("/")
    );
    const dtFim = new Date(
      dataFinalVigencia
        .split("/")
        .reverse()
        .join("/")
    );
    if (!Vue.moment(dtFim).isBefore(dtInicio)) {
      return false;
    }
    return true;
  }
}
export default DisciplinaCompetencia;
