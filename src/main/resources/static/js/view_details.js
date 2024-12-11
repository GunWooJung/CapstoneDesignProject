function initMap(place) {
	var mapContainer = document.getElementById('map');
	var mapOption = {
		center: new kakao.maps.LatLng(place.lat, place.lng),
		level: 3,
		draggable: false // 드래그 기능 비활성화
	};

	var map = new kakao.maps.Map(mapContainer, mapOption);

	// 지도 중심에 마커 표시
	new kakao.maps.Marker({
		map: map,
		position: map.getCenter()
	});
}

function displayComments(comments) {
	const commentsContainer = document.getElementById('reviews');
	commentsContainer.innerHTML = ''; // Clear existing comments
	if(comments != null){
		comments.forEach(comment => {
			const dateOnly = comment.createdDate.substring(0, 10);
	
			const commentElement = document.createElement('div');
			commentElement.className = 'comment-item';
			if( comment.mine === true)
			commentElement.innerHTML = `
	         <div class = "review_component">
	             <div class = "review_comment_container">
	                 <div class = "comment_container_detail">
	                     <div class = "review_comment_id">${comment.name}</div>
	                     <div class = "review_comment_bar"></div>
	                     <div class = "review_comment_date">${dateOnly}</div>  
	                 </div>
	                <div class="deleteComment" data-member-id="${comment.memberId}" data-comment-id="${comment.id}" style="cursor: pointer">&times</div>
	             </div>
	             <div class = "comment_container_content">${comment.content}</div>
	         </div>
	         `;
			 else
			 commentElement.innerHTML = `       
	          <div class = "review_component">
	              <div class = "review_comment_container">
	                  <div class = "comment_container_detail">
	                      <div class = "review_comment_id">${comment.name}</div>
	                      <div class = "review_comment_bar"></div>
	                      <div class = "review_comment_date">${dateOnly}</div>  
	                  </div>
	              </div>
	              <div class = "comment_container_content">${comment.content}</div>
	          </div>
	          `;
			commentsContainer.appendChild(commentElement);
		});
	}
}

document.addEventListener('DOMContentLoaded', function() {

	// 현재 URL 가져오기
	const path = window.location.pathname;

	// "/"를 기준으로 경로 분리
	const segments = path.split("/");

	var placeId = null;
	// 경로가 "/places/:id" 형식인지 확인
	if (segments.length > 2 && segments[1] === "places") {
	    placeId = segments[2]; // ID 추출
	} else {
		window.location.href = `/`;
	}
	const amendLink = document.getElementById('amendLink');

	document.getElementById('backButton').addEventListener('click', function() {
		window.location.href = `/`; // 현재 창에서 index.html로 이동
	});

	amendLink.addEventListener('click', function(event) {
		// 기본 동작(링크 이동) 방지
		event.preventDefault();
		window.location.href = `/places/${placeId}/amend`;
	});

	//place details 준원
	fetch(`/api/public/places/${placeId}`,{
		method: 'GET',
		 headers: {
			'Content-Type': 'application/json'
		 },
	})
		.then(response => response.json())
		.then(place => {

			const average = place.data.starAverage;

			document.getElementById('placeName').textContent = place.data.name;
			document.getElementById('starRating').textContent = Number.isInteger(average) ? average + ".0" : average.toFixed(1);
			document.getElementById('address').textContent = place.data.address;
			document.getElementById('opentime').textContent = place.data.openTime;
			document.getElementById('starCount').textContent = place.data.starCount;


			const disabledManIcon = document.getElementById('disabled_person').querySelector('img');
			disabledManIcon.src = place.data.disabledPerson === true ? '/static/public/disabled_man_colored.png' : '/static/public/disabled_man_gray.png';

			const diaperManIcon = document.getElementById('diaper_man').querySelector('img');
			diaperManIcon.src = place.data.changingTableMan == true ? '/static/public/diaper_man_colored.png' : '/static/public/diaper_man_gray.png';

			const diaperWomanIcon = document.getElementById('diaper_woman').querySelector('img');
			diaperWomanIcon.src = place.data.changingTableWoman == true ? '/static/public/diaper_woman_colored2.png' : '/static/public/diaper_woman_gray.png';

			const bellManIcon = document.getElementById('bell_man').querySelector('img');
			bellManIcon.src = place.data.emergencyBellMan === true ? '/static/public/bell_man_colored.png' : '/static/public/bell_man_gray.png';

			const bellWomanIcon = document.getElementById('bell_woman').querySelector('img');
			bellWomanIcon.src = place.data.emergencyBellWoman === true ? '/static/public/bell_woman_colored2.png' : '/static/public/bell_woman_gray.png';

			const bellDisabledIcon = document.getElementById('bell_disabled').querySelector('img');
			bellDisabledIcon.src = place.data.emergencyBellDisabled === true ? '/static/public/bell_disabled_colored3.png' : '/static/public/bell_disabled_gray.png';

			initMap(place.data);

		})
		.catch(error => {
			console.error('Error:', error);
		});




	fetch(`/api/places/${placeId}/comments`,{
			method: 'GET',
			 headers: {
				Authorization: 'Bearer '+localStorage.getItem('jwtToken'), // JWT를 Authorization 헤더에 추가
		   	   'Content-Type': 'application/json'
			 },
		})
		.then(response => response.json())
		.then(comments => {
			if(comments.status == 200){
			displayComments(comments.data);
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});


	document.getElementById('reviews').addEventListener('click', function(event) {
		if (event.target.classList.contains('deleteComment')) {
			const commentId = event.target.getAttribute('data-comment-id');
			const memberId = event.target.getAttribute('data-member-id');
			deleteComment(commentId, memberId);
		}
	});

	// Function to handle comment deletion
	function deleteComment(commentId, memberId) {
	    // Ensure placeId is passed correctly
	    if (!placeId) {
	        alert('장소 ID가 필요합니다.');
	        return;
	    }

	    fetch(`/api/places/${placeId}/comments/${commentId}?memberId=${memberId}`, {
	        method: 'DELETE',
			headers: {
				Authorization: 'Bearer '+localStorage.getItem('jwtToken'), // JWT를 Authorization 헤더에 추가
		   	   'Content-Type': 'application/json'
			 },
	    })
	    .then(response => {
	        if (!response.ok) { // 응답이 성공적이지 않으면 예외 처리
	            throw new Error('댓글 삭제에 실패했습니다.');
	        }
	        return response.json(); // JSON으로 응답 파싱
	    })
	    .then(response => {
	        if (response.status === 200) {
	            // 댓글 삭제 성공 처리
	            alert('댓글이 삭제되었습니다.');
	            location.reload(); // 페이지 리로드
        	}else {
	            throw new Error('댓글 삭제 실패: ' + response.message);
	        }
	    })
	    .catch(error => {
	        // 오류 발생 시 에러 메시지 출력
	        alert('댓글 삭제 실패');
	    });
	}


	// 제출 버튼 클릭
	document.getElementById('submitReview').addEventListener('click', function() {
		//const username = document.getElementById('username').value;
		//const password = document.getElementById('password').value;
		const reviewText = document.getElementById('reviewText').value;
		if (!reviewText) {
		    alert('입력하지 않은 칸이 존재합니다.');
		    return; // 빈칸이 있을 경우 폼 제출을 막음
		}
		
		const commentData = {
			placeId: placeId,
			content: reviewText
		};

		// 입력한 리뷰 보내기
		fetch(`/api/places/${placeId}/comments`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				 Authorization: 'Bearer '+localStorage.getItem('jwtToken') // JWT를 Authorization 헤더에 추가
			},
			body: JSON.stringify(commentData)
		})
			.then(response => {
				return response.json(); // Assuming the server responds with plain text
			})
			.then(body => {
				if (body.status == 200){
					// Handle successful submission	 
					document.getElementById('reviewText').value = '';
					// Uncomment the following lines if you want to display an alert and reload the page
					alert('댓글이 등록되었습니다.');
					location.reload();
				}else if (body.status == 409){
					alert('이미 댓글을 등록했습니다.');
				}
			});
	});


	// 별점 제출
	document.getElementById('submitRating').addEventListener('click', function() {
		const selectedRating = document.querySelector('input[name="rating"]:checked').value;
		const ratingData = {
			placeId: placeId,
			score: selectedRating
		};

		console.log('Selected Star Rating:', selectedRating);

		fetch(`/api/star-rating`, {
			method: 'POST',
			headers: {
				Authorization: 'Bearer '+localStorage.getItem('jwtToken'), // JWT를 Authorization 헤더에 추가
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(ratingData)
		})
		.then(response => response.json())
		.then(response => {
				// Handle successful submission
				// Uncomment the following lines if you want to display an alert and reload the page
				if(response.status == 200){
					alert(`제출된 별점은 ${selectedRating}점 입니다.`);
					location.reload();
				}else if (response.status === 409) {
		           alert('이미 별점을 등록했습니다.'); // 409 상태일 때 예외 던지기
		       } 
			   else {
		          alert('문제가 발생했습니다.'); // 기타 오류 처리
		       }
		}).catch((e) => alert('문제가 발생했습니다.'));

	});
});