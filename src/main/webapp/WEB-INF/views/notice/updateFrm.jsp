<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.delBtn:hover{
		cursor: pointer;
	}
	.delBtn{
		display:inline-block;
		height : 30px;
		vertical-align: middle;
	}
	#boardContent{
		background-color: white !important;
	}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content">
			<section class="section notice-update-wrap">
				<div class ="page-title">${notice.boardName}수정</div>
				<form action="/notice/update" method="post" enctype="multipart/form-data">
					<input type="hidden" name="postId" value="${notice.postId }">
					<table class="tbl">
						<tr>
							<th style="width:15%;">제목</th>
							<td>
								<div class="input-item">
									<input type="text" name="boardTitle" value="${notice.boardTitle }">
								</div>
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td>
								<div class="file-wrap left">
									<c:forEach var="file" items="${notice.fileList }">
										<div class="files">
											<span class="delFileName">${file.fileName }</span>
											<span class="material-icons delBtn" onclick="delFile(this, '${file.fileId}')">remove_circle</span>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr>
							<th>추가파일</th>
							<td>
								<input type="file" name="addFile">
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="left">
								<div class="input-item">
									<textarea id="boardContent" name="boardContent"><c:out value="${notice.boardContent}" escapeXml="false" /></textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button class="btn-primary lg">수정</button>
							</td>
						</tr>
					</table>
				</form>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>
	
	<%-- summerNote 사용을 위한 라이브러리 --%>
<script src="/resources/summernote/summernote-lite.js"></script>
	<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
	<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
	
	<script>
		function delFile(obj, fileId){
			swal({
				title : "삭제",
				text : "첨부파일을 삭제하시겠습니까?",
				icon : "warning",
				buttons : {
					cancel : {
						text : "취소",
						value : false,
						visible : true,
						closeModal : true
					},
					confirm : {
						text : "삭제",
						value : true,
						visible : true,
						closeModal : true
					}
				}
			}).then(function(isConfirm){
				if(isConfirm){
					//삭제 클릭 시, 실시간으로 삭제 처리하지 않고 '수정' 버튼 클릭 시 삭제 될 수 있도록 form 태그 내부에 hidden으로 추가
					let inputEl = $('<input>');
					inputEl.attr('type','hidden');
					inputEl.attr('name','delFileId');
					inputEl.attr('value',fileId);
					
					$(obj).parent().remove(); //화면에서 사라지도록
					$('form').prepend(inputEl); //첫번 째 자식으로 추가
					
					 console.log("폼 제출 시 delFileId 값 확인:", $('input[name="delFileId"]').map(function() { return $(this).val(); }).get());
				}
			});
		}
		
		$('#noticeContent').summernote({
			height : 500,
			width : 1000,
			lang : "ko-KR",
			disableResize : true,
			disalbeResizeEditor : true,
			resize : true
		});
		
		//html태그를 서머노트화 하여 수정가능
		$(document).ready(function() {
            $('#boardContent').summernote({
                height: 300, // 에디터 높이
                lang: 'ko-KR', // 한국어 설정
                placeholder: '내용을 입력하세요...',
                toolbar: [
                    ['style', ['bold', 'italic', 'underline', 'clear']],
                    ['insert', ['picture', 'link']],
                    ['view', ['fullscreen', 'codeview']]
                ]
            });
        });
		
		
	</script>
</body>
</html>