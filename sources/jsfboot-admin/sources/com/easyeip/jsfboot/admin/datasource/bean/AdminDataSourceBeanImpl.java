package com.easyeip.jsfboot.admin.datasource.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.admin.AdminScheduleService;
import com.easyeip.jsfboot.admin.datasource.CustomDataSourceService;
import com.easyeip.jsfboot.admin.datasource.service.DataSourceCheckTask;
import com.easyeip.jsfboot.admin.datasource.service.DataSourceDefinition;
import com.easyeip.jsfboot.admin.schedule.ScheduleFuture;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.jdbc.ModuleDataSource;

public class AdminDataSourceBeanImpl extends JsfbootBean implements AdminDataSourceBean {
	
	@UseJsfbootService(CustomDataSourceService.class)
	private CustomDataSourceService cdss;
	
	private List<JndiSourceBindRow> bindSourceList;
	private List<CustomDataSourceForm> customSourceList;
	
	private CustomDataSourceForm customForm;
	private List<String> checkMessage;
	private ScheduleFuture checkSchedule;
	
	public AdminDataSourceBeanImpl(){
		checkMessage = new ArrayList<String>();
	}

	@Override
	public List<JndiSourceBindRow> getBindSourceList() {
		if (bindSourceList == null){
			bindSourceList = new ArrayList<JndiSourceBindRow>();
			DataSourceService dss = JsfbootContext.getDriver().getDataSources();
			for (ModuleDataSource jsb : dss.getModuleDataSources()){
				bindSourceList.add(new JndiSourceBindRow(dss, jsb));
			}
		}
		return bindSourceList;
	}

	@Override
	public List<CustomDataSourceForm> getCustomDataSources() {
		if (customSourceList == null){
			customSourceList = new ArrayList<CustomDataSourceForm>();
			
			for (String name : cdss.getDataSourceList()){
				
				DataSourceDefinition def = cdss.getCustomDataSource(name);
				customSourceList.add(new CustomDataSourceFormImpl(def));
			}
		}
		return customSourceList;
	}

	@Override
	public CustomDataSourceForm getCustomForm() {
		if (customForm == null){
			// 取得jndiName　param
			String jndiName = FacesUtils.getFacesRequestParam("jndiName");
			if (StringKit.notEmpty(jndiName)){
				DataSourceDefinition def = cdss.getCustomDataSource(jndiName);
				customForm = new CustomDataSourceFormImpl(def);
			}
			
			if (customForm == null){
				customForm = new CustomDataSourceFormImpl();
			}
		}
		return customForm;
	}
	
	@Override
	public void doDeleteCustomSource(ActionEvent event) {
		String jndiName = (String) event.getComponent().getAttributes().get("jndiName");

		cdss.removeCustomDataSource(jndiName);

		customSourceList = null;
	}

	@Override
	public String doSaveCustomSource() {
		
		if (StringKit.isEmpty(customForm.getOldJndiName())){
			// 添加数据源（用新名称）
			DataSourceDefinition existds = cdss.getCustomDataSource(customForm.getName());
			if (existds != null){
				FacesUtils.addMessageError("添加数据源", customForm.getName() + "数据源已存在。");
				return null;
			}
			if (!NameUtils.isXMLName(customForm.getName())){
				FacesUtils.addMessageError("添加数据源", customForm.getName() + " 不符合名称规范。");
				return null;
			}
			
		}else{
			// 修改数据源（用原名称）
			customForm.setName(customForm.getOldJndiName());
		}
		
		try{
			// 创建新的
			if (StringKit.isEmpty(customForm.getOldJndiName())){
				cdss.createCustomDataSource(customForm.getName());
			}
			
			// 保存
			cdss.updateCustomDataSource(customForm);
			customSourceList = null;
			return "../data-sources";
			
		}catch (Exception e){
			FacesUtils.addMessageError("添加数据源", e.getMessage());
			return null;
		}

	}
	
	@Override
	public void doCheckDataSource(ActionEvent event) {
		String jndiName = (String) event.getComponent().getAttributes().get("jndiName");
		DataSourceDefinition definition = (DataSourceDefinition)
								event.getComponent().getAttributes().get("definition");
		
		AdminScheduleService schedule = (AdminScheduleService)
				JsfbootContext.getService(AdminScheduleService.class);
		
		// 停止当前的测试
		if (checkSchedule!=null && checkSchedule.isFinish() == false){
			schedule.cancel(checkSchedule);
		}

		// 启动连接
		if (definition != null){
			checkSchedule = schedule.schedule(new DataSourceCheckTask(definition), definition.getExplain());
		}else if (StringKit.notEmpty(jndiName)){
			DataSourceService dss = JsfbootContext.getDriver().getDataSources();
			checkSchedule = schedule.schedule(new DataSourceCheckTask(dss, jndiName), jndiName);
		}
		
		getCheckMessages();
	}
	
	@Override
	public List<String> getCheckMessages() {
		if (checkSchedule != null){
			DataSourceCheckTask task = (DataSourceCheckTask) checkSchedule.getTask();
			checkMessage = task.getMessage();
		}

		return checkMessage;
	}
}
