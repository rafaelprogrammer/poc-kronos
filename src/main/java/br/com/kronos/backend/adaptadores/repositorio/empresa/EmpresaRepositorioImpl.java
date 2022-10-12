package br.com.kronos.backend.adaptadores.repositorio.empresa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.empresa.Empresa;
import br.com.kronos.backend.aplicacao.empresa.EmpresaRepositorio;
import br.com.kronos.backend.aplicacao.empresa.FiltroEmpresa;

public class EmpresaRepositorioImpl extends SqlQueryRepositorio implements EmpresaRepositorio {
	
	public EmpresaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public int criar(Empresa empresa) {
		addFields("cnpj", "inscricao_estadual", "email_contato", "nome_contato", "nome_fantasia", "razao_social", "site");
		
		addValues(empresa.getCnpj(), empresa.getInscricaoEstadual(), empresa.getEmailContato(), empresa.getNomeContato(), empresa.getNomeFantasia(),
				empresa.getRazaoSocial(), empresa.getSite());
		
		return insert("empresa");
	}

	@Override
	public List<Empresa> listar(FiltroEmpresa filtro) {
		StringBuilder query = createQuery("select e.id, e.cnpj, e.inscricao_estadual, e.email_contato, e.nome_contato, e.nome_fantasia, ");
		query.append(" e.razao_social, e.site ");
		query.append(" from empresa e where 1=1 ");
		query = andEqual(query, "e.cnpj", filtro.getCnpj());
		query = andLike(query, "e.razao_social", filtro.getRazaoSocial());
		query = andLike(query, "e.nome_fantasia", filtro.getNomeFantasia());
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new EmpresaMapper());
	}
	
	@Override
	public int contar(FiltroEmpresa filtro) {
		StringBuilder query = createQuery("select count(e.id) from empresa e where 1=1 ");
		query = andEqual(query, "e.cnpj", filtro.getCnpj());
		query = andLike(query, "e.razao_social", filtro.getRazaoSocial());
		query = andLike(query, "e.nome_fantasia", filtro.getNomeFantasia());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(int id) {
		delete("pessoa_talento", id);
		return true;
	}
	
	private class EmpresaMapper implements RowMapper<Empresa> {

		@Override
		public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Empresa.builder()
					.id(rs.getInt("id"))
					.cnpj(rs.getString("cnpj"))
					.inscricaoEstadual(rs.getString("inscricao_estadual"))
					.emailContato(rs.getString("email_contato"))
					.nomeContato(rs.getString("nome_contato"))
					.nomeFantasia(rs.getString("nome_fantasia"))
					.razaoSocial(rs.getString("razao_social"))
					.site(rs.getString("site"))
					.build();
		}

	}

}
