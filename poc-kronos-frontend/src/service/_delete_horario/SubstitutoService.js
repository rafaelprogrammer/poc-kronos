import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Substituto from "@/model/horario/Substituto";
import SubstitutoResumo from "@/model/horario/SubstitutoResumo";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  listarTodos(substituto, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/horarios/substitutos", {
        params: {
          idHorario: substituto ? substituto.idHorario : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Substitutos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/horarios/substitutos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Substituto(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Substituto");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(substituto) {
    return baseService
      .post("/api/horarios/substitutos", substituto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(dadosExcluiSubstituto) {
    return baseService
      .post("/api/horarios/substitutos/exclui", dadosExcluiSubstituto)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Substituto", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboProfessoresHorario(idDisciplina, idFuncionarioDoHorario) {
    let tipos = [];
    return baseService
      .get("/api/horarios/substitutos/combo-professores-horario", {
        params: {
          idDisciplina: idDisciplina,
          idFuncionarioDoHorario: idFuncionarioDoHorario
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new SubstitutoResumo(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os professores para os substitutos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
