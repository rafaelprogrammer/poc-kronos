import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Escala from "@/model/instituicao/Escala";
import MensaoLimiteEscala from "@/model/instituicao/MensaoLimiteEscala";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  listarMensoesELimites(escala) {
    let mensoesELimites = [];
    return baseService
      .get("/api/instituicoes/escalas/mensoes-limites", {
        params: {
          id: escala ? escala.id : null,
          idCurso: escala ? escala.idCurso : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(mensaoELimite => {
            mensoesELimites.push(new MensaoLimiteEscala(mensaoELimite));
          });
        }
        return mensoesELimites;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar Mensoes e Limites de Uma Escala"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(escala, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/instituicoes/escalas", {
        params: {
          id: escala ? escala.id : null,
          idCurso: escala ? escala.idCurso : null,
          idTipoAbrangencia: escala ? escala.idTipoAbrangencia : null,
          idTipoAvaliacao: escala ? escala.idTipoAvaliacao : null,
          idInstituicao: escala ? escala.idInstituicao : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Escalas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/instituicoes/escalas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Escala(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Escala");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(escala) {
    if (escala.id) {
      return baseService
        .put("/api/instituicoes/escalas/" + escala.id, escala)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Escala", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/instituicoes/escalas", escala)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Escala", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(escala) {
    return baseService
      .delete("/api/instituicoes/escalas/" + escala.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Escala", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
