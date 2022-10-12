class Usuario {
  constructor(dadoUsuario) {
    this.id = dadoUsuario ? dadoUsuario.id : null;
    this.idPessoa = dadoUsuario ? dadoUsuario.idPessoa : null;
    this.nome = dadoUsuario ? dadoUsuario.nome : null;
    this.cpf = dadoUsuario ? dadoUsuario.cpf : null;
    this.numeroRegistro = dadoUsuario ? dadoUsuario.numeroRegistro : null;
    this.dataNascimento = dadoUsuario ? dadoUsuario.dataNascimento : null;
    this.email = dadoUsuario ? dadoUsuario.email : null;
    this.ativo = dadoUsuario ? dadoUsuario.ativo : false;
    this.bloqueado = dadoUsuario ? dadoUsuario.bloqueado : false;
    this.idsPerfis = dadoUsuario ? dadoUsuario.idsPerfis : [];
  }
}
export default Usuario;
