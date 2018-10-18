var baseUrl = "http://localhost:8080/api/v1";
var loginUrl = baseUrl + "/user/login";
var registerUrl = baseUrl + "/user/register";
var friendUrl = baseUrl + "/friend/";
var actionUrl = baseUrl + "/action/";
var defaultActionUrl = baseUrl + "/action/";
var messageUrl = baseUrl + "/message/";
var storageKey = 'user';

function getUser() {
    var userString = window.localStorage.getItem(storageKey);
    //alert(userString);
    if (userString) {
        var user = JSON.parse(userString) 
        //alert(user.name);
        if (user.name) {
            return user;
        }
    }
}

function isLoggedIn() {
    var user = getUser();
    //alert(userString);
    if (user) {
        $('#unauthenticated').hide();
        $('#authenticated').show();
        startGame(user);
    } else {
        window.localStorage.removeItem(storageKey);
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
    $('#messages').val('');
    isLoggedIn();
}

function startGame(user) {
    $("#input").val('');
    $("#messages").val('');
    $('#messages').append('<p>Welcome '+user.name+'</p>');
    showUsername(user);
    showMessages(user);
    getActions(user);
    enableCountdown(new Date(user.lastLoginAt + 30*60*1000));
}

function showUsername(user) {
    $('#username').text(user.displayName+' | LVL='+user.level+" | Stamina="+user.stamina);
}

function showFriendRequests(user) {
    var friendRequestMessage = user.friendRequests.map(fr => 
            fr.name+' (LVL='+fr.level+') wants to be a friend? '+
            '<a class="btn-small" href="javascript:acceptFriend('+fr.id+',true)">YES</a>/'+
            '<a class="btn-small" href="javascript:acceptFriend('+fr.id+',false)">NO</a>').join("<br/>");
    showScrollMessage('Friend Requests', friendRequestMessage);
}

function executeAction(actionKey) {
    $("#input").val(actionKey);
    $("#action-form").submit();
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
            showScrollMessage('Messages',showMessages);
        });
    }
    if (user.friendRequests && user.friendRequests.length==0 && user.friends && user.friends.length==0) {
        $('#action-friend').hide();
    } else {
        $('#action-friend').show();
        var friendRequestsCount = user.friendRequests.length;
        $('#action-friend').html('<i class="glyphicon glyphicon-user"></i> Friends ('+user.friends.length + (friendRequestsCount>0?'/'+friendRequestsCount:'') +')');
        if (friendRequestsCount>0) {
            $('#action-friend').css('color', 'red');
            $('#action-friend').on("click", showFriendRequests(user));
        } else {
            $('#action-friend').css('color', 'black');
            $('#action-friend').on("click", function() {
                executeAction('j');
            });
        }

    }
}

function continueGame(action, messageTitle) {
    var inputEle = $('#input');
    var messageEle = $('#messages');
    var actionButtonEle = $('#action-buttons');
    if (action.preMessage) {
        inputEle.attr("placeholder", action.preMessage).val("").focus().blur();
    }
    if (action.postMessage) {
        var message = action.postMessage;
        var addError='';
        if (!action.success) {
            addError = " class='error'";
            actionUrl = defaultActionUrl;
            toastr.error(message);
        }
        messageEle.prepend('<p'+addError+'>'+message+'</p>');
        if (messageTitle) {
            if (action.success) {
                showScrollMessage(messageTitle,message);
            } else {
                toastr.options = {
                    "closeButton": true,
                    "positionClass": "toast-top-right",
                    "timeOut": "5000",
                    "extendedTimeOut": "1000"
                }
                toastr.error(message);
            }
        }
    }
    if (action.user) {
        save(action.user);
        showMessages(action.user);
        showUsername(action.user);
    }
    if (action.actionUrl) {
        actionUrl = baseUrl+action.actionUrl;
    }
    if (action.actions) {
        actionButtonEle.html('');
        for (var childActionIndex in action.actions) {
            var childAction = action.actions[childActionIndex];
            var tooltipStart = '';
            var tooltipEnd = '';
            if (childAction.tooltip) {
                tooltipStart = "<span class='d-inline-block' tabindex='0' data-toggle='tooltip' title='"+childAction.tooltip+"'>";
                tooltipEnd = "</span>"
            }
            var styleClass="";
            if (childAction.styleClass) {
                styleClass = childAction.styleClass; 
            }
            var icon='<i class="glyphicon '+childAction.glyphicon+'"></i>';
            if (childAction.type && childAction.type == 'full') {
                var htmlButton = "<div class='col-xs-12 col-md-12 col-lg-12'><div class='tile disabled "+styleClass+"' id='btn-"+childAction.name+"' name='btn-action' data-key='"+childAction.actionKey+"' data-url='"+childAction.actionUrl+"'><div class='title'>"+icon+" "+childAction.name+"</div><div class='description-full'>"+childAction.helpMessage+"</div></div></div>";
                if (childAction.enabled) {
                    htmlButton = "<div class='col-xs-12 col-md-12 col-lg-12'><div class='tile "+styleClass+"' id='btn-"+childAction.name+"' name='btn-action' data-key='"+childAction.actionKey+"' data-url='"+childAction.actionUrl+"' onclick='doAction(this.id)'><div class='title'>"+icon+" "+childAction.name+"</div><div class='description-full'>"+childAction.helpMessage+"</div></div></div>";
                }
            } else {
                var htmlButton = "<div class='col-xs-4 col-md-4 col-lg-4'><div class='tile disabled "+styleClass+"' id='btn-"+childAction.name+"' name='btn-action' data-key='"+childAction.actionKey+"' data-url='"+childAction.actionUrl+"'><div class='title'>"+icon+" "+childAction.name+"</div><div class='description'>"+childAction.helpMessage+"</div></div></div>";
                if (childAction.enabled) {
                    htmlButton = "<div class='col-xs-4 col-md-4 col-lg-4'><div class='tile "+styleClass+"' id='btn-"+childAction.name+"' name='btn-action' data-key='"+childAction.actionKey+"' data-url='"+childAction.actionUrl+"' onclick='doAction(this.id)'><div class='title'>"+icon+" "+childAction.name+"</div><div class='description'>"+childAction.helpMessage+"</div></div></div>";
                }
            }
            actionButtonEle.append(tooltipStart+htmlButton+tooltipEnd);
        };
    }
}

function showScrollMessage(title, text) {
    var divScrollMessage = $("#scroll-message");
    var divScrollMessageHeading = $("#scroll-message-heading");
    var divScrollMessageContent = $("#scroll-message-content");
    divScrollMessage.show();
    divScrollMessageContent.show();
    divScrollMessageHeading.html(title);
    divScrollMessageContent.html(text);
}

function hideScrollMessage() {
    var divScrollMessage = $("#scroll-message");
    var divScrollMessageContent = $("#scroll-message-content");
    divScrollMessage.hide();
    divScrollMessageContent.hide();
    divScrollMessageContent.html('');
}

function getActions(user) {
    var user = getUser();
    jQuery.ajax({
        type: "GET",
        url: defaultActionUrl,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Bearer " + user.token); // Javascript mgaic. user in variables
            xhr.setRequestHeader ("Format", "html");
        }
    }).done(function (action) {
        console.log(action);
        continueGame(action);
    }).fail(function (actionResponseError) {
        console.log(actionResponseError);
    });    
}

function doAction(eleId) {
    var user = getUser();
    var element = $("#"+eleId);
    var actionKey = element.data('key');
    var actionUrl = baseUrl+element.data('url');
    jQuery.ajax({
        type: "GET",
        url: actionUrl,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Bearer " + user.token);
            xhr.setRequestHeader ("Format", "html");
        }
    }).done(function (action) {
        console.log(action);
        continueGame(action);
    }).fail(function (action) {
        console.log(action);
        continueGame(action.responseJSON);
    });
    return false;
}

function markRead(messageId) {
    var user = getUser();
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
    var user = getUser();
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

function enableCountdown(countDownDate) {
    if (!countDownDate) {
        return;
    }

    // Update the count down every 1 second
    var x = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();

    // Find the distance between now and the count down date
    var distance = countDownDate - now;

    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Display the result in the element with id="demo"
    document.getElementById("demo").innerHTML = //days + "d " + hours + "h " + minutes + "m " + seconds + "s ";
    document.getElementById("demo").innerHTML = minutes + "m " + seconds + "s ";

    // If the count down is finished, write some text
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("demo").innerHTML = "<span style='color: red'>ACTIVATE</span>";
    }
    }, 1000);
}

$(function(){
    isLoggedIn();
    hideScrollMessage();

    $("#action-form").bind('submit', function (e) {
        var isValid = true; // someYourFunctionToCheckIfFormIsValid();
        if (!isValid) {
            e.preventDefault();
            return false;
        }
        else {
            var user = getUser();
            var messageTitle;
            var actionKey = $("#input").val();
            if (actionKey=='?') {
                actionKey = 'help';
                messageTitle = 'Help';
            } else if (actionKey.toLowerCase()=='q') {
                logout();
            } else if (actionKey.toLowerCase()=='z') {
                messageTitle = 'Stats'
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
                continueGame(actionResponse, messageTitle);
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

    $("#action-logout").click(logout);
    $(".action-link").each(function(index) {
        var actionKey = $(this).data('key');
        if (actionKey) {
            $(this).on("click", function() {
                executeAction(actionKey);
            });
        }
    });
});