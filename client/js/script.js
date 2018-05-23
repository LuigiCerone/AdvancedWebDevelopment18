$(function () {
    console.log("ready!");

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
});