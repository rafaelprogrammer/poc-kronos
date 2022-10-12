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
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 100 caracteres"
      },
      sigla: {
        required: "Sigla é obrigatória",
        maxLength: "Sigla só aceita no máximo 200 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        numeric: "Somente números"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vigência é obrigatório"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vigência não pode ser menor que a Data Início de Vigência"
      },
      cargaHorariaTotal: {
        required: "Carga Horária Total é obrigatória",
        numeric: "Somente números"
      },
      cargaHorariaDistancia: {
        required: "Carga Horária Distância é obrigatória",
        numeric: "Somente números"
      },
      cargaHorariaPresencial: {
        required: "Carga Horária Presencial é obrigatória",
        numeric: "Somente números"
      },
      valor: {
        required: "Valor é obrigatório",
        decimal: "Somente valor decimal"
      },
      idTipoDisciplina: {
        required: "Disciplina é obrigatória"
      },
      idTipoRegimeLetivo: {
        required: "Tipo Regime Letivo é obrigatório"
      },
      idPeriodo: {
        required: "Período é obrigatório"
      }
    };
  }
}
export default Disciplina;
