import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Nivel from "@/model/basecurricular/Nivel";
import FaixaAno from "@/model/basecurricular/FaixaAno";
import Componente from "@/model/basecurricular/Componente";
import CampoExperiencia from "@/model/basecurricular/CampoExperiencia";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";

export default {
  comboNiveis() {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/niveis/combo")
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Nivel(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error("Não foi possível listar os níveis");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  faixasAnos(faixaAno) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/faixas-anos/combo", {
        params: {
          idNivel: faixaAno ? faixaAno.idNivel : null
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new FaixaAno(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar as faixas de anos"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboComponentes(idNivel) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/componentes/combo", {
        params: {
          idNivel: idNivel
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new Componente(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os campos experiências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboCamposExperiencias(idNivel) {
    let tipos = [];
    return baseService
      .get("/api/basescurriculares/campos-experiencias/combo", {
        params: {
          idNivel: idNivel
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            tipos.push(new CampoExperiencia(p));
          });
        }
        return tipos;
      })
      .catch(error => {
        mixinsMessage.methods.error(
          "Não foi possível listar os campos experiências"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarCompetenciasPorIds(idsCompetencias) {
    return baseService
      .get("/api/basescurriculares/competencias/ids", {
        params: {
          ids: JSON.stringify(idsCompetencias)
            .replace("[", "")
            .replace("]", "")
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Competências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodasCompetencias(competencia, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/basescurriculares/competencias", {
        params: {
          id: competencia ? competencia.id : null,
          codigo: competencia ? competencia.codigo : null,
          idNivel: competencia ? competencia.idNivel : null,
          idComponente: competencia ? competencia.idComponente : null,
          ativo: competencia ? competencia.ativo : null,
          bncc: competencia ? competencia.bncc : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar Competências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
