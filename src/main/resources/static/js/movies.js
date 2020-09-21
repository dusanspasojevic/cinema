$(document).ready(function() {
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");
        $.ajax({
            type: "GET",
            url: "http://localhost:8090/api/projection/",
            success: function (data) {
                console.log(data)
                data.forEach(projection => {
                                    var table = $("table tbody");
                                    var row = '<tr id=' + projection.id + '>' +
                                        '<td name="movie">' + projection.movieTitle + '</td>' +
                                         '<td name="duration">' + parseInt(projection.duration) + '</td>' +
                                        '<td name="cinema">' + projection.cinemaName + '</td>' +
                                        '<td name="genre" >' + projection.genre + '</td>' +
                                        '<td name="desc" >' + projection.desc + '</td>' +
                                        '<td name="date" >' + projection.time.slice(0,10) + '</td>' +
                                        '<td name="price" >' + parseInt(projection.price) + '</td>' +
                                         '<td name="vote" >' + parseFloat(projection.vote) + '</td>' +
                                          '<td name="seats" >' + parseInt(projection.notReservedSeats) + '</td>' +
                                            '<td class="reserve"><button type="button" class="btn btn-success">Reserve</button></td>' +
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
                                         '<li><a class="logout" >Logout</a></li>'
    break;
    case "MANAGER" :  navbar =         '<li class="active"><a href="manager.html">Cinemas</a></li>' +
                                                       '<li><a class="logout">Logout</a></li>'
    break;
    case "SPECTATOR" :  navbar =  '    <li><a href="profile.html">Profile</a></li>' +
                                             '<li><a class="logout">Logout</a></li>'
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
                                           '<td name="seats" >' + parseInt(projection.notReservedSeats) + '</td>' +
                                          '<td class="reserve"><button type="button" class="btn btn-success">Reserve</button></td>' +
                                    '</tr>';
                                    table.append(row);
                                    })
            },
            error: function() {
                $("#message").text("Invalid credentials.");
            }
        })

});

$(document).on("click", ".reserve", function(){
const element = $(this)
const id = element.parents("tr").attr("id");
  $.ajax({
         type: "POST",
         url: "http://localhost:8090/api/projection/reserve/" + id,
         success: function (data) {
          alert('Successfully reserved projection!')
           const column = $(`#${id}`).find('td[name="seats"]')
            const number = parseInt(column.text()) - 1
           element.html('<button type="button" class="btn btn-secondary" disabled>Reserved</button>');
           column.html('<td name="seats" >' +  number + '</td>')
         },
         error: function(eror) {
            console.log(eror)
         }
       })
})


});

function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("prjtable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}