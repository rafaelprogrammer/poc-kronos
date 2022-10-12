import Vue from 'vue';

class EventoCalendario {
  constructor(dados) {
    this.dataFinal = null;
    this.dataInicio = null;
    this.horaInicio = null;
    this.horaFinal = null;
    if (dados) {
      this.converterSaidaDataHoraInicio(dados.dataHoraInicio);
      this.converterSaidaDataHoraFinal(dados.dataHoraFinal);
    }
    this.id = dados ? dados.id : null;
    this.name = dados ? dados.descricao : null;
    this.details = dados ? dados.descricao : null;
    this.color = dados ? dados.corEvento : null;
    this.data = dados ? dados.data : null;
    this.diaLetivo = dados ? dados.diaLetivo : null;
    this.descricao = dados ? dados.descricao : null;
    this.idCategoriaEvento = dados ? dados.idCategoriaEvento : null;
    this.idCalendario = dados ? dados.idCalendario : null;
    this.idEventoInicial = dados ? dados.idEventoInicial : null;
    this.idEventoSequente = dados ? dados.idEventoSequente : null;
    this.diaTodo = dados ? dados.diaTodo : null;
  }

  converterEntradaDataHora() {
    this.converterEntradaDataHoraInicio();
    this.converterEntradaDataHoraFinal();
  }

  converterEntradaDataHoraInicio() {
    this.dataHoraInicio = `${this.dataInicio} ${this.horaInicio}`;
  }

  converterEntradaDataHoraFinal() {
    this.dataHoraFinal = `${this.dataFinal} ${this.horaFinal}`;
  }

  converterSaidaDataHoraInicio(dataHoraInicio) {
    const arrayDataHora = dataHoraInicio.split(' ');
    const hora = arrayDataHora[1];
    this.horaInicio = hora;
    const arrayData = arrayDataHora[0].split('/');
    const data = arrayDataHora[0];
    this.dataInicio = data;
    this.start = `${arrayData[2]}-${arrayData[1]}-${arrayData[0]} ${hora}`;
  }

  converterSaidaDataHoraFinal(dataHoraFinal) {
    const arrayDataHora = dataHoraFinal.split(' ');
    const hora = arrayDataHora[1];
    this.horaFinal = hora;
    const arrayData = arrayDataHora[0].split('/');
    const data = arrayDataHora[0];
    this.dataFinal = data;
    this.end = `${arrayData[2]}-${arrayData[1]}-${arrayData[0]} ${hora}`;
  }

  static intervaloMesmoMes(dataInicio, dataFinal) {
    const mesInicio = dataInicio.split('/')[1];
    const mesFinal = dataFinal.split('/')[1];
    return mesInicio === mesFinal;
  }

  validarIntervaloCalendario(dataInicioCalendario, dataFinalCalendario) {
    const dtInicio = new Date(
      this.dataInicio
        .split('/')
        .reverse()
        .join('/'),
    );
    const dtFim = new Date(
      this.dataFinal
        .split('/')
        .reverse()
        .join('/'),
    );
    const dtInicioCalendario = new Date(
      dataInicioCalendario
        .split('/')
        .reverse()
        .join('/'),
    );
    const dtFimCalendario = new Date(
      dataFinalCalendario
        .split('/')
        .reverse()
        .join('/'),
    );
    if (
      Vue.moment(dtInicio).isBefore(dtInicioCalendario)
      || Vue.moment(dtInicio).isAfter(dtFimCalendario)
      || Vue.moment(dtFim).isBefore(dtInicioCalendario)
      || Vue.moment(dtFim).isAfter(dtFimCalendario)
    ) {
      return false;
    }
    return true;
  }

  static validations(required, horaFinalMenor, maxLength, dataFimMenor) {
    return {
      idCalendario: { required },
      dataInicio: { required },
      dataFinal: { required, dataFimMenor: dataFimMenor('dataInicio') },
      descricao: { required, maxLength: maxLength(100) },
      horaInicio: { required },
      horaFinal: { required, horaFinalMenor: horaFinalMenor('horaInicio') },
      idCategoriaEvento: { required },
    };
  }

  static validationMessages() {
    return {
      dataInicio: {
        required: 'Data Início é obrigatório',
      },
      dataFinal: {
        required: 'Data Final é obrigatória',
        dataFimMenor: 'Data Final não pode ser menor que a Data Inicial',
      },
      horaInicio: {
        required: 'Hora Início obrigatório',
      },
      horaFinal: {
        required: 'Hora Final é obrigatório',
        horaFinalMenor: 'Hora Final não pode ser menor que a Hora Inicial',
      },
      descricao: {
        required: 'Descrição é obrigatória',
        maxLength: 'Descrição só aceito no máximo 100 caracteres',
      },
      idCategoriaEvento: {
        required: 'Categoria é obrigatória',
      },
      idCalendario: {
        required: 'Calendário é obrigatório',
      },
    };
  }
}

export default EventoCalendario;
