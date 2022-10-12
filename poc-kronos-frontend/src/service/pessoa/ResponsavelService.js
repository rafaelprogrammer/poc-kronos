import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoResponsavel from "@/model/pessoa/TipoResponsavel";
import Responsavel from "@/model/pessoa/Responsavel";
export default {
  listarTodos(responsavel, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/responsaveis", {
        params: {
          idPessoa: responsavel ? responsavel.idPessoa : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Responsaveis");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/responsaveis/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoResponsavel(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de responsaveis"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  responsaveisFinanceiros(idMatricula) {
    let tipos = [];
    return baseService
      .get("/api/responsaveis/responsaveis-financeiros", {
        params: {
          idMatricula: idMatricula
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new Responsavel(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os responsaveis financeiros"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(responsavel) {
    if (responsavel.id) {
      return baseService
        .put("/api/responsaveis/" + responsavel.id, responsavel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar responsavel",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/responsaveis", responsavel)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar salvar responsavel",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  editar(id) {
    return baseService
      .get("/api/responsaveis/" + id)
      .then(response => {
        if (response && response.data) {
          return new Responsavel(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Responsavel");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(responsavel) {
    return baseService
      .delete("/api/responsaveis/" + responsavel.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir responsavel",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaOcorrenciaResponsaveisCiencia(dataOcorrencia, idPessoaSelecionada) {
    return baseService
      .get("/api/responsaveis/ocorrencia/responsaveis-ciencia", {
        params: {
          idPessoa: idPessoaSelecionada,
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
          "Erro ao tentar listar Responsáveis Ciência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
