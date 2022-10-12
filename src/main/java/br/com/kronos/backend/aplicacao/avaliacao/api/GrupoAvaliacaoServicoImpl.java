package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoServico;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class GrupoAvaliacaoServicoImpl implements GrupoAvaliacaoServico {

	@NonNull
	private GrupoAvaliacaoRepositorio grupoAvaliacaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(GrupoAvaliacaoDTO grupoAvaliacaoDTO) throws ExcecaoDeNegocio {

			return grupoAvaliacaoRepositorio.criar(GrupoAvaliacao.builder()
					                        .numero(grupoAvaliacaoDTO.getNumero())                                
                                            .idAvaliacao(grupoAvaliacaoDTO.getIdAvaliacao())
                                            .tema(grupoAvaliacaoDTO.getTema()).build());	

    }

	@Override
	public Long alterar(GrupoAvaliacaoDTO grupoAvaliacaoDTO) throws ExcecaoDeNegocio {
			return grupoAvaliacaoRepositorio.alterar(GrupoAvaliacao.builder()
											.id(grupoAvaliacaoDTO.getId())
											.numero(grupoAvaliacaoDTO.getNumero())                                
                                            .idAvaliacao(grupoAvaliacaoDTO.getIdAvaliacao())
                                            .tema(grupoAvaliacaoDTO.getTema()).build());	

		
	}

	@Override
	public GrupoAvaliacaoDTO buscarPorId (long id) {
		return modelMapper.map(grupoAvaliacaoRepositorio.buscarPorId(id), GrupoAvaliacaoDTO.class);
	}

	@Override
	public PaginacaoDTO<GrupoAvaliacaoDTO> listar(FiltroGrupoAvaliacao filtroGrupoAvaliacao) throws ExcecaoDeNegocio {
			int total = grupoAvaliacaoRepositorio.contar(filtroGrupoAvaliacao);
			List<GrupoAvaliacaoDTO> gruposAvaliacao = modelMapper.map(grupoAvaliacaoRepositorio.listar(filtroGrupoAvaliacao),
					new TypeToken<List<GrupoAvaliacaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<GrupoAvaliacaoDTO>builder().total(total).dados(gruposAvaliacao).build();
	}

	@Override
	public boolean verificarSeExisteGrupoAvaliacaoCadastrado(FiltroGrupoAvaliacao filtroGrupoAvaliacao) {
		return grupoAvaliacaoRepositorio.contar(filtroGrupoAvaliacao) > 0;
	}
	
	@Override
	public boolean excluir(Long id) {
		return grupoAvaliacaoRepositorio.excluir(id);
	}

	@Override
	public List<GrupoAvaliacaoDTO> listarParaCombo(FiltroGrupoAvaliacao filtroGrupoAvaliacao) {
		return grupoAvaliacaoRepositorio.listar(filtroGrupoAvaliacao);
	}
}

