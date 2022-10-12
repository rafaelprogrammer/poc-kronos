import baseService from "./BaseService";
import mixinsMessage from "@/mixins/message";
import Usuario from "@/model/Usuario";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  listarTodos(usuario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/usuarios", {
        params: {
          nome: usuario ? usuario.nome : null,
          cpf: usuario ? usuario.cpf : null,
          numeroRegistro: usuario ? usuario.numeroRegistro : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Usuários");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/usuarios/" + id)
      .then(response => {
        if (response && response.data) {
          return new Usuario(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Usuário");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(usuario) {
    if (usuario.id) {
      return baseService
        .put("/api/usuarios/" + usuario.id, usuario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Usuário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/usuarios", usuario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Usuário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(usuario) {
    return baseService
      .delete("/api/usuarios/" + usuario.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Usuário", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
