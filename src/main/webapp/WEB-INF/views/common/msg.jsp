<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/sweetalert.min.js"></script>
</head>
<body>
	<script>
		const title = '${title}'; //메세지 제목(request.setAttribute("title", "회원가입")로 등록한 속성의 키값)
		const msg = '${msg}'; //메세지 본문
		const icon = '${icon}'; //alert 아이콘 종류(success,error, warning)
		const loc = '${loc}'; //이동할 서블릿의 url
		const callback = '${callback}'; //메세지를 띄워주고, 실행할 함수
		
		swal({
			title : title,
			text : msg,
			icon : icon
		}).then(function(){
			//alert의 확인 버튼을 누른 이후 동작
			
			if(callback != '' && callback !=null){
				//전달된 callback 내부 문자열을, Javascript 코드로 해석하고 실행할 수 있게 해주는 함수 : eval
				eval(callback);
			}
			
			if(loc != '' && loc != null){
				location.href = loc; //서블릿에서 등록한, 이동할 url로 이동!
			}
		});
	</script>
</body>
</html>