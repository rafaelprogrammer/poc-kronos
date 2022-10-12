package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PaginacaoDTO<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Integer total;
	List<T> dados;
	
}
