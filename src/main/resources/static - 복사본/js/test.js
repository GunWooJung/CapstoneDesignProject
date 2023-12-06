document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const placeId = urlParams.get('id');
    const amendLink = document.getElementById('amendLink');

    amendLink.addEventListener('click', function(event) {
        event.preventDefault();
        window.open(`/amend_information?id=${placeId}`, '_blank');
    });

    fetch(`/place/detail?id=${placeId}`)
        .then(response => response.json())
        .then(place => {
            document.getElementById('placeName').textContent = place.name;
            document.getElementById('starRating').textContent = place.star_average;
            document.getElementById('address').textContent = place.address;
        })
        .catch(error => {
            console.error('Error:', error);
        });

    fetch(`/comment/show?id=${placeId}`)
        .then(response => response.json())
        .then(comments => {
            const commentsContainer = document.getElementById('reviews');
            comments.forEach(comment => {
                const commentElement = document.createElement('p');
                commentElement.textContent = `${comment.username} : ${comment.content}`;
                commentsContainer.appendChild(commentElement);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });

    document.getElementById('submitReview').addEventListener('click', function() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const reviewText = document.getElementById('reviewText').value;
        const commentData = {
            username: username,
            password: password,
            placeId: placeId,
            content: reviewText
        };

        fetch('/comment/enroll', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(() => {
            const commentsContainer = document.getElementById('reviews');
            const commentElement = document.createElement('p');
            commentElement.textContent = `Username: ${username}, Comment: ${reviewText}`;
            commentsContainer.appendChild(commentElement);
        })
        .catch(error => {
            console.error('Error:', error);
        });

        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
        document.getElementById('reviewText').value = '';
    });

    // Star rating submission
    document.getElementById('submitRating').addEventListener('click', function() {
        const selectedRating = document.querySelector('input[name="rating"]:checked').value;
        const ratingData = {
            placeId: placeId,
            rating: selectedRating
        };

        fetch('/submit-rating', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ratingData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(() => {
            // Optional: Handle the response, such as updating the UI or notifying the user
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});
