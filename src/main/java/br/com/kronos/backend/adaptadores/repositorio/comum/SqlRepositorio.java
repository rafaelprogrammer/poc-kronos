package br.com.kronos.backend.adaptadores.repositorio.comum;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import lombok.Getter;



public class SqlRepositorio {
	
	@Getter
	private JdbcTemplate jdbcTemplate;
	
	@Getter
	private MapSqlParameterSource mapSqlParameterSource;
	
	private List<Object> values;
	private List<String> fields;
	
	private DataSource dataSource;

	public SqlRepositorio(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	
	public void addFields(String... fields) {
		this.fields = Arrays.asList(fields);
	}
	
	public void addValues(Object... values) {
		this.values = Arrays.asList(values);
	}
	
	
	public int insertAuto(String table) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(table)
                .usingGeneratedKeyColumns("id");
		this.mapSqlParameterSource = new MapSqlParameterSource();
		IntStream.range(0, this.fields.size()).forEach(i -> {
			this.mapSqlParameterSource.addValue(fields.get(i), values.get(i));
		});
		return simpleJdbcInsert.executeAndReturnKey(this.mapSqlParameterSource).intValue();
	}
	
	public int insert(String table) {
		StringBuilder strQuery = new StringBuilder("insert into ");
		strQuery.append(table);
		strQuery.append(" (");
		fields.stream().forEach(a -> {
			strQuery.append(a);
			strQuery.append(",");
		});
		strQuery.replace(strQuery.length()-1, strQuery.length(), " ");
		strQuery.append(") ");
		strQuery.append(" values (");
		values.stream().forEach(a -> {
			strQuery.append("?");
			strQuery.append(",");
		});
		strQuery.replace(strQuery.length()-1, strQuery.length(), " ");
		strQuery.append(") ");
		return jdbcTemplate.update(strQuery.toString(), values.toArray());
	}
	
	public int insertCustom(String strQuery) {
		return jdbcTemplate.update(strQuery);
	}
	
	public int update(String table, Object id) {
		this.mapSqlParameterSource = new MapSqlParameterSource();
		StringBuilder strQuery = new StringBuilder("update ");
		strQuery.append(table);
		strQuery.append(" set ");
		fields.stream().forEach(a -> {
			strQuery.append(a);
			strQuery.append(" = ?, ");
		});
		strQuery.replace(strQuery.length()-2, strQuery.length(), " ");
		strQuery.append(" where id = ? ");
		return jdbcTemplate.update(strQuery.toString(), this.values.toArray());
	}
	
	public void delete(String table, Object id) {
		this.mapSqlParameterSource = new MapSqlParameterSource();
		StringBuilder strQuery = new StringBuilder("delete from ");
		strQuery.append(table);
		strQuery.append(" where id = ?");
		jdbcTemplate.update(strQuery.toString(), id);
	}
	
	public void delete(String table) {
		this.mapSqlParameterSource = new MapSqlParameterSource();
		StringBuilder strQuery = new StringBuilder("delete from ");
		strQuery.append(table);
		strQuery.append(" where 1=1 ");
		fields.stream().forEach(a -> {
			strQuery.append(" and ");
			strQuery.append(a);
			strQuery.append(" = ? ");
		});
		jdbcTemplate.update(strQuery.toString(), values.toArray());
	}
	
	public java.sql.Array createSqlArray(String type, Object[] values){
	    java.sql.Array intArray = null;
	    try (Connection connection = dataSource.getConnection()){
	        intArray = connection.createArrayOf(type, values);
	    } catch (SQLException ignore) {
	    }
	    return intArray;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
