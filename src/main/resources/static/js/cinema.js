$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
	var actions =  '<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a><a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/cinema",
                success: function (data) {
                console.log(data)
                    data.forEach(cinema => {
                    var table = $("table tbody");
                    var row = '<tr id=' + cinema.id + '>' +
                        '<td>' + cinema.name + '</td>' +
                        '<td>' + cinema.address + '</td>' +
                        '<td>' + cinema.email + '</td>' +
                        '<td>' + cinema.phone + '</td>' +
                        '<td>' + cinema.managersID + '</td>' +
                         '<td>' +
                         '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>' +
                         '</td>'
                    '</tr>';
                    table.append(row);
                    })
                },
                error: function() {
                }
            })


	$(".add-new").click(function(){
    		$(this).attr("disabled", "disabled");
    		var index = $("table tbody tr:last-child").index();
            var row = '<tr>' +
                '<td><input type="text" class="form-control" name="name" id="name"></td>' +
                '<td><input type="text" class="form-control" name="address" id="address"></td>' +
                '<td><input type="text" class="form-control" name="email" id="email"></td>' +
                '<td><input type="text" class="form-control" name="phone" id="phone"></td>' +
                 '<td><input type="text" class="form-control" name="managers" id="managers"></td>' +
    			'<td>' + actions + '</td>' +
            '</tr>';
        	$("table").append(row);
    		$("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
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
                        managersID: $(this).parents("tr").find('input[id="managers"]').val()
                    }),
                   success: function (data) {
                        console.log(data)
                       const id = data.id;
                        field.parents('tr').attr("id", id);
                       input.each(function(){$(this).parent("td").html($(this).val()) });
                       field.parents("tr").find(".add, .edit").toggle();
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