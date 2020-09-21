$(document).ready(function(){
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");
$("#moviestable").children().hide();
$(".labelText").hide();
$(".labelText").children().hide();
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
            $("#moviestable").children().show();
            $(".labelText").show();
            $(".labelText").children().show();
            $("#moviestable tbody tr").remove();
              $.ajax({
                             type: "GET",
                             url: "http://localhost:8090/api/ticket/" + user,
                             success: function (data) {
                              var table = $("#moviestable tbody");
                              data.forEach(ticket => {
                                let vote = ticket.vote == 0 ? '': ticket.vote
                                 var row = '<tr id=' + ticket.movieId + '>' +
                                     '<td name="movie">' + ticket.movieTitle + '</td>' +
                                     '<td name="date">' + ticket.date.slice(0,10) + '</td>' +
                                     '<td name="cinema" >' + ticket.cinemaName + '</td>' +
                                     '<td name="duration" >' + ticket.movieDuration + '</td>' +
                                     '<td name="vote" class="editvote">' + vote + '</td>' +
                                 '</tr>';
                                 table.append(row);
                                 })
                             },
                             error: function(eror) {
                                console.log(eror)
                             }
                         })
        });

$("input:checkbox").on('click', function() {
  var $box = $(this);
  if ($box.is(":checked")) {
    var group = "input:checkbox[name='" + $box.attr("name") + "']";
    $(group).prop("checked", false);
    $box.prop("checked", true);
    const choosen = $box.val();

    if(choosen === "rated") {
     $('#moviestable tbody tr').each(function(){
       $(this).show();
        const value = $(this).find('td[name="vote"]').text()
        if (value === "")
            $(this).hide();
     });
     } else {
       $('#moviestable tbody tr').each(function(){
         $(this).show();
            const value = $(this).find('td[name="vote"]').text()
            if (value !== "")
                $(this).hide();
         });
  } }
  else {
    $box.prop("checked", false);
     $('#moviestable tr').each(function(){
        $(this).show();
     })
    }
});

$("#moviestable").on("click", "td", function(event) {
    const name = $(this).attr("name")
    const value = $(this).text()
    if (name === "vote" &&  value === "") {
        $(this).html('<td>' +
        '<select name="cars" id="voteOptions">' +
           '<option value="1">1</option>' +
           '<option value="2">2</option>' +
          ' <option value="3">3</option>' +
           '<option value="4">4</option>' +
            '<option value="5">5</option>' +
         '</select>' +
        '<button id="rate" type="button" style="margin-left:4px" class="btn btn-success btn-sm">Rate</button></td>')
    } else {
    event.prop
    }
});

$("#moviestable").on("click", "#rate",function(event) {
    const movie = $(this).parents("tr").attr("id")
    const number = $("#voteOptions").val()
       var vote = {
                spectator: sessionStorage.getItem("username"),
                vote: number,
                movie: movie
            }
    var element = $(this).parents("td")
    $.ajax({
            type: "POST",
            url: "http://localhost:8090/api/movie/rate",
             data: JSON.stringify(vote),
             contentType: 'application/json',
            success: function () {
                element.html('<td name="vote" class="editvote">' + number + '</td>');
            },
            error: function() {
            }
        })
})
});


