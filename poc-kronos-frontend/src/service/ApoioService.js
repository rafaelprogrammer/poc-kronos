import baseService from "./BaseService";
import mixinsMessage from "@/mixins/message";

export default {
  versaoDoSistema() {
    return baseService
      .get("/api/apoio/version-system/")
      .then(response => {
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao recuperar a versão ", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
