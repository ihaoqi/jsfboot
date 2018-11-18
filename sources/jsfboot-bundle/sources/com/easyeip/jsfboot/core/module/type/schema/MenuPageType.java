//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:31 PM CST 
//


package com.easyeip.jsfboot.core.module.type.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MenuPageType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MenuPageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="menu-item" type="{http://jsfboot.easyeip.com/jsfboot-module}MenuPageType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="page-action" type="{http://jsfboot.easyeip.com/jsfboot-module}PageActionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="public-pages" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="title" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="icon" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="outcome" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="explain" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MenuPageType", propOrder = {
    "menuItem",
    "pageAction",
    "publicPages"
})
public class MenuPageType {

    @XmlElement(name = "menu-item")
    protected List<MenuPageType> menuItem;
    @XmlElement(name = "page-action")
    protected List<PageActionType> pageAction;
    @XmlElement(name = "public-pages")
    protected String publicPages;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "title", required = true)
    protected String title;
    @XmlAttribute(name = "icon")
    protected String icon;
    @XmlAttribute(name = "outcome")
    protected String outcome;
    @XmlAttribute(name = "explain")
    protected String explain;

    /**
     * Gets the value of the menuItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuPageType }
     * 
     * 
     */
    public List<MenuPageType> getMenuItem() {
        if (menuItem == null) {
            menuItem = new ArrayList<MenuPageType>();
        }
        return this.menuItem;
    }

    /**
     * Gets the value of the pageAction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageAction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageActionType }
     * 
     * 
     */
    public List<PageActionType> getPageAction() {
        if (pageAction == null) {
            pageAction = new ArrayList<PageActionType>();
        }
        return this.pageAction;
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

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取title属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * 获取icon属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置icon属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcon(String value) {
        this.icon = value;
    }

    /**
     * 获取outcome属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * 设置outcome属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutcome(String value) {
        this.outcome = value;
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

}
