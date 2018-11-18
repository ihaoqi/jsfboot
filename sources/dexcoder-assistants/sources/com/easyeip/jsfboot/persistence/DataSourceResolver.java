package com.easyeip.jsfboot.persistence;

import javax.sql.DataSource;

public interface DataSourceResolver {
	DataSource resolver() throws Exception;
}
