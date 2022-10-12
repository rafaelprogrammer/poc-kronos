package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;  
import org.springframework.jdbc.core.RowMapper; 

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroPortaria;
import br.com.kronos.backend.aplicacao.curso.Portaria;
import br.com.kronos.backend.aplicacao.curso.PortariaRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortariaRepositorioImpl extends SqlQueryRepositorio implements PortariaRepositorio {
	
	public PortariaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Portaria portaria) {
        addFields("data_publicacao", "data_inicio_vigencia", "data_final_vigencia", "descricao", 
                  "documento_publicacao", "id_tipo_portaria", "id_curso"); 
		
		addValues(portaria.getDataPublicacao(), portaria.getDataInicioVigencia(), portaria.getDataFinalVigencia(),portaria.getDescricao(), 
                  portaria.getDocumentoPublicacao(), portaria.getIdTipoPortaria(), portaria.getIdCurso());
		
		return (long) insertAuto("portaria");
    }

	@Override
	public Long alterar(Portaria portaria) {
		addFields("data_publicacao", "data_inicio_vigencia", "data_final_vigencia", "descricao", 
                  "documento_publicacao", "id_tipo_portaria", "id_curso"); 
		
		addValues(portaria.getDataPublicacao(), portaria.getDataInicioVigencia(), portaria.getDataFinalVigencia(),portaria.getDescricao(), 
                  portaria.getDocumentoPublicacao(), portaria.getIdTipoPortaria(), portaria.getIdCurso(), portaria.getId());
		
		return (long) update("portaria", portaria.getId());
	}
	
	@Override
	public Portaria buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select p.id, p.data_publicacao, p.data_inicio_vigencia, p.data_final_vigencia, p.descricao, ");
            query.append("p.documento_publicacao, p.id_tipo_portaria, p.id_curso from portaria p where 1=1 ");
            query = andEqual(query, "p.id", id);
			Portaria portaria = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PortariaMapper());
			return portaria;
		} catch (EmptyResultDataAccessException e) {
			log.info("portaria não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Portaria> listar(FiltroPortaria filtroPortaria) {
        StringBuilder query = createQuery("select p.id, p.data_publicacao, p.data_inicio_vigencia, p.data_final_vigencia, p.descricao, ");
        query.append("p.documento_publicacao, p.id_tipo_portaria, p.id_curso from portaria p where 1=1 ");
        query = andEqual(query, "p.id", filtroPortaria.getId());
        query = andEqual(query, "p.descricao", filtroPortaria.getDescricao()); 
        query = andEqual(query, "p.id_tipo_portaria", filtroPortaria.getIdTipoPortaria());
        query = andEqual(query, "p.id_curso", filtroPortaria.getIdCurso());
        query = orderBy(query, Order.DESC, "p.data_publicacao");
		query = limit(query, filtroPortaria.getQtdTotal(), filtroPortaria.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PortariaMapper());
	}
	
	@Override
	public int contar(FiltroPortaria filtroPortaria) {
		StringBuilder query = createQuery("select count(p.id) from portaria p where 1=1 ");
		query = andEqual(query, "p.id", filtroPortaria.getId());
        query = andEqual(query, "p.descricao", filtroPortaria.getDescricao()); 
        query = andEqual(query, "p.id_tipo_portaria", filtroPortaria.getIdTipoPortaria());
        query = andEqual(query, "p.id_curso", filtroPortaria.getIdCurso());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("portaria", id);
		return true;
	}
	
	private class PortariaMapper implements RowMapper<Portaria> {

		@Override
		public Portaria mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Portaria.builder() 
                    .id(rs.getLong("id"))
                    .dataPublicacao(rs.getDate("data_publicacao").toLocalDate())
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .descricao(rs.getString("descricao"))
                    .documentoPublicacao(rs.getString("documento_publicacao"))
                    .idTipoPortaria(rs.getInt("id_tipo_portaria"))
                    .idCurso(rs.getLong("id_curso"))
                    .build();
		}
	}

}