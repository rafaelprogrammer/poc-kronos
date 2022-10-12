class AtividadeDisciplinaDireito {
  constructor(dados) {
    this.idDisciplinaDireito = dados ? dados.idDisciplinaDireito : null;
    this.idAtividade = dados ? dados.idAtividade : null;
    this.idDireito = dados ? dados.idDireito : null;
    this.codigoDireito = dados ? dados.codigoDireito : null;
    this.descricaoDireito = dados ? dados.descricaoDireito : null;
    this.idsDisciplinaDireito = dados ? dados.idsDisciplinaDireito : null;
  }
}
export default AtividadeDisciplinaDireito;
