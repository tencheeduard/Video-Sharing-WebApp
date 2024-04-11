document.getElementById("like_button").onclick = function()
{
    const id = /*[[${videoId}]]*/ 'videoId';
    const request = new XMLHttpRequest();
    let likes = /*[[${likes}]]*/ 'likes'
    request.open("POST","/like/" + id);
    request.send();
    request.onreadystatechange = function()
    {
        if (request.readyState === XMLHttpRequest.DONE) {
            if(request.status===200)
            {
                const likesCounter = document.getElementById("likes");
                likes = likes + 1;
                likesCounter.innerHTML = likes + ' likes';
            }
            else if(request.status === 401)
            {
                if(document.getElementById("error_not_logged_in_text") == null) {
                    const like_button = document.getElementById("like_button");
                    const errorText = document.createElement('a');
                    errorText.id = "error_not_logged_in_text";
                    errorText.appendChild(document.createTextNode("You must log in to perform this action. "));
                    like_button.parentElement.insertBefore(errorText, like_button);

                    const logInLink = document.createElement('a');
                    logInLink.href = "/login?redirect=/watch/" + id;
                    logInLink.appendChild(document.createTextNode("Click here to log in."));


                    like_button.parentElement.insertBefore(logInLink, like_button);

                    like_button.parentElement.insertBefore(document.createElement('br'), like_button);
                }
            }
        }
    }
}