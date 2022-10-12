class ConfiguracaoPonto {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.idFuncionario = dados ? dados.idFuncionario : null;
    this.tarefaOnline = dados ? dados.tarefaOnline : null;
    this.horaInicialP1 = dados ? dados.horaInicialP1 : null;
    this.horaFinalP1 = dados ? dados.horaFinalP1 : null;
    this.horaInicialP2 = dados ? dados.horaInicialP2 : null;
    this.horaFinalP2 = dados ? dados.horaFinalP2 : null;
    this.horaInicialP3 = dados ? dados.horaInicialP3 : null;
    this.horaFinalP3 = dados ? dados.horaFinalP3 : null;
    this.dataInicialVigencia = dados ? dados.dataInicialVigencia : null;
    this.dataFinalVigencia = dados ? dados.dataFinalVigencia : null;
    this.cargaHorariaSemanal = dados ? dados.cargaHorariaSemanal : null;
    this.tolerancia = dados ? dados.tolerancia : null;
    this.qtdPonto = dados ? dados.qtdPonto : null;
  }

  static validations(required, numeric, dataFimMenor, horaFinalMenor) {
    return {
      idFuncionario: { required },
      tolerancia: { required },
      dataInicialVigencia: { required },
      dataFinalVigencia: {
        required,
        dataFimMenor: dataFimMenor("dataInicialVigencia")
      },
      cargaHorariaSemanal: { required, numeric },
      horaFinalP1: {
        horaFinalMenor: horaFinalMenor("horaInicialP1", true)
      },
      horaInicialP2: {
        horaFinalMenor: horaFinalMenor("horaFinalP1", true)
      },
      horaFinalP2: {
        horaFinalMenor: horaFinalMenor("horaInicialP2", true)
      },
      horaInicialP3: {
        horaFinalMenor: horaFinalMenor("horaFinalP2", true)
      },
      horaFinalP3: {
        horaFinalMenor: horaFinalMenor("horaInicialP3", true)
      }
    };
  }

  static validationMessages() {
    return {
      idFuncionario: {
        required: "Funcionário é obrigatório"
      },
      tolerancia: {
        required: "Tolerância é obrigatória"
      },
      dataInicialVigencia: {
        required: "Data Inicial Vigência é obrigatória"
      },
      dataFinalVigencia: {
        required: "Data Final Vigência é obrigatória",
        dataFimMenor:
          "Data Final de Vigência não pode ser menor que a Data Inicial de Vigência"
      },
      cargaHorariaSemanal: {
        required: "CH Semanal é obrigatória",
        numeric: "Somente número"
      },
      horaFinalP1: {
        horaFinalMenor: "Hora Final P1 não pode ser menor ou igual a Hora Inicial P1"
      },
      horaInicialP2: {
        horaFinalMenor: "Hora Inicial P2 não pode ser menor ou igual a Hora Final P1"
      },
      horaFinalP2: {
        horaFinalMenor: "Hora Final P2 não pode ser menor ou igual a Hora Inicial P2"
      },
      horaInicialP3: {
        horaFinalMenor: "Hora Inicial P3 não pode ser menor ou igual a Hora Final P2"
      },
      horaFinalP3: {
        horaFinalMenor: "Hora Final P3 não pode ser menor ou igual a Hora Inicial P3"
      }
    };
  }
}
export default ConfiguracaoPonto;
