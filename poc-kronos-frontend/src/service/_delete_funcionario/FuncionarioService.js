import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoCategoriaFuncao from "@/model/funcionario/TipoCategoriaFuncao";
import TipoFuncao from "@/model/funcionario/TipoFuncao";
import Funcionario from "@/model/funcionario/Funcionario";
import FuncionarioFuncao from "@/model/funcionario/FuncionarioFuncao";
import ConfiguracaoPonto from "@/model/funcionario/ConfiguracaoPonto";
import TipoPeriodoPonto from "@/model/funcionario/TipoPeriodoPonto";
import Ponto from "@/model/funcionario/Ponto";
import Ausente from "@/model/funcionario/Ausente";

export default {
  comboParaOcorrenciaResponsaveisAnulacao(dataOcorrencia) {
    return baseService
      .get("/api/funcionarios/combo/ocorrencia/responsaveis-anulacao", {
        params: {
          data: dataOcorrencia
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
          "Erro ao tentar listar funcinários (responsáveis anulação) por data da ocorrência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaOcorrenciaRelatores(dataOcorrencia) {
    return baseService
      .get("/api/funcionarios/combo/ocorrencia/relatores", {
        params: {
          data: dataOcorrencia
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
          "Erro ao tentar listar funcinários (relatores) por data da ocorrência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboListarPorDisciplina(idDisciplina) {
    return baseService
      .get("/api/funcionarios/combo/disciplina/" + idDisciplina)
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar funcinários da disciplina"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(funcionario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/funcionarios", {
        params: {
          id: funcionario ? funcionario.id : null,
          idPessoa: funcionario ? funcionario.idPessoa : null,
          idInstituicao: funcionario ? funcionario.idInstituicao : null,
          numeroRegistro: funcionario ? funcionario.numeroRegistro : null,
          cpf: funcionario ? funcionario.cpf : null,
          nome: funcionario ? funcionario.nome : null,
          ativo: funcionario ? funcionario.ativo : null,
          idTipoFuncao: funcionario ? funcionario.idTipoFuncao : null,
          idTipoCategoriaFuncao: funcionario
            ? funcionario.idTipoCategoriaFuncao
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
        mixinsMessage.methods.error("Erro ao tentar listar funcionarios");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/funcionarios/" + id)
      .then(response => {
        if (response && response.data) {
          return new Funcionario(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Funcionário");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(funcionario) {
    if (funcionario.id) {
      return baseService
        .put("/api/funcionarios/" + funcionario.id, funcionario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Funcionário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/funcionarios", funcionario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Funcionário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  tiposFuncoes() {
    let tipos = [];
    return baseService
      .get("/api/funcionarios/funcoes/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoFuncao(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de funções"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposCategorias() {
    let tipos = [];
    return baseService
      .get("/api/funcionarios/categorias")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoCategoriaFuncao(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de categorias"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarFuncoes(idFuncionario) {
    let funcoes = [];
    return baseService
      .get("/api/funcionarios/funcoes", {
        params: {
          idFuncionario: idFuncionario
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            funcoes.push(new FuncionarioFuncao(p));
          });
        }
        return funcoes;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar funções do funcionário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarFuncoes(funcoes) {
    return baseService
      .post("/api/funcionarios/funcoes", funcoes)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar funções do funcionário",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasConfiguracoesPonto(idFuncionario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/funcionarios/configuracoes-pontos", {
        params: {
          idFuncionario: idFuncionario ? idFuncionario : null,
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
          "Erro ao tentar listar configurações do ponto"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarConfiguracaoPonto(id) {
    return baseService
      .get("/api/funcionarios/configuracoes-pontos/" + id)
      .then(response => {
        if (response && response.data) {
          return new ConfiguracaoPonto(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Configuração Ponto");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  verificarRegistraEntrada() {
    return baseService
      .get("/api/funcionarios/configuracoes-pontos/verifica-registra-entrada")
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return false;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar verificar se é possível registrar entrada"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  verificarRegistraTarefaOnline() {
    return baseService
      .get(
        "/api/funcionarios/configuracoes-pontos/verifica-registra-tarefa-online"
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return false;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar verificar se é possível registrar tarefa online"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarConfiguracaoPonto(configuracaoPonto) {
    if (configuracaoPonto.id) {
      return baseService
        .put(
          "/api/funcionarios/configuracoes-pontos/" + configuracaoPonto.id,
          configuracaoPonto
        )
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Configuração Ponto",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/funcionarios/configuracoes-pontos", configuracaoPonto)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Configuração Ponto",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirConfiguracaoPonto(configuracaoPonto) {
    return baseService
      .delete("/api/funcionarios/configuracoes-pontos/" + configuracaoPonto.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Configuração Ponto",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosPontos(ponto, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/funcionarios/pontos", {
        params: {
          idFuncionario: ponto ? ponto.idFuncionario : null,
          ano: ponto ? ponto.ano : null,
          mes: ponto ? ponto.mes : null,
          pendente: ponto ? ponto.pendente : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar pontos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarTarefaOnline(ponto) {
    return baseService
      .post("/api/funcionarios/pontos/tarefa-online", ponto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar tarefa online",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvarPonto(ponto) {
    if (ponto.id) {
      return baseService
        .put("/api/funcionarios/pontos/" + ponto.id, ponto)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Ponto", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/funcionarios/pontos", ponto)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Ponto", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  tiposPeriodosPontos() {
    let tipos = [];
    return baseService
      .get("/api/funcionarios/pontos/tipos/")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoPeriodoPonto(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos períodos do ponto"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  horasPrevistasPonto(idTipoPeriodoPonto) {
    return baseService
      .get(
        "/api/funcionarios/pontos/horas-previstas/tipos/" + idTipoPeriodoPonto
      )
      .then(response => {
        if (response && response.data) {
          return new Ponto(response.data);
        }
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível recuperar horas previstas"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarPonto(id) {
    return baseService
      .get("/api/funcionarios/pontos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Ponto(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Ponto");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  anosPontosCadastrados(idFuncionario) {
    return baseService
      .get("/api/funcionarios/pontos/anos", {
        params: {
          idFuncionario: idFuncionario ? idFuncionario : null
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível recuperar os anos de pontos cadastrados"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  mesesPontosCadastrados(idFuncionario, ano) {
    return baseService
      .get("/api/funcionarios/pontos/meses", {
        params: {
          idFuncionario: idFuncionario ? idFuncionario : null,
          ano: ano ? ano : null
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível recuperar os meses de pontos cadastrados"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosPontosParaHomologacao(ponto, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/funcionarios/pontos/homologacoes", {
        params: {
          idFuncionario: ponto ? ponto.idFuncionario : null,
          ano: ponto ? ponto.ano : null,
          mes: ponto ? ponto.mes : null,
          pendente: ponto ? ponto.pendente : null,
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
          "Erro ao tentar listar pontos para homologação"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  liberarPonto(ponto) {
    return baseService
      .post("/api/funcionarios/pontos/homologacoes/libera", ponto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar liberar Ponto(s)", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cancelarLiberacaoPonto(ponto) {
    return baseService
      .post("/api/funcionarios/pontos/homologacoes/libera/cancela", ponto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cancelar liberação do(s) Ponto(s)",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  homologarPonto(ponto) {
    return baseService
      .post("/api/funcionarios/pontos/homologacoes/homologa", ponto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar homologar Ponto(s)", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cancelarHomologacaoPonto(ponto) {
    return baseService
      .post("/api/funcionarios/pontos/homologacoes/homologa/cancela", ponto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cancelar homologação do(s) Ponto(s)",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodosPontosAusentes(ausente) {
    let ausentes = [];
    return baseService
      .get("/api/funcionarios/pontos/ausentes", {
        params: {
          idFuncionario: ausente ? ausente.idFuncionario : null,
          ano: ausente ? ausente.ano : null,
          mes: ausente ? ausente.mes : null,
          sabadoSuprimido: ausente ? ausente.sabadoSuprimido : null,
          domingoSuprimido: ausente ? ausente.domingoSuprimido : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(a => {
            ausentes.push(new Ausente(a));
          });
        }
        return ausentes;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar ausentes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarPontosAusentes(ausentes) {
    return baseService
      .post("/api/funcionarios/pontos/ausentes", ausentes)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar gerar os pontos ausentes",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
