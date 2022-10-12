import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Pessoa from "@/model/pessoa/Pessoa";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoGenero from "@/model/pessoa/TipoGenero";
import { dataURItoBlob } from "@/utils/helpers";
export default {
  listarTodosAlunos(pessoa, idCurso, idTurma, ano, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/pessoas/alunos", {
        params: {
          nome: pessoa ? pessoa.nome : null,
          cpf: pessoa ? pessoa.cpf : null,
          numeroRegistro: pessoa ? pessoa.numeroRegistro : null,
          idCurso: idCurso,
          idTurma: idTurma,
          ano: ano,
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
        mixinsMessage.methods.error("Erro ao tentar listar Pessoas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  oncomplete(valor) {
    let pessoa = {};
    pessoa.nome = valor;
    return this.listarTodos(pessoa);
  },
  foto(file, idPessoa) {
    let formData = new FormData();
    formData.append("file", dataURItoBlob(file));
    formData.append("idPessoa", idPessoa);
    return baseService
      .post("/api/pessoas/foto", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar cadastrar/alterar foto da pessoa",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(pessoa, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/pessoas", {
        params: {
          nome: pessoa ? pessoa.nome : null,
          cpf: pessoa ? pessoa.cpf : null,
          numeroRegistro: pessoa ? pessoa.numeroRegistro : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Pessoas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/pessoas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Pessoa(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Pessoa");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(pessoa) {
    if (pessoa.id) {
      return baseService
        .put("/api/pessoas/" + pessoa.id, pessoa)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar atualizar Pessoa", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    } else {
      return baseService
        .post("/api/pessoas", pessoa)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar cadastrar Pessoa", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  excluir(pessoa) {
    return baseService
      .delete("/api/pessoas/" + pessoa.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Pessoa", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposGeneros() {
    let generos = [];
    return baseService
      .get("/api/pessoas/generos")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            generos.push(new TipoGenero(p));
          });
        }
        return generos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de generos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
