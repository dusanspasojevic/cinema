$(document).ready(function(){
      $('[data-toggle="tooltip"]').tooltip();
     $("#halltable").children().hide();

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
                         '</td>'
                    '</tr>';
                    table.append(row);
                    })


                },
                error: function() {
                }
            })


//Show all halls on click
$(document).on("click", ".hall", function(){
    const id =  $(this).parents("tr").attr("id")
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

});