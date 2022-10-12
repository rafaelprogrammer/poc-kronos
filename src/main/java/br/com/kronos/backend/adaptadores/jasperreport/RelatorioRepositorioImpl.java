package br.com.kronos.backend.adaptadores.jasperreport;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.comum.EnumRelatorios;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.comum.RelatorioRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class RelatorioRepositorioImpl extends SqlQueryRepositorio implements RelatorioRepositorio {
	
	private DataSource ds;
	
	public RelatorioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
		this.ds = dataSource;
	}
	
	
	@Override
	public List<Integer> listarAnos() {
		StringBuilder query = createQuery("select distinct c.ano from contrato c ");
		query = orderBy(query, Order.DESC, "c.ano");
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<CursoResumoDTO> listarCursos(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select distinct c.id, c.id_nivel, c.sigla, c.nome from curso c ");
		query.append("left join matricula m on (c.id = m.id_curso) ");
		query.append("left join contrato ct on (m.id = ct.id_matricula) where 1=1 ");
		query = andEqual(query, "ct.ano", filtro.getAno());
		query = orderBy(query, Order.ASC, "c.id_nivel, c.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoResumoDTOMapper());
	}
	
	@Override
	public List<PeriodoExecucaoResumoDTO> listarPeriodos(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select pe.id, p.sigla ");
		query.append("from periodo_execucao pe ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", filtro.getIdCurso());
		query = andEqual(query, "pe.ano", filtro.getAno());
		query = orderBy(query, Order.ASC, "p.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoExecucaoResumoDTOMapper());
	}
	
	@Override
	public List<TurmaResumoDTO> listarTurmas(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select t.id, t.sigla||'/'||tt.sigla as sigla, tt.sigla as sigla_turno ");
		query.append("from turma t ");
		query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
		query = andEqual(query, "t.id_periodo_execucao", filtro.getIdPeriodoExecucao());
		query = orderBy(query, Order.ASC, "t.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOMapper());
	}
	
	private class TurmaResumoDTOMapper implements RowMapper<TurmaResumoDTO> {

		@Override
		public TurmaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaResumoDTO.builder()
					.id(rs.getLong("id"))
					.sigla(rs.getString("sigla"))
					.siglaTurno(rs.getString("sigla_turno"))
					.build();
		}

	}
	
	private class PeriodoExecucaoResumoDTOMapper implements RowMapper<PeriodoExecucaoResumoDTO> {

		@Override
		public PeriodoExecucaoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoExecucaoResumoDTO.builder()
					.id(rs.getInt("id"))
					.sigla(rs.getString("sigla"))
					.build();
		}

	}
	
	private class CursoResumoDTOMapper implements RowMapper<CursoResumoDTO> {

		@Override
		public CursoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoResumoDTO.builder()
					.id(rs.getInt("id"))
					.idNivel(rs.getLong("id_nivel"))
					.sigla(rs.getString("sigla"))
					.nome(rs.getString("nome"))
					.build();
		}

	}
	
	
	@Override
	public byte[] exportarParaPdf(EnumRelatorios relatorio, Map<String, Object> parameters) {
		try {
			parameters.put("LOGO", getClass().getResourceAsStream("/".concat("Logo_Escola_Fatima.png")));
			parameters.put("DATA_HORA", DateUtil.dataHoraAtualFormatada());
			return JasperExportManager.exportReportToPdf(preencher(compilar(relatorio.arquivo()), parameters));
		} catch (JRException | SQLException e) {
			 throw new RuntimeException("Erro ao exportar o relatorio - " + relatorio.nome(), e);
		}
	}
	
	@Override
	public byte[] exportarParaXLSX(EnumRelatorios relatorio, Map<String, Object> parameters) {
		try {
			parameters.put("LOGO", getClass().getResourceAsStream("/".concat("Logo_Escola_Fatima.png")));
			parameters.put("DATA_HORA", DateUtil.dataHoraAtualFormatada());
			
			JRXlsxExporter xlsxExporter = new JRXlsxExporter();
			 ByteArrayOutputStream os = new ByteArrayOutputStream();
			 
			 xlsxExporter.setExporterInput(new SimpleExporterInput(preencher(compilar(relatorio.arquivo()), parameters)));
			 xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
			 
			 
			 
			 xlsxExporter.exportReport();
			 
			 return os.toByteArray();
			
		} catch (JRException | SQLException e) {
			 throw new RuntimeException("Erro ao exportar o relatorio - " + relatorio.nome(), e);
		}
	}
	
	public JasperReport compilar(String arquivo) throws JRException {
            InputStream reportStream = getClass().getResourceAsStream("/".concat(arquivo));
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRSaver.saveObject(jasperReport, arquivo.replace(".jrxml", ".jasper"));
            return jasperReport;
    }

    public JasperPrint preencher(JasperReport jasperReport, Map<String, Object> parameters) throws JRException, SQLException {
    	try (Connection connection = ds.getConnection()){
    		return JasperFillManager.fillReport(jasperReport, parameters, connection);
    	} catch (Exception e) {
    		throw new RuntimeException("Erro ao tentar preencher o relatorio", e);
		}
    }

}
