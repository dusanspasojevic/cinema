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
                                         '<td name="duration">' + projection.duration + '</td>' +
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
    case "SPECTATOR" :  navbar = '<li class="active"><a href="#">Watched movies</a></li>' +
                                         '    <li><a href="me.html">Profile</a></li>' +
                                             '<li><a href="api/User/logout">Logout</a></li>'
    break;
    default:  console.log(role)
    }

$("#navbarlist").append(navbar);

$( "#srcBtn" ).click(function() {
    const title = $("#searchTitle").val();
    const desc = $("#searchDesc").val();
    const price = $("#searchPrice").val();
     const genre = $("#searchGenre").val();
     const vote = $("#searchVote").val();
     const duration = $("#searchDuration").val();

    title.replace(' ','+');
    let query = ""
    if (title !== "")
       query += `?title=${title}`

    if (desc !== "")
       if (query === "")
            query += `?desc=${desc}`
         else
             query += `&desc=${desc}`
     if (price !== "")
      if (query === "")
         query += `?price=${price}`
          else
                      query += `&price=${price}`
     if (genre != "")
   if (query === "")
        query += `?genre=${genre}`
         else
                     query += `&genre=${genre}`
      if (vote != "")
      if (query === "")
        query += `?vote=${vote}`
         else
                     query += `&vote=${vote}`
       if (duration != "")
         if (query === "")
            query += `?duration=${duration}`
             else
                         query += `&duration=${duration}`
     $.ajax({
            type: "GET",
            url: "http://localhost:8090/api/projection/search" + query,
            success: function (data) {
                console.log(data)
                var table = $("table tbody");
               table.empty();
                data.forEach(projection => {

                                    var row = '<tr id=' + projection.id + '>' +
                                        '<td name="movie">' + projection.movieTitle + '</td>' +
                                         '<td name="duration">' + projection.duration + '</td>' +
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

});

})