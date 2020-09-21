$(document).on("click", ".logout", function(){
     $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/User/logout",
                success: function (data) {
                                sessionStorage.removeItem("username");
                                sessionStorage.removeItem("role");
                                window.location.replace("http://localhost:8090/index.html");
                            },
                error: function() {
                console.log('error')
                }
});
});