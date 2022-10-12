class Turma {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.ano = dados ? dados.ano : null;
    this.sigla = dados ? dados.sigla : null;
    this.ativa = dados ? dados.ativa : null;
    this.aberta = dados ? dados.aberta : null;
    this.encerrada = dados ? dados.encerrada : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.tipoPeriodo = dados ? dados.tipoPeriodo : null;
    this.idCalendario = dados ? dados.idCalendario : null;
    this.calendario = dados ? dados.calendario : null;
    this.idTipoTurno = dados ? dados.idTipoTurno : null;
  }

  static validations(required) {
    return {
      sigla: { required },
      ano: { required },
      idPeriodoExecucao: { required },
      idCalendario: { required },
      idTipoTurno: { required }
    };
  }

  static validationMessages() {
    return {
      sigla: {
        required: "Sigla é obrigatória"
      },
      ano: {
        required: "Ano é obrigatório"
      },
      idPeriodoExecucao: {
        required: "Período Execução é obrigatória"
      },
      idCalendario: {
        required: "Calendário é obrigatório"
      },
      idTipoTurno: {
        required: "Turno é obrigatório"
      }
    };
  }
}
export default Turma;
