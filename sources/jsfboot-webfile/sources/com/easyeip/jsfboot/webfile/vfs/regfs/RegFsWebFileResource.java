package com.easyeip.jsfboot.webfile.vfs.regfs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public class RegFsWebFileResource extends AbstractRegFsWebFile implements WebFileResource {
	
	static final String ROWNAME_PREFIX = "b64_";

	public RegFsWebFileResource(RegistryFileSystem rfs, RegistryPath filePath, WebFileFolder parent) {
		super(rfs, filePath, parent);
	}

	@Override
	public long getFileSize() {
		return StringKit.toInteger(getRegistryItem().getValue("fileSize"), 0);
	}

	@Override
	public String getFileType() {
		// 取得文件扩展名，如png，不包括小数点
		String fileName = this.getFileName();
		int lastIdx = fileName.lastIndexOf(".");
		if (lastIdx >= 0){
			return fileName.substring(lastIdx + 1);
		}
		return "";
	}

	@Override
	public int load(OutputStream os) throws IOException {
		return load(os, -1);
	}

	@Override
	public int load(OutputStream os, int size) throws IOException {

		RegistryPath dataPath = getRegistryPath().makeChild("data");
		RegistryItem dataItem = rfs.getRegistry().getItem(dataPath);
		if (dataItem == null || size == 0)
			return 0;
		
		// 对行索引名排序
		List<Integer> rowNames = new ArrayList<Integer> ();
		StringBuilder b64Val = new StringBuilder();
		for (String rowName : dataItem.valueNames()){
			if (StringKit.isEmpty(rowName) || !rowName.startsWith(ROWNAME_PREFIX))
				continue;
			String rowIndex = rowName.substring(ROWNAME_PREFIX.length());
			rowNames.add(Integer.valueOf(rowIndex));
		}
		Collections.sort(rowNames);
		
		// 还原值
		for (Integer rowIndex : rowNames){
			String name = ROWNAME_PREFIX + rowIndex.toString();
			String value = dataItem.getValue(name);
			if (StringKit.notEmpty(value)){
				b64Val.append(value);
			}
		}
		byte[] result = ResourceEncodeUtils.decodeResource(b64Val);
		b64Val = null;
		rowNames.clear();
		
		// 写入流
		if (size < 0){
			int length = result.length;
			os.write(result);
			result = null;
			return length;
		}else{
			os.write(result, 0, size);
			result = null;
			return size;
		}
	}

	@Override
	public int save(byte[] data) throws IOException {
		return save(data, -1);
	}

	@Override
	public int save(byte[] data, int size) throws IOException {

		int lineLen = rfs.getRegistry().getProfile().getMaxValueLength();
		byte[] newdata = data;
		if (size >= 0 && size < data.length){
			newdata = new byte[0];
			System.arraycopy(data, 0, newdata, 0, size);
		}
		List<String> b64Datas = ResourceEncodeUtils.encodeResource(newdata, lineLen);
		
		// 先取得文件原来的内容行
		RegistryPath dataPath = getRegistryPath().makeChild("data");
		try {
			RegistryItem dataItem = rfs.getRegistry().createItem(dataPath);
			
			List<String> oldDataRows = dataItem.valueNames();
			for (int i = 0; i < b64Datas.size(); i ++){
				String valName = ROWNAME_PREFIX + Integer.valueOf(i).toString();
				if (oldDataRows.contains(valName)){
					oldDataRows.remove(valName);
				}
				dataItem.setValue(valName, b64Datas.get(i));
			}
			
			// 删除不需要的行
			for (String rowName : oldDataRows){
				dataItem.removeValue(rowName);
			}
			oldDataRows.clear();
			
			// 保存
			rfs.getRegistry().updateItem(dataItem);
			
			// 更新字节数
			getRegistryItem().setValue("fileSize", newdata.length);
			rfs.getRegistry().updateItem(getRegistryItem());
			
		} catch (RegistryException e) {
			throw new IOException (e.getMessage());
		}

		return newdata.length;
	}

}
