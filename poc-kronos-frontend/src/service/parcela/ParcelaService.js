import baseService from "../BaseService";
import mixinsMessage from "@/mixins/message";
import Parcela from "@/model/parcela/Parcela";
export default {
  listarTodos(parcela) {
    let parcelas = [];
    return baseService
      .get("/api/parcelas", {
        params: {
          id: parcela.id,
          idContrato: parcela.idContrato
        }
      })
      .then(response => {
        if (response && response.data) {
          response.data.map(p => {
            parcelas.push(new Parcela(p));
          });
          return parcelas;
        }
        return parcelas;
      })
      .catch(function(error) {
        mixinsMessage.methods.error(
          "Erro ao tentar listar as parcelas do contrato"
        );
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  editar(id) {
    return baseService
      .get("/api/parcelas/" + id)
      .then(response => {
        if (response && response.data) {
          return new Parcela(response.data);
        }
        return null;
      })
      .catch(function(error) {
        mixinsMessage.methods.error("Erro ao tentar editar Parcela");
        // eslint-disable-next-line no-console
        console.error(error);
      });
  },
  gerarParcelas(parcela) {
    if (parcela.idContrato && parcela.numeroParcelas) {
      return baseService
        .post("/api/parcelas", parcela)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error("Erro ao tentar gerar Parcelas", error);
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  },
  alterar(parcela) {
    if (parcela.id) {
      return baseService
        .put("/api/parcelas/" + parcela.id, parcela)
        .then(response => {
          mixinsMessage.methods.success(response.data);
          return response.data;
        })
        .catch(function(error) {
          mixinsMessage.methods.error(
            "Erro ao tentar atualizar Parcela",
            error
          );
          // eslint-disable-next-line no-console
          console.error(error);
        });
    }
  }
};
