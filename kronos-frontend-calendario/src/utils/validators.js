import Vue from 'vue';

// eslint-disable-next-line import/prefer-default-export
export const dataFimMenor = dataInicioProperties => (dataFim, object) => {
  if (!object || object[dataInicioProperties] == null || dataFim == null) {
    return true;
  }
  /* eslint-disable */
  const dtInicio = new Date(object[dataInicioProperties].split('/').reverse().join('/'));
  const dtFim = new Date(dataFim.split('/').reverse().join('/'));
  /* eslint-enable */
  return !Vue.moment(dtFim).isBefore(dtInicio);
};

export const horaFinalMenor = horaInicioProperties => (horaFim, object) => {
  if (!object || object[horaInicioProperties] == null || horaFim == null) {
    return true;
  }
  const inicio = Vue.moment(object[horaInicioProperties], 'HH:mm');
  const fim = Vue.moment(horaFim, 'HH:mm');
  return !Vue.moment(fim).isBefore(inicio);
};
