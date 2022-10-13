import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoTelefonePessoa from "@/model/pessoa/TipoTelefonePessoa";
import Operadora from "@/model/pessoa/Operadora";
import TipoUso from "@/model/pessoa/TipoUso";
export default {
  listarTodos(telefonePessoa, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/telefone-pessoa", {
        params: {
          numero: telefonePessoa ? telefonePessoa.numero : null,
          idPessoa: telefonePessoa ? telefonePessoa.idPessoa : null,
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
          "Erro ao tentar listar Telefones da Pessoa"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/telefone-pessoa/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoTelefonePessoa(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de telefones"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(telefone) {
    return baseService
      .post("/api/telefone-pessoa/", telefone)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar salvar Telefone da Pessoa",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(telefone) {
    return baseService
      .delete("/api/telefone-pessoa/" + telefone.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar excluir Telefone da Pessoa",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  operadoras() {
    let operadoras = [];
    return baseService
      .get("/api/telefone-pessoa/operadoras")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            operadoras.push(new Operadora(d));
          });
        }
        return operadoras;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar as operadoras");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposUsos() {
    let tipos = [];
    return baseService
      .get("/api/telefone-pessoa/tipos-usos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoUso(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os tipos de usos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
