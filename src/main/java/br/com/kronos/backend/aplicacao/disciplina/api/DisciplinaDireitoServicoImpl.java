package br.com.kronos.backend.aplicacao.disciplina.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireito;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaDireitoRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaDireito;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DisciplinaDireitoServicoImpl implements DisciplinaDireitoServico {

	@NonNull
	private DisciplinaDireitoRepositorio disciplinaDireitoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void criar(DisciplinaDireitoDTO disciplinaDireitoDTO) {

		if (disciplinaDireitoDTO.getIdsDireito() != null && !disciplinaDireitoDTO.getIdsDireito().isEmpty()) {
			disciplinaDireitoDTO.getIdsDireito().stream().forEach(idDireito -> {
				disciplinaDireitoRepositorio.criar(DisciplinaDireito.builder()
                        .idDisciplina(disciplinaDireitoDTO.getIdDisciplina())                                
                        .idDireito(idDireito)
                        .idSubFase(disciplinaDireitoDTO.getIdSubFase())
                        .dataInicioVigencia(disciplinaDireitoDTO.getDataInicioVigencia()).build());	
			});
		}

    }

	@Override
	public void alterar(DisciplinaDireitoDTO disciplinaDireitoDTO) {
			disciplinaDireitoRepositorio.alterar(DisciplinaDireito.builder()
											.id(disciplinaDireitoDTO.getId())
											.idDisciplina(disciplinaDireitoDTO.getIdDisciplina())                                
                                            .idDireito(disciplinaDireitoDTO.getIdDireito())
                                            .idSubFase(disciplinaDireitoDTO.getIdSubFase())
                                            .dataInicioVigencia(disciplinaDireitoDTO.getDataInicioVigencia())
                                            .dataFinalVigencia(disciplinaDireitoDTO.getDataFinalVigencia()).build());

		
	}

	@Override
	public DisciplinaDireitoDTO buscarPorId (long id) {
		return disciplinaDireitoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaDireitoDTO> listar(FiltroDisciplinaDireito filtroDisciplinaDireito) throws ExcecaoDeNegocio {
		int total = disciplinaDireitoRepositorio.contar(filtroDisciplinaDireito);
		
		return PaginacaoDTO.<DisciplinaDireitoDTO>builder().total(total).dados(disciplinaDireitoRepositorio.listar(filtroDisciplinaDireito)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return disciplinaDireitoRepositorio.excluir(id);
	}

	@Override
	public PaginacaoDTO<DisciplinaDireitoDTO> listarParaAtividadeDisciplinaDireito(FiltroDisciplinaDireito filtroDisciplinaDireito) {
		int total = disciplinaDireitoRepositorio.contarParaAtividadeDisciplinaDireito(filtroDisciplinaDireito);
		
		return PaginacaoDTO.<DisciplinaDireitoDTO>builder().total(total).dados(disciplinaDireitoRepositorio.listarParaAtividadeDisciplinaDireito(filtroDisciplinaDireito)).build();
	}

}
