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
            xhrFields: {
                withCredentials: true
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            crossDomain: true,
            success: function (data, text, xhr) {
                // debugger;
                console.log(xhr.getResponseHeader('Set-Cookie'));
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#logout').on('click', function (event) {
        $.ajax({
            url: "http://localhost:8080/awd18/rest/auth",
            type: "DELETE",
            xhrFields: {
                withCredentials: true
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            crossDomain: true,
            success: function (data, text, xhr) {
                console.log(data);
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

    $('#studentLookupInfo').on('click', function () {
        var studentId = $('#studentInput').val();
        console.log(studentId);
        // Selected company URL is stored in variable valueSelected.
        $.ajax({
            url: "http://localhost:8080/awd18/rest/auth/studenti/" + studentId,
            type: "GET",
            // data: {id : menuId},
            // crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            success: function (response) {
                console.log(response);
                $('#studentInfo').hide().empty();
                var table = document.createElement("table");
                $(table).addClass("table");

                // Append table's thead.
                $(table).append("<thead> <tr><th>Campo</th><th>Valore</th></tr></thead>");
                var tbody = document.createElement("tbody");

                // Change fields value with response data.
                $(tbody).append("<tr><td>Nome</td><td>" + response.firstName + "</td></tr>");
                $(tbody).append("<tr><td>Cognome</td><td>" + response.lastName + "</td></tr>");
                $(tbody).append("<tr><td>Data di nascita</td><td>" + response.birthDate + "</td></tr>");
                $(tbody).append("<tr><td>Luogo di nascita</td><td>" + response.birthPlace + "</td></tr>");
                $(tbody).append("<tr><td>Codice fiscale</td><td>" + response.cf + "</td></tr>");
                $(tbody).append("<tr><td>Livello studi</td><td>" + response.universityLevel + "</td></tr>");
                $(tbody).append("<tr><td>Corso di studi</td><td>" + response.universityCourse + "</td></tr>");
                $(table).append(tbody);

                $('#studentInfo').append(table).fadeIn("slow");
            },
            error: function (xhr, text, error) {
                debugger;
                $('#studentInfo').empty();
                $('#studentInfo').append('<h3>' + xhr.status + ' - ' + xhr.responseText + '</h3>');
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