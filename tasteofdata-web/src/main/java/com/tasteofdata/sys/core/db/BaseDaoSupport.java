package com.tasteofdata.sys.core.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDaoSupport extends JdbcDaoSupport{

	@Resource(name = "dataSource")
	public void setSuperDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	public Long getLastInsertId(){
		String sql ="select last_insert_id()";
		return getJdbcTemplate().queryForLong(sql);
	}
}
