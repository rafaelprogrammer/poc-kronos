import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Calendario from "@/model/calendario/Calendario";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoSituacaoCalendario from "@/model/calendario/TipoSituacaoCalendario";
export default {
  combo(idCurso, ano) {
    return baseService
      .get("/api/calendarios/combo", {
        params: {
          idCurso: idCurso,
          ano: ano
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
          "Erro ao tentar listar calendario por curso e ano"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboAnosPorCurso(idCurso) {
    return baseService
      .get("/api/calendarios/anos/curso/" + idCurso)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar anos por curso");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  concluir(calendario) {
    if (calendario.id) {
      return baseService
        .put("/api/calendarios/" + calendario.id + "/conclui")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar concluir calendário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  aprovar(calendario) {
    if (calendario.id) {
      return baseService
        .put("/api/calendarios/" + calendario.id + "/aprova")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar aprovar calendário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  liberarEdicao(calendario) {
    if (calendario.id) {
      return baseService
        .put("/api/calendarios/" + calendario.id + "/libera-edicao")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar liberar edição do calendário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  comboNumeros(ano) {
    return baseService
      .get("/api/calendarios/anos/" + ano + "/numeros")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar numeros dos calendarios por ano"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboAnos() {
    return baseService
      .get("/api/calendarios/anos")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar anos dos calendarios"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(calendario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/calendarios", {
        params: {
          id: calendario ? calendario.id : null,
          ano: calendario ? calendario.ano : null,
          numero: calendario ? calendario.numero : null,
          idTipoSituacaoCalendario: calendario
            ? calendario.idTipoSituacaoCalendario
            : null,
          descricao: calendario ? calendario.descricao : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Calendarios");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/calendarios/" + id)
      .then(response => {
        if (response && response.data) {
          return new Calendario(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Calendario");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(calendario) {
    if (calendario.id) {
      return baseService
        .put("/api/calendarios/" + calendario.id, calendario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Calendário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/calendarios", calendario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Calendário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(calendario) {
    return baseService
      .delete("/api/calendarios/" + calendario.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposSituacoes() {
    let tipos = [];
    return baseService
      .get("/api/calendarios/tipos-situacoes")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoSituacaoCalendario(p));
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
