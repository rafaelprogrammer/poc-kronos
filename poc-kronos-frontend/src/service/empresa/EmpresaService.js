import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
export default {
  listarTodos(empresa, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/empresas", {
        params: {
          id: empresa ? empresa.id : null,
          cnpj: empresa ? empresa.cnpj : null,
          nomeFantasia: empresa ? empresa.nomeFantasia : null,
          razaoSocial: empresa ? empresa.razaoSocial : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar empresas");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(empresa) {
    return baseService
      .post("/api/empresas/", empresa)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar salvar Empresa", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(empresa) {
    return baseService
      .delete("/api/empresas/" + empresa.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir Empresa", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
