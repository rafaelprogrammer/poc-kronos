import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";

export default {
  comboParaModulosDosProfessores(idCurso) {
    return baseService
      .get("/api/periodos-execucoes/combo-modulos-professores/curso/" + idCurso)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar períodos de execução para módulos utilizados por professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
