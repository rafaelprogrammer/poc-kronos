import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Fase from "@/model/curso/fase/Fase";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  comboFasesExecucao(idPeriodoExecucao) {
    return baseService
      .get("/api/cursos/periodos/fases/combo-fases-execucao", {
        params: {
          idPeriodoExecucao: idPeriodoExecucao
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
          "Erro ao tentar listar Fases por período de execucação"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  combo(idPeriodo) {
    return baseService
      .get("/api/cursos/periodos/fases/combo", {
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
        mixinsMessage.methods.error("Erro ao tentar listar Fases");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(fase, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos/periodos/fases", {
        params: {
          id: fase ? fase.id : null,
          idPeriodo: fase ? fase.idPeriodo : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Fases");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/periodos/fases/" + id)
      .then(response => {
        if (response && response.data) {
          return new Fase(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Fase");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(fase) {
    if (fase.id) {
      return baseService
        .put("/api/cursos/periodos/fases/" + fase.id, fase)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Fase", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos/periodos/fases", fase)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Fase", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(fase) {
    return baseService
      .delete("/api/cursos/periodos/fases/" + fase.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Fase", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
