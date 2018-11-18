package com.easyeip.jsfboot.core.registry.service;

import com.easyeip.jsfboot.core.registry.RegistryProfile;

public class DefaultRegistryProfile implements RegistryProfile {
	
	private int maxNameLen = 0;
	private int maxValueLen = 0;
	private int maxNodeDepth = 0;
	private int maxCommentLen = 0;

	@Override
	public int getMaxNameLength() {
		return maxNameLen;
	}

	@Override
	public int getMaxValueLength() {
		return maxValueLen;
	}
	
	@Override
	public int getMaxNodeDepth() {
		return maxNodeDepth;
	}
	
	@Override
	public int getMaxCommentLength() {
		return maxCommentLen;
	}

	public void setMaxNameLength(int len){
		this.maxNameLen = len;
	}
	
	public void setMaxValueLength(int len){
		this.maxValueLen = len;
	}
	
	public void setMaxCommentLength(int len) {
		this.maxCommentLen = len;
	}

	public void setMaxNodeDepth(int depth) {
		this.maxNodeDepth = depth;
	}
}
