package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.Escala;
import br.com.kronos.backend.aplicacao.instituicao.EscalaRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroEscala;
import br.com.kronos.backend.aplicacao.instituicao.api.MensaoLimiteDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EscalaRepositorioImpl extends SqlQueryRepositorio implements EscalaRepositorio {
	
	public EscalaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Escala escala) {
        addFields("nome", "data_inicial_vigencia", "data_final_vigencia", "idInstituicao", 
                  "idTipoAbrangencia", "idTipoAvaliacao");
		
		addValues(escala.getNome(), escala.getDataInicialVigencia(), escala.getDataFinalVigencia(), escala.getIdInstituicao(), 
                  escala.getIdTipoAbrangencia(), escala.getIdTipoAvaliacao()); 
		
		return (long) insertAuto("escala"); 
    }

	@Override
	public Long alterar(Escala escala) {
		addFields("nome", "data_inicial_vigencia", "data_final_vigencia", "id_instituicao", 
                "id_tipo_abrangencia", "id_tipo_avaliacao");
		
		addValues(escala.getNome(), escala.getDataInicialVigencia(), escala.getDataFinalVigencia(), escala.getIdInstituicao(), 
                escala.getIdTipoAbrangencia(), escala.getIdTipoAvaliacao(), escala.getId());
		
		return (long) update("escala", escala.getId());
	}
		
	@Override
	public Escala buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select e.id, e.nome, e.data_inicial_vigencia, e.data_final_vigencia, e.id_instituicao, ");
            query.append("e.id_tipo_abrangencia, e.id_tipo_avaliacao ");
            query.append("from escala e where 1=1 ");
            query = andEqual(query, "e.id", id);
			Escala escala = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new EscalaMapper());
			return escala;
		} catch (EmptyResultDataAccessException e) {
			log.info("escala não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Escala> listar(FiltroEscala filtroEscala) {
		  StringBuilder query = createQuery("select e.id, e.nome, e.data_inicial_vigencia, e.data_final_vigencia, e.id_instituicao, ");
          query.append("e.id_tipo_abrangencia, e.id_tipo_avaliacao ");
          if(filtroEscala.getIdCurso() != null) {
        	  query.append("from escala e join curso_escala c on (e.id = c.id_escala) ");  
          } else {
        	  query.append("from escala e ");
          }
          query.append("where 1=1 ");
          query = andEqual(query, "e.id", filtroEscala.getId());
	   	  query = andLike(query, "c.nome", filtroEscala.getNome());
          query = andEqual(query, "e.id_instituicao", filtroEscala.getIdInstituicao());
          query = andEqual(query, "e.id_tipo_abrangencia", filtroEscala.getIdTipoAbrangencia());
          query = andEqual(query, "e.id_tipo_avaliacao", filtroEscala.getIdTipoAvaliacao());
          if(filtroEscala.getIdCurso() != null) {
        	  query = andEqual(query, "c.id_curso", filtroEscala.getIdCurso());
              query = groupBy(query, "e.id, e.nome, e.data_inicial_vigencia, e.data_final_vigencia, e.id_instituicao, e.id_tipo_abrangencia, e.id_tipo_avaliacao");
          }
          query = orderBy(query, Order.DESC, "e.data_inicial_vigencia");
		  query = limit(query, filtroEscala.getQtdTotal(), filtroEscala.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new EscalaMapper());
	}
	
	@Override
	public int contar(FiltroEscala filtroEscala) {
		StringBuilder query = null;
		if(filtroEscala.getIdCurso() != null) {
			query = createQuery("select count(e.id) from escala e left join curso_escala c on (e.id = c.id_escala) where 1=1 ");
		} else {
			query = createQuery("select count(e.id) from escala e where 1=1 ");
		}
		query = andEqual(query, "e.id", filtroEscala.getId());
		if(filtroEscala.getIdCurso() != null) {
			query = andEqual(query, "c.id_curso", filtroEscala.getIdCurso());
		}
	   	query = andLike(query, "c.nome", filtroEscala.getNome());
        query = andEqual(query, "e.id_instituicao", filtroEscala.getIdInstituicao());
        query = andEqual(query, "e.id_tipo_abrangencia", filtroEscala.getIdTipoAbrangencia());
        query = andEqual(query, "e.id_tipo_avaliacao", filtroEscala.getIdTipoAvaliacao());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("escala", id);
		return true;
	}
	
	@Override
	public List<MensaoLimiteDTO> listarMensaoELimite(FiltroEscala filtro) {
		  StringBuilder query = createQuery("select l.id as idLimite, m.id as idMensao, l.minimo, l.maximo, m.nome, m.simbolo from escala e ");
          query.append("join curso_escala ce on (e.id = ce.id_escala) ");
          query.append("join limite l on (e.id = l.id_escala) ");
          query.append("join mencao m on (l.id_tipo_mencao = m.id) ");
          query.append("where 1=1 ");
          query = andEqual(query, "ce.id_curso", filtro.getIdCurso());
	   	  query = andEqual(query, "e.id", filtro.getId());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MensaoLimiteDTOMapper());
	}
	
	private class MensaoLimiteDTOMapper implements RowMapper<MensaoLimiteDTO> {

		@Override
		public MensaoLimiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MensaoLimiteDTO.builder()
					.idLimite(rs.getLong("idLimite"))
					.idMensao(rs.getLong("idMensao"))
					.minimo(rs.getDouble("minimo"))
					.maximo(rs.getDouble("maximo"))
					.nome(rs.getString("nome"))
					.simbolo(rs.getString("simbolo"))
					.build();
		}
	}
	
	private class EscalaMapper implements RowMapper<Escala> {

		@Override
		public Escala mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Escala.builder()
					.id(rs.getLong("id"))
					.nome(rs.getString("nome"))
					.dataInicialVigencia(rs.getDate("data_inicial_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .idTipoAbrangencia(rs.getInt("id_instituicao"))
                    .idTipoAvaliacao(rs.getInt("id_tipo_abrangencia"))
					.build();
		}
	}

}


