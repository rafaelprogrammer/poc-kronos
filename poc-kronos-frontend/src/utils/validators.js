import Vue from "vue";
export const uniqueUser = async value =>
  value ? value.match(/^[\w.-]+$/i) : true;
export const validPassword = async value =>
  value
    ? value.match(/^(?=.*[\d])(?=.*[A-Z])(?=.*[!@#$%^&*])[\w!@#$%^&*]{0,32}$/)
    : true;
export const validSubdomain = async value =>
  value ? value.match(/^[a-zA-Z0-9-]+$/i) : true;
export const validEmail = async value =>
  value ? value.match(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/) : true;
export const validNumber = async value => (value ? !isNaN(value) : true);
export const sizeFile = size => file => {
  if (!file) {
    return true;
  }
  return file && file.size < size;
};
export const maxValue_br = param => value => {
  if (!value) {
    return true;
  }
  return value.replace(",", ".") <= param;
};
export const decimal_br = async value =>
  value ? value.match(/^\d{1,2}(,\d{1,2})?$/) : true;
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
export const horaFinalMenor = (horaInicioProperties, notSame) => (
  horaFim,
  object
) => {
  if (!object || object[horaInicioProperties] == null || horaFim == null) {
    return true;
  }
  const inicio = Vue.moment(object[horaInicioProperties], "HH:mm");
  const fim = Vue.moment(horaFim, "HH:mm");
  if (notSame && Vue.moment(fim).isSame(inicio)) {
    return false;
  }
  return !Vue.moment(fim).isBefore(inicio);
};
//Validador de CPF no padrao do VueValidate
export const cpf = c => {
  if (!c) {
    return true;
  }
  c = c.replace(".", "");
  c = c.replace("-", "");
  /* eslint-disable */
  if ((c = c.replace(/[^\d]/g,"")).length != 11) {
    return false;
  }
  if (c == "00000000000" || c == "11111111111" || c == "22222222222" || c == "33333333333" ||
    c == "44444444444" || c == "55555555555" || c == "66666666666" || 
    c == "77777777777" || c == "88888888888"|| c == "99999999999") {
    return false;
  }
  /* eslint-enable */
  let r;
  let s = 0;
  for (let i = 1; i <= 9; i++) {
    s = s + parseInt(c[i - 1]) * (11 - i);
  }
  r = (s * 10) % 11;
  if (r == 10 || r == 11) {
    r = 0;
  }
  if (r != parseInt(c[9])) {
    return false;
  }
  s = 0;
  for (let i = 1; i <= 10; i++) {
    s = s + parseInt(c[i - 1]) * (12 - i);
  }
  r = (s * 10) % 11;
  if (r == 10 || r == 11) {
    r = 0;
  }
  if (r != parseInt(c[10])) {
    return false;
  }
  return true;
};
