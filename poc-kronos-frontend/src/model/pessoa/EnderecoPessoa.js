class EnderecoPessoa {
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.cep = dados ? dados.cep : null;
    this.logradouro = dados ? dados.logradouro : null;
    this.complemento = dados ? dados.complemento : null;
    this.bairro = dados ? dados.bairro : null;
    this.localidade = dados ? dados.localidade : null;
    this.uf = dados ? dados.uf : null;
    this.unidade = dados ? dados.unidade : null;
    this.ibge = dados ? dados.ibge : null;
    this.gia = dados ? dados.gia : null;
    this.idPessoa = dados ? dados.idPessoa : null;
    this.idTipoEndereco = dados ? dados.idTipoEndereco : null;
    this.nomeTipoEndereco = dados ? dados.nomeTipoEndereco : null;
    this.numero = dados ? dados.numero : null;
  }

  adicionarDadosCep(dados) {
    this.cep = dados.cep.replace("-", "");
    this.logradouro = dados.logradouro;
    this.complemento = dados.complemento;
    this.bairro = dados.bairro;
    this.localidade = dados.localidade;
    this.uf = dados.uf;
    this.unidade = dados.unidade;
    this.ibge = dados.ibge;
    this.gia = dados.gia;
  }

  static validations(required, maxLength, minLength, numeric) {
    return {
      cep: {
        required,
        maxLength: maxLength(8),
        minLength: minLength(8),
        numeric
      },
      logradouro: { required },
      bairro: { required },
      localidade: { required },
      uf: { required },
      ibge: { required },
      idPessoa: { required },
      idTipoEndereco: { required }
    };
  }

  static validationMessages() {
    return {
      cep: {
        required: "CEP é obrigatório",
        maxLength: "CEP deve ter 8 digítos",
        minLength: "CEP deve ter 8 digítos",
        numeric: "CEP só aceita digítos"
      },
      logradouro: {
        required: "Logradouro é obrigatório"
      },
      bairro: {
        required: "Bairro é obrigatório"
      },
      localidade: {
        required: "Localidade é obrigatória"
      },
      uf: {
        required: "UF é obrigatória"
      },
      ibge: {
        required: "IBGE é obrigatório"
      },
      idPessoa: {
        required: "Pessoa é obrigatória"
      },
      idTipoEndereco: {
        required: "Tipo Endereço é obrigatório"
      }
    };
  }
}
export default EnderecoPessoa;
