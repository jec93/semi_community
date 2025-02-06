<%@page import="kr.or.iei.search.word.vo.Word"%>
<%@page import="kr.or.iei.notice.model.vo.Notice"%>
<%@page import="kr.or.iei.search.word.vo.Blog"%>
<%@page import="kr.or.iei.search.word.vo.Img"%>
<%@page import="kr.or.iei.news.model.vo.NewsItem"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
ArrayList<NewsItem> newsList = (ArrayList<NewsItem>) request.getAttribute("newsList");
ArrayList<Img> imgList = (ArrayList<Img>) request.getAttribute("imgList");
ArrayList<Blog> blogList = (ArrayList<Blog>) request.getAttribute("blogList");
ArrayList<Notice> srchNoticeNm = (ArrayList<Notice>) request.getAttribute("srchNoticeNm");
String srchInput = (String) request.getAttribute("search");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
/* 이미지 관련 스타일 */
.srch-title{
	color:white;
}
.images {
	display: flex;
	flex-wrap: wrap;
	gap: 16px;
	justify-content: flex-start; /* 왼쪽 정렬 */
	align-items: flex-start; /* 상단 정렬 */
}

.img-content {
	display: flex;
	text-align: center;
	width: 250px; /* 이미지 크기를 일정하게 설정 */
	box-sizing: border-box;
}

.search-img {
	width: 100%; /* 너비를 100%로 설정하여 부모 요소에 맞게 크기 조정 */
	height: 250px; /* 고정된 높이로 세로 길이 조정 */
	object-fit: cover; /* 이미지가 가로/세로 비율에 맞게 잘리거나 늘어나도록 설정 */
	border-radius: 8px; /* 이미지의 둥근 모서리 */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<main class="main">
		<!-- 관리자 영역 -->
		<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
		<div class="body-content">
			<!-- 검색 입력이 있을 경우 출력 -->
			<c:if test="${not empty srchInput}">
				<c:forEach var="notice" items="${srchNoticeNm}">
					<div>
						<h2>${notice.boardName}</h2>
					</div>
				</c:forEach>
			</c:if>

			<div class="main-content">
				<!-- 뉴스 영역 -->
				<div class="news">
					<h2 class="srch-title">뉴스!!</h2>
					<% for (int i = 0; i < newsList.size(); i++) { %>
					<div class="content">
						<div>
							<a href="<%=newsList.get(i).getLink()%>"><%=newsList.get(i).getTitle()%></a>
						</div>
						<div><%=newsList.get(i).getDescription()%></div>
						<br>
					</div>
					<% } %>
				</div>

				<!-- 블로그 영역 -->
				<div class="blog">
					<h2 class="srch-title">블로그!!</h2>
					<% for (int i = 0; i < blogList.size(); i++) { %>
					<div class="content">
						<div>
							<a href="<%=blogList.get(i).getBlogLink()%>"><%=blogList.get(i).getBlogTitle()%></a>
						</div>
						<div><%=blogList.get(i).getBlogDescription()%></div>
						<br>
					</div>
					<% } %>
				</div>

				<!-- 이미지 영역 -->
				<h2 class="srch-title">이미지!!</h2>
				<div class="images">
					<% for (int i = 0; i < 8; i++) { %>
					<div class="img-content">
						<a href="<%=imgList.get(i).getImgLink()%>"> <img
							src="<%=imgList.get(i).getImgLink()%>" class="search-img"
							style="width: 250px">
						</a>
					</div>
					<% } %>
				</div>

				<!-- 비디오 영역 -->
				<div class="videos">
					<h2 class="srch-title">비디오!!</h2>
					<table id="videos-Result">

					</table>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
	$(document).ready(function(){
	    // URL 파라미터에서 search=라면 값을 가져옴 
	    var urlParams = new URLSearchParams(window.location.search);
	    var srchInput = urlParams.get('search'); // '롤'과 같은 값이 들어옵니다

	    $.ajax({
	        method: "GET",
	        url: "https://dapi.kakao.com/v2/search/vclip?target=title&query=" + encodeURIComponent(srchInput),
	        headers: { Authorization: "KakaoAK 9659ba1679deb39b4613fbf1fb92a094" }
	    }).done(function(data) {
	        //버튼 클릭시 기존 append 모두 제거
	        $('#videos-Result').empty();
	        for (let k = 1; k <= 3; k++) {
	            $('#videos-Result').append("<tr></tr>");
	            for (let i = 3 * (k - 1); i < 3 * k; i++) {
	                $('#videos-Result tr:nth-child(' + (2 * k - 1) + ')').append("<th>" + data.documents[i].title + "</th>");
	            }
	            $('#videos-Result').append("<tr></tr>");
	            for (let i = 3 * (k - 1); i < 3 * k; i++) {
	                const strUrl = data.documents[i].url;
	                const substrUrl = strUrl.substr(31);
	                $('#videos-Result tr:nth-child(' + (2 * k) + ')').append("<td><iframe width='350' height='200' src='https://www.youtube.com/embed/"+substrUrl+"' title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen/></td>");
	            }
	        }
	    });
	});
    </script>
</body>
</html>