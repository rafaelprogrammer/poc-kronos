import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import CursoDocumento from "@/model/curso/curso-documento/CursoDocumento";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  listarTodos(cursoDocumento, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos/documentos", {
        params: {
          id: cursoDocumento ? cursoDocumento.id : null,
          idCurso: cursoDocumento ? cursoDocumento.idCurso : null,
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
          "Erro ao tentar listar documentos do curso"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/documentos/" + id)
      .then(response => {
        if (response && response.data) {
          return new CursoDocumento(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar documentos do curso"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(cursoDocumento) {
    if (cursoDocumento.id) {
      return baseService
        .put("/api/cursos/documentos/" + cursoDocumento.id, cursoDocumento)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar documentos do curso",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos/documentos", cursoDocumento)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar documentos do curso",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(cursoDocumento) {
    return baseService
      .delete("/api/cursos/documentos/" + cursoDocumento.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir documento do curso",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
