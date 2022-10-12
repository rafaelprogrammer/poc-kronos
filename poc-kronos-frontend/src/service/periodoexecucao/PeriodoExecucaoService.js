import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import PeriodoExecucaoEstruturaAnoLetivo from "@/model/periodoexecucao/PeriodoExecucaoEstruturaAnoLetivo";
import FaseResumo from "@/model/periodoexecucao/FaseResumo";
import FaseExecucao from "@/model/periodoexecucao/FaseExecucao";
import SubFaseResumo from "@/model/periodoexecucao/SubFaseResumo";
import SubFaseExecucao from "@/model/periodoexecucao/SubFaseExecucao";

export default {
  comboParaModulosDosProfessores(idCurso) {
    return baseService
      .get("/api/periodos-execucoes/combo-modulos-professores/curso/" + idCurso)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar períodos de execução para módulos utilizados por professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  visualizarEstrutura(idCurso, ano) {
    return baseService
      .get(
        `/api/periodos-execucoes/visualiza-estrutura/curso/${idCurso}/ano/${ano}`
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return [];
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar visualizar estrutura do período de execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarSubFasesExecucao(
    idCurso,
    idPeriodoExecucao,
    idFaseExecucao,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/periodos-execucoes/sub-fases-execucao", {
        params: {
          idCurso: idCurso,
          idPeriodoExecucao: idPeriodoExecucao,
          idFaseExecucao: idFaseExecucao,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar fases execucoes para estrutura do ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarSubFase(id) {
    return baseService
      .get("/api/periodos-execucoes/sub-fases-execucao/" + id)
      .then(response => {
        if (response && response.data) {
          return new SubFaseExecucao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Sub-Fase Execucao estrutura ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirSubFase(subFaseExecucao) {
    return baseService
      .delete(
        "/api/periodos-execucoes/sub-fases-execucao/" + subFaseExecucao.id
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Sub-Fase Execucao estrutura ano letivo",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  alterarSubFasesExecucao(subFaseExecucao) {
    if (subFaseExecucao.id) {
      return baseService
        .put(
          "/api/periodos-execucoes/sub-fases-execucao/" + subFaseExecucao.id,
          subFaseExecucao
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Sub-Fase Execução",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  salvarSubFasesExecucoes(idCurso, anoPeriodoExecucao, subFases) {
    const subFasesExecucoes = [];
    subFases.map(f => {
      let subFaseExecucao = new SubFaseExecucao();
      subFaseExecucao.dataInicio = f.dataInicio;
      subFaseExecucao.dataFim = f.dataFim;
      subFaseExecucao.idCurso = idCurso;
      subFaseExecucao.anoPeriodoExecucao = anoPeriodoExecucao;
      subFaseExecucao.siglaSubFase = f.sigla;
      subFasesExecucoes.push(subFaseExecucao);
    });
    return baseService
      .post("/api/periodos-execucoes/sub-fases-execucao", subFasesExecucoes)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Sub-Fase Execução",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaGeracaoSubFaseExecucao(idCurso) {
    let fases = [];
    return baseService
      .get(
        "/api/periodos-execucoes/sub-fases-execucao/curso/" +
          idCurso +
          "/geracao"
      )
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            fases.push(new SubFaseResumo(p));
          });
        }
        return fases;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar sub-fases para geracao das sub-fases de execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirFase(faseExecucao) {
    return baseService
      .delete("/api/periodos-execucoes/fases-execucao/" + faseExecucao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Fase Execucao estrutura ano letivo",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarFase(id) {
    return baseService
      .get("/api/periodos-execucoes/fases-execucao/" + id)
      .then(response => {
        if (response && response.data) {
          return new FaseExecucao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Fase Execucao estrutura ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  alterarFasesExecucao(faseExecucao) {
    if (faseExecucao.id) {
      return baseService
        .put(
          "/api/periodos-execucoes/fases-execucao/" + faseExecucao.id,
          faseExecucao
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Fase Execução",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  listarFasesExecucao(idCurso, idPeriodoExecucao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/periodos-execucoes/fases-execucao", {
        params: {
          idCurso: idCurso,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar fases execucoes para estrutura do ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarFasesExecucoes(idCurso, anoPeriodoExecucao, fases) {
    const fasesExecucoes = [];
    fases.map(f => {
      let faseExecucao = new FaseExecucao();
      faseExecucao.dataInicio = f.dataInicio;
      faseExecucao.dataFim = f.dataFim;
      faseExecucao.idCurso = idCurso;
      faseExecucao.anoPeriodoExecucao = anoPeriodoExecucao;
      faseExecucao.siglaFase = f.sigla;
      fasesExecucoes.push(faseExecucao);
    });
    return baseService
      .post("/api/periodos-execucoes/fases-execucao", fasesExecucoes)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Fase Execução",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaGeracaoFaseExecucao(idCurso) {
    let fases = [];
    return baseService
      .get(
        "/api/periodos-execucoes/fases-execucao/curso/" + idCurso + "/geracao"
      )
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            fases.push(new FaseResumo(p));
          });
        }
        return fases;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar fases para geracao das fases de execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaEstruturaAnoLetivo(
    idCurso,
    idCalendario,
    ano,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/periodos-execucoes", {
        params: {
          idCurso: idCurso,
          idCalendario: idCalendario,
          ano: ano,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar periodos execucoes para estrutura do ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(periodoExecucao) {
    if (periodoExecucao.id) {
      return baseService
        .put("/api/periodos-execucoes/" + periodoExecucao.id, periodoExecucao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Período Execução",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/periodos-execucoes", periodoExecucao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Período Execução",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  editar(id) {
    return baseService
      .get("/api/periodos-execucoes/" + id)
      .then(response => {
        if (response && response.data) {
          return new PeriodoExecucaoEstruturaAnoLetivo(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Periodo Execucao estrutura ano letivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(periodoExecucao) {
    return baseService
      .delete("/api/periodos-execucoes/" + periodoExecucao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Periodo Execucao estrutura ano letivo",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
