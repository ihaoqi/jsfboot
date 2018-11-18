//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:30 PM CST 
//


package com.easyeip.jsfboot.core.options.type.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AppOptionsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AppOptionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="site-theme" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="admin-theme" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account-realm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppOptionsType", propOrder = {
    "siteTheme",
    "adminTheme",
    "accountRealm"
})
public class AppOptionsType {

    @XmlElement(name = "site-theme", required = true)
    protected String siteTheme;
    @XmlElement(name = "admin-theme", required = true)
    protected String adminTheme;
    @XmlElement(name = "account-realm")
    protected String accountRealm;

    /**
     * 获取siteTheme属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteTheme() {
        return siteTheme;
    }

    /**
     * 设置siteTheme属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteTheme(String value) {
        this.siteTheme = value;
    }

    /**
     * 获取adminTheme属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminTheme() {
        return adminTheme;
    }

    /**
     * 设置adminTheme属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminTheme(String value) {
        this.adminTheme = value;
    }

    /**
     * 获取accountRealm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountRealm() {
        return accountRealm;
    }

    /**
     * 设置accountRealm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountRealm(String value) {
        this.accountRealm = value;
    }

}
