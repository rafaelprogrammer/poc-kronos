package br.com.kronos.backend.aplicacao.basecurricular.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroObjetivo;
import br.com.kronos.backend.aplicacao.basecurricular.Objetivo;
import br.com.kronos.backend.aplicacao.basecurricular.ObjetivoRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ObjetivoServicoImpl implements ObjetivoServico {

	@NonNull
	private ObjetivoRepositorio objetivoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ObjetivoDTO objetivoDTO) throws ExcecaoDeNegocio {

			return objetivoRepositorio.criar(Objetivo.builder()
											.idCampoExperiencia(objetivoDTO.getIdCampoExperiencia())
					                        .descricao(objetivoDTO.getDescricao()) 
					                        .codigo(objetivoDTO.getCodigo())					                        
					                        .ativo(objetivoDTO.getAtivo()) 
					                        .bncc(objetivoDTO.getBncc()) 
					                        .idInstituicao(objetivoDTO.getIdInstituicao())
					                        .idFaixaAno(objetivoDTO.getIdFaixaAno()).build());	
    }

	@Override
	public Long alterar(ObjetivoDTO objetivoDTO) throws ExcecaoDeNegocio {
			return objetivoRepositorio.alterar(Objetivo.builder()
											    .id(objetivoDTO.getId())
											    .idCampoExperiencia(objetivoDTO.getIdCampoExperiencia())
						                        .descricao(objetivoDTO.getDescricao()) 
						                        .codigo(objetivoDTO.getCodigo())					                        
						                        .ativo(objetivoDTO.getAtivo()) 
						                        .bncc(objetivoDTO.getBncc()) 
						                        .idInstituicao(objetivoDTO.getIdInstituicao())
						                        .idFaixaAno(objetivoDTO.getIdFaixaAno()).build());		
	}

	@Override
	public ObjetivoDTO buscarPorId (long id) {
		return objetivoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<ObjetivoDTO> listar(FiltroObjetivo filtroObjetivo) throws ExcecaoDeNegocio {
			int total = objetivoRepositorio.contar(filtroObjetivo);
			return PaginacaoDTO.<ObjetivoDTO>builder().total(total).dados(objetivoRepositorio.listar(filtroObjetivo)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return objetivoRepositorio.excluir(id);
	}

	@Override
	public PaginacaoDTO<ObjetivoDTO> listarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo) {
		int total = objetivoRepositorio.contarParaDisciplinaObjetivo(filtroObjetivo);
		return PaginacaoDTO.<ObjetivoDTO>builder().total(total).dados(objetivoRepositorio.listarParaDisciplinaObjetivo(filtroObjetivo)).build();
	}
}
