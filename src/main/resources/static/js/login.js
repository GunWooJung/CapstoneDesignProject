$(function () {		
    
		$('form').on('submit', function (event) {
			event.preventDefault();

			$('#idDiv').empty();
			$('#passwordDiv').empty();
			let isValid = true;

			// 아이디 검사
			if ($('#loginId').val() == '') {
				$('#idDiv').html('아이디를 입력하세요.').css('color', 'red');
				$('#loginId').focus();
				isValid = false;
			}
			// 비밀번호 검사
			if ($('#password').val() == '') {
				$('#passwordDiv').html('비밀번호를 입력하세요.').css('color', 'red');
				$('#password').focus();
				isValid = false;
			}
			let formData = new FormData();

			// 데이터를 추가
			formData.append('username', document.getElementById('loginId').value.trim());
			formData.append('password', document.getElementById('password').value.trim());
			
			// 유효성 검사를 모두 통과한 경우에만 ajax 요청 실행한다.
			if (isValid) {
				$.ajax({
					type: 'post',
					url: '/api/members/login',
					data: formData, 
					processData: false, // FormData 사용 시 필수
					contentType: false, // FormData의 Content-Type 자동 설정
					success: function(data, textStatus, jqXHR) {
						console.log(data);
						 if(jqXHR.status === 200){ // 200 OK
							alert('로그인에 성공하였습니다!');
							// 로그인 후 리디렉션 또는 페이지 갱신 등 추가 작업
							window.location.href = "/";  // 예: 홈 페이지로 리디렉션
						} 
					},
					error: function(xhr, textStatus, errorThrown) {
						//	console.log(e);
						if (xhr.status === 401) {	// Unauthorized  
							alert('아이디 또는 비밀 번호가 일치하지 않습니다.');
						}else{
							alert('로그인 오류');
						}
					}
				});
			}
		});
	});