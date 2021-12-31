package com.my.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class CpDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파일이름 요청전달데이터 얻기
		String fileName = request.getParameter("filename");
		System.out.println("요청전달데이터 파일이름:" + fileName);
		//다운로드할 파일의 실제 경로 얻기
		String saveDirectory = "d:\\postfiles";
		Path path = Paths.get(saveDirectory, fileName);
		String contentType = Files.probeContentType(path);
		System.out.println(fileName+"파일의 contentType:" + contentType);
		File f = path.toFile();
		if(contentType.contains("image/")) {
			response.setContentType(contentType);
			response.setHeader("Content-Length", String.valueOf(f.length()));
			response.setHeader("Content-Disposition", "inline; filename="+URLEncoder.encode(fileName, "UTF-8"));
		}else {
			//응답형식 : application/octet-stream(8비트 단위의 binary data)
			response.setContentType("application/octet-stream;charset=UTF-8");

			//다운로드시 파일이름 결정
			//response.setHeader("Content-Disposition", "attachment;filename=" + name);
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		}

			//응답출력스트림
			//PrintWriter out = response.getWriter(); (X) --문자형태로 응답출력
			ServletOutputStream sos = response.getOutputStream(); //--바이트형태로 파일을 출력

			FileInputStream fis = null;
			fis = new FileInputStream(f);
			IOUtils.copy(fis, sos); //파일복사붙여넣기
			fis.close();
			sos.close();
		}
}