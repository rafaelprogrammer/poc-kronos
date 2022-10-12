package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.EnumTipoSituacaoMatricula;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatricula;
import br.com.kronos.backend.aplicacao.matricula.Matricula;
import br.com.kronos.backend.aplicacao.matricula.MatriculaRepositorio;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaCanceladaTransferidaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaContratoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaOcorrenciaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatriculaRepositorioImpl extends SqlQueryRepositorio implements MatriculaRepositorio {
	
	public MatriculaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Matricula matricula) {
        addFields("data", "ano_inicio_curso", "semestre_inicio_curso", "data_inicio_curso", 
                  "ano_conclusao_curso", "semestre_conclusao_curso", "data_conclusao_curso",  "data_colacao_grau", 
                  "id_pessoa", "id_curso", "id_tipo_situacao_matricula", "id_tipo_resultado", 
                  "id_empresa_origem");
		
		addValues(matricula.getData(), matricula.getAnoInicioCurso(), matricula.getSemestreInicioCurso(),matricula.getDataInicioCurso(), 
                  matricula.getAnoConclusaoCurso(), matricula.getSemestreConclusaoCurso(), matricula.getDataConclusaoCurso(), matricula.getDataColacaoGrau(),  
                  matricula.getIdPessoa(), matricula.getIdCurso(), matricula.getIdTipoSituacaoMatricula(), matricula.getIdTipoResultado(), 
                  matricula.getIdEmpresaOrigem()); 
		
		return (long) insertAuto("matricula"); 
    }

	@Override
	public Long alterar(Matricula matricula) {
		addFields("data", "ano_inicio_curso", "semestre_inicio_curso", "data_inicio_curso", 
                "ano_conclusao_curso", "semestre_conclusao_curso", "data_conclusao_curso",  "data_colacao_grau", 
                "id_pessoa", "id_curso", "id_tipo_situacao_matricula", "id_tipo_resultado", 
                "id_empresa_origem");
		
		addValues(matricula.getData(), matricula.getAnoInicioCurso(), matricula.getSemestreInicioCurso(),matricula.getDataInicioCurso(), 
                matricula.getAnoConclusaoCurso(), matricula.getSemestreConclusaoCurso(), matricula.getDataConclusaoCurso(), matricula.getDataColacaoGrau(),  
                matricula.getIdPessoa(), matricula.getIdCurso(), matricula.getIdTipoSituacaoMatricula(), matricula.getIdTipoResultado(), 
                matricula.getIdEmpresaOrigem(),matricula.getId());
		
		return (long) update("matricula", matricula.getId());
	}
	
	@Override
	public boolean verificarSeExisteMatricula(Long idPessoa, Long idCurso) {
        StringBuilder query = createQuery("select count(m.id) from matricula m where 1=1 ");
        query = andEqual(query, "m.id_pessoa", idPessoa);
        query = andEqual(query, "m.id_curso", idCurso);
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class) > 0;
	} 
	
	@Override
	public MatriculaDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select m.id, m.data, m.ano_inicio_curso, m.semestre_inicio_curso, m.data_inicio_curso, ");
            query.append("m.ano_conclusao_curso, m.semestre_conclusao_curso, m.data_conclusao_curso,  m.data_colacao_grau,   ");
            query.append("m.id_pessoa, m.id_curso, m.id_tipo_situacao_matricula, m.id_tipo_resultado, "); 
            query.append("m.id_empresa_origem, e.nome_fantasia as nome_instituicao_origem, "); 
            query.append("p.nome as nome_pessoa, p.cpf as cpf_pessoa, p.numero_registro as numero_registro_pessoa, ");
            query.append("p.data_nascimento as data_nascimento_pessoa, p.id_genero as id_genero_pessoa, p.id_arq_anexo as id_arq_anexo_pessoa, c.nome as nome_curso ");
            query.append("from matricula m ");
            query.append("join pessoa p on (m.id_pessoa = p.id) ");
            query.append("join curso c on (m.id_curso = c.id) ");
            query.append("left join empresa e on (m.id_empresa_origem = e.id) where 1=1 ");
            query = andEqual(query, "m.id", id);
            MatriculaDTO matricula = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new MatriculaDTOMapper());
			return matricula;
		} catch (EmptyResultDataAccessException e) {
			log.info("matricula não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public MatriculaCanceladaTransferidaDTO buscarPorIdParaCancelamentoOuTransferencia(Long id) {
		try {
            StringBuilder query = createQuery("select p.id as id_pessoa, m.id, p.numero_registro, p.cpf, p.nome, c.id as id_contrato, c.numero as numero_contrato, ");
            query.append("cs.nome as curso, pd.nome as periodo, cl.data_final_ano_letivo, c.data as data_contrato, c.ano as ano_contrato ");
            query.append("from contrato c  "); 
            query.append("left join matricula m on (c.id_matricula = m.id) "); 
            query.append("left join pessoa p on (m.id_pessoa = p.id) ");
            query.append("left join periodo_execucao pe on (c.id_periodo_execucao = pe.id) ");
            query.append("left join periodo pd on (pe.id_periodo = pd.id) ");
            query.append("left join curso cs on (pd.id_curso = cs.id) ");
            query.append("left join calendario cl on (pe.id_calendario = cl.id) ");
            query.append("where c.id = (select max(co.id) from contrato co where 1=1 ");
            query = andEqual(query, "co.id_matricula", id);
            query.append(") ");
            
            return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new MatriculaCanceladaTransferidaDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("matricula não existe - " + id);
			return null;
		}
	}
	
	private class MatriculaCanceladaTransferidaDTOMapper implements RowMapper<MatriculaCanceladaTransferidaDTO> {

		@Override
		public MatriculaCanceladaTransferidaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaCanceladaTransferidaDTO.builder()
					.id(rs.getLong("id"))
					.idPessoa(rs.getLong("id_pessoa"))
                    .nomePessoa(rs.getString("nome"))
                    .cpfPessoa(rs.getString("cpf"))
                    .numeroRegistroPessoa(rs.getInt("numero_registro"))
                    .idContrato(rs.getLong("id_contrato"))
                    .numeroContrato(rs.getLong("numero_contrato"))
                    .nomeCurso(rs.getString("curso"))
                    .nomePeriodo(rs.getString("periodo"))
                    .dataFinalAnoLetivo(rs.getDate("data_final_ano_letivo") != null ? rs.getDate("data_final_ano_letivo").toLocalDate() : null)
                    .dataContrato(rs.getDate("data_contrato") != null ? rs.getDate("data_contrato").toLocalDate() : null)
                    .anoContrato(rs.getInt("ano_contrato"))
					.build();
		}

	}
	
	@Override
	public List<MatriculaDTO> listar(FiltroMatricula filtroMatricula) {
		StringBuilder query = createQuery("select m.id, m.data, m.ano_inicio_curso, m.semestre_inicio_curso, m.data_inicio_curso, ");
        query.append("m.ano_conclusao_curso, m.semestre_conclusao_curso, m.data_conclusao_curso,  m.data_colacao_grau,   ");
        query.append("m.id_pessoa, m.id_curso, m.id_tipo_situacao_matricula, m.id_tipo_resultado, "); 
        query.append("m.id_empresa_origem, e.nome_fantasia as nome_instituicao_origem, ");
        query.append("p.nome as nome_pessoa, p.cpf as cpf_pessoa, p.numero_registro as numero_registro_pessoa, ");
        query.append("p.data_nascimento as data_nascimento_pessoa, p.id_genero as id_genero_pessoa, p.id_arq_anexo as id_arq_anexo_pessoa, c.nome as nome_curso ");
        query.append("from matricula m ");
        query.append("join pessoa p on (m.id_pessoa = p.id) ");
        query.append("join curso c on (m.id_curso = c.id) ");
        query.append("left join empresa e on (m.id_empresa_origem = e.id) where 1=1");
		query = andEqual(query, "m.id", filtroMatricula.getId());
		query = andEqual(query, "m.data", filtroMatricula.getData());
        query = andEqual(query, "m.ano_inicio_curso", filtroMatricula.getAnoInicioCurso());
        query = andEqual(query, "m.semestre_inicio_curso", filtroMatricula.getSemestreInicioCurso()); 
        query = andEqual(query, "m.ano_conclusao_curso", filtroMatricula.getAnoConclusaoCurso());
        query = andEqual(query, "m.semestre_conclusao_curso", filtroMatricula.getSemestreConclusaoCurso());
        query = andEqual(query, "m.id_pessoa", filtroMatricula.getIdPessoa()); 
        query = andEqual(query, "m.id_curso", filtroMatricula.getIdCurso());  
        query = andEqual(query, "m.id_tipo_situacao_matricula", filtroMatricula.getIdTipoSituacaoMatricula());
        query = andEqual(query, "m.id_tipo_resultado", filtroMatricula.getIdTipoResultado()); 
        query = andEqual(query, "m.id_empresa_origem", filtroMatricula.getIdEmpresaOrigem());
        query = andLike(query, "p.nome", filtroMatricula.getNomePessoa());
        query = andEqual(query, "p.cpf", filtroMatricula.getCpfPessoa());
        query = andEqual(query, "p.numero_registro", filtroMatricula.getNumeroRegistroPessoa());
        query = orderBy(query, Order.ASC, "ano_inicio_curso");
		query = limit(query, filtroMatricula.getQtdTotal(), filtroMatricula.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MatriculaDTOMapper());
	}
	
	@Override
	public int contar(FiltroMatricula filtroMatricula) {
		StringBuilder query = createQuery("select count(m.id) from matricula m ");
        query.append("join pessoa p on (m.id_pessoa = p.id) ");
        query.append("join curso c on (m.id_curso = c.id) ");
        query.append("left join empresa e on (m.id_empresa_origem = e.id) where 1=1 ");
		query = andEqual(query, "m.id", filtroMatricula.getId());
		query = andEqual(query, "m.data", filtroMatricula.getData());
        query = andEqual(query, "m.ano_inicio_curso", filtroMatricula.getAnoInicioCurso());
        query = andEqual(query, "m.semestre_inicio_curso", filtroMatricula.getSemestreInicioCurso()); 
        query = andEqual(query, "m.ano_conclusao_curso", filtroMatricula.getAnoConclusaoCurso());
        query = andEqual(query, "m.semestre_conclusao_curso", filtroMatricula.getSemestreConclusaoCurso());
        query = andEqual(query, "m.id_pessoa", filtroMatricula.getIdPessoa()); 
        query = andEqual(query, "m.id_curso", filtroMatricula.getIdCurso());  
        query = andEqual(query, "m.id_tipo_situacao_matricula", filtroMatricula.getIdTipoSituacaoMatricula());
        query = andEqual(query, "m.id_tipo_resultado", filtroMatricula.getIdTipoResultado()); 
        query = andEqual(query, "m.id_empresa_origem", filtroMatricula.getIdEmpresaOrigem());
        query = andLike(query, "p.nome", filtroMatricula.getNomePessoa());
        query = andEqual(query, "p.cpf", filtroMatricula.getCpfPessoa());
        query = andEqual(query, "p.numero_registro", filtroMatricula.getNumeroRegistroPessoa());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("matricula", id);
		return true;
	}
	
	private class MatriculaDTOMapper implements RowMapper<MatriculaDTO> {

		@Override
		public MatriculaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaDTO.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.anoInicioCurso(rs.getInt("ano_inicio_curso"))
                    .semestreInicioCurso(rs.getInt("semestre_inicio_curso"))
                    .dataInicioCurso(rs.getDate("data_inicio_curso").toLocalDate())
                    .anoConclusaoCurso(rs.getObject("ano_conclusao_curso") != null ? rs.getInt("ano_conclusao_curso") : null)
                    .semestreConclusaoCurso(rs.getObject("semestre_conclusao_curso") != null ? rs.getInt("semestre_conclusao_curso") : null)
                    .dataConclusaoCurso(rs.getDate("data_conclusao_curso") != null ? rs.getDate("data_conclusao_curso").toLocalDate() : null)
                    .dataColacaoGrau(rs.getDate("data_colacao_grau") !=null ? rs.getDate("data_colacao_grau").toLocalDate() : null)
                    .idPessoa(rs.getLong("id_pessoa"))
                    .nomePessoa(rs.getString("nome_pessoa"))
                    .cpfPessoa(rs.getString("cpf_pessoa"))
                    .numeroRegistroPessoa(rs.getInt("numero_registro_pessoa"))
                    .dataNascimentoPessoa(rs.getDate("data_nascimento_pessoa").toLocalDate())
                    .idGeneroPessoa(rs.getInt("id_genero_pessoa"))
                    .idArqAnexoPessoa(rs.getInt("id_arq_anexo_pessoa"))
                    .idCurso(rs.getLong("id_curso"))
                    .nomeCurso(rs.getString("nome_curso"))
                    .idTipoSituacaoMatricula(rs.getInt("id_tipo_situacao_matricula"))
                    .idTipoResultado(rs.getObject("id_tipo_resultado") != null ? rs.getInt("id_tipo_resultado") : null)
                    .idEmpresaOrigem(rs.getObject("id_empresa_origem") != null ? rs.getLong("id_empresa_origem") : null)
                    .nomeInstituicaoOrigem(rs.getString("nome_instituicao_origem"))
					.build();
		}

	}

	@Override
	public void atualizarTipoSituacao(Long id, EnumTipoSituacaoMatricula tipoSituacaoMatricula) {
		addFields("id_tipo_situacao_matricula");
		
		addValues(tipoSituacaoMatricula.id(), id);
		
		update("matricula", id);
	}
	
	@Override
	public List<MatriculaContratoDTO> listarPendentesContrato(Long idInstituicao, Integer... idsTiposSituacoesContratos) {
		StringBuilder query = createQuery("select distinct m.id, m.id, p.numero_registro, p.cpf as cpf_pessoa, p.nome as nome_pessoa  ");
        query.append("from matricula m   ");
        query.append("left join contrato c on (m.id = c.id_matricula) "); 
        query.append("left join tipo_situacao_contrato tc on (c.id_tipo_situacao_contrato = tc.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "c.id_instituicao", idInstituicao);
		query = andIn(query, "c.id_tipo_situacao_contrato", idsTiposSituacoesContratos);
		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MatriculaContratoDTOMapper());
	}
	
	private class MatriculaContratoDTOMapper implements RowMapper<MatriculaContratoDTO> {

		@Override
		public MatriculaContratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaContratoDTO.builder()
					.id(rs.getLong("id"))
					.numeroRegistroPessoa(rs.getInt("numero_registro"))
					.nomePessoa(rs.getString("nome_pessoa"))
					.cpfPessoa(rs.getString("cpf_pessoa"))
					.build();
		}

	}
	
	@Override
	public MatriculaOcorrenciaDTO buscarMatriculaParaOcorrencia(Long idPessoaSelecionada) {
		try {
			StringBuilder query = createQuery("select m.id, e.nome_fantasia as instituicao_origem, c.nome as curso_atual, m.data as data_matricula  ");
	        query.append("from matricula m   ");
	        query.append("left join curso c on (m.id_curso = c.id) "); 
	        query.append("left join empresa e on (m.id_empresa_origem = e.id) where 1=1 ");
	        query = andEqual(query, "m.id_pessoa", idPessoaSelecionada);
	        query.append(" and m.data = (select max(ma.data) from matricula ma where 1=1 ");
	        query = andEqual(query, "ma.id_pessoa", idPessoaSelecionada);
	        query.append(" )");
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), new MatriculaOcorrenciaDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("matricula não existe para a pessoa - " + idPessoaSelecionada);
			return null;
		}
	}
	
	private class MatriculaOcorrenciaDTOMapper implements RowMapper<MatriculaOcorrenciaDTO> {

		@Override
		public MatriculaOcorrenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaOcorrenciaDTO.builder()
					.idMatricula(rs.getLong("id"))
					.dataMatricula(rs.getDate("data_matricula").toLocalDate())
					.instituicaoOrigem(rs.getString("instituicao_origem"))
					.cursoAtual(rs.getString("curso_atual"))
					.build();
		}

	}
	
	@Override
	public List<MatriculaOcorrenciaDTO> listarMatriculaParaOcorrenciaCombo(Long idPessoaSelecionada) {
		StringBuilder query = createQuery("select m.id, c.nome as curso_atual  ");
        query.append("from matricula m   ");
        query.append("left join curso c on (m.id_curso = c.id) where 1=1 "); 
		query = andEqual(query, "m.id_pessoa", idPessoaSelecionada);
		query.append(" and m.data_conclusao_curso is null ");
		query = orderBy(query, Order.ASC, "c.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MatriculaOcorrenciaDTOParaComboMapper());
	}
	
	private class MatriculaOcorrenciaDTOParaComboMapper implements RowMapper<MatriculaOcorrenciaDTO> {

		@Override
		public MatriculaOcorrenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaOcorrenciaDTO.builder()
					.idMatricula(rs.getLong("id"))
					.cursoAtual(rs.getString("curso_atual"))
					.build();
		}

	}
	
}
