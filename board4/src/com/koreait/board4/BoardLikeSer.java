package com.koreait.board4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.dao.BoardLikeDAO;
import com.koreait.board4.vo.BoardLikeVO;
import com.koreait.board4.vo.UserVO;

@WebServlet("/boardLike") //404에러는 서블릿 확인. 
public class BoardLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_board = Integer.parseInt(request.getParameter("i_board"));
		int isLike = Integer.parseInt(request.getParameter("isLike")); //기존에 좋아요 했었는지 확인 
	
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO) hs.getAttribute("loginUser");
		
		BoardLikeVO param = new BoardLikeVO();
		param.setI_board(i_board);
		param.setI_user(loginUser.getI_user());
	
		int result = 0;
		if(isLike ==0) { //좋아요 enable
			result = BoardLikeDAO.enableLike(param);
		}else { //disable
			result = BoardLikeDAO.disableLike(param);
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String res = String.format("{\"result\": %d}", result);
		out.print(res);
		out.flush();
	}
}
