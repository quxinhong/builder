package com.jane.builder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jane.builder.common.constant.DBType;
import com.jane.builder.modules.service.core.StdQuery;
import com.jane.builder.modules.service.core.spec.MysqlStdQuery;

@Configuration
public class CoreConfig {

	@Autowired
	private Env env;
	
	@Bean(name="stdQuery")
	public StdQuery stdQuery() {
		StdQuery res = null;
		switch (env.getDbType()) {
		case DBType.MYSQL: 
			res = new MysqlStdQuery();
			break;

		default:
			res = new MysqlStdQuery();
			break;
		}
		return res;
	}
}
