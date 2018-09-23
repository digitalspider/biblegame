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

    function login(user) {
        var userString = JSON.stringify(user)
        window.localStorage.setItem(storageKey,userString);
        isLoggedIn();
    }

    function logout() {
        window.localStorage.removeItem(storageKey);
        $('#messages').etmpy();
        isLoggedIn();
    }

    function startGame(user) {
        $('#messages').append('<p>Welcome '+user.name+'</p>');
    }

    function continueGame(actionResponse, showToaster) {
        var addError='';
        if (!actionResponse.success) {
            addError = " class='error'";
        }
        if (actionResponse.message) {
            var message = actionResponse.message.replace('\n','<br/>');
            if (showToaster) {
                if (actionResponse.success) {
                    toastr.info(message);
                } else {
                    toastr.error(message);
                }
            } else {
                $('#messages').append('<p'+addError+'>'+message+'</p>');
            }
        }
        if (actionResponse.nextActionMessage) {
            var nextMessage = actionResponse.nextActionMessage.replace('\n','<br/>');
            $('#messages').append('<p>'+nextMessage+'</p>');    
        }
        if (actionResponse.nextActionUrl) {
            actionUrl = baseUrl+actionResponse.nextActionUrl;
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
            var showToaster = false;
            actionKey = $("#input").val();
            if (actionKey=='?') {
                actionKey = 'help';
            } else if (actionKey=='q') {
                logout();
            } else if (actionKey=='z') {
                showToaster = true;
            }
            jQuery.ajax({
                type: "GET",
                url: actionUrl + actionKey,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader ("Authorization", "Bearer " + user.token);
                }
            }).done(function (actionResponse) {
                console.log(actionResponse);
                continueGame(actionResponse, showToaster);
            }).fail(function (actionResponse) {
                console.log(actionResponse);
                continueGame(actionResponse.responseJSON, true);
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
                    login(user);
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
                    login(user);
                }
            });
            e.preventDefault();
            return false;
        }
    });
}); 