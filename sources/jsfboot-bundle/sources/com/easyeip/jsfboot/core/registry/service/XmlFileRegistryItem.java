package com.easyeip.jsfboot.core.registry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;

public class XmlFileRegistryItem implements RegistryItem {

	private Element elmt;
	private RegistryPath path;
	
	private String name;
	private Map<String, String> attrValues;

	public XmlFileRegistryItem(RegistryPath path, Element elmt){
		this.path = path;
		this.elmt = elmt;
		
		name = elmt.getNodeName();
	}
	
	@Override
	public void reset() {
		name = elmt.getNodeName();
		attrValues = null;
	}

	@Override
	public RegistryPath getPath() {
		return path;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return path.getFullPath();
	}

	@Override
	public String getComment() {
		return getValue("comment");
	}

	@Override
	public void setComment(String comment) {
		
		// 如果备注为空则删除备注属性
		if (StringKit.isEmpty(comment)){
			this.removeValue("comment");
			return;
		}

		setValue("comment", comment);
	}

	@Override
	public String getDefaultValue() {
		return getValue("default");
	}

	@Override
	public void setDefaultValue(String value) {
		setValue("default", value);
	}
	
	private Map<String, String> getAttrValues(){
		
		if (attrValues != null)
			return attrValues;
		
		// 生成属性值
		attrValues = new HashMap<String,String> ();
		for (int i = 0; i < elmt.getAttributes().getLength(); i ++){
			Attr attr = (Attr) elmt.getAttributes().item(i);
			if (StringKit.notEmpty(attr.getValue())){
				attrValues.put(attr.getName(), attr.getValue());
			}
		}
		
		return attrValues;
	}

	@Override
	public List<String> valueNames() {
		
		// 生成属性名列表
		List<String> names = new ArrayList<String>();
		names.add(null);// 默认值
		for (String key : getAttrValues().keySet().toArray(new String[0])){
			if (key.equals("default") == false && key.equals("comment") == false){
				names.add(key);
			}
		}

		// 排序
		Collections.sort(names, new Comparator<String>(){
			public int compare(String o1, String o2) {
				if (o1 == null) o1 = "";
				if (o2 == null) o2 = "";
				return o1.compareTo(o2);
			}
		});
		
		return names;
	}

	@Override
	public String getValue(String name) {
		if (StringKit.isEmpty(name))
			return this.getDefaultValue();
		
		return elmt.getAttribute(name);
	}

	@Override
	public void setValue(String name, String value) {
		if (StringKit.isEmpty(name)){
			setDefaultValue (value);
			return;
		}
		
		if (value == null){
			removeValue (name);
		}else{
			getAttrValues().put(name, value);
		}
	}

	@Override
	public void removeValue(String name) {

		getAttrValues().remove(name);

	}

	@Override
	public void setValue(String name, long value) {
		setValue (name, Long.valueOf(value).toString());
	}

	@Override
	public boolean hasValue(String name) {
		return elmt.hasAttribute(name);
	}
	
	/**
	 * 保存所有修改
	 * @param service
	 * @throws RegistryException
	 */
	public void save(XmlFileRegistryService service) throws RegistryException{
		
		// 检测名称是否修改
		if (!StringKit.equals(name, elmt.getNodeName())){
			throw new RegistryException ("XML注册表不支持修改结点名称。");
		}
		
		// 更新所有属性
		if (attrValues == null)
			return;
		
		// 检测名称规范
		for (Entry<String,String> entry : attrValues.entrySet()){
			if (!RegistryPath.isValidName(entry.getKey())){
				throw new RegistryException (entry.getKey() + "不符合名称规范。");
			}
		}
		
		service.modifyBegin();
		
		try{
			// 删除不存在的属性
			for (int i = elmt.getAttributes().getLength() - 1; i >= 0; i --){
				Attr attr = (Attr) elmt.getAttributes().item(i);
				String attrName = attr.getName();
				if (!attrValues.containsKey(attrName)){
					elmt.removeAttribute(attrName);
				}
			}
			
			// 保存属性
			for (Entry<String,String> entry : attrValues.entrySet()){
				elmt.setAttribute(entry.getKey(), entry.getValue());
			}
			
			
		}
		catch (Exception e){
			throw new RegistryException (e.getMessage());
		}
		finally{
			service.modifyComplete();
		}
	}
}
