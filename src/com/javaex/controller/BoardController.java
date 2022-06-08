package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/bdc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> bList = boardDao.boardList();
			
			request.setAttribute("bList", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			System.out.println("list");
		}else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no")) ;
			
			System.out.println("dele");
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getUser(no);
			
			int count = boardDao.delete(boardVo);
			request.setAttribute("boardVo", boardVo);
			WebUtil.redirect(request, response, "/mysite2/bdc?action=list");
			System.out.println( count+" 건이 삭제 되었습니다.");
			
			
		}else if("writeForm".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("write".equals(action)) {
			System.out.println("오냐");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = Integer.parseInt(request.getParameter("userNo")) ;
			
			BoardVo boardVo = new BoardVo(title, content, userNo);
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/bdc?action=list");
		}else if("read".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("no")) ;
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getUser(no);
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("modifyForm".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("no")) ;
			BoardDao  boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getUser(no);
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}else if("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no")) ;
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(no);
			System.out.println(title);
			System.out.println(content);
			
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getUser(no);
			int count = boardDao.update(boardVo);
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/bdc?action=list");
			System.out.println( count+" 건이 수정 되었습니다.");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
