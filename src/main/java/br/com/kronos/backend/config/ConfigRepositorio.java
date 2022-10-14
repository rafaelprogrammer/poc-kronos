package br.com.kronos.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.kronos.backend.adaptadores.jasperreport.RelatorioRepositorioImpl;
import br.com.kronos.backend.adaptadores.jasperreport.RelatoriosAlunosRepositorioImpl;
import br.com.kronos.backend.adaptadores.jasperreport.RelatoriosPedagogicosRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.UsuarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.arquivo.ArquivoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.atestado.AtestadoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.avaliacao.AvaliacaoHabilidadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.avaliacao.AvaliacaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.avaliacao.GrupoAvaliacaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.AtitudeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.CampoExperienciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.CompetenciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.ComponenteRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.DireitoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.FaixaAnoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.HabilidadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.NivelRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.ObjetivoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.basecurricular.ValorRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.CalendarioCursoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.CalendarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.EventoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.FaseExecucaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.PeriodoExecucaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.calendario.SubFaseExecucaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.comum.CidadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.comum.EstadoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.comum.PaisRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.contrato.ContratoConvenioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.contrato.ContratoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.contrato.ParcelaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.CursoDocumentoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.CursoFuncionarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.CursoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.FaseRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.PeriodoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.PortariaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.curso.SubFaseRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.diario.AtividadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.diario.DiarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.disciplina.DisciplinaCompetenciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.disciplina.DisciplinaDireitoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.disciplina.DisciplinaHabilidadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.disciplina.DisciplinaObjetivoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.disciplina.DisciplinaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.frequencia.FrequenciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.funcionario.ConfiguracaoPontoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.funcionario.FuncionarioFuncaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.funcionario.FuncionarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.funcionario.PontoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.horario.ConsultasHorasDiaDaSemanaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.horario.HorarioHoraAtividadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.horario.HorarioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.horario.SubstitutoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.instituicao.ConvenioRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.instituicao.EscalaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.instituicao.InstituicaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.instituicao.MencaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.matricula.CreditoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.matricula.TurmaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.ocorrencia.OcorrenciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.ocorrencia.TipoOcorrenciaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.DocumentoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.EnderecoPessoaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.FiliacaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.PessoaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.TalentoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.TelefonePessoaRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.pessoa.TitulacaoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.resultado.AvaliadoRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.resultado.ResultadoHabilidadeRepositorioImpl;
import br.com.kronos.backend.adaptadores.repositorio.resultado.ResultadoSubFaseRepositorioImpl;
import br.com.kronos.backend.aplicacao.UsuarioRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.atestado.AtestadoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.AtitudeRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.CampoExperienciaRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.CompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.ComponenteRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.DireitoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FaixaAnoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.HabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.NivelRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.ObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.ValorRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCursoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.comum.CidadeRepositorio;
import br.com.kronos.backend.aplicacao.comum.EstadoRepositorio;
import br.com.kronos.backend.aplicacao.comum.PaisRepositorio;
import br.com.kronos.backend.aplicacao.comum.RelatorioRepositorio;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenioRepositorio;
import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.ParcelaRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoDocumentoRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import br.com.kronos.backend.aplicacao.curso.PortariaRepositorio;
import br.com.kronos.backend.aplicacao.curso.SubFaseRepositorio;
import br.com.kronos.backend.aplicacao.diario.AtividadeRepositorio;
import br.com.kronos.backend.aplicacao.diario.DiarioRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireitoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaRepositorio;
import br.com.kronos.backend.aplicacao.frequencia.FrequenciaRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncaoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.PontoRepositorio;
import br.com.kronos.backend.aplicacao.horario.ConsultasHorariosDiaDaSemanaRepositorio;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividadeRepositorio;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import br.com.kronos.backend.aplicacao.horario.SubstitutoRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.ConvenioRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.EscalaRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.MencaoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.CreditoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.TurmaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.OcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.DocumentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiliacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TalentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TitulacaoRepositorio;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosAlunosRepositorio;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosPedagogicosRepositorio;
import br.com.kronos.backend.aplicacao.resultado.AvaliadoRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFaseRepositorio;

@Configuration
public class ConfigRepositorio {
	
	@Bean
	public ArquivoRepositorio arquivoRepositorio(@Qualifier("ds") DataSource ds) {
		return new ArquivoRepositorioImpl(ds);
	}
	
	@Bean
	public InstituicaoRepositorio instituicaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new InstituicaoRepositorioImpl(ds);
	}

	@Bean
	public UsuarioRepositorio usuarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new UsuarioRepositorioImpl(ds);
	}
	
	@Bean
	public CidadeRepositorio cidadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new CidadeRepositorioImpl(ds);
	}
	
	@Bean
	public EstadoRepositorio estadoRepositorio(@Qualifier("ds") DataSource ds) {
		return new EstadoRepositorioImpl(ds);
	}
	
	@Bean
	public PaisRepositorio paisRepositorio(@Qualifier("ds") DataSource ds) {
		return new PaisRepositorioImpl(ds);
	}
	
	@Bean
	public PessoaRepositorio pessoaRepositorio(@Qualifier("ds") DataSource ds) {
		return new PessoaRepositorioImpl(ds);
	}
	
	@Bean
	public EnderecoPessoaRepositorio enderecoPessoaRepositorio(@Qualifier("ds") DataSource ds) {
		return new EnderecoPessoaRepositorioImpl(ds);
	}
	
	@Bean
	public TelefonePessoaRepositorio telefonePessoaRepositorio(@Qualifier("ds") DataSource ds) {
		return new TelefonePessoaRepositorioImpl(ds);
	}
	
	@Bean
	public FiliacaoRepositorio filiacaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new FiliacaoRepositorioImpl(ds);
	}
	
	@Bean
	public TalentoRepositorio talentoRepositorio(@Qualifier("ds") DataSource ds) {
		return new TalentoRepositorioImpl(ds);
	}
	
	@Bean
	public DocumentoRepositorio documentoRepositorio(@Qualifier("ds") DataSource ds) {
		return new DocumentoRepositorioImpl(ds);
	}
	
	@Bean
	public TitulacaoRepositorio titulacaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new TitulacaoRepositorioImpl(ds);
	}
	
	
	@Bean
	public CursoRepositorio cursoRepositorio(@Qualifier("ds") DataSource ds) {
		return new CursoRepositorioImpl(ds);
	}
	
	@Bean
	public FaixaAnoRepositorio faixaAnoRepositorio(@Qualifier("ds") DataSource ds) {
		return new FaixaAnoRepositorioImpl(ds);
	}
	
	@Bean
	public PeriodoRepositorio periodoRepositorio(@Qualifier("ds") DataSource ds) {
		return new PeriodoRepositorioImpl(ds);
	}
	
	@Bean
	public FaseRepositorio faseRepositorio(@Qualifier("ds") DataSource ds) {
		return new FaseRepositorioImpl(ds);
	}
	
	@Bean
	public SubFaseRepositorio subFaseRepositorio(@Qualifier("ds") DataSource ds) {
		return new SubFaseRepositorioImpl(ds);
	}
	
	@Bean
	public PortariaRepositorio portariaRepositorio(@Qualifier("ds") DataSource ds) {
		return new PortariaRepositorioImpl(ds);
	}
	
	@Bean
	public EscalaRepositorio escalaRepositorio(@Qualifier("ds") DataSource ds) {
		return new EscalaRepositorioImpl(ds);
	}
	
	@Bean
	public CursoDocumentoRepositorio cursoDocumentoRepositorio(@Qualifier("ds") DataSource ds) {
		return new CursoDocumentoRepositorioImpl(ds);
	}
	
	@Bean
	public CursoFuncionarioRepositorio cursoFuncionarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new CursoFuncionarioRepositorioImpl(ds);
	}
	
	@Bean
	public FuncionarioRepositorio funcionarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new FuncionarioRepositorioImpl(ds);
	}
	
	@Bean
	public FuncionarioFuncaoRepositorio funcionarioFuncaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new FuncionarioFuncaoRepositorioImpl(ds);
	}
	
	@Bean
	public DisciplinaRepositorio disciplinaRepositorio(@Qualifier("ds") DataSource ds) {
		return new DisciplinaRepositorioImpl(ds);
	}
	
	
	@Bean
	public ContratoRepositorio contratoRepositorio(@Qualifier("ds") DataSource ds) {
		return new ContratoRepositorioImpl(ds);
	}
	
	@Bean
	public TurmaRepositorio turmaRepositorio(@Qualifier("ds") DataSource ds) {
		return new TurmaRepositorioImpl(ds);
	}
	
	@Bean
	public CreditoRepositorio creditoRepositorio(@Qualifier("ds") DataSource ds) {
		return new CreditoRepositorioImpl(ds);
	}
	
	@Bean
	public ConvenioRepositorio convenioRepositorio(@Qualifier("ds") DataSource ds) {
		return new ConvenioRepositorioImpl(ds);
	}
	
	@Bean
	public ContratoConvenioRepositorio contratoConvenioRepositorio(@Qualifier("ds") DataSource ds) {
		return new ContratoConvenioRepositorioImpl(ds);
	}
	
	@Bean
	public ParcelaRepositorio parcelaRepositorio(@Qualifier("ds") DataSource ds) {
		return new ParcelaRepositorioImpl(ds);
	}
	
	@Bean
	public CalendarioRepositorio calendarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new CalendarioRepositorioImpl(ds);
	}
	
	@Bean
	public CalendarioCursoRepositorio calendarioCursoRepositorio(@Qualifier("ds") DataSource ds) {
		return new CalendarioCursoRepositorioImpl(ds);
	}
	
	@Bean
	public EventoRepositorio eventoRepositorio(@Qualifier("ds") DataSource ds) {
		return new EventoRepositorioImpl(ds);
	}
	
	@Bean
	public PeriodoExecucaoRepositorio periodoExecucaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new PeriodoExecucaoRepositorioImpl(ds);
	}
	
	@Bean
	public HorarioRepositorio horarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new HorarioRepositorioImpl(ds);
	}
	
	@Bean
	public ConsultasHorariosDiaDaSemanaRepositorio consultasHorariosDiaDaSemanaRepositorio(@Qualifier("ds") DataSource ds) {
		return new ConsultasHorasDiaDaSemanaRepositorioImpl(ds);
	}
	
	@Bean
	public HorarioHoraAtividadeRepositorio horarioHoraAtividadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new HorarioHoraAtividadeRepositorioImpl(ds);
	}
	
	@Bean
	public SubstitutoRepositorio substitutoRepositorio(@Qualifier("ds") DataSource ds) {
		return new SubstitutoRepositorioImpl(ds);
	}
	
	@Bean
	public AtividadeRepositorio atividadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new AtividadeRepositorioImpl(ds);
	}
	
	@Bean
	public FaseExecucaoRepositorio faseExecucaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new FaseExecucaoRepositorioImpl(ds);
	}
	
	@Bean
	public SubFaseExecucaoRepositorio subFaseExecucaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new SubFaseExecucaoRepositorioImpl(ds);
	}
	
	@Bean
	public NivelRepositorio nivelRepositorio(@Qualifier("ds") DataSource ds) {
		return new NivelRepositorioImpl(ds);
	}
	
	@Bean
	public CampoExperienciaRepositorio campoExperienciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new CampoExperienciaRepositorioImpl(ds);
	}
	
	@Bean
	public ComponenteRepositorio componenteRepositorio(@Qualifier("ds") DataSource ds) {
		return new ComponenteRepositorioImpl(ds);
	}
	
	@Bean
	public DireitoRepositorio direitoRepositorio(@Qualifier("ds") DataSource ds) {
		return new DireitoRepositorioImpl(ds);
	}
	
	@Bean
	public ObjetivoRepositorio objetivoRepositorio(@Qualifier("ds") DataSource ds) {
		return new ObjetivoRepositorioImpl(ds);
	}
	
	@Bean
	public CompetenciaRepositorio competenciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new CompetenciaRepositorioImpl(ds);
	}
	
	@Bean
	public HabilidadeRepositorio habilidadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new HabilidadeRepositorioImpl(ds);
	}
	
	@Bean
	public ValorRepositorio valorRepositorio(@Qualifier("ds") DataSource ds) {
		return new ValorRepositorioImpl(ds);
	}
	
	@Bean
	public AtitudeRepositorio atitudeRepositorio(@Qualifier("ds") DataSource ds) {
		return new AtitudeRepositorioImpl(ds);
	}
	
	@Bean
	public DisciplinaDireitoRepositorio disciplinaDireitoRepositorio(@Qualifier("ds") DataSource ds) {
		return new DisciplinaDireitoRepositorioImpl(ds);
	}
	
	@Bean
	public DisciplinaObjetivoRepositorio disciplinaObjetivoRepositorio(@Qualifier("ds") DataSource ds) {
		return new DisciplinaObjetivoRepositorioImpl(ds);
	}
	
	@Bean
	public DisciplinaCompetenciaRepositorio disciplinaCompetenciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new DisciplinaCompetenciaRepositorioImpl(ds);
	}
	
	@Bean
	public DisciplinaHabilidadeRepositorio disciplinaHabilidadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new DisciplinaHabilidadeRepositorioImpl(ds);
	}
	
	@Bean
	public DiarioRepositorio diarioRepositorio(@Qualifier("ds") DataSource ds) {
		return new DiarioRepositorioImpl(ds);
	}
	
	@Bean
	public FrequenciaRepositorio frequenciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new FrequenciaRepositorioImpl(ds);
	}
	
	@Bean
	public AvaliacaoRepositorio avaliacaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new AvaliacaoRepositorioImpl(ds);
	}
	
	@Bean
	public GrupoAvaliacaoRepositorio grupoAvaliacaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new GrupoAvaliacaoRepositorioImpl(ds);
	}
	
	@Bean
	public AvaliacaoHabilidadeRepositorio avaliacaoHabilidadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new AvaliacaoHabilidadeRepositorioImpl(ds);
	}
	
	@Bean
	public AvaliadoRepositorio avaliadoRepositorio(@Qualifier("ds") DataSource ds) {
		return new AvaliadoRepositorioImpl(ds);
	}
	
	@Bean
	public MencaoRepositorio mencaoRepositorio(@Qualifier("ds") DataSource ds) {
		return new MencaoRepositorioImpl(ds);
	}
	
	@Bean
	public ResultadoHabilidadeRepositorio resultadoHabilidadeRepositorio(@Qualifier("ds") DataSource ds) {
		return new ResultadoHabilidadeRepositorioImpl(ds);
	}
	
	@Bean
	public TipoOcorrenciaRepositorio tipoOcorrenciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new TipoOcorrenciaRepositorioImpl(ds);
	}
	
	@Bean
	public OcorrenciaRepositorio ocorrenciaRepositorio(@Qualifier("ds") DataSource ds) {
		return new OcorrenciaRepositorioImpl(ds);
	}
	
	@Bean
	public AtestadoRepositorio atestadoRepositorio(@Qualifier("ds") DataSource ds) {
		return new AtestadoRepositorioImpl(ds);
	}
	
	@Bean
	public ConfiguracaoPontoRepositorio configuracaoPontoRepositorio(@Qualifier("ds") DataSource ds) {
		return new ConfiguracaoPontoRepositorioImpl(ds);
	}
	
	@Bean
	public PontoRepositorio pontoRepositorio(@Qualifier("ds") DataSource ds) {
		return new PontoRepositorioImpl(ds);
	}
	
	@Bean
	public RelatorioRepositorio relatorioRepositorio(@Qualifier("ds") DataSource ds) {
		return new RelatorioRepositorioImpl(ds);
	}
	
	@Bean
	public RelatoriosPedagogicosRepositorio relatoriosPedagogicosRepositorio(@Qualifier("ds") DataSource ds) {
		return new RelatoriosPedagogicosRepositorioImpl(ds);
	}
	
	@Bean
	public RelatoriosAlunosRepositorio relatoriosAlunosRepositorio(@Qualifier("ds") DataSource ds) {
		return new RelatoriosAlunosRepositorioImpl(ds);
	}
	
	@Bean
	public ResultadoSubFaseRepositorio resultadoSubFaseRepositorio(@Qualifier("ds") DataSource ds) {
		return new ResultadoSubFaseRepositorioImpl(ds);
	}
	
}
