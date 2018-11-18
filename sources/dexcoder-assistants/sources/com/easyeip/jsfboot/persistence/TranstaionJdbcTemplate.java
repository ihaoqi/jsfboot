package com.easyeip.jsfboot.persistence;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 支持事务处理的JdbcTemplate
 * @author liao
 *
 */
public class TranstaionJdbcTemplate extends JdbcTemplate {
	private PlatformTransactionManager platformTransactionManager;
	private DefaultTransactionDefinition transactionDefinition;
	private ThreadLocal<TransactionStatus> transcationStatus = new ThreadLocal<TransactionStatus>();

	public TranstaionJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	public void beginTranstaion() {
		TransactionStatus tmp = platformTransactionManager.getTransaction(transactionDefinition);
		transcationStatus.set(tmp);
	}

	public void commit() {
		TransactionStatus tmp = transcationStatus.get();
		if (tmp == null) {
			throw new RuntimeException("no transcation");
		}
		platformTransactionManager.commit(tmp);
		transcationStatus.remove();
	}

	public void rollback() {
		TransactionStatus tmp = transcationStatus.get();
		if (tmp == null) {
			throw new RuntimeException("no transcation");
		}
		platformTransactionManager.rollback(tmp);
		transcationStatus.remove();

	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		platformTransactionManager = new DataSourceTransactionManager(getDataSource());

	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public DefaultTransactionDefinition getTransactionDefinition() {
		return transactionDefinition;
	}

	public ThreadLocal<TransactionStatus> getTranscationStatus() {
		return transcationStatus;
	}
}
