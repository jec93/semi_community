//일반 게시글
function noticeList() {
    $.ajax({
        url: "/notice/index", // 일반 게시물 API 호출
        type: "GET",
        dataType: "json",
        success: function(res) {
            console.log("Notice List:", res);
			
			// 기존 데이터 초기화 (헤더는 유지)
			$('.tbl tr:not(.th)').remove();
			
            $(res).each(function(index, item) {
                let html = '';
				if (item.noticeYn === 'Y') {
					html += "<tr class='selected-row'>"; // When noticeYn is 'Y'
				} else {
					html += "<tr>"; // When noticeYn is 'N'
				}
                html += "<input type='hidden' value='" + item.boardId + "'>";
                html += "<td><a href='/notice/view?postId=" + item.postId + "'>" + item.boardTitle + "</a></td>";
                html += "<td>" + item.nickname + "</td>";
                html += "<td>" + item.createdDate + "</td>";
                html += "</tr>";

                // 각 게시판에 해당하는 테이블에 데이터 추가
                $('.section.type' + item.boardId).find('.tbl').append(html);
            });
        },
        error: function() {
            console.log("일반 게시글 로딩 실패");
        }
    });
}

function popularNoticeList() {
    $.ajax({
        url: "/notice/popular", // 인기 게시물 API 호출
        type: "GET",
        dataType: "json",
        success: function(res) {
            console.log("Popular Notice List:", res);

            $(res).each(function(index, item) {
                let html = '';
                html += "<tr>"; // 인기 게시물
                html += "<input type='hidden' value='" + item.boardId + "'>";
                html += "<td><a href='/notice/view?postId=" + item.postId + "'>" + item.boardTitle + "</a></td>";
                html += "<td>" + item.nickname + "</td>";
                html += "<td>" + item.createdAt + "</td>";
                html += "</tr>";

                // 인기 게시물 테이블에 데이터 추가
                $('.section.typePopular').find('.tbl').append(html);
            });
        },
        error: function() {
            console.log("인기 게시글 로딩 실패");
        }
    });
}
// 페이지 로드 시 및 주기적으로 데이터를 갱신
$(function() {
    noticeList(); // 일반 게시물 데이터 로드
    popularNoticeList(); // 인기 게시물 데이터 로드
});

// 매 10분마다 데이터 갱신
setInterval(function() {
    popularNoticeList();
    noticeList();
}, 1000 * 60 * 10); // 10분마다 실행