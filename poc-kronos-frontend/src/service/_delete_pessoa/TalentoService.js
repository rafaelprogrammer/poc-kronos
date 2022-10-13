import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoTalento from "@/model/pessoa/TipoTalento";
export default {
  buscarTalentosDaPessoa(idPessoa) {
    let idsTiposTalentos = [];
    return baseService
      .get(`/api/talentos/pessoa/${idPessoa}`)
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            idsTiposTalentos.push(d.idTipoTalento);
          });
        }
        return idsTiposTalentos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os talentos da pessoa"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(talento, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/talentos", {
        params: {
          idPessoa: talento ? talento.idPessoa : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Talentos");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/talentos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoTalento(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de talentos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(talento) {
    return baseService
      .post("/api/talentos/", talento)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar salvar talento", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(talento) {
    return baseService
      .delete(
        "/api/talentos/" + talento.idPessoa + "/idtipo/" + talento.idTipoTalento
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir talento", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
