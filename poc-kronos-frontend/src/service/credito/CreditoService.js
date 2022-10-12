import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
export default {
  excluir(credito) {
    if (!credito.id) {
      return null;
    }
    return baseService
      .delete("/api/creditos/" + credito.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Crédito", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
