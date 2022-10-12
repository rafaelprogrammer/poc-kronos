import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Nivel from "@/model/basecurricular/Nivel";
import FaixaAno from "@/model/basecurricular/FaixaAno";
import Componente from "@/model/basecurricular/Componente";
import CampoExperiencia from "@/model/basecurricular/CampoExperiencia";
import Direito from "@/model/basecurricular/Direito";
import Objetivo from "@/model/basecurricular/Objetivo";
import Competencia from "@/model/basecurricular/Competencia";
import Habilidade from "@/model/basecurricular/Habilidade";
import Valor from "@/model/basecurricular/Valor";
import Atitude from "@/model/basecurricular/Atitude";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  listarTodosNiveis(nivel, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/niveis", {
        params: {
          id: nivel ? nivel.id : null,
          nome: nivel ? nivel.nome : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Niveis");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarNivel(id) {
    return baseService
      .get("/api/basescurriculares/niveis/" + id)
      .then(response => {
        if (response && response.data) {
          return new Nivel(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Nivel");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarNivel(nivel) {
    if (nivel.id) {
      return baseService
        .put("/api/basescurriculares/niveis/" + nivel.id, nivel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Nivel", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/niveis", nivel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Nivel", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirNivel(nivel) {
    return baseService
      .delete("/api/basescurriculares/niveis/" + nivel.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Nivel", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboNiveis() {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/niveis/combo")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Nivel(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os níveis");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  faixasAnos(faixaAno) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/faixas-anos/combo", {
        params: {
          idNivel: faixaAno ? faixaAno.idNivel : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new FaixaAno(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar as faixas de anos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasFaixasAno(faixaAno, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/faixas-anos", {
        params: {
          id: faixaAno ? faixaAno.id : null,
          nome: faixaAno ? faixaAno.nome : null,
          idNivel: faixaAno ? faixaAno.idNivel : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Faixas Ano");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarFaixaAno(id) {
    return baseService
      .get("/api/basescurriculares/faixas-anos/" + id)
      .then(response => {
        if (response && response.data) {
          return new FaixaAno(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Faixas Ano");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarFaixaAno(nivel) {
    if (nivel.id) {
      return baseService
        .put("/api/basescurriculares/faixas-anos/" + nivel.id, nivel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Faixas Ano",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/faixas-anos", nivel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Faixas Ano",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirFaixaAno(nivel) {
    return baseService
      .delete("/api/basescurriculares/faixas-anos/" + nivel.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Faixas Ano", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboComponentes(idNivel) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/componentes/combo", {
        params: {
          idNivel: idNivel
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Componente(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os campos experiências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosComponentes(componente, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/componentes", {
        params: {
          id: componente ? componente.id : null,
          nome: componente ? componente.nome : null,
          idNivel: componente ? componente.idNivel : null,
          ativo: componente ? componente.ativo : null,
          bncc: componente ? componente.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Componentes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarComponente(id) {
    return baseService
      .get("/api/basescurriculares/componentes/" + id)
      .then(response => {
        if (response && response.data) {
          return new Componente(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Componente");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarComponente(componente) {
    if (componente.id) {
      return baseService
        .put("/api/basescurriculares/componentes/" + componente.id, componente)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Componente",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/componentes", componente)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Componente",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirComponente(componente) {
    return baseService
      .delete("/api/basescurriculares/componentes/" + componente.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Componente", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosCamposExperiencias(campoExperiencia, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/campos-experiencias", {
        params: {
          id: campoExperiencia ? campoExperiencia.id : null,
          nome: campoExperiencia ? campoExperiencia.nome : null,
          idNivel: campoExperiencia ? campoExperiencia.idNivel : null,
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
          "Erro ao tentar listar Campos Experiências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarCampoExperiencia(id) {
    return baseService
      .get("/api/basescurriculares/campos-experiencias/" + id)
      .then(response => {
        if (response && response.data) {
          return new CampoExperiencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Campo Experiência");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarCampoExperiencia(campoExperiencia) {
    if (campoExperiencia.id) {
      return baseService
        .put(
          "/api/basescurriculares/campos-experiencias/" + campoExperiencia.id,
          campoExperiencia
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Campo Experiência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/campos-experiencias", campoExperiencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Campo Experiência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirCampoExperiencia(campoExperiencia) {
    return baseService
      .delete(
        "/api/basescurriculares/campos-experiencias/" + campoExperiencia.id
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Campo Experiência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboCamposExperiencias(idNivel) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/campos-experiencias/combo", {
        params: {
          idNivel: idNivel
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new CampoExperiencia(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os campos experiências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarDireitosParaInclusaoDisciplinasDireitos(
    idDisciplina,
    idNivel,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/basescurriculares/direitos/inclusao-disciplina-direito", {
        params: {
          idDisciplina: idDisciplina,
          idNivel: idNivel,
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
  listarTodosDireitos(direito, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/direitos", {
        params: {
          id: direito ? direito.id : null,
          codigo: direito ? direito.codigo : null,
          idNivel: direito ? direito.idNivel : null,
          ativo: direito ? direito.ativo : null,
          bncc: direito ? direito.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Direitos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarDireito(id) {
    return baseService
      .get("/api/basescurriculares/direitos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Direito(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Direito");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarDireito(direito) {
    if (direito.id) {
      return baseService
        .put("/api/basescurriculares/direitos/" + direito.id, direito)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Direito",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/direitos", direito)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Direito",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirDireito(direito) {
    return baseService
      .delete("/api/basescurriculares/direitos/" + direito.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Direito", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarObjetivosParaInclusaoDisciplinasObjetivos(
    idDisciplina,
    idFaixaAno,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get("/api/basescurriculares/objetivos/inclusao-disciplina-objetivo", {
        params: {
          idDisciplina: idDisciplina,
          idFaixaAno: idFaixaAno,
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
  listarTodosObjetivos(objetivo, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/objetivos", {
        params: {
          id: objetivo ? objetivo.id : null,
          codigo: objetivo ? objetivo.codigo : null,
          idFaixaAno: objetivo ? objetivo.idFaixaAno : null,
          ativo: objetivo ? objetivo.ativo : null,
          bncc: objetivo ? objetivo.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Objetivos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarObjetivo(id) {
    return baseService
      .get("/api/basescurriculares/objetivos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Objetivo(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Objetivo");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarObjetivo(objetivo) {
    if (objetivo.id) {
      return baseService
        .put("/api/basescurriculares/objetivos/" + objetivo.id, objetivo)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Objetivo",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/objetivos", objetivo)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Objetivo");
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirObjetivo(objetivo) {
    return baseService
      .delete("/api/basescurriculares/objetivos/" + objetivo.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Objetivo", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarCompetenciasParaInclusaoDisciplinasCompetencias(
    idDisciplina,
    idNivel,
    idComponente,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get(
        "/api/basescurriculares/competencias/inclusao-disciplina-competencia",
        {
          params: {
            idDisciplina: idDisciplina,
            idNivel: idNivel,
            idComponente: idComponente,
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
  listarCompetenciasPorIds(idsCompetencias) {
    return baseService
      .get("/api/basescurriculares/competencias/ids", {
        params: {
          ids: JSON.stringify(idsCompetencias)
            .replace("[", "")
            .replace("]", "")
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Competências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasCompetencias(competencia, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/competencias", {
        params: {
          id: competencia ? competencia.id : null,
          codigo: competencia ? competencia.codigo : null,
          idNivel: competencia ? competencia.idNivel : null,
          idComponente: competencia ? competencia.idComponente : null,
          ativo: competencia ? competencia.ativo : null,
          bncc: competencia ? competencia.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Competências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarCompetencia(id) {
    return baseService
      .get("/api/basescurriculares/competencias/" + id)
      .then(response => {
        if (response && response.data) {
          return new Competencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Competência");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarCompetencia(competencia) {
    if (competencia.id) {
      return baseService
        .put(
          "/api/basescurriculares/competencias/" + competencia.id,
          competencia
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Competência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/competencias", competencia)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Competência",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirCompetencia(competencia) {
    return baseService
      .delete("/api/basescurriculares/competencias/" + competencia.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Competência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarHabilidadesParaInclusaoDisciplinasHabilidades(
    idDisciplina,
    idComponente,
    numeroPagina,
    qtdTotal
  ) {
    return baseService
      .get(
        "/api/basescurriculares/habilidades/inclusao-disciplina-habilidade",
        {
          params: {
            idDisciplina: idDisciplina,
            idComponente: idComponente,
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
  listarTodasHabilidades(habilidade, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/habilidades", {
        params: {
          id: habilidade ? habilidade.id : null,
          codigo: habilidade ? habilidade.codigo : null,
          idNivel: habilidade ? habilidade.idNivel : null,
          idComponente: habilidade ? habilidade.idComponente : null,
          ativo: habilidade ? habilidade.ativo : null,
          bncc: habilidade ? habilidade.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Habilidades");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarHabilidade(id) {
    return baseService
      .get("/api/basescurriculares/habilidades/" + id)
      .then(response => {
        if (response && response.data) {
          return new Habilidade(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Habilidade");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarHabilidade(habilidade) {
    if (habilidade.id) {
      return baseService
        .put("/api/basescurriculares/habilidades/" + habilidade.id, habilidade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Habilidade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/habilidades", habilidade)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Habilidade",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirHabilidade(habilidade) {
    return baseService
      .delete("/api/basescurriculares/habilidades/" + habilidade.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Habilidade", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosValores(numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/valores", {
        params: {
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
        mixinsMessage.methods.error("Erro ao tentar listar Valores");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarValor(id) {
    return baseService
      .get("/api/basescurriculares/valores/" + id)
      .then(response => {
        if (response && response.data) {
          return new Valor(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Valor");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarValor(valor) {
    if (valor.id) {
      return baseService
        .put("/api/basescurriculares/valores/" + valor.id, valor)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Valor", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/valores", valor)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Campo Valor",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirValor(valor) {
    return baseService
      .delete("/api/basescurriculares/valores/" + valor.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Valor", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboValores() {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/valores/combo")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Valor(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os valores");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasAtitudes(numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/atitudes", {
        params: {
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
        mixinsMessage.methods.error("Erro ao tentar listar Atitudes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarAtitude(id) {
    return baseService
      .get("/api/basescurriculares/atitudes/" + id)
      .then(response => {
        if (response && response.data) {
          return new Atitude(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Atitude");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarAtitude(atitude) {
    if (atitude.id) {
      return baseService
        .put("/api/basescurriculares/atitudes/" + atitude.id, atitude)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Valor", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/basescurriculares/atitudes", atitude)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Atitude", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirAtitude(atitude) {
    return baseService
      .delete("/api/basescurriculares/atitudes/" + atitude.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Atitude", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
