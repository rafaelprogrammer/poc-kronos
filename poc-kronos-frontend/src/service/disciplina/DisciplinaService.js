import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  comboParaModulosDosProfessores(idTurma) {
    return baseService
      .get("/api/disciplinas/combo-modulos-professores/turma/" + idTurma)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar disciplinas para o diário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaAtividadeDisciplinaDireito(
    idDisciplina,
    idAtividade,
    idSubFase,
    codigo,
    descricao,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/direitos/atividades-disciplinas-direitos", {
        params: {
          idDisciplina: idDisciplina,
          idAtividade: idAtividade,
          idSubFase: idSubFase,
          codigo: codigo,
          descricao: descricao,
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
          "Erro ao tentar listar Disciplinas Direitos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasDisciplinasDireitos(disciplinaDireito, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/disciplinas/direitos", {
        params: {
          idDisciplina: disciplinaDireito
            ? disciplinaDireito.idDisciplina
            : null,
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
          "Erro ao tentar listar Disciplinas Direitos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaAtividadeDisciplinaObjetivo(
    idDisciplina,
    idAtividade,
    idSubFase,
    codigo,
    descricao,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/objetivos/atividades-disciplinas-objetivos", {
        params: {
          idDisciplina: idDisciplina,
          idAtividade: idAtividade,
          idSubFase: idSubFase,
          codigo: codigo,
          descricao: descricao,
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
          "Erro ao tentar listar Disciplinas Objetivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasDisciplinasObjetivos(disciplinaObjetivo, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/disciplinas/objetivos", {
        params: {
          idDisciplina: disciplinaObjetivo
            ? disciplinaObjetivo.idDisciplina
            : null,
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
          "Erro ao tentar listar Disciplinas Objetivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaAtividadeDisciplinaCompetencia(
    idDisciplina,
    idAtividade,
    idSubFase,
    codigo,
    descricao,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get(
        "/api/disciplinas/competencias/atividades-disciplinas-competencias",
        {
          params: {
            idDisciplina: idDisciplina,
            idAtividade: idAtividade,
            idSubFase: idSubFase,
            codigo: codigo,
            descricao: descricao,
            pagina: numeroPagina ? numeroPagina : null,
            total: qtdTotal ? qtdTotal : null
          }
        }
      )
      .then(response => {
        if (response && response.data) {
          return new ResultadoPesquisa(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar Disciplinas Competências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaAvaliacaoHabilidade(
    idDisciplina,
    idAvaliacao,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/habilidades/avaliacoes-habilidades", {
        params: {
          idDisciplina: idDisciplina,
          idAvaliacao: idAvaliacao,
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
          "Erro ao tentar listar Disciplinas Habilidades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarParaAtividadeDisciplinaHabilidade(
    idDisciplina,
    idAtividade,
    idSubFase,
    codigoHabilidade,
    descricaoHabilidade,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/habilidades/atividades-disciplinas-habilidades", {
        params: {
          idDisciplina: idDisciplina,
          idAtividade: idAtividade,
          idSubFase: idSubFase,
          codigoHabilidade: codigoHabilidade,
          descricaoHabilidade: descricaoHabilidade,
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
          "Erro ao tentar listar Disciplinas Habilidades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
