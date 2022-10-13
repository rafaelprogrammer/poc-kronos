import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Categoria from "@/model/calendario/Categoria";

export default {
  salvar(evento) {
    return baseService
      .post("/api/eventos", evento)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(error => {
        mixinsMessage.methods.error("Erro ao tentar cadastrar Evento", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  categorias() {
    const tipos = [];
    return baseService
      .get("/api/eventos/categorias")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Categoria(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de situacoes de calendários"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
