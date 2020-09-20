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
                sessionStorage.setItem("username", data["username"]);
                sessionStorage.setItem("role",data["role"]);
                switch(data["role"]) {
                case "ADMIN" :  window.location.replace("http://localhost:8090/admin.html");
                break;
                case "MANAGER" :  window.location.replace("http://localhost:8090/manager.html");
                break;
                case "SPECTATOR" :  window.location.replace("http://localhost:8090/movies.html");
                break;
                default:  console.log(data["role"])
                }
            },
            error: function() {
                $("#message").text("Invalid credentials.");
            }
        })
    })
})