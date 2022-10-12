package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoResponsavel;
import br.com.kronos.backend.aplicacao.pessoa.FiltroResponsavel;
import br.com.kronos.backend.aplicacao.pessoa.Responsavel;
import br.com.kronos.backend.aplicacao.pessoa.ResponsavelRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponsavelRepositorioImpl extends SqlQueryRepositorio implements ResponsavelRepositorio {
	
	public ResponsavelRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(Responsavel responsavel) {
		addFields("data_inicio", "data_fim", "prioridade", 
				"id_tipo_responsavel", "id_pessoa", "id_pessoa_responsavel");
		
		addValues(responsavel.getDataInicio(), responsavel.getDataFim(), responsavel.getPrioridade(), 
				responsavel.getIdTipoResponsavel(), responsavel.getIdPessoa(), responsavel.getIdPessoaResponsavel());
		
		return insertAuto("responsavel");
	}
	
	@Override
	public int alterar(Responsavel responsavel) {
		addFields("data_inicio", "data_fim", "prioridade", 
				"id_tipo_responsavel", "id_pessoa", "id_pessoa_responsavel");
		
		addValues(responsavel.getDataInicio(), responsavel.getDataFim(), responsavel.getPrioridade(), 
				responsavel.getIdTipoResponsavel(), responsavel.getIdPessoa(), responsavel.getIdPessoaResponsavel(), responsavel.getId());
		
		return update("responsavel", responsavel.getId());
	}
	
	@Override
	public ResponsavelDTO buscarPorId(int id) {
		try {     
			StringBuilder query = createQuery("select r.id, r.data_inicio, r.data_fim, id_pessoa, id_pessoa_responsavel, pr.nome, id_tipo_responsavel, prioridade ");
			query.append(" from responsavel r ");
			query.append(" join pessoa pr on (r.id_pessoa_responsavel = pr.id) where 1=1 ");
			query = andEqual(query, "r.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), new ResponsavelDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("responsavel não existe - " + id);
			return null;
		}
	} 

	@Override
	public List<ResponsavelDTO> listar(FiltroResponsavel filtro) {
		StringBuilder query = createQuery("select r.id, r.data_inicio, r.data_fim, id_pessoa, id_pessoa_responsavel, pr.nome, id_tipo_responsavel, prioridade ");
		query.append(" from responsavel r ");
		query.append(" join pessoa pr on (r.id_pessoa_responsavel = pr.id) where 1=1 ");
		query = andEqual(query, "r.id", filtro.getId());
		query = andEqual(query, "r.id_pessoa", filtro.getIdPessoa());
		query = andEqual(query, "r.id_tipo_responsavel", filtro.getIdTipoPessoaResponsavel());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResponsavelDTOMapper());
	}
	
	@Override
	public int contar(FiltroResponsavel filtro) {
		StringBuilder query = createQuery("select count(r.id) from responsavel r where 1=1 ");
		query = andEqual(query, "r.id", filtro.getId());
		query = andEqual(query, "r.id_pessoa", filtro.getIdPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("responsavel", id);
		return true;
	}
	
	private class ResponsavelDTOMapper implements RowMapper<ResponsavelDTO> {

		@Override
		public ResponsavelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResponsavelDTO.builder()
					.id(rs.getInt("id"))
					.idPessoa(rs.getLong("id_pessoa"))
					.idPessoaResponsavel(rs.getLong("id_pessoa_responsavel"))
					.nomePessoaResponsavel(rs.getString("nome"))
					.dataInicio(rs.getDate("data_inicio").toLocalDate())
					.dataFim(rs.getDate("data_fim") !=null ? rs.getDate("data_fim").toLocalDate() : null)
					.prioridade(rs.getInt("prioridade"))
					.idTipoResponsavel(rs.getInt("id_tipo_responsavel"))
					.build();
		}

	}
	
	@Override
	public List<ResponsavelDTO> listarParaOcorrenciaResponsavelCiencia(LocalDate dataOcorrencia, Long idPessoaSelecionada) {
		StringBuilder query = createQuery("select r.id, p.nome  ");
        query.append("from responsavel r ");
        query.append("left join pessoa p on (r.id_pessoa_responsavel = p.id) where 1=1 ");
        query = andEqual(query, "r.id_pessoa", idPessoaSelecionada);
        query = andEqual(query, "r.id_tipo_responsavel", EnumTipoResponsavel.TRANSPPORTE.id());
        
        query.append(" and r.data_inicio <= ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append(" and (r.data_fim is null or r.data_fim > ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd')) ");

		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResponsavelDTOParaOcorrenciaMapper());
	}
	
	private class ResponsavelDTOParaOcorrenciaMapper implements RowMapper<ResponsavelDTO> {

		@Override
		public ResponsavelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResponsavelDTO.builder()
					.id(rs.getInt("id"))
					.nomePessoaResponsavel(rs.getString("nome"))
					.build();
		}

	}


}
