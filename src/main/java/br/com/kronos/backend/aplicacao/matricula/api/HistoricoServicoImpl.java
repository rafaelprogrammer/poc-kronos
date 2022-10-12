package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroHistorico;
import br.com.kronos.backend.aplicacao.matricula.Historico;
import br.com.kronos.backend.aplicacao.matricula.api.HistoricoServico;
import br.com.kronos.backend.aplicacao.matricula.HistoricoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class HistoricoServicoImpl implements HistoricoServico {

	@NonNull
	private HistoricoRepositorio historicoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(HistoricoDTO historicoDTO) throws ExcecaoDeNegocio {

			return historicoRepositorio.criar(Historico.builder()
					                        .idMatricula(historicoDTO.getIdMatricula())                                
                                            .idEmpresa(historicoDTO.getIdEmpresa())
                                            .idCredito(historicoDTO.getIdCredito())
                                            .ano(historicoDTO.getAno())
                                            .nota(historicoDTO.getNota())
                                            .mencao(historicoDTO.getMencao())
                                            .disciplina(historicoDTO.getDisciplina())
                                            .periodo(historicoDTO.getPeriodo()).build());	

    }

	@Override
	public Long alterar(HistoricoDTO historicoDTO) throws ExcecaoDeNegocio {
			return historicoRepositorio.alterar(Historico.builder()
											.id(historicoDTO.getId())
											.idMatricula(historicoDTO.getIdMatricula())                                
                                            .idEmpresa(historicoDTO.getIdEmpresa())
                                            .idCredito(historicoDTO.getIdCredito())
                                            .ano(historicoDTO.getAno())
                                            .nota(historicoDTO.getNota())
                                            .mencao(historicoDTO.getMencao())
                                            .disciplina(historicoDTO.getDisciplina())
                                            .periodo(historicoDTO.getPeriodo()).build());	

		
	}

	@Override
	public HistoricoDTO buscarPorId (long id) {
		return modelMapper.map(historicoRepositorio.buscarPorId(id), HistoricoDTO.class);
	}

	@Override
	public PaginacaoDTO<HistoricoDTO> listar(FiltroHistorico filtroHistorico) throws ExcecaoDeNegocio {
			int total = historicoRepositorio.contar(filtroHistorico);
			List<HistoricoDTO> historicos = modelMapper.map(historicoRepositorio.listar(filtroHistorico),
					new TypeToken<List<HistoricoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<HistoricoDTO>builder().total(total).dados(historicos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return historicoRepositorio.excluir(id);
	}

}
