package br.com.kronos.backend.adaptadores.repositorio.ocorrencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.Ocorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.OcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcorrenciaRepositorioImpl extends SqlQueryRepositorio implements OcorrenciaRepositorio {
	
	public OcorrenciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Ocorrencia ocorrencia) {
        addFields("data", "hora", "registro", "data_ciencia", 
                  "anulado", "data_anulacao", "motivo_anulacao",  "id_tipo_ocorrencia", 
                  "id_matricula", "id_funcionario_relator", "id_funcionario_registro", "id_responsavel_ciencia", 
                  "id_funcionario_anulacao", "id_turma");
		
		addValues(ocorrencia.getData(), ocorrencia.getHora(), ocorrencia.getRegistro(),ocorrencia.getDataCiencia(), 
                  ocorrencia.isAnulado(), ocorrencia.getDataAnulacao(), ocorrencia.getMotivoAnulacao(), ocorrencia.getIdTipoOcorrencia(),  
                  ocorrencia.getIdMatricula(), ocorrencia.getIdFuncionarioRelator(), ocorrencia.getIdFuncionarioRegistro(), ocorrencia.getIdResponsavelCiencia(), 
                  ocorrencia.getIdFuncionarioAnulacao(), ocorrencia.getIdTurma());
		
		return (long) insertAuto("ocorrencia"); 
    }
	
	@Override
	public Long alterar(Ocorrencia ocorrencia) {
		addFields("data", "hora", "registro", "data_ciencia", 
                  "anulado", "data_anulacao", "motivo_anulacao",  "id_tipo_ocorrencia", 
                  "id_matricula", "id_funcionario_relator", "id_funcionario_registro", "id_responsavel_ciencia", 
                  "id_funcionario_anulacao", "id_turma");
		
		addValues(ocorrencia.getData(), ocorrencia.getHora(), ocorrencia.getRegistro(),ocorrencia.getDataCiencia(), 
                ocorrencia.isAnulado(), ocorrencia.getDataAnulacao(), ocorrencia.getMotivoAnulacao(), ocorrencia.getIdTipoOcorrencia(),  
                ocorrencia.getIdMatricula(), ocorrencia.getIdFuncionarioRelator(), ocorrencia.getIdFuncionarioRegistro(), ocorrencia.getIdResponsavelCiencia(), 
                ocorrencia.getIdFuncionarioAnulacao(), ocorrencia.getIdTurma(), ocorrencia.getId());
		
		return (long) update("ocorrencia", ocorrencia.getId());
	}
	
	@Override
	public OcorrenciaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select o.id, o.data, o.hora, o.registro, o.data_ciencia, ");
			query.append("o.anulado, o.data_anulacao, o.motivo_anulacao,  o.id_tipo_ocorrencia, ");
			query.append("o.id_matricula, o.id_funcionario_relator, o.id_funcionario_registro, o.id_responsavel_ciencia, "); 
			query.append("o.id_funcionario_anulacao, o.id_turma, t.valor, t.fator ");
            query.append("from ocorrencia o ");
            query.append("left join tipo_ocorrencia t on (o.id_tipo_ocorrencia = t.id) ");
            query.append("left join matricula m on (o.id_matricula = m.id) where 1=1 ");
            query = andEqual(query, "o.id", id);
            OcorrenciaDTO ocorrencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new OcorrenciaDTOMapper());
			return ocorrencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("ocorrencia não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public Float returnaValorGrauComportamento(Long idPessoaSelecionada) {
		try {
			StringBuilder query = createQuery("select CASE ");
			query.append("WHEN (8 + sum(t.fator * t.valor)) > 10 THEN 10 ");
			query.append("WHEN (8 + sum(t.fator * t.valor)) < 0 THEN 0 ");
			query.append("ELSE 8 + sum(t.fator * t.valor) ");
			query.append("END as grau_comportamento ");
            query.append("from ocorrencia o ");
            query.append("left join tipo_ocorrencia t on (o.id_tipo_ocorrencia = t.id) ");
            query.append("left join matricula m on (o.id_matricula = m.id) where 1=1 ");
            query.append("and o.anulado = false ");
            query = andEqual(query, "m.id_pessoa", idPessoaSelecionada);
           return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), Float.class);
		} catch (EmptyResultDataAccessException e) {
			log.info("ocorrencia não existe para a pessoa - " + idPessoaSelecionada);
			return null;
		}
	}
	
	@Override
	public List<OcorrenciaDTO> listar(FiltroOcorrencia filtroOcorrencia) {
		StringBuilder query = createQuery("select o.id, o.data, o.hora, o.registro, o.data_ciencia, ");
		query.append("o.anulado, o.data_anulacao, o.motivo_anulacao,  o.id_tipo_ocorrencia, ");
		query.append("o.id_matricula, o.id_funcionario_relator, o.id_funcionario_registro, o.id_responsavel_ciencia, "); 
		query.append("o.id_funcionario_anulacao, o.id_turma, t.valor, t.fator ");
        query.append("from ocorrencia o ");
        query.append("left join tipo_ocorrencia t on (o.id_tipo_ocorrencia = t.id) ");
        query.append("left join matricula m on (o.id_matricula = m.id) where 1=1 ");

        query = andEqual(query, "o.id", filtroOcorrencia.getId());
		query = andEqual(query, "o.id_turma", filtroOcorrencia.getIdTurma());
        query = andEqual(query, "o.id_tipo_ocorrencia", filtroOcorrencia.getIdTipoOcorrencia());
        query = andEqual(query, "o.id_matricula", filtroOcorrencia.getIdMatricula());   
        query = andEqual(query, "m.id_pessoa", filtroOcorrencia.getIdPessoa());   
        query = orderBy(query, Order.DESC, "o.data");
		query = limit(query, filtroOcorrencia.getQtdTotal(), filtroOcorrencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new OcorrenciaDTOMapper());
	}
	
	@Override
	public int contar(FiltroOcorrencia filtroOcorrencia) {
		StringBuilder query = createQuery("select count(o.id) from ocorrencia o ");
		query.append("left join tipo_ocorrencia t on (o.id_tipo_ocorrencia = t.id) ");
        query.append("left join matricula m on (o.id_matricula = m.id) where 1=1 ");
		query = andEqual(query, "o.id", filtroOcorrencia.getId());
		query = andEqual(query, "o.id_turma", filtroOcorrencia.getIdTurma());
        query = andEqual(query, "o.id_tipo_ocorrencia", filtroOcorrencia.getIdTipoOcorrencia());
        query = andEqual(query, "o.id_matricula", filtroOcorrencia.getIdMatricula());
        query = andEqual(query, "m.id_pessoa", filtroOcorrencia.getIdPessoa());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("ocorrencia", id);
		return true;
	}
	
	private class OcorrenciaDTOMapper implements RowMapper<OcorrenciaDTO> {

		@Override
		public OcorrenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return OcorrenciaDTO.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.hora(rs.getTime("hora").toLocalTime())
                    .registro(rs.getString("registro"))
                    .dataCiencia(rs.getDate("data_ciencia") != null ? rs.getDate("data_ciencia").toLocalDate() : null)
                    .anulado(rs.getBoolean("anulado"))
                    .dataAnulacao(rs.getDate("data_anulacao") !=null ? rs.getDate("data_anulacao").toLocalDate() : null)
                    .motivoAnulacao(rs.getString("motivo_anulacao"))
                    .idTipoOcorrencia(rs.getLong("id_tipo_ocorrencia"))
                    .idMatricula(rs.getLong("id_matricula"))
                    .idFuncionarioRelator(rs.getLong("id_funcionario_relator"))
                    .idFuncionarioRegistro(rs.getLong("id_funcionario_registro"))
                    .idResponsavelCiencia(rs.getLong("id_responsavel_ciencia"))
                    .idFuncionarioAnulacao(rs.getLong("id_funcionario_anulacao"))
                    .idTurma(rs.getLong("id_turma"))
                    .fator(rs.getInt("fator"))
                    .valor(rs.getDouble("valor"))
					.build();
		}
	}
}

