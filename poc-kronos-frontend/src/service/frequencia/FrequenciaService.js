import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";

export default {
  criarTiposAtestadosTodos(idDisciplina, idAtividade, idSubFaseExecucao, anoTurma) {
    return baseService
      .post(
        `/api/frequencias/tipos-atestados/todos/sub-fase-execucao/${idSubFaseExecucao}/atividade/${idAtividade}/disciplina/${idDisciplina}/ano-turma/${anoTurma}`
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar alterar Frequências para TP"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoTPTodos(idDisciplina, idAtividade, idSubFaseExecucao, anoTurma) {
    console.log(anoTurma);
    return baseService
      .post(
        `/api/frequencias/tp/todos/sub-fase-execucao/${idSubFaseExecucao}/atividade/${idAtividade}/disciplina/${idDisciplina}/ano-turma/${anoTurma}`
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar alterar Frequências para TP"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoTFTodos(idDisciplina, idAtividade, idSubFaseExecucao, anoTurma) {
    return baseService
      .post(
        `/api/frequencias/tf/todos/sub-fase-execucao/${idSubFaseExecucao}/atividade/${idAtividade}/disciplina/${idDisciplina}/ano-turma/${anoTurma}`
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar alterar Frequências para TF"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoADTodos(idDisciplina, idAtividade, idSubFaseExecucao, anoTurma) {
    return baseService
      .post(
        `/api/frequencias/ad/todos/sub-fase-execucao/${idSubFaseExecucao}/atividade/${idAtividade}/disciplina/${idDisciplina}/ano-turma/${anoTurma}`
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar alterar Frequências para AD"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  limparTodos(idDisciplina, idAtividade, idSubFaseExecucao) {
    return baseService
      .post(
        `/api/frequencias/limpar/todos/sub-fase-execucao/${idSubFaseExecucao}/atividade/${idAtividade}/disciplina/${idDisciplina}`
      )
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar redefinar as frequências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  listarTodos(idDisciplina, idAtividade, idSubFaseExecucao, anoTurma) {
    return baseService
      .get("/api/frequencias", {
        params: {
          idDisciplina: idDisciplina,
          idAtividade: idAtividade,
          idSubFaseExecucao: idSubFaseExecucao,
          anoTurma: anoTurma
        }
      })
      .then(response => {
        if (response && response.data) {
          return response.data;
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar listar Frequências");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoTP(frequencia) {
    return baseService
      .post("/api/frequencias/tp", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar Frequência TP",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoTF(frequencia) {
    return baseService
      .post("/api/frequencias/tf", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar Frequência TF",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  criarTipoAD(frequencia) {
    return baseService
      .post("/api/frequencias/ad", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar Frequência TAD",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  limpar(frequencia) {
    return baseService
      .post("/api/frequencias/limpar", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar redefinir a Frequência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  registrarReposicao(frequencia) {
    return baseService
      .post("/api/frequencias/registra-reposicao", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar reposição da Frequência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  anularReposicao(frequencia) {
    return baseService
      .post("/api/frequencias/anula-reposicao", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar registrar anulação de reposição da Frequência",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  atualizarIndividual(frequencia) {
    return baseService
      .post("/api/frequencias/atualiza-frequencia-individual", frequencia)
      .then(response => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar atualizar Frequência Individual",
          error
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }
};
