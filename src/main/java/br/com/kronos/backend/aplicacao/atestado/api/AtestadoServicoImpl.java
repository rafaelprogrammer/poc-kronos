package br.com.kronos.backend.aplicacao.atestado.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;
import br.com.kronos.backend.aplicacao.atestado.Atestado;
import br.com.kronos.backend.aplicacao.atestado.AtestadoRepositorio;
import br.com.kronos.backend.aplicacao.atestado.FiltroAtestado;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AtestadoServicoImpl implements AtestadoServico {

	@NonNull
	private AtestadoRepositorio atestadoRepositorio;

	@NonNull
	private ModelMapper modelMapper;
	
	@NonNull
	private ArquivoRepositorio arquivoRepositorio;
	
	public void verificarAtestadoConflitante(FiltroAtestado filtroAtestado) {
		boolean atestadoConflitando = atestadoRepositorio.verificarAtestadoConflitante(filtroAtestado);
		if (atestadoConflitando) {
			throw new ExcecaoDeNegocio("O período do atestado está conflitando com um atestado já cadastrado");
		}
	}

	@Override
	public Long criar(AtestadoDTO dto) throws ExcecaoDeNegocio {

		verificarAtestadoConflitante(FiltroAtestado.builder()
				.idPessoa(dto.getIdPessoa())
				.dataInicioVigencia(dto.getDataInicioVigencia())
				.dataFinalVigencia(dto.getDataFinalVigencia())
				.build());
		
		return atestadoRepositorio.criar(Atestado.builder()
				.dataEntrega(dto.getDataEntrega())
				.dataFinalVigencia(dto.getDataFinalVigencia())
				.dataInicioVigencia(dto.getDataInicioVigencia())
				.idFuncionario(dto.getIdFuncionario())
				.idInstituicao(dto.getIdInstituicao())
				.idPessoa(dto.getIdPessoa())
				.idTipoFalta(dto.getIdTipoFalta())
				.build());	

	}

	@Override
	public Long alterar(AtestadoDTO dto) throws ExcecaoDeNegocio {
		
		verificarAtestadoConflitante(FiltroAtestado.builder()
				.idPessoa(dto.getIdPessoa())
				.dataInicioVigencia(dto.getDataInicioVigencia())
				.dataFinalVigencia(dto.getDataFinalVigencia())
				.id(dto.getId())
				.build());
		
		return atestadoRepositorio.alterar(Atestado.builder()
				.id(dto.getId())
				.dataEntrega(dto.getDataEntrega())
				.dataFinalVigencia(dto.getDataFinalVigencia())
				.dataInicioVigencia(dto.getDataInicioVigencia())
				.idFuncionario(dto.getIdFuncionario())
				.idInstituicao(dto.getIdInstituicao())
				.idPessoa(dto.getIdPessoa())
				.idTipoFalta(dto.getIdTipoFalta())
				.build());	
		
	}

	@Override
	public AtestadoDTO buscarPorId(Long id) {
		return modelMapper.map(atestadoRepositorio.buscarPorId(id), AtestadoDTO.class);
	}

	@Override
	public PaginacaoDTO<AtestadoDTO> listar(FiltroAtestado filtro) throws ExcecaoDeNegocio {
		int total = atestadoRepositorio.contar(filtro);
		
		List<AtestadoDTO> atestados = modelMapper.map(atestadoRepositorio.listar(filtro),
				new TypeToken<List<AtestadoDTO>>() {
				}.getType());
		
		return PaginacaoDTO.<AtestadoDTO>builder().total(total).dados(atestados).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long id) {
		Atestado atestado = atestadoRepositorio.buscarPorId(id);
		atestadoRepositorio.excluir(id);
		if (atestado.getIdArqAnexo() != null) {
			arquivoRepositorio.excluir(atestado.getIdArqAnexo());
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void alterarArquivo(AtestadoDTO dto) {
		ArquivoDTO arquivoDTO = dto.getArquivoDTO();
		Atestado atestadoBanco = atestadoRepositorio.buscarPorId(dto.getId());
		Integer idArquivoAnexoAntigo = atestadoBanco.getIdArqAnexo() != null ? atestadoBanco.getIdArqAnexo() : null;
		int idArquivo = arquivoRepositorio.criar(Arquivo.builder()
										.bytes(arquivoDTO.getBytes())
										.tamanho(arquivoDTO.getTamanho())
										.contentType(arquivoDTO.getContentType())
										.nome(arquivoDTO.getNome())
										.legenda(arquivoDTO.getLegenda())
										.idTipoConteudo(EnumTipoConteudo.DOCUMENTO.id())
										.build());
		
		atestadoRepositorio.alterarArquivo(dto.getId(), idArquivo);
		if (idArquivoAnexoAntigo != null) {
			arquivoRepositorio.excluir(idArquivoAnexoAntigo);
		}
		
	}

}

