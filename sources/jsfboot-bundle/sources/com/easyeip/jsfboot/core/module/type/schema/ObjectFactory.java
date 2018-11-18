//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.08.26 时间 12:05:31 PM CST 
//


package com.easyeip.jsfboot.core.module.type.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.easyeip.jsfboot.core.module.type.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _JsfbootModule_QNAME = new QName("http://jsfboot.easyeip.com/jsfboot-module", "jsfboot-module");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyeip.jsfboot.core.module.type.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JsfbootModuleType }
     * 
     */
    public JsfbootModuleType createJsfbootModuleType() {
        return new JsfbootModuleType();
    }

    /**
     * Create an instance of {@link ThemePageTemplateType }
     * 
     */
    public ThemePageTemplateType createThemePageTemplateType() {
        return new ThemePageTemplateType();
    }

    /**
     * Create an instance of {@link PageActionType }
     * 
     */
    public PageActionType createPageActionType() {
        return new PageActionType();
    }

    /**
     * Create an instance of {@link ModuleVersionType }
     * 
     */
    public ModuleVersionType createModuleVersionType() {
        return new ModuleVersionType();
    }

    /**
     * Create an instance of {@link ServiceOptionType }
     * 
     */
    public ServiceOptionType createServiceOptionType() {
        return new ServiceOptionType();
    }

    /**
     * Create an instance of {@link UserMenuType }
     * 
     */
    public UserMenuType createUserMenuType() {
        return new UserMenuType();
    }

    /**
     * Create an instance of {@link MenuPositionType }
     * 
     */
    public MenuPositionType createMenuPositionType() {
        return new MenuPositionType();
    }

    /**
     * Create an instance of {@link BackMenuType }
     * 
     */
    public BackMenuType createBackMenuType() {
        return new BackMenuType();
    }

    /**
     * Create an instance of {@link ThemeTemplateItemType }
     * 
     */
    public ThemeTemplateItemType createThemeTemplateItemType() {
        return new ThemeTemplateItemType();
    }

    /**
     * Create an instance of {@link DataSourceType }
     * 
     */
    public DataSourceType createDataSourceType() {
        return new DataSourceType();
    }

    /**
     * Create an instance of {@link AccountRealmType }
     * 
     */
    public AccountRealmType createAccountRealmType() {
        return new AccountRealmType();
    }

    /**
     * Create an instance of {@link MenuPageType }
     * 
     */
    public MenuPageType createMenuPageType() {
        return new MenuPageType();
    }

    /**
     * Create an instance of {@link PositionItemType }
     * 
     */
    public PositionItemType createPositionItemType() {
        return new PositionItemType();
    }

    /**
     * Create an instance of {@link DefineThemeType }
     * 
     */
    public DefineThemeType createDefineThemeType() {
        return new DefineThemeType();
    }

    /**
     * Create an instance of {@link ThemeExtendTemplateType }
     * 
     */
    public ThemeExtendTemplateType createThemeExtendTemplateType() {
        return new ThemeExtendTemplateType();
    }

    /**
     * Create an instance of {@link DefineServiceType }
     * 
     */
    public DefineServiceType createDefineServiceType() {
        return new DefineServiceType();
    }

    /**
     * Create an instance of {@link PageResourceType }
     * 
     */
    public PageResourceType createPageResourceType() {
        return new PageResourceType();
    }

    /**
     * Create an instance of {@link ServiceItemType }
     * 
     */
    public ServiceItemType createServiceItemType() {
        return new ServiceItemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsfbootModuleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jsfboot.easyeip.com/jsfboot-module", name = "jsfboot-module")
    public JAXBElement<JsfbootModuleType> createJsfbootModule(JsfbootModuleType value) {
        return new JAXBElement<JsfbootModuleType>(_JsfbootModule_QNAME, JsfbootModuleType.class, null, value);
    }

}
