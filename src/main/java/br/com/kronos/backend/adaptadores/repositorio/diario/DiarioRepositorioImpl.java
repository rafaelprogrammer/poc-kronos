package br.com.kronos.backend.adaptadores.repositorio.diario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.diario.FiltroDiario;
import br.com.kronos.backend.aplicacao.diario.api.DiarioDTO;
import br.com.kronos.backend.aplicacao.diario.Diario;
import br.com.kronos.backend.aplicacao.diario.DiarioRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiarioRepositorioImpl extends SqlQueryRepositorio implements DiarioRepositorio {
	
	public DiarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Diario diario) {
        addFields("procedimento", "recurso", "observacao", "id_atividade", 
                  "id_tipo_programa" );
		
		addValues(diario.getProcedimento(), diario.getRecurso(), diario.getObservacao(),diario.getIdAtividade(), 
                  diario.getIdTipoPrograma()); 
		
		return (long) insertAuto("diario"); 
    }
	
	@Override
	public Long alterar(Diario diario) {
		addFields("procedimento", "recurso", "observacao", "id_atividade", 
                "id_tipo_programa" );
		
		addValues(diario.getProcedimento(), diario.getRecurso(), diario.getObservacao(),diario.getIdAtividade(), 
                diario.getIdTipoPrograma(), diario.getId());
		
		return (long) update("diario", diario.getId());
	}
	
	@Override
	public DiarioDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select d.id, d.procedimento, d.recurso, d.observacao, d.id_atividade,  ");
            query.append("d.id_tipo_programa, t.nome as nome_programa ");
            query.append("from diario d ");
            query.append("left join tipo_programa t on (d.id_tipo_programa = t.id) where 1=1 ");
            query = andEqual(query, "d.id", id);
            DiarioDTO diario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DiarioDTOMapper());
			return diario;
		} catch (EmptyResultDataAccessException e) {
			log.info("diario não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<DiarioDTO> listar(FiltroDiario filtroDiario) {
		StringBuilder query = createQuery("select d.id, d.procedimento, d.recurso, d.observacao, d.id_atividade,  ");
        query.append("d.id_tipo_programa, t.nome as nome_programa ");
        query.append("from diario d ");
        query.append("left join tipo_programa t on (d.id_tipo_programa = t.id) where 1=1 ");
        query = andEqual(query, "d.id", filtroDiario.getId());
		query = andEqual(query, "d.id_atividade", filtroDiario.getIdAtividade());
        query = andEqual(query, "d.id_tipo_programa", filtroDiario.getIdTipoPrograma());
        query = orderBy(query, Order.ASC, "d.id_atividade");
		query = limit(query, filtroDiario.getQtdTotal(), filtroDiario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DiarioDTOMapper());
	}
	
	@Override
	public int contar(FiltroDiario filtroDiario) {
		StringBuilder query = createQuery("select count(d.id) ");
		query.append("from diario d ");
        query.append("left join tipo_programa t on (d.id_tipo_programa = t.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDiario.getId());
		query = andEqual(query, "d.id_atividade", filtroDiario.getIdAtividade());
        query = andEqual(query, "d.id_tipo_programa", filtroDiario.getIdTipoPrograma());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("diario", id);
		return true;
	}
	
	private class DiarioDTOMapper implements RowMapper<DiarioDTO> {

		@Override
		public DiarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DiarioDTO.builder()
					.id(rs.getLong("id"))
					.procedimento(rs.getString("procedimento"))
                    .recurso(rs.getString("recurso"))
                    .observacao(rs.getString("observacao"))
                    .idAtividade(rs.getLong("id_atividade"))
                    .idTipoPrograma(rs.getInt("id_tipo_programa"))
                    .nomePrograma(rs.getString("nome_programa"))
					.build();
		}
	}
}
