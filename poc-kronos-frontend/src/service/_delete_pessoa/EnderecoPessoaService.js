import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoEnderecoPessoa from "@/model/pessoa/TipoEnderecoPessoa";
export default {
  listarTodos(enderecoPessoa, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/endereco-pessoa", {
        params: {
          cep: enderecoPessoa ? enderecoPessoa.cep : null,
          idPessoa: enderecoPessoa ? enderecoPessoa.idPessoa : null,
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
          "Erro ao tentar listar Endereços da Pessoa"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/endereco-pessoa/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoEnderecoPessoa(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de endereços"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(endereco) {
    return baseService
      .post("/api/endereco-pessoa/", endereco)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar salvar Endereço da Pessoa",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(endereco) {
    return baseService
      .delete("/api/endereco-pessoa/" + endereco.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Endereço da Pessoa",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
