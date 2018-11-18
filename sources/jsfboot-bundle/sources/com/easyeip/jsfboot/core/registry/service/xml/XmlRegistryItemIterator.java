package com.easyeip.jsfboot.core.registry.service.xml;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.service.XmlFileRegistryService;

/**
 * 结点遍历器
 * @author ihaoqi
 *
 */
public class XmlRegistryItemIterator implements Iterator<RegistryItem>{
	XmlFileRegistryService service;
	Element next;
	RegistryPath path;
	
	public XmlRegistryItemIterator (XmlFileRegistryService service, RegistryPath path, Element root){
		this.service = service;
		this.next = getFirstChildElement(root);
		this.path = path;
	}

	@Override
	public boolean hasNext() {
		return next != null && path != null;
	}

	@Override
	public RegistryItem next() {
		RegistryPath cp = path.makeChild(next.getNodeName());
		next = (Element) getNextElement (next);
		return service.getItem(cp);
	}

	@Override
	public void remove() {
//		RegistryPath cp = path.makeChild(next.getNodeName());
//		service.removeItem(cp);
	}
	
	public static Element getFirstChildElement (Element elmt){
		if (elmt == null)
			return null;
		
		Node node = elmt.getFirstChild();

		while (node != null){
			if (node instanceof Element)
				return (Element) node;
			node = node.getNextSibling();
		}
		
		return (Element) node;
	}
	
	public static Element getNextElement (Element elmt){
		Node node = elmt.getNextSibling();

		while (node != null){
			if (node instanceof Element)
				return (Element) node;
			node = node.getNextSibling();
		}
		
		return (Element) node;
	}
}