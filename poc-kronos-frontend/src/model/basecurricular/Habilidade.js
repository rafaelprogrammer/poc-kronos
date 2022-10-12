class Habilidade {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idNivel = dados ? dados.idNivel : null;
    this.nivel = dados ? dados.nivel : null;
    this.idComponente = dados ? dados.idComponente : null;
    this.componente = dados ? dados.componente : null;
    this.descricao = dados ? dados.descricao : null;
    this.codigo = dados ? dados.codigo : null;
    this.ativo = dados ? dados.ativo : true;
    this.bncc = dados ? dados.bncc : null;
    this.eixo = dados ? dados.eixo : null;
    this.campoAtuacao = dados ? dados.campoAtuacao : null;
    this.praticaLinguagem = dados ? dados.praticaLinguagem : null;
    this.objetoConhecimento = dados ? dados.objetoConhecimento : null;
    this.unidadeTematica = dados ? dados.unidadeTematica : null;
    this.idsCompetencias = dados ? dados.idsCompetencias : [];
    this.idsFaixasAnos = dados ? dados.idsFaixasAnos : null;
  }

  static validations(required, maxLength) {
    return {
      descricao: { required, maxLength: maxLength(1500) },
      codigo: { required, maxLength: maxLength(5) },
      idNivel: { required },
      // idsCompetencias: { required },
      idsFaixasAnos: { required },
      eixo: { maxLength: maxLength(100) },
      campoAtuacao: { maxLength: maxLength(300) },
      praticaLinguagem: { maxLength: maxLength(300) },
      objetoConhecimento: { maxLength: maxLength(500) },
      unidadeTematica: { maxLength: maxLength(300) }
    };
  }

  static validationMessages() {
    return {
      descricao: {
        required: "Descrição é obrigatória",
        maxLength: "Descrição só aceita no máximo 500 caracteres"
      },
      codigo: {
        required: "Código é obrigatório",
        maxLength: "Código só aceita no máximo 5 caracteres"
      },
      idNivel: {
        required: "Nível é obrigatório"
      },
      // idsCompetencias: {
      //   required: "Competência é obrigatória"
      // },
      idsFaixasAnos: {
        required: "Faixa/Ano é obrigatório"
      }
    };
  }
}
export default Habilidade;
