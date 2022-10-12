package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroValor;
import br.com.kronos.backend.aplicacao.basecurricular.Valor;
import br.com.kronos.backend.aplicacao.basecurricular.api.ValorServico;
import br.com.kronos.backend.aplicacao.basecurricular.ValorRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ValorServicoImpl implements ValorServico {

	@NonNull
	private ValorRepositorio valorRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ValorDTO valorDTO) throws ExcecaoDeNegocio {

			return valorRepositorio.criar(Valor.builder()
					                        .nome(valorDTO.getNome()) 
					                        .codigo(valorDTO.getCodigo())					                        
					                        .idInstituicao(valorDTO.getIdInstituicao())
					                        .ativo(true).build());	

    }	    
	
	@Override
	public Long alterar(ValorDTO valorDTO) throws ExcecaoDeNegocio {
			return valorRepositorio.alterar(Valor.builder()
											    .id(valorDTO.getId())
											    .nome(valorDTO.getNome()) 
						                        .codigo(valorDTO.getCodigo())					                        
						                        .idInstituicao(valorDTO.getIdInstituicao())
						                        .ativo(valorDTO.isAtivo()).build());	
	}

	@Override
	public ValorDTO buscarPorId (long id) {
		return modelMapper.map(valorRepositorio.buscarPorId(id), ValorDTO.class);
	}

	@Override
	public PaginacaoDTO<ValorDTO> listar(FiltroValor filtroValor) throws ExcecaoDeNegocio {
			int total = valorRepositorio.contar(filtroValor);
			List<ValorDTO> valores = modelMapper.map(valorRepositorio.listar(filtroValor),
					new TypeToken<List<ValorDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<ValorDTO>builder().total(total).dados(valores).build();
	}

	@Override
	public boolean excluir(Long id) {
		return valorRepositorio.excluir(id);
	}

	@Override
	public List<ValorDTO> listarParaCombo(FiltroValor filtro) {
		return modelMapper.map(valorRepositorio.listar(filtro),
				new TypeToken<List<ValorDTO>>() {
				}.getType());
	}

}
