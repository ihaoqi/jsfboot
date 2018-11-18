package com.easyeip.jsfboot.webfile.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.utils.DateUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

/**
 * 资源浏览servlet，如/jres/images/logo.png
 * @author liao
 *
 */
public class WebfileResourceServlet extends GenericServlet {

	public static final String JRESOURCE_SERVLET = "/jresource";
	private static Map<String, String> FileContentTypes; 
	
	private static final long serialVersionUID = 1L;
	private String tempDir;
	
	static{
		FileContentTypes = new HashMap<String, String>();
		FileContentTypes.put(".ico", "image/x-icon");
		FileContentTypes.put(".bmp", "image/bmp");
		FileContentTypes.put(".png", "image/png");
		FileContentTypes.put(".gif", "image/gif");
		FileContentTypes.put(".jpeg", "image/jpeg");
		FileContentTypes.put(".jpg", "image/jpeg");
		FileContentTypes.put(".html", "text/html");
		FileContentTypes.put(".htm", "text/html");
		FileContentTypes.put(".txt", "text/plain");
		FileContentTypes.put(".xml", "text/xml");
		FileContentTypes.put(".json", "application/json");
		FileContentTypes.put(".pdf", "application/pdf");
		FileContentTypes.put(".rtf", "application/rtf");
		FileContentTypes.put(".doc", "application/msword");
		FileContentTypes.put(".docx", "application/msword");
		FileContentTypes.put(".xls", "application/vnd.ms-excel");
		FileContentTypes.put(".xlsx", "application/vnd.ms-excel");
		FileContentTypes.put(".ppt", "application/vnd.ms-powerpoint");
		FileContentTypes.put(".pptx", "application/vnd.ms-powerpoint");
		FileContentTypes.put(".vsd", "application/vnd.visio");
		FileContentTypes.put(".vsdx", "application/vnd.visio");
		FileContentTypes.put(".zip", "application/zip");
		FileContentTypes.put(".rar", "application/x-rar-compressed");
		FileContentTypes.put(".mpeg", "video/mpeg");
		FileContentTypes.put(".mpg", "video/mpeg");
		FileContentTypes.put(".mp3", "audio/x-mpeg");
		FileContentTypes.put(".mp4", "video/mp4");
		FileContentTypes.put(".3gp", "video/3gpp");
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		tempDir = System.getProperty("java.io.tmpdir");
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// 取出路径
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String reqPath = req.getPathInfo();
		
		WebFileResource res = findResource (reqPath);
		if (res == null){
			return;
		}
		
		outputResource (res, tempDir, req, resp);

		return;
	}
	
	/**
	 * 查询指定路径的资源
	 * @param path
	 * @return
	 */
	public static WebFileResource findResource (String path){
		
		WebfileResourceService service = (WebfileResourceService) JsfbootContext.getService(WebfileResourceService.class);
		WebFileEntry entry = service.findResource(path, false);
		if (entry != null)
			return entry.asResource();
		return null;
	}
	
	public static void outputResource (WebFileResource resource, String tempDir,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqPath = request.getPathInfo();
		// 生成临时文件名
		String tmpFile = tempDir + "/"  + 
				JsfbootContext.getDriver().getSiteOptions().getAppVersion().getProductName() + reqPath;
		
		boolean isProudctMode = JsfbootContext.getDriver().getRuntimeInfo().isProductionMode();
		
		// 取得扩展名并设置输出类型
		int extIdx = resource.getFileName().lastIndexOf(".");
		String types = null;
		if (extIdx > 0){
			String extName = resource.getFileName().substring(extIdx).toLowerCase();
			types = FileContentTypes.get(extName);
		}
		if (types != null){
			response.setContentType(types + ";charset=utf-8");
		}else{
			// 设置成二进制文件
			response.setContentType("application/octet-stream");
		}
		
		// 先把文件缓存到磁盘
		File fileObj = new File(tmpFile);
		boolean fileUpdate = true;
		if (fileObj.exists()){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(fileObj.lastModified());
			String tmpCreate = DateUtils.dateToStr(cal.getTime());
			String resModify = DateUtils.dateToStr(resource.getModifyTime());
			if (StringKit.equals(tmpCreate, resModify)){
				// 文件不用更新
				fileUpdate = false;
			}
		}
		
		// 判断文件是否要更新
		if (fileUpdate == true){
			fileObj.mkdirs();
			fileObj.delete();
			FileOutputStream fos = new FileOutputStream(fileObj);
			resource.load(fos);
			fos.close();
			fileObj.setLastModified(resource.getModifyTime().getTime());
		}
		
		// 设置输出流长度
		long fileSize = fileObj.length();
		response.setContentLength((int) fileSize);
		
		// 返回文件
		handleCache (response, isProudctMode);
		outputFile (response, fileObj);
	}
	
	/**
	 * 输出文件到流
	 * @param resp 输出流
	 * @param file 实体文件
	 * @throws IOException
	 */
	private static void outputFile (HttpServletResponse resp, File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024*4];
		int readLen = buffer.length;
		while (readLen == buffer.length){
			readLen = fis.read(buffer);
			resp.getOutputStream().write(buffer, 0, readLen);
		}
		fis.close();
	}
	
	private static void handleCache(HttpServletResponse resp, boolean cache)
	  {
	    if (cache) {
	      DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	      httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	      Calendar calendar = Calendar.getInstance();
	      calendar.add(Calendar.YEAR, 1);
	      resp.setHeader("Cache-Control", "max-age=29030400");
	      resp.setHeader("Expires", httpDateFormat.format(calendar.getTime()));
	    }
	    else {
	    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    	resp.setHeader("Pragma", "no-cache");
	    	resp.setHeader("Expires", "Mon, 8 Aug 1980 10:00:00 GMT");
	    }
	  }
}
