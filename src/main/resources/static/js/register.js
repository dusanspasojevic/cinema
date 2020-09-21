$(document).ready(function() {
const user = sessionStorage.getItem("username");
if (user)
    window.location.replace("http://localhost:8090/movies.html");


    $("#register").submit(function(e) {
        e.preventDefault();

        $("#message").text("");

        var newUser = {
            role: $("#role").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            phoneNumber: $("#phoneNumber").val(),
            email: $("#email").val(),
            address: $("#address").val(),
            birthDate: $("#birthDate").val().slice(6,10) + '-' + $("#birthDate").val().slice(3,5) + '-' + $("#birthDate").val().slice(0,2)
        }
        if (newUser.password !== $("#password_confirmation").val()) {
                $("#password").focus();
                 $("#password_confirmation").focus()
              alert("Passwords don't match!")
        } else {

        $.ajax({
            type: "POST",
            url: "http://localhost:8090/api/User/register",
            data: JSON.stringify(newUser),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
            if (newUser.role == "SPECTATOR")
                alert("Successfully created account! Please login.")
            else
                 alert("Successfully created account! Wait for admin to aprove your account.")
             window.location.replace("http://localhost:8090/login.html");
            },
            error: function() {
                $("#message").text("Invalid credentials.");
            }
        })
        }
    })
})