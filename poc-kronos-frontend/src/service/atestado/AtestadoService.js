import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoFalta from "@/model/atestado/TipoFalta";
import Atestado from "@/model/atestado/Atestado";

export default {
  arquivo(arquivo, atestado) {
    let formData = new FormData();
    formData.append("file", arquivo.dados);
    formData.append("id", atestado.id);
    arquivo.dados = null;
    formData.append("metadado", JSON.stringify(arquivo));
    return baseService
      .post("/api/atestados/arquivo", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar/alterar arquivo do atestado",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(atestado, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atestados", {
        params: {
          idPessoa: atestado ? atestado.idPessoa : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Atestados");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/atestados/" + id)
      .then(response => {
        if (response && response.data) {
          return new Atestado(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Atestado");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(atestado) {
    if (atestado.id) {
      return baseService
        .put("/api/atestados/" + atestado.id, atestado)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Atestado",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/atestados", atestado)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Atestado",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(atestado) {
    return baseService
      .delete("/api/atestados/" + atestado.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Atestado", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboTipoFalta() {
    let tiposFaltas = [];
    return baseService
      .get("/api/atestados/combo-tipo-falta")
      .then(response => {
        if (response && response.data) {
          response.data.map(tipoFalta => {
            tiposFaltas.push(new TipoFalta(tipoFalta));
          });
        }
        return tiposFaltas;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar tipos de faltas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
