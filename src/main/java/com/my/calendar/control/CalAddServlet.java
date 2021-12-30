package com.my.calendar.control;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
import com.my.customer.vo.Customer;
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
 * Servlet implementation class CalAddServlet
 */
@WebServlet("/caladd")
@MultipartConfig
public class CalAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CalendarService service = CalendarService.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");		
		int uIdx = c.getUIdx();
		
		String path = "";
		/*--파일 업로드가능한 파일(확장자가 존재)인가 확인 작업 --*/		
		String saveDirectory = "d:\\files"; //파일이 저장될 경로 
		
		//이미지가 저장될 경로가 현재배포된 프로젝트내부의 경로로 설정되면
		//프로젝트가 리로드(코드변경,새로고침 )될때마다 배포된 기존프로젝트는 제거되었다가 이클립스폴더내용이 붙여넣기 되는것이다.
		ServletContext sc = getServletContext();
//		String saveDirectory = sc.getRealPath("images\\calimages");
//		String saveDirectory = "D:\\230\\myWeb\\msa_project\\src\\main\\webapp\\images\\calimages"; //"D:\\files"
		System.out.println("in CalAddservlet saveDirectory:" + saveDirectory);
		
		Collection<Part> parts = request.getParts(); //업로드된 파일들 얻기 
		System.out.println("in CallAddServlet parts.size()=" + parts.size());
		String extension = null; //확장자
		Part part = null;
		for(Part p: parts) {
			if("calThumbnail".equals(p.getName())) { //name이 calThumbnail인 파일인 경우 
				String fileName = p.getSubmittedFileName(); //실제 업로드된 파일이름
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
		}else {
			String calCategory = request.getParameter("calCategory");
			String calThumbnail =extension; //request.getParameter("calThumbnail");
			
			CalInfo ci = new CalInfo();
			ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
			ci.setCalCategory(calCategory);
			ci.setCalThumbnail(calThumbnail);
		
			try {
				CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
				System.out.println(calinfo);
				
				/*--파일 업로드작업 --*/
				String saveFileName = "cal_post_" + uIdx + "_" + calinfo.getCalIdx() + "." + extension;
				part.write(saveDirectory+"\\" + saveFileName); //파일 저장하기 
				
				//request.setAttribute("ci", calinfo);
				List<CalInfo> list = service.findCalsByUIdx(uIdx);	
				request.setAttribute("list", list);
				path="callistresult.jsp";
			} catch (AddException | FindException e ) {
				System.out.println(e.getMessage());
				//resultmsg = "캘린더 생성 실패:" + e.getMessage();
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());
				path="failresult.jsp";
			}
		}		
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
			
			
	}

}
