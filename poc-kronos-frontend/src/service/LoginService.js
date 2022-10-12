import baseService from "./BaseService";
import mixinsMessage from "@/mixins/message";
export default {
  logar(login) {
    return baseService
      .post("/api/auth", login)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        mixinsMessage.methods.error("Usuário ou Senha inválidos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  logout(login) {
    return baseService
      .post("/api/auth/logout", login)
      .then(response => {
        return response.data;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível fazer logout");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
