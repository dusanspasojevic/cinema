$(document).ready(function(){
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/User/managers",
                success: function (data) {
                console.log(data)
                    data.forEach(user => {
                    var table = $("table tbody");
                    var row = '<tr id=' + user.username + '>' +
                        '<td>' + user.firstName + '</td>' +
                        '<td>' + user.lastName + '</td>' +
                        '<td>' + user.email + '</td>' +
                        '<td>' + user.phoneNumber + '</td>' +
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


	$('[data-toggle="tooltip"]').tooltip();
	var actions = $("table td:last-child").html();
	// Append table with add row form on add new button click
    $(".add-new").click(function(){
		$(this).attr("disabled", "disabled");
		var index = $("table tbody tr:last-child").index();
        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="firstName" id="firstName"></td>' +
            '<td><input type="text" class="form-control" name="lastName" id="lastName"></td>' +
            '<td><input type="text" class="form-control" name="email" id="email"></td>' +
            '<td><input type="text" class="form-control" name="phoneNumber" id="phoneNumber"></td>' +
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
			input.each(function(){
				$(this).parent("td").html($(this).val());
			});
			$(this).parents("tr").find(".add, .edit").toggle();
			$(".add-new").removeAttr("disabled");
		}
    });
	// Delete row on delete button click
	$(document).on("click", ".delete", function(){
	    const id = $(this).parents("tr").attr("id")
		 $.ajax({
                        type: "DELETE",
                        url: "http://localhost:8090/api/User/" + id,
                        success: function (data) {
                            $(`#${id}`).remove();
                           },
                        error: function() {
                        console.log('eror')
                        }
                    })
    });
});