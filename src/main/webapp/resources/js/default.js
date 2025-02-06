function closeModal(obj) {
  const modalBg = $(obj).parents(".modal-bg");
  modalBg.removeClass("open");
}
function openModal(modal) {
  console.log(modal);
  $(modal).addClass("open");
}
function validateSearch() {
    var searchInput = document.getElementById("search").value.trim();
    if (searchInput === "") {
        alert("검색어를 입력해주세요.");
        return false;  // 폼 제출을 막음
    }
    return true;  // 폼이 정상적으로 제출됨
}