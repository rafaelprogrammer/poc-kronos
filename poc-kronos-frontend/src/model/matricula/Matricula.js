class Matricula {
  static PRE_MATRICULA() {
    return 1;
  }
  static CANCELADA() {
    return 6;
  }
  static TRANSFERIDA() {
    return 5;
  }
  static VALIDADA() {
    return 2;
  }
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataInicioCurso = dados ? dados.dataInicioCurso : null;
    this.dataConclusaoCurso = dados ? dados.dataConclusaoCurso : null;
    this.data = dados ? dados.data : null;
    this.anoInicioCurso = dados ? dados.anoInicioCurso : null;
    this.semestreInicioCurso = dados ? dados.semestreInicioCurso : null;
    this.dataColacaoGrau = dados ? dados.dataColacaoGrau : null;
    this.anoConclusaoCurso = dados ? dados.anoConclusaoCurso : null;
    this.semestreConclusaoCurso = dados ? dados.semestreConclusaoCurso : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.numeroRegistroPessoa = dados ? dados.numeroRegistroPessoa : null;
    this.cpfPessoa = dados ? dados.cpfPessoa : null;
    this.nomePessoa = dados ? dados.nomePessoa : null;
    this.dataNascimentoPessoa = dados ? dados.dataNascimentoPessoa : null;
    this.idGeneroPessoa = dados ? dados.idGeneroPessoa : null;
    this.idArqAnexoPessoa = dados ? dados.idArqAnexoPessoa : null;
    this.idCurso = dados ? dados.idCurso : null;
    this.nomeCurso = dados ? dados.nomeCurso : null;
    this.idTipoSituacaoMatricula = dados ? dados.idTipoSituacaoMatricula : null;
    this.nomeTipoSituacaoMatricula = dados
      ? dados.nomeTipoSituacaoMatricula
      : null;
    this.idTipoResultado = dados ? dados.idTipoResultado : null;
    this.nomeTipoResultado = dados ? dados.nomeTipoResultado : null;
    this.idEmpresaOrigem = dados ? dados.idEmpresaOrigem : null;
    this.nomeInstituicaoOrigem = dados ? dados.nomeInstituicaoOrigem : null;

  }

  static validations(required, numeric, dataFimMenor) {
    return {
      dataInicioCurso: { required },
      dataConclusaoCurso: { dataFimMenor: dataFimMenor("dataInicioCurso") },
      data: { required },
      anoInicioCurso: { required, numeric },
      semestreInicioCurso: { required, numeric },
      anoConclusaoCurso: { numeric },
      semestreConclusaoCurso: { numeric },
      idPessoa: { required },
      idCurso: { required }
      // idEmpresaOrigem: { required }
    };
  }

  static validationMessages() {
    return {
      dataInicioCurso: {
        required: "Data Início do Curso é obrigatório"
      },
      dataConclusaoCurso: {
        dataFimMenor:
          "Data Conclusão do Curso não pode ser menor que a Data Conclusão do Curso"
      },
      data: {
        required: "Data é obrigatório"
      },
      anoInicioCurso: {
        required: "Ano início é obrigatório",
        numeric: "Somente números"
      },
      semestreInicioCurso: {
        required: "Semestre início é obrigatório",
        numeric: "Somente números"
      },
      anoConclusaoCurso: {
        numeric: "Somente números"
      },
      semestreConclusaoCurso: {
        numeric: "Somente números"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idCurso: {
        required: "Curso é obrigatório"
      }
      // idEmpresaOrigem: {
      //   required: "Empresa origem é obrigatória"
      // }
    };
  }
}
export default Matricula;
