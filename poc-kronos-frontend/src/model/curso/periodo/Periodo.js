class Periodo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.sigla = dados ? dados.sigla : null;
    this.numero = dados ? dados.numero : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.idTipoPeriodo = dados ? dados.idTipoPeriodo : null;
    this.nomeTipoPeriodo = dados ? dados.nomeTipoPeriodo : null;
    this.idFaixaAno = dados ? dados.idFaixaAno : null;
    this.nomeFaixaAno = dados ? dados.nomeFaixaAno : null;
    this.valor = dados ? dados.valor : 0.0;
    this.idNivel = null;
  }

  static validations(required, maxLength, numeric, decimal) {
    return {
      nome: { required, maxLength: maxLength(30) },
      sigla: { required, maxLength: maxLength(10) },
      numero: { required, numeric },
      idCurso: { required },
      idTipoPeriodo: { required },
      idFaixaAno: { required },
      idNivel: { required },
      valor: { required, decimal }
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 30 caracteres"
      },
      sigla: {
        required: "Sigla é obrigatória",
        maxLength: "Sigla só aceita no máximo 10 caracteres"
      },
      numero: {
        required: "Número é obrigatório",
        numeric: "Somente número"
      },
      valor: {
        required: "Valor é obrigatório",
        decimal: "Somente número decimal"
      },
      idNivel: {
        required: "Nível é obrigatório"
      },
      idCurso: {
        required: "Curso é obrigatório"
      },
      idTipoPeriodo: {
        required: "Tipo de Período é obrigatório"
      },
      idFaixaAno: {
        required: "Faixa Ano é obrigatório"
      }
    };
  }
}
export default Periodo;
