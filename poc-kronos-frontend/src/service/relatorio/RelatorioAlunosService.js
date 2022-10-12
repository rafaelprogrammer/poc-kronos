import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  gerarPorTurmaRSituacaoPDF(
    idTurma,
    ano,
    idsTipoSituacaoContrato,
    nomeCurso,
    nomePeriodo,
    nomeTurma
  ) {
    return baseService
      .get("/api/relatorios/alunos/turmas-situacoes/pdf", {
        responseType: "blob",
        params: {
          idTurma: idTurma,
          ano: ano,
          idsTipoSituacaoContrato: idsTipoSituacaoContrato
            .toString()
            .replace("[", "")
            .replace("]", ""),
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo,
          nomeTurma: nomeTurma
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Alunos Por Turma e Situação - " +
              nomeTurma +
              "_" +
              nomePeriodo +
              ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório alunos por turma e situação"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarPorPeriodoRSituacaoPDF(
    idPeriodoExecucao,
    ano,
    idsTipoSituacaoContrato,
    nomeCurso,
    nomePeriodo
  ) {
    return baseService
      .get("/api/relatorios/alunos/periodos-situacoes/pdf", {
        responseType: "blob",
        params: {
          idPeriodoExecucao: idPeriodoExecucao,
          ano: ano,
          idsTipoSituacaoContrato: idsTipoSituacaoContrato
            .toString()
            .replace("[", "")
            .replace("]", ""),
          nomeCurso: nomeCurso,
          nomePeriodo: nomePeriodo
        }
      })
      .then(response => {
        if (response) {
          this._criarLinkDownload(
            "Alunos Por Turma e Situação - " + nomePeriodo + ".pdf",
            response
          );
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar o relatório alunos por turma e situação"
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
  listarPorTurmaRSituacoes(
    idTurma,
    idsTipoSituacaoContrato,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/relatorios/alunos/turmas-situacoes", {
        params: {
          idTurma: idTurma,
          idsTipoSituacaoContrato: idsTipoSituacaoContrato
            .toString()
            .replace("[", "")
            .replace("]", ""),
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
          "Erro ao tentar listar alunos por turma e situações"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarPorPeriodoRSituacoes(
    idPeriodoExecucao,
    idsTipoSituacaoContrato,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/relatorios/alunos/periodos-situacoes", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao,
          idsTipoSituacaoContrato: idsTipoSituacaoContrato
            .toString()
            .replace("[", "")
            .replace("]", ""),
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
          "Erro ao tentar listar alunos por turma e situações"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
