<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/default.css"/>
<style>
		.wrap{
			min-width:400px;
			min-height:300px;
		}
		.pw-container{
			display:flex;
			align-items:center;
		}
		.section{
			width:300px;
			margin : 0 auto;
		}
		.pw-info-wrap{
			width: 80%
		}
		.pw-btn{
			margin:20px 0px;
			text-align : center;
		}
		.pw-btn>button{
			width:85%;
		}
	</style>
</head>
<body>
	<div class="wrap">
		<main class="content pw-container">
			<section class="section">
				<div class="pw-info-wrap">
					<form id="pwChgForm" action="/member/pwChg" method = "post">
						<input type="hidden" name="userNo" value="${loginMember.userNo }">
						<div class="input-wrap">
							<div class="input-title">
								<label for="userPw">기존 비밀번호</label>
							</div>
							<div class="input-item">
								<input type="password" id="userPw" name="userPw">
							</div>
						</div>
						<div class="input-wrap">
							<div class="input-title">
								<label for="newUserPw">새 비밀번호</label>
							</div>
							<div class="input-item">
								<input type="password" id="newUserPw" name="newUserPw" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30자">
							</div>
						</div>
						<div class="input-wrap">
							<div class="input-title">
								<label for="newUserPwRe">새 비밀번호 확인</label>
							</div>
							<div class="input-item">
								<input type="password" id="newUserPwRe" name="newUserPwRe">
							</div>
							<p id="pwMessage" class="input-msg"></p>
						</div>
						<div class="pw-btn">
							<button type="button" onclick="pwChg()" class="btn-primary sm">변경하기</button>
						</div>
						<div class="pw-btn">
							<button type="button" onclick="closePop()" class="btn-secondary sm">닫기</button>
						</div>
					</form>
				</div>
			</section>
		</main>
	</div>
	<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="/resources/js/sweetalert.min.js"></script>
	<script>
	//입력값 검증 결과를 저장할 객체
	const checkObj = {
			"userPw" : false,
			"newUserPw" : false,
			"newUserPwRe" : false
	};
	
	const userPw = $('#userPw');
	userPw.on('input', function(){
		if(userPw.val().length < 1){
			//입력값을 모두 지웠을 때
			checkObj.userPw = false;
		}else{
			//기존 비밀번호 입력값 존재
			checkObj.userPw = true;
		}
	});
	
	//새 비밀번호
	const newUserPw = $('#newUserPw');
	const pwMessage = $('#pwMessage'); //새 비밀번호 및 확인 값에 대한 검증 결과 메시지를 보여줄 p 태그
	
	newUserPw.on('input', function(){
		pwMessage.removeClass("valid");
		pwMessage.removeClass("invalid");
		
		const regExp = /^[a-zA-Z0-9!@#$]{6,30}$/;
		
		if(regExp.test(newUserPw.val())){
			//새 비밀번호 입력값이 정규표현식 패턴에 만족하는 경우
			checkObj.newUserPw = true;
			
			//새 비밀번호 확인 값이 입력되어있는 상태일 때
			if(newUserPwRe.val().length > 0){
				checkNewPwRe();
			}else{
			pwMessage.addClass("valid");
			pwMessage.html("");				
			}
		}else{
			checkObj.newUserPw = false;
			pwMessage.addClass("invalid");
			pwMessage.html("비밀번호 형식이 유효하지 않습니다.");
		}
	});
	
	//새 비밀번호 확인값
	const newUserPwRe = $('#newUserPwRe');
	newUserPwRe.on('input', checkNewPwRe);
	
	function checkNewPwRe(){
		pwMessage.removeClass('valid');
		pwMessage.removeClass('invalid');
		
		//새 비밀번호 == 새 비밀번호 확인
		if(newUserPw.val() == newUserPwRe.val()){
			checkObj.newUserPwRe = true;
			pwMessage.addClass('valid');
			pwMessage.html("");
		}else{
			checkObj.newUserPwRe = false;
			pwMessage.addClass('invalid');
			pwMessage.html("비밀번호가 일치하지 않습니다.");
		}
	}
	
	//변경하기 버튼 클릭 시, submit 이전에 입력값 검증
	function pwChg(){
		let str = "";
		
		for(let key in checkObj){
			//key : 각 속성명
			
			//각 속성에 대한 값이 false인지?
			if(!checkObj[key]){
				switch(key){
				case "userPw" : str = "기존 비밀번호를 입력하세요. " ; break;
				case "newUserPw" : str = "새 비밀번호 형식이 유효하지 않습니다."; break;
				case "newUserPwRe" : str = "비밀번호가 일치하지 않습니다."; break;
				}
				
				swal({
					title:"알림",
					text : str,
					icon : "warning"
				});
				
				return false;
			}
		}
		
		
		swal({
			title : "변경",
			text : "비밀번호를 변경하시겠습니까?",
			icon : "warning",
			buttons : {
				cancel: {
					text : "취소",
					value : false,
					visible : true,
					closeModal : true
				},
				confirm: {
					text : "변경하기",
					value : true,
					visible : true,
					closeModal : true
				}
			}
		}).then(function(confirm){
			$('#pwChgForm').submit();
		});
	}
	
	function closePop(){
		self.close(); //비밀번호 변경을 클릭하여, 띄워준 현재 부라우저 닫기
	}
	/*
	newMemberPwRe.on('input', function(){
		pwMessage.removeClass('valid');
		pwMessage.removeClass('invalid');
		
		//새 비밀번호 == 새 비밀번호 확인
		if(newMemberPw.val() == newMemberPwRe.val()){
			checkObj.newMemberPwRe = true;
			pwMessage.addClass('valid');
			pwMessage.html("");
		}else{
			checkObj.newMemberPwRe = false;
			pwMessage.addClass('invalid');
			pwMessage.html("비밀번호가 일치하지 않습니다.");
		}
	});
	*/
	
	</script>
</body>
</html>