document.addEventListener('DOMContentLoaded', function () {
    const placeId = new URLSearchParams(window.location.search).get('id');
    const reportsContainer = document.getElementById('previous_reports_container');


    ///////////////////////////////////////////////////////////////////////////
    // function createDummyData() {
    //     return [
    //         { id: 1, type: "장소명 오류", content: "주소가 정확하지 않습니다.", createDate: '2023-01-01', count: 5 },
    //         { id: 2, type: "정보 누락", content: "남자 화장실 기저귀 교환대가 없거나 파손됨.", createDate: '2023-02-15', count: 3 },
    //         { id: 3, type: "기타", content: "영업시간이 변경되었습니다.", createDate: '2023-03-20', count: 2 }
    //     ];
    // }

    // // 더미 데이터를 화면에 표시
    // function displayDummyData() {
    //     const dummyData = createDummyData();
    //     reportsContainer.innerHTML = ''; // 기존 내용 초기화

    //     dummyData.forEach(report => {
    //         const reportElement = createReportElement(report);
    //         reportsContainer.appendChild(reportElement);
    //     });
    // }
    // displayDummyData(); // 더미 데이터 표시 함수 호출

    ///////////////////////////////////////////////////

    function fetchLatestReports() {
        fetch(`/report/show?id=${placeId}`) // Adjust this URL to match your backend endpoint
            .then(response => response.json())
            .then(reports => {
                // Clear existing content
                reportsContainer.innerHTML = '';

                // Process and display up to the first three reports
                reports.slice(0, 3).forEach(report => {
                    const reportElement = createReportElement(report);
                    reportsContainer.appendChild(reportElement);
                });
            })
            .catch(error => console.error('Error fetching reports:', error));
    }

    function createReportElement(report) {
        // Create a new HTML element for each report
        const reportDiv = document.createElement('div');
        reportDiv.className = 'body_content_submit';
        reportDiv.innerHTML = `
            
        <div class="recent_textbox">
            <div class="recent_textbox_title">${report.type}</div>
            <div class="recent_textbox_detail">
                <div class="textbox_detail_text">${report.content}</div>
                <div class="textbox_detail_date">${new Date(report.createDate).toLocaleDateString('ko-KR')}</div>
            </div>
        </div>
        <div class="recent_likeButton">
            <div class="recent_likeButton_number">+ ${report.count}</div>
            <div class="recent_likeButton_heart" data-report-id="${report.id}">♡</div>
        </div>
    
        `


        const heartIcon = reportDiv.querySelector('.recent_likeButton_heart');
        heartIcon.addEventListener('click', function (event) {
            if (event.target.classList.contains('recent_likeButton_heart')) {
                const reportId = event.target.getAttribute('data-report-id');
                fetch(`/report/clickheart?id=${reportId}&placeid=${placeId}`)
                    .then(response => {
                        return response.text(); // Assuming the server responds with plain text
                    })
                    .then(body => {
                        if (body.trim().toLowerCase() === "ip") {
                            // Handle duplicated IP case
                            alert('이미 신고 횟수가 추가 되었습니다.');
                        }
                        else if (body.trim().toLowerCase() === "count3") {
                            alert('신고 누적으로 신고 내용이 반영되었습니다.');
                            location.reload();
                        } 
                        else {
                            // Handle successful submission	       
                            alert('신고 횟수가 증가되었습니다.');
                            location.reload();
                        }
                    });
            }
        });//리스너

        return reportDiv;
    }

    document.addEventListener("reportSubmitted", function () {
        fetchLatestReports();
    });

    fetchLatestReports();
});