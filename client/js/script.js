$(function() {
    console.log( "ready!" );

    var request = $.ajax({
        url: "http://localhost:8080/aziende",
        type: "GET",
        // data: {id : menuId},
        dataType: "json",
        success: function (response){
            console.log(response);
        },
        error: function (error) {
            console.log(error);
          }
      });
});