import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Curso from "@/model/curso/Curso";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoRegimeLetivo from "@/model/curso/TipoRegimeLetivo";
import TipoTurno from "@/model/curso/TipoTurno";
export default {
  comboParaTiposMatrizesHorarios() {
    return baseService
      .get("/api/cursos/combo-tipos-matrizes-horarios")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar cursos para tipos de matrizes dos horários"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaModulosDosProfessores() {
    return baseService
      .get("/api/cursos/combo-modulos-professores")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar cursos para os módulos utilizados pelos professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboCursoHorarioPorAno(ano) {
    return baseService
      .get("/api/cursos/combo-horario/ano/" + ano)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar cursos por ano para o horário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  combo() {
    return baseService
      .get("/api/cursos/combo")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar cursos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  visualizarEstrutura(idCurso) {
    return baseService
      .get(`/api/cursos/visualiza-estrutura/curso/${idCurso}`)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return [];
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Curso");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  naoExisteVinculoCursoEscala(cursoEscala) {
    return baseService
      .get(
        `/api/cursos/vinculo/curso/${cursoEscala.idCurso}/escala/${
          cursoEscala.idEscala
        }`
      )
      .then(response => {
        if (response && response.data === true) {
          mixinsMessage.methods.warning(
            "Curso já vinculado à escala selecionada!"
          );
          return false;
        }
        return true;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Curso");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  vincularCursoEscala(cursoEscala) {
    return baseService
      .post("/api/cursos/vincula-cursos-escalas", cursoEscala)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar vincular Curso Escala", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  desvincularCursoEscala(cursoEscala) {
    return baseService
      .post("/api/cursos/desvincula-cursos-escalas", cursoEscala)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar desvincular Curso Escala", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(curso, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos", {
        params: {
          id: curso ? curso.id : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Cursos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Curso(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Curso");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(curso) {
    if (curso.id) {
      return baseService
        .put("/api/cursos/" + curso.id, curso)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Curso", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos", curso)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Curso", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(curso) {
    return baseService
      .delete("/api/cursos/" + curso.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Curso", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposRegimesLetivos() {
    let tipos = [];
    return baseService
      .get("/api/cursos/tiposRegimesLetivos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoRegimeLetivo(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de regimes letivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposTurnos() {
    let tipos = [];
    return baseService
      .get("/api/cursos/tiposTurnos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoTurno(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de turnos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
