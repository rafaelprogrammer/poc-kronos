package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.Curso;
import br.com.kronos.backend.aplicacao.curso.CursoEscala;
import br.com.kronos.backend.aplicacao.curso.CursoRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCurso;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CursoServicoImpl implements CursoServico {

	@NonNull
	private CursoRepositorio cursoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	@CacheEvict(value = {Curso.COMBO_CACHE_NAME, Curso.COMBO_CACHE_NAME_PERFIL_PROFESSORES}, allEntries=true)
	public Long criar(CursoDTO CursoDTO) throws ExcecaoDeNegocio {
			return cursoRepositorio.criar(Curso.builder()
											.nome(CursoDTO.getNome())
											.sigla(CursoDTO.getSigla())
											.tituloMasculino(CursoDTO.getTituloMasculino())
											.tituloFeminino(CursoDTO.getTituloFeminino())
											.dataInicioVigencia(CursoDTO.getDataInicioVigencia())
											.dataFinalVigencia(CursoDTO.getDataFinalVigencia())
											.dataLimiteConclusao(CursoDTO.getDataLimiteConclusao())
											.tempoMaximoConclusao(CursoDTO.getTempoMaximoConclusao())
											.tempoMaximoDescontinuo(CursoDTO.getTempoMaximoDescontinuo())
											.cargaHoraria(CursoDTO.getCargaHoraria())
											.notaMinimaAprovacaoDireta(CursoDTO.getNotaMinimaAprovacaoDireta())
                                            .notaMinimaAprovacaoRecuperacao(CursoDTO.getNotaMinimaAprovacaoRecuperacao())
                                            .ativo(CursoDTO.isAtivo())
                                            .idNivel(CursoDTO.getIdNivel())
                                            .idTipoRegimeLetivo(CursoDTO.getIdTipoRegimeLetivo())
                                            .idTipoTurno(CursoDTO.getIdTipoTurno())
											.idInstituicao(CursoDTO.getIdInstituicao())
											.idTipoMatrizHorario(CursoDTO.getIdTipoMatrizHorario())
											.build());
			
    }
    
	@Override
	@CacheEvict(value = {Curso.COMBO_CACHE_NAME, Curso.COMBO_CACHE_NAME_PERFIL_PROFESSORES}, allEntries=true)
	public Long alterar(CursoDTO cursoDTO) throws ExcecaoDeNegocio {
			return cursoRepositorio.alterar(Curso.builder()
											.id(cursoDTO.getId())
											.nome(cursoDTO.getNome())
											.sigla(cursoDTO.getSigla())
											.tituloMasculino(cursoDTO.getTituloMasculino())
											.tituloFeminino(cursoDTO.getTituloFeminino())
											.dataInicioVigencia(cursoDTO.getDataInicioVigencia())
											.dataFinalVigencia(cursoDTO.getDataFinalVigencia())
											.dataLimiteConclusao(cursoDTO.getDataLimiteConclusao())
											.tempoMaximoConclusao(cursoDTO.getTempoMaximoConclusao())
											.tempoMaximoDescontinuo(cursoDTO.getTempoMaximoDescontinuo())
											.cargaHoraria(cursoDTO.getCargaHoraria())
											.notaMinimaAprovacaoDireta(cursoDTO.getNotaMinimaAprovacaoDireta())
                                            .notaMinimaAprovacaoRecuperacao(cursoDTO.getNotaMinimaAprovacaoRecuperacao())
                                            .ativo(cursoDTO.isAtivo())
                                            .idNivel(cursoDTO.getIdNivel())
                                            .idTipoRegimeLetivo(cursoDTO.getIdTipoRegimeLetivo())
                                            .idTipoTurno(cursoDTO.getIdTipoTurno())
											.idInstituicao(cursoDTO.getIdInstituicao())
											.idTipoMatrizHorario(cursoDTO.getIdTipoMatrizHorario())
											.build());
		
	}

	@Override
	public CursoDTO buscarPorId(long id) {
		return cursoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<CursoDTO> listar(FiltroCurso filtroCurso) throws ExcecaoDeNegocio {
			int total = cursoRepositorio.contar(filtroCurso);
			
			return PaginacaoDTO.<CursoDTO>builder().total(total).dados(cursoRepositorio.listar(filtroCurso)).build();
	}

	@Override
	@CacheEvict(value = {Curso.COMBO_CACHE_NAME, Curso.COMBO_CACHE_NAME_PERFIL_PROFESSORES}, allEntries=true)
	public boolean excluir(long id) {
		return cursoRepositorio.excluir(id);
	}
	
	@Override
	public void vincularCursoEscala(CursoEscalaDTO dto) {
		this.cursoRepositorio.vincularCursoEscala(CursoEscala.builder().idCurso(dto.getIdCurso()).idEscala(dto.getIdEscala()).build());
	}

	@Override
	public void desvincularCursoEscala(CursoEscalaDTO dto) {
		this.cursoRepositorio.desvincularCursoEscala(CursoEscala.builder().idCurso(dto.getIdCurso()).idEscala(dto.getIdEscala()).build());
	}

	@Override
	public boolean verificarVinculoCursoEscala(FiltroCursoEscala filtro) {
		return this.cursoRepositorio.verificarVinculoCursoEscala(filtro);
	}

	@Override
	public List<EstruturaDTO> visualizarEstrutura(long idCurso) {
		return modelMapper.map(cursoRepositorio.visualizarEstrutura(idCurso),
				new TypeToken<List<EstruturaDTO>>() {
				}.getType());
	}

	@Override
	@Cacheable(cacheNames = Curso.COMBO_CACHE_NAME, key="#filtroCurso.hashCode()")
	public List<CursoDTO> listarParaCombo(FiltroCurso filtroCurso) {
		return cursoRepositorio.listar(filtroCurso);
	}

	@Override
	public List<CursoResumoDTO> listarParaHorario(Integer ano) {
		return cursoRepositorio.listarParaHorario(ano);
	}

	@Override
	@Cacheable(cacheNames = Curso.COMBO_CACHE_NAME_PERFIL_PROFESSORES, key="{#idInstituicao, #idPessoaUsuarioLogado}")
	public List<CursoResumoDTO> listarParaModulosDosProfessores(Long idInstituicao, Long idPessoaUsuarioLogado) {
		return cursoRepositorio.listarParaModulosDosProfessores(idInstituicao, idPessoaUsuarioLogado);
	}

	@Override
	public List<TipoMatrizHorarioDTO> listarTipoMatrizHorario() {
		return modelMapper.map(cursoRepositorio.listarTipoMatrizHorario(),
				new TypeToken<List<TipoMatrizHorarioDTO>>() {
				}.getType());
	}

}
