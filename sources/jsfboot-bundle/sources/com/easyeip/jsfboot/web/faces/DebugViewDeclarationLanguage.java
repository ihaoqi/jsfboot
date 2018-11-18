package com.easyeip.jsfboot.web.faces;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageWrapper;
import javax.faces.view.ViewMetadata;

public class DebugViewDeclarationLanguage extends ViewDeclarationLanguageWrapper {

	private ViewDeclarationLanguage vdl;
	
	public DebugViewDeclarationLanguage(ViewDeclarationLanguage vdl){
		this.vdl = vdl;
	}

	@Override
	public ViewDeclarationLanguage getWrapped() {
		return vdl;
	}
	
	@Override
	public ViewMetadata getViewMetadata(FacesContext context, String viewId) {
		return super.getViewMetadata(context, viewId);
	}


}
