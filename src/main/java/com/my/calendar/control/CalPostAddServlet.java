package com.my.calendar.control;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalPost;
import com.my.customer.vo.Customer;
import com.my.calendar.vo.CalInfo;
//import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class CalPostAddServlet
 * @param <CalPost>
 * 
 */
@WebServlet("/CalPostAdd") //서블릿url 경로
@MultipartConfig (
		maxFileSize=1024*1024*3, //3mb,최대파일크기
		location="d:\\files") // 파일저장위치

public class CalPostAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();

	
	/**get요청에 대한 응답을 주는 메소드*/
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");//로그인 세션에 저장된 loginInfo 불러오기	
    	
		CalInfo calinfo = new CalInfo();
		calinfo.setCustomer(c);
		
	    String path = "";
        String saveDirectory = "d:\\files"; //calpostwrite에서 등록한 파일이 저장될 경로
		
	    //모든 서블릿이 사용할 수 있도록 ServletContet 인스턴스에 저장
	    ServletContext sc = getServletContext();
	  
	    System.out.println("in CalPostAddservlet saveDirectory:" + saveDirectory);
	 // getParts()->업로드 데이터에 접근을 통해 Body에 넘어온 데이터들을 각각의 Part로 쪼개어 리턴
	    Collection<Part> parts = request.getParts(); 
		System.out.println("in CalPostAddServlet parts.size()=" + parts.size());	
		String extension = null; //확장자
		Part part = null;
		for(Part p: parts) {
			if("calMainImg".equals(p.getName())) { //name이 calMainImg인 경우
				String fileName = p.getSubmittedFileName(); //실제 업로드된 파일이름
	 // 확장자 얻기 - lastIndexOf 마침표가 있는 마지막 위치를 찾은뒤 확장자를 얻어온다
				int extensionIndex = fileName.lastIndexOf('.'); 
				if(extensionIndex != -1) { //확장자가 존재하면					
					extension = fileName.substring(extensionIndex+1); //확장자 
					System.out.println("확장자:" + extension);
					part = p;
					break;					
				}
			}
		}
		if(part == null) {
			request.setAttribute("msg", "첨부할 파일이 없거나 확장자가 없습니다");
			path="failresult.jsp";
	// 폼값 받기
		}else {
			String calDate = request.getParameter("calDate");
			String calMemo = request.getParameter("calMemo");
			String calMainImg =extension;
			CalPost cp1 = new CalPost();
			cp1.setCalinfo(calinfo);
			cp1.setCalMemo(calMemo);
			cp1.setCalDate(calDate);
			cp1.setCalMainImg(calMainImg);
			
		
	   
		try {
			CalPost calpost= service.addCalPost(cp1);
			System.out.println(calpost);
			
			String saveFileName = "cal_"+ calinfo.getCustomer().getUIdx() + "_" + calinfo.getCalIdx() + "_" + calDate+ "." + extension; //파일이름("선택날짜.확장자")
			part.write(saveDirectory+"\\" + saveFileName); //파일 저장하기 
			
			List<CalPost> list = service.findCalsByDate(calinfo,calDate);
			request.setAttribute("list", list);
			path="calpostlistresult.jsp";
		
		} catch (AddException | FindException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();   
            request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		}
		
		}	
	
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}

	
