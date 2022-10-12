class Instituicao {
  constructor(dados) {
    this.id = dados.id;
    this.nome = dados.nome;
    this.ativo = dados.ativo;
    this.id_arq_anexo = dados.id_arq_anexo;
    this.id_empresa = dados.id_empresa;
    this.mantenedora = dados.mantenedora;
  }
}
export default Instituicao;
