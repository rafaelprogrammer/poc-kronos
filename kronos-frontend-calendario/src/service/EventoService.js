import mixinsMessage from '@/mixins/message';
import EventoCalendario from '@/model/EventoCalendario';
import Categoria from '@/model/Categoria';
import baseService from './BaseService';

export default {
  listarTodos(evento) {
    const eventos = [];
    return baseService
      .get('/api/eventos', {
        params: {
          id: evento ? evento.id : null,
          idCalendario: evento ? evento.idCalendario : null,
        },
      })
      .then((response) => {
        if (response && response.data) {
          response.data.map((e) => {
            eventos.push(new EventoCalendario(e));
          });
        }
        return eventos;
      })
      .catch((error) => {
        mixinsMessage.methods.error('Erro ao tentar listar evento');
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get(`/api/eventos/${id}`)
      .then((response) => {
        if (response && response.data) {
          return new EventoCalendario(response.data);
        }
        return null;
      })
      .catch((error) => {
        mixinsMessage.methods.error('Erro ao tentar editar Evento');
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  salvar(evento) {
    if (evento.id) {
      return baseService
        .put(`/api/eventos/${evento.id}`, evento)
        .then((response) => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch((error) => {
          mixinsMessage.methods.error('Erro ao tentar atualizar Evento');
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
    return baseService
      .post('/api/eventos', evento)
      .then((response) => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch((error) => {
        mixinsMessage.methods.error('Erro ao tentar cadastrar Evento');
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  excluir(evento) {
    return baseService
      .delete(`/api/eventos/${evento.id}`)
      .then((response) => {
        mixinsMessage.methods.success(response.data);
        return response.data;
      })
      .catch((error) => {
        mixinsMessage.methods.error('Erro ao tentar excluir Evento');
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  categorias() {
    const tipos = [];
    return baseService
      .get('/api/eventos/categorias')
      .then((response) => {
        if (response && response.data) {
          response.data.map((p) => {
            tipos.push(new Categoria(p));
          });
        }
        return tipos;
      })
      .catch((error) => {
        mixinsMessage.methods.error(
          'Não foi possível listar os tipos de situacoes de calendários',
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
};
