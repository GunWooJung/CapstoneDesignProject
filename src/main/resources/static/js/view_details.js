document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const placeId = urlParams.get('id');
    const amendLink = document.getElementById('amendLink');


    document.getElementById('backButton').addEventListener('click', function () {
        window.location.href = `/`; // 현재 창에서 index.html로 이동
    });

    // document.getElementById('backButton').addEventListener('click', function () {
    //     const urlParams = new URLSearchParams(window.location.search);
    //     const placeId = urlParams.get('id');

    //     fetch(`/place/detail?id=${placeId}`)
    //         .then(response => response.json())
    //         .then(place => {
    //             sessionStorage.setItem('lastViewedPlace', JSON.stringify({lat: place.latitude, lng: place.longitude}));
    //             window.location.href = '/';
    //         })
    //         .catch(error => {
    //             console.error('Error:', error);
    //         });
    // });



    amendLink.addEventListener('click', function (event) {
        // 기본 동작(링크 이동) 방지
        event.preventDefault();
        // window.open(`/amend_information?id=${placeId}`, '_blank');
        window.location.href = `/amend_information?id=${placeId}`;

    });


    //// 이 부분 추가함 11-19, 19시 38분
    function initMap(place) {
        var mapContainer = document.getElementById('map');
        var mapOption = {
            center: new kakao.maps.LatLng(place.latitude, place.longitude),
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

    // function updateEmergencyBell(bellString) {
    //     const hasBellMan = bellString.indexOf('남자화장실') !== -1;
    //     const hasBellWoman = bellString.indexOf('여자화장실') !== -1;
    //     const hasBellDisabled = bellString.indexOf('장애인화장실') !== -1;

    //     document.getElementById('bell_man').textContent = hasBellMan ? '있음' : '없음';
    //     document.getElementById('bell_woman').textContent = hasBellWoman ? '있음' : '없음';
    //     document.getElementById('bell_disabled').textContent = hasBellDisabled ? '있음' : '없음';
    // }

    //place details 준원
    fetch(`/place/detail?id=${placeId}`)
        .then(response => response.json())
        .then(place => {

            const average = place.star_average;

            document.getElementById('placeName').textContent = place.name;
            //document.getElementById('starRating').textContent = place.star_average;
            document.getElementById('starRating').textContent = Number.isInteger(average) ? average + ".0" : average;
            document.getElementById('address').textContent = place.address;
            //document.getElementById('opentime').textContent = place.opentime;
            document.getElementById('starCount').textContent = place.star_count;


            const disabledManIcon = document.getElementById('disabled_man').querySelector('img');
            disabledManIcon.src = place.disabled_man === "있음" ? '/public/disabled_man_colored.png' : '/public/disabled_man_gray.png';
   
            const disabledWomanIcon = document.getElementById('disabled_woman').querySelector('img');
            disabledWomanIcon.src = place.disabled_woman === "있음" ? '/public/disabled_woman_colored2.png' : '/public/disabled_woman_gray.png';


            const diaperManIcon = document.getElementById('diaper_man').querySelector('img');
            diaperManIcon.src = place.diaper.indexOf("남") !== -1 ? '/public/diaper_man_colored.png' : '/public/diaper_man_gray.png';

            const diaperWomanIcon = document.getElementById('diaper_woman').querySelector('img');
            diaperWomanIcon.src = place.diaper.indexOf("여") !== -1 ? '/public/diaper_woman_colored2.png' : '/public/diaper_woman_gray.png';


            const bellManIcon = document.getElementById('bell_man').querySelector('img');
            bellManIcon.src = place.emergency_bell.indexOf("남") !== -1 ? '/public/bell_man_colored.png' : '/public/bell_man_gray.png';

            const bellWomanIcon = document.getElementById('bell_woman').querySelector('img');
            bellWomanIcon.src = place.emergency_bell.indexOf("여") !== -1 ? '/public/bell_woman_colored2.png' : '/public/bell_woman_gray.png';

            const bellDisabledIcon = document.getElementById('bell_disabled').querySelector('img');
            bellDisabledIcon.src = place.emergency_bell.indexOf("장애") !== -1 ? '/public/bell_disabled_colored3.png' : '/public/bell_disabled_gray.png';


            // if (place.disabled_man == "있음")
            //     document.getElementById('disabled_man').textContent = 1;
            // else
            //     document.getElementById('disabled_man').textContent = 0;
            // if (place.disabled_woman == "있음")
            //     document.getElementById('disabled_woman').textContent = 1;
            // else
            //     document.getElementById('disabled_woman').textContent = 0;


            // if (place.diaper.indexOf("남") != -1)
            //     document.getElementById('diaper_man').textContent = 1;
            // else
            //     document.getElementById('diaper_man').textContent = 0;
            // if (place.diaper.indexOf("여") != -1)
            //     document.getElementById('diaper_woman').textContent = 1;
            // else
            //     document.getElementById('diaper_woman').textContent = 0;


            // if (place.emergency_bell.indexOf("장애") != -1)
            //     document.getElementById('bell_disabled').textContent = 1;
            // else
            //     document.getElementById('bell_disabled').textContent = 0;
            // if (place.emergency_bell.indexOf("여") != -1)
            //     document.getElementById('bell_woman').textContent = 1;
            // else
            //     document.getElementById('bell_woman').textContent = 0;
            // if (place.emergency_bell.indexOf("남") != -1)
            //     document.getElementById('bell_man').textContent = 1;
            // else
            //     document.getElementById('bell_man').textContent = 0;
            initMap(place);

        })
        .catch(error => {
            console.error('Error:', error);
        });


    // 프론트 css를 위한 더미 데이터
    // const dummyComments = [
    //     { username: "사용자1", content: "댓글 내용 1", id: "1", createDate: "2023-11-25" },
    //     { username: "사용자2", content: "댓글 내용 2", id: "2", createDate: "2023-11-24" },
    //     { username: "사용자3", content: "댓글 내용 3", id: "3", createDate: "2023-11-23" },
    //     { username: "사용자1", content: "댓글 내용 1", id: "1", createDate: "2023-11-25" },
    //     { username: "사용자2", content: "댓글 내용 2", id: "2", createDate: "2023-11-24" },
    //     { username: "사용자3", content: "댓글 내용 3", id: "3", createDate: "2023-11-23" },
    //     { username: "사용자1", content: "댓글 내용 1", id: "1", createDate: "2023-11-25" },
    //     { username: "사용자2", content: "댓글 내용 2", id: "2", createDate: "2023-11-24" },
    //     { username: "사용자3", content: "댓글 내용 3", id: "3", createDate: "2023-11-23" },
    //     { username: "사용자1", content: "댓글 내용 1", id: "1", createDate: "2023-11-25" },
    //     { username: "사용자2", content: "댓글 내용 2", id: "2", createDate: "2023-11-24" },
    //     { username: "사용자3", content: "댓글 내용 3", id: "3", createDate: "2023-11-23" },
        
    //     // 기타 더미 코멘트
    // ];

    // displayComments(dummyComments);
     



    // function displayComments(comments) {
    //     const commentsContainer = document.getElementById('reviews');
    //     commentsContainer.innerHTML = ''; // Clear existing comments
    //     comments.forEach(comment => {
    //         const commentElement = document.createElement('div');
    //         commentElement.className = 'comment-item';
    //         commentElement.innerHTML = `
            
    //             <span>${comment.username} : ${comment.content}</span>
    //             <button class="deleteComment" data-comment-id="${comment.id}">X</button>
    //         `;
    //         commentsContainer.appendChild(commentElement);
    //     });
    // }

    function displayComments(comments) {
        const commentsContainer = document.getElementById('reviews');
        commentsContainer.innerHTML = ''; // Clear existing comments
        comments.forEach(comment => {
            const dateOnly = comment.createDate.substring(0, 10);

            const commentElement = document.createElement('div');
            commentElement.className = 'comment-item';
            commentElement.innerHTML = `
            
            <div class = "review_component">
                <div class = "review_comment_container">
                    <div class = "comment_container_detail">
                        <div class = "review_comment_id">${comment.username}</div>
                        <div class = "review_comment_bar"></div>
                        <div class = "review_comment_date">${dateOnly}</div>  
                    </div>
                   <div class="deleteComment" data-comment-id="${comment.id}" style="cursor: pointer">&times</div>
                </div>
                <div class = "comment_container_content">${comment.content}</div>
            </div>
            `;
            commentsContainer.appendChild(commentElement);
        });
    }

    fetch(`/comment/show?id=${placeId}`)
        .then(response => response.json())
        .then(comments => {
            displayComments(comments);
        })
        .catch(error => {
            console.error('Error:', error);
        });


    document.getElementById('reviews').addEventListener('click', function (event) {
        if (event.target.classList.contains('deleteComment')) {
            const commentId = event.target.getAttribute('data-comment-id');
            const userPassword = prompt('비밀번호를 입력해주세요:');
            if (userPassword !== null && userPassword !== '') {
                deleteComment(commentId, userPassword);
            }
        }
    });

    // Function to handle comment deletion
    function deleteComment(commentId, password) {
        // Call your backend delete endpoint
        fetch(`/comment/delete?id=${commentId}`, {
            method: 'POST', // Use POST if your endpoint supports it
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                placeId: placeId,
                password: password
            })
        })
            //11.20 수정
            .then(response => {
                return response.text(); // Assuming the server responds with plain text
            })
            .then(body => {
                if (body.trim().toLowerCase() === "fail") {
                    alert('비밀 번호가 안맞습니다.');
                }
                else {
                    // Handle successful submission	        // Uncomment the following lines if you want to display an alert and reload the page
                    const commentToDelete = document.querySelector(`[data-comment-id="${commentId}"]`).parentNode;
                    commentToDelete.remove();
                    alert('댓글이 삭제되었습니다.');
                    location.reload();
                }
            });
    }

    // 제출 버튼 클릭
    document.getElementById('submitReview').addEventListener('click', function () {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const reviewText = document.getElementById('reviewText').value;
        const commentData = {
            username: username,
            password: password,
            placeId: placeId,
            content: reviewText
        };

        console.log('Username:', username);
        console.log('Password:', password);
        console.log('Review:', reviewText);

        // 입력한 리뷰 보내기
        fetch('/comment/enroll', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentData)
        })
            /* 11.20수정 .then(response => {
                 if (!response.ok) {
                     throw new Error('Network response was not ok');
                 }
                 return response.json();
             })
 
             .then(() => {
                 //리뷰 창 업데이트?
                 const commentsContainer = document.getElementById('reviews');
                 const commentElement = document.createElement('p');
                 commentElement.textContent = `Username: ${username}, Comment: ${reviewText}`;
                 commentsContainer.appendChild(commentElement);
             })
             .catch(error => {
                 console.error('Error:', error);
             });
 */
            .then(response => {
                return response.text(); // Assuming the server responds with plain text
            })
            .then(body => {
                if (body.trim().toLowerCase() === "fail") {
                    // Handle duplicated IP case
                    alert('이미 작성한 댓글이 있습니다.');

                }
                else if (body.trim().toLowerCase() === "blank") {
                    // 빈칸이 존재
                    alert('입력하지 않은 칸이 존재합니다.');
                }
                else {
                    // Handle successful submission	 
                    document.getElementById('username').value = '';
                    document.getElementById('password').value = '';
                    document.getElementById('reviewText').value = '';
                    // Uncomment the following lines if you want to display an alert and reload the page
                    alert('댓글이 등록되었습니다.');
                    location.reload();
                }
            });

        // 리뷰창 비우기

        /* window.onclick = function (event) {
             let modal = document.getElementById('amendModal');
             if (event.target == modal) {
                 modal.style.display = 'none';
             }
         };*/
    });


    // 별점 제출
    document.getElementById('submitRating').addEventListener('click', function () {
        const selectedRating = document.querySelector('input[name="rating"]:checked').value;
        const ratingData = {
            placeId: placeId,
            rating: selectedRating
        };

        console.log('Selected Star Rating:', selectedRating);

        fetch(`/starrating/enroll`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ratingData)

        })
            /*11.20 수정  .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
              .then(() => {
                  // alert으로 별점 제출 알림
                  alert(`제출된 별점: ${selectedRating}점`);
                  location.reload();
              })
              .catch(error => {
                  console.error('Error:', error);
                  alert('별점 제출 중 오류가 발생했습니다.');
              });*/
            .then(response => {
                return response.text(); // Assuming the server responds with plain text
            })
            .then(body => {
                if (body.trim().toLowerCase() === "fail") {
                    // Handle duplicated IP case
                    alert('이미 별점을 제출했습니다.');
                } else {
                    // Handle successful submission
                    // Uncomment the following lines if you want to display an alert and reload the page
                    alert(`제출된 별점: ${selectedRating}점`);
                    location.reload();
                }
            });

    });
});