const max_title_length = 40;
const video_amount = 12;

const upload_button = document.getElementById("upload_button");

const logged_in_message = document.getElementById("logged_in_message");

const userRequest = new XMLHttpRequest();
userRequest.open("GET","/getUserData" );
userRequest.send();

let userInfo;

upload_button.onclick = function()
{
    if(userInfo != null)
        window.location.replace("/upload");
    else
        window.location.replace("/login");
}

userRequest.onreadystatechange = function()
{
    if(userRequest.status === 200 && userRequest.response !== "")
    {
        userInfo = JSON.parse(userRequest.response);
        logged_in_message.innerText = "Logged in as: "
        if(userInfo.publicName != null)
            logged_in_message.innerText += userInfo.publicName;
        else
            logged_in_message.innerText += userInfo.username;
    }
}

const sort_select = document.getElementById("sort_select");


sort_select.onchange = function(){
    const request = new XMLHttpRequest();
    request.open("POST","/setFilterType/" + sort_select.options[sort_select.selectedIndex].value);
    request.send();
    request.onreadystatechange = function()
    {
        if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
            location.reload();
        }
    }
}

let selected = sort_select.options[sort_select.selectedIndex].value;

const videosRequest = new XMLHttpRequest();
videosRequest.open("GET","/recommend?searchType=" + selected + "&amount=" + video_amount);
videosRequest.send();

videosRequest.onreadystatechange = function()
{
    if(videosRequest.readyState === 4 && videosRequest.status === 200 && videosRequest.response !== "")
    {
        const response = JSON.parse(videosRequest.response);

        const table = document.getElementById("videos_table");
        const template = document.getElementById("recommended_video_template");

        console.log(response);

        let row = 0;
        for(let i = 0; i < response.length; i++)
        {
            if(i % 4===0) {
                row++;
                const newRow = document.createElement('tr');
                newRow.id = "recommended_videos_table_row_" + row;
                table.appendChild(newRow);
            }

            const row_elem = document.getElementById("recommended_videos_table_row_" + row);

            const clone = template.content.cloneNode(true);
            const thumbnail = clone.querySelector('.recommended_video_thumbnail');
            const title = clone.querySelector('.recommended_video_title');
            const link = clone.querySelector('.recommended_video_thumbnail_link');

            thumbnail.src = "/thumb/" + response[i].videoId;
            link.href="/watch/" + response[i].videoId;

            console.log(link.href);

            let title_text = response[i].title;
            if(title_text.length > max_title_length)
                title_text = title_text.substring(0,max_title_length-3) + "...";

            title.textContent = title_text;

            row_elem.appendChild(clone);
        }
    }
}