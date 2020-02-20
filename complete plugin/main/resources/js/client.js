AJS.$(document).ready(function() {
    AJS.$('.buttonUpdateClient').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idClientClient").innerHTML;
        var lastName = this.parentNode.parentNode.querySelector("#LastNameClient").firstElementChild.value;
        var firstName = this.parentNode.parentNode.querySelector("#FirstNameClient").firstElementChild.value;
        var middleName = this.parentNode.parentNode.querySelector("#MiddleNameClient").firstElementChild.value;
        var birthday = this.parentNode.parentNode.querySelector("#BirthdayClient").firstElementChild.value;
        var address = this.parentNode.parentNode.querySelector("#AddressClient").firstElementChild.value;
        var phone = this.parentNode.parentNode.querySelector("#PhoneClient").firstElementChild.value;
        var email = this.parentNode.parentNode.querySelector("#EmailClient").firstElementChild.value;
        var params = {
            "lastName": lastName,
            "firstName": firstName,
            "middleName": middleName,
            "birthday": birthday,
            "address": address,
            "phone": phone,
            "email": email
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restclient/1.0/restClient/updClient/" + id,
            data: json,
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                alert("OKAY, update " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });

    AJS.$('.buttonDeleteClient').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idClientClient").innerHTML;
        AJS.$.ajax({
            type: "DELETE",
            url: "/rest/restclient/1.0/restClient/delClient/" + id,
            dataType: "json",
            contentType: "application/json",
            success: function () {
                alert("OKAY, delete");
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var dialog = new AJS.Dialog({
        width: 800,
        height: 500,
        id: "example-dialog",
        closeOnOutsideClick: true
    });

    dialog.addHeader("Adding a client");

    dialog.addPanel("SinglePanel", "<p>LastName: <input id=\"LastNameDialogClientFromClient\" value=\"Vlad\"></p>" +
        "<p>FirstName: <input id=\"FirstNameDialogClientFromClient\" value=\"Kulibin\"></p>" +
        "<p>MiddleName: <input id=\"MiddleNameDialogClientFromClient\" value=\"Valerevich\"></p>" +
        "<p>Birthday: <input id=\"BirthdayDialogClientFromClient\" type=\"date\" max=\"2005-12-31\" value=\"1990-03-04\"></p>" +
        "<p>Address: <input id=\"AddressDialogClientFromClient\" value=\"Minina\"></p>" +
        "<p>Phone: <input id=\"PhoneDialogClientFromClient\" value=\"89756472284\"></p>" +
        "<p>Email: <input id=\"EmailDialogClientFromClient\" value=\"vlad@yandex.ru\"></p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var lastName = $("#LastNameDialogClientFromClient").val();
        var firstName = $("#FirstNameDialogClientFromClient").val();
        var middleName = $("#MiddleNameDialogClientFromClient").val();
        var birthday = $("#BirthdayDialogClientFromClient").val();
        var address = $("#AddressDialogClientFromClient").val();
        var phone = $("#PhoneDialogClientFromClient").val();
        var email = $("#EmailDialogClientFromClient").val();
        var params = {
            "lastName": lastName,
            "firstName": firstName,
            "middleName": middleName,
            "birthday": birthday,
            "address": address,
            "phone": phone,
            "email": email
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "POST",
            url: "/rest/restclient/1.0/restClient/newClient/",
            data: json,
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                alert("OKAY, add " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        dialog.hide();
    });

    dialog.addLink("Cancel", function (dialog) {
        dialog.hide();
    }, "#");

    AJS.$('#dialog-button-add-a-client-from-client').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});
