package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String action = request.getParameter("action");
	     
	     if("joinForm".equals(action)) {
	    	 
	    	 WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
	     }else if ("join".equals(action)) {
	    	 
	    	 String id = request.getParameter("id");
	    	 String password = request.getParameter("password");
	    	 String name = request.getParameter("name");
	    	 String gender = request.getParameter("gender");
	    	 
	    	 UserVo userVo = new UserVo(id, password, name, gender);
	    	 
	    	 UserDao userDao = new UserDao();
	    	 userDao.insert(userVo);
	    	 
	    	 WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
	     }else if("loginForm".equals(action)) {
	    	 
	    	 WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
	     }else if("login".equals(action)) {
	    	 String id = request.getParameter("id");
	    	 String password = request.getParameter("password");
	    	 
	    	 UserVo userVo = new UserVo();
	    	 userVo.setId(id);
	    	 userVo.setPassword(password);
	    	 
	    	 UserDao userDao = new UserDao();
	    	  UserVo authUser = userDao.getUser(userVo);
	    	 
	    	  if(authUser == null) {
	    		  System.out.println("로그인 실패");
	    	  }else {
	    		  System.out.println("로그인 성공");
	    		  
	    		 HttpSession session = request.getSession();
	    		 session.setAttribute("authUser", authUser);
	    		 
	    		 //메인 리다이렉트
	    		 WebUtil.redirect(request, response, "/mysite2/main");
	    	  }
	     }else if("logout".equals(action)) {
	    	 System.out.println("UserController>logout");
	    	 
	    	 //세션값을 지운다
	    	 HttpSession session = request.getSession();
	    	 session.removeAttribute("authUser");
	    	 session.invalidate();//캐시없애줌
	    	 
	    	 //메인으로 리다이렉트
	    	 WebUtil.redirect(request, response, "/mysite2/main");
	     }else if("modifyForm".equals(action)) {
	    	 System.out.println("UserController>modifyForm");
	    	 
	    	 //로그인체크
	    	 
	    	 
	    	 //로그인한 사용자의 no값을 세션에 가져오기
	    	 HttpSession session = request.getSession();
	    	 UserVo authuser = (UserVo)session.getAttribute("authUser");
	    	 int no = authuser.getNo();
	    	 
	    	 //no 로 사용자 정보 가져오기
	    	 UserDao userDao = new UserDao();
	    	 UserVo userVo =  userDao.getUser(no); //no id password name gender
	    	 
	    	 //request 의 attribute 에 userVo 널어서 포워딩
	    	 request.setAttribute("userVo", userVo);
	    	 WebUtil.forward(request, response, "/WEB-INF/views/user/modify.jsp");
	    	 
	     }else if("modify".equals(action)) { //수정
	    	 System.out.println("UserController>modify");
	    	 //세션에서 no
	    	 HttpSession session = request.getSession();
	    	 UserVo authUser = (UserVo)session.getAttribute("authUser");
	    	 int no = authUser.getNo();
	    	 
	    	 //파라미터 꺼낸다
	    	 String password = request.getParameter("password");
	    	 String name = request.getParameter("name");
	    	 String gender = request.getParameter("gender");
	    	 
	    	 //묶어준다
	    	 UserVo userVo = new UserVo();
			 userVo.setNo(no);
			 userVo.setPassword(password);
			 userVo.setName(name);
			 userVo.setGender(gender);
			 
	    	 //dao 사용한다
	    	 UserDao userDao = new UserDao();
	    	 int count = userDao.update(userVo);
	    	 //리다이렉트(main)
	    	 WebUtil.redirect(request, response, "/mysite2/main");
	     }
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}