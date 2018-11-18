package com.easyeip.jsfboot.webfile.vfs;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWebFileSystem implements WebFileSystem {

	public static final String CREATE_NOTIFY = "append";
	public static final String REMOVE_NOTIFY = "remove";
	public static final String RENAME_NOTIFY = "rename";
	
	private List<WebFileListener> listenerList;
	
	public AbstractWebFileSystem(){
		listenerList = new ArrayList<WebFileListener>();
	}
	
	@Override
	public void addFileListener(WebFileListener listener) {
		this.listenerList.add(listener);
	}

	/**
	 * 发送通知
	 * @param entry
	 * @param notify
	 */
	public void sendNotify (WebFileEntry entry, String notify){
		for (WebFileListener wfl : listenerList){
			wfl.webFileNotify(entry, notify);
		}
	}
}
