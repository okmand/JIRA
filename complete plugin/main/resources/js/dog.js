AJS.$(document).ready(function() {
    AJS.$('.buttonUpdateDog').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idDogDog").innerHTML;
        var name = this.parentNode.parentNode.querySelector("#NameDog").firstElementChild.value;
        var gender = this.parentNode.parentNode.querySelector("#GenderDog").firstElementChild.value;
        var birthday = this.parentNode.parentNode.querySelector("#BirthdayDog").firstElementChild.value;
        var breed = this.parentNode.parentNode.querySelector("#BreedDog").firstElementChild.value;
        var color = this.parentNode.parentNode.querySelector("#ColorDog").firstElementChild.value;
        var character = this.parentNode.parentNode.querySelector("#CharacterDog").firstElementChild.value;
        var idClient = this.parentNode.parentNode.querySelector("#ClientDog").firstElementChild.value;
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

    AJS.$('.buttonDeleteDog').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idDogDog").innerHTML;
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

    var idClientClientDog = document.querySelectorAll("#idClientClientDog");

    function selectClient() {
        var result = "<select id=\"idClientDialogDogFromDog\">";
        for (var i = 0; i < idClientClientDog.length; i++) {
            result += "<option>" + idClientClientDog.item(i).innerHTML + "</option>";
        }
        result += "</select>";
        return result;
    }

    dialog.addPanel("SinglePanel", "<p>Name: <input id=\"nameDialogDogFromDog\" value=\"Pesik\"></p>" +
        "<p>Gender: <input id=\"genderDialogDogFromDog\" value=\"M\"></p>" +
        "<p>Birthday: <input id=\"birthdayDialogDogFromDog\" type=\"date\" max=\"2019-12-31\" value=\"2015-09-14\"></p>" +
        "<p>Breed: <input id=\"breedDialogDogFromDog\" value=\"DOG\"></p>" +
        "<p>Color: <input id=\"colorDialogDogFromDog\" value=\"Black\"></p>" +
        "<p>Character: <input id=\"characterDialogDogFromDog\" value=\"Good\"></p>" +
        "<p>Id client: " + selectClient() + "</p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var name = $("#nameDialogDogFromDog").val();
        var gender = $("#genderDialogDogFromDog").val();
        var birthday = $("#birthdayDialogDogFromDog").val();
        var breed = $("#breedDialogDogFromDog").val();
        var color = $("#colorDialogDogFromDog").val();
        var character = $("#characterDialogDogFromDog").val();
        var idClient = $("#idClientDialogDogFromDog").val();
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

    AJS.$('#dialog-button-add-a-dog-from-dog').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});
