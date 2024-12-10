package jp.ken.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoginDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	



}
