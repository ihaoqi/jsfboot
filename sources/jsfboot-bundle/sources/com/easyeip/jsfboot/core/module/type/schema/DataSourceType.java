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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DataSourceType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DataSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="required" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" fixed="true" />
 *       &lt;attribute name="explain" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSourceType")
public class DataSourceType {

    @XmlAttribute(name = "required", required = true)
    protected boolean required;
    @XmlAttribute(name = "explain", required = true)
    protected String explain;
    @XmlAttribute(name = "default")
    protected String _default;

    /**
     * 获取required属性的值。
     * 
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * 设置required属性的值。
     * 
     */
    public void setRequired(boolean value) {
        this.required = value;
    }

    /**
     * 获取explain属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置explain属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExplain(String value) {
        this.explain = value;
    }

    /**
     * 获取default属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefault() {
        return _default;
    }

    /**
     * 设置default属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefault(String value) {
        this._default = value;
    }

}
