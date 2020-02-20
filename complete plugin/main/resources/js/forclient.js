AJS.$(document).ready(function() {

    AJS.$('.buttonUpdateDogFromForClient').click(function () {
        var id = this.parentNode.parentNode.querySelector("#id").innerHTML;
        var name = this.parentNode.parentNode.querySelector("#name").firstElementChild.value;
        var gender = this.parentNode.parentNode.querySelector("#gender").firstElementChild.value;
        var birthday = this.parentNode.parentNode.querySelector("#birthday").firstElementChild.value;
        var breed = this.parentNode.parentNode.querySelector("#breed").firstElementChild.value;
        var color = this.parentNode.parentNode.querySelector("#color").firstElementChild.value;
        var character = this.parentNode.parentNode.querySelector("#character").firstElementChild.value;
        var idClient = document.querySelector("#idForClient").innerHTML;
        var params = {
            "name": name,
            "gender": gender,
            "birthday": birthday,
            "breed": breed,
            "color": color,
            "character": character
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restdog/1.0/restDog/updDog/" + id + '/' + idClient,
            dataType: "json",
            contentType: "application/json",
            data: json,
            success: function (data) {
                alert("OKAY, update " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });

    AJS.$('.buttonDeleteDogFromForClient').click(function () {
        var id = this.parentNode.parentNode.querySelector("#id").innerHTML;
        AJS.$.ajax({
            type: "DELETE",
            url: "/rest/restdog/1.0/restDog/delDog/" + id,
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

    dialog.addHeader("Adding a dog");

    dialog.addPanel("SinglePanel", "<p>Name: <input id=\"nameDialogDogFromForClient\" value=\"Pesik\"></p>" +
        "<p>Gender: <input id=\"genderDialogDogFromForClient\" value=\"M\"></p>" +
        "<p>Birthday: <input id=\"birthdayDialogDogFromForClient\" type=\"date\" max=\"2019-12-31\" value=\"2015-09-14\"></p>" +
        "<p>Breed: <input id=\"breedDialogDogFromForClient\" value=\"DOG\"></p>" +
        "<p>Color: <input id=\"colorDialogDogFromForClient\" value=\"Black\"></p>" +
        "<p>Character: <input id=\"characterDialogDogFromForClient\" value=\"Good\"></p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var idClient = document.querySelector("#idForClient").innerHTML;
        var name = $("#nameDialogDogFromForClient").val();
        var gender = $("#genderDialogDogFromForClient").val();
        var birthday = $("#birthdayDialogDogFromForClient").val();
        var breed = $("#breedDialogDogFromForClient").val();
        var color = $("#colorDialogDogFromForClient").val();
        var character = $("#characterDialogDogFromForClient").val();
        var params = {
            "name": name,
            "gender": gender,
            "birthday": birthday,
            "breed": breed,
            "color": color,
            "character": character
        }
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "POST",
            url: "/rest/restdog/1.0/restDog/newDog/" + idClient,
            dataType: "json",
            contentType: "application/json",
            data: json,
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

    AJS.$('#dialog-button-add-a-dog-from-for-client').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});