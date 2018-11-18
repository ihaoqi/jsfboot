package com.easyeip.jsfboot.admin.datasource.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.utils.KeyValuePair;
import com.easyeip.jsfboot.utils.StringKit;

public class DataSourceDefinitionImpl implements DataSourceDefinition {
	
	static final String MoveParamsName = "moveParams";

	String jndiName;
	String jndiExplain;
	private Map<String, String> propPairs;
	
	public DataSourceDefinitionImpl(String name){
		jndiName = name;
		propPairs = new HashMap<String, String>();
	}
	
	public DataSourceDefinitionImpl(String name, DataSourceDefinition init){
		this(name);
		
		this.setConnectionUrl(init.getConnectionUrl());
		this.setDriverClassName(init.getDriverClassName());
		this.setFilters(init.getFilters());
		this.setExplain(init.getExplain());
		this.setInitialSize(init.getInitialSize());
		this.setMaxActive(init.getMaxActive());
		this.setMaxWait(init.getMaxWait());
		this.setMinIdle(init.getMinIdle());
		this.setMoveParams(init.getMoveParams());
		this.setPassword(init.getPassword());
		this.setQueryTimeout(init.getQueryTimeout());
		this.setUsername(init.getUsername());
	}

	@Override
	public Map<String, String> allProperty() {
		
		Map<String, String> newMap = new HashMap<String, String>();
		for (Entry<String,String> ee : propPairs.entrySet()){
			if (StringKit.notEmpty(ee.getValue())){
				newMap.put(ee.getKey(), ee.getValue());
			}
		}
		newMap.remove(MoveParamsName);
		
		String moveParams = propPairs.get(MoveParamsName);
		
		List<KeyValuePair> pairList = StringKit.readKeyValueLines(moveParams, "=");

		for (KeyValuePair param : pairList){
			if (StringKit.notEmpty(param.getValue())){
				newMap.put(param.getKey(), param.getValue());
			}
		}
		return newMap;
	}

	@Override
	public String getName() {
		return jndiName;
	}

	@Override
	public String getExplain() {
		return jndiExplain;
	}

	@Override
	public void setExplain(String explain) {
		this.jndiExplain = explain;
	}

	@Override
	public String getDriverClassName() {
		return propPairs.get("driverClassName");
	}

	@Override
	public void setDriverClassName(String value) {
		propPairs.put("driverClassName", value);
	}

	@Override
	public String getConnectionUrl() {
		return propPairs.get("url");
	}

	@Override
	public void setConnectionUrl(String value) {
		propPairs.put("url", value);
	}

	@Override
	public String getUsername() {
		return propPairs.get("username");
	}

	@Override
	public void setUsername(String name) {
		propPairs.put("username", name);
	}

	@Override
	public String getPassword() {
		return propPairs.get("password");
	}

	@Override
	public void setPassword(String pwd) {
		propPairs.put("password", pwd);
	}

	@Override
	public String getInitialSize() {
		return propPairs.get("initialSize");
	}

	@Override
	public void setInitialSize(String value) {
		propPairs.put("initialSize", value);
	}

	@Override
	public String getMinIdle() {
		return propPairs.get("minIdle");
	}

	@Override
	public void setMinIdle(String value) {
		propPairs.put("minIdle", value);
	}

	@Override
	public String getMaxActive() {
		return propPairs.get("maxActive");
	}

	@Override
	public void setMaxActive(String value) {
		propPairs.put("maxActive", value);
	}

	@Override
	public String getMaxWait() {
		return propPairs.get("maxWait");
	}

	@Override
	public void setMaxWait(String value) {
		propPairs.put("maxWait", value);
	}

	@Override
	public String getQueryTimeout() {
		return propPairs.get("queryTimeout");
	}

	@Override
	public void setQueryTimeout(String value) {
		propPairs.put("queryTimeout", value);
	}

	@Override
	public String getFilters() {
		return propPairs.get("filters");
	}

	@Override
	public void setFilters(String filters) {
		propPairs.put("filters", filters);
	}

	@Override
	public String getMoveParams() {
		return propPairs.get(MoveParamsName);
	}

	@Override
	public void setMoveParams(String params) {
		propPairs.put(MoveParamsName, params);
	}
}
