class MatriculaCanceladaTransferida {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.numeroRegistroPessoa = dados ? dados.numeroRegistroPessoa : null;
    this.cpfPessoa = dados ? dados.cpfPessoa : null;
    this.nomePessoa = dados ? dados.nomePessoa : null;
    this.idContrato = dados ? dados.idContrato : null;
    this.numeroContrato = dados ? dados.numeroContrato : null;
    this.nomeCurso = dados ? dados.nomeCurso : null;
    this.nomePeriodo = dados ? dados.nomePeriodo : null;
    this.dataFinalAnoLetivo = dados ? dados.dataFinalAnoLetivo : null;
    this.dataInformada = dados ? dados.dataInformada : null;
    this.dataContrato = dados ? dados.dataContrato : null;
    this.anoContrato = dados ? dados.anoContrato : null;
  }
}
export default MatriculaCanceladaTransferida;
