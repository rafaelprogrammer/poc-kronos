import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import TipoConteudo from "@/model/arquivo/TipoConteudo";
import TipoArquivo from "@/model/arquivo/TipoArquivo";
import Arquivo from "@/model/arquivo/Arquivo";
export default {
  listarTodos(arquivo, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/arquivos", {
        params: {
          idPessoa: arquivo ? arquivo.idPessoa : null,
          idTipoArquivo: arquivo ? arquivo.idTipoArquivo : null,
          idTipoConteudo: arquivo ? arquivo.idTipoConteudo : null,
          nome: arquivo ? arquivo.nome : null,
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
  upload(arquivo) {
    let formData = new FormData();
    formData.append("file", arquivo.dados);
    arquivo.dados = null;
    formData.append("json", JSON.stringify(arquivo));
    return baseService
      .post("/api/arquivos", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      })
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar fazer upload do arquivo",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(pessoa) {
    return baseService
      .delete("/api/arquivos/" + pessoa.id)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar excluir arquivo", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  buscarPorId(id) {
    return baseService
      .get("/api/arquivos/" + id)
      .then(response => {
        if (response && response.data) {
          return new Arquivo(response.data);
        }
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de arquivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tipos() {
    let tipos = [];
    return baseService
      .get("/api/arquivos/tipos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoArquivo(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de arquivos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  tiposConteudos() {
    let tipos = [];
    return baseService
      .get("/api/arquivos/tipos-conteudos")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            tipos.push(new TipoConteudo(d));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os tipos de conteudos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  buscarFoto(idPessoa) {
    return baseService
      .get("/api/arquivos/foto/" + idPessoa)
      .then(response => {
        if (response && response.data) {
          return new Arquivo(response.data);
        }
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível buscar foto da Pessoa");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
