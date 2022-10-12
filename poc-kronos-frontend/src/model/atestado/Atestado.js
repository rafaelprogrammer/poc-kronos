class Atestado {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.dataEntrega = dados ? dados.dataEntrega : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idTipoFalta = dados ? dados.idTipoFalta : null;
    this.nomeTipoFalta = dados ? dados.nomeTipoFalta : null;
    this.idArqAnexo = dados ? dados.idArqAnexo : null;
    this.dataInicioVigencia = dados ? dados.dataInicioVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.numeroDias = dados ? dados.numeroDias : null;
  }

  static validations(required, dataFimMenor) {
    return {
      dataEntrega: { required },
      idPessoa: { required },
      idTipoFalta: { required },
      dataInicioVigencia: { required },
      dataFinalVigencia: {
        required,
        dataFimMenor: dataFimMenor("dataInicioVigencia")
      }
    };
  }

  static validationMessages() {
    return {
      dataEntrega: {
        required: "Data Entrega é obrigatória"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idTipoFalta: {
        required: "Tipo Falta é obrigatória"
      },
      dataInicioVigencia: {
        required: "Data Inicio Vigência é obrigatória"
      },
      dataFinalVigencia: {
        required: "Data Final Vigência é obrigatório",
        dataFimMenor: "Data Final Vigência não pode ser menor que a Data Inicio Vigência"
      }
    };
  }
}
export default Atestado;
