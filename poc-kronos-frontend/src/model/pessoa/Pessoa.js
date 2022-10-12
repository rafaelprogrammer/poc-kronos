class Pessoa {
  constructor(dadosPessoa) {
    this.id = dadosPessoa ? dadosPessoa.id : null;
    this.nome = dadosPessoa ? dadosPessoa.nome : null;
    this.cpf = dadosPessoa ? dadosPessoa.cpf : null;
    this.numeroRegistro = dadosPessoa ? dadosPessoa.numeroRegistro : null;
    this.dataNascimento = dadosPessoa ? dadosPessoa.dataNascimento : null;
    this.email = dadosPessoa ? dadosPessoa.email : null;
    this.idGenero = dadosPessoa ? dadosPessoa.idGenero : null;
    this.idCidade = dadosPessoa ? dadosPessoa.idCidade : null;
    this.idArqAnexo = dadosPessoa ? dadosPessoa.idArqAnexo : null;
    this.nomeUsual = dadosPessoa ? dadosPessoa.nomeUsual : null;
    this.nomeSocial = dadosPessoa ? dadosPessoa.nomeSocial : null;
    this.idInstituicao = dadosPessoa ? dadosPessoa.idInstituicao : null;
    this.bancoTalento = dadosPessoa ? dadosPessoa.bancoTalento : null;
    this.bancoTalentoStr = dadosPessoa
      ? dadosPessoa.bancoTalento === true
        ? "Sim"
        : "Não"
      : null;
    this.grauComportamento = dadosPessoa ? dadosPessoa.grauComportamento : null;
    this.idEstado = null;
  }

  static validations(required, cpf, email, decimal, maxValue) {
    return {
      nome: { required },
      cpf: { required, cpf },
      idCidade: { required },
      idEstado: { required },
      dataNascimento: { required },
      idGenero: { required },
      nomeUsual: { required },
      nomeSocial: { required },
      email: { required, email },
      grauComportamento: { required, decimal, maxValue: maxValue(10.0) }
    };
  }

  static validationMessages() {
    return {
      idEstado: {
        required: "Estado é obrigatório"
      },
      nome: {
        required: "Nome é obrigatório"
      },
      idCidade: {
        required: "Cidade é obrigatório"
      },
      dataNascimento: {
        required: "Data de Nascimento é obrigatório"
      },
      cpf: {
        required: "CPF é obrigatória",
        minLength: "Tamanho do cpf deve ser 11 characters",
        cpf: "Cpf inválido"
      },
      idGenero: {
        required: "Gênero é obrigatório"
      },
      nomeUsual: {
        required: "Nome Usuario é obrigatório"
      },
      nomeSocial: {
        required: "Nome Social é obrigatório"
      },
      email: {
        required: "Email é obrigatório",
        email: "Email inválido"
      },
      grauComportamento: {
        required: "Grau Comportamento é obrigatório",
        decimal: "Somente decimal. Ex: 5.9",
        maxValue: "Valor máximo 10.0"
      }
    };
  }
}
export default Pessoa;
