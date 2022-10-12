package br.com.kronos.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.kronos.backend.adaptadores.rest.BuscaCepRestService;
import br.com.kronos.backend.adaptadores.seguranca.servico.JsonWebTokenAuthenticationService;
import br.com.kronos.backend.adaptadores.seguranca.servico.JsonWebTokenService;
import br.com.kronos.backend.adaptadores.seguranca.servico.ServicoAutenticacaoSpringSecurity;
import br.com.kronos.backend.adaptadores.seguranca.servico.TokenAuthenticationService;
import br.com.kronos.backend.adaptadores.seguranca.servico.TokenService;
import br.com.kronos.backend.aplicacao.UsuarioRepositorio;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServicoImpl;
import br.com.kronos.backend.aplicacao.api.impl.comum.CidadeServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.CidadeServicoImpl;
import br.com.kronos.backend.aplicacao.api.impl.comum.EstadoServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.EstadoServicoImpl;
import br.com.kronos.backend.aplicacao.api.impl.comum.PaisServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.PaisServicoImpl;
import br.com.kronos.backend.aplicacao.api.impl.comum.RelatorioServico;
import br.com.kronos.backend.aplicacao.api.impl.comum.RelatorioServicoImpl;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoServico;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoServicoImpl;
import br.com.kronos.backend.aplicacao.atestado.AtestadoRepositorio;
import br.com.kronos.backend.aplicacao.atestado.api.AtestadoServico;
import br.com.kronos.backend.aplicacao.atestado.api.AtestadoServicoImpl;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeServicoImpl;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoServicoImpl;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoServico;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoServicoImpl;
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
import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.NivelServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.NivelServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoServicoImpl;
import br.com.kronos.backend.aplicacao.basecurricular.api.ValorServico;
import br.com.kronos.backend.aplicacao.basecurricular.api.ValorServicoImpl;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCursoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioServico;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioServicoImpl;
import br.com.kronos.backend.aplicacao.calendario.api.EventoServico;
import br.com.kronos.backend.aplicacao.calendario.api.EventoServicoImpl;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoServico;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoServicoImpl;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoServico;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoServicoImpl;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoServico;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoServicoImpl;
import br.com.kronos.backend.aplicacao.comum.CidadeRepositorio;
import br.com.kronos.backend.aplicacao.comum.EstadoRepositorio;
import br.com.kronos.backend.aplicacao.comum.PaisRepositorio;
import br.com.kronos.backend.aplicacao.comum.RelatorioRepositorio;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.contrato.ContratoConvenioRepositorio;
import br.com.kronos.backend.aplicacao.contrato.ContratoRepositorio;
import br.com.kronos.backend.aplicacao.contrato.ParcelaRepositorio;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoConvenioServico;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoConvenioServicoImpl;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoServico;
import br.com.kronos.backend.aplicacao.contrato.api.ContratoServicoImpl;
import br.com.kronos.backend.aplicacao.contrato.api.ParcelaServico;
import br.com.kronos.backend.aplicacao.contrato.api.ParcelaServicoImpl;
import br.com.kronos.backend.aplicacao.curso.CursoDocumentoRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import br.com.kronos.backend.aplicacao.curso.PortariaRepositorio;
import br.com.kronos.backend.aplicacao.curso.SubFaseRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.CursoDocumentoServico;
import br.com.kronos.backend.aplicacao.curso.api.CursoDocumentoServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioServico;
import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.CursoServico;
import br.com.kronos.backend.aplicacao.curso.api.CursoServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.FaseServico;
import br.com.kronos.backend.aplicacao.curso.api.FaseServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoServico;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.PortariaServico;
import br.com.kronos.backend.aplicacao.curso.api.PortariaServicoImpl;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseServico;
import br.com.kronos.backend.aplicacao.curso.api.SubFaseServicoImpl;
import br.com.kronos.backend.aplicacao.diario.AtividadeRepositorio;
import br.com.kronos.backend.aplicacao.diario.DiarioRepositorio;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeServico;
import br.com.kronos.backend.aplicacao.diario.api.AtividadeServicoImpl;
import br.com.kronos.backend.aplicacao.diario.api.DiarioServico;
import br.com.kronos.backend.aplicacao.diario.api.DiarioServicoImpl;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaCompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireitoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaServicoImpl;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDireitoServicoImpl;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaHabilidadeServicoImpl;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoServicoImpl;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaServico;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaServicoImpl;
import br.com.kronos.backend.aplicacao.empresa.EmpresaRepositorio;
import br.com.kronos.backend.aplicacao.empresa.api.EmpresaServico;
import br.com.kronos.backend.aplicacao.empresa.api.EmpresaServicoImpl;
import br.com.kronos.backend.aplicacao.frequencia.FrequenciaRepositorio;
import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaServico;
import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaServicoImpl;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncaoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.PontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoServicoImpl;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoServicoImpl;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServico;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServicoImpl;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoServico;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoServicoImpl;
import br.com.kronos.backend.aplicacao.horario.ConsultasHorariosDiaDaSemanaRepositorio;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividadeRepositorio;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import br.com.kronos.backend.aplicacao.horario.SubstitutoRepositorio;
import br.com.kronos.backend.aplicacao.horario.api.HorarioServico;
import br.com.kronos.backend.aplicacao.horario.api.HorarioServicoImpl;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoServico;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoServicoImpl;
import br.com.kronos.backend.aplicacao.instituicao.ConvenioRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.EscalaRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.MencaoRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioServico;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioServicoImpl;
import br.com.kronos.backend.aplicacao.instituicao.api.EscalaServico;
import br.com.kronos.backend.aplicacao.instituicao.api.EscalaServicoImpl;
import br.com.kronos.backend.aplicacao.instituicao.api.InstituicaoServico;
import br.com.kronos.backend.aplicacao.instituicao.api.InstituicaoServicoImpl;
import br.com.kronos.backend.aplicacao.instituicao.api.MencaoServico;
import br.com.kronos.backend.aplicacao.instituicao.api.MencaoServicoImpl;
import br.com.kronos.backend.aplicacao.matricula.CreditoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.MatriculaRepositorio;
import br.com.kronos.backend.aplicacao.matricula.TurmaRepositorio;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoServico;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoServicoImpl;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaServico;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaServicoImpl;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaServico;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaServicoImpl;
import br.com.kronos.backend.aplicacao.ocorrencia.OcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaServico;
import br.com.kronos.backend.aplicacao.ocorrencia.api.OcorrenciaServicoImpl;
import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaServico;
import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.DocumentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiliacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.ResponsavelRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TalentoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.TitulacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.EnderecoPessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.EnderecoPessoaServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelServico;
import br.com.kronos.backend.aplicacao.pessoa.api.ResponsavelServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.TalentoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TalentoServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TelefonePessoaServicoImpl;
import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoServico;
import br.com.kronos.backend.aplicacao.pessoa.api.TitulacaoServicoImpl;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosAlunosRepositorio;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosPedagogicosRepositorio;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosAlunosServico;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosAlunosServicoImpl;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosPedagogicosServico;
import br.com.kronos.backend.aplicacao.relatorio.api.RelatoriosPedagogicosServicoImpl;
import br.com.kronos.backend.aplicacao.resultado.AvaliadoRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFaseRepositorio;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoServico;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoServicoImpl;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoHabilidadeServico;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoHabilidadeServicoImpl;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseServico;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseServicoImpl;

@Configuration
public class ConfigService {
	
	@Value("${version}") 
	private String versionSystem;
	
	@Bean
	public String versionSystem() {
		return versionSystem;
	}
	
	@Bean
	public BuscaCepRestService buscaCepRestService(@Value("${viacep.url}") String url) {
		return new BuscaCepRestService(url);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ServicoAutenticacao servicoAutenticacao() {
		return new ServicoAutenticacaoSpringSecurity();
	}
	
	@Bean
	public InstituicaoServico instituicaoServico(@Qualifier("instituicaoRepositorio") InstituicaoRepositorio instituicaoRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new InstituicaoServicoImpl(instituicaoRepositorio, modelMapper);
	}

	@Bean
	public PessoaServico pessoaServico(@Qualifier("pessoaRepositorio") PessoaRepositorio pessoaRepositorio,
			@Qualifier("arquivoRepositorio") ArquivoRepositorio arquivoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PessoaServicoImpl(pessoaRepositorio, arquivoRepositorio, modelMapper);
	}
	
	@Bean
	public EnderecoPessoaServico enderecoPessoaServico(@Qualifier("enderecoPessoaRepositorio") EnderecoPessoaRepositorio enderecoPessoaRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new EnderecoPessoaServicoImpl(enderecoPessoaRepositorio, modelMapper);
	}
	
	@Bean
	public TelefonePessoaServico telefonePessoaServico(
			@Qualifier("modelMapper") ModelMapper modelMapper,
			@Qualifier("telefonePessoaRepositorio") TelefonePessoaRepositorio telefonePessoaRepositorio) {
		return new TelefonePessoaServicoImpl(telefonePessoaRepositorio, modelMapper);
	}
	
	@Bean
	public FiliacaoServico filiacaoServico(
			@Qualifier("modelMapper") ModelMapper modelMapper,
			@Qualifier("filiacaoRepositorio") FiliacaoRepositorio filiacaoRepositorio) {
		return new FiliacaoServicoImpl(filiacaoRepositorio, modelMapper);
	}
	
	@Bean
	public ResponsavelServico responsavelServico(
			@Qualifier("modelMapper") ModelMapper modelMapper,
			@Qualifier("responsavelRepositorio") ResponsavelRepositorio responsavelRepositorio,
			@Qualifier("matriculaRepositorio") MatriculaRepositorio matriculaRepositorio) {
		return new ResponsavelServicoImpl(responsavelRepositorio, matriculaRepositorio, modelMapper);
	}
	
	@Bean
	public TalentoServico talentoServico(
			@Qualifier("modelMapper") ModelMapper modelMapper,
			@Qualifier("talentoRepositorio") TalentoRepositorio talentoRepositorio) {
		return new TalentoServicoImpl(talentoRepositorio, modelMapper);
	}
	
	@Bean
	public UsuarioServico usuarioServico(@Qualifier("usuarioRepositorio") UsuarioRepositorio usuarioRepositorio,
			@Qualifier("pessoaRepositorio") PessoaRepositorio pessoaRepositorio,
			@Qualifier("instituicaoRepositorio") InstituicaoRepositorio instituicaoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new UsuarioServicoImpl(usuarioRepositorio, pessoaRepositorio, instituicaoRepositorio, modelMapper, passwordEncoder());
	}
	
	@Bean
	public CidadeServico cidadeServico(@Qualifier("cidadeRepositorio") CidadeRepositorio cidadeRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CidadeServicoImpl(cidadeRepositorio, modelMapper);
	}
	
	@Bean
	public EstadoServico estadoServico(@Qualifier("estadoRepositorio") EstadoRepositorio estadoRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new EstadoServicoImpl(estadoRepositorio, modelMapper);
	}
	
	@Bean
	public PaisServico paisServico(@Qualifier("paisRepositorio") PaisRepositorio paisRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PaisServicoImpl(paisRepositorio, modelMapper);
	}
	
	@Bean
	public ArquivoServico arquivoServico(@Qualifier("arquivoRepositorio") ArquivoRepositorio arquivoRepositorio,
			@Qualifier("pessoaRepositorio") PessoaRepositorio pessoaRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ArquivoServicoImpl(arquivoRepositorio, pessoaRepositorio, modelMapper);
	}
	
	@Bean
	public DocumentoServico documentoServico(@Qualifier("documentoRepositorio") DocumentoRepositorio documentoRepositorio, 
			@Qualifier("arquivoRepositorio") ArquivoRepositorio arquivoRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DocumentoServicoImpl(documentoRepositorio, arquivoRepositorio, modelMapper);
	}
	
	@Bean
	public TitulacaoServico titulacaoServico(@Qualifier("titulacaoRepositorio") TitulacaoRepositorio titulacaoRepositorio, 
			@Qualifier("arquivoRepositorio") ArquivoRepositorio arquivoRepositorio, @Qualifier("modelMapper") ModelMapper modelMapper) {
		return new TitulacaoServicoImpl(titulacaoRepositorio, arquivoRepositorio, modelMapper);
	}
	
	@Bean
	public EmpresaServico empresaServico(@Qualifier("empresaRepositorio") EmpresaRepositorio empresaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new EmpresaServicoImpl(empresaRepositorio, modelMapper);
	}
	
	@Bean
	public CursoServico cursoServico(@Qualifier("cursoRepositorio") CursoRepositorio cursoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CursoServicoImpl(cursoRepositorio, modelMapper);
	}
	
	@Bean
	public FaixaAnoServico faixaAnoServico(@Qualifier("faixaAnoRepositorio") FaixaAnoRepositorio faixaAnoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FaixaAnoServicoImpl(faixaAnoRepositorio, modelMapper);
	}
	
	@Bean
	public PeriodoServico periodoServico(@Qualifier("periodoRepositorio") PeriodoRepositorio periodoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PeriodoServicoImpl(periodoRepositorio, modelMapper);
	}
	
	@Bean
	public FaseServico faseServico(@Qualifier("faseRepositorio") FaseRepositorio faseRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FaseServicoImpl(faseRepositorio, modelMapper);
	}
	
	@Bean
	public SubFaseServico subFaseServico(@Qualifier("subFaseRepositorio") SubFaseRepositorio subFaseRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new SubFaseServicoImpl(subFaseRepositorio, modelMapper);
	}
	
	@Bean
	public PortariaServico portariaServico(@Qualifier("portariaRepositorio") PortariaRepositorio portariaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PortariaServicoImpl(portariaRepositorio, modelMapper);
	}
	
	@Bean
	public EscalaServico escalaServico(@Qualifier("escalaRepositorio") EscalaRepositorio escalaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new EscalaServicoImpl(escalaRepositorio, modelMapper);
	}
	
	@Bean
	public CursoDocumentoServico cursoDocumentoServico(@Qualifier("cursoDocumentoRepositorio") CursoDocumentoRepositorio cursoDocumentoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CursoDocumentoServicoImpl(cursoDocumentoRepositorio, modelMapper);
	}
	
	@Bean
	public CursoFuncionarioServico cursoFuncionarioServico(@Qualifier("cursoFuncionarioRepositorio") CursoFuncionarioRepositorio cursoFuncionarioRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CursoFuncionarioServicoImpl(cursoFuncionarioRepositorio, modelMapper);
	}
	
	@Bean
	public FuncionarioServico funcionarioServico(@Qualifier("funcionarioRepositorio") FuncionarioRepositorio funcionarioRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FuncionarioServicoImpl(funcionarioRepositorio, modelMapper);
	}
	
	@Bean
	public FuncionarioFuncaoServico funcionarioFuncaoServico(@Qualifier("funcionarioFuncaoRepositorio") FuncionarioFuncaoRepositorio funcionarioFuncaoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FuncionarioFuncaoServicoImpl(funcionarioFuncaoRepositorio, modelMapper);
	}
	
	@Bean
	public DisciplinaServico disciplinaServico(@Qualifier("disciplinaRepositorio") DisciplinaRepositorio disciplinaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DisciplinaServicoImpl(disciplinaRepositorio, modelMapper);
	}
	
	@Bean
	public MatriculaServico matriculaServico(@Qualifier("matriculaRepositorio") MatriculaRepositorio matriculaRepositorio, 
			@Qualifier("contratoRepositorio") ContratoRepositorio contratoRepositorio, 
			@Qualifier("atestadoRepositorio") AtestadoRepositorio atestadoRepositorio, 
			@Qualifier("servicoAutenticacao") ServicoAutenticacao servicoAutenticacao, 
			@Qualifier("funcionarioRepositorio") FuncionarioRepositorio funcionarioRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new MatriculaServicoImpl(matriculaRepositorio, contratoRepositorio, atestadoRepositorio, servicoAutenticacao, funcionarioRepositorio, modelMapper);
	}
	
	@Bean
	public ContratoServico contratoServico(@Qualifier("contratoRepositorio") ContratoRepositorio contratoRepositorio,
			@Qualifier("creditoRepositorio") CreditoRepositorio creditoRepositorio,
			@Qualifier("instituicaoRepositorio") InstituicaoRepositorio instituicaoRepositorio,
			@Qualifier("periodoRepositorio") PeriodoRepositorio periodoRepositorio,
			@Qualifier("convenioRepositorio") ConvenioRepositorio convenioRepositorio,
			@Qualifier("contratoConvenioServico") ContratoConvenioServico contratoConvenioServico,
			@Qualifier("parcelaServico") ParcelaServico parcelaServico,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ContratoServicoImpl(contratoRepositorio, 
				creditoRepositorio, instituicaoRepositorio, 
				periodoRepositorio, convenioRepositorio, contratoConvenioServico, parcelaServico, modelMapper);
	}
	
	@Bean
	public TurmaServico turmaServico(@Qualifier("turmaRepositorio") TurmaRepositorio turmaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new TurmaServicoImpl(turmaRepositorio, modelMapper);
	}
	
	@Bean
	public CreditoServico creditoServico(@Qualifier("creditoRepositorio") CreditoRepositorio creditoRepositorio,
			@Qualifier("disciplinaRepositorio") DisciplinaRepositorio disciplinaRepositorio,
			@Qualifier("turmaRepositorio") TurmaRepositorio turmaRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CreditoServicoImpl(creditoRepositorio, disciplinaRepositorio, turmaRepositorio, modelMapper);
	}
	
	@Bean
	public ConvenioServico convenioServico(@Qualifier("convenioRepositorio") ConvenioRepositorio convenioRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ConvenioServicoImpl(convenioRepositorio, modelMapper);
	}
	
	@Bean
	public ContratoConvenioServico contratoConvenioServico(@Qualifier("contratoConvenioRepositorio") ContratoConvenioRepositorio contratoConvenioRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ContratoConvenioServicoImpl(contratoConvenioRepositorio, modelMapper);
	}
	
	@Bean
	public ParcelaServico parcelaServico(@Qualifier("parcelaRepositorio") ParcelaRepositorio parcelaRepositorio,
			@Qualifier("contratoRepositorio") ContratoRepositorio contratoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ParcelaServicoImpl(parcelaRepositorio, contratoRepositorio, modelMapper);
	}
	
	@Bean
	public CalendarioServico calendarioServico(@Qualifier("calendarioRepositorio") CalendarioRepositorio calendarioRepositorio,
			@Qualifier("calendarioCursoRepositorio") CalendarioCursoRepositorio calendarioCursoRepositorio,
			@Qualifier("funcionarioRepositorio") FuncionarioRepositorio funcionarioRepositorio,
			@Qualifier("periodoExecucaoRepositorio") PeriodoExecucaoRepositorio periodoExecucaoRepositorio,
			@Qualifier("eventoRepositorio") EventoRepositorio eventoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CalendarioServicoImpl(calendarioRepositorio, calendarioCursoRepositorio, funcionarioRepositorio, periodoExecucaoRepositorio, eventoRepositorio, modelMapper);
	}
	
	@Bean
	public EventoServico eventoServico(@Qualifier("eventoRepositorio") EventoRepositorio eventoRepositorio,
			@Qualifier("calendarioRepositorio") CalendarioRepositorio calendarioRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new EventoServicoImpl(eventoRepositorio, calendarioRepositorio, modelMapper);
	}
	
	@Bean
	public HorarioServico horarioServico(@Qualifier("horarioRepositorio") HorarioRepositorio horarioRepositorio,
			@Qualifier("consultasHorariosDiaDaSemanaRepositorio") ConsultasHorariosDiaDaSemanaRepositorio consultasHorariosDiaDaSemanaRepositorio,
			@Qualifier("horarioHoraAtividadeRepositorio") HorarioHoraAtividadeRepositorio horarioHoraAtividadeRepositorio,
			@Qualifier("atividadeRepositorio") AtividadeRepositorio atividadeRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new HorarioServicoImpl(horarioRepositorio, consultasHorariosDiaDaSemanaRepositorio, horarioHoraAtividadeRepositorio, atividadeRepositorio, modelMapper, null);
	}
	
	@Bean
	public SubstitutoServico substitutoServico(@Qualifier("substitutoRepositorio") SubstitutoRepositorio substitutoRepositorio,
			@Qualifier("horarioRepositorio") HorarioRepositorio horarioRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new SubstitutoServicoImpl(substitutoRepositorio, horarioRepositorio, modelMapper);
	}
	
	@Bean
	public PeriodoExecucaoServico periodoExecucaoServico(@Qualifier("periodoExecucaoRepositorio") PeriodoExecucaoRepositorio periodoExecucaoRepositorio,
			@Qualifier("periodoRepositorio") PeriodoRepositorio periodoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PeriodoExecucaoServicoImpl(periodoExecucaoRepositorio, periodoRepositorio, modelMapper);
	}
	
	@Bean
	public FaseExecucaoServico faseExecucaoServico(@Qualifier("faseExecucaoRepositorio") FaseExecucaoRepositorio faseExecucaoRepositorio,
			@Qualifier("faseRepositorio") FaseRepositorio faseRepositorio,
			@Qualifier("periodoExecucaoRepositorio") PeriodoExecucaoRepositorio periodoExecucaoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FaseExecucaoServicoImpl(faseExecucaoRepositorio, faseRepositorio, periodoExecucaoRepositorio, modelMapper);
	}
	
	@Bean
	public SubFaseExecucaoServico subFaseExecucaoServico(@Qualifier("subFaseExecucaoRepositorio") SubFaseExecucaoRepositorio subFaseExecucaoRepositorio,
			@Qualifier("subFaseRepositorio") SubFaseRepositorio subFaseRepositorio,
			@Qualifier("faseExecucaoRepositorio") FaseExecucaoRepositorio faseExecucaoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new SubFaseExecucaoServicoImpl(subFaseExecucaoRepositorio, subFaseRepositorio, faseExecucaoRepositorio, modelMapper);
	}
	
	@Bean
	public NivelServico nivelServico(@Qualifier("nivelRepositorio") NivelRepositorio nivelRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new NivelServicoImpl(nivelRepositorio, modelMapper);
	}
	
	@Bean
	public ComponenteServico componenteServico(@Qualifier("componenteRepositorio") ComponenteRepositorio componenteRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ComponenteServicoImpl(componenteRepositorio, modelMapper);
	}
	
	@Bean
	public DireitoServico direitoServico(@Qualifier("direitoRepositorio") DireitoRepositorio direitoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DireitoServicoImpl(direitoRepositorio, modelMapper);
	}
	
	@Bean
	public CampoExperienciaServico campoExperienciaServico(@Qualifier("campoExperienciaRepositorio") CampoExperienciaRepositorio campoExperienciaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CampoExperienciaServicoImpl(campoExperienciaRepositorio, modelMapper);
	}
	
	@Bean
	public ObjetivoServico objetivoServico(@Qualifier("objetivoRepositorio") ObjetivoRepositorio objetivoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ObjetivoServicoImpl(objetivoRepositorio, modelMapper);
	}
	
	@Bean
	public CompetenciaServico competenciaServico(@Qualifier("competenciaRepositorio") CompetenciaRepositorio competenciaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new CompetenciaServicoImpl(competenciaRepositorio, modelMapper);
	}
	
	@Bean
	public HabilidadeServico habilidadeServico(@Qualifier("habilidadeRepositorio") HabilidadeRepositorio habilidadeRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new HabilidadeServicoImpl(habilidadeRepositorio, modelMapper);
	}
	
	@Bean
	public ValorServico valorServico(@Qualifier("valorRepositorio") ValorRepositorio valorRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ValorServicoImpl(valorRepositorio, modelMapper);
	}
	
	@Bean
	public AtitudeServico atitudeServico(@Qualifier("atitudeRepositorio") AtitudeRepositorio atitudeRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new AtitudeServicoImpl(atitudeRepositorio, modelMapper);
	}
	
	@Bean
	public DisciplinaDireitoServico disciplinaDireitoServico(@Qualifier("disciplinaDireitoRepositorio") DisciplinaDireitoRepositorio disciplinaDireitoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DisciplinaDireitoServicoImpl(disciplinaDireitoRepositorio, modelMapper);
	}
	
	@Bean
	public DisciplinaObjetivoServico disciplinaObjetivoServico(@Qualifier("disciplinaObjetivoRepositorio") DisciplinaObjetivoRepositorio disciplinaObjetivoRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DisciplinaObjetivoServicoImpl(disciplinaObjetivoRepositorio, modelMapper);
	}
	
	@Bean
	public DisciplinaCompetenciaServico disciplinaCompetenciaServico(@Qualifier("disciplinaCompetenciaRepositorio") DisciplinaCompetenciaRepositorio disciplinaCompetenciaRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DisciplinaCompetenciaServicoImpl(disciplinaCompetenciaRepositorio, modelMapper);
	}
	
	@Bean
	public DisciplinaHabilidadeServico disciplinaHabilidadeServico(@Qualifier("disciplinaHabilidadeRepositorio") DisciplinaHabilidadeRepositorio disciplinaHabilidadeRepositorio, 
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DisciplinaHabilidadeServicoImpl(disciplinaHabilidadeRepositorio, modelMapper);
	}
	
	@Bean
	public AtividadeServico atividadeServico(@Qualifier("atividadeRepositorio") AtividadeRepositorio atividadeRepositorio,
			@Qualifier("horarioRepositorio") HorarioRepositorio horarioRepositorio,
			@Qualifier("subFaseExecucaoRepositorio") SubFaseExecucaoRepositorio subFaseExecucaoRepositorio,
			@Qualifier("calendarioRepositorio") CalendarioRepositorio calendarioRepositorio,
			@Qualifier("eventoRepositorio") EventoRepositorio eventoRepositorio,
			@Qualifier("servicoAutenticacao") ServicoAutenticacao servicoAutenticacao,
			@Qualifier("funcionarioServico") FuncionarioServico funcionarioServico) {
		return new AtividadeServicoImpl(atividadeRepositorio, horarioRepositorio, subFaseExecucaoRepositorio, calendarioRepositorio, 
				eventoRepositorio, servicoAutenticacao, funcionarioServico);
	}
	
	@Bean
	public DiarioServico diarioServico(@Qualifier("diarioRepositorio") DiarioRepositorio diarioRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new DiarioServicoImpl(diarioRepositorio, modelMapper);
	}
	
	@Bean
	public AvaliacaoServico avaliacaoServico(@Qualifier("avaliacaoRepositorio") AvaliacaoRepositorio avaliacaoRepositorio,
			@Qualifier("grupoAvaliacaoRepositorio") GrupoAvaliacaoRepositorio grupoAvaliacaoRepositorio,
			@Qualifier("avaliacaoHabilidadeRepositorio") AvaliacaoHabilidadeRepositorio avaliacaoHabilidadeRepositorio,
			@Qualifier("avaliadoRepositorio") AvaliadoRepositorio avaliadoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new AvaliacaoServicoImpl(avaliacaoRepositorio, grupoAvaliacaoRepositorio, avaliacaoHabilidadeRepositorio, avaliadoRepositorio, modelMapper);
	}
	
	@Bean
	public AvaliadoServico avaliadoServico(@Qualifier("avaliadoRepositorio") AvaliadoRepositorio avaliadoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new AvaliadoServicoImpl(avaliadoRepositorio, modelMapper);
	}
	
	@Bean
	public FrequenciaServico frequenciaServico(@Qualifier("frequenciaRepositorio") FrequenciaRepositorio frequenciaRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new FrequenciaServicoImpl(frequenciaRepositorio, modelMapper);
	}
	
	@Bean
	public GrupoAvaliacaoServico grupoAvaliacaoServico(@Qualifier("grupoAvaliacaoRepositorio") GrupoAvaliacaoRepositorio grupoAvaliacaoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new GrupoAvaliacaoServicoImpl(grupoAvaliacaoRepositorio, modelMapper);
	}
	
	@Bean
	public AvaliacaoHabilidadeServico avaliacaoHabilidadeServico(@Qualifier("avaliacaoHabilidadeRepositorio") AvaliacaoHabilidadeRepositorio avaliacaoHabilidadeRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new AvaliacaoHabilidadeServicoImpl(avaliacaoHabilidadeRepositorio, modelMapper);
	}
	
	@Bean
	public MencaoServico mencaoServico(@Qualifier("mencaoRepositorio") MencaoRepositorio mencaoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new MencaoServicoImpl(mencaoRepositorio, modelMapper);
	}
	
	@Bean
	public TipoOcorrenciaServico tipoOcorrenciaServico(@Qualifier("tipoOcorrenciaRepositorio") TipoOcorrenciaRepositorio tipoOcorrenciaRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new TipoOcorrenciaServicoImpl(tipoOcorrenciaRepositorio, modelMapper);
	}
	
	@Bean
	public OcorrenciaServico ocorrenciaServico(@Qualifier("ocorrenciaRepositorio") OcorrenciaRepositorio ocorrenciaRepositorio,
			@Qualifier("pessoaRepositorio") PessoaRepositorio pessoaRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new OcorrenciaServicoImpl(ocorrenciaRepositorio, pessoaRepositorio, modelMapper);
	}
	
	@Bean
	public ResultadoHabilidadeServico resultadoHabilidadeServico(@Qualifier("resultadoHabilidadeRepositorio") ResultadoHabilidadeRepositorio resultadoHabilidadeRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ResultadoHabilidadeServicoImpl(resultadoHabilidadeRepositorio, modelMapper);
	}
	
	@Bean
	public AtestadoServico atestadoServico(@Qualifier("atestadoRepositorio") AtestadoRepositorio atestadoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper,
			@Qualifier("arquivoRepositorio") ArquivoRepositorio arquivoRepositorio) {
		return new AtestadoServicoImpl(atestadoRepositorio, modelMapper, arquivoRepositorio);
	}
	
	@Bean
	public ConfiguracaoPontoServico configuracaoPontoServico(@Qualifier("configuracaoPontoRepositorio") ConfiguracaoPontoRepositorio configuracaoPontoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ConfiguracaoPontoServicoImpl(configuracaoPontoRepositorio, modelMapper);
	}
	
	@Bean
	public PontoServico pontoServico(@Qualifier("pontoRepositorio") PontoRepositorio pontoRepositorio,
			@Qualifier("configuracaoPontoRepositorio") ConfiguracaoPontoRepositorio configuracaoPontoRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new PontoServicoImpl(pontoRepositorio, configuracaoPontoRepositorio, modelMapper);
	}
	
	@Bean
	public RelatorioServico relatorioServico(@Qualifier("relatorioRepositorio") RelatorioRepositorio relatorioRepositorio) {
		return new RelatorioServicoImpl(relatorioRepositorio);
	}
	
	@Bean
	public ResultadoSubFaseServico resultadoSubFaseServico(@Qualifier("resultadoSubFaseRepositorio") ResultadoSubFaseRepositorio resultadoSubFaseRepositorio,
			@Qualifier("modelMapper") ModelMapper modelMapper) {
		return new ResultadoSubFaseServicoImpl(resultadoSubFaseRepositorio, modelMapper);
	}
	
	@Bean
	public RelatoriosPedagogicosServico relatoriosPedagogicosServico(@Qualifier("relatoriosPedagogicosRepositorio") RelatoriosPedagogicosRepositorio relatoriosPedagogicosRepositorio) {
		return new RelatoriosPedagogicosServicoImpl(relatoriosPedagogicosRepositorio);
	}
	
	@Bean
	public RelatoriosAlunosServico relatoriosAlunosServico(@Qualifier("relatoriosAlunosRepositorio") RelatoriosAlunosRepositorio relatoriosAlunosRepositorio) {
		return new RelatoriosAlunosServicoImpl(relatoriosAlunosRepositorio);
	}
	
	@Bean
	public TokenAuthenticationService tokenAuthenticationService(@Value("${security.token.secret.key}") String secretKey, @Qualifier("usuarioServico") UsuarioServico usuarioServico) {
		return new JsonWebTokenAuthenticationService(secretKey, usuarioServico);
	}
	
	@Bean
	public TokenService tokenService(@Value("${security.token.secret.key}") String secretKey, @Qualifier("usuarioServico") UsuarioServico usuarioServico) {
		return new JsonWebTokenService(secretKey, usuarioServico, passwordEncoder());
	}
	
}
