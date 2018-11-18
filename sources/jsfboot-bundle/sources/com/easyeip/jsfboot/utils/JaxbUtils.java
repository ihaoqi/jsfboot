package com.easyeip.jsfboot.utils;

import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class JaxbUtils {
	/**
	 * 把xml转换成对象
	 * @param root_class	这个xml的根结象
	 * @param xml_file	xml文件流，可通过 getClass().getResourceStream()获取
	 * @param xsd_url	xsd文件，可通过getClass().getResource()获取，可为空
	 * @return	返回解析后的对象
	 * @throws Exception 中间出错就会抛出异常
	 */
	public static <T> Object createXsdObject(Class<T> root_class,
			InputStream xml_file, URL xsd_url) throws Exception {  
		
        Object object = null;   
        JAXBContext act = JAXBContext.newInstance(root_class);  
        Unmarshaller unMarshaller = act.createUnmarshaller();
        
        if (xsd_url != null){
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schemaSource = schemaFactory.newSchema(xsd_url);
	        unMarshaller.setSchema(schemaSource);
        }
        
        XMLStreamReader xml_reader = XMLInputFactory.newInstance().createXMLStreamReader(xml_file);
        object = unMarshaller.unmarshal(xml_reader, root_class).getValue();
        return object;  
    }
}
