import ConvenioContrato from "./ConvenioContrato";
import Parcela from "../parcela/Parcela";

class Contrato {
  static ANALISE_FINANCEIRA() {
    return 3;
  }
  static AGUARDANDO_APROVACAO() {
    return 4;
  }
  static CONFIRMAR_ASSINATURA() {
    return 5;
  }
  constructor(dados) {
    this.id = dados ? dados.id : null;
    this.numero = dados ? dados.numero : null;
    this.ano = dados ? dados.ano : null;
    this.data = dados ? dados.data : null;
    this.dataVencimentoPrimeiraParcela = dados
      ? dados.dataVencimentoPrimeiraParcela
      : null;
    this.dataVencimentoUltimaParcela = dados
      ? dados.dataVencimentoUltimaParcela
      : null;
    this.diaVencimentoParcela = dados ? dados.diaVencimentoParcela : null;
    this.numeroParcelas = dados ? dados.numeroParcelas : null;
    this.percentualJurosAtraso = dados ? dados.percentualJurosAtraso : null;
    this.percentualMultaAtraso = dados ? dados.percentualMultaAtraso : null;
    this.percentualBomPagador = dados ? dados.percentualBomPagador : null;
    this.valorTotalOriginal = dados ? dados.valorTotalOriginal : null;
    this.observacao = dados ? dados.observacao : null;
    this.idTipoSituacaoContrato = dados ? dados.idTipoSituacaoContrato : null;
    this.nomeTipoSituacaoContrato = dados
      ? dados.nomeTipoSituacaoContrato
      : null;
    this.idTipoFormaPagamento = dados ? dados.idTipoFormaPagamento : null;
    this.nomeTipoFormaPagamento = dados ? dados.nomeTipoFormaPagamento : null;
    this.idInstituicao = dados ? dados.idInstituicao : null;
    this.idPeriodoExecucao = dados ? dados.idPeriodoExecucao : null;
    this.nomePeriodo = dados ? dados.nomePeriodo : null;
    this.idMatricula = dados ? dados.idMatricula : null;
    this.idResponsavelFinanceiro = dados ? dados.idResponsavelFinanceiro : null;
    this.idEmpresaOrigem = dados ? dados.idEmpresaOrigem : null;
    this.nomeEmpresaOrigem = dados ? dados.nomeEmpresaOrigem : null;
    this.valorDescontoConvenio = dados ? dados.valorDescontoConvenio : null;
    this.percentualDescontoConvenio = dados
      ? dados.percentualDescontoConvenio
      : null;
    this.valorTotalFinal = dados ? dados.valorTotalFinal : null;
    this.idTipoResultado = dados ? dados.idTipoResultado : null;
    this.dependencia = dados ? dados.dependencia : null;
    this.idTipoComposicaoValor = dados ? dados.idTipoComposicaoValor : null;
    this.nomeTipoComposicaoValor = dados ? dados.nomeTipoComposicaoValor : null;
    this.valorTotalPadrao = dados ? dados.valorTotalPadrao : null;
    this.valorTotalPendencia = dados ? dados.valorTotalPendencia : null;
    this.creditosContratos = dados ? dados.creditosContratos : [];
    this.conveniosContratos = [];
    if (dados && dados.conveniosContratos) {
      dados.conveniosContratos.map(c => {
        this.conveniosContratos.push(new ConvenioContrato(c));
      });
    }
    this.parcelas = [];
    if (dados && dados.parcelas) {
      dados.parcelas.map(p => {
        this.parcelas.push(new Parcela(p));
      });
    }
  }

  static validations(required, maxLength, numeric, decimal, dataFimMenor) {
    return {
      ano: { required, numeric },
      data: { required },
      observacao: { maxLength: maxLength(500) },
      dataVencimentoUltimaParcela: {
        dataFimMenor: dataFimMenor("dataVencimentoPrimeiraParcela")
      },
      diaVencimentoParcela: { numeric },
      numeroParcelas: { required, numeric },
      percentualJurosAtraso: { decimal },
      percentualMultaAtraso: { decimal },
      percentualBomPagador: { decimal },
      valorTotalOriginal: { decimal },
      idTipoSituacaoContrato: { required },
      idPeriodoExecucao: { required },
      idMatricula: { required },
      valorDescontoConvenio: { decimal },
      percentualDescontoConvenio: { decimal },
      valorTotalFinal: { decimal },
      valorTotalPadrao: { decimal },
      valorTotalPendencia: { decimal },
      idResponsavelFinanceiro: { required },
      idTipoFormaPagamento: { required },
      dataVencimentoPrimeiraParcela: { required }
    };
  }

  static validationMessages() {
    return {
      ano: {
        required: "Ano é obrigatório",
        numeric: "Somente números"
      },
      observacao: {
        maxLength: "Observação só aceita no máximo 500 caracteres"
      },
      dataVencimentoUltimaParcela: {
        dataFimMenor:
          "Data Vencimento Última Parcela não pode ser menor que a Data Vencimento Primeira Parcela"
      },
      data: {
        required: "Data é obrigatória"
      },
      diaVencimentoParcela: {
        numeric: "Somente números"
      },
      numeroParcelas: {
        required: "Número de parcelas é obrigatória",
        numeric: "Somente números"
      },
      percentualJurosAtraso: {
        decimal: "Somente número decimal"
      },
      percentualMultaAtraso: {
        decimal: "Somente número decimal"
      },
      percentualBomPagador: {
        decimal: "Somente número decimal"
      },
      valorTotalOriginal: {
        decimal: "Somente número decimal"
      },
      idTipoSituacaoContrato: {
        required: "Tipo de Situação do Contrato é obrigatório"
      },
      idPeriodo: {
        required: "Período é obrigatório"
      },
      idMatricula: {
        required: "Matrícula é obrigatória"
      },
      valorDescontoConvenio: {
        decimal: "Somente número decimal"
      },
      percentualDescontoConvenio: {
        decimal: "Somente número decimal"
      },
      valorTotalFinal: {
        decimal: "Somente número decimal"
      },
      valorTotalPadrao: {
        decimal: "Somente número decimal"
      },
      valorTotalPendencia: {
        decimal: "Somente número decimal"
      },
      idResponsavelFinanceiro: {
        required: "Responsável financeiro é obrigatório"
      },
      idTipoFormaPagamento: {
        required: "Forma de pagamento é obrigatório"
      },
      dataVencimentoPrimeiraParcela: {
        required: "Data Primeira Parcela é obrigatória"
      }
    };
  }
}
export default Contrato;
