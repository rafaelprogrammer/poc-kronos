import Calendario from '@/model/Calendario';
import mixinsMessage from '@/mixins/message';
import baseService from './BaseService';

export default {
  editar(id) {
    return baseService
      .get(`/api/calendarios/${id}`)
      .then((response) => {
        if (response && response.data) {
          return new Calendario(response.data);
        }
        return null;
      })
      .catch((error) => {
        mixinsMessage.methods.error('Erro ao tentar editar Calendario');
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
};
