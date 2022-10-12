import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Periodo from "@/model/curso/periodo/Periodo";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoPeriodo from "@/model/curso/periodo/TipoPeriodo";
export default {
  comboPeriodoHorario(idCurso, anoPeriodoExecucao) {
    return baseService
      .get("/api/cursos/periodos/combo-periodo-horario", {
        params: {
          idCurso: idCurso,
          anoPeriodoExecucao: anoPeriodoExecucao
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
          "Erro ao tentar listar Periodos para o horário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboPeriodoPendenticaContrato(
    idCurso,
    anoPeriodoExecucao,
    idMatricula,
    numero
  ) {
    return baseService
      .get("/api/cursos/periodos/combo-periodo-pendencia-contrato", {
        params: {
          idCurso: idCurso,
          anoPeriodoExecucao: anoPeriodoExecucao,
          idMatricula: idMatricula,
          numero: numero
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
          "Erro ao tentar listar Periodos do Contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboPeriodoContrato(idCurso, anoPeriodoExecucao, idMatricula) {
    return baseService
      .get("/api/cursos/periodos/combo-periodo-contrato", {
        params: {
          idCurso: idCurso,
          anoPeriodoExecucao: anoPeriodoExecucao,
          idMatricula: idMatricula
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
          "Erro ao tentar listar Periodos do Contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  combo(idCurso) {
    return baseService
      .get("/api/cursos/periodos/combo", {
        params: {
          idCurso: idCurso
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Periodos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(periodo, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos/periodos", {
        params: {
          id: periodo ? periodo.id : null,
          idCurso: periodo ? periodo.idCurso : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Periodos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/periodos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Periodo(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Periodo");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(periodo) {
    if (periodo.id) {
      return baseService
        .put("/api/cursos/periodos/" + periodo.id, periodo)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Periodo",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos/periodos", periodo)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Periodo",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(periodo) {
    return baseService
      .delete("/api/cursos/periodos/" + periodo.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Periodo", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/cursos/periodos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoPeriodo(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de periodos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
