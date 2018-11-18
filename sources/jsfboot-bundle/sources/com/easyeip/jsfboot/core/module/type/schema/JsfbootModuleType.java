//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:31 PM CST 
//


package com.easyeip.jsfboot.core.module.type.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>JsfbootModuleType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="JsfbootModuleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="module-version" type="{http://jsfboot.easyeip.com/jsfboot-module}ModuleVersionType"/>
 *         &lt;element name="page-resource" type="{http://jsfboot.easyeip.com/jsfboot-module}PageResourceType" minOccurs="0"/>
 *         &lt;element name="account-realm" type="{http://jsfboot.easyeip.com/jsfboot-module}AccountRealmType" minOccurs="0"/>
 *         &lt;element name="define-service" type="{http://jsfboot.easyeip.com/jsfboot-module}DefineServiceType" minOccurs="0"/>
 *         &lt;element name="define-theme" type="{http://jsfboot.easyeip.com/jsfboot-module}DefineThemeType" minOccurs="0"/>
 *         &lt;element name="data-source" type="{http://jsfboot.easyeip.com/jsfboot-module}DataSourceType" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="load-on-startup" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JsfbootModuleType", propOrder = {

})
public class JsfbootModuleType {

    @XmlElement(name = "module-version", required = true)
    protected ModuleVersionType moduleVersion;
    @XmlElement(name = "page-resource")
    protected PageResourceType pageResource;
    @XmlElement(name = "account-realm")
    protected AccountRealmType accountRealm;
    @XmlElement(name = "define-service")
    protected DefineServiceType defineService;
    @XmlElement(name = "define-theme")
    protected DefineThemeType defineTheme;
    @XmlElement(name = "data-source")
    protected DataSourceType dataSource;
    @XmlAttribute(name = "load-on-startup")
    protected Integer loadOnStartup;

    /**
     * 获取moduleVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ModuleVersionType }
     *     
     */
    public ModuleVersionType getModuleVersion() {
        return moduleVersion;
    }

    /**
     * 设置moduleVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ModuleVersionType }
     *     
     */
    public void setModuleVersion(ModuleVersionType value) {
        this.moduleVersion = value;
    }

    /**
     * 获取pageResource属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PageResourceType }
     *     
     */
    public PageResourceType getPageResource() {
        return pageResource;
    }

    /**
     * 设置pageResource属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PageResourceType }
     *     
     */
    public void setPageResource(PageResourceType value) {
        this.pageResource = value;
    }

    /**
     * 获取accountRealm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AccountRealmType }
     *     
     */
    public AccountRealmType getAccountRealm() {
        return accountRealm;
    }

    /**
     * 设置accountRealm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AccountRealmType }
     *     
     */
    public void setAccountRealm(AccountRealmType value) {
        this.accountRealm = value;
    }

    /**
     * 获取defineService属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DefineServiceType }
     *     
     */
    public DefineServiceType getDefineService() {
        return defineService;
    }

    /**
     * 设置defineService属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DefineServiceType }
     *     
     */
    public void setDefineService(DefineServiceType value) {
        this.defineService = value;
    }

    /**
     * 获取defineTheme属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DefineThemeType }
     *     
     */
    public DefineThemeType getDefineTheme() {
        return defineTheme;
    }

    /**
     * 设置defineTheme属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DefineThemeType }
     *     
     */
    public void setDefineTheme(DefineThemeType value) {
        this.defineTheme = value;
    }

    /**
     * 获取dataSource属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DataSourceType }
     *     
     */
    public DataSourceType getDataSource() {
        return dataSource;
    }

    /**
     * 设置dataSource属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceType }
     *     
     */
    public void setDataSource(DataSourceType value) {
        this.dataSource = value;
    }

    /**
     * 获取loadOnStartup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getLoadOnStartup() {
        if (loadOnStartup == null) {
            return  0;
        } else {
            return loadOnStartup;
        }
    }

    /**
     * 设置loadOnStartup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLoadOnStartup(Integer value) {
        this.loadOnStartup = value;
    }

}
