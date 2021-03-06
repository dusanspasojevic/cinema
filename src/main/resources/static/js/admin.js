$(document).ready(function(){
const user = sessionStorage.getItem("username");
if (!user)
    window.location.replace("http://localhost:8090/index.html");
    $('[data-toggle="tooltip"]').tooltip();
	var actions =   '<a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>' +
	                '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
    $.ajax({
                type: "GET",
                url: "http://localhost:8090/api/User/managers",
                success: function (data) {
                console.log(data)
                    data.forEach(user => {
                    var table = $("table tbody");

                    const activate = user.active? '': '<a class="activate" title="Activate" data-toggle="tooltip"> <i class="fa fa-check-circle" style="color:green"></i></a>'
                        console.log(activate)
                    var row = '<tr id=' + user.username + '>' +
                        '<td>' + user.firstName + '</td>' +
                        '<td>' + user.lastName + '</td>' +
                        '<td>' + user.email + '</td>' +
                        '<td>' + user.phoneNumber + '</td>' +
                         '<td>' + user.username + '</td>' +
                          '<td>' + user.birthDate.slice(0,10) + '</td>' +
                          '<td>' + user.password + '</td>' +
                         '<td>' + '<a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></i></a>' + activate +
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
                '<td><input type="text" class="form-control" name="firstName" id="firstName"></td>' +
                '<td><input type="text" class="form-control" name="lastName" id="lastName"></td>' +
                '<td><input type="text" class="form-control" name="email" id="email"></td>' +
                '<td><input type="text" class="form-control" name="phoneNumber" id="phoneNumber"></td>' +
                 '<td><input type="text" class="form-control" name="username" id="username"></td>' +
                 '<td><input type="text" class="form-control" name="birthDate" id="birthDate"></td>' +
                 '<td><input type="text" class="form-control" name="password" id="password"></td>' +
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
            const id = $(this).parents("tr").find('input[id="username"]').val()
            $(this).closest('tr').attr("id", id);
             $.ajax({
                    type: "POST",
                    url: "http://localhost:8090/api/User/managers/" ,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        username: id,
                        active: true,
                        role: 'MANAGER',
                        firstName: $(this).parents("tr").find('input[id="firstName"]').val(),
                        lastName: $(this).parents("tr").find('input[id="lastName"]').val(),
                        email: $(this).parents("tr").find('input[id="email"]').val(),
                        birthDate: $(this).parents("tr").find('input[id="birthDate"]').val(),
                        phoneNumber: $(this).parents("tr").find('input[id="phoneNumber"]').val(),
                        password: $(this).parents("tr").find('input[id="password"]').val()
                    }),
                   success: function (data) {

                       input.each(function(){$(this).parent("td").html($(this).val()) });
                       $(this).parents("tr").find(".add, .edit").toggle();
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
                        url: "http://localhost:8090/api/User/" + id,
                        success: function (data) {
                            $(`#${id}`).remove();
                           },
                        error: function() {
                        alert("Cinema managed by this manager has only one manager so manager can't be deleted.")
                        }
                    })
    });

    // Activate manager on click
    	$(document).on("click", ".activate", function(){
    	    const element = $(this).parents("tr")
    	    const id = element.attr("id")
    		 $.ajax({
                            type: "POST",
                            url: "http://localhost:8090/api/User/activate/" + id,
                            success: function (data) {
                                alert("Manager account is active now.")
                                element.find(".activate").remove()
                               },
                            error: function() {
                            alert("Can't perform action.")
                            }
                        })
        });
});