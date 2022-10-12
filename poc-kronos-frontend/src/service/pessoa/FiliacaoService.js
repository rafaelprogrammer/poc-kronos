import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoFiliacao from "@/model/pessoa/TipoFiliacao";
export default {
  listarTodos(filiacao, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/filiacoes", {
        params: {
          idPessoaFilho: filiacao ? filiacao.idPessoaFilho : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Filiacoes");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/filiacoes/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoFiliacao(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de filiacoes"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(filiacao) {
    return baseService
      .post("/api/filiacoes/", filiacao)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar salvar Filiacao", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(filiacao) {
    return baseService
      .delete("/api/filiacoes/" + filiacao.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Filiacao", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
