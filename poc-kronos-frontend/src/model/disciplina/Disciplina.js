class Disciplina {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.codigo = dados ? dados.codigo : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.nome = dados ? dados.nome : null;
    this.sigla = dados ? dados.sigla : null;
    this.cargaHorariaTotal = dados ? dados.cargaHorariaTotal : null;
    this.cargaHorariaPresencial = dados ? dados.cargaHorariaPresencial : null;
    this.cargaHorariaDistancia = dados ? dados.cargaHorariaDistancia : null;
    this.ativa = dados ? dados.ativa : null;
    this.obrigatoria = dados ? dados.obrigatoria : null;
    this.valor = dados ? dados.valor : null;
    this.idTipoDisciplina = dados ? dados.idTipoDisciplina : null;
    this.nomeTipoDisciplina = dados ? dados.nomeTipoDisciplina : null;
    this.idTipoRegimeLetivo = dados ? dados.idTipoRegimeLetivo : null;
    this.nomeTipoRegimeLetivo = dados ? dados.nomeTipoRegimeLetivo : null;
    this.idPeriodo = dados ? dados.idPeriodo : null;
    this.siglaPeriodo = dados ? dados.siglaPeriodo : null;
    this.idComponente = dados ? dados.idComponente : null;
    this.preRequisitos = dados ? dados.preRequisitos : null;
    this.idDisciplinaUnificacao = dados ? dados.idDisciplinaUnificacao : null;
    this.siglaDisciplinaUnificacao = dados
      ? dados.siglaDisciplinaUnificacao
      : null;
  }

  static validations(required, maxLength, numeric, decimal, dataFimMenor) {
    return {
      codigo: { required, numeric },
      nome: { required, maxLength: maxLength(100) },
      sigla: { required, maxLength: maxLength(20) },
      cargaHorariaTotal: { required, numeric },
      cargaHorariaPresencial: { required, numeric },
      cargaHorariaDistancia: { required, numeric },
      dataInicioVigencia: { required },
      dataFinalVigencia: { dataFimMenor: dataFimMenor("dataInicioVigencia") },
      valor: { required, decimal },
      idTipoDisciplina: { required },
      idTipoRegimeLetivo: { required },
      idPeriodo: { required }
    };
  }

  static validationMessages() {
    return {
      nome: {
        required: "Nome ?? obrigat??rio",
        maxLength: "Nome s?? aceita no m??ximo 100 caracteres"
      },
      sigla: {
        required: "Sigla ?? obrigat??ria",
        maxLength: "Sigla s?? aceita no m??ximo 200 caracteres"
      },
      codigo: {
        required: "C??digo ?? obrigat??rio",
        numeric: "Somente n??meros"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vig??ncia ?? obrigat??rio"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vig??ncia n??o pode ser menor que a Data In??cio de Vig??ncia"
      },
      cargaHorariaTotal: {
        required: "Carga Hor??ria Total ?? obrigat??ria",
        numeric: "Somente n??meros"
      },
      cargaHorariaDistancia: {
        required: "Carga Hor??ria Dist??ncia ?? obrigat??ria",
        numeric: "Somente n??meros"
      },
      cargaHorariaPresencial: {
        required: "Carga Hor??ria Presencial ?? obrigat??ria",
        numeric: "Somente n??meros"
      },
      valor: {
        required: "Valor ?? obrigat??rio",
        decimal: "Somente valor decimal"
      },
      idTipoDisciplina: {
        required: "Disciplina ?? obrigat??ria"
      },
      idTipoRegimeLetivo: {
        required: "Tipo Regime Letivo ?? obrigat??rio"
      },
      idPeriodo: {
        required: "Per??odo ?? obrigat??rio"
      }
    };
  }
}
export default Disciplina;
