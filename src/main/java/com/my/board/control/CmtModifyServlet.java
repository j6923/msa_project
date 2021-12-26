package com.my.board.control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.board.service.BoardService;
import com.my.board.vo.Comment;
import com.my.notice.vo.Notice;

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
		
	}

}