package br.com.kronos.backend.aplicacao.matricula.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroComprovante;
import br.com.kronos.backend.aplicacao.matricula.Comprovante;
import br.com.kronos.backend.aplicacao.matricula.api.ComprovanteServico;
import br.com.kronos.backend.aplicacao.matricula.ComprovanteRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ComprovanteServicoImpl implements ComprovanteServico {

	@NonNull
	private ComprovanteRepositorio comprovanteRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(ComprovanteDTO comprovanteDTO) throws ExcecaoDeNegocio {

			return comprovanteRepositorio.criar(Comprovante.builder()
					                        .dataEmissao(comprovanteDTO.getDataEmissao())                                
                                            .codigoValidacao(comprovanteDTO.getCodigoValidacao())
                                            .dataRegistro(comprovanteDTO.getDataRegistro())
                                            .numeroRegistro(comprovanteDTO.getNumeroRegistro())
                                            .livroRegistro(comprovanteDTO.getLivroRegistro())
                                            .folhaRegistro(comprovanteDTO.getFolhaRegistro())
                                            .dataAverbacao(comprovanteDTO.getDataAverbacao())
                                            .dataEntrega(comprovanteDTO.getDataEntrega())
                                            .idTipoComprovante(comprovanteDTO.getIdTipoComprovante())
                                            .idMatricula(comprovanteDTO.getIdMatricula())
                                            .idFuncionarioEmissor(comprovanteDTO.getIdFuncionarioEmissor())
                                            .idFuncionarioAverbacao(comprovanteDTO.getIdFuncionarioAverbacao())
                                            .idFuncionarioEntrega(comprovanteDTO.getIdFuncionarioEntrega())
                                            .idPessoaRetirada(comprovanteDTO.getIdPessoaRetirada())
                                            .idEmpresaRegistro(comprovanteDTO.getIdEmpresaRegistro()).build());	

    }

	@Override
	public Long alterar(ComprovanteDTO comprovanteDTO) throws ExcecaoDeNegocio {
			return comprovanteRepositorio.alterar(Comprovante.builder()
											.id(comprovanteDTO.getId())
											.dataEmissao(comprovanteDTO.getDataEmissao())                                
                                            .codigoValidacao(comprovanteDTO.getCodigoValidacao())
                                            .dataRegistro(comprovanteDTO.getDataRegistro())
                                            .numeroRegistro(comprovanteDTO.getNumeroRegistro())
                                            .livroRegistro(comprovanteDTO.getLivroRegistro())
                                            .folhaRegistro(comprovanteDTO.getFolhaRegistro())
                                            .dataAverbacao(comprovanteDTO.getDataAverbacao())
                                            .dataEntrega(comprovanteDTO.getDataEntrega())
                                            .idTipoComprovante(comprovanteDTO.getIdTipoComprovante())
                                            .idMatricula(comprovanteDTO.getIdMatricula())
                                            .idFuncionarioEmissor(comprovanteDTO.getIdFuncionarioEmissor())
                                            .idFuncionarioAverbacao(comprovanteDTO.getIdFuncionarioAverbacao())
                                            .idFuncionarioEntrega(comprovanteDTO.getIdFuncionarioEntrega())
                                            .idPessoaRetirada(comprovanteDTO.getIdPessoaRetirada())
                                            .idEmpresaRegistro(comprovanteDTO.getIdEmpresaRegistro()).build());

		
	}

	@Override
	public ComprovanteDTO buscarPorId (long id) {
		return modelMapper.map(comprovanteRepositorio.buscarPorId(id), ComprovanteDTO.class);
	}

	@Override
	public PaginacaoDTO<ComprovanteDTO> listar(FiltroComprovante filtroComprovante) throws ExcecaoDeNegocio {
			int total = comprovanteRepositorio.contar(filtroComprovante);
			List<ComprovanteDTO> Comprovantes = modelMapper.map(comprovanteRepositorio.listar(filtroComprovante),
					new TypeToken<List<ComprovanteDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<ComprovanteDTO>builder().total(total).dados(Comprovantes).build();
	}

	@Override
	public boolean excluir(Long id) {
		return comprovanteRepositorio.excluir(id);
	}

}
