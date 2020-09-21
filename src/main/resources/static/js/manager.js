$(document).ready(function(){
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");
$('[data-toggle="tooltip"]').tooltip();
$("#halltable").children().hide();
$("#prjtable").children().hide();
$("#addProj").hide();
$("#addBtn").hide();

     var actions =  '<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
     	                '<a class="save" title="Save" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
     	                '<a class="edit"  title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>' +
     	                '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/cinema/" + sessionStorage.getItem("username") + "/manager",
                success: function (data) {
                console.log(data)
                    data.forEach(cinema => {
                    var table = $("#cinematable tbody");
                    var row = '<tr id=' + cinema.id + '>' +
                        '<td name="name">' + cinema.name + '</td>' +
                        '<td name="address">' + cinema.address + '</td>' +
                        '<td name="email" >' + cinema.email + '</td>' +
                        '<td name="phone" >' + cinema.phone + '</td>' +
                        '<td name="managersID" >' + cinema.managersID + '</td>' +
                         '<td>' +
                         '<a class="hall" ><i class="material-icons dashboard_customize">&#xe99b;</i></a>' +
                         '</td>'+
                         '<td>' +
                                                  '<a class="schedule" ><i class="material-icons dashboard_customize">&#xe99b;</i></a>' +
                                                  '</td>'
                    '</tr>';
                    table.append(row);
                    })


                },
                error: function() {
                }
            })

var cinemaName;
var cinemaId;
//Show all halls on click
$(document).on("click", ".hall", function(){
    const id =  $(this).parents("tr").attr("id")
    cinemaName = $(this).parents("tr").find('td[name="name"]').text()
    cinemaId = id;

     $.ajax({
        type: "GET",
        url: "http://localhost:8090/api/hall/" + id +'/cinema',
       success: function (data) {
           $("#cinematable").children().hide();
           $("#halltable").children().show();


            data.forEach(hall => {
                               var table = $("#halltable tbody");
                               var row = '<tr id=' + hall.id + '>' +
                                 '<td name="label">' + hall.label + '</td>' +
                                     '<td name="capacity" >' + hall.capacity + '</td>' +
                                   '<td name="cinema">' + hall.cinema + '</td>' +
                                   '<td name="cinemaName">' + hall.cinemaName + '</td>' +
                                    '<td>' + actions +
                                    '</td>'
                               '</tr>';
                               table.append(row);

                               })

                                $(".save").hide();
                                 $("#addBtn").show();
        },
        error: function() {
            console.log('eror u dobavljanju sala')
        }
            })
})


$(document).on("click", ".schedule", function(){
    const id =  $(this).parents("tr").attr("id")
    cinemaName = $(this).parents("tr").find('td[name="name"]').text()
    cinemaId = id;

     $.ajax({
        type: "GET",
        url: "http://localhost:8090/api/projection/cinema/" + id ,
       success: function (data) {
           $("#cinematable").children().hide();
           $("#halltable").children().hide();
           $("#prjtable").children().show();
          $("#addProj").show();
            var table = $("#prjtable tbody");
                console.log(data)
             data.forEach(projection => {

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
                                                     '<td name="hall" >' + projection.hallName + '</td>' +
                                               '</tr>';
                                               table.append(row);
                                               })
        },
        error: function() {
            console.log('eror u dobavljanju sala')
        }
            })
})


// Edit row on edit button click
$(document).on("click", ".edit", function(){
    $(this).parents("tr").find("td:not(:last-child)").each(function(){
       console.log($(this))
        $(this).html('<input type="text" class="form-control" name="' + $(this).attr("name") + '" value="' + $(this).text() + '">');
    });
    $(this).parents("tr").find('input[name="cinema"]').prop('disabled', true);
     $(this).parents("tr").find('input[name="cinemaName"]').prop('disabled', true);
    $(this).parents("tr").find(".edit").toggle();
    $(this).parents("tr").find(".save").show();
    $(".add-new").attr("disabled", "disabled");
});


//Save edit changed
$(document).on("click", ".save", function(){
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function(){
            if(!$(this).val()){
                $(this).addClass("error");
                empty = true;
            } else{
                $(this).removeClass("error");
            }
        });
        $(this).parents("tr").find(".error").first().focus();
        if(!empty){
        var field =  $(this).parents("tr")
        const id = field.attr("id")
        console.log(field)
             $.ajax({
                    type: "PUT",
                    url: "http://localhost:8090/api/hall/" + id ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        id: id,
                        label: field.find('input[name="label"]').val(),
                        cinema: field.find('input[name="cinema"]').val(),
                        capacity: field.find('input[name="capacity"]').val(),
                        cinemaName: field.find('input[name="cinemaName"]').val(),
                    }),
                   success: function (data) {
                       input.each(function(){$(this).parent("td").html($(this).val()) });
                       field.find(".edit").toggle();
                       $(".add-new").removeAttr("disabled");
                        field.find(".save").hide();
                    },
                    error: function() {
                        console.log('eror u editovanju')
                    }
                    })
        }
    });

// Delete row on delete button click
	$(document).on("click", ".delete", function(){
	    const id = $(this).parents("tr").attr("id")
		 $.ajax({
                        type: "DELETE",
                        url: "http://localhost:8090/api/hall/" + id,
                        success: function (data) {
                            $(`#${id}`).remove();
                           },
                        error: function() {
                        console.log('eror')
                        }
                    })
    });

//Add row for inserting new values
$(".add-new").click(function(){
        $(this).attr("disabled", "disabled");
        var index = $("#halltable tbody tr:last-child").index();

        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="label" id="label"></td>' +
            '<td><input type="text" class="form-control" name="capacity" id="capacity"></td>' +
            '<td><input type="text" disabled="true" class="form-control"  id="cinema" name="cinema" value="' +
                 cinemaId + '"></td>' +
            '<td><input type="text" disabled="true" class="form-control"  id="cinemaName" name="cinemaName" value="' + cinemaName + '"></td>' +
            '<td>' + actions + '</td>' +
        '</tr>';
        $("#halltable").append(row);
        $("#halltable tbody tr").eq(index + 1).find(".add, .edit, .delete").toggle();
        $("#halltable tbody tr").eq(index + 1).find(".save").hide();
        $('[data-toggle="tooltip"]').tooltip();
});

//Add row for inserting new projection
$(".addProjection").click(function(){
        $(this).attr("disabled", "disabled");
        var index = $("#prjtable tbody tr:last-child").index();
        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="movie" id="movie"></input></td>' +
             '<td><input type="text" disabled="true" class="form-control"/></td>' +
                 '<td><input type="text" disabled="true" class="form-control" /></td>' +
                     '<td><input type="text" disabled="true" class="form-control" /></td>' +
                         '<td><input type="text" disabled="true" class="form-control" /></td>' +
            '<td><input type="text" class="form-control" name="date" id="date"/></td>' +
            '<td><input type="text" class="form-control"  id="price" name="price"/ ></td>' +
             '<td><input type="text" disabled="true" class="form-control"/ ></td>' +
                                     '<td><input type="text" disabled="true" class="form-control"/ ></td>' +
             '<td><input type="text" name="hall"  class="form-control"/ ></td>' +
                                     '<td><button type="button" id="saveProjBtn" class="btn btn-success">Add</button></td>'
        '</tr>';
        $("#prjtable tbody").append(row);
});


    	// Add row on add button click
    $(document).on("click", ".add", function(){
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function(){
            if(!$(this).val()){
                $(this).addClass("error");
                empty = true;
            } else{
                $(this).removeClass("error");
            }
        });
        $(this).parents("tr").find(".error").first().focus();
        if(!empty){
        var field =  $(this)
             $.ajax({
                    type: "POST",
                    url: "http://localhost:8090/api/hall/" ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        label: $(this).parents("tr").find('input[id="label"]').val(),
                        capacity: $(this).parents("tr").find('input[id="capacity"]').val(),
                        cinema: $(this).parents("tr").find('input[id="cinema"]').val(),
                        cinemaName: $(this).parents("tr").find('input[id="cinemaName"]').val(),
                    }),
                   success: function (data) {
                        console.log(data)
                       const id = data.id;
                        field.parents('tr').attr("id", id);
                       input.each(function(){
                            $(this).parent("td").attr("name", $(this).attr("name"));
                            console.log($(this).attr("name"))
                             $(this).parent("td").html($(this).val());
                       });

                       field.parents("tr").find(".add, .edit, .delete").toggle();
                       $(".add-new").removeAttr("disabled");
                    },
                    error: function() {
                        console.log('eror')
                    }
                    })
        }
    });

//Add projection on save button
$(document).on("click", "#saveProjBtn", function(){
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]:not(:disabled)');
        input.each(function(){

            if(!$(this).val()){
                $(this).addClass("error");
                empty = true;
            } else{
                $(this).removeClass("error");
            }
        });
        $(this).parents("tr").find(".error").first().focus();
        if(!empty){
        var field =  $(this)
             $.ajax({
                    type: "POST",
                    url: "http://localhost:8090/api/projection/" ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        dateTime: $(this).parents("tr").find('input[name="date"]').val(),
                        movie: $(this).parents("tr").find('input[name="movie"]').val(),
                        price: $(this).parents("tr").find('input[name="price"]').val(),
                        hall: $(this).parents("tr").find('input[name="hall"]').val(),
                    }),
                   success: function (projection) {
                        console.log(projection)
                        var row =  '<td name="movie">' + projection.movieTitle + '</td>' +
                                                                            '<td name="duration">' + parseInt(projection.duration) + '</td>' +
                                                                           '<td name="cinema">' + projection.cinemaName + '</td>' +
                                                                           '<td name="genre" >' + projection.genre + '</td>' +
                                                                           '<td name="desc" >' + projection.desc + '</td>' +
                                                                           '<td name="date" >' + projection.time.slice(0,10) + '</td>' +
                                                                           '<td name="price" >' + parseInt(projection.price) + '</td>' +
                                                                            '<td name="vote" >' + parseFloat(projection.vote) + '</td>' +
                                                                             '<td name="seats" >' + parseInt(projection.notReservedSeats) + '</td>' +
                                                                             '<td name="hall" >' + projection.hallName + '</td>';
                        field.parents("tr").html(row);


                       $(".addProjection").removeAttr("disabled");
                    },
                    error: function() {
                        console.log('eror')
                    }
                    })
        }
    });

});