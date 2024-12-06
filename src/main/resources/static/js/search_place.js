

function performNewSearch(keyword) {
    var center = map.getCenter();
    clearMarkers();
    searchNearby(keyword, center);
    saveCurrentMapCenter(); // 검색 후 중심 위치 저장
}

// 검색 버튼 클릭 이벤트
document.getElementById('search-button').addEventListener('click', function () {
    var keyword = document.getElementById('keyword').value;
    if (keyword != null && keyword.trim() !== '') {
        performNewSearch(keyword);
    } else {
        alert('검색어를 입력해주세요.'); //11.28 수정
    }
});

// 키보드 엔터키 이벤트
document.getElementById('keyword').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        var keyword = document.getElementById('keyword').value;
        if (keyword.trim() !== '') {
            performNewSearch(keyword);
        } else {
            alert('검색어를 입력해주세요.'); //11.28 수정
        }
    }
});

// 지도 중심 위치를 세션 스토리지에 저장
function saveCurrentMapCenter() {
    var center = map.getCenter();
    sessionStorage.setItem('lastViewedPlace', JSON.stringify({ lat: center.getLat(), lng: center.getLng() }));
}

// 지우기
document.getElementById('close-button').addEventListener('click', function () {
    document.getElementById('keyword').value = '';
});

document.getElementById('show-all-button').addEventListener('click', function () {
    document.getElementById('keyword').value = '';
	updateCenterAndSearch();
});

