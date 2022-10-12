class Responsavel {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicio = dados ? dados.dataInicio : null;
    this.dataFim = dados ? dados.dataFim : null;
    this.prioridade = dados ? dados.prioridade : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idPessoaResponsavel = dados ? dados.idPessoaResponsavel : null;
    this.nomePessoaResponsavel = dados ? dados.nomePessoaResponsavel : null;
    this.idTipoResponsavel = dados ? dados.idTipoResponsavel : null;
    this.nomeTipoResponsavel = dados ? dados.nomeTipoResponsavel : null;
  }

  static validations(required, dataFimMenor, maxValue, numeric) {
    return {
      dataInicio: { required },
      dataFim: { dataFimMenor: dataFimMenor("dataInicio") },
      prioridade: { required, numeric, maxValue: maxValue(5) },
      idPessoa: { required },
      idPessoaResponsavel: { required },
      idTipoResponsavel: { required }
    };
  }

  static validationMessages() {
    return {
      dataInicio: {
        required: "Data Início é obrigatória",
        dataFimMenor: "Data Fim não pode ser menor que Data Inicial"
      },
      dataFim: {
        dataFimMenor: "Data Fim não pode ser menor que Data Inicial"
      },
      prioridade: {
        required: "Prioridade é obrigatório",
        numeric: "Somente número",
        maxValue: "Valor máximo 5"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idPessoaResponsavel: {
        required: "Pessoa Responsável é obrigatória"
      },
      idTipoResponsavel: {
        required: "Tipo Responsável é obrigatório"
      }
    };
  }
}
export default Responsavel;
