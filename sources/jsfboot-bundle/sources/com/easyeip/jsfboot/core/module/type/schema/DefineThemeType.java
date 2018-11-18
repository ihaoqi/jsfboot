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
 * <p>DefineThemeType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DefineThemeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="primefaces-theme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="page-template" type="{http://jsfboot.easyeip.com/jsfboot-module}ThemePageTemplateType"/>
 *         &lt;element name="conf-page" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="login-page" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menu-position" type="{http://jsfboot.easyeip.com/jsfboot-module}MenuPositionType" minOccurs="0"/>
 *         &lt;element name="extend-template" type="{http://jsfboot.easyeip.com/jsfboot-module}ThemeExtendTemplateType" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="explain" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="use-site" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="use-admin" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefineThemeType", propOrder = {

})
public class DefineThemeType {

    @XmlElement(name = "primefaces-theme")
    protected String primefacesTheme;
    @XmlElement(name = "page-template", required = true)
    protected ThemePageTemplateType pageTemplate;
    @XmlElement(name = "conf-page")
    protected String confPage;
    @XmlElement(name = "login-page")
    protected String loginPage;
    @XmlElement(name = "menu-position")
    protected MenuPositionType menuPosition;
    @XmlElement(name = "extend-template")
    protected ThemeExtendTemplateType extendTemplate;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "explain")
    protected String explain;
    @XmlAttribute(name = "use-site", required = true)
    protected boolean useSite;
    @XmlAttribute(name = "use-admin", required = true)
    protected boolean useAdmin;

    /**
     * 获取primefacesTheme属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimefacesTheme() {
        return primefacesTheme;
    }

    /**
     * 设置primefacesTheme属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimefacesTheme(String value) {
        this.primefacesTheme = value;
    }

    /**
     * 获取pageTemplate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ThemePageTemplateType }
     *     
     */
    public ThemePageTemplateType getPageTemplate() {
        return pageTemplate;
    }

    /**
     * 设置pageTemplate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ThemePageTemplateType }
     *     
     */
    public void setPageTemplate(ThemePageTemplateType value) {
        this.pageTemplate = value;
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
     * 获取loginPage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginPage() {
        return loginPage;
    }

    /**
     * 设置loginPage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginPage(String value) {
        this.loginPage = value;
    }

    /**
     * 获取menuPosition属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MenuPositionType }
     *     
     */
    public MenuPositionType getMenuPosition() {
        return menuPosition;
    }

    /**
     * 设置menuPosition属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MenuPositionType }
     *     
     */
    public void setMenuPosition(MenuPositionType value) {
        this.menuPosition = value;
    }

    /**
     * 获取extendTemplate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ThemeExtendTemplateType }
     *     
     */
    public ThemeExtendTemplateType getExtendTemplate() {
        return extendTemplate;
    }

    /**
     * 设置extendTemplate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ThemeExtendTemplateType }
     *     
     */
    public void setExtendTemplate(ThemeExtendTemplateType value) {
        this.extendTemplate = value;
    }

    /**
     * 获取image属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置image属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
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
     * 获取useSite属性的值。
     * 
     */
    public boolean isUseSite() {
        return useSite;
    }

    /**
     * 设置useSite属性的值。
     * 
     */
    public void setUseSite(boolean value) {
        this.useSite = value;
    }

    /**
     * 获取useAdmin属性的值。
     * 
     */
    public boolean isUseAdmin() {
        return useAdmin;
    }

    /**
     * 设置useAdmin属性的值。
     * 
     */
    public void setUseAdmin(boolean value) {
        this.useAdmin = value;
    }

}
