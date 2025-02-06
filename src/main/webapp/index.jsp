<%@page import="kr.or.iei.news.model.vo.NewsItem"%>
<%@page import="kr.or.iei.search.word.vo.Word"%>
<%@page import="kr.or.iei.aside.model.vo.Product"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   ArrayList<NewsItem> newsList = (ArrayList<NewsItem>)request.getAttribute("newsList");
   ArrayList<Word> wordList = (ArrayList<Word>)session.getAttribute("wordList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.section {
   margin-top: 20px;
}

.list-header {
   text-align: right;
   margin-bottom: 10px;
}

.list-header>a:hover {
   text-decoration: underline;
}

.selected-row {
   color: red;
}
</style>
</head>
<body>
   <jsp:include page="/WEB-INF/views/common/header.jsp" />
   <main class="main">
      <jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
      <div class="content">
      <section class="section typePopular">
    <div class="page-title">
        <div class="page-title-position">
            <!-- 하드코딩된 링크로 "인기 게시물" 페이지로 이동 -->
            <a class="page-title-a" href="/notice/list?reqPage=1&boardId=0&boardName=인기게시판">
                <span>인기 게시물</span>
            </a>
            <div class="list-header">
                <a href="/notice/list?reqPage=1&boardId=0&boardName=인기게시판">더보기</a>
            </div>
        </div>
        <div class="list-content">
            <table class="tbl hover">
                <tr class="th">
                    <th style="width: 50%">제목</th>
                    <th style="width: 20%">작성자</th>
                    <th style="width: 20%">작성일</th>
                </tr>
                <!-- 인기 게시물 (popular post) 데이터가 여기에 삽입됩니다 -->
            </table>
        </div>
    </div>
    <div class="bottom-line"></div>
</section>
      
         <c:forEach var="notice" items="${noticeTypeList }">
            <section class="section type${notice.boardId }">
               <div class="page-title">
                  <div class="page-title-position">
                     <a class="page-title-a"
                        href="/notice/list?reqPage=1&boardId=${notice.boardId }&boardName=${notice.boardName}">
                        <span>${notice.boardName}</span>
                     </a>
                     <div class="list-header">
                        <a
                           href="/notice/list?reqPage=1&boardId=${notice.boardId }&boardName=${notice.boardName}">더보기</a>
                     </div>
                  </div>
                  <div class="list-content">
                     <table class="tbl hover">
                        <tr class="th">
                           <th style="width: 50%">제목</th>
                           <th style="width: 20%">작성자</th>
                           <th style="width: 20%">작성일</th>
                        </tr>
                     </table>
                  </div>
               </div>
               <div class="bottom-line"></div>
            </section>
         </c:forEach>

         <%--웹 안뭉게 지도록 뉴스를 여기 놨음!!!(없었음, 다른 페이지에 놔도됨)
                  작성일 자리 배치가 개트롤 중 대화 나눴던 대로 시간을 쳐내든 뭐든 해야할듯
                  당연하지만 노티스/리스트 페이지 css조정해야함
                  
                  본인 집컴의 DB문제인지  --%>
         <div class="newsContainer">
            <%for(int i=0; i<newsList.size(); i++) {%>
            <div class="newsTitle">
               <a href="${newsList.get(i).getLink() }"><%=newsList.get(i).getTitle() %></a>
            </div>
            <div class="newsDescription"><%=newsList.get(i).getDescription() %></div>
            <br>
            <%} %>
         </div>
      </div>
      <jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
   </main>
   <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>