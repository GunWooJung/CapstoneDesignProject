// 변경 사항 없음 11-16, 20시 45분
// 모달 가져오기
var modal = document.getElementById('myModal');

// 모달을 여는 버튼
var filter_button = document.getElementById('filter');

// 모달을 닫는 close 버튼
var close_button = document.getElementsByClassName('close')[0];

// 사용자가 버튼을 클릭하면 모달을 열기
filter_button.onclick = function () {
    modal.style.display = 'block';
}

// 모달을 닫기
close_button.onclick = function () {
    modal.style.display = 'none';
}

// 사용자가 모달 외부를 클릭하면 모달 닫기
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}

// 리셋 버튼
document.getElementById('reset-filter').addEventListener('click', function () {
    document.querySelectorAll('#myModal .content_body_choice input[type="checkbox"]').forEach(function (checkbox) {
        checkbox.checked = false;
    });
    resetSlider();
});

// 적용 버튼
document.getElementById('apply-filter').addEventListener('click', function () {
    // Create an object to hold the state of the checkboxes
    var center = map.getCenter();
    clearMarkers();
    fetchPlacesFromBackend(center.getLat(), center.getLng());
    modal.style.display = 'none';
});