import baseService from "../BaseService";
import TurmaDiarioFrequencia from "@/model/turma/TurmaDiarioFrequencia";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import Turma from "@/model/turma/Turma";

export default {
  comboParaDiarioFrequencia() {
    let disciplinas = [];
    return baseService
      .get("/api/turmas/combo-turmas-diario-frequencia")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            disciplinas.push(new TurmaDiarioFrequencia(d));
          });
        }
        return disciplinas;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar disciplinas para o diário/frequência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaOcorrenciaPorMatricula(idMatricula) {
    return baseService
      .get("/api/turmas/combo-ocorrencia/matricula/" + idMatricula)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar turmas para ocorrência por matricula"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaOcorrenciaPorIdCursoEAno(idCurso, ano) {
    return baseService
      .get("/api/turmas/combo-ocorrencia/curso/" + idCurso + "/ano/" + ano)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar turmas por curso e ano"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboAnosPorCurso(idCurso) {
    return baseService
      .get("/api/turmas/combo-anos/curso/" + idCurso)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar anos das turmas do curso"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaModulosDosProfessores(idPeriodoExecucao) {
    return baseService
      .get(
        "/api/turmas/combo-modulos-professores/periodo-execucao/" +
          idPeriodoExecucao
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar turmas para módulos utilizados por professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  visualizarEstrutura(idCurso, ano) {
    return baseService
      .get(`/api/turmas/visualiza-estrutura/curso/${idCurso}/ano/${ano}`)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return [];
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar visualizar estrutura da turma"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(idPeriodoExecucao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/turmas", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao,
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
        mixinsMessage.methods.error("Erro ao tentar listar Turmas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/turmas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Turma(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Turma");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerar(turma) {
    return baseService
      .post("/api/turmas/geracao", turma)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar gerar Turmas", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(turma) {
    if (turma.id) {
      return baseService
        .put("/api/turmas/" + turma.id, turma)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Turma", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/turmas", turma)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Turma", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(turma) {
    return baseService
      .delete("/api/turmas/" + turma.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Turma", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  combo(idPeriodoExecucao) {
    return baseService
      .get("/api/turmas/combo-contratos", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar turmas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboAnosHorario() {
    return baseService
      .get("/api/turmas/combo-anos-horario")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar anos das turmas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboTurmaHorario(idPeriodoExecucao) {
    return baseService
      .get("/api/turmas/combo-turma-horario", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao
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
          "Erro ao tentar listar turmas para o horário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
