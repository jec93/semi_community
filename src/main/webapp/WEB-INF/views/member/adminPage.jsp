<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	input[type=checkbox].chk + label{
		height : 24px;
	}
</style>

</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content">
			<section class="section admin-wrap">
				<div class="page-title">회원 관리 페이지</div>
				<table class="tbl tbl-hover">
					<tr>
						<th style="width:5%">선택</th>
						<th style="width:5%">번호</th>
						<th style="width:10%">아이디</th>
						<th style="width:10%">이름</th>
						<th style="width:10%">이메일</th>
						<th style="width:15%">전화번호</th>
						<th style="width:15%">포인트</th>
						<th style="width:10%">가입일</th>
						<th style="width:10%">회원등급</th>
						<th style="width:10%">등급변경</th>
						<th style="width:10%">삭제</th>
					</tr>
					<c:forEach var="u" items="${memberList}">
						<tr>
							<td>
								<div class="input-wrap">
									<input type="checkbox" class="chk">
									<label onclick="chkLabel(this)"></label>
									<label onclick="deleteUser(this)"></label>
								</div>
							</td>
							<td class="userNo">${u.userNo}</td>
							<td class="userId">${u.userId}</td>
							<td>${u.nickname}</td>
							<td class="userEmail">${u.userEmail}</td>
							<td class="phone">${u.phone}</td>
							<td>${u.userPoint}</td>
							<td>${u.userDate}</td>
							<td>
								<div class="select">
									<select>
										<c:choose>
											<c:when test="${u.userGrade eq 1}">
												<option value="1" selected>브론즈</option>
												<option value="2">실버</option>
												<option value="3">골드</option>
												<option value="4">플래티넘</option>
												<option value="5">다이아몬드</option>
											</c:when>
											<c:when test="${u.userGrade eq 2}">
												<option value="1">브론즈</option>
												<option value="2"selected>실버</option>
												<option value="3">골드</option>
												<option value="4">플래티넘</option>
												<option value="5">다이아몬드</option>
											</c:when>
											<c:when test="${u.userGrade eq 3}">
												<option value="1">브론즈</option>
												<option value="2">실버</option>
												<option value="3"selected>골드</option>
												<option value="4">플래티넘</option>
												<option value="5">다이아몬드</option>
											</c:when>
											<c:when test="${u.userGrade eq 4}">
												<option value="1">브론즈</option>
												<option value="2">실버</option>
												<option value="3">골드</option>
												<option value="4"selected>플래티넘</option>
												<option value="5">다이아몬드</option>
											</c:when>
											<c:when test="${u.userGrade eq 5}">
												<option value="1" >브론즈</option>
												<option value="2">실버</option>
												<option value="3">골드</option>
												<option value="4">플래티넘</option>
												<option value="5"selected>다이아몬드</option>
											</c:when>
											
										</c:choose>
									</select>
								</div>
							</td>
							<td>
								<button class="btn-primary sm" onclick="chgLevel(this)">등급변경</button>
							</td>
							<td>
								<button class="btn-primary sm" onclick="deleteUser(this)">강퇴</button>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="10">
							<button class="btn-point lg" onclick="chgAllLevel()">선택 회원 등급 변경</button>
						</td>
						<td colspan="10">
							<button class="btn-point lg" onclick="deleteAllUser()">선택 회원 강퇴</button>
						</td>
					</tr>
				</table>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
	
	<script>
		//등급변경 버튼 클릭 시, 동작 함수
		function chgLevel(obj) {
			let userNo = $(obj).parents('tr').find('.userNo').html();
			let userGrade = $(obj).parent().prev().find('select option:selected').val();
			
			
			/*
				서블릿으로 요청 시, 전송 파라미터
				- 회원번호 : memberNo
				- 변경할 등급 : memberLevel
				
				SQL : update tbl_member set member_level = ? where member_no = ?
			*/
			
			swal({
				title : "알림",
				text : "등급을 변경하시겠습니까?",
				icon : "success",
				buttons : {
					cancel : {
						text : "취소",
						value : false,
						visible : true,
						closeModal : true
					},
					confirm : {
						text : "변경",
						value : true,
						visible : true,
						closeModal : true
					}
				}
			}).then(function(isConfirm) {

				if(isConfirm){
					location.href ="/member/chgLevel?userNo="+userNo+"&userGrade="+userGrade;
					/*
					$.ajax({
						url : "/member/chgLevel",
						type : "GET",
						data : { "userNo" : userNo, "userGrade" : userGrade},
						success : function(res) {

							let title = "알림";
							let text = '';
							let icon = '';
							
							if(res > 0){
								text = '등급이 변경되었습니다.';
								icon = 'success';
							}else{
								text = '등급 변경 중, 오류가 발생하였습니다.';
								icon = 'error';
							}
							
							swal({
								title : title,
								text : text,
								icon : icon
							});
						},
						error : function() {
							console.log('ajax 통신 오류');
						}
					});
					*/

				}
				
				
			});
		}
		
		function chkLabel(obj){
			$(obj).prev().click();
		}
		
		//선택 회원 변경 시 동작 함수
		function chgAllLevel() {
			//체크된 체크박스's
			let checkBoxs = $('.chk:checked'); //클래스가 chk인 태그 중에, checked인 태그's
			
			//console.log(checkBoxs);
			//length : 체크된 체크박스의 갯수
			
			if(checkBoxs.length < 1){
				swal({
					title : "알림",
					text : "선택한 회원이 없습니다.",
					icon : "warning"
				});
				
				return;
			}
			
			let memberNoArr = [];
			let memberLevelArr = [];
			
			$.each(checkBoxs, function(index, item){
				memberNoArr.push($(item).parents('tr').find('.userNo').html());
				memberLevelArr.push($(item).parents('tr').find('.select option:selected').val());
			});
			
			swal({
				title : "알림",
				text : "등급을 변경하시겟습니까?",
				icon : "warning",
				buttons : {
					cancel : {
						text : "취소",
						value : false,
						visible : true,
						closeModal : true
					},
					confirm : {
						text : "변경",
						value : true,
						visible : true,
						closeModal : true
					}
				}
			}).then(function(isConfirm){
				if(isConfirm){
					location.href = "/member/chgAllLevel?memberNoArr=" + memberNoArr.join("/") + "&memberLevelArr=" + memberLevelArr.join("/");
					/*
					$.ajax({
						url : "/member/chgAllLevel",
						type : "GET",
						data : {"memberNoArr" : memberNoArr.join("/"), "memberLevelArr" : memberLevelArr.join("/")},
						success : function(res){
							let title = "알림";
							let text = '';
							let icon = '';
							
							if(res > 0){
								text = '등급이 변경되었습니다.';
								icon = 'success';
							}else{
								text = '등급 변경 중, 오류가 발생하였습니다.';
								icon = 'error';
							}
							
							swal({
								title : title,
								text : text,
								icon : icon
							});
						},
						error : function() {
							
						}
					});
					*/
				}
			});
		}
		
		
		
		
		function deleteUser(obj){
			$(obj).prev().click();
		}
		
	
		
		//강퇴기능
		
		function deleteUser(obj) {
			console.log("deleteUser 함수가 호출되었습니다.");
			
			    let userNo = $(obj).parents('tr').find('.userNo').html();
			    let userId = $(obj).parents('tr').find('.userId').html();
			    let userEmail = $(obj).parents('tr').find('.userEmail').html();
			    let phone = $(obj).parents('tr').find('.phone').html();

			    swal({
			        title: "알림",
			        text: "해당 회원을 강퇴하시겠습니까?",
			        icon: "success",
			        buttons: {
			            cancel: {
			                text: "취소",
			                value: false,
			                visible: true,
			                closeModal: true
			            },
			            confirm: {
			                text: "강퇴!",
			                value: true,
			                visible: true,
			                closeModal: true
			            }
			        }
			    }).then(function(isConfirm) {
			        if (isConfirm) {
			        	 location.href = "/member/bann?userNo=" + userNo + "&userId=" + userId + "&userEmail=" + userEmail + "&phone=" + phone;
			        }
			    });
			}
		
		
		//일괄강퇴기능
		
		
		
		
		//선택 회원 변경 시 동작 함수
		function deleteAllUser() {
			//체크된 체크박스's
			let checkBoxs = $('.chk:checked'); //클래스가 chk인 태그 중에, checked인 태그's
			
			//console.log(checkBoxs);
			//length : 체크된 체크박스의 갯수
			
			if(checkBoxs.length < 1){
				swal({
					title : "알림",
					text : "선택한 회원이 없습니다.",
					icon : "warning"
				});
				
				return;
			}
			
			let userNoArr = [];
			let userIdArr=[];
			let userEmailArr=[];
			let phoneArr=[];
			
			$.each(checkBoxs, function(index, item){
				userNoArr.push($(item).parents('tr').find('.userNo').html());
				userIdArr.push($(item).parents('tr').find('.userId').html());
				userEmailArr.push($(item).parents('tr').find('.userEmail').html());
				phoneArr.push($(item).parents('tr').find('.phone').html());
			});
			
			swal({
				title : "알림",
				text : "선택한 회원 모두 강퇴시킵니까?",
				icon : "warning",
				buttons : {
					cancel : {
						text : "취소",
						value : false,
						visible : true,
						closeModal : true
					},
					confirm : {
						text : "강퇴!",
						value : true,
						visible : true,
						closeModal : true
					}
				}
			}).then(function(isConfirm){
				if(isConfirm){
					
					 location.href = "/member/bannUsers?userNoArr=" + userNoArr.join("/") + "&userIdArr=" + userIdArr.join("/") + "&userEmailArr=" + userEmailArr.join("/") + "&phoneArr=" + phoneArr.join("/");
				}
			});
		}
		
		
		
		
		
		
		
	</script>
</body>
</html>