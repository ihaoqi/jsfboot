package com.easyeip.jsfboot.core.registry.service;

import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.JsfbootService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceUtils;

/**
 * 注册表服务
 * @author ihaoqi
 *
 */
public class DefaultRegistryService extends RegistryServiceWrapper implements JsfbootService  {

	public DefaultRegistryService() {
		super(null);
	}

	@Override
	public void startService(ServiceContext context) throws Exception {
		
		// 取得注册表实现
		Object adapterObject = ServiceUtils.getParamObjectValue(context, "provider");
		
		// 重新更新服务实现
		if (!(adapterObject instanceof RegistryService)){
			throw new Exception ("provider 参数没有实现'" + RegistryService.class.getName() + "'接口。");
		}
		
		// 准备好它
		if (adapterObject instanceof RegistryServiceAdmin){
			((RegistryServiceAdmin)adapterObject).makeReady(context);
		}
		
		this.setWrapper((RegistryService) adapterObject);
	}

	@Override
	public void stopService() throws Exception {
		if (this.getWrapper() instanceof RegistryAdmin){
			RegistryAdmin admin = (RegistryAdmin) this.getWrapper();
			admin.close();
		}
	}

}
