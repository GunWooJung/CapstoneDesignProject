var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.504937827895866, 126.9576790776909),
        level: 3
    };
// 준원
var map = new kakao.maps.Map(mapContainer, mapOption);
var currentInfowindow = null;
var markers = [];
//11.24추가 시작
var imageGraySrc = '/static/img/gray_marker.svg';   
var imageBlueSrc = '/static/img/blue_marker.svg';
var imageGreenSrc = '/static/img/green_marker.svg';
var imageRedSrc = '/static/img/red_marker.svg';
var imageYellowSrc = '/static/img/yellow_marker.svg'; //11.29 3:39 추가

var imageSize = new kakao.maps.Size(30,30); // 마커이미지의 크기입니다
			 // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
var imageOption = {offset: new kakao.maps.Point(15, 30)};
       		// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다

// 지도에 마커와 인포윈도우를 표시
function displayMarker(locPosition, message) {

    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition
    });

    var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable
    });

    // 인포윈도우를 마커위에 표시
    infowindow.open(map, marker);

    // 지도 중심좌표를 접속위치로 변경
    map.setCenter(locPosition);
}

function clearMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
    // closeCurrentOverlay(); // 이거 없애면 일단 오버레이는 안없어짐
}

//위 주석 처리는 프론트에서 쓰던거, 아래 부분은 백엔드 코드
function convertToPlaceFormat(dbData) {
	//11.24 placecontainer로 변경
    return dbData.map(place => {
	return{
        id: place.id,
        name: place.name,
        address: place.address,
        lat: place.lat,
        lng: place.lng,
        opentime: place.openTime,
        averageOfStarRating: place.starAverage,
        numberOfStarRating: place.starCount,
        numberOfComments: place.commentCount,
		color : place.color
 	 	};
	});
}

function handleMarkerClick(marker) {
    const useBackend = true; // 백엔드 쓸때는 true로 바꿔
	var place = marker.data;
	//console.log('Clicked Marker ID:', place.id);
    if (useBackend) {
		const token = localStorage.getItem('jwtToken');
		
        fetch(`/api/public/places/${place.id}`)
            .then(response => response.json())
            .then(place => {
			const convertedData = {
			  id: place.data.id,
		      name: place.data.name,
		      address: place.data.address,
		      lat: place.data.lat,
		      lng: place.data.lng,
		      opentime: place.data.openTime,
		      averageOfStarRating: place.data.starAverage,
		      numberOfStarRating: place.data.starCount,
		      numberOfComments: place.data.commentCount,
			};
                createAndShowOverlay(convertedData);
            })
            .catch(error => {
                console.error('Error fetching place details:', error);
            });
    } else {
        createAndShowOverlay(mockData);
    }
}


function createAndShowOverlay(placeData) {
    const overlay = createPlaceOverlay(placeData, map);
    if (window.currentOverlay) {
        window.currentOverlay.setMap(null);
    }
    overlay.setMap(map);
    window.currentOverlay = overlay;
}


function markPlaces(places) {
    clearMarkers();
    //11.24추가 markerImage
    var markerImageGray = new kakao.maps.MarkerImage(imageGraySrc, imageSize, imageOption);
    var markerImageBlue = new kakao.maps.MarkerImage(imageBlueSrc, imageSize, imageOption);
    var markerImageGreen = new kakao.maps.MarkerImage(imageGreenSrc, imageSize, imageOption);
    var markerImageRed = new kakao.maps.MarkerImage(imageRedSrc, imageSize, imageOption);
 	var markerImageYellow = new kakao.maps.MarkerImage(imageYellowSrc, imageSize, imageOption);
       places.forEach(function (place) { //11.24 plcacecontainer로 변경
    var markerPosition = new kakao.maps.LatLng(place.lat, place.lng);
    //11.24 마커 색상 조건
    var marker;// 0은 회색 , 1은 파란색 , 2는 초록색 , 3은 빨강 ,4는 노란색
    //console.log(place.color);
    if( place.color === 'YELLOW'){
        marker =  new kakao.maps.Marker({
            position: markerPosition,
            title: place.name ,
            image: markerImageYellow 	//11.24추가 markerImage
        });
    }
    else if( place.color === 'RED'){
        marker =  new kakao.maps.Marker({
            position: markerPosition,
            title: place.name ,
            image: markerImageRed 	//11.24추가 markerImage
        });
    }
    else if(place.color === 'GREEN'){
        marker =  new kakao.maps.Marker({
            position: markerPosition,
            title: place.name ,
            image: markerImageGreen 	//11.24추가 markerImage
        });
    }
    else if(place.color === 'BLUE'){
        marker =  new kakao.maps.Marker({
            position: markerPosition,
            title: place.name ,
            image: markerImageBlue 	//11.24추가 markerImage
        });
    }
    else{
            marker =  new kakao.maps.Marker({
            position: markerPosition,
            title: place.name 
        });
    }
	        marker.setMap(map);
	        markers.push(marker);
	        marker.data = place;
	        kakao.maps.event.addListener(marker, 'click', function () {
            handleMarkerClick(marker);
        });
    });
}

function searchNearby(keyword, location) {
    fetch(`/api/public/places/search?keyword=${keyword}&lat=${location.getLat()}&lng=${location.getLng()}`)
        .then(response => response.json())
        .then(data => {

            const convertedData = convertToPlaceFormat(data.data);
            //11.28 추가
			console.log(convertedData);
			
			//11.28 추가
            markPlaces(convertedData);
            if (convertedData.length > 0) {
                map.panTo(new kakao.maps.LatLng(convertedData[0].lat, convertedData[0].lng));
            saveCurrentMapCenter();
          }
        })
        .catch(error => {
			alert("검색 결과가 존재하지 않습니다.");
            console.error("Error fetching places:", error);
        });
}

//백엔드에서 정보 가져오기
function fetchPlacesFromBackend(lat, lng) {

    fetch(`/api/public/places`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            disabledPerson: document.getElementById('disabled_person').checked,
            changingTableMan: document.getElementById('changing_table_man').checked,
            changingTableWoman: document.getElementById('changing_table_woman').checked,
            emergencyBellMan: document.getElementById('emergency_bell_man').checked,
            emergencyBellWoman: document.getElementById('emergency_bell_woman').checked,
            emergencyBellDisabled: document.getElementById('emergency_bell_disabled').checked,
            lat: lat,
            lng: lng,
            leftValue: parseFloat(document.getElementById('sign-left').innerHTML),
            rightValue: parseFloat(document.getElementById('sign-right').innerHTML),
            rated: document.getElementById('rated').checked,
            notRated: document.getElementById('not_rated').checked
        })
    })
    .then(response => response.json())
    .then(data => {
        // convertToPlaceFormat 함수를 이용해 백엔드로부터 받은 데이터를 마커로 변환
        const convertedData = convertToPlaceFormat(data.data);
        markPlaces(convertedData);
    })
    .catch(error => {
        console.error('Error fetching filtered places:', error);
    });


}

function updateCenterAndSearch() {
	let keyword = document.getElementById('keyword').value;
	if (keyword.trim() !== '') {
	    return;
	} 
    var center = map.getCenter();
    clearMarkers();
    fetchPlacesFromBackend(center.getLat(), center.getLng());
}

function saveCurrentMapCenter() {
    var center = map.getCenter();
    sessionStorage.setItem('lastViewedPlace', JSON.stringify({lat: center.getLat(), lng: center.getLng()}));
}

// 지도 드래그 이벤트에 대한 리스너 추가
kakao.maps.event.addListener(map, 'dragend', function () {
    saveCurrentMapCenter(); // 지도 이동 후 세션 스토리지 업데이트
    updateCenterAndSearch();
});

// 페이지 로드 시 마지막으로 본 위치로 지도 중심 설정
document.addEventListener('DOMContentLoaded', function () {
    const lastViewedPlace = JSON.parse(sessionStorage.getItem('lastViewedPlace'));
    if (lastViewedPlace) {
        map.setCenter(new kakao.maps.LatLng(lastViewedPlace.lat, lastViewedPlace.lng));
    } 
    
    updateCenterAndSearch();
});