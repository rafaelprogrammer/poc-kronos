import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Documento from "@/model/pessoa/Documento";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoDocumento from "@/model/pessoa/TipoDocumento";
export default {
  arquivo(arquivo, documento) {
    let formData = new FormData();
    formData.append("file", arquivo.dados);
    formData.append("id", documento.id);
    arquivo.dados = null;
    formData.append("metadado", JSON.stringify(arquivo));
    return baseService
      .post("/api/documentos/arquivo", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar/alterar arquivo do documento",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(documento, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/documentos", {
        params: {
          id: documento ? documento.id : null,
          idPessoa: documento ? documento.idPessoa : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Documentos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/documentos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Documento(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Documento");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(documento) {
    if (documento.id) {
      return baseService
        .put("/api/documentos/" + documento.id, documento)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Documento",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/documentos", documento)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Documento",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(documento) {
    return baseService
      .delete("/api/documentos/" + documento.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Documento", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/documentos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoDocumento(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de documentos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
