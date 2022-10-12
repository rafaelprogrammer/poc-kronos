package br.com.kronos.backend.adaptadores.repositorio.contrato;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.contrato.FiltroParcela;
import br.com.kronos.backend.aplicacao.contrato.Parcela;
import br.com.kronos.backend.aplicacao.contrato.ParcelaRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParcelaRepositorioImpl extends SqlQueryRepositorio implements ParcelaRepositorio {
	
	public ParcelaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Parcela parcela) {
        addFields("numero", "data_vencimento", "valor_original", "data_pagamento", 
                  "valor_juros", "valor_multa", "valor_desconto",  "valor_pagamento", 
                  "observacao", "id_tipo_forma_pagamento", "id_contrato");
		
		addValues(parcela.getNumero(), parcela.getDataVencimento(), parcela.getValorOriginal(), parcela.getDataPagamento(),
				  parcela.getValorJuros(), parcela.getValorMulta(), parcela.getValorDesconto(), parcela.getValorPagamento(),
                  parcela.getObservacao(), parcela.getIdTipoFormaPagamento(), parcela.getIdContrato()); 
		
		return (long) insertAuto("parcela"); 
    }
	
	@Override
	public Long alterar(Parcela parcela) {
		addFields("numero", "data_vencimento", "valor_original", "data_pagamento", 
                 "valor_juros", "valor_multa", "valor_desconto",  "valor_pagamento", 
                 "observacao", "id_tipo_forma_pagamento", "id_contrato");
		
		addValues(parcela.getNumero(), parcela.getDataVencimento(), parcela.getValorOriginal(), parcela.getDataPagamento(),
				  parcela.getValorJuros(), parcela.getValorMulta(), parcela.getValorDesconto(), parcela.getValorPagamento(),
                  parcela.getObservacao(), parcela.getIdTipoFormaPagamento(), parcela.getIdContrato(), parcela.getId());
		
		return (long) update("parcela", parcela.getId());
	}
	
	@Override
	public Parcela buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select p.id, p.numero, p.data_vencimento, p.valor_original, p.data_pagamento, "); 
            query.append("p.valor_juros, p.valor_multa, p.valor_desconto,  p.valor_pagamento, "); 
            query.append("p.observacao, p.id_tipo_forma_pagamento, p.id_contrato ");     
            query.append("from parcela p where 1=1 ");
            query = andEqual(query, "p.id", id);
			Parcela parcela = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ParcelaMapper());
			return parcela;
		} catch (EmptyResultDataAccessException e) {
			log.info("parcela não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Parcela> listar(FiltroParcela filtroParcela) {
		StringBuilder query = createQuery("select p.id, p.numero, p.data_vencimento, p.valor_original, p.data_pagamento, "); 
        query.append("p.valor_juros, p.valor_multa, p.valor_desconto,  p.valor_pagamento, "); 
        query.append("p.observacao, p.id_tipo_forma_pagamento, p.id_contrato ");     
        query.append("from parcela p where 1=1 ");
		query = andEqual(query, "p.id", filtroParcela.getId());		
		query = andEqual(query, "p.numero", filtroParcela.getNumero());
        query = andEqual(query, "p.id_contrato", filtroParcela.getIdContrato());
        query = orderBy(query, Order.ASC, "p.numero");
//		query = limit(query, filtroParcela.getQtdTotal(), filtroParcela.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ParcelaMapper());
	}
	
	@Override
	public int contar(FiltroParcela filtroParcela) {
		StringBuilder query = createQuery("select count(p.id) from parcela p where 1=1 ");
		query = andEqual(query, "p.id", filtroParcela.getId());		
		query = andEqual(query, "p.numero", filtroParcela.getNumero());
        query = andEqual(query, "p.id_contrato", filtroParcela.getIdContrato());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("parcela", id);
		return true;
	}
	
	private class ParcelaMapper implements RowMapper<Parcela> {

		@Override
		public Parcela mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Parcela.builder()
					.id(rs.getLong("id"))
					.numero(rs.getInt("numero"))
                    .dataVencimento(rs.getDate("data_vencimento").toLocalDate())
                    .valorOriginal(rs.getDouble("valor_original"))
                    .dataPagamento(rs.getDate("data_pagamento") != null ? rs.getDate("data_pagamento").toLocalDate() : null)
                    .valorJuros(rs.getDouble("valor_juros"))
                    .valorMulta(rs.getDouble("valor_multa"))
                    .valorDesconto(rs.getDouble("valor_desconto"))
                    .valorPagamento(rs.getDouble("valor_pagamento"))
                    .observacao(rs.getString("observacao"))
                    .idTipoFormaPagamento(rs.getInt("id_tipo_forma_pagamento"))
                    .idContrato(rs.getLong("id_contrato"))  
					.build();
		}
	}
}
