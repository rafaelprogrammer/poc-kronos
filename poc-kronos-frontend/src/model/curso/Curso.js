class Curso {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.nome = dados ? dados.nome : null;
    this.sigla = dados ? dados.sigla : null;
    this.tituloMasculino = dados ? dados.tituloMasculino : null;
    this.tituloFeminino = dados ? dados.tituloFeminino : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.dataLimiteConclusao = dados ? dados.dataLimiteConclusao : null;
    this.tempoMaximoConclusao = dados ? dados.tempoMaximoConclusao : null;

    this.tempoMaximoDescontinuo = dados ? dados.tempoMaximoDescontinuo : null;
    this.cargaHoraria = dados ? dados.cargaHoraria : null;
    this.notaMinimaAprovacaoDireta = dados
      ? dados.notaMinimaAprovacaoDireta
      : null;
    this.notaMinimaAprovacaoRecuperacao = dados
      ? dados.notaMinimaAprovacaoRecuperacao
      : null;

    this.ativo = dados ? dados.ativo : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.nomeNivel = dados ? dados.nomeNivel : null;
    this.idTipoRegimeLetivo = dados ? dados.idTipoRegimeLetivo : null;
    this.nomeTipoRegimeLetivo = dados ? dados.nomeTipoRegimeLetivo : null;

    this.idTipoTurno = dados ? dados.idTipoTurno : null;
    this.nomeTipoTurno = dados ? dados.NomeTipoTurno : null;

    this.idInstituicao = dados ? dados.idInstituicao : null;

    this.ativo = dados ? dados.ativo : null;
    this.idTipoMatrizHorario = dados ? dados.idTipoMatrizHorario : null;
    this.nomeTipoMatrizHorario = dados ? dados.nomeTipoMatrizHorario : null;
  }

  static validations(required, maxLength, numeric, dataFimMenor) {
    return {
      nome: { required, maxLength: maxLength(100) },
      sigla: { required, maxLength: maxLength(15) },
      tituloMasculino: { required, maxLength: maxLength(100) },
      tituloFeminino: { required, maxLength: maxLength(100) },
      dataInicioVigencia: { required },
      dataFinalVigencia: { dataFimMenor: dataFimMenor("dataInicioVigencia") },
      tempoMaximoConclusao: { required, numeric },
      tempoMaximoDescontinuo: { required, numeric },
      cargaHoraria: { required, numeric },
      notaMinimaAprovacaoDireta: { required, numeric },
      notaMinimaAprovacaoRecuperacao: { required, numeric },
      idNivel: { required },
      idTipoRegimeLetivo: { required },
      idTipoTurno: { required },
      idInstituicao: { required },
      idTipoMatrizHorario: { required }
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
        maxLength: "Sigla s?? aceita no m??ximo 15 caracteres"
      },
      tituloMasculino: {
        required: "T??tulo Masculino ?? obrigat??rio",
        maxLength: "T??tulo Masculino s?? aceita no m??ximo 100 caracteres"
      },
      tituloFeminino: {
        required: "T??tulo Feminino ?? obrigat??rio",
        maxLength: "T??tulo Feminino s?? aceita no m??ximo 100 caracteres"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vig??ncia ?? obrigat??rio"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vig??ncia n??o pode ser menor que a Data In??cio de Vig??ncia"
      },
      tempoMaximoConclusao: {
        required: "Tempo M??ximo de Conclus??o ?? obrigat??rio",
        numeric: "Somente n??mero"
      },
      tempoMaximoDescontinuo: {
        required: "Tempo M??ximo de Descontinue ?? obrigat??rio",
        numeric: "Somente n??mero"
      },
      cargaHoraria: {
        required: "Carga Hor??ria ?? obrigat??ria",
        numeric: "Somente n??mero"
      },
      notaMinimaAprovacaoDireta: {
        required: "Nota m??nima de aprova????o direta ?? obrigat??ria",
        numeric: "Somente n??mero"
      },
      notaMinimaAprovacaoRecuperacao: {
        required: "Nota m??nima de aprova????o recupera????o ?? obrigat??ria",
        numeric: "Somente n??mero"
      },
      idNivel: {
        required: "N??vel ?? obrigat??rio"
      },
      idTipoRegimeLetivo: {
        required: "Tipo de Regime Letivo ?? obrigat??rio"
      },
      idTipoTurno: {
        required: "Tipo de Turno ?? obrigat??rio"
      },
      idInstituicao: {
        required: "Institui????o ?? obrigat??ria"
      },
      idTipoMatrizHorario: {
        required: "Tipo Matriz Hor??rio ?? obrigat??ria"
      }
    };
  }
}
export default Curso;
