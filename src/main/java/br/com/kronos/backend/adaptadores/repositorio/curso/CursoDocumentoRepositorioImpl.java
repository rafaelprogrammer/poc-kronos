package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoDocumento;
import br.com.kronos.backend.aplicacao.curso.CursoDocumento;
import br.com.kronos.backend.aplicacao.curso.CursoDocumentoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CursoDocumentoRepositorioImpl extends SqlQueryRepositorio implements CursoDocumentoRepositorio {
	
	public CursoDocumentoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(CursoDocumento cursoDocumento) {
        addFields("original", "autenticado", "numero_copia", "id_curso", 
                  "id_tipo_documento"); 
		
		addValues(cursoDocumento.isOriginal(), cursoDocumento.isAutenticacao(), cursoDocumento.getNumeroCopia(),cursoDocumento.getIdCurso(), 
                  cursoDocumento.getIdTipoDocumento());
		
		return (long) insertAuto("curso_documento");
    }

	@Override
	public Long alterar(CursoDocumento cursoDocumento) {
		addFields("original", "autenticado", "numero_copia", "id_curso", 
                  "id_tipo_documento"); 
		
		addValues(cursoDocumento.isOriginal(), cursoDocumento.isAutenticacao(), cursoDocumento.getNumeroCopia(),cursoDocumento.getIdCurso(), 
                  cursoDocumento.getIdTipoDocumento(), cursoDocumento.getId());
		
		return (long) update("curso_documento", cursoDocumento.getId());
	}
	
	@Override
	public CursoDocumento buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.id, c.original, c.autenticado, c.numero_copia, c.id_curso, ");
            query.append("c.id_tipo_documento  from curso_documento c where 1=1 ");
            query = andEqual(query, "c.id", id);
			CursoDocumento cursoDocumento = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CursoDocumentoMapper());
			return cursoDocumento;
		} catch (EmptyResultDataAccessException e) {
			log.info("CursoDocumento não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<CursoDocumento> listar(FiltroCursoDocumento filtroCursoDocumento) {
		StringBuilder query = createQuery("select c.id, c.original, c.autenticado, c.numero_copia, c.id_curso, ");
        query.append("c.id_tipo_documento  from curso_documento c where 1=1 ");
		query = andEqual(query, "c.id", filtroCursoDocumento.getId());
        query = andEqual(query, "c.id_curso", filtroCursoDocumento.getIdCurso()); 
        query = andEqual(query, "c.id_tipo_documento", filtroCursoDocumento.getIdTipoDocumento());
        query = orderBy(query, Order.ASC, "c.id_tipo_documento");
		query = limit(query, filtroCursoDocumento.getQtdTotal(), filtroCursoDocumento.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoDocumentoMapper());
	}
	
	@Override
	public int contar(FiltroCursoDocumento filtroCursoDocumento) {
		StringBuilder query = createQuery("select count(c.id) from curso_documento c where 1=1 ");
		query = andEqual(query, "c.id", filtroCursoDocumento.getId());
        query = andEqual(query, "c.id_curso", filtroCursoDocumento.getIdCurso()); 
        query = andEqual(query, "c.id_tipo_documento", filtroCursoDocumento.getIdTipoDocumento());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("curso_documento", id);
		return true;
	}
	
	private class CursoDocumentoMapper implements RowMapper<CursoDocumento> {

		@Override
		public CursoDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoDocumento.builder()
                    .id(rs.getLong("id"))
                    .original(rs.getBoolean("original"))
                    .autenticacao(rs.getBoolean("autenticado"))
                    .numeroCopia(rs.getInt("numero_copia"))
                    .idCurso(rs.getLong("id_curso"))
                    .idTipoDocumento(rs.getInt("id_tipo_documento"))
                    .build();
		}

	}

}
