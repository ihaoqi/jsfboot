package com.easyeip.jsfboot.core.module.type;

//*       &lt;all>
//*         &lt;element name="module-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
//*         &lt;element name="module-version" type="{http://www.w3.org/2001/XMLSchema}string"/>
//*         &lt;element name="module-title" type="{http://www.w3.org/2001/XMLSchema}string"/>
//*         &lt;element name="module-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
//*         &lt;element name="company-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
//*         &lt;element name="company-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
//*       &lt;/all>

public interface ModuleVersion {

	String getModuleName();
	
	String getModuleVersion();
	
	String getModuleTitle();
	
	String getModuleDescription();
	
	String getCompanyName();
	
	String getCompanyDescription();
}
