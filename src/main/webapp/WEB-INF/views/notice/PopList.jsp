<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.notice-list-wrap {
	width: 1200px;
	margin: 0 auto;
}

.list-content {
	height: 500px;
}

.list-header {
	padding: 15px 0px;
	text-align: right;
}

.row-checkbox {
	display: none;
	margin-top: 10px;
}

/* 새로운 스타일: 선택된 행의 배경색을 빨간색으로 변경 */
.selected-row {
	color: red;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content">
			<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
			<section class="select notice-list-wrap">
				<div class="page-title">인기게시판</div>
				<div class="list-content">
					<table class="tbl hover">
						<thead>
							<tr>
								<th style="width: 5%;"></th>
								<th style="width: 10%;">번호</th>
								<th style="width: 35%;">제목</th>
								<th style="width: 15%;">작성자</th>
								<th style="width: 15%;">작성일</th>
								<th style="width: 10%;">추천수</th>
								<th style="width: 10%;">조회수</th>
							</tr>
						</thead>
						<tbody>
							<!-- 인기 게시물 목록 출력 -->
							<c:forEach var="notice" items="${noticeList}">
								<tr>
									<td><a href='/notice/view?postId=${notice.postId}'>${notice.boardTitle}</a></td>
									<td>${notice.id}</td>
									<!-- 게시물 번호 -->
									<td>${notice.boardTitle}</td>
									<td>${notice.nickname}</td>
									<td>${notice.createdAt}</td>
									<td>${notice.likes}</td>
									<td>${notice.views}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- 페이지 네비게이션 출력 -->
				<div class="pagination">${pageNavi}</div>
			</section>
			<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>

</body>
</html>