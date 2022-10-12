import baseService from "./BaseService";
import mixinsMessage from "@/mixins/message";
import Perfil from "@/model/Perfil";
import Pais from "@/model/comum/Pais";
import Estado from "@/model/comum/Estado";
import Cidade from "@/model/comum/Cidade";
export default {
  cep(numero) {
    return baseService
      .get("/api/apoio/cep/" + numero)
      .then(response => {
        return response.data;
      })
      .catch(function(error) {
        if (error.response.status === 400) {
          mixinsMessage.methods.error("CEP não localizado - " + numero);
        } else {
          mixinsMessage.methods.error(
            "Erro ao tentar localizar o cep - " + numero
          );
        }
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  perfis() {
    let perfis = [];
    return baseService
      .get("/api/apoio/perfis")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            perfis.push(new Perfil(p));
          });
        }
        return perfis;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os perfis");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  paises(id) {
    let paises = [];
    return baseService
      .get("/api/apoio/pais", {
        params: {
          id: id ? id : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            paises.push(new Pais(p));
          });
          return paises;
        }
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os paises");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  estados(id) {
    let estados = [];
    return baseService
      .get("/api/apoio/estado", {
        params: {
          id: id ? id : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            estados.push(new Estado(d));
          });
        }
        return estados;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os estados");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  cidades(id, idEstado) {
    let cidades = [];
    return baseService
      .get("/api/apoio/cidade", {
        params: {
          id: id ? id : null,
          idEstado: idEstado ? idEstado : null
        }
      })
      .then(response => {
        if (response && response.data && response.data.dados) {
          response.data.dados.map(d => {
            cidades.push(new Cidade(d));
          });
        }
        return cidades;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar as cidades");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  versaoDoSistema() {
    return baseService
      .get("/api/apoio/version-system/")
      .then(response => {
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao recuperar a versão ", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
