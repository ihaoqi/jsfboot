//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:30 PM CST 
//


package com.easyeip.jsfboot.core.options.type.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.easyeip.jsfboot.core.options.type.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _JsfbootConfigure_QNAME = new QName("http://jsfboot.easyeip.com/jsfboot-configure", "jsfboot-configure");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyeip.jsfboot.core.options.type.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JsfbootConfigureType }
     * 
     */
    public JsfbootConfigureType createJsfbootConfigureType() {
        return new JsfbootConfigureType();
    }

    /**
     * Create an instance of {@link AppOptionsType }
     * 
     */
    public AppOptionsType createAppOptionsType() {
        return new AppOptionsType();
    }

    /**
     * Create an instance of {@link AppVersionType }
     * 
     */
    public AppVersionType createAppVersionType() {
        return new AppVersionType();
    }

    /**
     * Create an instance of {@link ServiceParamType }
     * 
     */
    public ServiceParamType createServiceParamType() {
        return new ServiceParamType();
    }

    /**
     * Create an instance of {@link ServiceConfType }
     * 
     */
    public ServiceConfType createServiceConfType() {
        return new ServiceConfType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsfbootConfigureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jsfboot.easyeip.com/jsfboot-configure", name = "jsfboot-configure")
    public JAXBElement<JsfbootConfigureType> createJsfbootConfigure(JsfbootConfigureType value) {
        return new JAXBElement<JsfbootConfigureType>(_JsfbootConfigure_QNAME, JsfbootConfigureType.class, null, value);
    }

}
