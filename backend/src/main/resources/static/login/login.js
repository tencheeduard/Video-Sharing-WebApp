const submit_button = document.getElementById("submit_button");


submit_button.onclick = function() {

    const request = new XMLHttpRequest();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    request.open("POST","/login/request?username=" + username + "&password=" + password);
    request.send();

    request.onreadystatechange = function()
    {
        if (request.readyState === XMLHttpRequest.DONE) {
            if(request.status===200)
            {
                let redirect = '/';
                window.location.replace(redirect);
            }
            else
            {
                const submit_button = document.getElementById("submit_button");

                let errorText = document.getElementById("error_text");
                if(errorText == null)
                {
                    errorText = document.createElement('a');
                    errorText.id = "error_text";
                    submit_button.parentElement.insertBefore(errorText, submit_button);
                    submit_button.parentElement.insertBefore(document.createElement('br'), submit_button);

                }
                else{
                    errorText.textContent = '';
                }
                if (request.status === 600)
                    errorText.appendChild(document.createTextNode("The username or e-mail does not exist."));

                else if (request.status === 700)
                    errorText.appendChild(document.createTextNode("The password is incorrect."));

                else
                    errorText.appendChild(document.createTextNode("Could not log in: Unknown error"));
            }
        }
    }
}

const register_submit_button = document.getElementById("register_submit_button");

register_submit_button.onclick = function() {

    const request = new XMLHttpRequest();

    let username = document.getElementById("register_username").value;
    let email = document.getElementById("register_email").value;
    let password = document.getElementById("register_password").value;

    request.open("POST","/login/signup?username=" + username + "&password=" + password + "&email=" + email);
    request.send();

    request.onreadystatechange = function()
    {
        if (request.readyState === XMLHttpRequest.DONE) {
            if(request.status===200)
            {
                let redirect = '/';
                window.location.replace(redirect);
            }
        }
    }
}