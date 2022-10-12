package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.Direito;
import br.com.kronos.backend.aplicacao.basecurricular.DireitoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroDireito;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DireitoServicoImpl implements DireitoServico {

	@NonNull
	private DireitoRepositorio direitoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(DireitoDTO direitoDTO) throws ExcecaoDeNegocio {

		Long id = direitoRepositorio.criar(Direito.builder()
				                        .descricao(direitoDTO.getDescricao()) 
				                        .idNivel(direitoDTO.getIdNivel())
				                        .codigo(direitoDTO.getCodigo())					                        
				                        .ativo(direitoDTO.getAtivo()) 
				                        .bncc(direitoDTO.getBncc()) 
				                        .idInstituicao(direitoDTO.getIdInstituicao()).build());
		
		if(direitoDTO.getIdsCamposExperiencias() != null && !direitoDTO.getIdsCamposExperiencias().isEmpty()) {
			direitoDTO.getIdsCamposExperiencias().stream().forEach(idCampoExperiencia -> {
				direitoRepositorio.criarCampoExperienciaDireito(id, idCampoExperiencia);
			});
		}
			
		return id;

    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(DireitoDTO direitoDTO) throws ExcecaoDeNegocio {
		direitoRepositorio.alterar(Direito.builder()
											    .id(direitoDTO.getId())
											    .descricao(direitoDTO.getDescricao()) 
						                        .idNivel(direitoDTO.getIdNivel())
						                        .codigo(direitoDTO.getCodigo())					                        
						                        .ativo(direitoDTO.getAtivo()) 
						                        .bncc(direitoDTO.getBncc()) 
						                        .idInstituicao(direitoDTO.getIdInstituicao()).build());
		
		if(direitoDTO.getIdsCamposExperiencias() != null && !direitoDTO.getIdsCamposExperiencias().isEmpty()) {
			direitoRepositorio.excluirCamposExperienciasDireito(direitoDTO.getId());
			direitoDTO.getIdsCamposExperiencias().stream().forEach(idCampoExperiencia -> {
				direitoRepositorio.criarCampoExperienciaDireito(direitoDTO.getId(), idCampoExperiencia);
			});
		}
		
		return direitoDTO.getId();
	}

	@Override
	public DireitoDTO buscarPorId (long id) {
		DireitoDTO dto = modelMapper.map(direitoRepositorio.buscarPorId(id), DireitoDTO.class);
		dto.setIdsCamposExperiencias(direitoRepositorio.buscarIdsCamposExperienciasDireito(id));
		return dto;
	}

	@Override
	public PaginacaoDTO<DireitoDTO> listar(FiltroDireito filtroDireito) throws ExcecaoDeNegocio {
			int total = direitoRepositorio.contar(filtroDireito);
			List<DireitoDTO> direitos = modelMapper.map(direitoRepositorio.listar(filtroDireito),
					new TypeToken<List<DireitoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DireitoDTO>builder().total(total).dados(direitos).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long id) {
		direitoRepositorio.excluirCamposExperienciasDireito(id);
		return direitoRepositorio.excluir(id);
	}

	@Override
	public PaginacaoDTO<DireitoDTO> listarParaDisciplinaDireito(FiltroDireito filtroDireito) {
		int total = direitoRepositorio.contarParaDisciplinaDireito(filtroDireito);
		List<DireitoDTO> direitos = modelMapper.map(direitoRepositorio.listarParaDisciplinaDireito(filtroDireito),
				new TypeToken<List<DireitoDTO>>() {
				}.getType());
		
		return PaginacaoDTO.<DireitoDTO>builder().total(total).dados(direitos).build();
	}
}
