$(function(){
    var baseUrl = "http://localhost:8080/api/v1";
    var loginUrl = baseUrl + "/user/login";
    var registerUrl = baseUrl + "/user/register";

    
    $(".login-form").bind('submit', function (e) {
        var isValid = someYourFunctionToCheckIfFormIsValid();
        if (!isValid) {
            e.preventDefault();
            return false;
        }
        else {
            jQuery.ajax({
                type: "POST",
                url: "my_custom/url",
                dataType: "html",
                data: { "text": jQuery("#edit-body").html()
                },
                success: function (result) {
                    console.log(result);
                }
            });
            e.preventDefault();
            return false;
        }
    });
}); 