import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import TipoSituacaoContrato from "@/model/contrato/TipoSituacaoContrato";
export default {
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/contratos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoSituacaoContrato(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de situacoes de contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
