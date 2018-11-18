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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>UserMenuType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="UserMenuType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="use-service" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menu-item" type="{http://jsfboot.easyeip.com/jsfboot-module}MenuPageType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserMenuType", propOrder = {
    "useService",
    "menuItem"
})
public class UserMenuType {

    @XmlElement(name = "use-service")
    protected String useService;
    @XmlElement(name = "menu-item")
    protected List<MenuPageType> menuItem;

    /**
     * 获取useService属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseService() {
        return useService;
    }

    /**
     * 设置useService属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseService(String value) {
        this.useService = value;
    }

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

}
