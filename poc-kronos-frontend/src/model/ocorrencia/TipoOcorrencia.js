class TipoOcorrencia {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.fator = dados ? dados.fator : null;
    this.nomeFator = dados ? dados.nomeFator : null;
    this.codigo = dados ? dados.codigo : null;
    this.descricao = dados ? dados.descricao : null;
    this.valor = dados ? dados.valor : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.qtdOcorrencia = dados ? dados.qtdOcorrencia : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
  }

  static validations(required, maxLength, decimal, dataFimMenor, maxValue) {
    return {
      descricao: { required, maxLength: maxLength(100) },
      fator: { required },
      dataInicioVigencia: { required },
      dataFinalVigencia: { dataFimMenor: dataFimMenor("dataInicioVigencia") },
      valor: { required, decimal, maxValue: maxValue(10.0) }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        required: "Descrição é obrigatório",
        maxLength: "Descrição só aceita no máximo 100 caracteres"
      },
      fator: {
        required: "Fator é obrigatório"
      },
      dataInicioVigencia: {
        required: "Data Início Vigência é obrigatória"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final Vigência do Curso não pode ser menor que a Data Início Vigência"
      },
      valor: {
        required: "Valor é obrigatório",
        decimal: "Somente número decimal. Ex: 9 ou 8,5 ou 5,66",
        maxValue: "Valor máximo 10,0"
      }
    };
  }
}
export default TipoOcorrencia;
