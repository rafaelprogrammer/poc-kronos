import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Atividade from "@/model/atividade/Atividade";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  verificarSePodeAddAvaliacaoDeDesempenho(idAtividade) {
    return baseService
      .get("/api/atividades/" + idAtividade + "/avaliacao-desempenho")
      .then(response => {
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar verificar se possui avaliacao de desempenho"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirDiasNaoLetivos(idTurma, idDisciplina, idPeriodoExecucao) {
    return baseService
      .delete(
        "/api/atividades/exclui-dias-nao-letivos/turma/" +
          idTurma +
          "/disciplina/" +
          idDisciplina +
          "/periodo-execucao/" +
          idPeriodoExecucao
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir dias não letivos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarHorasIniciaisAtividades(id) {
    return baseService
      .get("/api/atividades/" + id + "/combo-hora-inicio")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar horas inicias das atividades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarDatasAtividades(idTurma, idDisciplina) {
    return baseService
      .get("/api/atividades/combo-datas-previstas", {
        params: {
          idTurma: idTurma,
          idDisciplina: idDisciplina
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
          "Erro ao tentar listar datas das atividades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(atividade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atividades", {
        params: {
          idSubFaseExecucao: atividade ? atividade.idSubFaseExecucao : null,
          idTurma: atividade ? atividade.idTurma : null,
          idDisciplina: atividade ? atividade.idDisciplina : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar atividades");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/atividades/" + id)
      .then(response => {
        if (response && response.data) {
          return new Atividade(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Atividade");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  confirmar(atividade) {
    if (atividade.id) {
      return baseService
        .put("/api/atividades/confirma/" + atividade.id, atividade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Atividade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  salvar(atividade) {
    if (atividade.id) {
      return baseService
        .put("/api/atividades/" + atividade.id, atividade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Atividade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/atividades", atividade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Atividade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(atividade) {
    return baseService
      .delete("/api/atividades/" + atividade.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Atividade", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAtividadesDisciplinasDireitos(idAtividade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atividades/disciplinas-direitos", {
        params: {
          id: idAtividade,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar atividades disciplinas direitos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAtividadeDisciplinaDireito(atividadeDisciplinaDireito) {
    return baseService
      .post("/api/atividades/disciplinas-direitos", atividadeDisciplinaDireito)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Atividade Disciplinas Direitos",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirAtividadeDisciplinaDireito(atividadeDisciplinaDireito) {
    return baseService
      .delete(
        "/api/atividades/disciplinas-direitos/" +
          atividadeDisciplinaDireito.idAtividade +
          "/disciplina-direito/" +
          atividadeDisciplinaDireito.idDisciplinaDireito
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Atividade Disciplina Direito",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAtividadesDisciplinasObjetivos(idAtividade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atividades/disciplinas-objetivos", {
        params: {
          id: idAtividade,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar atividades disciplinas objetivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAtividadeDisciplinaObjetivo(atividadeDisciplinaObjetivo) {
    return baseService
      .post(
        "/api/atividades/disciplinas-objetivos",
        atividadeDisciplinaObjetivo
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Atividade Disciplinas Objetivos",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirAtividadeDisciplinaObjetivo(atividadeDisciplinaObjetivo) {
    return baseService
      .delete(
        "/api/atividades/disciplinas-objetivos/" +
          atividadeDisciplinaObjetivo.idAtividade +
          "/disciplina-objetivo/" +
          atividadeDisciplinaObjetivo.idDisciplinaObjetivo
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Atividade Disciplina Objetivo",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAtividadesDisciplinasCompetencias(idAtividade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atividades/disciplinas-competencias", {
        params: {
          id: idAtividade,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar atividades disciplinas competências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAtividadeDisciplinaCompetencia(atividadeDisciplinaCompetencia) {
    return baseService
      .post(
        "/api/atividades/disciplinas-competencias",
        atividadeDisciplinaCompetencia
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Atividade Disciplinas Competências",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirAtividadeDisciplinaCompetencia(atividadeDisciplinaCompetencia) {
    return baseService
      .delete(
        "/api/atividades/disciplinas-competencias/" +
          atividadeDisciplinaCompetencia.idAtividade +
          "/disciplina-competencia/" +
          atividadeDisciplinaCompetencia.idDisciplinaCompetencia
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Atividade Disciplina Competência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAtividadesDisciplinasHabilidades(idAtividade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/atividades/disciplinas-habilidades", {
        params: {
          id: idAtividade,
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
        mixinsMessage.methods.error(
          "Erro ao tentar listar atividades disciplinas habilidades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAtividadeDisciplinaHabilidade(atividadeDisciplinaHabilidade) {
    return baseService
      .post(
        "/api/atividades/disciplinas-habilidades",
        atividadeDisciplinaHabilidade
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Atividade Disciplinas Habilidades",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirAtividadeDisciplinaHabilidade(atividadeDisciplinaHabilidade) {
    return baseService
      .delete(
        "/api/atividades/disciplinas-habilidades/" +
          atividadeDisciplinaHabilidade.idAtividade +
          "/disciplina-habilidade/" +
          atividadeDisciplinaHabilidade.idDisciplinaHabilidade
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Atividade Disciplina Habilidade",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
