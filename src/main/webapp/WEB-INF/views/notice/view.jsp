<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.notice-view-wrap {
		width: 1200px;
		margin: 0 auto;
	}
	.noticeContent {
		min-height: 300px;
	}
	.comment-write {
		overflow: hidden;
	}
	.comment-write textarea[name=commentContent] {
		width: 1000px;
		height: 100px;
		margin-right: 10px;
	}
	.comment-write li {
		float: left;
	}
	.inputCommentBox {
		margin-bottom: 20px;
	}
	.comment-write button {
		width: 150px;
		height: 130px;
		font-size: 20px;
	}
	.commentBox ul {
		margin-bottom: 15px;
		border-bottom: solid 1px black;
	}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content">
			<section class="section notice-view-wrap">
				<div class="page-title">${notice.boardName}</div>
				<table class="tbl notice-view">
					<tr>
						<th colspan="6">${notice.boardTitle}</th>
					</tr>
					<tr>
                     <th style="width:20%">작성자</th>
                     <td style="width:20%">${notice.nickname }</td>
                     <th style="width:20%">작성일</th>
                     <td style="width:20%">${notice.createdDate}</td>
                     <th style="width:20%">추천수</th>
                     <td style="width:20%">${notice.likes }</td>
                  </tr>
					<tr>
						<th>첨부파일</th>
						<td colspan="5">
							<c:forEach var="file" items="${notice.fileList}">
								<a href="javascript:fileDown('${file.fileName}', '${file.filePath}')">${file.fileName}</a>
							</c:forEach>
						</td>
					</tr>
					<tr>
                     <td class="left" colspan="6">
                        <div class="noticeContent">${notice.boardContent }</div>
                     </td>
                  </tr>
                  <c:if test="${not empty loginMember }">
							 <button type="button" onclick="recommend(${loginMember.userNo}, ${notice.postId}, ${notice.boardWriter})">추천하기</button>
					 	</c:if>
					<c:if test="${not empty loginMember and loginMember.userNo eq notice.boardWriter or loginMember.userGrade eq '100'}">
						<tr>
							<td colspan="6">
								<c:if test="${not empty loginMember and loginMember.userNo eq notice.boardWriter}">
								<a href='/notice/updateFrm?postId=${notice.postId}' class="btn-primary">수정</a>
								</c:if>
								<button class="btn-secondary" onclick="deleteNotice('${notice.postId}')">삭제</button>
								
							</td>
						</tr>
					</c:if>
				</table>
				<c:if test="${not empty loginMember}">
					<div class="inputCommentBox">
						<form name="insertComment" action="/notice/insertComment">
							<input type="hidden" name="postId" value="${notice.postId}">
							<input type="hidden" name="boardId" value="${notice.boardId}">
							<input type="hidden" name="commentWriter" value="${loginMember.userNo}">
							
							<ul>
								<li>
									<div class="input-item">
										<textarea name="commentContent"></textarea>
									</div>
								</li>
								<li>
									<button type="submit" class="btn-primary">등록</button>
								</li>
							</ul>
						</form>
					</div>
				</c:if>
				<div class="commentBox">
					<c:forEach var="comment" items="${notice.commentList}">
						<ul class="posting-comment">
							<li>
								<span class="material-icons">account_box</span>
							</li>
							<li>
								<p class="comment-info">
									<span>${comment.commenterName}</span>
									<span>${comment.commTime}</span>
									<c:if test="${not empty loginMember and loginMember.userNo eq comment.commentWriter or loginMember.userGrade eq '100'}">
										<c:if test="${not empty loginMember and loginMember.userNo eq comment.commentWriter}">
											<a href='javascript:void(0)' onclick="mdfComment(this,'${comment.commentId}');">수정</a>
										</c:if>
										<a href='javascript:void(0)' onclick="delComment('${comment.commentId}');">삭제</a>
									</c:if>
								</p>
								<p class="comment-content">${comment.comments}</p>
								<div class="input-item" style="display:none;">
									<textarea name="commentContent">${comment.comments}</textarea>
								</div>
							</li>
						</ul>
					</c:forEach>
				</div>
			</section>
			<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
	<script>
		function fileDown(fileName, filePath) {
			location.href = '/notice/fileDown?fileName=' + fileName + '&filePath=' + filePath;
		}
		
		
		
		
		function deleteNotice(postId) { // 삭제 버튼 클릭 시 호출
			swal({
				title: "삭제",
				text: "게시글을 삭제하시겠습니까?",
				icon: "warning",
				buttons: {
					cancel: {
						text: "취소",
						value: false,
						visible: true,
						closeModal: true
					},
					confirm: {
						text: "삭제",
						value: true,
						visible: true,
						closeModal: true
					}
				}
			}).then(function(isConfirm) {
				if (isConfirm) {
					let postId = '${notice.postId}';
					location.href = '/notice/delete?postId=' + postId;
				}
			});
			
			
		}

		function delComment(commentId) { // 삭제 버튼 클릭 시 호출
			swal({
				title: "삭제",
				text: "댓글을 삭제하시겠습니까?",
				icon: "warning",
				buttons: {
					cancel: {
						text: "취소",
						value: false,
						visible: true,
						closeModal: true
					},
					confirm: {
						text: "삭제",
						value: true,
						visible: true,
						closeModal: true
					}
				}
			}).then(function(isConfirm) {
				if (isConfirm) {
					let postId = '${notice.postId}';
					location.href = '/notice/deleteComment?postId=' + postId + '&commentId=' + commentId;
				}
			});
		}
		
		function mdfComment(obj, commentId) { // 수정 기능 활성화
			let commentContentLi = $(obj).parents('li');
			$(commentContentLi).find('div.input-item').show();
			$(commentContentLi).find('p.comment-content').hide();

			$(obj).text('수정완료');
			$(obj).attr('onclick', 'mdfCommentComplete(this,"'+commentId+'")');
			
			$(obj).next().text('취소');
			$(obj).next().attr('onclick', 'mdfCommentCancel(this,"'+commentId+'")');
		}
		
		function mdfCommentComplete(obj, commentId) { // 수정 완료
			let form = $('<form>');
			form.attr('action', '/notice/updateComment');
			form.attr('method', 'post');
			
			let postId = '${notice.postId}';
			form.append($('<input type="hidden" name="postId">').val(postId));
			form.append($('<input type="hidden" name="commentId">').val(commentId));

			let commentContent = $(obj).parents('li').find('textarea[name="commentContent"]').val();
			form.append($('<input type="hidden" name="commentContent">').val(commentContent));
			
			$('body').append(form);
			form.submit();
		}
		
		function mdfCommentCancel(obj, commentId) { // 수정 취소
			let commentContentLi = $(obj).parents('li');
			$(commentContentLi).find('div.input-item').hide();
			$(commentContentLi).find('p.comment-content').show();
			
			$(obj).text('삭제');
			$(obj).attr('onclick', 'delComment("'+commentId+'")');
			
			$(obj).prev().text('수정');
			$(obj).prev().attr('onclick', 'mdfComment(this,"'+commentId+'")');
		}
		
		function recommend(userNo, postId, boardWriter) {
		    if (userNo && postId && boardWriter) {
		        location.href = '/notice/recommend?postId=' + postId + "&userNo=" + userNo+"&boardWriter="+boardWriter;
		        console.log(postId, userNo, boardWriter);
		    } else {
		        console.error('userNo 또는 postId, boardWriter 가 정의되지 않았습니다.');
		        
		    }
		}
	</script>
</body>
</html>
