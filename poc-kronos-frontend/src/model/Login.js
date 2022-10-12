class Login {
  constructor(dadosUsuario) {
    this.avatar = "/static/doc-images/lists/avatar-default.jpg";
    this.id = dadosUsuario ? dadosUsuario.id : null;
    this.username = dadosUsuario ? dadosUsuario.username : null;
    this.idInstituicao = dadosUsuario ? dadosUsuario.idInstituicao : null;
    this.instituicao = dadosUsuario ? dadosUsuario.instituicao : null;
    this.instituicaoMantenedora = dadosUsuario
      ? dadosUsuario.instituicaoMantenedora
      : null;
    this.instituicaoAtiva = dadosUsuario
      ? dadosUsuario.instituicaoAtiva
      : null;
    this.idPessoa = dadosUsuario ? dadosUsuario.idPessoa : null;
    this.nome = dadosUsuario ? dadosUsuario.nome : null;
    this.password = null;
    this.remeberme = false;
  }
}
export default Login;
