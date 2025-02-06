<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/default.css"/>
<link rel = "stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons" type="text/css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>
html,body{
	width:100%;
	height:100%;
	
}
	.wrap{
		
		min-height: 100%;
	/*
		min-width: 400px;
		min-height:300px;
		*/
	}
	.content{
		min-height:100%;
	}
	.input-item{
		width:250px;
		
	}
	.input-item input{
		width:100%;
	}
	.srch-Info-container{
		display: flex;
		align-items: center;
	}
	.srch-Info-wrap{
		width: 80%;
	}
	.section {
		width: 400px;
		margin: 0 auto;
	}
	.input-wrap{
			
	}
	.btn-wrap{
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.btn-wrap button{
		font-size: 20px;
		width:70px;
	}
	
</style>

</head>
<body>
	<div class="wrap">
		<main class="content srch-info-container">
			<section class="section">
				<div class="srch-info-wrap">
					<c:if test="${gb eq 'id' }">
						<div class="page-title">아이디 찾기</div>
					</c:if>
					<c:if test="${gb eq 'pw' }">
						<div class="page-title">비밀번호 찾기</div>
					</c:if>
					
					
						<div class="input-wrap">
							<label for="userEmail">이메일 입력</label>
						<div class="input-item">
							<input type="email" id="userEmail" name="userEmail">
						</div>
						</div>
						<c:if test="${gb eq 'pw' }">
						
						<div class="input-wrap">
							<label for="userEmail">아이디 입력</label>
						<div class="input-item">
							<input type="email" id="userEmail" name="userEmail">
						</div>
						</div>
						</c:if>
						
					<div class="btn-wrap">
						<button style="background-color: white" type="button" onclick="srchInfo('${gb}')" class="btn-primary md">찾기</button>
					
						<button style="background-color: #43494c; " type="button" onclick="closeFn()" class="btn-secondary md">닫기</button>
					</div>
				</div>
			</section>
		</main>
	</div>
	<script>
    function srchInfo(gb) {
        let userEmail = $('#userEmail');
        let link = '';
        let param = {};

        if (userEmail.val().length < 1) {
            msg("알림", "이메일을 입력하라", "warning");
            return;
        }
        param.userEmail = userEmail.val();

        if (gb == 'id') {
            link = '/member/srchInfoId';
        } else if (gb == 'pw') {
            let userId = $('#userId');
            if (userId.val().length < 1) {
                msg('알림', '아이디가 입력되지 않음', 'warning');
                return;
            }
            param.userId = userId.val();
            link = '/member/srchInfoPw';
        } else {
            msg('알림', '구분값이 입력되지 않음', 'warning');
            return;
        }

        $.ajax({
            url: link,
            data: param,
            type: "GET",
            success: function (res) {
                if (gb == 'id') {
                    if (res == '') {
                        msg('알림', '일치하는 회원 없음', 'warning', 'closeFn()');
                    } else {
                        msg('알림', '아이디 찾기 결과: ' + res, 'success', 'closeFn()');
                    }
                } else if (gb == 'pw') {
                    if (res == '') {
                        msg('알림', '일치하는 회원 없음', 'warning', 'closeFn()');
                    } else {
                        msg('알림', '아이디 찾기 결과: ' + res, 'success', 'closeFn()');
                    }
                }
            },
            error: function () {
                msg('알림', '오류가 발생했습니다', 'error');
            }
        });
    }

    function msg(title, text, icon, callback) {
        swal({
            title: title,
            text: text,
            icon: icon
        }).then(function () {
            if (callback != null && callback != '') {
                eval(callback);
            }
        });
    }

    function closeFn() {
        window.close(); // 팝업 창 닫기
    }
</script>

</body>
</html>