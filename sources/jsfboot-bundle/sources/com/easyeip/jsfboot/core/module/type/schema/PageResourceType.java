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
 * <p>PageResourceType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PageResourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="site-menu" type="{http://jsfboot.easyeip.com/jsfboot-module}UserMenuType" minOccurs="0"/>
 *         &lt;element name="admin-menu" type="{http://jsfboot.easyeip.com/jsfboot-module}BackMenuType" minOccurs="0"/>
 *         &lt;element name="conf-page" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="public-pages" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageResourceType", propOrder = {

})
public class PageResourceType {

    @XmlElement(name = "site-menu")
    protected UserMenuType siteMenu;
    @XmlElement(name = "admin-menu")
    protected BackMenuType adminMenu;
    @XmlElement(name = "conf-page")
    protected String confPage;
    @XmlElement(name = "public-pages")
    protected String publicPages;

    /**
     * 获取siteMenu属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UserMenuType }
     *     
     */
    public UserMenuType getSiteMenu() {
        return siteMenu;
    }

    /**
     * 设置siteMenu属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UserMenuType }
     *     
     */
    public void setSiteMenu(UserMenuType value) {
        this.siteMenu = value;
    }

    /**
     * 获取adminMenu属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BackMenuType }
     *     
     */
    public BackMenuType getAdminMenu() {
        return adminMenu;
    }

    /**
     * 设置adminMenu属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BackMenuType }
     *     
     */
    public void setAdminMenu(BackMenuType value) {
        this.adminMenu = value;
    }

    /**
     * 获取confPage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfPage() {
        return confPage;
    }

    /**
     * 设置confPage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfPage(String value) {
        this.confPage = value;
    }

    /**
     * 获取publicPages属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicPages() {
        return publicPages;
    }

    /**
     * 设置publicPages属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicPages(String value) {
        this.publicPages = value;
    }

}
