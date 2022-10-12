class PessoaResumo {
  constructor(dadosPessoa) {
    this.id = dadosPessoa ? dadosPessoa.id : null;
    this.nome = dadosPessoa ? dadosPessoa.nome : null;
    this.cpf = dadosPessoa ? dadosPessoa.cpf : null;
    this.dataNascimento = dadosPessoa ? dadosPessoa.dataNascimento : null;
    this.numeroRegistro = dadosPessoa ? dadosPessoa.numeroRegistro : null;
    this.idMatricula = dadosPessoa ? dadosPessoa.idMatricula : null;
    this.idMatricula = dadosPessoa ? dadosPessoa.idMatricula : null;
    this.idGenero = dadosPessoa ? dadosPessoa.idGenero : null;
    this.grauComportamento = dadosPessoa ? dadosPessoa.grauComportamento : null;
  }

}
export default PessoaResumo;
