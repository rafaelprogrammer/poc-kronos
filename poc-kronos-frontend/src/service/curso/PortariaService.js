import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Portaria from "@/model/curso/portaria/Portaria";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoPortaria from "@/model/curso/portaria/TipoPortaria";
export default {
  listarTodos(portaria, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos/portarias", {
        params: {
          id: portaria ? portaria.id : null,
          idCurso: portaria ? portaria.idCurso : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Portarias");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/portarias/" + id)
      .then(response => {
        if (response && response.data) {
          return new Portaria(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Portaria");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(portaria) {
    if (portaria.id) {
      return baseService
        .put("/api/cursos/portarias/" + portaria.id, portaria)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Portaria",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos/portarias", portaria)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Portaria",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(portaria) {
    return baseService
      .delete("/api/cursos/portarias/" + portaria.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Portaria", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/cursos/portarias/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoPortaria(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de portarias"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
