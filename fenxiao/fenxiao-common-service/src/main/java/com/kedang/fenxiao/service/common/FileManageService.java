package com.kedang.fenxiao.service.common;

import java.io.File;





import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.UUIDTool;

@Component
@Transactional(readOnly = true)
public class FileManageService {
	
	@Autowired
	private ResourcesConfig resourcesConfig;
	private Logger logger=LoggerFactory.getLogger(FileManageService.class);
	
	public String saveLocalFile(HttpServletRequest request,String folder,String uploadFileName){
		String filePath=null;
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
			MultipartFile file=null;
			if(StringUtils.isNotBlank(uploadFileName)){
				file=mRequest.getFile(uploadFileName);
				if(file!=null&&!file.isEmpty()){
					filePath=saveLocalFile(file, folder);
				}
			}
		}
		return filePath;
	}
	
	public String saveLocalFileMany(HttpServletRequest request,String folder){
		String filePath="";
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
			Iterator<String> names=mRequest.getFileNames();
			while(names.hasNext()){
				String name=names.next();
				List<MultipartFile> files= mRequest.getFiles(name);
				for(MultipartFile mf:files){
					if(mf.isEmpty()){
						continue;
					}
					String	temp=saveLocalFile(mf, folder);
					if(StringUtils.isNotBlank(temp)){
						if(StringUtils.isBlank(filePath)){
							filePath+=temp;
						}else{
							filePath+=","+temp;
						}
					}
				}
			}
		}
		return filePath;
	}
	
	public String saveLocalFileManyXj(HttpServletRequest request,String folder){
		String filePath="";
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
			Iterator<String> names=mRequest.getFileNames();
			while(names.hasNext()){
				String name=names.next();
				List<MultipartFile> files= mRequest.getFiles(name);
				for(MultipartFile mf:files){
					if(mf.isEmpty()){
						continue;
					}
					String	temp=saveLocalFile(mf, folder);
					if(StringUtils.isNotBlank(temp)){
						if(StringUtils.isBlank(filePath)){
							filePath+=temp;
						}else{
							filePath+=","+temp;
						}
					}
				}
			}
			if ("".equals(filePath))
			{
				filePath = " , ";
			}else if (!filePath.contains(","))
			{
				filePath+= ", ";
			}
		}
		return filePath;
	}
	public String saveLocalFile(MultipartFile mf,String folder){
		String filePath=null;
		String uploadBase = resourcesConfig.getConfigString(Constant.UPLOAD_BASE);
		//创建保存目录
		String relativePath=File.separator+folder+File.separator;
		File path = new File(uploadBase+relativePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		try {
			String fileName =UUIDTool.getUuid(32)+"." + StringUtils.substringAfterLast(mf.getOriginalFilename(), ".");
			mf.transferTo(new File(uploadBase+relativePath + fileName));
			filePath=StringUtils.replace(relativePath, File.separator, "/")+fileName;
		} catch (Exception e) {
			logger.warn("图片拷贝失败", e);
		} 
		return filePath;
	}
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	public void deleteLocalFile(String filePath){
		if(StringUtils.isNotBlank(filePath)){
			String uploadBase = resourcesConfig.getConfigString(Constant.UPLOAD_BASE);
			String savePath = uploadBase + StringUtils.replace(filePath, "/", File.separator);
			File saveFile = new File(savePath);
			//删除文件
			if (saveFile.exists()) {
				saveFile.delete();
			}
		}
	}

	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	public void deleteManyLocalFile(String filePath)
	{
		if (null != filePath && filePath.length() > 0)
		{
			String[] path = filePath.split(",");
			for (int i = 0; i < path.length; i++)
			{
				if (StringUtils.isNotBlank(path[i]))
				{
					String uploadBase = resourcesConfig.getConfigString(Constant.UPLOAD_BASE);
					String savePath = uploadBase + StringUtils.replace(path[i], "/", File.separator);
					File saveFile = new File(savePath);
					//删除文件
					if (saveFile.exists())
					{
						saveFile.delete();
					}
				}
			}
		}
	}
}
