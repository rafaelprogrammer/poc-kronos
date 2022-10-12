package br.com.kronos.backend.adaptadores.repositorio.diario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.diario.Atividade;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaDireito;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.diario.AtividadeRepositorio;
import br.com.kronos.backend.aplicacao.diario.FiltroAtividade;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaCompetenciaDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaDireitoDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaHabilidadeDTO;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeDisciplinaObjetivoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtividadeRepositorioImpl extends SqlQueryRepositorio implements AtividadeRepositorio {
	
	public AtividadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
//	@CacheEvict(value = {Atividade.CACHE_NAME, Atividade.CACHE_NAME_COUNT}, allEntries=true)
	public Long criar(Atividade atividade) {
        addFields("data_prevista", "data_realizacao", "id_horario", "id_sub_fase_execucao", 
                  "id_funcionario" );
		
		addValues(atividade.getDataPrevista(), atividade.getDataRealizacao(), atividade.getIdHorario(),atividade.getIdSubFaseExecucao(), 
                  atividade.getIdFuncionario()); 
		
		return (long) insertAuto("atividade"); 
    }
		
	@Override
//	@CacheEvict(value = {Atividade.CACHE_NAME, Atividade.CACHE_NAME_COUNT}, allEntries=true)
	public Long alterar(Atividade atividade) {
		addFields("data_prevista", "data_realizacao", "id_horario", "id_sub_fase_execucao", 
                "id_funcionario" );
		
		addValues(atividade.getDataPrevista(), atividade.getDataRealizacao(), atividade.getIdHorario(),atividade.getIdSubFaseExecucao(), 
                atividade.getIdFuncionario(), atividade.getId());
		
		return (long) update("atividade", atividade.getId());
	}
	
	@Override
	public boolean verificarDiarioCadastrado(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select CASE ");
        query.append("WHEN count(id) > 0 then true ELSE false ");
		query.append("END ");
		query.append("from diario where 1=1 ");
        query = andEqual(query, "id_atividade", filtroAtividade.getId());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Boolean.class);
	}
	
	@Override
	public boolean verificarHabilidadesCadastradas(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select CASE ");
        query.append("WHEN count(id_disciplina_habilidade) > 0 then true ");
		query.append("ELSE false END ");
		query.append("from atividade_disciplina_habilidade where 1=1 ");
        query = andEqual(query, "id_atividade", filtroAtividade.getId());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Boolean.class);
	}
	
	@Override
	public boolean verificarFrequenciaCadastrada(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select CASE ");
        query.append("WHEN ((select count(c.id) ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join turma t on (h.id_turma = t.id) ");
		query.append("left join credito c on (t.id = c.id_turma) ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) where 1=1 ");
		query = andEqual(query, "a.id", filtroAtividade.getId());
		query = andEqual(query, "c.id_disciplina", filtroAtividade.getIdDisciplina());
		query = andEqual(query, "ct.ano", filtroAtividade.getAnoTurma());
		query.append(" ) - count(*)) > 0 then false ELSE true ");
        query.append(" END ");
        query.append(" from frequencia f ");
        query.append(" where STRPOS(f.frequencia::text, 'I') = 0 ");
        query = andEqual(query, "f.id_atividade", filtroAtividade.getId());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Boolean.class);
	}
	
	@Override
	public List<String> listarHorasIniciaisAtividades(Long idAtividade) {
		StringBuilder query = createQuery("select distinct to_char(ha.hora_inicial, 'HH:mm') ");
		query.append("from hora_atividade ha ");
		query.append("left join horario_hora_atividade hh on (ha.id = hh.id_hora_atividade) ");
		query.append("left join horario h on (hh.id_horario = h.id) ");
		query.append("left join atividade a on (h.id = a.id_horario) where 1=1 ");
		query = andEqual(query, "a.id", idAtividade);
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), String.class);
	}
	
	@Override
	public List<String> recuperarHorasAtividades(Long idAtividade) {
		StringBuilder query = createQuery("select to_char(ha.hora_inicial, 'HH24:MI') || ' - ' || to_char(ha.hora_final, 'HH24:MI') ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join horario_hora_atividade hha on (hha.id_horario = h.id) ");
		query.append("left join hora_atividade ha on (ha.id = hha.id_hora_atividade) where 1=1 ");
		query = andEqual(query, "a.id", idAtividade);
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), String.class);
	}
	
	@Override
	public AtividadeDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select a.id, a.data_prevista, t.nome as dia_semana, a.data_realizacao,  ");
            query.append("r.nome as regime_letivo, sf.sigla as bimestre, p.nome_usual as professor, a.id_horario, a.id_sub_fase_execucao, a.id_funcionario ");
            query.append("from atividade a ");
            query.append("left join horario h on (a.id_horario = h.id) ");
            query.append("left join sub_fase_execucao sfe on (a.id_sub_fase_execucao = sfe.id) ");
            query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) ");
            query.append("left join funcionario f on (a.id_funcionario = f.id) ");
            query.append("left join pessoa p on (f.id_pessoa = p.id) ");
            query.append("left join tipo_dia_semana t on (h.id_tipo_dia_semana = t.id) ");
            query.append("left join tipo_regime_letivo r on (h.id_tipo_regime_letivo = r.id) where 1=1 ");
            query = andEqual(query, "a.id", id);
            AtividadeDTO atividade = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AtividadeDTOMapper());
			return atividade;
		} catch (EmptyResultDataAccessException e) {
			log.info("atividade não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<AtividadeDTO> listarDatasAtividades(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select distinct a.id, a.data_prevista  ");
        query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) where 1=1 ");
        query = andEqual(query, "h.id_turma", filtroAtividade.getIdTurma());
        query = andEqual(query, "h.id_disciplina", filtroAtividade.getIdDisciplina());
        query = orderBy(query, Order.ASC, "a.data_prevista");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDTOParaListaDatasMapper());
	}
	
	@Override
//	@Cacheable(cacheNames = Atividade.CACHE_NAME, key="#filtroAtividade.hashCode()")
	public List<AtividadeDTO> listar(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select a.id, a.data_prevista, a.data_realizacao, a.id_horario, a.id_sub_fase_execucao,  ");
        query.append("a.id_funcionario, t.sigla as dia_semana, ");
        query.append("CASE ");
        query.append("WHEN count(av.id) > 0 then true ELSE false ");
        query.append("END as avaliacao ");
        query.append("from atividade a ");
        query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join funcionario f on (a.id_funcionario = f.id) ");
		query.append("left join tipo_dia_semana t on (h.id_tipo_dia_semana = t.id) ");
		query.append("left join avaliacao av on (a.id = av.id_atividade) where 1=1 ");
        query = andEqual(query, "a.id", filtroAtividade.getId());
		query = andEqual(query, "a.data_prevista", filtroAtividade.getDataPrevista());   
        query = andEqual(query, "a.data_realizacao", filtroAtividade.getDataRealizacao());   
        query = andEqual(query, "a.id_horario", filtroAtividade.getIdHorario()); 
        query = andEqual(query, "a.id_sub_fase_execucao", filtroAtividade.getIdSubFaseExecucao());
        query = andEqual(query, "a.id_funcionario", filtroAtividade.getIdFuncionario());
        query = andEqual(query, "h.id_turma", filtroAtividade.getIdTurma());
        query = andEqual(query, "h.id_disciplina", filtroAtividade.getIdDisciplina());
        query = andEqual(query, "f.id_pessoa", filtroAtividade.getIdPessoaUsuarioLogado());
        query = groupBy(query, "a.id, a.data_prevista, a.data_realizacao, a.id_horario, a.id_sub_fase_execucao, a.id_funcionario, t.sigla");
        query = orderBy(query, Order.ASC, "a.data_prevista");
		query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDTOParaListarMapper());
	}
	
	@Override
//	@Cacheable(cacheNames = Atividade.CACHE_NAME_COUNT, key="#filtroAtividade.hashCode()")
	public int contar(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(distinct a.id) ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join funcionario f on (a.id_funcionario = f.id) ");
		query.append("left join tipo_dia_semana t on (h.id_tipo_dia_semana = t.id) ");
		query.append("left join avaliacao av on (a.id = av.id_atividade) where 1=1 ");
        query = andEqual(query, "a.id", filtroAtividade.getId());
        if (filtroAtividade.getDataPrevista() != null) {
        	query = andEqualDate(query, "a.data_prevista", filtroAtividade.getDataPrevista().toString());
        }
        query = andEqual(query, "a.data_realizacao", filtroAtividade.getDataRealizacao());   
        query = andEqual(query, "a.id_horario", filtroAtividade.getIdHorario()); 
        query = andEqual(query, "a.id_sub_fase_execucao", filtroAtividade.getIdSubFaseExecucao());
        query = andEqual(query, "a.id_funcionario", filtroAtividade.getIdFuncionario());
        query = andEqual(query, "h.id_turma", filtroAtividade.getIdTurma());
        query = andEqual(query, "h.id_disciplina", filtroAtividade.getIdDisciplina());
        query = andEqual(query, "f.id_pessoa", filtroAtividade.getIdPessoaUsuarioLogado());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public int verificarSeAtividadeExiste(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(a.id) ");
		query.append("from atividade a ");
		query.append("join horario h on (a.id_horario = h.id) ");
		query.append("left join funcionario f on (a.id_funcionario = f.id) ");
		query.append("left join tipo_dia_semana t on (h.id_tipo_dia_semana = t.id) where 1=1 ");
        query = andEqual(query, "a.id", filtroAtividade.getId());
        if (filtroAtividade.getDataPrevista() != null) {
        	query = andEqualDate(query, "a.data_prevista", filtroAtividade.getDataPrevista().toString());
        }
        query = andEqual(query, "a.id_horario", filtroAtividade.getIdHorario()); 
        query = andEqual(query, "a.id_funcionario", filtroAtividade.getIdFuncionario());
        query = andEqual(query, "a.id_sub_fase_execucao", filtroAtividade.getIdSubFaseExecucao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("atividade", id);
		return true;
	}
	
	private class AtividadeDTOParaListaDatasMapper implements RowMapper<AtividadeDTO> {

		@Override
		public AtividadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDTO.builder()
					.id(rs.getLong("id"))
					.dataPrevista(rs.getDate("data_prevista").toLocalDate())
					.build();
		}
	}
	
	private class AtividadeDTOParaListarMapper implements RowMapper<AtividadeDTO> {

		@Override
		public AtividadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDTO.builder()
					.id(rs.getLong("id"))
					.dataPrevista(rs.getDate("data_prevista").toLocalDate())
	                .dataRealizacao(rs.getDate("data_realizacao") != null ? rs.getDate("data_realizacao").toLocalDate() : null)
                    .idHorario(rs.getLong("id_horario"))
                    .idSubFaseExecucao(rs.getLong("id_sub_fase_execucao"))
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .diaDaSemana(rs.getString("dia_semana"))
                    .existeAvaliacao(rs.getBoolean("avaliacao"))
					.build();
		}
	}
	
	private class AtividadeDTOMapper implements RowMapper<AtividadeDTO> {

		@Override
		public AtividadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDTO.builder()
					.id(rs.getLong("id"))
					.dataPrevista(rs.getDate("data_prevista").toLocalDate())
	                .dataRealizacao(rs.getDate("data_realizacao") != null ? rs.getDate("data_realizacao").toLocalDate() : null)
                    .idHorario(rs.getLong("id_horario"))
                    .idSubFaseExecucao(rs.getLong("id_sub_fase_execucao"))
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .bimestre(rs.getString("bimestre"))
                    .diaDaSemana(rs.getString("dia_semana"))
                    .regimeLetivo(rs.getString("regime_letivo"))
                    .professor(rs.getString("professor"))
					.build();
		}
	}
	
	@Override
	public List<AtividadeDisciplinaDireitoDTO> listarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select ad.id_atividade, ad.id_disciplina_direito, d.id as id_direito, d.codigo, d.descricao, d.bncc, sf.sigla  ");
        query.append("from atividade_disciplina_direito ad ");
        query.append("left join disciplina_direito dd on (ad.id_disciplina_direito = dd.id) ");
		query.append("left join direito d on (dd.id_direito = d.id) ");
		query.append("left join sub_fase sf on (dd.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "ad.id_atividade", filtroAtividade.getId());
        query = orderBy(query, Order.ASC, "d.codigo");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDisciplinaDireitoDTOMapper());
	}
	
	@Override
	public int contarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(ad.id_atividade)  ");
        query.append("from atividade_disciplina_direito ad ");
        query.append("left join disciplina_direito dd on (ad.id_disciplina_direito = dd.id) ");
		query.append("left join direito d on (dd.id_direito = d.id) where 1=1 ");
        query = andEqual(query, "ad.id_atividade", filtroAtividade.getId());
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}

	private class AtividadeDisciplinaDireitoDTOMapper implements RowMapper<AtividadeDisciplinaDireitoDTO> {

		@Override
		public AtividadeDisciplinaDireitoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDisciplinaDireitoDTO.builder()
					.idAtividade(rs.getLong("id_atividade"))
					.idDisciplinaDireito(rs.getLong("id_disciplina_direito"))
                    .idDireito(rs.getLong("id_direito"))
                    .codigoDireito(rs.getString("codigo"))
                    .descricaoDireito(rs.getString("descricao"))
                    .bncc(rs.getBoolean("bncc"))
                    .siglaSubFase(rs.getString("sigla"))
					.build();
		}
	}
	
	@Override
	public void criarAtividadeDisciplinaDireito(AtividadeDisciplinaDireito atividadeDisciplinaDireito) {
		addFields("id_disciplina_direito", "id_atividade");
		
		addValues(atividadeDisciplinaDireito.getIdDisciplinaDireito(), atividadeDisciplinaDireito.getIdAtividade()); 
		
		insert("atividade_disciplina_direito"); 
		
	}

	@Override
	public boolean excluirAtividadeDisciplinaDireito(Long idDisciplinaDireito, Long idAtividade) {
		addFields("id_disciplina_direito", "id_atividade");
		addValues(idDisciplinaDireito, idAtividade);
		delete("atividade_disciplina_direito");
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaObjetivoDTO> listarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select ao.id_atividade, ao.id_disciplina_objetivo, o.id as id_objetivo, o.codigo, o.descricao, o.bncc, sf.sigla  ");
        query.append("from atividade_disciplina_objetivo ao ");
        query.append("left join disciplina_objetivo dob on (ao.id_disciplina_objetivo = dob.id) ");
		query.append("left join objetivo o on (dob.id_objetivo = o.id) ");
		query.append("left join sub_fase sf on (dob.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "ao.id_atividade", filtroAtividade.getId());
        query = orderBy(query, Order.ASC, "o.codigo");
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDisciplinaObjetivoDTOMapper());
	}
	
	@Override
	public int contarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(ao.id_atividade)  ");
		query.append("from atividade_disciplina_objetivo ao ");
        query.append("left join disciplina_objetivo dob on (ao.id_disciplina_objetivo = dob.id) ");
		query.append("left join objetivo o on (dob.id_objetivo = o.id) where 1=1 ");
        query = andEqual(query, "ao.id_atividade", filtroAtividade.getId());
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AtividadeDisciplinaObjetivoDTOMapper implements RowMapper<AtividadeDisciplinaObjetivoDTO> {

		@Override
		public AtividadeDisciplinaObjetivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDisciplinaObjetivoDTO.builder()
					.idAtividade(rs.getLong("id_atividade"))
					.idDisciplinaObjetivo(rs.getLong("id_disciplina_objetivo"))
                    .idObjetivo(rs.getLong("id_objetivo"))
                    .codigoObjetivo(rs.getString("codigo"))
                    .descricaoObjetivo(rs.getString("descricao"))
                    .bncc(rs.getBoolean("bncc"))
                    .siglaSubFase(rs.getString("sigla"))
					.build();
		}
	}
	
	@Override
	public void criarAtividadeDisciplinaObjetivo(AtividadeDisciplinaObjetivo atividadeDisciplinaObjetivo) {
		addFields("id_disciplina_objetivo", "id_atividade");
		
		addValues(atividadeDisciplinaObjetivo.getIdDisciplinaObjetivo(), atividadeDisciplinaObjetivo.getIdAtividade()); 
		
		insert("atividade_disciplina_objetivo"); 
		
	}

	@Override
	public boolean excluirAtividadeDisciplinaObjetivo(Long idDisciplinaObjetivo, Long idAtividade) {
		addFields("id_disciplina_objetivo", "id_atividade");
		addValues(idDisciplinaObjetivo, idAtividade);
		delete("atividade_disciplina_objetivo");
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaCompetenciaDTO> listarAtividadesDisciplinasCompetencias(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select ac.id_atividade, ac.id_disciplina_competencia, c.id as id_competencia, c.codigo, c.descricao, c.bncc, sf.sigla  ");
        query.append("from atividade_disciplina_competencia ac ");
        query.append("left join disciplina_competencia dc on (ac.id_disciplina_competencia = dc.id) ");
		query.append("left join competencia c on (dc.id_competencia = c.id) ");
		query.append("left join sub_fase sf on (dc.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "ac.id_atividade", filtroAtividade.getId());
        query = orderBy(query, Order.ASC, "c.codigo");
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDisciplinaCompetenciaDTOMapper());
	}
	
	@Override
	public int contarAtividadesDisciplinasCompetencias(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(ac.id_atividade)  ");
		query.append("from atividade_disciplina_competencia ac ");
        query.append("left join disciplina_competencia dc on (ac.id_disciplina_competencia = dc.id) ");
		query.append("left join competencia c on (dc.id_competencia = c.id) where 1=1 ");
        query = andEqual(query, "ac.id_atividade", filtroAtividade.getId());
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AtividadeDisciplinaCompetenciaDTOMapper implements RowMapper<AtividadeDisciplinaCompetenciaDTO> {

		@Override
		public AtividadeDisciplinaCompetenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDisciplinaCompetenciaDTO.builder()
					.idAtividade(rs.getLong("id_atividade"))
					.idDisciplinaCompetencia(rs.getLong("id_disciplina_competencia"))
                    .idCompetencia(rs.getLong("id_competencia"))
                    .codigoCompetencia(rs.getString("codigo"))
                    .descricaoCompetencia(rs.getString("descricao"))
                    .bncc(rs.getBoolean("bncc"))
                    .siglaSubFase(rs.getString("sigla"))
					.build();
		}
	}
	
	@Override
	public void criarAtividadeDisciplinaCompetencia(AtividadeDisciplinaCompetencia atividadeDisciplinaCompetencia) {
		addFields("id_disciplina_competencia", "id_atividade");
		
		addValues(atividadeDisciplinaCompetencia.getIdDisciplinaCompetencia(), atividadeDisciplinaCompetencia.getIdAtividade()); 
		
		insert("atividade_disciplina_competencia"); 
		
	}
	
	@Override
	public boolean excluirAtividadeDisciplinaCompetencia(Long idDisciplinaCompetencia, Long idAtividade) {
		addFields("id_disciplina_competencia", "id_atividade");
		addValues(idDisciplinaCompetencia, idAtividade);
		delete("atividade_disciplina_competencia");
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaHabilidadeDTO> listarAtividadesDisciplinasHabilidades(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select ah.id_atividade, ah.id_disciplina_habilidade, h.id as id_habilidade, h.codigo, h.descricao, h.bncc, sf.sigla ");
        query.append("from atividade_disciplina_habilidade ah ");
        query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
		query.append("left join habilidade h on (dh.id_habilidade = h.id) ");
		query.append("left join sub_fase sf on (dh.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "ah.id_atividade", filtroAtividade.getId());
        query = orderBy(query, Order.ASC, "h.codigo");
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtividadeDisciplinaHabilidadeDTOMapper());
	}
	
	@Override
	public int contarAtividadesDisciplinasHabilidades(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select count(ah.id_atividade)  ");
		query.append("from atividade_disciplina_habilidade ah ");
        query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
		query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
        query = andEqual(query, "ah.id_atividade", filtroAtividade.getId());
        query = limit(query, filtroAtividade.getQtdTotal(), filtroAtividade.getNumeroPagina());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AtividadeDisciplinaHabilidadeDTOMapper implements RowMapper<AtividadeDisciplinaHabilidadeDTO> {

		@Override
		public AtividadeDisciplinaHabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtividadeDisciplinaHabilidadeDTO.builder()
					.idAtividade(rs.getLong("id_atividade"))
					.idDisciplinaHabilidade(rs.getLong("id_disciplina_habilidade"))
                    .idHabilidade(rs.getLong("id_habilidade"))
                    .codigoHabilidade(rs.getString("codigo"))
                    .descricaoHabilidade(rs.getString("descricao"))
                    .bncc(rs.getBoolean("bncc"))
                    .siglaSubFase(rs.getString("sigla"))
					.build();
		}
	}
	
	@Override
	public boolean verificarSePodeAddAvaliacaoDeDesempenho(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("select  ");
		query.append("(select case when count(*) = 0 then true else false end  from avaliacao where id_tipo_avaliacao = 1 ");
		query = andEqual(query, "id_atividade", filtroAtividade.getId());
		query.append(" )");
		query.append(" and (select case when count(id_disciplina_habilidade) > 0 then true else false end  from atividade_disciplina_habilidade where  1=1 ");
		query = andEqual(query, "id_atividade", filtroAtividade.getId());
		query.append(" )");
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Boolean.class);
	}
	
	
	@Override
	public void criarAtividadeDisciplinaHabilidade(AtividadeDisciplinaHabilidade atividadeDisciplinaHabilidade) {
		addFields("id_disciplina_habilidade", "id_atividade");
		
		addValues(atividadeDisciplinaHabilidade.getIdDisciplinaHabilidade(), atividadeDisciplinaHabilidade.getIdAtividade()); 
		
		insert("atividade_disciplina_habilidade"); 
		
	}
	
	@Override
	public boolean excluirAtividadeDisciplinaHabilidade(Long idDisciplinaHabilidade, Long idAtividade) {
		addFields("id_disciplina_habilidade", "id_atividade");
		addValues(idDisciplinaHabilidade, idAtividade);
		delete("atividade_disciplina_habilidade");
		return true;
	}
	
	@Override
	@CacheEvict(value = {Atividade.CACHE_NAME, Atividade.CACHE_NAME_COUNT}, allEntries=true)
	public boolean excluirDiasNaoLetivos(FiltroAtividade filtroAtividade) {
		StringBuilder query = createQuery("delete from atividade where id in (  ");
		query.append("select a.id ");
        query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
		
		query.append(" and  h.id_turma = ");
		query.append(filtroAtividade.getIdTurma());
		
		query.append(" and f.id_pessoa = ");
		query.append(filtroAtividade.getIdPessoaUsuarioLogado());
		
		query.append(" and h.id_disciplina = ");
		query.append(filtroAtividade.getIdDisciplina());
		
        query.append("and exists ( ");
        query.append("select x.dia_nao_letivo from ( ");
        query.append("select generate_series ( data_hora_inicio::date, data_hora_final::date, '1 day'::interval) as dia_nao_letivo ");
        
        query.append("from evento ");
        query.append("where id in (select id from evento where dia_letivo = false ");
        query.append(" and id_calendario = ");
		query.append(filtroAtividade.getIdCalendario());
        query.append(")) x ");
        query.append("where x.dia_nao_letivo = a.data_prevista )) ");
        this.getNamedParameterJdbcTemplate().getJdbcTemplate().update(query.toString());
        
        return true;
	}
}

