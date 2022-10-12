import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Contrato from "@/model/contrato/Contrato";
import ContratoMatricula from "@/model/contrato/ContratoMatricula";
import ConvenioContrato from "@/model/contrato/ConvenioContrato";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoSituacaoContrato from "@/model/contrato/TipoSituacaoContrato";
import TipoFormaPagamento from "@/model/contrato/TipoFormaPagamento";
export default {
  reativar(contrato) {
    return baseService
      .put("/api/contratos/" + contrato.id + "/reativa")
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
  confirmarAssinatura(contrato) {
    if (contrato.id) {
      return baseService
        .put("/api/contratos/" + contrato.id + "/confirma-assinatura")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar confirmar assinatura",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  aprovar(contrato) {
    if (contrato.id) {
      return baseService
        .put("/api/contratos/" + contrato.id + "/aprova")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar aprovar contrato", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  finalizarAnalise(contrato) {
    if (contrato.id) {
      return baseService
        .put("/api/contratos/" + contrato.id + "/finaliza-analise")
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar finalizar análise",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  atualizarAnalise(contrato) {
    if (contrato.id) {
      return baseService
        .put("/api/contratos/" + contrato.id + "/atualiza-analise", contrato)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Contrato",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluirContratoConvenio(idContrato, idConvenio) {
    if (idContrato && idConvenio) {
      return baseService
        .delete("/api/contratos/" + idContrato + "/convenio/" + idConvenio)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar excluir Contrato Convenio",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  listarConveniosContrato(idContrato, dataContrato) {
    let conveniosContratos = [];
    return baseService
      .get("/api/contratos/convenios", {
        params: {
          idContrato: idContrato,
          dataContrato: dataContrato
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(c => {
            conveniosContratos.push(new ConvenioContrato(c));
          });
          return conveniosContratos;
        }
        return conveniosContratos;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar os convenios para o contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cacularDiaVencimento(numerosParcelas, dataUltimaParcela) {
    return baseService
      .get("/api/contratos/calcula-dia-vencimento", {
        params: {
          numerosParcelas: numerosParcelas,
          dataUltimaParcela: dataUltimaParcela
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
          "Erro ao tentar calcular o dia do vencimento e a data da ultima parcela"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  enviarFinanceiro(contrato) {
    return baseService
      .put("/api/contratos/" + contrato.id + "/envia-financeiro")
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
  validar(contrato) {
    return baseService
      .put("/api/contratos/" + contrato.id + "/valida")
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
  listarTodos(contrato, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/contratos", {
        params: {
          idMatricula: contrato ? contrato.idMatricula : null,
          idInstituicao: contrato ? contrato.idInstituicao : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Contratos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/contratos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Contrato(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Contrato");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editarContratoMatricula(id) {
    return baseService
      .get("/api/contratos/" + id + "/matricula")
      .then(response => {
        if (response && response.data) {
          return new ContratoMatricula(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Contrato");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(contrato) {
    if (contrato.id) {
      return baseService
        .put("/api/contratos/" + contrato.id, contrato)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Contrato",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/contratos", contrato)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Contrato",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(contrato) {
    return baseService
      .delete("/api/contratos/" + contrato.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Contrato", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/contratos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoSituacaoContrato(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de situacoes de contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposFormasPagamento() {
    let tipos = [];
    return baseService
      .get("/api/contratos/tipos-formas-pagamento")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new TipoFormaPagamento(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de formas de pagamento"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
