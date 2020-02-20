AJS.$(document).ready(function() {
    AJS.$('.buttonUpdateWalker').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idWalkerWalker").innerHTML;
        var lastName = this.parentNode.parentNode.querySelector("#LastNameWalker").firstElementChild.value;
        var firstName = this.parentNode.parentNode.querySelector("#FirstNameWalker").firstElementChild.value;
        var middleName = this.parentNode.parentNode.querySelector("#MiddleNameWalker").firstElementChild.value;
        var birthday = this.parentNode.parentNode.querySelector("#BirthdayWalker").firstElementChild.value;
        var phone = this.parentNode.parentNode.querySelector("#PhoneWalker").firstElementChild.value;
        var email = this.parentNode.parentNode.querySelector("#EmailWalker").firstElementChild.value;
        var params = {
            "lastName": lastName,
            "firstName": firstName,
            "middleName": middleName,
            "birthday": birthday,
            "phone": phone,
            "email": email
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restwalker/1.0/restWalker/updWalker/" + id,
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

    AJS.$('.buttonDeleteWalker').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idWalkerWalker").innerHTML;
        AJS.$.ajax({
            type: "DELETE",
            url: "/rest/restwalker/1.0/restWalker/delWalker/" + id,
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

    dialog.addHeader("Adding a walker");

    dialog.addPanel("SinglePanel", "<p>LastName: <input id=\"LastNameDialogWalkerFromWalker\" value=\"Petr\"></p>" +
        "<p>FirstName: <input id=\"FirstNameDialogWalkerFromWalker\" value=\"Veselov\"></p>" +
        "<p>MiddleName: <input id=\"MiddleNameDialogWalkerFromWalker\" value=\"Genadevich\"></p>" +
        "<p>Birthday: <input id=\"BirthdayDialogWalkerFromWalker\" type=\"date\" max=\"2005-12-31\" value=\"1998-10-20\"></p>" +
        "<p>Phone: <input id=\"PhoneDialogWalkerFromWalker\" value=\"86758492204\"></p>" +
        "<p>Email: <input id=\"EmailDialogWalkerFromWalker\" value=\"veselov.walker@yandex.ru\"></p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var lastName = $("#LastNameDialogWalkerFromWalker").val();
        var firstName = $("#FirstNameDialogWalkerFromWalker").val();
        var middleName = $("#MiddleNameDialogWalkerFromWalker").val();
        var birthday = $("#BirthdayDialogWalkerFromWalker").val();
        var phone = $("#PhoneDialogWalkerFromWalker").val();
        var email = $("#EmailDialogWalkerFromWalker").val();
        var params = {
            "lastName": lastName,
            "firstName": firstName,
            "middleName": middleName,
            "birthday": birthday,
            "phone": phone,
            "email": email
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "POST",
            url: "/rest/restwalker/1.0/restWalker/newWalker/",
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

    AJS.$('#dialog-button-add-a-walker-from-walker').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});