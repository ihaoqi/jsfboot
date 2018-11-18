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
 * <p>ThemePageTemplateType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ThemePageTemplateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="main-page" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="only-content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blank-page" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThemePageTemplateType", propOrder = {

})
public class ThemePageTemplateType {

    @XmlElement(name = "main-page", required = true)
    protected String mainPage;
    @XmlElement(name = "only-content")
    protected String onlyContent;
    @XmlElement(name = "blank-page")
    protected String blankPage;

    /**
     * 获取mainPage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainPage() {
        return mainPage;
    }

    /**
     * 设置mainPage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainPage(String value) {
        this.mainPage = value;
    }

    /**
     * 获取onlyContent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlyContent() {
        return onlyContent;
    }

    /**
     * 设置onlyContent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlyContent(String value) {
        this.onlyContent = value;
    }

    /**
     * 获取blankPage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlankPage() {
        return blankPage;
    }

    /**
     * 设置blankPage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlankPage(String value) {
        this.blankPage = value;
    }

}
