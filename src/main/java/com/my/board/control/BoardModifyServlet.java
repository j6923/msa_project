package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
import com.my.exception.ModifyException;
import com.my.notice.vo.Notice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardModifyServlet
 */
@WebServlet("/brdmodify")
public class BoardModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();

   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String brdIdx=request.getParameter("brdIdx");
		int intBrdIdx = Integer.parseInt(brdIdx);
		String brdType = request.getParameter("brdType");
		int intBrdType = Integer.parseInt(brdType);
		String brdTitle=request.getParameter("brdTitle");
		String brdContent=request.getParameter("brdContent");
		String brdAttachment=request.getParameter("brdAttachment");

		Board b = new Board();
		b.setBrdIdx(intBrdIdx);
		b.setBrdTitle(brdTitle);
		b.setBrdContent(brdContent);
		b.setBrdAttachment(brdAttachment);
		b.setBrdIdx(intBrdType);
		
		String path="";
		String resultmsg="";
		try {
			Board  board= service.modifyBrd(b);
			System.out.println("글 수정 성공");
			
			resultmsg="수정 성공";
			request.setAttribute("b", board);
			request.setAttribute("status", 1);
			path="boarddetailresult.jsp";
		} catch (ModifyException e) {
			System.out.println(e.getMessage());
			resultmsg="수정 실패";
			request.setAttribute("status", 0);
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		}
		
		request.setAttribute("msg", resultmsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
		
	}

}