package br.com.kronos.backend.adaptadores.repositorio.contrato;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.contrato.FiltroContratoConvenio;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenio;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenioRepositorio;

public class ContratoConvenioRepositorioImpl extends SqlQueryRepositorio implements ContratoConvenioRepositorio {
	
	public ContratoConvenioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(ContratoConvenio contratoConvenio) {
		addFields("id_contrato", "id_convenio", "percentual_desconto");
		
		addValues(contratoConvenio.getIdContrato(), contratoConvenio.getIdConvenio(), contratoConvenio.getPercentualDesconto());
		
		return insert("contrato_convenio");
	}

	@Override
	public List<ContratoConvenio> listar(FiltroContratoConvenio filtroContratoConvenio) {
		StringBuilder query = createQuery("select c.id_contrato, c.id_convenio, c.percentual_desconto ");
		query.append(" from contrato_convenio c where 1=1 ");
		query = andEqual(query, "c.id_contrato", filtroContratoConvenio.getIdContrato());
		query = limit(query, filtroContratoConvenio.getQtdTotal(), filtroContratoConvenio.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ContratoConvenioMapper());
	}
	
	@Override
	public int contar(FiltroContratoConvenio filtroContratoConvenio) {
		StringBuilder query = createQuery("select count(c.id_convenio) from contrato_convenio c where 1=1 ");
		query = andEqual(query, "c.id_contrato", filtroContratoConvenio.getIdContrato());
		query = andEqual(query, "c.id_convenio", filtroContratoConvenio.getIdConvenio());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public Double somarPercentualDesconto(Long idContrato) {
		StringBuilder query = createQuery("select sum(percentual_desconto) from contrato_convenio cc where 1=1 ");
		query = andEqual(query, "cc.id_contrato", idContrato);
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Double.class);
	}
	
	@Override
	public boolean excluir(long idContrato, long idContratoConvenio) {
		addFields("id_contrato", "id_convenio");
		
		addValues(idContrato, idContratoConvenio);
		
		delete("contrato_convenio");
		return true;
	}
	
	private class ContratoConvenioMapper implements RowMapper<ContratoConvenio> {

		@Override
		public ContratoConvenio mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ContratoConvenio.builder()
					.idContrato(rs.getLong("id_contrato"))
					.idConvenio(rs.getLong("id_convenio"))
					.percentualDesconto(rs.getDouble("percentual_desconto"))
					.build();
		}

	}

	@Override
	public int contarPorIdContratoEIdConvenio(FiltroContratoConvenio filtroContratoConvenio) {
		StringBuilder query = createQuery("select count(c.id_convenio) from contrato_convenio c where 1=1 ");
		query = andEqual(query, "c.id_contrato", filtroContratoConvenio.getIdContrato());
		query = andEqual(query, "c.id_convenio", filtroContratoConvenio.getIdConvenio());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}


}
