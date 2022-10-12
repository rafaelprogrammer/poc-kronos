class Objetivo {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idCampoExperiencia = dados ? dados.idCampoExperiencia : null;
    this.camposExperiencia = dados ? dados.camposExperiencia : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : null;
    this.bncc = dados ? dados.bncc : null;
    this.idFaixaAno = dados ? dados.idFaixaAno : null;
    this.faixaAno = dados ? dados.faixaAno : null;
  }

  static validations(required, maxLength) {
    return {
      descricao: { required, maxLength: maxLength(400) },
      codigo: { required, maxLength: maxLength(10) },
      idCampoExperiencia: { required },
      idFaixaAno: { required }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        required: "Descrição é obrigatória",
        maxLength: "Descrição só aceita no máximo 400 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        maxLength: "Código só aceita no máximo 10 caracteres"
      },
      idCampoExperiencia: {
        required: "Campo Experiência é obrigatório"
      },
      idFaixaAno: {
        required: "Faixa/Ano é obrigatório"
      }
    };
  }
}
export default Objetivo;
