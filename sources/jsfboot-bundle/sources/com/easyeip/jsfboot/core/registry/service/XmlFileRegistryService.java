package com.easyeip.jsfboot.core.registry.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryProfile;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.registry.service.xml.XmlRegistryItemIterator;
import com.easyeip.jsfboot.core.registry.service.xml.XmlRegistrySaveCallback;
import com.easyeip.jsfboot.core.registry.service.xml.XmlRegistrySaveTask;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceUtils;

/**
 * 基于xml的注册表实现，第一次读的时候才真的去打开文件
 * @author ihaoqi
 *
 */
public class XmlFileRegistryService extends RegistryServiceAdmin implements RegistryService, XmlRegistrySaveCallback {

	static Logger logger = Logger.getLogger(XmlFileRegistryService.class.getName());
	
	static final String RegistryFilename = "jsfboot-registry.xml";
	
	private ServiceContext context;
	private DefaultRegistryProfile profile;
	
	private String saveXmlFile;	// 要保存的文件名
	private String openXmlFile;	// 要打开的文件名
	
	private Document document;
	private String lastError;
	
	private XmlRegistrySaveTask saveTask;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		this.context = context;
		profile = new DefaultRegistryProfile ();
		profile.setMaxNameLength(128);
		profile.setMaxValueLength(1024);
		profile.setMaxCommentLength(512);
		profile.setMaxNodeDepth(16);
	}

	@Override
	public void stopService() throws Exception {
		close();
	}
	
	@Override
	public RegistryProfile getProfile() {
		return profile;
	}
	
	@Override
	public void makeReady(ServiceContext callContext) throws RegistryException {
		// 取得缺省文件与用户定义的文件
		String userXmlFile = ServiceUtils.getParamTextValue(context, "filePath");
		String defaultFile;

		String folder = context.getDriver().getRuntimeInfo().getConfigSavePath();
		File folderObj = new File (folder);
		folderObj.mkdirs();
		defaultFile = folder + "/" + RegistryFilename;
		
		// 选择默认要加载的文件、保存文件
		openXmlFile = getWarRegistryFile (defaultFile);
		saveXmlFile = defaultFile;
		if (!StringKit.isEmpty(userXmlFile)){
			saveXmlFile = userXmlFile;
			File file = new File (userXmlFile);
			if (file.exists()){
				openXmlFile = userXmlFile;
			}
		}
		
		if (!StringKit.isEmpty(openXmlFile)){
			loadData(openXmlFile);
			callContext.sendAsyncNotify(NOTIFY_LOADED);
		}
		
		// 创建保存任务
		long saveDelay = ServiceUtils.getParamLongValue(context, "saveDelay", 3000);
		saveTask = new XmlRegistrySaveTask (saveDelay, this);
		
		context.getAppMessage().add(logger, Level.INFO, "注册表文件：" + saveXmlFile);
	}
	
	/**
	 * 如果要打开的注册表文件不存在则取上下文里的注册表
	 * @param defaultFile
	 * @return
	 */
	private String getWarRegistryFile(String defaultFile){
		if (!context.getDriver().getRuntimeInfo().isArchiveWebSite())
			return defaultFile;
		
		String contextFile = context.getDriver().getRuntimeInfo().getWebContentPath()
										+ "/WEB-INF/conf/" + RegistryFilename;
		if (defaultFile.equals(contextFile))
			return defaultFile;
		
		File defFileObj = new File(defaultFile);
		if (defFileObj.exists())
			return defaultFile;
		
		return contextFile;
	}
	
	private Document getDocument(){
		return document;
	}
	
	/**
	 * 打开xml文件
	 * @param xmlfile
	 */
	private void loadData(String xmlfile) throws RegistryException{		

		// 打开已存在的文件
		File xml = new File(xmlfile);
		
		if (xml.isFile() && xml.exists()){
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
			} catch (Exception e) {
				String errmsg = "注册表文件：" + xmlfile + " 加载失败";
				logger.log(Level.WARNING, errmsg, e);
				throw new RegistryException (e.getMessage());
			}
		}
		
		// 创建一个空文档
		if (document == null){
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (Exception e) {
				throw new RegistryException (e.getMessage());
			}
		}
	}
	
	/**
	 * 保存xml文件
	 * @param xmlfile
	 */
	private void saveData(String xmlfile) throws RegistryException{
		
		if (document == null)
			return;
		
		// 保存xml文件
		File xml = new File(xmlfile);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", new Integer(4));
		try {
			Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty("encoding", "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");

	        transformer.transform(new DOMSource(document), new StreamResult(xml));
	        
//	        // 输出到字符串，测试
//	        StringWriter writer = new StringWriter();  
//	        transformer.transform(new DOMSource(document), new StreamResult(writer));  
//	        System.out.println(writer.toString());  
	        
	        logger.log(Level.INFO, "注册表已保存：" + xmlfile);
	        
		} catch (Exception e) {
			throw new RegistryException (e.getMessage());
		}  

	}

	@Override
	public RegistryPath getRootPath() {
		return RegistryPath.valueOfne(RegistryPath.SPARATOR_CHAR);
	}

	@Override
	public RegistryItem createItem(RegistryPath path) throws RegistryException{

		Element elmt = getElementOfPath(path, true);
		if (elmt == null){
			// getElementOfPath路径创建失败了会设置 lastError
			throw new RegistryException (lastError);
		}

		return new XmlFileRegistryItem(path, elmt);
	}

	@Override
	public void removeItem(RegistryPath path) throws RegistryException{
		Element elmt = getElementOfPath(path, false);
		if (elmt == null){
			return;
		}
		if (path.isRootPath()){
			throw new RegistryException ("根路径不能删除。");
		}
		Element parent = (Element) elmt.getParentNode();
		
		modifyBegin();
		try{
			parent.removeChild(elmt);
		}finally{
			modifyComplete();
		}
	}

	@Override
	public RegistryItem getItem(RegistryPath path) {
		Element elmt = getElementOfPath(path, false);
		if (elmt == null){
			lastError = "路径不存在。";
			return null;
		}

		return new XmlFileRegistryItem(path, elmt);
	}
	
	@Override
	public RegistryItem useItem(RegistryPath path) throws RegistryException {
		Element elmt = getElementOfPath(path, false);
		if (elmt == null){
			throw new RegistryException (path + "路径不存在。");
		}

		return new XmlFileRegistryItem(path, elmt);
	}
	
	@Override
	public void updateItem(RegistryItem item) throws RegistryException {
		Element elmt = getElementOfPath(item.getPath(), false);
		if (elmt == null){
			throw new RegistryException (item.getPath() + "路径不存在。");
		}
		
		if (!(item instanceof XmlFileRegistryItem)){
			throw new RegistryException ("不支持的结点类型。");
		}
		
		XmlFileRegistryItem xmlItem = (XmlFileRegistryItem) item;
		xmlItem.save(this);
	}

	@Override
	public List<RegistryItem> listChildren(RegistryPath path) {
		List<RegistryItem> result = new ArrayList<RegistryItem>();
		Element elmt = getElementOfPath(path, false);
		if (elmt != null){
			Element child = XmlRegistryItemIterator.getFirstChildElement(elmt);
			while (child != null){
				RegistryPath childPath = path.makeChild(((Element) child).getTagName());
				RegistryItem item = getItem (childPath);
				result.add(item);
				child = XmlRegistryItemIterator.getNextElement(child);
			}
		}
		return result;
	}

	@Override
	public Iterable<RegistryItem> allChildren(final RegistryPath path) {
		final Element elmt = getElementOfPath(path, false);
		final XmlFileRegistryService Owner = this;
		return new Iterable<RegistryItem>(){
			@Override
			public Iterator<RegistryItem> iterator() {
				return new XmlRegistryItemIterator(Owner, path, elmt);
			}
			
		};
	}
	
	@Override
	public void close() {
		if (document == null)
			return;
		
		// 保存
		saveTask.close();
		if (saveTask.haveTask()){
			SaveRegistry();
		}
		
		document = null;
	}
	
	/**
	 * 取得xml结点
	 * @param path 结点路径
	 * @param create 不存在是否创建
	 * @return
	 */
	private Element getElementOfPath (RegistryPath path, boolean create){
		
		if (path == null){
			this.lastError = "路径不能为空。";
			return null;
		}
		
		// 取得根结点
		Element check_node = getDocument().getDocumentElement();
		if (check_node == null){
			getDocument().appendChild(getDocument().createElement("Registry"));
			check_node = getDocument().getDocumentElement();
		}
		
		for (String name : path.toNameArray()){
			
			// 查找子结点是否有同名的
			Node child = check_node.getFirstChild();
			boolean findNameNode = false;
			while (child != null){
				if (child instanceof Element){
					Element child_elmt = (Element) child;
					String tagName = child_elmt.getNodeName();
					if (name.equalsIgnoreCase(tagName)){
						check_node = child_elmt;
						findNameNode = true;
						break;
					}
				}
				
				child = child.getNextSibling();
			}
			if (findNameNode == true)
				continue;
			
			// 没有就创建
			if (create){
				modifyBegin();
				try{
					check_node = (Element) check_node.appendChild(getDocument().createElement(name));
				}catch (Exception e){
					this.lastError = e.getMessage();
					return null;
				}finally{
					modifyComplete();
				}
			}else{
				this.lastError = "路径不存在。";
				return null;
			}
		}
		
		return check_node;
	}
	
	void modifyBegin(){
		saveTask.modifyBegin();
	}
	
	void modifyCancel(){
		saveTask.modifyCancel();
	}
	
	void modifyComplete(){
		saveTask.modifyComplete();
	}

	@Override
	public void SaveRegistry() {
		if (!StringKit.isEmpty(saveXmlFile)){
			try {
				saveData (saveXmlFile);
			} catch (RegistryException e) {
				e.printStackTrace();
			}
		}
	}
}
