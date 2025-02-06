<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<section class="select notice-list-wrap">
				<div class="page-title">${boardName}</div>
				<%--정렬기능 사용( 최신, 추천, 조회수 순) --%>
				<form method="get" action="/notice/list">
					<input type="hidden" name="boardId" value="${boardId}" />
					<input type="hidden" name="boardName" value="${boardName}" />
					<select id="sortSelect" name="sort" onchange="this.form.submit()">
						<option value="latest" ${sort == 'latest' ? 'selected' : ''}>최신순</option>
						<option value="recommend" ${sort == 'recommend' ? 'selected' : ''}>추천수순</option>
						<option value="views" ${sort == 'views' ? 'selected' : ''}>조회수순</option>
						<%--정렬기능 사용위해 넣음. --%>
					</select>
				</form>


				<c:if test="${not empty loginMember}">
					<div class="list-header">
						<a class="btn-point" id="write-btn"
							href='/notice/writeFrm?boardId=${boardId}&boardName=${boardName}'>${boardName}작성</a>
						<a class="btn-point" id="write-btn"
							href='/notice/editorWriteFrm?boardId=${boardId}&boardName=${boardName}'>${boardName}작성(Editor)</a>
					</div>
				</c:if>
				<div class="list-content">
				<form action="/NoticeAnnounceUpdate" method="POST" id="noticeForm">
				<input type="hidden" name="boardId" value="${boardId}" />
				<input type="hidden" name="boardName" value="${boardName}" />
					<table class="tbl hover">
						<tr>
							<th style="width: 5%;"></th>
							<th style="width: 10%;">번호</th>
							<th style="width: 35%;">제목</th>
							<th style="width: 15%;">작성자</th>
							<th style="width: 15%;">작성일</th>
							<th style="width: 10%;">추천수</th>
							<%--조회, 추천수 정렬도 있어 td를 하나 늘렸음 --%>
							<th style="width: 10%;">조회수</th>
						</tr>
						<c:forEach var="notice" items="${noticeList}">
						<c:choose>
						    <c:when test="${notice.noticeYn == 'Y'}">
						        <!-- n_y 값이 "Y"일 때 실행되는 코드 -->
						        <tr class="not-notice selected-row">
						    </c:when>
						    <c:otherwise>
						        <!-- n_y 값이 "Y"가 아닐 때 실행되는 코드 (else 역할) -->
						        <tr class="not-notice">
						    </c:otherwise>
						</c:choose>
			                    <td><input type="checkbox" class="row-checkbox" data-postid="${notice.postId}"></td>
			                    <c:choose>
								    <c:when test="${notice.noticeYn == 'Y'}">
								        <!-- 조건이 참일 때 실행되는 코드 -->
								        <td><input type="hidden" value=${notice.postId}>[공지사항]</td>
								    </c:when>
								    <c:otherwise>
								        <!-- 조건이 거짓일 때 실행되는 코드 -->
								        <td class="check-cell post-id">${notice.postId}</td>
								    </c:otherwise>
								</c:choose>
			                    
			                    <td><a href='/notice/view?postId=${notice.postId}'>${notice.boardTitle}</a></td>
			                    <td>${notice.boardWriter}</td>
			                    <td>${notice.createdDate}</td>
			                    <td>${notice.likes}</td>
			                    <td>${notice.readCount}</td>
				            </tr>
			            </c:forEach>
					</table>
					<c:if test="${loginMember.userGrade eq 100}">
						<button type="button" onclick="showCheckbox()">공지사항 설정</button><br>
		        <!-- 숨겨진 입력 필드들,기존 리스트에 넘겨주던 값+공지로 바꾼id값 -->
						
						<!-- 선택된 postId 값을 저장할 hidden input -->
						<input type="hidden" name="selectedPostIds" id="selectedPostIds">
		        		<button type="button" onclick="applyStyleAndSubmit()">확인</button>
					</c:if>

				</form>
				</div>
				<div id="pageNavi">${pageNavi}</div>
			</section>
			<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<script>
	function showCheckbox() {
		const elements = document.getElementsByClassName("row-checkbox");

		// 각 요소에 대해 반복하여 스타일 적용
		for (let i = 0; i < elements.length; i++) {
		    elements[i].style.display = 'block';
		}
	}
	
	function applyStyleAndSubmit() {
        const selectedPostIds = []; // 선택된 postId 값을 저장할 배열
        const checkboxes = document.querySelectorAll('.row-checkbox:checked'); // 체크된 체크박스들
        
        checkboxes.forEach(checkbox => {
            // 체크된 각 체크박스의 postId 값을 배열에 추가
            selectedPostIds.push(checkbox.getAttribute('data-postid'));
        });

        // 선택된 postId 값을 hidden input에 저장
        document.getElementById('selectedPostIds').value = selectedPostIds.join(',');

        // 폼을 제출
        document.getElementById('noticeForm').submit();
    }
</script>
</body>
</html>