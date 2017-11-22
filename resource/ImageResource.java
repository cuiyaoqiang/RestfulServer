package resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@Path("/images")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImageResource {
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://imageFile";

	@Context
	HttpServletRequest request;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadStatePolicy() {
		System.out.println("llllllllllll"+request);
		try {
			String fileName = saveFile(request);
			if (!fileName.equals("")) {
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String saveFile(HttpServletRequest request) throws IOException {

		//定义合法后缀名的数组
		String[] allowedExtName=new String[]
				{"zip","rar",//压缩文件
						"txt","doc","wps","docx","java",//文本
						"xls","xlsx",//表格
						"ppt","pptx",//幻灯片
						"pdf",//pdf
						"jpg","jpeg","bmp","gif","png"//图片
				};
		boolean isAllowed=false;
		String upFileName="";

		//读取上传路径
		URL url=getClass().getClassLoader().getResource("");
		String u=url.getPath();
		System.out.println(u);

		String error="";

		//创建工厂对象和文件上传对象
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);

		//上传路径
		//String path = request.getSession().getServletContext().getRealPath("/upload");

		String[] paths = SERVER_UPLOAD_LOCATION_FOLDER.split(File.separator+File.separator);
		StringBuffer fullPath = new StringBuffer();
		for (int i = 0; i < paths.length; i++) {
			fullPath.append(paths[i]);
			File file = new File(fullPath.toString());
			if(!file.exists()&&!file.isDirectory()){
				file.mkdir();
			}
			fullPath.append(File.separator);
		}

		String requestPath = SERVER_UPLOAD_LOCATION_FOLDER;
		File dirFile =new File(SERVER_UPLOAD_LOCATION_FOLDER);
		//如果文件夹不存在则创建    
		if  (!dirFile .exists()  && !dirFile .isDirectory()){ 
			dirFile .mkdir();
		}
		try {
			//解析上传请求
			List<FileItem> items=upload.parseRequest(request);
			System.out.println(items.size());
			Iterator<FileItem> itr=items.iterator();
			while(itr.hasNext()){
				FileItem item=(FileItem)itr.next();

				//判断是否为文件域
				if(!item.isFormField()){
					if(item.getName()!=null&&!item.getName().equals("")){

						//获取上传文件大小和文件名称
						long upFileSize=item.getSize();   
						String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1, item.getName().length());

						//获取文件后缀名
						String[] splitName=fileName.split("\\.");
						String extName=splitName[splitName.length-1];

						//检查文件后缀名
						for(String allowed:allowedExtName) {
							if(allowed.equalsIgnoreCase(extName)) {
								isAllowed=true;
							}
						}
						if(!isAllowed){
							error="上传文件格式不合法！";
							break;
						}

						//给上传的文件设一个最大值，这里是不得超过100MB
						int maxSize=100*1024*1024;
						if(upFileSize>maxSize){
							error="您上传的文件太大了，请选择不超过100MB的文件!";
							break;
						}

						//此时文件暂存在服务器的内存中，构造临时对象
						File tempFile=new File(UUID.randomUUID().toString() + "$" + fileName);

						//指定文件上传服务器的目录及文件名称
						File file=new File(SERVER_UPLOAD_LOCATION_FOLDER+"/",tempFile.getName());

						item.write(file);//写文件方法
						upFileName=requestPath+"/"+tempFile.getName();
						if(upFileName.equals("")){
							error="没选择上传文件！";
						}
					}else{
						error="没选择上传文件！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!error.equals("")){ 
			return "false";
		}else{  
			System.out.println("OK   "+upFileName);
		}
		return "true";
	}
}