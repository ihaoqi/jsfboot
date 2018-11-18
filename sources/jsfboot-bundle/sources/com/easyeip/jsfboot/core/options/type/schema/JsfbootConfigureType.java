//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:30 PM CST 
//


package com.easyeip.jsfboot.core.options.type.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>JsfbootConfigureType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="JsfbootConfigureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="app-version" type="{http://jsfboot.easyeip.com/jsfboot-configure}AppVersionType"/>
 *         &lt;element name="app-options" type="{http://jsfboot.easyeip.com/jsfboot-configure}AppOptionsType"/>
 *         &lt;element name="service-conf" type="{http://jsfboot.easyeip.com/jsfboot-configure}ServiceConfType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JsfbootConfigureType", propOrder = {
    "appVersion",
    "appOptions",
    "serviceConf"
})
public class JsfbootConfigureType {

    @XmlElement(name = "app-version", required = true)
    protected AppVersionType appVersion;
    @XmlElement(name = "app-options", required = true)
    protected AppOptionsType appOptions;
    @XmlElement(name = "service-conf")
    protected List<ServiceConfType> serviceConf;

    /**
     * 获取appVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AppVersionType }
     *     
     */
    public AppVersionType getAppVersion() {
        return appVersion;
    }

    /**
     * 设置appVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AppVersionType }
     *     
     */
    public void setAppVersion(AppVersionType value) {
        this.appVersion = value;
    }

    /**
     * 获取appOptions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AppOptionsType }
     *     
     */
    public AppOptionsType getAppOptions() {
        return appOptions;
    }

    /**
     * 设置appOptions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AppOptionsType }
     *     
     */
    public void setAppOptions(AppOptionsType value) {
        this.appOptions = value;
    }

    /**
     * Gets the value of the serviceConf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceConf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceConf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceConfType }
     * 
     * 
     */
    public List<ServiceConfType> getServiceConf() {
        if (serviceConf == null) {
            serviceConf = new ArrayList<ServiceConfType>();
        }
        return this.serviceConf;
    }

}
