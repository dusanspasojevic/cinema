$(document).ready(function() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8090/api/projection/",
            success: function (data) {
                console.log(data)
                data.forEach(projection => {
                                    var table = $("table tbody");
                                    var row = '<tr id=' + projection.id + '>' +
                                        '<td name="movie">' + projection.movieTitle + '</td>' +
                                        '<td name="cinema">' + projection.cinemaName + '</td>' +
                                        '<td name="genre" >' + projection.genre + '</td>' +
                                        '<td name="desc" >' + projection.desc + '</td>' +
                                        '<td name="date" >' + projection.time.slice(0,10) + '</td>' +
                                        '<td name="price" >' + projection.price + '</td>' +
                                         '<td name="vote" >' + projection.vote + '</td>' +
                                    '</tr>';
                                    table.append(row);
                                    })
            },
            error: function() {
                $("#message").text("Invalid credentials.");
            }
        })
    var role = sessionStorage.getItem("role");
    var navbar = [];
    switch(role) {
    case "ADMIN" :  navbar =        '<li class="active"><a href="admin.html">Employees</a></li>' +
                                     '    <li><a href="cinema.html">Cinemas</a></li>' +
                                         '<li><a href="api/User/logout">Logout</a></li>'
    break;
    case "MANAGER" :  navbar =         '<li class="active"><a href="manager.html">Cinemas</a></li>' +
                                                       '<li><a href="api/User/logout">Logout</a></li>'
    break;
    case "SPECTATOR" :  navbar = ['Watched movies', 'Profile']
    break;
    default:  console.log(role)
    }

$("#navbarlist").append(navbar);

})