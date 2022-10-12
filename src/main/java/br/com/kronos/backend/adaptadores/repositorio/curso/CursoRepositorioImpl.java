package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.adaptadores.repositorio.comum.ResultadoPesquisaEstrutura;
import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.Curso;
import br.com.kronos.backend.aplicacao.curso.CursoEscala;
import br.com.kronos.backend.aplicacao.curso.CursoRepositorio;
import br.com.kronos.backend.aplicacao.curso.Estrutura;
import br.com.kronos.backend.aplicacao.curso.FiltroCurso;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;
import br.com.kronos.backend.aplicacao.curso.TipoMatrizHorario;
import br.com.kronos.backend.aplicacao.curso.api.CursoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CursoRepositorioImpl extends SqlQueryRepositorio implements CursoRepositorio {
	
	public CursoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Curso curso) {
        addFields("nome", "sigla", "titulo_masculino", "titulo_feminino", 
                  "data_inicio_vigencia", "data_final_vigencia", "data_limite_conclusao",  "tempo_maximo_conclusao", 
                  "tempo_maximo_descontinuo", "carga_horaria", "nota_min_aprov_direta", "ativo", 
                  "id_nivel", "id_tipo_regime_letivo", "id_tipo_turno", "id_instituicao", 
                  "nota_min_aprov_recup", "id_tipo_matriz_horario");
		
		addValues(curso.getNome(), curso.getSigla(), curso.getTituloMasculino(),curso.getTituloFeminino(), 
                  curso.getDataInicioVigencia(), curso.getDataFinalVigencia(), curso.getDataLimiteConclusao(), curso.getTempoMaximoConclusao(),  
                  curso.getTempoMaximoDescontinuo(), curso.getCargaHoraria(), curso.getNotaMinimaAprovacaoDireta(), curso.isAtivo(), 
                  curso.getIdNivel(), curso.getIdTipoRegimeLetivo(), curso.getIdTipoTurno(), curso.getIdInstituicao(), 
                  curso.getNotaMinimaAprovacaoRecuperacao(), curso.getIdTipoMatrizHorario());
		
		return (long) insertAuto("curso"); 
    }

	
	@Override
	public Long alterar(Curso curso) {
		addFields("nome", "sigla", "titulo_masculino", "titulo_feminino", 
                  "data_inicio_vigencia", "data_final_vigencia", "data_limite_conclusao",  "tempo_maximo_conclusao", 
                  "tempo_maximo_descontinuo", "carga_horaria", "nota_min_aprov_direta", "ativo", 
                  "id_nivel", "id_tipo_regime_letivo", "id_tipo_turno", "id_instituicao", 
                  "nota_min_aprov_recup", "id_tipo_matriz_horario");
		
		addValues(curso.getNome(), curso.getSigla(), curso.getTituloMasculino(),curso.getTituloFeminino(), 
                  curso.getDataInicioVigencia(), curso.getDataFinalVigencia(), curso.getDataLimiteConclusao(), curso.getTempoMaximoConclusao(),  
                  curso.getTempoMaximoDescontinuo(), curso.getCargaHoraria(), curso.getNotaMinimaAprovacaoDireta(), curso.isAtivo(), 
                  curso.getIdNivel(), curso.getIdTipoRegimeLetivo(), curso.getIdTipoTurno(), curso.getIdInstituicao(), 
                  curso.getNotaMinimaAprovacaoRecuperacao(), curso.getIdTipoMatrizHorario(), curso.getId());
		
		return (long) update("curso", curso.getId());
	}
	
	@Override
	public CursoDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.id, c.nome, c.sigla, c.titulo_masculino, c.titulo_feminino, ");
            query.append("c.data_inicio_vigencia, c.data_final_vigencia, c.data_limite_conclusao,  c.tempo_maximo_conclusao, ");
            query.append("c.tempo_maximo_descontinuo, c.carga_horaria, c.nota_min_aprov_direta, c.ativo, ");
            query.append("c.id_nivel, c.id_tipo_regime_letivo, c.id_tipo_turno, c.id_instituicao, ");
            query.append("c.nota_min_aprov_recup, c.id_tipo_matriz_horario, m.nome as nome_tipo_matriz_horario ");
            query.append("from curso c ");
            query.append("left join tipo_turno t on (c.id_tipo_turno = t.id) ");
            query.append("left join tipo_regime_letivo r on (c.id_tipo_regime_letivo = r.id) ");
            query.append("left join tipo_matriz_horario m on (c.id_tipo_matriz_horario = m.id) where 1=1 ");
            query = andEqual(query, "c.id", id);
            CursoDTO curso = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CursoDTOMapper());
			return curso;
		} catch (EmptyResultDataAccessException e) {
			log.info("curso não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<TipoMatrizHorario> listarTipoMatrizHorario() {
		StringBuilder query = createQuery("select t.id, t.nome from tipo_matriz_horario t ");
        query = orderBy(query, Order.ASC, "t.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TipoMatrizHorarioMapper());
	}
	
	@Override
	public List<CursoResumoDTO> listarParaModulosDosProfessores(Long idInstituicao, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct c.id, c.id_nivel, c.nome ");
        query.append("from curso c ");
        query.append("left join periodo p on (c.id = p.id_curso) ");
        query.append("left join disciplina d on (p.id = d.id_periodo) ");
        query.append("left join horario h on (d.id = h.id_disciplina) ");
        query.append("left join funcionario f on (h.id_funcionario = f.id) ");
        query.append("left join turma t on (h.id_turma = t.id) where t.encerrada = false ");
		query = andEqual(query, "c.id_instituicao", idInstituicao);
		query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
        query = orderBy(query, Order.ASC, "c.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoResumoDTOParaDiarioMapper());
	}
	
	
	@Override
	public List<CursoDTO> listarParaCombo(FiltroCurso filtroCurso) {
		return this.listar(filtroCurso);
	}
	
	@Override
	public List<CursoResumoDTO> listarParaHorario(Integer ano) {
		StringBuilder query = createQuery("select distinct c.id, c.nome, c.id_tipo_matriz_horario ");
        query.append("from periodo_execucao pe ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join curso c on (p.id_curso = c.id) where 1=1 ");
		query = andEqual(query, "pe.ano", ano);
        query = orderBy(query, Order.ASC, "c.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoResumoDTOMapper());
	}
	
	@Override
	public List<CursoDTO> listar(FiltroCurso filtroCurso) {
		StringBuilder query = createQuery("select c.id, c.nome, c.sigla, c.titulo_masculino, c.titulo_feminino, ");
        query.append("c.data_inicio_vigencia, c.data_final_vigencia, c.data_limite_conclusao,  c.tempo_maximo_conclusao, ");
        query.append("c.tempo_maximo_descontinuo, c.carga_horaria, c.nota_min_aprov_direta, c.ativo, ");
        query.append("c.id_nivel, c.id_tipo_regime_letivo, c.id_tipo_turno, c.id_instituicao, ");
        query.append("c.nota_min_aprov_recup, c.id_tipo_matriz_horario, m.nome as nome_tipo_matriz_horario ");
        query.append("from curso c ");
        query.append("left join tipo_turno t on (c.id_tipo_turno = t.id) ");
        query.append("left join tipo_regime_letivo r on (c.id_tipo_regime_letivo = r.id) ");
        query.append("left join tipo_matriz_horario m on (c.id_tipo_matriz_horario = m.id) where 1=1 ");
		query = andEqual(query, "c.id", filtroCurso.getId());
		query = andLike(query, "c.nome", filtroCurso.getNome());
        query = andEqual(query, "c.id_nivel", filtroCurso.getIdNivel());
        query = andEqual(query, "c.id_tipo_turno", filtroCurso.getIdTipoTurno()); 
        query = andEqual(query, "c.id_instituicao", filtroCurso.getIdInstituicao());
        query = orderBy(query, Order.ASC, "c.nome");
		query = limit(query, filtroCurso.getQtdTotal(), filtroCurso.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoDTOMapper());
	}
	
	@Override
	public int contar(FiltroCurso filtroCurso) {
		StringBuilder query = createQuery("select count(c.id) ");
		query.append("from curso c ");
        query.append("left join tipo_turno t on (c.id_tipo_turno = t.id) ");
        query.append("left join tipo_regime_letivo r on (c.id_tipo_regime_letivo = r.id) ");
        query.append("left join tipo_matriz_horario m on (c.id_tipo_matriz_horario = m.id) where 1=1 ");
		query = andEqual(query, "c.id", filtroCurso.getId());
		query = andLike(query, "c.nome", filtroCurso.getNome());
        query = andEqual(query, "c.id_nivel", filtroCurso.getIdNivel());
        query = andEqual(query, "c.id_tipo_turno", filtroCurso.getIdTipoTurno()); 
        query = andEqual(query, "c.id_instituicao", filtroCurso.getIdInstituicao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("curso", id);
		return true;
	}
	
	@Override
	public void vincularCursoEscala(CursoEscala cursoEscala) {
		addFields("id_curso", "id_escala");
		
		addValues(cursoEscala.getIdCurso(), cursoEscala.getIdEscala()); 
		
		insert("curso_escala"); 
	}

	@Override
	public void desvincularCursoEscala(CursoEscala cursoEscala) {
		addFields("id_curso", "id_escala");
		
		addValues(cursoEscala.getIdCurso(), cursoEscala.getIdEscala());
		
		delete("curso_escala");
	}
	
	private class CursoDTOMapper implements RowMapper<CursoDTO> {

		@Override
		public CursoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
					.tituloMasculino(rs.getString("titulo_masculino"))
                    .tituloFeminino(rs.getString("titulo_feminino"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .dataLimiteConclusao(rs.getDate("data_limite_conclusao") != null ? rs.getDate("data_limite_conclusao").toLocalDate() : null)
                    .tempoMaximoConclusao(rs.getInt("tempo_maximo_conclusao"))
                    .tempoMaximoDescontinuo(rs.getInt("tempo_maximo_descontinuo"))
                    .cargaHoraria(rs.getInt("carga_horaria"))
                    .notaMinimaAprovacaoDireta(rs.getDouble("nota_min_aprov_direta"))
                    .ativo(rs.getBoolean("ativo"))
					.idNivel(rs.getInt("id_nivel"))
                    .idTipoRegimeLetivo(rs.getInt("id_tipo_regime_letivo"))
                    .idTipoTurno(rs.getInt("id_tipo_turno"))
					.idInstituicao(rs.getInt("id_instituicao"))
                    .notaMinimaAprovacaoRecuperacao(rs.getDouble("nota_min_aprov_recup"))
                    .idTipoMatrizHorario(rs.getInt("id_tipo_matriz_horario"))
                    .nomeTipoMatrizHorario(rs.getString("nome_tipo_matriz_horario"))
					.build();
		}

	}
	
	private class TipoMatrizHorarioMapper implements RowMapper<TipoMatrizHorario> {

		@Override
		public TipoMatrizHorario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TipoMatrizHorario.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
					.build();
		}

	}
	
	private class CursoResumoDTOParaDiarioMapper implements RowMapper<CursoResumoDTO> {

		@Override
		public CursoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoResumoDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idNivel(rs.getLong("id_nivel"))
					.build();
		}

	}
	
	private class CursoResumoDTOMapper implements RowMapper<CursoResumoDTO> {

		@Override
		public CursoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoResumoDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idTipoMatrizHorario(rs.getInt("id_tipo_matriz_horario"))
					.build();
		}

	}

	@Override
	public boolean verificarVinculoCursoEscala(FiltroCursoEscala filtro) {
		StringBuilder query = createQuery("select count(c.id_escala) from curso_escala c where 1=1 ");
		query = andEqual(query, "c.id_curso", filtro.getIdCurso());
		query = andEqual(query, "c.id_escala", filtro.getIdEscala());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class) > 0;
	}
	
	@Override
	public List<Estrutura> visualizarEstrutura(long idCurso) {
		StringBuilder query = createQuery("select p.id as id_periodo, p.nome as nome_periodo, p.numero as numeroPeriodo, ");
		query.append("f.id as id_fase, f.nome as nome_fase, f.numero as numeroFase,  ");
		query.append("sf.id as id_sub_fase, sf.nome as nome_sub_fase, sf.numero as numeroSubFase from periodo p ");
		query.append("left join fase f on (p.id =  f.id_periodo) ");
		query.append("left join sub_fase sf on (f.id =  sf.id_fase) ");
		query.append("where 1=1");
		query = andEqual(query, "p.id_curso", idCurso);
		query = orderBy(query, Order.ASC, "p.numero, f.numero, sf.numero");
		List<ResultadoPesquisaEstrutura> resultados = this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResultadoPesquisaEstruturaMapper());
		return criarEstrutura(resultados);
	}

	private List<Estrutura> criarEstrutura(List<ResultadoPesquisaEstrutura> resultados) {
		List<Estrutura> estruturas = new ArrayList<>();
		resultados.forEach(r -> {
			Estrutura estrutura = estruturas.stream().filter(e -> e.getId().equals(r.getIdPeriodo())).findAny()
										.orElse(null);
			if(estrutura == null) {
				estrutura = Estrutura.comIdENome(r.getIdPeriodo(), r.getNomePeriodo());
				estruturas.add(estrutura);
			}
			criarEstrutura(estrutura, estrutura.getId(), estrutura.getNome(), r);
		});
		return estruturas;
	}
	
	private Estrutura criarEstrutura(Estrutura estrutura, Long id, String nome, ResultadoPesquisaEstrutura resultado) {
		if(resultado.getIdPeriodo() != null &&  resultado.getNomePeriodo() != null && resultado.getIdPeriodo().equals(id) && resultado.getNomePeriodo().equals(nome)) {
			Estrutura estruturaFase = adicionarFilho(estrutura, resultado.getIdFase(), resultado.getNomeFase());
			if(estruturaFase != null) {
				criarEstrutura(estruturaFase, estruturaFase.getId(), estruturaFase.getNome(), resultado);
			}
		} 
		if(resultado.getIdFase() !=null && resultado.getNomeFase() !=null && resultado.getIdFase().equals(id) && resultado.getNomeFase().equals(nome)) {
			adicionarFilho(estrutura, resultado.getIdSubFase(), resultado.getNomeSubFase());
		}
		return estrutura;
	}

	private Estrutura adicionarFilho(Estrutura estrutura, Long idFilho, String nomeFilho) {
		Estrutura estruturaFase = estrutura.getFilhos().stream().filter(e -> e.getId().equals(idFilho)).findAny()
				.orElse(null);
		if(estruturaFase == null && idFilho != null && !StringUtils.isEmpty(nomeFilho)) {
			estruturaFase = Estrutura.comIdENome(idFilho, nomeFilho);
			estrutura.adicionarFilho(estruturaFase);
		}
		return estruturaFase;
	}
	
	private class ResultadoPesquisaEstruturaMapper implements RowMapper<ResultadoPesquisaEstrutura> {

		@Override
		public ResultadoPesquisaEstrutura mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoPesquisaEstrutura.builder()
                    .idPeriodo(rs.getLong("id_periodo") > 0 ? rs.getLong("id_periodo") : null)
                    .nomePeriodo(rs.getString("nome_periodo"))
                    .idFase(rs.getLong("id_fase") > 0 ? rs.getLong("id_fase") : null)
                    .nomeFase(rs.getString("nome_fase"))
                    .idSubFase(rs.getLong("id_sub_fase") > 0 ? rs.getLong("id_sub_fase") : null)
                    .nomeSubFase(rs.getString("nome_sub_fase"))
                    .build();
		}
	}

}
