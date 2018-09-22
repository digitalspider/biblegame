$(function(){
    var baseUrl = "http://localhost:8080/api/v1";
    var loginUrl = baseUrl + "/user/login";
    var registerUrl = baseUrl + "/user/register";
    var actionUrl = baseUrl + "/action/";
    var defaultActionUrl = baseUrl + "/action/";
    var storageKey = 'user';

    isLoggedIn();

    function isLoggedIn() {
        userString = window.localStorage.getItem(storageKey);
        //alert(userString);
        if (userString) {
            user = JSON.parse(userString) 
            //alert(user.name);
            if (user.name) {
                $('#unauthenticated').hide();
                $('#authenticated').show();
                startGame(user);
            } else {
                window.localStorage.removeItem(storageKey);
            }            
        } else {
            $('#unauthenticated').show();
            $('#authenticated').hide();
        }
    }

    function logIn(user) {
        var userString = JSON.stringify(user)
        window.localStorage.setItem(storageKey,userString);
        isLoggedIn();
    }

    function startGame(user) {
        $('#messages').append('<p>Welcome '+user.name+'</p>');
    }

    function continueGame(actionResponse) {
        $('#messages').append('<p>'+actionResponse.message.replace('\n','<br/>')+'</p>');
        if (actionResponse.nextActionMessage) {
            $('#messages').append('<p>'+actionResponse.nextActionMessage.replace('\n','<br/>')+'</p>');    
        }
        if (actionResponse.nextActionUrl) {
            actionUrl = actionResponse.nextActionUrl;
        } else {
            actionUrl = defaultActionUrl;
        }
    }

    $("#action-form").bind('submit', function (e) {
        var isValid = true; // someYourFunctionToCheckIfFormIsValid();
        if (!isValid) {
            e.preventDefault();
            return false;
        }
        else {
            jQuery.ajax({
                type: "GET",
                url: actionUrl + $("#input").val(),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader ("Authorization", "Bearer " + user.token);
                },
                success: function (actionResponse) {
                    console.log(actionResponse);
                    continueGame(actionResponse);
                }
            });
            e.preventDefault();
            return false;
        }
    });

    $("#login-form").bind('submit', function (e) {
        var isValid = true; // someYourFunctionToCheckIfFormIsValid();
        if (!isValid) {
            e.preventDefault();
            return false;
        }
        else {
            jQuery.ajax({
                type: "POST",
                url: loginUrl,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "username": $("#login-username").val(),
                    "password": $("#login-password").val()
                }),
                success: function (user) {
                    console.log(user);
                    logIn(user);
                }
            });
            e.preventDefault();
            return false;
        }
    });

    $("#register-form").bind('submit', function (e) {
        var isValid = true; // someYourFunctionToCheckIfFormIsValid();
        if (!isValid) {
            e.preventDefault();
            return false;
        }
        else {
            jQuery.ajax({
                type: "POST",
                url: registerUrl,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "email": $("#register-email").val(),
                    "username": $("#register-username").val(),
                    "password": $("#register-password").val()
                }),
                success: function (user) {
                    console.log(user);
                    logIn(user);
                }
            });
            e.preventDefault();
            return false;
        }
    });
}); 