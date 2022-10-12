package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroInstituicao;
import br.com.kronos.backend.aplicacao.instituicao.Instituicao;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstituicaoRepositorioImpl extends SqlQueryRepositorio implements InstituicaoRepositorio {

	public InstituicaoRepositorioImpl(DataSource dataSource)  {
		super(dataSource);
	}
	
	@Override
	public Long criar(Instituicao instituicao) {
        addFields("nome", "ativo", "id_arq_anexo", "id_empresa", 
        		  "mantenedora", "id_composicao_valor", "perc_juros_atraso",
        		  "perc_multa_atraso", "perc_bom_pagador");
		
		addValues(instituicao.getNome(), instituicao.isAtivo(),  instituicao.getIdArquivoAnexo(), instituicao.getIdEmpresa(),
				 instituicao.isMantenedora(), instituicao.getIdComposicaoValor(), instituicao.getPercentualJurosAtraso(),
				 instituicao.getPercentualMultaAtraso(), instituicao.getPercentualBomPagador());
		
		return (long) insertAuto("instituicao"); 
    }
	
	@Override
	public Long alterar(Instituicao instituicao) {
		addFields("nome", "ativo", "id_arq_anexo", "id_empresa", 
      		  "mantenedora", "id_composicao_valor");
		
		addValues(instituicao.getNome(), instituicao.isAtivo(),  instituicao.getIdArquivoAnexo(), instituicao.getIdEmpresa(),
				 instituicao.isMantenedora(), instituicao.getIdComposicaoValor(), instituicao.getPercentualJurosAtraso(),
				 instituicao.getPercentualMultaAtraso(), instituicao.getPercentualBomPagador(), instituicao.getId());
		
		return (long) update("instituicao", instituicao.getId());
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("instituicao", id);
		return true;
	}
	

	@Override
	public Instituicao buscarPorId(long id) {
		try {
			StringBuilder query = createQuery("select i.id, i.nome, i.ativo, i.id_arq_anexo, ");
		    query.append("i.id_empresa, i.mantenedora, i.id_composicao_valor, i.perc_juros_atraso, ");
		    query.append("i.perc_multa_atraso, i.perc_bom_pagador ");
		    query.append("from instituicao i where 1=1 ");
			query = andEqual(query, "i.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new InstituicaoMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("Instituição não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<Instituicao> listar(FiltroInstituicao filtroInstituicao) {
		StringBuilder query = createQuery("select i.id, i.nome, i.ativo, i.id_arq_anexo, ");
	    query.append("i.id_empresa, i.mantenedora, i.id_composicao_valor, i.perc_juros_atraso, ");
	    query.append("i.perc_multa_atraso, i.perc_bom_pagador ");
	    query.append("from instituicao i where 1=1 ");
		query = andEqual(query, "i.id", filtroInstituicao.getId());
		query = andLike(query, "i.nome", filtroInstituicao.getNome());
        query = andEqual(query, "i.ativo", filtroInstituicao.getAtivo());
        query = andEqual(query, "i.id_empresa", filtroInstituicao.getIdEmpresa()); 
        query = andEqual(query, "i.mantenedora", filtroInstituicao.getMantenedora());
        query = orderBy(query, Order.ASC, "i.nome");
		query = limit(query, filtroInstituicao.getQtdTotal(), filtroInstituicao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new InstituicaoMapper());
	}
	
	@Override
	public int contar(FiltroInstituicao filtroInstituicao) {
		StringBuilder query = createQuery("select count(i.id) from instituicao i where 1=1 ");
		query = andEqual(query, "i.id", filtroInstituicao.getId());
		query = andLike(query, "i.nome", filtroInstituicao.getNome());
        query = andEqual(query, "i.ativo", filtroInstituicao.getAtivo());
        query = andEqual(query, "i.id_empresa", filtroInstituicao.getIdEmpresa()); 
        query = andEqual(query, "i.mantenedora", filtroInstituicao.getMantenedora());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	

	private class InstituicaoMapper implements RowMapper<Instituicao> {

		@Override
		public Instituicao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Instituicao.builder()
					.id(rs.getLong("id"))
					.nome(rs.getString("nome"))
					.ativo(rs.getBoolean("ativo"))
					.idArquivoAnexo(rs.getLong("id_arq_anexo"))
					.idEmpresa(rs.getLong("id_empresa"))
					.mantenedora(rs.getBoolean("mantenedora"))
					.idComposicaoValor(rs.getInt("id_composicao_valor"))
					.percentualJurosAtraso(rs.getDouble("perc_juros_atraso"))
					.percentualMultaAtraso(rs.getDouble("perc_multa_atraso"))
					.percentualBomPagador(rs.getDouble("perc_bom_pagador"))
					.build();
		}
	}
}
