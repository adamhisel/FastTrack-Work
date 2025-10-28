
fetch('https://api.github.com/users/adamhisel')
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        $('#avatar').attr('src', data.avatar_url)
        $(`#bio`).html(data.bio)
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });

$('#contact-form').submit(event => {
    event.preventDefault();

    const formData = new FormData(event.target);

    fetch(event.target.action, {
        method: "POST",
        body: formData,
        headers: { Accept: "application/json" }
    })
        .then(response => {
            if (response.ok) {
                // Clear form
                event.target.reset();
                // Show thank you inline
                $('#thank-you').text('✅ Thanks! Your message has been sent.').show();
            } else {
                $('#thank-you').text('⚠️ Something went wrong. Try again later.').show();
            }
        })
        .catch(() => {
            $('#thank-you').text('⚠️ Network error. Try again later.').show();
        });
});

