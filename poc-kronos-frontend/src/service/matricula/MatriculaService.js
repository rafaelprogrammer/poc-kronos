import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";

import TipoPrograma from "@/model/matricula/TipoPrograma";
export default {
  tiposProgramas() {
    let tipos = [];
    return baseService
      .get("/api/matriculas/tipos-programas")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoPrograma(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de programas"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
