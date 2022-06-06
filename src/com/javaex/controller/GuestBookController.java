package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("addlist".equals(action)) {
			
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestBookVo> gList = guestbookDao.guestbookList();
			
			request.setAttribute("gList", gList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addlist.jsp");
			
		}else if("add".equals(action)) {
			System.out.println("add");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestBookVo guestbookVo = new GuestBookVo(name, password, content);
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.insert(guestbookVo);
			
			WebUtil.redirect(request, response, "/mysite2/gbc?action=addlist");
		}else if("deleteForm".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			System.out.println("지워");
		}else if ("delete".equals(action)) {
			System.out.println("안오냐");
			int no = Integer.parseInt(request.getParameter("no")) ;
			String password = request.getParameter("password");
			
			GuestbookDao guestbookDao = new GuestbookDao();
			GuestBookVo guestbookVo = guestbookDao.getGuest(no);
			
			if(guestbookVo.getPassword().equals(password)) {
				
				int count = guestbookDao.delete(guestbookVo);
				
				WebUtil.redirect(request, response, "/mysite2/gbc?action=addlist");
				System.out.println(count);
			}else {
				WebUtil.redirect(request, response, "/mysite2/gbc?action=addlist");
				System.out.println("패스워드를 잘못입력하였습니다.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
