import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  atualizarResultadosBimestrais(idTurma, ano, idDisciplina, idFuncionario, idSubFaseExecucao) {
    return baseService
      .put(
        "/api/relatorios/pedagogicos/resultados-bimestrais", {
          "idTurma": idTurma, 
          "ano": ano, "idDisciplina": idDisciplina, 
          "idFuncionario": idFuncionario, 
          "idSubFaseExecucao": idSubFaseExecucao
        }
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar atualizar o resultado do bimestre", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarFolhaPontoPdf(idFuncionario, ano, mes) {
    return baseService
      .get("/api/relatorios/folha-ponto/pdf", {
        responseType: "blob",
        params: {
          idFuncionario: idFuncionario,
          ano: ano,
          mes: mes
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Folha de Ponto - " + mes + "_" + ano + ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório da folha de ponto"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarDiarioPdf(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    turma,
    subFaseExecucao,
  ) {
    return baseService
      .get("/api/relatorios/diario/pdf", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: turma.sigla,
          idTurma: turma.id,
          numeroDias: disciplina.numeroDias,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Diário - " + disciplina.nomeFuncionario + ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório do diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarDiarioXLSX(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    turma,
    subFaseExecucao,
  ) {
    return baseService
      .get("/api/relatorios/diario/xlsx", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: turma.sigla,
          idTurma: turma.id,
          numeroDias: disciplina.numeroDias,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Diário - " + disciplina.nomeFuncionario + ".xlsx",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório do diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarFrequenciaPdf(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    nomeTurma,
    subFaseExecucao,
    idTurma
  ) {
    return baseService
      .get("/api/relatorios/frequencia/pdf", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          idTurma: idTurma,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          numeroDias: disciplina.numeroDias,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: nomeTurma,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Frequência - " + nomeTurma + ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório do diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarFrequenciaXLSX(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    nomeTurma,
    subFaseExecucao,
    idTurma
  ) {
    return baseService
      .get("/api/relatorios/frequencia/xlsx", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          idTurma: idTurma,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: nomeTurma,
          numeroDias: disciplina.numeroDias,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Frequência - " + nomeTurma + ".xlsx",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório do diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarResultadoBimestrePdf(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    nomeTurma,
    subFaseExecucao,
    idTurma
  ) {
    return baseService
      .get("/api/relatorios/resultado-bimestre/pdf", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          idTurma: idTurma,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          numeroDias: disciplina.numeroDias,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: nomeTurma,
          nomeBimestre: subFaseExecucao.siglaSubFase,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Resultados Bimestrais - " + nomeTurma + ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório dos resultados bimestrais"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarResultadoBimestreXLSX(
    disciplina,
    idSubFaseExecucao,
    nomeCurso,
    nomePeriodo,
    nomeTurma,
    subFaseExecucao,
    idTurma
  ) {
    return baseService
      .get("/api/relatorios/resultado-bimestre/xlsx", {
        responseType: "blob",
        params: {
          idDisciplina: disciplina ? disciplina.id : null,
          idFuncionario: disciplina ? disciplina.idFuncionario : null,
          idSubFaseExecucao: idSubFaseExecucao,
          idTurma: idTurma,
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          nomeDisciplina: disciplina ? disciplina.nome : null,
          nomeTurma: nomeTurma,
          nomeBimestre: subFaseExecucao.siglaSubFase,
          numeroDias: disciplina.numeroDias,
          dataInicio: subFaseExecucao ? subFaseExecucao.dataInicio : null,
          dataFim: subFaseExecucao ? subFaseExecucao.dataFim : null,
          nomeProfessor: disciplina ? disciplina.nomeFuncionario : null,
          cargaHorariaPrevista: disciplina
            ? disciplina.cargaHorariaPrevista
            : null,
          cargaHorariaMinistrada: disciplina
            ? disciplina.cargaHorariaMinistrada
            : null
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Resultados Bimestrais - " + nomeTurma + ".xlsx",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório dos resultados bimestrais"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  _criarLinkDownload(nome, response) {
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", nome);
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link);
  },
  anos() {
    return baseService
      .get("/api/relatorios/anos")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Não foi possível listar os anos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cursosPorAno(ano) {
    return baseService
      .get("/api/relatorios/cursos/" + ano)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os cursos por ano"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  periodosPorCursoEAno(idCurso, ano) {
    return baseService
      .get("/api/relatorios/periodos/curso/" + idCurso + "/ano/" + ano)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os cursos por ano"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  turmasPorPeriodoExecucao(idPeriodoExecucao) {
    return baseService
      .get("/api/relatorios/turmas/" + idPeriodoExecucao)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os turmas por período execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  bimestresPorPeriodoExecucao(idPeriodoExecucao) {
    return baseService
      .get("/api/relatorios/bimestres/" + idPeriodoExecucao)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os bimestres por período execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarPedagogicos(idTurma, idSubFaseExecucao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/relatorios/pedagogicos", {
        params: {
          idTurma: idTurma,
          idSubFaseExecucao: idSubFaseExecucao,
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
        mixinsMessage.methods.error("Erro ao tentar listar diários");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarPedagogicosResultadosBimestrais(idTurma, idSubFaseExecucao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/relatorios/pedagogicos/resultados-bimestrais", {
        params: {
          idTurma: idTurma,
          idSubFaseExecucao: idSubFaseExecucao,
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
        mixinsMessage.methods.error("Erro ao tentar listar diários");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  encerrarDiario(idDisciplina, idSubFaseExecucao) {
    return baseService
      .post(
        "/api/relatorios/diarios/encerra/disciplina/" +
          idDisciplina +
          "/subFaseExecucao/" +
          idSubFaseExecucao
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar encerrar o diário", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  reabrirDiario(idDisciplina, idSubFaseExecucao) {
    return baseService
      .post(
        "/api/relatorios/diarios/reabre/disciplina/" +
          idDisciplina +
          "/subFaseExecucao/" +
          idSubFaseExecucao
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar reabrir o diário", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
