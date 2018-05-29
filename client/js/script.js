$(function () {
    $.ajax({
        url: "http://localhost:8080/awd18/rest/aziende",
        type: "GET",
        // data: {id : menuId},
        dataType: "json",
        success: function (response) {
            $.each(response, function (i, value) {
                console.log(value);
                $('#companySelect').append($('<option>').text(value.nome).attr('value', value.nome));
            });
        },
        error: function (error) {
            console.log(error);
        }
    });


    $('#loginForm').on('submit', function (event) {
        console.log("Clicked!");
        event.preventDefault();
        $.ajax({
            url: "http://localhost:8080/awd18/rest/auth",
            type: "POST",
            data: JSON.stringify({
                "email": $('#email').val(),
                "password": $('#password').val()
            }),
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                console.log("Cookie:" + document.cookie);
                $('#cookie').text(document.cookie);
                console.log(response);
            },
            error: function (error) {
                console.log(error);
            }
        });
    })
});