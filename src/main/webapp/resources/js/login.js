let validEmail=true;
let validPassword=true;
function checkEmail(e) {
    let errorMessageEmail = document.getElementById("errorMessageEmail");
        if (e.value==='') {
            errorMessageEmail.innerHTML = "empty field login";
            e.style.borderColor = "red";
            validEmail=false;
        }else if (e.value.length>40) {
            errorMessageEmail.innerHTML = "very long email, must be less 40 characters";
            e.style.borderColor = "red";
            validEmail=false;
        }else{
            e.style.borderColor = "blue";
            errorMessageEmail.innerHTML = null;
            validEmail=true;
        }
}
function checkPassword(e) {
    let errorMessagePassword = document.getElementById("errorMessagePassword");
    if (e.value==='') {
        errorMessagePassword.innerHTML = "empty field password";
        e.style.borderColor = "red";
        validPassword=false;
    }else if (e.value.length>30) {
        errorMessagePassword.innerHTML = "very long password, must be less 30 characters";
        e.style.borderColor = "red";
        validPassword=false;
    }else{
        e.style.borderColor = "blue";
        errorMessagePassword.innerHTML = null;
        validPassword=true;
    }
}
jQuery(document).ready(function($) {
    $("#login-form").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        if(validEmail && validPassword){
            console.log("sending to server");
            loginViaAjax();
        }
    });

});

function loginViaAjax() {
    //creating user hash and sending it via ajax, in success section call function display - if user is invalid? else redirect to default root
    let username = $("#username").val();
    let  password= $("#password").val();
    $.ajax({
        type: "POST",
        url: "login",
        data: {username:username, password:password},
        success : function(data) {
                if (data['code'] === "200") {
                    location.href = "explorer";
                } else {
                    display(data['msg']);
                }
        },
        error : function(e) {
            console.log("ERROR: ", e);
            // display(e);
        },

    });
}


function display(data) {
    let json = "<pre>" + data + "</pre>";
    $('#feedback').html(json);
}

