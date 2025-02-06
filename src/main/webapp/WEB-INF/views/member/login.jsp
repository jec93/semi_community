<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	html,body{
		height:100%;
	}
	.wrap{
		min-height:100%;
		
	}
	.login-container{
	  	flex-grow: 1;
		display : flex;
		justify-content: center;
		align-items: center;
		min-height:70%;
	}
	.login-wrap{
		width : 500px;
		height:100%;
		background-color:white;
	}
	.login-wrap .input-wrap{
		padding : 15px 30px;
		margin-left:20%;
		border:0px;
		
	}
	.input-item{
		
		border: none;
		/*
    	border-bottom: 2px solid black;*/ 
		width:70%;
		font-size:25px;
		
	}
	.input-item input {
    	width: 100%;
    	border:none;
    	
    	
    }
	.login-wrap .input-button-box{
		padding : 20px 30px;
		display : flex;
		justify-content : center;
	}
	.login-wrap .login-button-box>button {
		width : 60%;
		margin-left:20%;
		font-size: 40px;
		border-radius: 20px;
		background-color : rgba(33, 37, 41, 0.75);
		border: 0px;
		color: white;
	}
	.member-link-box{
		display : flex;
		justify-content : center;
		gap : 20px;
	}
	.member-link-box :hover{
		text-decoration: underline;
	}

	.page-title{
		font-weight: bold;
		font-size: 50px;
	}
	
</style>
</head>
<body>
	<div class = "wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
			<main class="content login-container">
				<section class="section login-wrap">
					<div class="member-title"><b>로그인</b></div>
					<form action="/member/login" method="post" autocomplete = "off" onsubmit = "return loginValidate()">
						<div class="input-wrap">
							<div class="input-title">
								<label for="loginId"><b>아이디</b></label>
							</div>
							<div class="input-item">
								<%-- MemberLoginServlet에서 저장한 쿠키 값을 기본적으로 설정 --%>
								<input type="text" id="loginId" name="loginId" value="${cookie.saveId.value}">
							</div>
						</div>
						<div class="input-wrap">
							<div class="input-title">
								<label for="loginPw"><b>비밀번호</b></label>
							</div>
							<div class="input-item">
								<input type="password" id="loginPw" name="loginPw">
							</div>
						</div>
						<div class="input-wrap">
							<c:if test="${!empty cookie.saveId.value }">
							<input type="checkbox" name="saveId" id="saveId" value="chk">
							</c:if>
							<c:if test="${empty cookie.saveId.value }">
								<input type="checkbox" name="saveId" id="saveId" value="chk" checked>
							</c:if>
							<label for="saveId">아이디 저장</label>
						</div>
						<div class="login-button-box">
							<button type="submit" class="btn-primary lg">로그인</button>
						</div>
						<div class="member-link-box">
							<a href="/member/joinFrm">회원가입</a>
							<a href="javascript:void(0)" onclick="searchInfo('id')">아이디 찾기</a>
							<a href="javascript:void(0)" onclick="searchInfo('pw')">비밀번호 찾기</a>
						</div>
					</form>
				</section>
			</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
	<script>
	function loginValidate(){
		if($('#loginId').val().length < 1){
			msg("알림", "아이디를 입력하세요", "warning");
			return false;
		}
		if($('#loginPw').val().length < 1){
			msg("알림","비밀번호를 입력하세요.","warning");
			return false;
		}
	}
	function searchInfo(gb){
		
		let popupWidth=500;
		let popupHeight=330;
		if(gb=='pw'){
			popupHeight=400;
		}
		let top=(window.innerHeight-popupHeight)/2+window.screenY;
		let left=(window.innerWidth-popupWidth)/2+window.screenX;
		
		window.open("/member/searchInfoFrm?gb=" + gb, "searchInfo", "width=" + popupWidth + ", height=" + popupHeight + ", top=" + top + ", left=" + left);
		
	}
	</script>
</body>
</html>