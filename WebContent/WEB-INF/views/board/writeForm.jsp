<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header -->
		
		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->
		
		<div id="container" class="clearfix">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
			<!-- //aside -->
			<c:choose>
				<c:when test="${authUser.name == null}">
					<div id="content">

					<div id="content-head">
						<h3>게시판</h3>
						<div id="location">
							<ul>
								<li>홈</li>
								<li>게시판</li>
								<li class="last">일반게시판</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="content">
			
						<div id="content-head">
								<h3>게시판</h3>
								<div id="location">
									<ul>
										<li>홈</li>
										<li>게시판</li>
										<li class="last">일반게시판</li>
									</ul>
								</div>
								<div class="clear"></div>
							</div>
							<!-- //content-head -->
							<div id="board">
								<div id="writeForm">
									<form action="/bdc" method="get">
									<input type ="hidden" name = "userNo" value="${authUser.no }">
									<input type="hidden" name="action" value ="write">
										<!-- 제목 -->
										<div class="form-group">
											<label class="form-text" for="txt-title">제목</label>
											<input type="text" id="txt-title" name="title" value="" placeholder="제목을 입력해 주세요">
										</div>
									
										<!-- 내용 -->
										<div class="form-group">
											<textarea id="txt-content" name="content" value=""></textarea>
										</div>
										
										<a id="btn_cancel" href="">취소</a>
										<button id="btn_add" type="submit" >등록</button>
										
									</form>
									<!-- //form -->
								</div>
								<!-- //writeForm -->
							
							</div>
							<!-- //board -->
						</div>
						<!-- //content  -->
			
			
					</div>
				</c:otherwise>
			</c:choose>	
			
			
			

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>
