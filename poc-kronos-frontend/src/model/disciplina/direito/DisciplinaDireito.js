import Vue from "vue";

class DisciplinaDireito {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idDireito = dados ? dados.idDireito : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : null;
    this.bncc = dados ? dados.bncc : null;
    this.idsDireito = dados ? dados.idsDireito : null;
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
export default DisciplinaDireito;
