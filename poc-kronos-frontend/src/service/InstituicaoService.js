import baseService from "./BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import Mencao from "@/model/instituicao/Mencao";
export default {
  listar() {
    return baseService
      .get("/api/instituicao")
      .then(response => {
        if (response && response.data) {
          return new ResultadoPesquisa(response.data);
        }
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar as instituições");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarMencoes() {
    let mencoes = [];
    return baseService
      .get("/api/instituicao/mencoes")
      .then(response => {
        if (response && response.data) {
          response.data.map(m => {
            mencoes.push(new Mencao(m));
          });
        }
        return mencoes;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Menções");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
