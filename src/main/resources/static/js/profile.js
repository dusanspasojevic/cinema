$(document).ready(function(){
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");

    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/User/me/",
                success: function (data) {
                console.log(data)
                    $("#fullName").html(data.firstName + ' ' + data.lastName);
                     $("#username").html(data.username);
                     $("#phone").html(data.phoneNumber);
                     $("#email").html(data.email);
                     $("#birth").html(data.birthDate.slice(0,10));
                     $("#pass").html(data.password);
                },
                error: function() {
                }
            })

        $("#watchedLink").click(function() {
            const user = sessionStorage.getItem("username");
              $.ajax({
                             type: "GET",
                             url: "http://localhost:8090/api/ticket/" + user,
                             success: function (data) {
                             console.log(data)
                             },
                             error: function(eror) {
                                console.log(eror)
                             }
                         })
        });
});

