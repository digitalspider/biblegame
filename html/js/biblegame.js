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
        $('#messages').empty();
        isLoggedIn();
    }

    function startGame(user) {
        $('#messages').append('<p>Welcome '+user.name+'</p>');
    }

    function continueGame(actionResponse, showToaster) {
        var addError='';
        if (!actionResponse.success) {
            addError = " class='error'";
            toastr.error(actionResponse.message);
        }
        if (actionResponse.message) {
            var message = actionResponse.message;
            if (showToaster) {
                if (actionResponse.success) {
                    toastr.options = {
                        "closeButton": true,
                        "positionClass": "toast-top-full-width",
                        "preventDuplicates": true,
                        "showDuration": "300",
                        "hideDuration": "1000",
                        "timeOut": "5000",
                        "extendedTimeOut": "1000"
                    }
                    toastr.info(message);
                } else {
                    toastr.error(message);
                }
            } else {
                $('#messages').append('<p'+addError+'>'+message+'</p>');
            }
        }
        if (actionResponse.nextActionMessage) {
            var nextMessage = actionResponse.nextActionMessage;
            $('#messages').append('<p>'+nextMessage+'</p>');    
        }
        if (actionResponse.success) {
            if (actionResponse.nextActionUrl) {
                actionUrl = baseUrl+actionResponse.nextActionUrl;
            } else {
                actionUrl = defaultActionUrl;
            }
        }
        $("#input").val('');
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
                showToaster = true;
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
                    xhr.setRequestHeader ("Format", "html");
                }
            }).done(function (actionResponse) {
                console.log(actionResponse);
                continueGame(actionResponse, showToaster);
            }).fail(function (actionResponseError) {
                console.log(actionResponseError);
                continueGame(actionResponseError.responseJSON, true);
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
                })
            }).done(function (user) {
                console.log(user);
                login(user);
            }).fail(function (loginError) {
                console.log(loginError.responseText);
                toastr.error(loginError.responseText);
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
                })
            }).done(function (user) {
                console.log(user);
                login(user);
            }).fail(function (loginError) {
                console.log(loginError.responseText);
                toastr.error(loginError.responseText);
            });
            e.preventDefault();
            return false;
        }
    });

    $("#action-logout").click(function() {
        logout();
    });
    $("#action-work").click(function(e) {
        $("#input").val("w");
        $("#action-form").submit();
    });
    $("#action-study").click(function(e) {
        $("#input").val("s");
        $("#action-form").submit();
    });
    $("#action-pray").click(function(e) {
        $("#input").val("p");
        $("#action-form").submit();
    });
    $("#action-beg").click(function(e) {
        $("#input").val("a");
        $("#action-form").submit();
    });
    $("#action-buy").click(function(e) {
        $("#input").val("b");
        $("#action-form").submit();
    });
    $("#action-give").click(function(e) {
        $("#input").val("g");
        $("#action-form").submit();
    });
    $("#action-steal").click(function(e) {
        $("#input").val("x");
        $("#action-form").submit();
    });
    $("#action-chat").click(function(e) {
        $("#input").val("c");
        $("#action-form").submit();
    });
    $("#action-message").click(function(e) {
        $("#input").val("m");
        $("#action-form").submit();
    });
    $("#action-knock").click(function(e) {
        $("#input").val("k");
        $("#action-form").submit();
    });
    $("#action-help").click(function(e) {
        $("#input").val("?");
        $("#action-form").submit();
    });
    $("#action-stats").click(function(e) {
        $("#input").val("z");
        $("#action-form").submit();
    });
});