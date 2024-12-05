/*
if (navigator.geolocation) {

//     // GeoLocation을 이용해서 접속 위치 얻어오기
     navigator.geolocation.getCurrentPosition(function (position) {

         var lat = position.coords.latitude, 
             lon = position.coords.longitude; 

         var locPosition = new kakao.maps.LatLng(lat, lon),
             message = '<div style="padding:5px;">여기에 계신가요?!</div>'; 

         displayMarker(locPosition, message);

     });

 } else {

     var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
         message = 'geolocation을 사용할수 없어요..'

     displayMarker(locPosition, message);
 }

function initializeMap() {
    const lastViewedPlace = JSON.parse(sessionStorage.getItem('lastViewedPlace'));
    if (lastViewedPlace) {
        var lastPosition = new kakao.maps.LatLng(lastViewedPlace.lat, lastViewedPlace.lng);
        map.setCenter(lastPosition);
    } else if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치 얻어오기
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude,
                lon = position.coords.longitude;
            var locPosition = new kakao.maps.LatLng(lat, lon),
                message = '<div style="padding:5px;">여기에 계신가요?!</div>';
            displayMarker(locPosition, message);
            map.setCenter(locPosition);
        });
    } else {
        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
            message = 'geolocation을 사용할 수 없어요..'
        displayMarker(locPosition, message);
    }
}
*/

// function moveToCurrentLocation() {
//     if (navigator.geolocation) {
//         navigator.geolocation.getCurrentPosition(function (position) {
//             var lat = position.coords.latitude,
//                 lng = position.coords.longitude;
//             var newCenter = new kakao.maps.LatLng(lat, lng);
//             map.setCenter(newCenter);
//         }, function (error) {
//             console.error("Error: " + error.message);
//         });
//     } else {
//         console.error("Geolocation is not supported by this browser.");
//     }
// }


var location_marker_displayed = false; // 현재 위치 마커 존재 여부
var location_marker = null;  // 현재 위치 마커 
//11.24추가 markerHomeImage
var imageHomeSrc = '/static/img/home_marker.png',   
    imageHomeSize = new kakao.maps.Size(50, 50), //이전 값 30, 30
    
    imageHomeOption = { offset: new kakao.maps.Point(0, 30) }; // 이전값 15, 15
// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다

function moveToCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude,
                lng = position.coords.longitude;
            var newCenter = new kakao.maps.LatLng(lat, lng);
            map.setCenter(newCenter);
            saveCurrentMapCenter(); //11.24 추가
            //11.24추가 markerImage
            var markerImage = new kakao.maps.MarkerImage(imageHomeSrc, imageHomeSize, imageHomeOption);
            if (location_marker_displayed == false) {
                location_marker = new kakao.maps.Marker({
                    position: newCenter,
                    image: markerImage 	//11.24추가 markerImage
                });
                location_marker.setMap(map);   //현재 위치 마커 생성
                location_marker_displayed = true;   //현재 위치 마커 존재로 변경
            }
            else {  //true
                location_marker.setMap(null);   // 기존 위치 마커 삭제
                location_marker = new kakao.maps.Marker({
                    position: newCenter,
                    image: markerImage 	//11.24추가 markerImage
                });
                location_marker.setMap(map); // 새로운 위치 마커 생성
            }
            // 현재 위치를 기반으로 주변 화장실 데이터 불러오기
            fetchPlacesFromBackend(lat, lng)
                .then(response => response.json())
                .then(response => {
                    const convertedData = convertToPlaceFormat(data);
                    clearMarkers();
                    markPlaces(convertedData);
                });
        }, function (error) {
            console.error("Error: " + error.message);
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
}

document.getElementById('recenter-map').addEventListener('click', function () {
    moveToCurrentLocation();
});
