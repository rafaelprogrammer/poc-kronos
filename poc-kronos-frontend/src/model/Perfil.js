class Perfil {
  constructor(dadosPerfil) {
    this.id = dadosPerfil ? dadosPerfil.id : null;
    this.nome = dadosPerfil ? dadosPerfil.nome : null;
  }
}
export default Perfil;
