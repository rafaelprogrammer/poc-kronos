import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Avaliacao from "@/model/avaliacao/Avaliacao";
import TipoAvaliacao from "@/model/avaliacao/TipoAvaliacao";
import TipoAbrangencia from "@/model/avaliacao/TipoAbrangencia";
import TipoFormato from "@/model/avaliacao/TipoFormato";
import TipoRegistroNota from "@/model/avaliacao/TipoRegistroNota";
import GrupoAvaliacao from "@/model/avaliacao/GrupoAvaliacao";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  combo(idTurma, idDisciplina) {
    let avaliacoes = [];
    return baseService
      .get("/api/avaliacoes/combo", {
        params: {
          idTurma: idTurma,
          idDisciplina: idDisciplina
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(a => {
            avaliacoes.push(new Avaliacao(a));
          });
          return avaliacoes;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar avaliações para combo"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(idTurma, idDisciplina, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/avaliacoes", {
        params: {
          idTurma: idTurma,
          idDisciplina: idDisciplina,
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
        mixinsMessage.methods.error("Erro ao tentar listar avaliações");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAutomatica(idAtividade, idTurma, idDisciplina) {
    let avaliacao = new Avaliacao();
    avaliacao.idAtividade = idAtividade;
    avaliacao.idTurma = idTurma;
    avaliacao.idDisciplina = idDisciplina;
    return baseService
      .post("/api/avaliacoes/automatica", avaliacao)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar criar avaliação");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/avaliacoes/" + id)
      .then(response => {
        if (response && response.data) {
          return new Avaliacao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar avaliação");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(avaliacao) {
    if (avaliacao.id) {
      return baseService
        .put("/api/avaliacoes/" + avaliacao.id, avaliacao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Avaliação",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/avaliacoes", avaliacao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Avaliação",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(avaliacao) {
    return baseService
      .delete("/api/avaliacoes/" + avaliacao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Avaliação", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/avaliacoes/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoAvaliacao(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de avaliacoes"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposAbrangencias() {
    let tipos = [];
    return baseService
      .get("/api/avaliacoes/tipos-abrangencias")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoAbrangencia(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de abrangencias"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposFormatos() {
    let tipos = [];
    return baseService
      .get("/api/avaliacoes/tipos-formatos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoFormato(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de formatos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposRegistrosNotas() {
    let tipos = [];
    return baseService
      .get("/api/avaliacoes/tipos-registros-notas")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoRegistroNota(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de registros de notas"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarGruposAvaliacoes(idAvaliacao) {
    return baseService
      .get("/api/avaliacoes/grupos-avaliacoes/combo", {
        params: {
          idAvaliacao: idAvaliacao
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar avaliações");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosAvaliados(
    idAvaliacao,
    idTurma,
    idDisciplina,
    anoTurma,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/avaliacoes/avaliados", {
        params: {
          idAvaliacao: idAvaliacao,
          idTurma: idTurma,
          idDisciplina: idDisciplina,
          anoTurma: anoTurma,
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
        mixinsMessage.methods.error("Erro ao tentar listar avaliados");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAvaliados(avaliados) {
    return baseService
      .post("/api/avaliacoes/avaliados", avaliados)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar avaliados",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarResultadoHabiliadeDoAvaliado(
    idAvaliacao,
    dataAvaliacao,
    anoTurma,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/avaliacoes/avaliados-mencoes", {
        params: {
          idAvaliacao: idAvaliacao,
          dataAvaliacao: dataAvaliacao,
          anoTurma: anoTurma,
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
          "Erro ao tentar listar resultados habilidades dos avaliados"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarResultadoHabilidade(resultadoHabilidade) {
    return baseService
      .post("/api/avaliacoes/habilidades/resultados", resultadoHabilidade)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar resultado habilidade",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosGruposAvaliacoes(idAvaliacao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/avaliacoes/grupos-avaliacoes", {
        params: {
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
        mixinsMessage.methods.error("Erro ao tentar listar grupos avaliações");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarGrupoAvaliacao(id) {
    return baseService
      .get("/api/avaliacoes/grupos-avaliacoes/" + id)
      .then(response => {
        if (response && response.data) {
          return new GrupoAvaliacao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar grupo avaliação");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarGrupoAvaliacao(grupoAvaliacao) {
    if (grupoAvaliacao.id) {
      return baseService
        .put(
          "/api/avaliacoes/grupos-avaliacoes/" + grupoAvaliacao.id,
          grupoAvaliacao
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Avaliação",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/avaliacoes/grupos-avaliacoes", grupoAvaliacao)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Grupo Avaliação",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirGrupoAvaliacao(grupoAvaliacao) {
    return baseService
      .delete("/api/avaliacoes/grupos-avaliacoes/" + grupoAvaliacao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Grupo Avaliação",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarAvaliacoesHabilidades(idAvaliacao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/avaliacoes/habilidades", {
        params: {
          idAvaliacao: idAvaliacao,
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
          "Erro ao tentar listar avaliações habilidades"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAvaliacaoHabilidade(avaliacoesHabilidades) {
    return baseService
      .post("/api/avaliacoes/habilidades", avaliacoesHabilidades)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar Avaliações Habilidades",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluirAvaliacaoHabilidade(avaliacaoHabilidade) {
    return baseService
      .delete("/api/avaliacoes/habilidades/" + avaliacaoHabilidade.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Avaliação Habilidade",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  existeGrupoAvaliacao(idAvaliacao) {
    return baseService
      .get("/api/avaliacoes/grupos-avaliacoes/existe/avaliacao/" + idAvaliacao)
      .then(response => {
        if (response && response.data) {
          return new Avaliacao(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar verificar se existe cadastro de grupos para a avaliação"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
