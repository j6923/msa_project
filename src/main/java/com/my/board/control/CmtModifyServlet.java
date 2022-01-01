package com.my.board.control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

/**
 * Servlet implementation class cmtModifyServlet
 */
@WebServlet("/cmtmodify")
public class CmtModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String brdIdx=request.getParameter("brdIdx");
		int intBrdIdx = Integer.parseInt(brdIdx);
		String cmtIdx=request.getParameter("cmtIdx");
		int intCmtIdx = Integer.parseInt(cmtIdx);
		String cmtContent=request.getParameter("cmtContent");
		Comment comment = new Comment();
		comment.setBrdIdx(intBrdIdx);
		comment.setCmtIdx(intCmtIdx);
		comment.setCmtContent(cmtContent);
		
		String path="";
		String resultmsg="";
		try {
			service.modifyCmt(comment);
			System.out.println("댓글 수정 성공");
			Board b = service.findBrdByIdx(intBrdIdx);
			request.setAttribute("b", b);
			path="boarddetailresult.jsp";
		} catch (ModifyException e) {
			System.out.println(e.getMessage());
			System.out.println("댓글 수정 실패");
			resultmsg="수정 실패";
			request.setAttribute("status", 0);
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("msg", resultmsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}