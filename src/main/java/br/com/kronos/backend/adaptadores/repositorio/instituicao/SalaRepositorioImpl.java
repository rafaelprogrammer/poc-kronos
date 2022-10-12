package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSala;
import br.com.kronos.backend.aplicacao.instituicao.Sala;
import br.com.kronos.backend.aplicacao.instituicao.SalaRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SalaRepositorioImpl extends SqlQueryRepositorio implements SalaRepositorio {
	
	public SalaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Sala sala) {
        addFields("bloco", "ala" , "andar", "numero",
        		  "lotacao", "pool_reserva", "ativo", "id_instituicao",
        		  "id_tipo_sala");
		
		addValues(sala.getBloco(), sala.getAla(),  sala.getAndar(),  sala.getNumero(),
				 sala.getLotacao(),  sala.isPoolReserva(),  sala.isAtivo(),  sala.getIdInstitucicao(),
				 sala.getIdTipoSala()); 
		
		return (long) insertAuto("sala"); 
    }
	
	@Override
	public Long alterar(Sala sala) {
		addFields("bloco", "ala" , "andar", "numero",
      		      "lotacao", "pool_reserva", "ativo", "id_instituicao",
      		      "id_tipo_sala");
		
		addValues(sala.getBloco(), sala.getAla(),  sala.getAndar(),  sala.getNumero(),
				 sala.getLotacao(),  sala.isPoolReserva(),  sala.isAtivo(),  sala.getIdInstitucicao(),
				 sala.getIdTipoSala(), sala.getId());
		
		return (long) update("sala", sala.getId());
	}
		
	@Override
	public Sala buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select s.bloco, s.ala, s.andar, s.numero, ");
            query.append("s.lotacao, s.pool_reserva, s.ativo, s.id_instituicao, ");
            query.append("s.id_tipo_sala ");
            query.append("from sala s where 1=1 ");
            query = andEqual(query, "s.id", id);
			Sala sala = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new SalaMapper());
			return sala;
		} catch (EmptyResultDataAccessException e) {
			log.info("sala não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Sala> listar(FiltroSala filtroSala) {
		  StringBuilder query = createQuery("select s.bloco, s.ala, s.andar, s.numero, ");
          query.append("s.lotacao, s.pool_reserva, s.ativo, s.id_instituicao, ");
          query.append("s.id_tipo_sala ");
          query.append("from sala s where 1=1 ");
          query = andEqual(query, "s.id", filtroSala.getId());
	   	  query = andEqual(query, "s.id_tipo_sala", filtroSala.getIdTipoSala());
	   	  query = andEqual(query, "s.id_instituicao", filtroSala.getIdInstituicao());
          query = orderBy(query, Order.ASC, "s.id_tipo_sala");
		  query = limit(query, filtroSala.getQtdTotal(), filtroSala.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SalaMapper());
	}
	
	@Override
	public int contar(FiltroSala filtroSala) {
		  StringBuilder query = createQuery("select count(sm.id) from sala s where 1=1 ");
		  query = andEqual(query, "s.id", filtroSala.getId());
	   	  query = andEqual(query, "s.id_tipo_sala", filtroSala.getIdTipoSala());
	   	  query = andEqual(query, "s.id_instituicao", filtroSala.getIdInstituicao());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("sala", id);
		return true;
	}
	
	private class SalaMapper implements RowMapper<Sala> {

		@Override
		public Sala mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Sala.builder()
					.id(rs.getLong("id"))
                    .bloco(rs.getString("bloco"))
                    .ala(rs.getString("ala"))
                    .andar(rs.getString("andar"))
                    .numero(rs.getString("numaro"))
                    .lotacao(rs.getInt("lotacao"))
                    .poolReserva(rs.getBoolean("pool_reserva"))
                    .ativo(rs.getBoolean("ativo"))
                    .idInstitucicao(rs.getLong("id_instituicao"))
                    .idTipoSala(rs.getInt("id_tipo_sala"))
					.build();
		}
	}
}




