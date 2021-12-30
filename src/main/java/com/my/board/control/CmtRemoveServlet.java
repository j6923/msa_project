package com.my.board.control;

import java.io.IOException;
import java.util.List;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.exception.FindException;
import com.my.exception.RemoveException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CmtRemoveServlet
 */
@WebServlet("/cmtremove")
public class CmtRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path="";
		String resultmsg="";
		String brdIdx = request.getParameter("brdIdx");	
		int intBrdIdx = Integer.parseInt(brdIdx);
		String cmtIdx = request.getParameter("cmtIdx");	
		int intCmtIdx = Integer.parseInt(cmtIdx);
		
		try {
			service.removeCmt(intBrdIdx, intCmtIdx);
			Board b= service.findBrdByIdx(intBrdIdx);

			request.setAttribute("b", b);
			System.out.println("댓글 삭제 성공");
			path="/boarddetailresult.jsp";
		} catch (RemoveException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("resultmsg", resultmsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	
	}

}
