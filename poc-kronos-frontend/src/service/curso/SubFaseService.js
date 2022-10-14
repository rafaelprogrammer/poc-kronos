import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import EstatisticaResultadoSubFase from "@/model/curso/subfase/EstatisticaResultadoSubFase";
export default {
  comboSubFasesExecucaoParaDiario(idTurma, idDisciplina) {
    return baseService
      .get(
        "/api/cursos/periodos/fases/sub-fases/combo-sub-fases-execucao/turma/" +
          idTurma +
          "/disciplina/" +
          idDisciplina
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar Sub-Fases Execução para o diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboCursos() {
    return baseService
      .get("/api/cursos/periodos/fases/sub-fases/resultados/cursos")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar cursos ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboPeriodos(idCurso) {
    return baseService
      .get(
        "/api/cursos/periodos/fases/sub-fases/resultados/cursos/" +
          idCurso +
          "/periodos/"
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar períodos ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboTurmas(idPeriodoExecucao) {
    return baseService
      .get(
        "/api/cursos/periodos/fases/sub-fases/resultados/periodos/" +
          idPeriodoExecucao +
          "/turmas"
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar turmas ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboDisciplinas(idTurma) {
    return baseService
      .get(
        "/api/cursos/periodos/fases/sub-fases/resultados/turmas/" +
          idTurma +
          "/disciplinas"
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar disciplinas ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboBimestres(idPeriodoExecucao) {
    return baseService
      .get(
        "/api/cursos/periodos/fases/sub-fases/resultados/periodos/" +
          idPeriodoExecucao +
          "/sub-fases-execucoes"
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar disciplinas ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  recuperarEstatisticasResultado(
    idSubFaseExecucao,
    idTurma,
    idDisciplina,
    dataInicial,
    dataFinal
  ) {
    return baseService
      .get("/api/cursos/periodos/fases/sub-fases/resultados/estatisticas", {
        params: {
          idSubFaseExecucao: idSubFaseExecucao,
          idTurma: idTurma,
          idDisciplina: idDisciplina,
          dataInicial: dataInicial,
          dataFinal: dataFinal
        }
      })
      .then(response => {
        if (response && response.data) {
          return new EstatisticaResultadoSubFase(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar disciplinas ");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAlunosResultados(
    idTurma,
    idDisciplina,
    idSubFaseExecucao,
    dataFinal,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/cursos/periodos/fases/sub-fases/resultados/alunos", {
        params: {
          idTurma: idTurma,
          idDisciplina: idDisciplina,
          idSubFaseExecucao: idSubFaseExecucao,
          dataFinal: dataFinal,
          pagina: numeroPagina ? numeroPagina : null,
          total: qtdTotal ? qtdTotal : null
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar alunos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAvaliacoesResultados(
    idTurma,
    idDisciplina,
    idCredito,
    dataInicial,
    dataFinal
  ) {
    return baseService
      .get("/api/cursos/periodos/fases/sub-fases/resultados/avaliacoes", {
        params: {
          idTurma: idTurma,
          idDisciplina: idDisciplina,
          idCredito: idCredito,
          dataInicial: dataInicial,
          dataFinal: dataFinal
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
          "Erro ao tentar listar resultados avaliações"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarResultadosSubFases(resultados) {
    return baseService
      .post("/api/cursos/periodos/fases/sub-fases/resultados", resultados)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar resultados",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
