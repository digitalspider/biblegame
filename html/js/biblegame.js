var baseUrl = "http://localhost:8080/api/v1";
var loginUrl = baseUrl + "/user/login";
var registerUrl = baseUrl + "/user/register";
var friendUrl = baseUrl + "/friend/";
var actionUrl = baseUrl + "/action/";
var defaultActionUrl = baseUrl + "/action/";
var messageUrl = baseUrl + "/message/";
var storageKey = 'user';


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

function save(user) {
    var userString = JSON.stringify(user)
    window.localStorage.setItem(storageKey,userString);
}

function login(user) {
    var userString = JSON.stringify(user)
    window.localStorage.setItem(storageKey,userString);
    $("#input").val('');
    $("#messages").val('');
    isLoggedIn();
}

function logout() {
    window.localStorage.removeItem(storageKey);
    $('#messages').empty();
    isLoggedIn();
}

function startGame(user) {
    $("#input").val('');
    $("#messages").val('');
    $('#messages').append('<p>Welcome '+user.name+'</p>');
    $('#username').text(user.displayName+' | '+user.level);
    showMessages(user);
    showActions(user);
}

function continueGame(actionResponse, showToaster) {
    var addError='';
    if (!actionResponse.success) {
        addError = " class='error'";
        actionUrl = defaultActionUrl;
        toastr.error(actionResponse.message);
    }
    if (actionResponse.user) {
        save(actionResponse.user);
        showMessages(actionResponse.user);
        showActions(actionResponse.user);
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
            $('#messages').prepend('<p'+addError+'>'+message+'</p>');
        }
    }
    if (actionResponse.nextActionMessage) {
        var nextMessage = actionResponse.nextActionMessage;
        $('#messages').prepend('<p>'+nextMessage+'</p>');    
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

function showMessages(user) {
    if (user.messages && user.messages.length==0) {
        $('#action-msg').hide();
    } else {
        $('#action-msg').show();
        $('#action-msg').text('Messages ('+user.messages.length+')');
        $('#action-msg').css('color', 'red');
        $('#action-msg').click(function() {
            var showMessages = user.messages.map(msg => msg.from.name+': '+msg.message+
                    '. <a href="javascript:markRead('+msg.id+')">Mark READ</a>|<a href="javascript:setReply(\''+msg.from.name+'\')">REPLY</a>').join("<br/>");
            toastr.success(showMessages, '', {"closeButton": true, "preventDuplicates": true, "positionClass": "toast-top-right","timeOut": "0","extendedTimeOut": "10000"});
        });
    }
    if (user.friendRequests && user.friendRequests.length==0 && user.friends && user.friends.length==0) {
        $('#action-friend').hide();
    } else {
        $('#action-friend').show();
        var friendRequestsCount = user.friendRequests.length;
        $('#action-friend').text('Friends ('+user.friends.length + (friendRequestsCount>0?'/'+friendRequestsCount:'') +')');
        if (friendRequestsCount>0) {
            $('#action-friend').css('color', 'red');
        } else {
            $('#action-friend').css('color', 'black');
        }
        $('#action-friend').click(function() {
            var friendMessages = user.friendRequests.map(fr => 
                    fr.name+' (LVL='+fr.level+') wants to be a friend? '+
                    '<a class="btn-small" href="javascript:acceptFriend('+fr.id+',true)">YES</a>/'+
                    '<a class="btn-small" href="javascript:acceptFriend('+fr.id+',false)">NO</a>').join("<br/>");
            toastr.success(friendMessages, '', {"closeButton": true, "preventDuplicates": true, "positionClass": "toast-top-right","timeOut": "0","extendedTimeOut": "10000"});
        });
    }
}

function showActions(user) {
    if (user.stamina>0) {
        $('#action-work').show();
        $('#action-study').show();
        $('#action-pray').show();
        $('#action-beg').show();
        $('#action-steal').show();
    } else {
        $('#action-work').hide();
        $('#action-study').hide();
        $('#action-pray').hide();
        $('#action-beg').hide();
        $('#action-steal').hide();
    }
    if (user.riches>0) {
        $('#action-buy').show();
        $('#action-give').show();
    } else {
        $('#action-buy').hide();
        $('#action-give').hide();
    }
}

function markRead(messageId) {
    jQuery.ajax({
        type: "GET",
        url: messageUrl + 'read/' + messageId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Bearer " + user.token); // Javascript mgaic. user in variables
            xhr.setRequestHeader ("Format", "html");
        }
    }).done(function (actionResponse) {
        console.log(actionResponse);
        if (actionResponse.user) {
            save(actionResponse.user);
            showMessages(actionResponse.user);
        }
    }).fail(function (actionResponseError) {
        console.log(actionResponseError);
    });
}

function viewVerse(elementId) {
    var url = $("#"+elementId).data('url').replace(' ','+');
    jQuery.ajax({
        type: "GET",
        url: url,
        crossDomain: true,
        dataType: "jsonp",
    }).done(function (data) {
        console.log(data);
        if (data.verses[0].text) {
            toastr.info(data.reference+': '+data.verses[0].text);
        }
    }).fail(function (data) {
        console.log(data);
    });
    return false;
}

function setReply(username) {
    $('input').val('m:'+username+':');
    $('input').focus();
}

function acceptFriend(friendId, accept) {
    jQuery.ajax({
        type: "GET",
        url: friendUrl + (accept ? "accept/" : "remove/") +friendId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Bearer " + user.token); // Javascript mgaic. user in variables
            xhr.setRequestHeader ("Format", "html");
        }
    }).done(function (actionResponse) {
        console.log(actionResponse);
        if (actionResponse.user) {
            save(actionResponse.user);
            showMessages(actionResponse.user);
        }
    }).fail(function (actionResponseError) {
        console.log(actionResponseError);
    });
}

$(function(){
    isLoggedIn();

    
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
            } else if (actionKey.toLowerCase()=='q') {
                logout();
            } else if (actionKey.toLowerCase()=='z') {
                showToaster = true;
            } else if (actionKey.toLowerCase().startsWith('m:')) {
                if (actionKey.split(":").length==3) {
                    var username = actionKey.split(":")[1];
                    var message = actionKey.split(":")[2];
                    actionUrl=messageUrl+'send/name/'+username+'/'+message;
                    actionKey='';
                }
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
    $(".action-link").each(function(index) {
        var actionKey = $(this).data('key');
        if (actionKey) {
            $(this).on("click", function() {
                $("#input").val(actionKey);
                $("#action-form").submit();
            });
        }
    });
});