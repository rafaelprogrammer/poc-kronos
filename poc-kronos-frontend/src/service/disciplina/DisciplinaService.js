import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Disciplina from "@/model/disciplina/Disciplina";
import DisciplinaDireito from "@/model/disciplina/direito/DisciplinaDireito";
import DisciplinaObjetivo from "@/model/disciplina/objetivo/DisciplinaObjetivo";
import DisciplinaCompetencia from "@/model/disciplina/competencia/DisciplinaCompetencia";
import DisciplinaHabilidade from "@/model/disciplina/habilidade/DisciplinaHabilidade";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoDisciplina from "@/model/disciplina/TipoDisciplina";

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
  comboListarPorPeriodoExecucao(idPeriodoExecucao) {
    return baseService
      .get("/api/disciplinas/combo/periodo-execucao/" + idPeriodoExecucao)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar disciplinas por período de execução"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  buscarPreRequisitosRegistrado(id) {
    return baseService
      .get("/api/disciplinas/pre-requisitos/" + id)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar buscar pré-requisitos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  registrarPreRequisito(disciplinaPreRequisito) {
    return baseService
      .post("/api/disciplinas/pre-requisitos", disciplinaPreRequisito)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar Disciplinas Pré-Requisitos",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboUsadoEmPreRequisito(id, idCurso, numeroPeriodo) {
    return baseService
      .get("/api/disciplinas/combo-pre-requisito", {
        params: {
          id: id,
          idCurso: idCurso,
          numeroPeriodo: numeroPeriodo
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
          "Erro ao tentar listar disciplinas pre-requisitos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  combo(idPeriodo) {
    return baseService
      .get("/api/disciplinas/combo", {
        params: {
          idPeriodo: idPeriodo
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar disciplinas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboUnificacoes(idDisciplina, idPeriodo) {
    return baseService
      .get("/api/disciplinas/combo-disciplinas-unificacoes", {
        params: {
          id: idDisciplina,
          idPeriodo: idPeriodo
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
          "Erro ao tentar listar disciplinas unificações"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(disciplina, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/disciplinas", {
        params: {
          id: disciplina ? disciplina.id : null,
          idPeriodo: disciplina ? disciplina.idPeriodo : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Disciplinas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/disciplinas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Disciplina(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Disciplina");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(disciplina) {
    if (disciplina.id) {
      return baseService
        .put("/api/disciplinas/" + disciplina.id, disciplina)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Disciplina",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/disciplinas", disciplina)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Disciplina");
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(disciplina) {
    return baseService
      .delete("/api/disciplinas/" + disciplina.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Disciplina", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/disciplinas/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoDisciplina(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de disciplinas"
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
  editarDisciplinaDireito(id) {
    return baseService
      .get("/api/disciplinas/direitos/" + id)
      .then(response => {
        if (response && response.data) {
          return new DisciplinaDireito(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Disciplina Direito");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarDisciplinaDireito(disciplinaDireito) {
    if (disciplinaDireito.id) {
      return baseService
        .put(
          "/api/disciplinas/direitos/" + disciplinaDireito.id,
          disciplinaDireito
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Disciplina Direito",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/disciplinas/direitos", disciplinaDireito)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Disciplina Direito",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirDireitoDisciplina(disciplinaDireito) {
    return baseService
      .delete("/api/disciplinas/direitos/" + disciplinaDireito.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Disciplina Direito",
          error
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
  editarDisciplinaObjetivo(id) {
    return baseService
      .get("/api/disciplinas/objetivos/" + id)
      .then(response => {
        if (response && response.data) {
          return new DisciplinaObjetivo(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Disciplina Objetivo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarDisciplinaObjetivo(disciplinaObjetivo) {
    if (disciplinaObjetivo.id) {
      return baseService
        .put(
          "/api/disciplinas/objetivos/" + disciplinaObjetivo.id,
          disciplinaObjetivo
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Disciplina Objetivo",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/disciplinas/objetivos", disciplinaObjetivo)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Disciplina Objetivo",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirDisciplinaObjetivo(disciplinaObjetivo) {
    return baseService
      .delete("/api/disciplinas/objetivos/" + disciplinaObjetivo.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Disciplina Objetivo",
          error
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
  listarTodasDisciplinasCompetencias(
    disciplinaCompetencia,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/competencias", {
        params: {
          idDisciplina: disciplinaCompetencia
            ? disciplinaCompetencia.idDisciplina
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
          "Erro ao tentar listar Disciplinas Competencias"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarDisciplinaCompetencia(id) {
    return baseService
      .get("/api/disciplinas/competencias/" + id)
      .then(response => {
        if (response && response.data) {
          return new DisciplinaCompetencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Disciplina Competencia"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarDisciplinaCompetencia(disciplinaCompetencia) {
    if (disciplinaCompetencia.id) {
      return baseService
        .put(
          "/api/disciplinas/competencias/" + disciplinaCompetencia.id,
          disciplinaCompetencia
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Disciplina Competencia",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/disciplinas/competencias", disciplinaCompetencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Disciplina Competencia",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirDisciplinaCompetencia(disciplinaCompetencia) {
    return baseService
      .delete("/api/disciplinas/competencias/" + disciplinaCompetencia.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Disciplina Competencia",
          error
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
  },
  listarTodasDisciplinasHabilidades(
    disciplinaHabilidade,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/disciplinas/habilidades", {
        params: {
          idDisciplina: disciplinaHabilidade
            ? disciplinaHabilidade.idDisciplina
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
          "Erro ao tentar listar Disciplinas Habilidades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarDisciplinaHabilidade(id) {
    return baseService
      .get("/api/disciplinas/habilidades/" + id)
      .then(response => {
        if (response && response.data) {
          return new DisciplinaHabilidade(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Disciplina Habilidade"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarDisciplinaHabilidade(disciplinaHabilidade) {
    if (disciplinaHabilidade.id) {
      return baseService
        .put(
          "/api/disciplinas/habilidades/" + disciplinaHabilidade.id,
          disciplinaHabilidade
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Disciplina Habilidade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/disciplinas/habilidades", disciplinaHabilidade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Disciplina Habilidade"
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirDisciplinaHabilidade(disciplinaHabilidade) {
    return baseService
      .delete("/api/disciplinas/habilidades/" + disciplinaHabilidade.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Disciplina Habilidade",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
