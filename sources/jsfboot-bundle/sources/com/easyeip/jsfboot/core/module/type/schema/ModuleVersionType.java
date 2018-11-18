//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:31 PM CST 
//


package com.easyeip.jsfboot.core.module.type.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ModuleVersionType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ModuleVersionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="module-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="module-version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="module-title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="module-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="company-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="company-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModuleVersionType", propOrder = {

})
public class ModuleVersionType {

    @XmlElement(name = "module-name", required = true)
    protected String moduleName;
    @XmlElement(name = "module-version", required = true)
    protected String moduleVersion;
    @XmlElement(name = "module-title", required = true)
    protected String moduleTitle;
    @XmlElement(name = "module-description")
    protected String moduleDescription;
    @XmlElement(name = "company-name")
    protected String companyName;
    @XmlElement(name = "company-description")
    protected String companyDescription;

    /**
     * 获取moduleName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置moduleName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleName(String value) {
        this.moduleName = value;
    }

    /**
     * 获取moduleVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleVersion() {
        return moduleVersion;
    }

    /**
     * 设置moduleVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleVersion(String value) {
        this.moduleVersion = value;
    }

    /**
     * 获取moduleTitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleTitle() {
        return moduleTitle;
    }

    /**
     * 设置moduleTitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleTitle(String value) {
        this.moduleTitle = value;
    }

    /**
     * 获取moduleDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleDescription() {
        return moduleDescription;
    }

    /**
     * 设置moduleDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleDescription(String value) {
        this.moduleDescription = value;
    }

    /**
     * 获取companyName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置companyName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * 获取companyDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyDescription() {
        return companyDescription;
    }

    /**
     * 设置companyDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyDescription(String value) {
        this.companyDescription = value;
    }

}
