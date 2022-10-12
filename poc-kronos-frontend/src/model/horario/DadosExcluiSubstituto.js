class DadosExcluiSubstituto {
  constructor(dados) {
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idTurma = dados ? dados.idTurma : null;
    this.regular = dados ? dados.regular : null;
    this.idTipoRegimeLetivo = dados ? dados.idTipoRegimeLetivo : null;
    this.dataInicialHorario = dados ? dados.dataInicialHorario : null;
    this.dataFinalHorario = dados ? dados.dataFinalHorario : null;
    this.dataInicialSubstituto = dados ? dados.dataInicialSubstituto : null;
    this.dataFinalSubstituto = dados ? dados.dataFinalSubstituto : null;
    this.idFuncionarioHorario = dados ? dados.idFuncionarioHorario : null;
    
  }
}
export default DadosExcluiSubstituto;
