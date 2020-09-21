$(document).ready(function(){
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");
$("#moviestable").children().hide();
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
            $(".labelText").children().show();
              $.ajax({
                             type: "GET",
                             url: "http://localhost:8090/api/ticket/" + user,
                             success: function (data) {
                              var table = $("#moviestable tbody");
                              data.forEach(ticket => {
                                let vote = ticket.vote == 0 ? '': ticket.vote
                                 var row = '<tr id=' + ticket.id + '>' +
                                     '<td name="movie">' + ticket.movieTitle + '</td>' +
                                     '<td name="date">' + ticket.date.slice(0,10) + '</td>' +
                                     '<td name="cinema" >' + ticket.cinemaName + '</td>' +
                                     '<td name="duration" >' + ticket.movieDuration + '</td>' +
                                     '<td name="vote" >' + vote + '</td>' +
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
});

