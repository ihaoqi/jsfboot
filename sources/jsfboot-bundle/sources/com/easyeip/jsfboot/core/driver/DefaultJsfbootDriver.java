package com.easyeip.jsfboot.core.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.ProjectStage;
import javax.servlet.ServletContext;

import com.easyeip.jsfboot.utils.ClasspathSearch;
import com.easyeip.jsfboot.utils.JaxbUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootDriver;
import com.easyeip.jsfboot.core.RuntimeInfo;
import com.easyeip.jsfboot.core.driver.impl.AppMessageServiceImpl;
import com.easyeip.jsfboot.core.driver.impl.RuntimeInfoImpl;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.ModulePath;
import com.easyeip.jsfboot.core.module.impl.DefaultModuleManger;
import com.easyeip.jsfboot.core.module.impl.ModuleManagerAdmin;
import com.easyeip.jsfboot.core.options.JsfbootOptions;
import com.easyeip.jsfboot.core.options.impl.DefaultJsfbootOptions;
import com.easyeip.jsfboot.core.options.type.schema.AppOptionsType;
import com.easyeip.jsfboot.core.options.type.schema.JsfbootConfigureType;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.secutiry.SecutiryService;
import com.easyeip.jsfboot.core.secutiry.admin.SecutiryServiceAdmin;
import com.easyeip.jsfboot.core.services.ServiceManager;
import com.easyeip.jsfboot.core.services.impl.DefaultServiceManager;
import com.easyeip.jsfboot.core.services.impl.ServiceManagerAdmin;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SurfaceService;

/**
 * jsfboot驱动程序
 * @author ihaoqi
 *
 */
public class DefaultJsfbootDriver implements JsfbootDriver {
	
	static Logger logger = Logger.getLogger(DefaultJsfbootDriver.class.getName());
	
	private static final String WEBINF_CONF_PATH = "/WEB-INF/conf/";
	private static final String DEBUG_CONF_FILE = "jsfboot-development.xml";
	private static final String PRODUCT_CONF_FILE = "jsfboot-production.xml";
	
    private static final String META_INF_PREFIX = "META-INF/";
    private static final String MODULE_CONFIG_FILE = "jsfboot-module.xml";
    private static final String MODULE_CONFIG_SUFFIX = ".jsfboot-module.xml";
    private static final String MODULE_CONFIG_IMPLICIT = "META-INF/jsfboot-module.xml";
    
    private static final String CONFIGURE_XSD_FILE = "/jsfboot-configure-1.0.xsd";

	private RuntimeInfo runtimeInfo;
	private ModuleManager moduleManager;
	private ServiceManager serviceManager;
	private JsfbootOptions jsfbootOptions;
	
	private JsfbootConfigureType jsfbootConfigure;
	private ServletContext servletContext;
	
	private AppMessageService msgService;
	
	/**
	 * 初使化驱动器
	 * @throws Exception
	 */
	public void startDriver(ServletContext servletContext) throws Exception{
		
		msgService = new AppMessageServiceImpl();
		
		// 初使化对象
		this.servletContext = servletContext;
		RuntimeInfoImpl runtime = new RuntimeInfoImpl(servletContext);
		runtimeInfo = runtime;
		DefaultModuleManger module = new DefaultModuleManger();
		this.moduleManager = module;
		DefaultServiceManager service = new DefaultServiceManager();
		this.serviceManager = service;
		
		// 读取应用程序配置文件，创建控制台
		jsfbootConfigure = loadAppConfigure(runtime);
		jsfbootOptions = new DefaultJsfbootOptions (jsfbootConfigure);
		String appName = jsfbootConfigure.getAppVersion().getProductName();
		runtime.setProductName(appName);
		
		// 加载模块配置文件
		logger.log(Level.INFO, appName + "：加载系统模块 ...");
		List<URL> moduleList = getMetaInfModuleConfigures();
		module.loadModuleList(moduleList);
		
		// 创建模块中的服务
		service.addNewService(AppMessageService.class.getName(), msgService);
		service.startAllService(module);
		
		// 测试基本服务是否存在
		this.getRegistryService();
		this.getSecutiryService();
		this.getSurfaceService();
		
		// 配置默认主题
		initDefaultTheme();
		
		// 配置授权适配器
		initAccountRealm();
		
		// 设置jsf开发状态（如果web.xml没有设置的话）
		if (StringKit.isEmpty(servletContext.getInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME))){
			boolean isProductMode = runtimeInfo.isProductionMode();
			servletContext.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, 
					isProductMode ? ProjectStage.Production.name() : ProjectStage.Development.name());
		}
		
		// 填充应用消息
		msgService.add(logger, Level.INFO, appName + "：服务启动完成!");
	}
	
	/**
	 * 停止运行
	 */
	public void stopDriver(){
		
		String appName = jsfbootConfigure.getAppVersion().getProductName();

		// 停止服务
		if (serviceManager instanceof ServiceManagerAdmin){
			ServiceManagerAdmin sma = (ServiceManagerAdmin) serviceManager;
			sma.stopAllService(moduleManager);
		}
		
		// 卸载模块
		if (moduleManager instanceof ModuleManagerAdmin){
			ModuleManagerAdmin mma = (ModuleManagerAdmin) moduleManager;
			mma.freeModuleList();
		}
		
		moduleManager = null;
		serviceManager = null;
		runtimeInfo = null;
		jsfbootConfigure = null;
		servletContext = null;
		
		logger.log(Level.INFO, appName + "：已停止运行!");
	}
	
	/**
	 * 取得jsfboot配置文件
	 * @return
	 */
	public JsfbootConfigureType getRawConfigure(){
		return jsfbootConfigure;
	}
	
	/**
	 * 配置默认主题
	 * @throws Exception 
	 */
	private void initDefaultTheme() throws Exception{
		
		// 选择后台主题
		String backThemeName = jsfbootConfigure.getAppOptions().getAdminTheme();
		if (StringKit.isEmpty(backThemeName)){
			throw new Exception ("后台主题名称缺失，请在系统配置文件的<app-options>节中设置。");
		}
		this.getSurfaceService().setCurrentTheme(PageDomainType.Admin, backThemeName);
		
		// 选择前台主题
		String userThemeName = jsfbootConfigure.getAppOptions().getSiteTheme();
		if (StringKit.isEmpty(userThemeName)){
			throw new Exception ("前台主题名称缺失，请在系统配置文件的<app-options>节中设置。");
		}
		this.getSurfaceService().setCurrentTheme(PageDomainType.Site, userThemeName);
		
		logger.log(Level.INFO, "前后台主题为：" + userThemeName + " / " + backThemeName);
	}
	
	/**
	 * 初使化授权适配器
	 * @throws Exception
	 */
	private void initAccountRealm () throws Exception{
		String conf_auth = jsfbootConfigure.getAppOptions().getAccountRealm();
		if (StringKit.isEmpty(conf_auth)){
			throw new Exception("系统配置文件中未设置授权适配器。");
		}
		SecutiryServiceAdmin ssa = (SecutiryServiceAdmin) this.getSecutiryService();
		
		// 可以有多个，用逗号隔开
		List<String> multRealm = StringKit.stringSplit(conf_auth, ",");
		for (String auth : multRealm){
			ssa.getRealmManager().selectCurrentRealm(auth);
		}
		logger.log(Level.INFO, "授权适配器为： " + conf_auth);
	}
	
	/**
	 * 加载应用程序配置文件
	 * @param runtime 运行时配置，要更新运行模式
	 * @return 返回配置文件对象
	 * @throws Exception 加载出错会抛出异常
	 */
	private JsfbootConfigureType loadAppConfigure (RuntimeInfoImpl runtime) throws Exception{

		// 获取应用程序配置文件
		String rootpath = runtime.getWebContentPath();
		String debugFile = rootpath + WEBINF_CONF_PATH + DEBUG_CONF_FILE;
		String ProduFile = rootpath + WEBINF_CONF_PATH + PRODUCT_CONF_FILE;
		
		// 取得应用所在的目录名称
		File rootPath = new File(rootpath);
		String rootName = rootPath.getName();
		if (StringKit.isEmpty(rootName))
			rootName = rootpath;
		String curConfFile;
		
		InputStream conf_stream = openFileStream(debugFile);
		if (conf_stream != null){
			runtime.setDevelopmentMode(true);
			curConfFile = rootName + WEBINF_CONF_PATH + DEBUG_CONF_FILE;
		}else{
			conf_stream = openFileStream(ProduFile);
			if (conf_stream != null){
				runtime.setDevelopmentMode(false);
				curConfFile = rootName + WEBINF_CONF_PATH + PRODUCT_CONF_FILE;
			}else{
				throw new Exception("没有找到 '" + debugFile + "' 或 '" + ProduFile + "' 配置文件。");
			}
		}
		
		msgService.add(logger, Level.INFO, "系统配置文件：" + curConfFile);
		
		// 加载 JsfbootConfigureType
		JsfbootConfigureType conf = (JsfbootConfigureType) JaxbUtils.createXsdObject(
							JsfbootConfigureType.class, conf_stream, 
							this.getClass().getResource(CONFIGURE_XSD_FILE));
		
		// 初使化空对象
		if (conf.getAppOptions() == null){
			conf.setAppOptions(new AppOptionsType());
		}
		
		return conf;
	}
	
	/**
	 * 查找 jar中的jsfboot-module.xml配置文件
	 * @return
	 * @throws Exception
	 */
	public List<URL> getMetaInfModuleConfigures() throws Exception
    {
        List<URL> urlSet = new ArrayList<URL>();
        
        //Scan jars looking for paths including META-INF/faces-config.xml
        Enumeration<URL> resources = getClassLoader().getResources(MODULE_CONFIG_IMPLICIT);
        while (resources.hasMoreElements())
        {
            urlSet.add(resources.nextElement());
        }

        //Scan files inside META-INF ending with .faces-config.xml
        URL[] urls = ClasspathSearch.search(getClassLoader(), META_INF_PREFIX, MODULE_CONFIG_SUFFIX);
        for (int i = 0; i < urls.length; i++)
        {
            urlSet.add(urls[i]);
        }
        
        // 先按文件名排一次
		Collections.sort(urlSet, new Comparator<URL>(){
			public int compare(URL o1, URL o2) {
				String file1 = ModulePath.valueOf(o1).getFilename();
				String file2 = ModulePath.valueOf(o2).getFilename();
				return file1.compareTo(file2);
			}
		});

        // 加载本地META-INF下的jsfboot-module.xml、*.jsfboot-module.xml
        Set<String> otherModules = servletContext.getResourcePaths("/" + META_INF_PREFIX);
        List<URL> urlSet2 = new ArrayList<URL>();
        if (otherModules != null){
	        for (String path : otherModules){
	        	String filePath = runtimeInfo.getWebContentPath() + path;
	        	File file = new File(filePath);
	        	String name = file.getName();
	        	if (name.equals(MODULE_CONFIG_FILE) || name.endsWith(MODULE_CONFIG_SUFFIX)){
		        	urlSet2.add(file.toURI().toURL());
	        	}
	        }
        }
        
		Collections.sort(urlSet2, new Comparator<URL>(){
			public int compare(URL o1, URL o2) {
				String file1 = ModulePath.valueOf(o1).getFilename();
				String file2 = ModulePath.valueOf(o2).getFilename();
				return file1.compareTo(file2);
			}
		});
        
		// 返回所有文件URL
        urlSet.addAll(urlSet2);
        
        return urlSet;
    }
    
    /**
     * 打开文件流
     * @param filePath
     * @return
     */
    private InputStream openFileStream (String filePath){
    	try {
			return new FileInputStream( new File(filePath));
		} catch (FileNotFoundException e) {
			return null;
		}
    }
    
    private ClassLoader getClassLoader()
    {
    	//return servletContext.getClassLoader();
    	return this.getClass().getClassLoader();
    }

	@Override
	public RuntimeInfo getRuntimeInfo() {
		return this.runtimeInfo;
	}

	@Override
	public ModuleManager getModuleManager() {
		return this.moduleManager;
	}

	@Override
	public ServiceManager getServiceManager() {
		return this.serviceManager;
	}
	
	@Override
	public JsfbootOptions getSiteOptions() {
		return jsfbootOptions;
	}

	@Override
	public RegistryService getRegistryService() {
		RegistryService service = (RegistryService) serviceManager.getService(RegistryService.class);
		if (service == null)
			throw new RuntimeException("'" + RegistryService.class.getName() + "' 服务不存在，这是jsfboot必须的一个服务。");
		return service;
	}

	@Override
	public SecutiryService getSecutiryService() {
		SecutiryService service = (SecutiryService) serviceManager.getService(SecutiryService.class);
		if (service == null)
			throw new RuntimeException("'" + SecutiryService.class.getName() + "' 服务不存在，这是jsfboot必须的一个服务。");
		return service;
	}

	@Override
	public SurfaceService getSurfaceService() {
		SurfaceService service = (SurfaceService) serviceManager.getService(SurfaceService.class);
		if (service == null)
			throw new RuntimeException("'" + SurfaceService.class.getName() + "' 服务不存在，这是jsfboot必须的一个服务。");
		return service;
	}

	@Override
	public Object getAppBean(String beanName) {
		AppBeanService service = (AppBeanService) serviceManager.getService(AppBeanService.class);
		if (service == null)
			return null;
		
		return service.getAppBean(beanName);
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public DataSourceService getDataSources() {
		DataSourceService service = (DataSourceService) serviceManager.getService(DataSourceService.class);
		if (service == null)
			throw new RuntimeException("'" + SurfaceService.class.getName() + "' 服务不存在，这是jsfboot必须的一个服务。");
		return service;
	}
}
