import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoOcorrencia from "@/model/ocorrencia/TipoOcorrencia";
import Ocorrencia from "@/model/ocorrencia/Ocorrencia";

export default {
  retornarGrauComportamento(idPessoa) {
    return baseService
      .get("/api/ocorrencias/grauComportamento/pessoa/" + idPessoa)
      .then(response => {
        if (response) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar informar o grau do comportamento"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(ocorrencia, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/ocorrencias", {
        params: {
          idTipoOcorrencia: ocorrencia ? ocorrencia.idTipoOcorrencia : null,
          idMatricula: ocorrencia ? ocorrencia.idMatricula : null,
          idPessoa: ocorrencia ? ocorrencia.idPessoa : null,
          idTurma: ocorrencia ? ocorrencia.idTurma : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Ocorrências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/ocorrencias/" + id)
      .then(response => {
        if (response && response.data) {
          return new Ocorrencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Ocorrência");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(ocorrencia) {
    if (ocorrencia.id) {
      return baseService
        .put("/api/ocorrencias/" + ocorrencia.id, ocorrencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Ocorrência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/ocorrencias", ocorrencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Ocorrência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(ocorrencia) {
    return baseService
      .delete("/api/ocorrencias/" + ocorrencia.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Ocorrência", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosTiposOcorrencias(tipoOcorrencia, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/ocorrencias/tipos", {
        params: {
          codigo: tipoOcorrencia ? tipoOcorrencia.codigo : null,
          fator: tipoOcorrencia ? tipoOcorrencia.fator : null,
          vigente: tipoOcorrencia ? tipoOcorrencia.vigente : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Tipos Ocorrências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarTipoOcorrencia(id) {
    return baseService
      .get("/api/ocorrencias/tipos/" + id)
      .then(response => {
        if (response && response.data) {
          return new TipoOcorrencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Tipo Ocorrência");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarTipoOcorrencia(tipoOcorrencia) {
    if (tipoOcorrencia.id) {
      return baseService
        .put("/api/ocorrencias/tipos/" + tipoOcorrencia.id, tipoOcorrencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Tipo Ocorrência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/ocorrencias/tipos", tipoOcorrencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Tipo Ocorrência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirTipoOcorrencia(tipoOcorrencia) {
    return baseService
      .delete("/api/ocorrencias/tipos/" + tipoOcorrencia.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Tipo Ocorrência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboFatorTipoOcorrencia() {
    return baseService
      .get("/api/ocorrencias/tipos/combo-fatores")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar fatores");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboVigenteTipoOcorrencia() {
    return baseService
      .get("/api/ocorrencias/tipos/combo-vigentes")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar vigentes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboTipoOcorrencia(dataOcorrencia) {
    return baseService
      .get("/api/ocorrencias/tipos/combo", {
        params: {
          dataOcorrencia: dataOcorrencia
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar tipos ocorrências por data"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
