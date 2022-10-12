package br.com.kronos.backend.adaptadores.repositorio.conselho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.conselho.Conselho;
import br.com.kronos.backend.aplicacao.conselho.ConselhoPessoa;
import br.com.kronos.backend.aplicacao.conselho.ConselhoRepositorio;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselho;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselhoPessoa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConselhoRepositorioImpl extends SqlQueryRepositorio implements ConselhoRepositorio {
	
	public ConselhoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Conselho conselho) {
        addFields("id_tipo_finalidade", "id_instituicao", "data_inicio_vigencia", "data_final_vigencia" );
		
		addValues(conselho.getIdTipoFinalidadeConselho(), conselho.getIdInstituicao(), conselho.getDataInicioVigencia(),conselho.getDataFinalVigencia() );
		
		return (long) insertAuto("conselho"); 
    }
	
	@Override
	public Long alterar(Conselho conselho) {
addFields("id_tipo_finalidade", "id_instituicao", "data_inicio_vigencia", "data_final_vigencia" );
		
		addValues(conselho.getIdTipoFinalidadeConselho(), conselho.getIdInstituicao(), conselho.getDataInicioVigencia(),conselho.getDataFinalVigencia(),
				  conselho.getId());
		
		return (long) update("conselho", conselho.getId());
	}
	
	@Override
	public Conselho buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.id_tipo_finalidade, c.id_instituicao, c.data_inicio_vigencia, c.data_final_vigencia, ");
            query.append("from conselho c where 1=1 ");
            query = andEqual(query, "c.id", id);
			Conselho conselho = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ConselhoMapper());
			return conselho;
		} catch (EmptyResultDataAccessException e) {
			log.info("conselho não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Conselho> listar(FiltroConselho filtroConselho) {
		 StringBuilder query = createQuery("select c.id_tipo_finalidade, c.id_instituicao, c.data_inicio_vigencia, c.data_final_vigencia, ");
         query.append("from conselho c where 1=1 ");
         query = andEqual(query, "c.id", filtroConselho.getId());
 	     query = andEqual(query, "c.id_instituicao", filtroConselho.getIdInstituicao());
         query = andEqual(query, "c.id_tipo_finalidade", filtroConselho.getId());
         query = andEqual(query, "c.data_inicio_vigencia", filtroConselho.getDataInicioVigencia());  
         query = orderBy(query, Order.ASC, "c.data_inicio_vigencia");
		 query = limit(query, filtroConselho.getQtdTotal(), filtroConselho.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ConselhoMapper());
	}
	
	@Override
	public int contar(FiltroConselho filtroConselho) {
		StringBuilder query = createQuery("select count(c.id) from conselho c where 1=1 ");
		 query = andEqual(query, "c.id", filtroConselho.getId());
 	     query = andEqual(query, "c.id_instituicao", filtroConselho.getIdInstituicao());
         query = andEqual(query, "c.id_tipo_finalidade", filtroConselho.getId());
         query = andEqual(query, "c.data_inicio_vigencia", filtroConselho.getDataInicioVigencia());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("conselho", id);
		return true;
	}
	
	@Override
	public void vincularPessoaConselho(ConselhoPessoa conselhoPessoa) {
		addFields("id_pessoa", "id_conselho", "id_tipo_finalidade" );
		
		addValues(conselhoPessoa.getIdPessoa(), conselhoPessoa.getIdConselho(), conselhoPessoa.getIdTipoFuncao()); 
		
		insert("conselho_pessoa"); 
	}

	@Override
	public void desvincularPessoaConselho(ConselhoPessoa conselhoPessoa) {
		addFields("id_pessoa", "id_conselho");
		
		addValues(conselhoPessoa.getIdPessoa(), conselhoPessoa.getIdConselho());
		
		delete("conselho_pessoa");
	}
	
	private class ConselhoMapper implements RowMapper<Conselho> {

		@Override
		public Conselho mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Conselho.builder()
					.id(rs.getLong("id"))
                    .idTipoFinalidadeConselho(rs.getInt("id_tipo_finalidade"))
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
					.build();
		}

	}

	@Override
	public boolean verificarVinculoConselhoPessoa(FiltroConselhoPessoa filtroConselhoPessoa) {
		StringBuilder query = createQuery("select count(c.id_pessoa) from conselho_pessoa c where 1=1 ");
		query = andEqual(query, "c.id_pessoa", filtroConselhoPessoa.getIdPessoa());
		query = andEqual(query, "c.id_conselho", filtroConselhoPessoa.getIdConselho());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class) > 0;
	}

}
