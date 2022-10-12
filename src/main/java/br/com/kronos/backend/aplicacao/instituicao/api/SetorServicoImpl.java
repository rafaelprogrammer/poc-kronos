package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSetor;
import br.com.kronos.backend.aplicacao.instituicao.Setor;
import br.com.kronos.backend.aplicacao.instituicao.api.SetorServico;
import br.com.kronos.backend.aplicacao.instituicao.SetorRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SetorServicoImpl implements SetorServico {

	@NonNull
	private SetorRepositorio setorRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(SetorDTO setorDTO) throws ExcecaoDeNegocio {

			return setorRepositorio.criar(Setor.builder()
					                        .nome(setorDTO.getNome()) 
					                        .ativo(setorDTO.isAtivo()) 
					                        .idInstitucicao(setorDTO.getIdInstitucicao()).build());	

    }
	
	@Override
	public Long alterar(SetorDTO setorDTO) throws ExcecaoDeNegocio {
			return setorRepositorio.alterar(Setor.builder()
											    .id(setorDTO.getId())
											    .nome(setorDTO.getNome()) 
						                        .ativo(setorDTO.isAtivo()) 
						                        .idInstitucicao(setorDTO.getIdInstitucicao()).build());		
	}

	@Override
	public SetorDTO buscarPorId (long id) {
		return modelMapper.map(setorRepositorio.buscarPorId(id), SetorDTO.class);
	}

	@Override
	public PaginacaoDTO<SetorDTO> listar(FiltroSetor filtroSetor) throws ExcecaoDeNegocio {
			int total = setorRepositorio.contar(filtroSetor);
			List<SetorDTO> setores = modelMapper.map(setorRepositorio.listar(filtroSetor),
					new TypeToken<List<SetorDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<SetorDTO>builder().total(total).dados(setores).build();
	}

	@Override
	public boolean excluir(Long id) {
		return setorRepositorio.excluir(id);
	}

}

