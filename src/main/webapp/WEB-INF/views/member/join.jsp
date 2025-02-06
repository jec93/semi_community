<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	.join-wrap{
		width:500px;
		padding : 20px;
		margin : 0 auto;
		
	}
	.join-wrap .input-wrap{
		padding : 15px 30px;
		margin-left:20%;
		border:0px;
		
	}
	
	.join-wrap .join-button-box{
		padding : 15px 30px;
		display : flex;
		justify-content : center;
	}
	.join-wrap .join-button-box>button{
		width : 60%;
		margin-left:0%;
		font-size: 40px;
		border-radius: 20px;
		background-color : rgba(33, 37, 41, 0.75);
		border: 0px;
		color: white;
	}
	
	.input-item{
		border: none; 
		/*
   		border-bottom: 2px solid black; */
		width:70%;
		font-size:25px;
		background-color: gray;
	}
	.input-item input{
		border:none;
	
		width:100%;
		
	}
	.inpit-item input:hover{
	outline:none;
	
	}
	.btn-primary{
	 border-radius: 20px;
	 padding :3px;
	 background-color : rgba(33, 37, 41, 0.75);
	 border:1px solid black;
	 color:white;
	}
	
	.page-title{
		font-weight: bold;
		font-size: 50px;
	}
	form label{
		font-weight: bold;
	}
</style>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content"> 
		<%--main은 시멘틱 태그, 시멘틱은 화면에 어떻게 적용될지 짐작하는 특징 --%>
			<section class="section join-wrap">
				<div class="member-title"><b>회원가입</b></div>
				<form action="/member/join" method="post" autocomplete = "off" onsubmit="return joinValidate()">
					<div class="input-wrap">
						<div class="input-title">
							<label for="memberId">아이디</label>
						</div>
						<div class="input-item">
							<input type="text" id="userId" name="userId" placeholder="영어, 숫자 8~20글자" maxlength="20"/>
							<button type="button" id="idDuplChkBtn" class = "btn-primary">중복체크</button>
						</div>
						<p id="idMessage" class = "input-msg"></p>
					</div>
					<div class="input-wrap">
						<div class="input-title">
							<label for="userPw">비밀번호</label>
						</div>
						<div class="input-item">
							<input type="password" id="userPw" name="userPw" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30자" maxlength="30">
						</div>
					</div>
					<div class="input-wrap">
						<div class="input-title">
							<label for="userPwConfirm">비밀번호 확인</label>
						</div>
						<div class="input-item">
							<input type="password" id="userPwConfirm" maxlength="30">
						</div>
						<p id="pwMessage" class="input-msg"></p>
					</div>
					<div class="input-wrap">
						<div class="input-title">
							<label for="userEmail">이메일</label>
						</div>
						<div class="input-item">
							<input type="email" id="userEmail" name = "userEmail">
						</div>
						<p id="emailMessage" class="input-msg"></p>
					</div>
					<div class="input-wrap">
						<div class="input-title">
							<label for="nickname">닉네임</label>
						</div>
						<div class="input-item">
							<input type="text" id="nickname" name="nickname" placeholder="한글 2~10자" maxlength="10">
						</div>
						<p id="nameMessage" class="input-msg"></p>
					</div>
					<div class="input-wrap">
						<div class="input-title">
							<label for="userPhone">전화번호</label>
						</div>
						<div class="input-item">
							<input type="text" id="userPhone" name="userPhone" placeholder="전화번호(010-0000-0000)" maxlength="13">
						</div>
						<p id="phoneMessage" class="input-msg"></p>
					</div>
					
					<div class="join-button-box">
						<button type="submit" class="btn-primary lg">회원가입</button>
					</div>
				</form>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
	
	<script>
	//submit 동작 시, 아래 객체 모든 속성에 대해서 값이 true 인지 검사할것임!
	const checkObj = {
			"userId" 			: false,
			"idDuplChk"			: false, //아이디 중복 체크 결과
			"userPw" 			: false,
			"userPwConfirm" 	: false,
			"nickName" 		: false,
			"userPhone" 		: false
	};
	
	const userId = $('#userId'); // id 속성값이 memberId인 요소
	const idMessage = $('#idMessage'); //id를 입력할 수 있는 input에 입력 시, 결과 메시지를 보여줄 요소

	if(userId.val().length < 1){
		
	}
	//한글자 입력마다
	userId.on('input', function(){
		
		/*
		아래 checkObj.idDuplChk = false;를 작성하지 않으면! 발생하는 현상
		
		1.중복되지 않은 아이디 입력 후, 체크 버튼 클릭 -> true
		2.아이디를 중복된 아이디로 수정(중복체크X)
		3.회원가입 버튼 클릭 (버튼 클릭 시절에, idDuplChk의 값은? true)
		*/
		
		checkObj.idDuplChk = false; //아이디값 변경마다
		idMessage.removeClass("valid");
		idMessage.removeClass("invalid");
		const regExp = /^[a-zA-Z0-9]{8,20}$/;
		
		if(regExp.test($(this).val())){ //정규표현식 패턴에 일치할 때
			idMessage.html("");
			idMessage.addClass("valid");
			checkObj.userId = true;
		}else{
			//일치하지 않을 때
			idMessage.html("영어, 숫자 8~20글자 사이로 입력하세요.");
			idMessage.addClass("invalid");
			checkObj.userId = false;
		}
	});
	
	/* qowo0420
	q: false
	o: false
	w: false
	o: false
	0: false
	4: false
	2: false
	0: 
	*/
	
	//아이디 중복체크
	$('#idDuplChkBtn').on('click', function(){
		//아이디 중복체크 요청 이전에, 아이디를 입력했는지 체크
		if(!checkObj.userId){
			msg("알림", "유효한 내용을 작성하고 눌러주세요", "error");
			return false;
		}
		
		
		//스크립트에서 GET요청 보내는 방법 == location.href
		//idDuplChk 주소를 가진 서블릿에 요청하며, memberId 전달
		/*
		location.href = '/idDuplChk?memberId='+memberId.val();
		서블릿에서, 중복 체크 후, 이동하는 서블릿으로 요청하는
		현재 join.jsp의 페이지가 다시 그려지며, 입력했던 값들이 사라짐
		*/
		
		//ajax : 비동기 통신 기술 중, 하나로 페이지 전환없이 서버와 데이터를 주고 받음(핵심은 페이지 전환이 없다)
		$.ajax({
			url : "/idDuplChk",
			data : {"userId" : userId.val()},
			type : "GET",
			success : function(res){
				if(res == 0){
					//중복된 아이디가 없음 == 회원가입 가능
					msg("알림", "사용 가능한 아이디입니다.", "success");
					checkObj.idDuplChk = true;
				}else{
					msg("알림", "중복된 아이디가 존재합니다.", "warning");
					checkObj.idDuplChk = false;
				}
			},
			error : function(){
				console.log('ajax오류 발생');
			}
		});
	});
	
	const userPw = $('#userPw'); //비밀번호 입력 input
	const pwMessage = $('#pwMessage'); //비밀번호 입력값과 정규표현식 패턴 일치성 검사 후, 메시지를 보여줄 요소
	
	userPw.on('input', function(){
		pwMessage.removeClass('valid');
		pwMessage.removeClass('invalid');
		const regExp = /^[a-zA-Z0-9!@#$]{6,30}$/;
		
		if(regExp.test($(this).val())){
			//비밀번호 값이 정규 패턴에 만족할 때, 비밀번호 확인 입력값이 입력되었는 지를 조건식에 작성
			if($(userPwConfirm).val().length < 1){
				//비밀번호는 정상 입력 상태 && 비밀번호 확인값은 입력되지 않은 상태
				pwMessage.html("");
				pwMessage.addClass("valid");
				checkObj.userPw = true;
			}else{
				//비밀번호 정상 입력 && 비밀번호 확인값도 입력된 상태
				checkPw();
			}
			pwMessage.html("");
			pwMessage.addClass("valid");
		}else{
			pwMessage.html("비밀번호 형식이 유효하지 않습니다.");
			pwMessage.addClass("invalid");
			checkObj.userPw = false;
		}
	});
	
	const userPwConfirm = $('#userPwConfirm');
	userPwConfirm.on('input',checkPw);

	//이벤트 핸들러 함수!
	function checkPw(){
		pwMessage.removeClass('valid');
		pwMessage.removeClass('invalid');
		
		if(userPwConfirm.val()==userPw.val()){
			// 비밀번호 값 == 비밀번호 확인 값
			pwMessage.addClass('valid');
			pwMessage.html("");
			checkObj.userPwConfirm = true;
		}else{
			pwMessage.addClass('invalid');
			pwMessage.html('비밀번호가 일치하지 않습니다.');
			checkObj.userPwConfirm = false;
		}
	};
	
	const userPhone = $("#userPhone");
	   const phoneMessage= $("#phoneMessage");
	   
	   userPhone.on('input',function(){
			phoneMessage.removeClass('valid');
			phoneMessage.removeClass('invalid');
			
			const regExp=/^010-\d{3,4}-\d{4}$/;
			
			if(regExp.test($(this).val())){
				phoneMessage.addClass("valid");
				phoneMessage.html("");
				checkObj.userPhone=true;
			}else{
				phoneMessage.addClass("invalid");
				phoneMessage.html("형식이 다릅니다.");
				checkObj.userPhone=false
			}
			
		});
	
	const nickName = $('#nickname');
	const nameMessage = $('#nameMessage');
	
	nickName.on('input',function(){
		nameMessage.removeClass('valid');
		nameMessage.removeClass('invalid');
		
		const regExp = /^[가-힣]{2,10}$/;
		
		if(regExp.test($(this).val())){
			nameMessage.html("");
			nameMessage.addClass("valid");
			checkObj.nickName = true;
		}else{
			nameMessage.html("닉네임은 한글 2~10글자로 입력");
			nameMessage.addClass("invalid");
			checkObj.nickName = false;
		}
	});
	
	function joinValidate(){
      
      let str = "";
      
      for(let key in checkObj){
         
         /*
         객체명.속성명 -> checkObj.key   -key라는 속성명을 찾음 ->X
         객체명[속성명]-> checkObj[key]   -O
         */
         
         /*
         각 입력값의 유효성 검사 결과를 저장하고 있는 객체의, 현재 값이 false일 때
         */
         if(!checkObj[key]){
            switch(key){//memberId or memberPw or memberName ......
            case"userId"            : str = "아이디 형식"; break;
            case"idDuplChk"				: str = "아이디 중복 체크를 진행하세요"; break;
            case"userPw"            : str = "비밀번호 형식"; break;
            case"userPwConfirm"      : str = "비밀번호 확인 형식"; break;
            case"nickName"         : str = "이름 형식";      break;
            case"userPhone"         : str = "전화번호 형식";   break;
            }
            
            if(key != "idDuplChk"){
            str += "이 유효하지 않습니다.";            	
            }
            
            msg("회원가입 실패", str, "error");
            
            return false;
         }
      }

      return true;
      
   }
	</script>
</body>
</html>