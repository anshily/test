package io.anshily.base.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class JdbcTemplateUtil {
	private static JdbcTemplate jdbcTemplate;

	@SuppressWarnings("staticByhang-access")
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly = true)
	public static List<?> queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	@Transactional(readOnly = true)
	public static List<?> queryForList(String sql, Object[] obj) {
		return jdbcTemplate.queryForList(sql, obj);
	}

	public static int update(String sql, Object[] obj) {
		return jdbcTemplate.update(sql, obj);
	}

	@Transactional(readOnly = true)
	public static int queryForInt(String sql) {
		return jdbcTemplate.queryForObject(sql,Integer.class);
	}

	@Transactional(readOnly = true)
	public static String queryforString(String sql) {
		String returnValue = "";
		try {
			returnValue = jdbcTemplate.queryForObject(sql, String.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> queryForMap(String sql) {
		List<?> list = jdbcTemplate.queryForList(sql);
		if (list.size() != 0) {
			return (Map) list.get(0);
		} else {
			return new HashMap();
		}
	}

	public static void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}

	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
