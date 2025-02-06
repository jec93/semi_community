<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/js/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="/resources/css/default.css" />

<header class="header">
	<div class="nav">
		<div class="logo">
			<a href="/index"> <img src="/resources/image/logo.png" alt="로고"
				width="250" height="50">
			</a>
		</div>
		<div class="titleAndcontents-search">


			<form action="/search" method="get" onsubmit="return validateSearch()">
				<input type="search" name="search" id="search"
					placeholder="게시판명 & 통합검색" autocomplete="off" onkeyup="fetchSearchResults(this.value)">
				<button type="submit">
					<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
						fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path
							d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
                        </svg>
				</button>
			<div id="searchResults">
			</form>
    		</div>

		</div>
		<div class="navbar-nav">
			<!-- 전체게시판 및 메뉴-->
			<ul class="nav-menu">
				<li>
					<div class="dropdown">
						<a class="btn btn-secondary dropdown-toggle" href="#"
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
							전체 게시판 </a>
						<ul class="dropdown-menu">
							<%-- Listener에서 조회한, 메뉴 종류 --%>
							<c:forEach var="noticeType" items="${noticeTypeList}">
								<li><a class="dropdown-item"
									href="/notice/list?reqPage=1&boardId=${noticeType.boardId}&boardName=${noticeType.boardName}">${noticeType.boardName}</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</li>
				<li>
					<div class="dropdown">
						<a class="btn btn-secondary dropdown-toggle" href="#"
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
							<img
							src="/resources/image/menu_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24.svg"
							alt="">
						</a>
						<ul class="dropdown-menu">
							<c:choose>
								<c:when test="${empty sessionScope.loginMember }">
									<%-- 세션이 비면 --%>
									<li><a class="dropdown-item" href="/member/loginFrm">로그인</a></li>
									<li><a class="dropdown-item" href="/member/joinFrm">회원가입</a></li>
								</c:when>
								<c:otherwise>
									<div class="username">
										<a href="/member/mypage">${loginMember.nickname}님</a>
										<div>포인트 : ${loginMember.userPoint }</div>
									</div>
									<div class="dropdown-bottom-line"></div>
									<li><a class="dropdown-item" href="#">내 글 조회</a></li>
									<li><a class="dropdown-item" href="#">내 댓글 조회</a></li>
									<li><a class="dropdown-item" href="#">스크랩 목록</a></li>
									<div class="dropdown-bottom-line"></div>
									<li><a class="dropdown-item" href="/member/logout">로그아웃</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</div>
	
	<script>
	function msg(title, text, icon){
		swal({
			title : title,
			text : text,
			icon : icon
		});
	}
	
	function getContextPath() {
	    var pathname = window.location.pathname;
	    var indexOfSecondSlash = pathname.indexOf("/", 1);
	    if (indexOfSecondSlash === -1) {
	        return '';
	    }
	    var contextPath = pathname.substring(0, indexOfSecondSlash);
	    return contextPath;
	}

	function fetchSearchResults(query) {
	    var searchResults = $('#searchResults');
	    if (query.length === 0) {
	        searchResults.hide();
	        return;
	    }

	    var contextPath = getContextPath();

	    $.ajax({
	        type: 'POST',
	        url: contextPath + '/searchBoard',
	        data: { search: query },
	        success: function(response) {
	            if ($.trim(response)) {
	                searchResults.html(response);
	                searchResults.show();
	            } else {
	                searchResults.html('<div>검색 결과가 없습니다.</div>');
	                searchResults.show();
	            }
	        },
	        error: function() {
	            searchResults.html('<div>서버 오류가 발생했습니다.</div>');
	            searchResults.show();
	        }
	    });
	}
	</script>
</header>