import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Titulacao from "@/model/pessoa/Titulacao";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoTitulo from "@/model/pessoa/TipoTitulo";
export default {
  arquivo(arquivo, titulacao) {
    let formData = new FormData();
    formData.append("file", arquivo.dados);
    formData.append("id", titulacao.id);
    arquivo.dados = null;
    formData.append("metadado", JSON.stringify(arquivo));
    return baseService
      .post("/api/titulacoes/arquivo", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar/alterar arquivo da titulacao",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(titulacao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/titulacoes", {
        params: {
          id: titulacao ? titulacao.id : null,
          idPessoa: titulacao ? titulacao.idPessoa : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Titulacoes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/titulacoes/" + id)
      .then(response => {
        if (response && response.data) {
          return new Titulacao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Titulacao");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(titulacao) {
    if (titulacao.id) {
      return baseService
        .put("/api/titulacoes/" + titulacao.id, titulacao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Titulacao",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/titulacoes", titulacao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Titulacao",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(titulacao) {
    return baseService
      .delete("/api/titulacoes/" + titulacao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Titulacao", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/titulacoes/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoTitulo(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de titulacoes"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
