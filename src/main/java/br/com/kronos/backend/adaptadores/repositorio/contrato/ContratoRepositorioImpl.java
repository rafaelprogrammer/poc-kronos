package br.com.kronos.backend.adaptadores.repositorio.contrato;

import static br.com.kronos.backend.aplicacao.util.DecimalUtil.converterDoubleDoisDecimais;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.contrato.Contrato;
import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.contrato.FiltroContrato;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContratoRepositorioImpl extends SqlQueryRepositorio implements ContratoRepositorio {
	
	public ContratoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Contrato contrato) {
        addFields("ano", "data", "data_venc_primeira_parcela", 
                  "data_venc_ultima_parcela", "dia_venc_parcela", "numero_parcelas",  "perc_juros_atraso", 
                  "perc_multa_atraso", "perc_bom_pagador", "valor_total_original", "observacao", 
                  "id_tipo_situacao_contrato", "id_tipo_forma_pagamento", "id_instituicao", "id_periodo_execucao",
                  "id_matricula", "id_reponsavel_financeiro", "id_empresa_origem", "valor_desconto_convenio",
                  "percentual_desconto_convenio", "valor_total_final", "id_tipo_resultado", "dependencia",
                  "id_tipo_composicao_valor", "valor_total_padrao", "valor_total_pendencia");
		
		addValues(contrato.getAno(), contrato.getData(), contrato.getDataVencimentoPrimeiraParcela(), 
                  contrato.getDataVencimentoUltimaParcela(), contrato.getDiaVencimentoParcela(), contrato.getNumeroParcelas(), contrato.getPercentualJurosAtraso(), 
                  contrato.getPercentualMultaAtraso(), contrato.getPercentualBomPagador(), contrato.getValorTotalOriginal(), contrato.getObservacao(),
                  contrato.getIdTipoSituacaoContrato(), contrato.getIdTipoFormaPagamento(), contrato.getIdInstituicao(), contrato.getIdPeriodoExecucao(),
                  contrato.getIdMatricula(), contrato.getIdResponsavelFinanceiro(), contrato.getIdEmpresaOrigem(), contrato.getValorDescontoConvenio(),
                  contrato.getPercentualDescontoConvenio(), contrato.getValorTotalFinal(), contrato.getIdTipoResultado(), contrato.isDependencia(),
                  contrato.getIdTipoComposicaoValor(), contrato.getValorTotalPadrao(), contrato.getValorTotalPendencia() ); 
		
		return (long) insertAuto("contrato"); 
    }

	@Override
	public Long alterar(Contrato contrato) {
		addFields("ano", "data", "data_venc_primeira_parcela", 
                "data_venc_ultima_parcela", "dia_venc_parcela", "numero_parcelas",  "perc_juros_atraso", 
                "perc_multa_atraso", "perc_bom_pagador", "valor_total_original", "observacao", 
                "id_tipo_situacao_contrato", "id_tipo_forma_pagamento", "id_instituicao", "id_periodo_execucao",
                "id_matricula", "id_reponsavel_financeiro", "id_empresa_origem", "valor_desconto_convenio",
                "percentual_desconto_convenio", "valor_total_final", "id_tipo_resultado", "dependencia",
                "id_tipo_composicao_valor", "valor_total_padrao", "valor_total_pendencia");
		
		addValues(contrato.getAno(), contrato.getData(), contrato.getDataVencimentoPrimeiraParcela(), 
                contrato.getDataVencimentoUltimaParcela(), contrato.getDiaVencimentoParcela(), contrato.getNumeroParcelas(), contrato.getPercentualJurosAtraso(), 
                contrato.getPercentualMultaAtraso(), contrato.getPercentualBomPagador(), contrato.getValorTotalOriginal(), contrato.getObservacao(),
                contrato.getIdTipoSituacaoContrato(), contrato.getIdTipoFormaPagamento(), contrato.getIdInstituicao(), contrato.getIdPeriodoExecucao(),
                contrato.getIdMatricula(), contrato.getIdResponsavelFinanceiro(), contrato.getIdEmpresaOrigem(), contrato.getValorDescontoConvenio(),
                contrato.getPercentualDescontoConvenio(), contrato.getValorTotalFinal(), contrato.getIdTipoResultado(), contrato.isDependencia(),
                contrato.getIdTipoComposicaoValor(), contrato.getValorTotalPadrao(), contrato.getValorTotalPendencia(), contrato.getId());
		
		return (long) update("contrato", contrato.getId());
	}
	
	@Override
	public ContratoDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select c.id, c.numero, c.ano, c.data, c.data_venc_primeira_parcela, "); 
            query.append("c.data_venc_ultima_parcela, c.dia_venc_parcela, c.numero_parcelas,  c.perc_juros_atraso, "); 
            query.append("c.perc_multa_atraso, c.perc_bom_pagador, c.valor_total_original, c.observacao, "); 
            query.append("c.id_tipo_situacao_contrato, c.id_tipo_forma_pagamento, c.id_instituicao, c.id_periodo_execucao, ");
            query.append("c.id_matricula, c.id_reponsavel_financeiro, c.id_empresa_origem, e.nome_fantasia as nome_empresa, c.valor_desconto_convenio, ");
            query.append("c.percentual_desconto_convenio, c.valor_total_final, id_tipo_resultado, dependencia, "); 
            query.append("c.id_tipo_composicao_valor, c.valor_total_padrao, valor_total_pendencia, p.nome as nome_periodo "); 
            query.append("from contrato c ");
            query.append("left join empresa e on (e.id = c.id_empresa_origem) ");
            query.append("join periodo_execucao pe on (c.id_periodo_execucao = pe.id) ");
            query.append("join periodo p on (p.id = pe.id_periodo) where 1=1 ");
            query = andEqual(query, "c.id", id);
            ContratoDTO contrato = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ContratoDTOMapper());
			return contrato;
		} catch (EmptyResultDataAccessException e) {
			log.info("contrato não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<ContratoDTO> listar(FiltroContrato filtroContrato) {
		StringBuilder query = createQuery("select c.id, c.numero, c.ano, c.data, c.data_venc_primeira_parcela, "); 
        query.append("c.data_venc_ultima_parcela, c.dia_venc_parcela, c.numero_parcelas,  c.perc_juros_atraso, "); 
        query.append("c.perc_multa_atraso, c.perc_bom_pagador, c.valor_total_original, c.observacao, "); 
        query.append("c.id_tipo_situacao_contrato, c.id_tipo_forma_pagamento, c.id_instituicao, c.id_periodo_execucao, ");
        query.append("c.id_matricula, c.id_reponsavel_financeiro, c.id_empresa_origem, e.nome_fantasia as nome_empresa, c.valor_desconto_convenio, ");
        query.append("c.percentual_desconto_convenio, c.valor_total_final, id_tipo_resultado, dependencia, "); 
        query.append("c.id_tipo_composicao_valor, c.valor_total_padrao, valor_total_pendencia, p.nome as nome_periodo ");     
        query.append("from contrato c ");
        query.append("left join empresa e on (e.id = c.id_empresa_origem) ");
        query.append("join periodo_execucao pe on (c.id_periodo_execucao = pe.id) ");
        query.append("join periodo p on (p.id = pe.id_periodo) where 1=1 ");
		query = andEqual(query, "c.id", filtroContrato.getId());		
		query = andEqual(query, "c.numero", filtroContrato.getNumero());
        query = andEqual(query, "c.ano", filtroContrato.getAno());
        query = andEqual(query, "c.id_instituicao", filtroContrato.getIdInstituicao()); 
        query = andEqual(query, "c.id_periodo_execucao", filtroContrato.getIdPeriodoExecucao());
        query = andEqual(query, "c.id_matricula", filtroContrato.getIdMatricula()); 
        query = andEqual(query, "c.id_reponsavel_financeiro", filtroContrato.getIdResponsavelFinanceiro());
        query = andEqual(query, "c.dependencia", filtroContrato.getDependencia());  
        query = orderBy(query, Order.DESC, "c.data");
		query = limit(query, filtroContrato.getQtdTotal(), filtroContrato.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ContratoDTOMapper());
	}
	
	@Override
	public int contar(FiltroContrato filtroContrato) {
		StringBuilder query = createQuery("select count(c.id) from contrato c ");
		query.append("left join empresa e on (e.id = c.id_empresa_origem) ");
        query.append("join periodo_execucao pe on (c.id_periodo_execucao = pe.id) ");
        query.append("join periodo p on (p.id = pe.id_periodo) where 1=1 ");
		query = andEqual(query, "c.id", filtroContrato.getId());		
		query = andEqual(query, "c.numero", filtroContrato.getNumero());
        query = andEqual(query, "c.ano", filtroContrato.getAno());
        query = andEqual(query, "c.id_instituicao", filtroContrato.getIdInstituicao()); 
        query = andEqual(query, "c.id_periodo_execucao", filtroContrato.getIdPeriodoExecucao());
        query = andEqual(query, "c.id_matricula", filtroContrato.getIdMatricula()); 
        query = andEqual(query, "c.id_reponsavel_financeiro", filtroContrato.getIdResponsavelFinanceiro());
        query = andEqual(query, "c.dependencia", filtroContrato.getDependencia());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("contrato", id);
		return true;
	}
	
	@Override
	public void alterarSituacaoContrato(Long id, EnumTipoSituacaoContrato tipo) {
		addFields("id_tipo_situacao_contrato");
		
		addValues(tipo.id(), id);
		
		update("contrato", id);
	}
	
	private class ContratoDTOMapper implements RowMapper<ContratoDTO> {

		@Override
		public ContratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ContratoDTO.builder()
					.id(rs.getLong("id"))
					.numero(rs.getInt("numero"))
                    .ano(rs.getInt("ano"))
                    .data(rs.getDate("data").toLocalDate())
                    .dataVencimentoPrimeiraParcela(rs.getDate("data_venc_primeira_parcela") !=null ? rs.getDate("data_venc_primeira_parcela").toLocalDate() : null)
                    .dataVencimentoUltimaParcela(rs.getDate("data_venc_ultima_parcela") != null ? rs.getDate("data_venc_ultima_parcela").toLocalDate() : null)
                    .diaVencimentoParcela(rs.getObject("dia_venc_parcela") !=null ? rs.getInt("dia_venc_parcela") : null)
                    .numeroParcelas(rs.getObject("numero_parcelas") != null ? rs.getInt("numero_parcelas") : null)
                    .percentualJurosAtraso(rs.getDouble("perc_juros_atraso"))
                    .percentualMultaAtraso(rs.getDouble("perc_multa_atraso"))
                    .percentualBomPagador(rs.getDouble("perc_bom_pagador"))
                    .valorTotalOriginal(rs.getObject("valor_total_original") != null ? converterDoubleDoisDecimais(rs.getDouble("valor_total_original")) : null)
                    .observacao(rs.getString("observacao"))
                    .idTipoSituacaoContrato(rs.getInt("id_tipo_situacao_contrato"))
                    .idTipoFormaPagamento(rs.getObject("id_tipo_forma_pagamento") !=null ? rs.getInt("id_tipo_forma_pagamento") : null)
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
                    .nomePeriodo(rs.getString("nome_periodo"))
                    .idMatricula(rs.getLong("id_matricula"))
                    .idResponsavelFinanceiro(rs.getObject("id_reponsavel_financeiro") != null ? rs.getLong("id_reponsavel_financeiro") : null)
                    .idEmpresaOrigem(rs.getObject("id_empresa_origem") != null ? rs.getLong("id_empresa_origem") : null)
                    .nomeEmpresaOrigem(rs.getString("nome_empresa") != null ? rs.getString("nome_empresa") : null)
                    .valorDescontoConvenio(rs.getObject("valor_desconto_convenio") != null ? converterDoubleDoisDecimais(rs.getDouble("valor_desconto_convenio")) : null)
                    .percentualDescontoConvenio(rs.getObject("percentual_desconto_convenio") != null ? rs.getDouble("percentual_desconto_convenio") : null)
                    .valorTotalFinal(rs.getObject("valor_total_final") != null ? converterDoubleDoisDecimais(rs.getDouble("valor_total_final")) : null)  
                    .idTipoResultado(rs.getObject("id_tipo_resultado") != null ? rs.getInt("id_tipo_resultado") : null)  
                    .dependencia(rs.getBoolean("dependencia")) 
                	.idTipoComposicaoValor(rs.getInt("id_tipo_composicao_valor"))  
                    .valorTotalPadrao(converterDoubleDoisDecimais(rs.getDouble("valor_total_padrao")))
                    .valorTotalPendencia(converterDoubleDoisDecimais(rs.getDouble("valor_total_pendencia")))
					.build();
		}
	}
}


