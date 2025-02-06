const rankBox = document.querySelector('.rank-box');
const rankWrapper = document.querySelector('.rank-wrapper');
const searchRank = document.querySelector('.search-rank');
let index = 0;
const itemHeight = 44; // 각 항목의 높이 (픽셀 단위)
const totalItems = document.querySelectorAll('.rank-item').length;

function moveUp() {
    index++;
    rankBox.style.transition = 'transform 0.3s ease-in-out'; // 빠른 이동
    rankBox.style.transform = `translateY(-${itemHeight * index}px)`;

    if (index === totalItems - 1) {
        // 마지막 항목에 도달하면 초기화
        setTimeout(() => {
            rankBox.style.transition = 'none';
            rankBox.style.transform = 'translateY(0)';
            index = 0;
        }, 300); // 이동 시간과 동일하게 설정
    }
}

// 애니메이션 시작
let intervalId = setInterval(moveUp, 2500);

// 마우스 호버 시 애니메이션 정지 및 높이 증가
searchRank.addEventListener('mouseenter', () => {
    clearInterval(intervalId); // 애니메이션 정지
    // transform 초기화 제거하여 현재 위치 유지
    rankBox.style.transform = 'translateY(0)'; // 이 줄을 제거 또는 주석 처리
    rankBox.style.transition = 'none';
    rankWrapper.style.height = 'auto'; // 높이 늘리기
});

// 마우스 호버 해제 시 애니메이션 재개 및 높이 감소
searchRank.addEventListener('mouseleave', () => {
    // 현재 transformY 값을 기반으로 index 업데이트
    const computedStyle = window.getComputedStyle(rankBox);
    const matrix = new DOMMatrixReadOnly(computedStyle.transform);
    const currentTranslateY = matrix.m42; // Y축 변환 값 (px 단위)
    index = Math.round(-currentTranslateY / itemHeight);

    rankWrapper.style.height = `${itemHeight}px`; // 높이 원래대로

    // 애니메이션 재개
    intervalId = setInterval(moveUp, 2500);
});