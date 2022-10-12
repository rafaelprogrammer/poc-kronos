class DisciplinaPreRequisito {
  constructor(dados) {
    this.idDisciplina = dados ? dados.idDisciplina : null;
    this.idsDisciplinasPreRequisitos = dados
      ? dados.idsDisciplinasPreRequisitos
      : null;
  }

  static validations(required) {
    return {
      idDisciplina: { required },
      idsDisciplinasPreRequisitos: { required }
    };
  }

  static validationMessages() {
    return {
      idDisciplina: {
        required: "Disciplina é obrigatória"
      },
      idsDisciplinasPreRequisitos: {
        required: "Disciplina Pré-Requisito é obrigatório"
      }
    };
  }
}
export default DisciplinaPreRequisito;
