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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ServiceConfType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ServiceConfType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="service-param" type="{http://jsfboot.easyeip.com/jsfboot-configure}ServiceParamType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceConfType", propOrder = {
    "serviceParam"
})
public class ServiceConfType {

    @XmlElement(name = "service-param")
    protected List<ServiceParamType> serviceParam;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the serviceParam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceParam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceParamType }
     * 
     * 
     */
    public List<ServiceParamType> getServiceParam() {
        if (serviceParam == null) {
            serviceParam = new ArrayList<ServiceParamType>();
        }
        return this.serviceParam;
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

}
