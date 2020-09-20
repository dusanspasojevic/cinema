$(document).ready(function(){
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/cinema/" + sessionStorage.getItem("username") + "/manager",
                success: function (data) {
                console.log(data)
                    data.forEach(cinema => {
                    var table = $("table tbody");
                    var row = '<tr id=' + cinema.id + '>' +
                        '<td name="name">' + cinema.name + '</td>' +
                        '<td name="address">' + cinema.address + '</td>' +
                        '<td name="email" >' + cinema.email + '</td>' +
                        '<td name="phone" >' + cinema.phone + '</td>' +
                        '<td name="managersID" >' + cinema.managersID + '</td>' +
                         '<td>' +
                         '<a class="halls" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
                         '</td>'
                    '</tr>';
                    table.append(row);
                    })

                    $(".save").hide();
                },
                error: function() {
                }
            })


});