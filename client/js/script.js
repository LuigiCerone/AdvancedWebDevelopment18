$(function () {
    $.ajax({
        url: "http://localhost:8080/awd18/rest/aziende",
        type: "GET",
        // data: {id : menuId},
        dataType: "json",
        success: function (response) {
            $.each(response, function (i, value) {
                console.log(value);
                $('#companySelect').append($('<option>').text(value.nome).attr('value', value.url));
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
    });

    $('#companySelect').on('change', function (e) {
        var optionSelected = $("option:selected", this);
        // Selected company URL is stored in variable valueSelected.
        var valueSelected = this.value;
        $.ajax({
            url: valueSelected,
            type: "GET",
            // data: {id : menuId},
            dataType: "json",
            success: function (response) {
                // Clear old values.
                var table = $('#companyTable').clone()
                $('#companyInfoBody').empty();
                $('#companyInfoBody').append(table);

                console.log(response);

                $('#socialRegion').text(response.socialRegion);
                $('#legalAddress').text(response.legalAddress);
                $('#piva').text(response.piva);
                $('#lawyer').text(response.lawyerFirstName + " " + response.lawyerLastName);
                $('#person').text(response.personFirstName + " " + response.personLastName);
                $('#personTel').text(response.personTelNumber);
                $('#legalForum').text(response.legalForum);
                $('#active').text(response.active ? "Si" : "No");

                $('#internshipListTrigger').attr('data-id', response.id);

                $('#companyInfo').fadeIn("slow");
            },
            error: function (error) {
                console.log(error);
            }
        });

    });

    $('#companyInternshipModal').on('show.bs.modal', function (e) {
        var id = $('#internshipListTrigger').attr('data-id');
        $.ajax({
            url: "http://localhost:8080/awd18/rest/aziende/" + id + "/offerte",
            type: "GET",
            // data: {id : menuId},
            dataType: "json",
            success: function (response) {
                // Clear old values.
                $('#companyInternshipModalBody').empty();


                $.each(response, function (index, internship) {
                    console.log(internship);
                    var panel = document.createElement("div");
                    $(panel).addClass("panel panel-default");
                    var panelHeader = document.createElement("div");
                    $(panelHeader).addClass("panel-heading");
                    panelHeader.innerHTML = "<h3>" + internship.place + "</h3>";
                    panel.append(panelHeader);

                    var panelBody = document.createElement("div");
                    $(panelBody).addClass("panel-body");
                    panelBody.innerHTML = "<p>test</p>";
                    panel.append(panelBody);

                    $('#companyInternshipModalBody').append(panel);
                    var hr = document.createElement("hr");
                    $('#companyInternshipModalBody').append(hr);

                });
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#studentSelect').on('change', function (e) {
        var optionSelected = $("option:selected", this);
        // Selected company URL is stored in variable valueSelected.
        var valueSelected = this.value;
        $.ajax({
            url: valueSelected,
            type: "GET",
            // data: {id : menuId},
            dataType: "json",
            success: function (response) {
                // Clear old values.
                var table = $('#companyTable').clone()
                $('#companyInfoBody').empty();
                $('#companyInfoBody').append(table);

                console.log(response);

                $('#socialRegion').text(response.socialRegion);
                $('#legalAddress').text(response.legalAddress);
                $('#piva').text(response.piva);
                $('#lawyer').text(response.lawyerFirstName + " " + response.lawyerLastName);
                $('#person').text(response.personFirstName + " " + response.personLastName);
                $('#personTel').text(response.personTelNumber);
                $('#legalForum').text(response.legalForum);
                $('#active').text(response.active ? "Si" : "No");

                $('#internshipListTrigger').attr('data-id', response.id);

                $('#companyInfo').fadeIn("slow");
            },
            error: function (error) {
                console.log(error);
            }
        });

    });
});


// var table = document.createElement("table");
// $(table).addClass("table");
// var thead = document.createElement("thead");
// // thead.innerHTML = "<tr><th>1</th><th>2</th></tr>";
// table.append(thead);
// var tbody = document.createElement("tbody");
//
// $.each(response, function (key, value) {
//     var tr = document.createElement("tr");
//     var td = document.createElement("td");
//     td.innerHTML = "<b>" + key + "</b>";
//     tr.append(td);
//     var td1 = document.createElement("td");
//     td1.innerText = value;
//     tr.append(td1);
//
//     tbody.append(tr);
// });
//
// table.append(tbody);
// $('#companyInfoBody').append(table);