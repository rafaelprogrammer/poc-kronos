import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Diario from "@/model/diario/Diario";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  listarTodos(diario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/diarios", {
        params: {
          idAtividade: diario ? diario.idAtividade : null,
          pagina: numeroPagina ? numeroPagina : null,
          total: qtdTotal ? qtdTotal : null
        }
      })
      .then(response => {
        if (response && response.data) {
          return new ResultadoPesquisa(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Diários");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/diarios/" + id)
      .then(response => {
        if (response && response.data) {
          return new Diario(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Diário");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(diario) {
    if (diario.id) {
      return baseService
        .put("/api/diarios/" + diario.id, diario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/diarios", diario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(diario) {
    return baseService
      .delete("/api/diarios/" + diario.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Diário", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
