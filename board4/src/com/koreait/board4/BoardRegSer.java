package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.dao.BoardDAO;
import com.koreait.board4.vo.*;

@WebServlet("/boardReg")
public class BoardRegSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//등록
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsp = "/WEB-INF/jsp/boardRegmod.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);		
	}
	
	//처리(reg, mod)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트로 부터 파라미터값 받아오기 
		String strI_board = request.getParameter("i_board");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		//콘솔 확인용 
		System.out.println("i_board : " + strI_board);
		System.out.println("title : " + title);
		System.out.println("ctnt : " + ctnt);
		
		//세션얻기 
		HttpSession hs = request.getSession();
		
		//세션의 loginUser 정보 받아와서 UserVO에 저장 
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		
		//BoardVO에 title, ctnt, i_user 값 셋팅 
		//i_user값은 세션의 I_user값으로 셋팅 
		BoardVO param = new BoardVO();
		param.setTitle(title);
		param.setCtnt(ctnt);
		param.setI_user(loginUser.getI_user());
		
		//등록 (파라미터로 받아온 strI_board 값이 아무것도 없으면 BoardDAO의 regBoard(BoardVO param) 호출해서 글쓰기 
		if("".equals(strI_board)) { 
			int i_board = BoardDAO.regBoard(param);
			//DB작업후 작성한글 Detail로 이동 
			response.sendRedirect("/boardDetail?i_board=" + i_board);
			//새로운 글쓰기가 실행되면 수정은 실행되지 않으므로 return 으로 메서드 종료 
			return;
		} 
		//위의 if("".equals(strI_board)) 가 실행되지 않으면 수정이 실행되어야 함. 
		//파라미터로 받아온 strI_board 값을 integer형으로 parsing 해서 UserVO 의 i_board에 셋팅해줌 
		int i_board = Integer.parseInt(strI_board);
		param.setI_board(i_board);
		//DB작업 실행 수정작업실행 될꺼임. 
		//성공 실패를 int 형으로 받아와서 result에 저장 . 
		int result = BoardDAO.modBoard(param);
		String qStr = "";
		//result가 0이면 에러메세지 셋팅 
		if(result == 0) {
			qStr = "&err=2";
		}
		//BoardDetail서블릿으로 간다. 
		String url = String.format("/boardDetail?i_board=%d%s", i_board, qStr);
		response.sendRedirect(url);

	}

}
