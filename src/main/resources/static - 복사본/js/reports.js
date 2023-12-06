document.addEventListener('DOMContentLoaded', function () {
    const placeId = new URLSearchParams(window.location.search).get('id');

    document.querySelector('.amend_info_container .close').addEventListener('click', function () {
    
        window.location.href = `/viewdetails?id=${placeId}`;
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
                formData["placeId"] = placeId;
                // 라디오 버튼 입력 처리
                var radioInputs = modal.querySelectorAll('input[type="radio"]:checked');
                radioInputs.forEach(function (input) {
                    formData[input.name] = input.value;
                });

                // 텍스트 입력 처리
                var textInputs = modal.querySelectorAll('textarea');
                textInputs.forEach(function (input) {
                    formData[input.name] = input.value;
                });

                // 서버로 폼 데이터 전송
                console.log('Submitting form data:', Object.fromEntries(formData.entries()));
                fetch('/report/enroll', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(formData)
                })
               .then(response => {
						    return response.text(); // Assuming the server responds with plain text
			})
			.then(body => {
			   if (body.trim().toLowerCase() === "ip") {
						        // Handle duplicated IP case
					   alert('이미 신고를 제출하였습니다.');
				} 
				else if (body.trim().toLowerCase() === "fail") {
						        // Handle duplicated IP case
					   alert('신고 제출에 실패하였습니다.');
				} 
				else if (body.trim().toLowerCase() === "user_opinion") {
						        // Handle duplicated IP case
					   alert('의견이 전달되었습니다.');
				} 
                else if (body.trim().toLowerCase() === "count3") {
                    alert('신고 누적으로 신고 내용이 반영되었습니다.');
                    location.reload();
                }
				else {
				       // Handle successful submission	        // Uncomment the following lines if you want to display an alert and reload the page
				 alert('신고를 제출하였습니다.');
				  location.reload();
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