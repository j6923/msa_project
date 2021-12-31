package com.my.upload;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadServlet
 */
public class CpUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//enctype 에 맞게? 스트림타입으로 얻어와야한다. request로 하면 t(요청전달데이터)만 얻고 f(파일)는 못얻어 
//		InputStream is = request.getInputStream();
//		Scanner sc = new Scanner(is);
//		while(sc.hasNextLine()) {
//		String line = sc.nextLine();
//		System.out.println(line);
//		
//		}
//		String saveDirectory = "d:\files";
//		MultipartRequest mr = new MultipartRequest(request, saveDirectory);
		DiskFileItemFactory fileItemFactory;
		fileItemFactory = new DiskFileItemFactory();
		String saveDirectory = "d:\\postfiles";
		File f = new File(saveDirectory);
		fileItemFactory.setRepository(f);
		ServletFileUpload fileUpload = new ServletFileUpload();
		//fileUpload.parseRequest(request);
		
		
	}

}