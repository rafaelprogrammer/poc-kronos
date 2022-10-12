import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import CursoFuncionario from "@/model/curso/curso-funcionario/CursoFuncionario";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoFuncao from "@/model/curso/curso-funcionario/TipoFuncao";
export default {
  listarTodos(cursoFuncionario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/cursos/funcionarios", {
        params: {
          id: cursoFuncionario ? cursoFuncionario.id : null,
          idCurso: cursoFuncionario ? cursoFuncionario.idCurso : null,
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
          "Erro ao tentar listar Cursos dos Funcionários"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/cursos/funcionarios/" + id)
      .then(response => {
        if (response && response.data) {
          return new CursoFuncionario(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar editar Curso do Funcionário"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(cursoFuncionario) {
    if (cursoFuncionario.id) {
      return baseService
        .put(`api/cursos/funcionarios/${cursoFuncionario.id}`, cursoFuncionario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Curso do Funcionário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/cursos/funcionarios", cursoFuncionario)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar cadastrar Curso do Funcionário",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(cursoFuncionario) {
    return baseService
      .delete("/api/cursos/funcionarios/" + cursoFuncionario.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Curso do Funcionário",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/cursos/funcionarios/tipos")
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
  }
};
