import baseService from "../BaseService";
import TurmaDiarioFrequencia from "@/model/turma/TurmaDiarioFrequencia";
import mixinsMessage from "@/mixins/message";

export default {
  comboParaDiarioFrequencia() {
    let disciplinas = [];
    return baseService
      .get("/api/turmas/combo-turmas-diario-frequencia")
      .then(response => {
        if (response && response.data) {
          response.data.map(d => {
            disciplinas.push(new TurmaDiarioFrequencia(d));
          });
        }
        return disciplinas;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar disciplinas para o diário/frequência"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  comboParaModulosDosProfessores(idPeriodoExecucao) {
    return baseService
      .get(
        "/api/turmas/combo-modulos-professores/periodo-execucao/" +
          idPeriodoExecucao
      )
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar turmas para módulos utilizados por professores"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
