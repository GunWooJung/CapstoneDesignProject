document.addEventListener('DOMContentLoaded', function () {
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
	document.querySelector('.amend_info_container .close').addEventListener('click', function () {
	    window.location.href = `/places/${placeId}`;
	});

    function setupModalEventListeners(modal) {

        modal.querySelector('.close').addEventListener('click', function () {
            modal.style.display = 'none';
        });

        var closeFooterButton = modal.querySelector('.report_container_footer_left');
        if (closeFooterButton) {
            closeFooterButton.addEventListener('click', function () {
                modal.style.display = 'none';

            });
        }


        // '제출하기' 버튼에 대한 이벤트 리스너 설정
        var submitButton = modal.querySelector('.report_container_footer_right');
        if (submitButton) {
            submitButton.addEventListener('click', function () {
				var formData = new FormData();
                 // 라디오 버튼 입력 처리
                 var radioInputs = modal.querySelectorAll('input[type="radio"]:checked');
                 radioInputs.forEach(function (input) {
                     formData["type"] = input.name;
  					 formData["content"] = input.value;
                 });

                 // 텍스트 입력 처리
                 var textInputs = modal.querySelectorAll('textarea');
                 textInputs.forEach(function (input) {
					formData["type"] = input.name;
					formData["content"] = input.value;
                 });

                // 서버로 폼 데이터 전송
                console.log('Submitting form data:', Object.fromEntries(formData.entries()));
                fetch(`/api/places/${placeId}/reports`, {
                    method: 'POST',
					headers: {
							Authorization: 'Bearer '+localStorage.getItem('jwtToken'), // JWT를 Authorization 헤더에 추가
							'Content-Type': 'application/json'
					},
                    body: JSON.stringify(formData)
                })
               .then(response => {
						    return response.json(); // Assuming the server responds with plain text
			})
			.then(body => {
				if(body.status === 200){
	                alert('신고가 제출되었습니다.');
	                location.reload();
	            }else if(body.status === 409){
					 alert('이미 신고가 제출되었습니다.');
					 location.reload();
				}
				else{
					alert('신고가 실패했습니다.');
				}
			});
                    
                modal.style.display = 'none'; // 폼 전송 후 모달 닫기
            });
        }
    }

    // 모달 외부 클릭 시 모달 닫기 이벤트 리스너
    window.onclick = function (event) {
        var modal = document.getElementById('reportModal');
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };

    // 각 보고서 항목 클릭 시 모달 로드 및 이벤트 리스너 설정
    document.querySelectorAll('.body_content_submit').forEach(item => {
        item.addEventListener('click', function () {
            const reportPage = this.getAttribute('data-target');
            fetch(reportPage)
                .then(response => response.text())
                .then(html => {
                    var modal = document.getElementById('reportModal');
                    var modalContent = document.getElementById('reportModalContent');
                    modalContent.innerHTML = html;
                    setupModalEventListeners(modal);
                    modal.style.display = 'flex';
                })
                .catch(error => {
                    console.error('Error loading modal content:', error);
                });
        });
    });
});