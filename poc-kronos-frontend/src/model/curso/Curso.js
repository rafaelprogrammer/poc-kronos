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
        required: "Nome é obrigatório",
        maxLength: "Nome só aceita no máximo 100 caracteres"
      },
      sigla: {
        required: "Sigla é obrigatória",
        maxLength: "Sigla só aceita no máximo 15 caracteres"
      },
      tituloMasculino: {
        required: "Título Masculino é obrigatório",
        maxLength: "Título Masculino só aceita no máximo 100 caracteres"
      },
      tituloFeminino: {
        required: "Título Feminino é obrigatório",
        maxLength: "Título Feminino só aceita no máximo 100 caracteres"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vigência é obrigatório"
      },
      dataFinalVigencia: {
        dataFimMenor:
          "Data Final de Vigência não pode ser menor que a Data Início de Vigência"
      },
      tempoMaximoConclusao: {
        required: "Tempo Máximo de Conclusão é obrigatório",
        numeric: "Somente número"
      },
      tempoMaximoDescontinuo: {
        required: "Tempo Máximo de Descontinue é obrigatório",
        numeric: "Somente número"
      },
      cargaHoraria: {
        required: "Carga Horária é obrigatória",
        numeric: "Somente número"
      },
      notaMinimaAprovacaoDireta: {
        required: "Nota mínima de aprovação direta é obrigatória",
        numeric: "Somente número"
      },
      notaMinimaAprovacaoRecuperacao: {
        required: "Nota mínima de aprovação recuperação é obrigatória",
        numeric: "Somente número"
      },
      idNivel: {
        required: "Nível é obrigatório"
      },
      idTipoRegimeLetivo: {
        required: "Tipo de Regime Letivo é obrigatório"
      },
      idTipoTurno: {
        required: "Tipo de Turno é obrigatório"
      },
      idInstituicao: {
        required: "Instituição é obrigatória"
      },
      idTipoMatrizHorario: {
        required: "Tipo Matriz Horário é obrigatória"
      }
    };
  }
}
export default Curso;
