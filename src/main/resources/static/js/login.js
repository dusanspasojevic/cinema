$(document).ready(function() {
    $("#login").submit(function(e) {
        e.preventDefault();

        $("#message").text("");

        var newCustomerJSON = JSON.stringify({
            "username": $("#username").val(),
            "password": $("#password").val(),
            "firstName": $("#firstName").val(),
            "lastName": $("#lastName").val(),
            "phoneNumber": $("#phoneNumber").val(),
            "email": $("#email").val(),
            "address": $("#address").val()
        })
    
        $.ajax({
            type: "POST",
            url: "http://localhost:8090/api/User/login",
            data: {
                username: $("#username").val(),
                password: $("#password").val()
            },
            success: function (data) {
                alert("Welcome " + $("#username").val() + "!");
                window.location.replace("http://localhost:8090/");
            },
            error: function() {
                $("#message").text("Invalid credentials.");
            }
        })
    })
})