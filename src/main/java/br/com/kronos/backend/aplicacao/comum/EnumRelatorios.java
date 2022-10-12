package br.com.kronos.backend.aplicacao.comum;

public enum EnumRelatorios {

	REL_REG_FOLHA_PONTO("Folha de Ponto", "REL_REG_FOLHA_PONTO.jrxml"),
	REL_REG_ALUNOS_POR_TURMA_E_SITUACAO("Alunos por turma e situação", "relatorios/REL_ALUNOS_POR_TURMA_E_SITUACAO.jrxml"),
	REL_REG_ALUNOS_POR_PERIODO_E_SITUACAO("Alunos por período e situação", "relatorios/REL_ALUNOS_POR_PERIODO_E_SITUACAO.jrxml"),
	REL_REG_DIARIO("Diário", "REL_REG_DIARIO.jrxml"),
	REL_REG_FREQUENCIA("Diário", "REL_REG_FREQUENCIA.jrxml"),
	REL_REG_RESULTADO_BIMESTRE("Diário", "REL_REG_RESULTADO_BIMESTRE.jrxml");
	
	private String nome;
	private String arquivo;
	
	EnumRelatorios(String nome, String arquivo) {
		this.nome = nome;
		this.arquivo = arquivo;
	}
	
	public String nome() {
		return this.nome;
	}
	
	public String arquivo() {
		return this.arquivo;
	}
	
}
