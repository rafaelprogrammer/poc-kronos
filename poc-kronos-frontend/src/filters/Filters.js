export const booleanFilter = function(value, trueText, falseText) {
  return value ? trueText || "Sim" : falseText || "Não";
};
export const limitCaracteres = function(value, count) {
  return value
    ? value.slice(0, count) + (value.length > count ? "..." : "")
    : null;
};
