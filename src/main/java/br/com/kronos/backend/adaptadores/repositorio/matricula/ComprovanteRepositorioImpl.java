package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroComprovante;
import br.com.kronos.backend.aplicacao.matricula.Comprovante;
import br.com.kronos.backend.aplicacao.matricula.ComprovanteRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComprovanteRepositorioImpl extends SqlQueryRepositorio implements ComprovanteRepositorio {
	
	public ComprovanteRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Comprovante comprovante) {
        addFields("data_emissao", "codigo_validacao", "numero_processo", "data_registro", 
                  "numero_registro", "livro_registro", "folha_registro",  "data_averbacao", 
                  "data_entrega", "id_tipo_comprovante", "id_matricula", "id_funcionario_emissor", 
                  "id_funcionario_averbacao", "id_funcionario_entrega", "id_pessoa_retirada", "id_empresa_registro");
		
		addValues(comprovante.getDataEmissao(), comprovante.getCodigoValidacao(), comprovante.getNumeroProcesso(),comprovante.getDataRegistro(), 
                  comprovante.getNumeroRegistro(), comprovante.getLivroRegistro(), comprovante.getFolhaRegistro(), comprovante.getDataAverbacao(),  
                  comprovante.getDataEntrega(), comprovante.getIdTipoComprovante(), comprovante.getIdMatricula(), comprovante.getIdFuncionarioEmissor(), 
                  comprovante.getIdFuncionarioAverbacao(), comprovante.getIdFuncionarioEntrega(), comprovante.getIdPessoaRetirada(), comprovante.getIdPessoaRetirada()); 
		
		return (long) insertAuto("comprovante"); 
    }
	
	@Override
	public Long alterar(Comprovante comprovante) {
		addFields("data_emissao", "codigo_validacao", "numero_processo", "data_registro", 
                "numero_registro", "livro_registro", "folha_registro",  "data_averbacao", 
                "data_entrega", "id_tipo_comprovante", "id_matricula", "id_funcionario_emissor", 
                "id_funcionario_averbacao", "id_funcionario_entrega", "id_pessoa_retirada", "id_empresa_registro");
		
		addValues(comprovante.getDataEmissao(), comprovante.getCodigoValidacao(), comprovante.getNumeroProcesso(),comprovante.getDataRegistro(), 
                comprovante.getNumeroRegistro(), comprovante.getLivroRegistro(), comprovante.getFolhaRegistro(), comprovante.getDataAverbacao(),  
                comprovante.getDataEntrega(), comprovante.getIdTipoComprovante(), comprovante.getIdMatricula(), comprovante.getIdFuncionarioEmissor(), 
                comprovante.getIdFuncionarioAverbacao(), comprovante.getIdFuncionarioEntrega(), comprovante.getIdPessoaRetirada(), comprovante.getIdPessoaRetirada(),
                comprovante.getId());
		
		return (long) update("comprovante", comprovante.getId());
	}
	
	@Override
	public Comprovante buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select c.data_emissao, c.codigo_validacao, c.numero_processo, c.data_registro, ");
            query.append("c.numero_registro, c.livro_registro, c.folha_registro,  c.data_averbacao,  ");
            query.append("c.data_entrega, c.id_tipo_comprovante, c.id_matricula, c.id_funcionario_emissor, "); 
            query.append("c.id_funcionario_averbacao, c.id_funcionario_entrega, c.id_pessoa_retirada, c.id_empresa_registro "); 
            query.append("from comprovante c where 1=1 ");
            query = andEqual(query, "d.id", id);
			Comprovante comprovante = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ComprovanteMapper());
			return comprovante;
		} catch (EmptyResultDataAccessException e) {
			log.info("comprovante não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Comprovante> listar(FiltroComprovante filtroComprovante) {
		StringBuilder query = createQuery("select c.data_emissao, c.codigo_validacao, c.numero_processo, c.data_registro, ");
        query.append("c.numero_registro, c.livro_registro, c.folha_registro,  c.data_averbacao,  ");
        query.append("c.data_entrega, c.id_tipo_comprovante, c.id_matricula, c.id_funcionario_emissor, "); 
        query.append("c.id_funcionario_averbacao, c.id_funcionario_entrega, c.id_pessoa_retirada, c.id_empresa_registro "); 
         query.append("from comprovante c where 1=1 ");;
		query = andEqual(query, "d.id", filtroComprovante.getId());
		query = andEqual(query, "c.codigo_validacao", filtroComprovante.getCodigoValidacao());
        query = andEqual(query, "c.id_tipo_comprovante", filtroComprovante.getIdTipoCcomprovante());
        query = andEqual(query, "c.id_matricula", filtroComprovante.getIdMatricula()); 
        query = andEqual(query, "c.id_empresa_registro", filtroComprovante.getIdEmpresaRegistro());
        query = orderBy(query, Order.ASC, "c.codigo");
		query = limit(query, filtroComprovante.getQtdTotal(), filtroComprovante.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ComprovanteMapper());
	}
	
	@Override
	public int contar(FiltroComprovante filtroComprovante) {
		StringBuilder query = createQuery("select count(c.id) from comprovante c where 1=1 ");
		query = andEqual(query, "d.id", filtroComprovante.getId());
		query = andEqual(query, "c.codigo_validacao", filtroComprovante.getCodigoValidacao());
        query = andEqual(query, "c.id_tipo_comprovante", filtroComprovante.getIdTipoCcomprovante());
        query = andEqual(query, "c.id_matricula", filtroComprovante.getIdMatricula()); 
        query = andEqual(query, "c.id_empresa_registro", filtroComprovante.getIdEmpresaRegistro());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("comprovante", id);
		return true;
	}
	
	private class ComprovanteMapper implements RowMapper<Comprovante> {

		@Override
		public Comprovante mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Comprovante.builder()
					.id(rs.getLong("id"))
					.dataEmissao(rs.getDate("data_emissao").toLocalDate())
					.codigoValidacao(rs.getString("codigo_validacao"))
                    .numeroProcesso(rs.getString("numero_processo"))
                    .dataRegistro(rs.getDate("data_registro").toLocalDate())
                    .numeroRegistro(rs.getString("numero_registro"))
                    .livroRegistro(rs.getString("livro_registro"))
                    .folhaRegistro(rs.getString("folha_registro"))
                    .dataAverbacao(rs.getDate("data_averbacao").toLocalDate())
                    .dataEntrega(rs.getDate("data_entrega").toLocalDate())
                    .idTipoComprovante(rs.getInt("id_tipo_comprovante"))
                    .idMatricula(rs.getLong("id_matricula"))
                    .idFuncionarioEmissor(rs.getLong("id_funcionario_emissor"))
                    .idFuncionarioAverbacao(rs.getLong("id_funcionario_averbacao"))
                    .idFuncionarioEntrega(rs.getLong("id_funcionario_entrega"))
                    .idPessoaRetirada(rs.getLong("id_pessoa_retirada"))
                    .idEmpresaRegistro(rs.getLong("id_empresa_registro"))
					.build();
		}

	}

}
