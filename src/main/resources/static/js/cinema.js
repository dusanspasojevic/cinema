$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
	var actions =  '<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
	                '<a class="save" title="Save" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
	                '<a class="edit"  title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>' +
	                '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/cinema",
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
                         '<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
                         '<a class="save" title="Save" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
                         '<a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>'   +
                         '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>' +
                         '</td>'
                    '</tr>';
                    table.append(row);
                    })

                    $(".save").hide();
                },
                error: function() {
                }
            })


// Edit row on edit button click
$(document).on("click", ".edit", function(){
    $(this).parents("tr").find("td:not(:last-child)").each(function(){
       console.log($(this))
        $(this).html('<input type="text" class="form-control" name="' + $(this).attr("name") + '" value="' + $(this).text() + '">');

    });
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
                    url: "http://localhost:8090/api/cinema/" + id ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        id: id,
                        name: field.find('input[name="name"]').val(),
                        address: field.find('input[name="address"]').val(),
                        email: field.find('input[name="email"]').val(),
                        phone: field.find('input[name="phone"]').val(),
                        managersID: field.find('input[name="managersID"]').val()
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


	$(".add-new").click(function(){
    		$(this).attr("disabled", "disabled");
    		var index = $("table tbody tr:last-child").index();
            var row = '<tr>' +
                '<td><input type="text" class="form-control" name="name" id="name"></td>' +
                '<td><input type="text" class="form-control" name="address" id="address"></td>' +
                '<td><input type="text" class="form-control" name="email" id="email"></td>' +
                '<td><input type="text" class="form-control" name="phone" id="phone"></td>' +
                 '<td><input type="text" class="form-control" name="managersID" id="managersID"></td>' +
    			'<td>' + actions + '</td>' +
            '</tr>';
        	$("table").append(row);
    		$("table tbody tr").eq(index + 1).find(".add, .edit, .delete").toggle();
    		$("table tbody tr").eq(index + 1).find(".save").hide();
            $('[data-toggle="tooltip"]').tooltip();
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
                    url: "http://localhost:8090/api/cinema/" ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        name: $(this).parents("tr").find('input[id="name"]').val(),
                        address: $(this).parents("tr").find('input[id="address"]').val(),
                        email: $(this).parents("tr").find('input[id="email"]').val(),
                        phone: $(this).parents("tr").find('input[id="phone"]').val(),
                        managersID: $(this).parents("tr").find('input[id="managersID"]').val()
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
	// Delete row on delete button click
	$(document).on("click", ".delete", function(){
	    const id = $(this).parents("tr").attr("id")
		 $.ajax({
                        type: "DELETE",
                        url: "http://localhost:8090/api/cinema/" + id,
                        success: function (data) {
                            $(`#${id}`).remove();
                           },
                        error: function() {
                        console.log('eror')
                        }
                    })
    });
});