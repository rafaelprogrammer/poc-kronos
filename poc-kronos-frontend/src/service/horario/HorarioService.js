import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import ResultadoPesquisa from "@/model/comum/ResultadoPesquisa";
import HorariosDiaDaSemana from "@/model/horario/HorariosDiaDaSemana";
import DadosVinculacaoHorariosHorasAtividades from "@/model/horario/DadosVinculacaoHorariosHorasAtividades";

export default {
  salvar(horario, horariosDiaDaSemana) {
    const dados = new DadosVinculacaoHorariosHorasAtividades(
      horario,
      horariosDiaDaSemana
    );
    return baseService
      .post("/api/horarios", dados)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar registrar Horários", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(horario, numeroPagina, qtdTotal) {
    return baseService
      .get("/api/horarios", {
        params: {
          idTurma: horario ? horario.idTurma : null,
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
        mixinsMessage.methods.error("Erro ao tentar listar horários");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarHorasAtividadesDiaDaSemana(horario, inclusao) {
    return baseService
      .get("/api/horarios/horarios-dias-semana", {
        params: {
          inclusao: inclusao,
          idFuncionario: horario ? horario.idFuncionario : null,
          idTurma: horario ? horario.idTurma : null,
          idDisciplina: horario ? horario.idDisciplina : null,
          idTipoDiaSemana: horario ? horario.idTipoDiaSemana : null,
          idTipoMatrizHorario: horario ? horario.idTipoMatrizHorario : null,
          dataInicial: horario ? horario.dataInicial : null,
          dataFinal: horario ? horario.dataFinal : null
        }
      })
      .then(response => {
        if (response && response.data) {
          return new HorariosDiaDaSemana(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar horários", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(horario) {
    return baseService
      .delete(
        "/api/horarios/" +
          horario.id +
          "/hora-atividade/" +
          horario.idHoraAtividade
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("", error);
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
