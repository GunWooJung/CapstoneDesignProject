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
    const reportsContainer = document.getElementById('previous_reports_container');

    function fetchLatestReports() {
        fetch(`/api/places/${placeId}/reports`) // Adjust this URL to match your backend endpoint
            .then(response => response.json())
            .then(reports => {
                // Clear existing content
                reportsContainer.innerHTML = '';

                // Process and display up to the first three reports
                if(reports.data != null){
					reports.data.forEach(report => {
	                    const reportElement = createReportElement(report);
	                    reportsContainer.appendChild(reportElement);
	                });
				}
            })
            .catch(error => console.error('Error fetching reports:', error));
    }

    function createReportElement(report) {
        // Create a new HTML element for each report
        const reportDiv = document.createElement('div');
        reportDiv.className = 'body_content_submit';
		
		let reportType = report.type;
		let reportContent = report.content;
		
		switch (reportType) {
		  case 'bell':
		    reportType = '비상벨 정보 수정';
		    break;
		  case 'deleteLocation':
		    reportType = '장소 삭제';
		    break;
		  case 'diaperChange':
		    reportType = '기저귀 교환대 정보 수정';
		    break;
		  case 'disabled':
		    reportType = '장애인 화장실 정보 수정';
		    break;
		case 'reportLocation':
		    reportType = '장소명 및 위치 수정';
		    break;			
		  default:
		    reportType = '내용 없음';
		}
		
		switch (reportContent) {
		  case 'manBellYes':
		    reportContent = '남자 화장실 비상벨 있음';
		    break;
		  case 'manBellNo':
		    reportContent = '남자 화장실 비상벨 없음';
		    break;
		  case 'womanBellYes':
		    reportContent = '여자 화장실 비상벨 있음';
		    break;
		  case 'womanBellNo':
		    reportContent = '여자 화장실 비상벨 없음';
		    break;
		 case 'disabledBellYes':
		    reportContent = '장애인 화장실 비상벨 있음';
		    break;	
		 case 'disabledBellNo':
		    reportContent = '장애인 화장실 비상벨 없음';
		    break;
			// 위까지가 비상벨 정보
		 case 'deleteClosed':
			    reportContent = '폐업';
			    break;
		  case 'deleteDuplicated':
		    reportContent = '중복된 장소';
		    break;
		  case 'deleteNowhere':
		    reportContent = '없는 장소';
		    break;
		  case 'deleteRelocation':
		    reportContent = '다른 장소 이전';
		    break;
			//여기까지가 장소 삭제
		 case 'manDiaperYes':
		    reportContent = '남자 화장실 기저귀 교환대가 있음';
		    break;	
		 case 'manDiaperNo':
		    reportContent = '남자 화장실 기저귀 교환대가 없거나 파손됨';
		    break;	
		 case 'womanDiaperYes':
		    reportContent = '여자 화장실 기저귀 교환대가 있음';
		    break;
		  case 'womanDiaperNo':
		    reportContent = '여자 화장실 기저귀 교환대가 없거나 파손됨';
		    break;
			//여기까지 기저귀
			case 'DisabledYes':
			   reportContent = '장애인 화장실이 있음';
			   break;	
			case 'DisabledNo':
			   reportContent = '장애인 화장실이 없거나 파손됨';
			   break;	
			   //장애인		
		}

		console.log(reportType);
        reportDiv.innerHTML = `
            
        <div class="recent_textbox">
            <div class="recent_textbox_title">${reportType}</div>
            <div class="recent_textbox_detail">
                <div class="textbox_detail_text">${reportContent}</div>
                <div class="textbox_detail_date">${new Date(report.createdDate).toLocaleDateString('ko-KR')}</div>
            </div>
        </div>
        <div class="recent_likeButton">
            <div class="recent_likeButton_number">+ ${report.heart}</div>
            <div class="recent_likeButton_heart" data-report-id="${report.id}">
                <img src="/static//public/heart.svg" alt="♡">
            </div>
        </div>
    
        `
        const heartIcon = reportDiv.querySelector('.recent_likeButton_heart img');
        heartIcon.addEventListener('click', function () {
            const reportId = heartIcon.parentElement.getAttribute('data-report-id');
            fetch(`/api/places/${placeId}/reports/${reportId}/heart`,{
				method: 'PUT'
			})
                .then(response => response.json())
                .then(body => {
                 	if(body.status == 200){
                        // Handle successful submission	       
                        alert('신고 횟수가 증가되었습니다.');
                        location.reload();
                    }
                });
        });

        return reportDiv;
    }

    document.addEventListener("reportSubmitted", function () {
        fetchLatestReports();
    });

    fetchLatestReports();
});