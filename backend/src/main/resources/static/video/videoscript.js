const title = document.getElementById("title");
const source = document.getElementById("video_source");
const uploader = document.getElementById("uploader");
const view_counter = document.getElementById("view_counter");
const likes_counter = document.getElementById("likes_counter");
const like_button = document.getElementById("like_button");
const description = document.getElementById("description");
const main_video = document.getElementById("main_video");

const template = document.getElementById("recommended_video_template");
const recommendations_element = document.getElementById("recommendations_element");

const max_title_length = 30;

const videoRequest = new XMLHttpRequest();
videoRequest.open("GET","/test?id=" + window.location.href.split('/')[4].split('?')[0]);
videoRequest.send();

let videoInfo;

videoRequest.onreadystatechange = function()
{
    if(videoRequest.readyState === 4 && videoRequest.status === 200 && videoRequest.response !== "")
    {
        videoInfo = JSON.parse(videoRequest.response);
        title.innerText = videoInfo.title;
        source.setAttribute('src', '/video/' + videoInfo.videoId);
        uploader.innerText = videoInfo.user;
        view_counter.innerText = videoInfo.views + ' views';
        likes_counter.innerText = videoInfo.likes + ' likes';
        description.innerText = videoInfo.description;

        main_video.load();
    }
}

document.getElementById("like_button").onclick = function()
{
    const id = videoInfo.videoId;
    let likes = videoInfo.likes;

    const request = new XMLHttpRequest();
    request.open("POST","/like/" + id);
    request.send();
    request.onreadystatechange = function()
    {
        if (request.readyState === XMLHttpRequest.DONE) {
            if(request.status===200)
            {
                likes = likes + 1;
                likes_counter.innerHTML = likes + ' likes';
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

const videosRequest = new XMLHttpRequest();
videosRequest.open("GET","/recommend");
videosRequest.send();

videosRequest.onreadystatechange = function()
{
    if(videosRequest.readyState === 4 && videosRequest.status === 200 && videosRequest.response !== "")
    {
        const response = JSON.parse(videosRequest.response);

        console.log(response);
        for(let i = 0; i < response.length; i++)
        {
            const clone = template.content.cloneNode(true);
            const thumbnail = clone.querySelector('.recommended_video_thumbnail');
            const title = clone.querySelector('.recommended_video_title');
            const link = clone.querySelector('.recommended_video_thumbnail_link');

            thumbnail.src = "/thumb/" + response[i].videoId;
            link.href="/watch/" + response[i].videoId;

            let title_text = response[i].title;
            if(title_text.length > max_title_length)
                title_text = title_text.substring(0,max_title_length-3) + "...";

            title.textContent = title_text;

            recommendations_element.appendChild(clone);
        }
    }
}