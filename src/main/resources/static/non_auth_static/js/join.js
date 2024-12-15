
// 이미지 미리보기 함수
function PreviewImage(event) {
	const input = event.target;
	const preview = document.getElementById('imagePreview');

	if (input.files && input.files[0]) {
		const reader = new FileReader();

		reader.onload = function(e) {
			const reset = document.getElementById('reset');
			reset.style.display = 'block';
			preview.src = e.target.result;
			preview.style.display = 'block'; // 미리보기 이미지 보이게 설정
		};

		reader.readAsDataURL(input.files[0]);
	}
}

// 미리보기 초기화 함수
function ClearPreview() {
	const preview = document.getElementById('imagePreview');
	const imageInput = document.getElementById('image');
	const reset = document.getElementById('reset');
	reset.style.display = 'none';
	preview.src = '';
	preview.style.display = 'none';
	imageInput.value = '';
}	



$(function () {
		let isIdAvailable = false;  // 아이디 사용 가능 여부를 저장하는 변수
		let isNameAvailable = false;  // 아이디 사용 가능 여부를 저장하는 변수
		$('#loginId').on('focusout', function () {
			// 아이디 입력값 가져오기
			let user_id = $('#loginId').val();

			// 아이디가 비어 있는지 확인
			if (user_id === '') {
				$('#idDiv').html('아이디를 입력하세요.').css('color', 'red');
				isIdAvailable = false;  // 아이디가 비어 있으면 사용 불가능으로 설정
				return;
			}
			// 아이디 중복검사 
			$.ajax({
				type: 'get',
				url: '/api/public/members/id-check?loginId='+user_id,
				success: function(data, textStatus, jqXHR) {
			    console.log(data);
			       if(jqXHR.status === 200){ // 200 OK 사용 가능
			    		$('#idDiv').html('사용 가능한 아이디입니다.').css('color', 'green');
						isIdAvailable = true;  // 사용 가능하면 true로 설정
		           }
			    },
			    error: function(xhr, textStatus, errorThrown) {
				   //console.log(e);
					if (xhr.status === 409) {	// Conflict 중복
						$('#idDiv').html('이미 사용 중인 아이디입니다.').css('color', 'red');
						isIdAvailable = false;  // 사용 불가능하면 false로 설정
					}
	        	}
			});//ajax 끝
		});
		
		$('#name').on('focusout', function () {
					// 아이디 입력값 가져오기
					let name = $('#name').val();

					// 아이디가 비어 있는지 확인
					if (name === '') {
						$('#nameDiv').html('이름을 입력하세요.').css('color', 'red');
						isNameAvailable = false;  // 아이디가 비어 있으면 사용 불가능으로 설정
						return;
					}
					// 아이디 중복검사 
					$.ajax({
						type: 'get',
						url: '/api/public/members/name-check?name='+name,
						success: function(data, textStatus, jqXHR) {
					    console.log(data);
					       if(jqXHR.status === 200){ // 200 OK 사용 가능
					    		$('#nameDiv').html('사용 가능한 이름입니다.').css('color', 'green');
								isNameAvailable = true;  // 사용 가능하면 true로 설정
				           }
					    },
					    error: function(xhr, textStatus, errorThrown) {
						   //console.log(e);
							if (xhr.status === 409) {	// Conflict 중복
								$('#nameDiv').html('이미 사용 중인 이름입니다.').css('color', 'red');
								isNameAvailable = false;  // 사용 불가능하면 false로 설정
							}
			        	}
					});//ajax 끝
				});

		$('#signup-button').on('click', function (event) {

			$('#passwordDiv').empty();
			$('#confirmPasswordDiv').empty();
			let isValid = true;

			// 이름 검사
			if ($('#name').val() == '') {
				$('#nameDiv').html('이름을 입력하세요.').css('color', 'red');
				$('#name').focus();
				isValid = false;
			}

			// 아이디 검사
			if ($('#loginId').val() == '') {
				$('#idDiv').html('아이디를 입력하세요.').css('color', 'red');
				$('#loginId').focus();
				isValid = false;
			}

			// 비밀번호 검사
			if ($('#password').val().trim() == '') {
				$('#passwordDiv').html('비밀번호를 입력하세요.').css('color', 'red');
				$('#password').focus();
				isValid = false;
			}

			// 비밀번호 확인 검사
			if ($('#confirmPassword').val() == '') {
				$('#confirmPasswordDiv').html('비밀번호 확인을 해주세요.').css('color', 'red');
				$('#confirmPassword').focus();
				isValid = false;
			} else if ($('#password').val() != $('#confirmPassword').val()) {
				$('#confirmPasswordDiv').html('비밀번호가 일치하지 않습니다.').css('color', 'red');
				$('#confirmPassword').focus();
				isValid = false;
			}
			
			// 아이디 중복 여부 검사
			if ($('#loginId').val() != '' && !isIdAvailable) {
				$('#idDiv').html('이미 사용 중인 아이디입니다.').css('color', 'red');
				$('#loginId').focus();
				isValid = false;
			}

			// 이름 중복 여부 검사
			if ($('#name').val() != '' && !isNameAvailable) {
				$('#nameDiv').html('이미 사용 중인 이름입니다.').css('color', 'red');
				$('#name').focus();
				isValid = false;
			}

			let name = $('#name').val().trim();
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val().trim();
			const imageFile=document.getElementById('image').files[0];
			//폼데이터 객체
			const formData = new FormData();
			formData.append('name', name);
			formData.append('loginId', loginId);
			formData.append('password', password);
			if (imageFile) {
				formData.append('image', imageFile);
			}
			console.log(formData);
			// 유효성 검사를 모두 통과한 경우에만 ajax 요청 실행한다.
			if (isValid) {
				$.ajax({
					type: 'post',
					url: '/api/public/members/join',
					data: formData, 
					processData: false,  // FormData를 처리하지 않도록 설정
					contentType: false,  // 기본 contentType을 설정하지 않도록 설정
					success: function(data, textStatus, jqXHR) {
						console.log(data);
						 if(jqXHR.status === 200){
							alert('회원가입에 성공하였습니다!');
							window.location.href = "/login";  // 회원가입 성공 시 페이지 이동
						} 
					},
				    error: function(xhr, textStatus, errorThrown) {
						alert('회원가입에 실패하였습니다.');
					}
				});
			}
		});
	});