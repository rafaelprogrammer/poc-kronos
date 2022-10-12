import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Matricula from "@/model/matricula/Matricula";
import MatriculaCanceladaTransferida from "@/model/matricula/MatriculaCanceladaTransferida";
import MatriculaOcorrencia from "@/model/matricula/MatriculaOcorrencia";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoSituacaoMatricula from "@/model/matricula/TipoSituacaoMatricula";
import TipoResultado from "@/model/matricula/TipoResultado";
import TipoCredito from "@/model/matricula/TipoCredito";
import TipoPrograma from "@/model/matricula/TipoPrograma";
import CreditoContrato from "@/model/matricula/CreditoContrato";
export default {
  comboParaOcorrencia(idPessoaSelecionada) {
    return baseService
      .get(
        "/api/matriculas/ocorrencia/cursos/combo/pessoa/" + idPessoaSelecionada
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar Matriculas para Ocorrência por pessoa selecionada"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  buscarParaOcorrencia(idPessoaSelecionada) {
    return baseService
      .get("/api/matriculas/ocorrencia/pessoa/" + idPessoaSelecionada)
      .then(response => {
        if (response && response.data) {
          return new MatriculaOcorrencia(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar Matriculas para Ocorrência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarPendentesContrato(idInstituicao) {
    return baseService
      .get("/api/matriculas/pendentes-contrato", {
        params: {
          idInstituicao: idInstituicao
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
          "Erro ao tentar listar Matriculas Pendentes Contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarCreditosPendentes(
    idPeriodoExecucao,
    idMatricula,
    idTurma,
    idTipoCredito,
    idTipoPrograma
  ) {
    let creditos = [];
    return baseService
      .get("/api/matriculas/gera-creditos-pendentes", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao,
          idMatricula: idMatricula,
          idTurma: idTurma,
          idTipoCredito: idTipoCredito,
          idTipoPrograma: idTipoPrograma
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            creditos.push(new CreditoContrato(p));
          });
        }
        return creditos;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar gerar creditos", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarCreditos(contrato, idTurma) {
    let creditos = [];
    return baseService
      .get("/api/matriculas/gera-creditos", {
        params: {
          idContrato: contrato.id,
          idPeriodoExecucao: contrato.idPeriodoExecucao,
          idTurma: idTurma,
          idTipoCredito: contrato.idTipoCredito,
          idTipoPrograma: contrato.idTipoPrograma
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            creditos.push(new CreditoContrato(p));
          });
        }
        return creditos;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar gerar creditos", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  validar(matricula) {
    return baseService
      .put("/api/matriculas/" + matricula.id + "/valida")
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
        return;
      });
  },
  reativar(matricula) {
    return baseService
      .put("/api/matriculas/" + matricula.id + "/reativa")
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
        return;
      });
  },
  listarTodos(matricula, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/matriculas", {
        params: {
          id: matricula ? matricula.id : null,
          idPessoa: matricula ? matricula.idPessoa : null,
          numeroRegistroPessoa: matricula.numeroRegistroPessoa
            ? matricula.numeroRegistroPessoa
            : null,
          cpfPessoa: matricula ? matricula.cpfPessoa : null,
          nomePessoa: matricula ? matricula.nomePessoa : null,
          idCurso: matricula ? matricula.idCurso : null,
          anoInicioCurso: matricula ? matricula.anoInicioCurso : null,
          idTipoSituacaoMatricula: matricula
            ? matricula.idTipoSituacaoMatricula
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
        mixinsMessage.methods.error("Erro ao tentar listar Matriculas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/matriculas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Matricula(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Matricula");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(matricula) {
    if (matricula.id) {
      return baseService
        .put("/api/matriculas/" + matricula.id, matricula)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("", error);
          // eslint-disable-next-line no-console
          console.error(error);
          return;
        });
    } else {
      return baseService
        .post("/api/matriculas", matricula)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("", error);
          // eslint-disable-next-line no-console
          console.error(error);
          return;
        });
    }
  },
  excluir(matricula) {
    return baseService
      .delete("/api/matriculas/" + matricula.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Matricula", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposSituacoesMatriculas() {
    let tipos = [];
    return baseService
      .get("/api/matriculas/tipos-situacoes")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoSituacaoMatricula(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de situacoes de matriculas"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposResultados() {
    let tipos = [];
    return baseService
      .get("/api/matriculas/tipos-resultados")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoResultado(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de resultados"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposCreditos() {
    let tipos = [];
    return baseService
      .get("/api/matriculas/tipos-creditos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoCredito(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de creditos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposProgramas() {
    let tipos = [];
    return baseService
      .get("/api/matriculas/tipos-programas")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoPrograma(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de programas"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarParaTransferenciaOuCancelamento(id) {
    return baseService
      .get("/api/matriculas/" + id + "/transferencias/cancelamentos")
      .then(response => {
        if (response && response.data) {
          return new MatriculaCanceladaTransferida(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar para cancelamento ou transferência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cancelar(matriculaCanceladaTransferida) {
    return baseService
      .post("/api/matriculas/cancela", matriculaCanceladaTransferida)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
        return;
      });
  },
  transferir(matriculaCanceladaTransferida) {
    return baseService
      .post("/api/matriculas/tranfere", matriculaCanceladaTransferida)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
        return;
      });
  }
};
