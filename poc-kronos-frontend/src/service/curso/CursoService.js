import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
export default {
  comboParaModulosDosProfessores() {
    return baseService
      .get("/api/cursos/combo-modulos-professores")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar cursos para os módulos utilizados pelos professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
