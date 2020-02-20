AJS.$(document).ready(function() {
    var elems = document.querySelectorAll("#hidddd");
    elems.forEach(function (item) {
        item.hidden = true;
    });

    AJS.$('.buttonUpdatePaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idPaddockPaddock").innerHTML;
        var place = this.parentNode.parentNode.querySelector("#PlacePaddock").firstElementChild.value;
        var walkingTime = this.parentNode.parentNode.querySelector("#WalkingTimePaddock").firstElementChild.value;
        var duration = this.parentNode.parentNode.querySelector("#DurationPaddock").firstElementChild.value;
        var status = this.parentNode.parentNode.querySelector("#StatusPaddock").firstElementChild.value;
        var idWalker = this.parentNode.parentNode.querySelector("#WalkerPaddock").firstElementChild.value;
        var idClient = this.parentNode.parentNode.querySelector("#ClientPaddock").firstElementChild.value;
        // var idDogs =  this.parentNode.parentNode.querySelector("#IdDogsPaddock").firstElementChild.value;
        var wTime;
        if (walkingTime != "")
            wTime = Date.parse(walkingTime);
        else wTime = 0;
        var params = {
            "place": place,
            "duration": duration,
            // "idDogs": idDogs,
            "status": status,
            "close": close
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restpaddock/1.0/restPaddock/updPaddock/" + id + '/' + idWalker + '/' + idClient + '/' + wTime,
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

    AJS.$('.buttonDeletePaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idPaddockPaddock").innerHTML;
        jQuery.ajax({
            type: "DELETE",
            url: "/rest/restpaddock/1.0/restPaddock/delPaddock/" + id,
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

    dialog.addHeader("Adding a paddock");

    var idClientClientPaddock = document.querySelectorAll("#idClientClientPaddock");
    var idWalkerWalkerPaddock = document.querySelectorAll("#idWalkerWalkerPaddock");
    var idDogDogPaddock = document.querySelectorAll("#idDogDogPaddock");

    function selectClient() {
        var result = "<select id=\"idClientDialogPaddockFromPaddock\">";
        for (var i = 0; i < idClientClientPaddock.length; i++) {
            result += "<option>" + idClientClientPaddock.item(i).innerHTML + "</option>";
        }
        result += "</select>";
        return result;
    }

    function selectWalker() {
        var result = "<select id=\"idWalkerDialogPaddockFromPaddock\">";
        for (var i = 0; i < idWalkerWalkerPaddock.length; i++) {
            result += "<option>" + idWalkerWalkerPaddock.item(i).innerHTML + "</option>";
        }
        result += "</select>";
        return result;
    }

    function selectDog() {
        var result = "";
        idDogDogPaddock.forEach(function (item) {
            result += "<input type='checkbox' id='idDogDialogPaddockFromPaddock' value='" +
                item.innerHTML + "'>" + item.innerHTML + "  ";
        });
        return result;
    }

    function getCountDogs() {
        var ingredients = document.querySelectorAll('#idDogDialogPaddockFromPaddock');
        var count = 0;
        for (var i = 0; i < ingredients.length; i++) {
            if (ingredients[i].checked) {
                count++;
            }
        }
        return count;
    }

    function getTrueDog() {
        var ingredients = document.querySelectorAll('#idDogDialogPaddockFromPaddock');
        var arr = [];
        for (var i = 0; i < ingredients.length; i++) {
            if (ingredients[i].checked) {
                arr.push(ingredients[i].value);
            }
        }
        var result = "";
        if (arr.length > 0)
            result += arr[0];
        for (var i = 1; i < arr.length; i++) {
            result += "&" + arr[i];
        }
        return result;
    }


    dialog.addPanel("SinglePanel", "<p>Place: " +
        "<select id='placeDialogPaddockFromPaddock'>" +
        "            <option>Площадь Горького</option>" +
        "            <option>Площадь Минина</option>" +
        "            <option>Центр Сормово</option>" +
        "            <option>Московский вокзал</option>" +
        "            <option>Пушкинский парк</option> " +
        "</select>" +
        "<p>Walking time: <input type='datetime-local' id='walkingTimeDialogPaddockFromPaddock' value='2020-02-20T08:30'></p>" +
        "<p>Duration: <input type='number' id='durationDialogPaddockFromPaddock' value='40'></p>" +
        "<p>Id walker:" + selectWalker() + "</p>" +
        "<p>Id client:" + selectClient() + "</p>" +
        "<p>Id dog:" + selectDog() + "</p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var place = $("#placeDialogPaddockFromPaddock").val();
        var walkingTime = $("#walkingTimeDialogPaddockFromPaddock").val();
        var duration = $("#durationDialogPaddockFromPaddock").val();
        var status = "open";
        var idWalker = $("#idWalkerDialogPaddockFromPaddock").val();
        var idClient = $("#idClientDialogPaddockFromPaddock").val();
        var wTime = Date.parse(walkingTime);
        var countDogs = getCountDogs();
        var idDogs = getTrueDog();
        var params = {
            "place": place,
            "duration": duration,
            "status": status,
            "idDogs": idDogs
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        var checkBusyWalker = false;
        AJS.$.ajax({
            type: "GET",
            dataType: "json",
            url: "/rest/restpaddock/1.0/restPaddock/busyWalker/" + idWalker + '/' + wTime + '/' + duration,
            contentType: "application/json",
            async: false,
            success: function (data) {
                checkBusyWalker = data;
            },
            error: function (data) {
                alert('ERROR in busyWalker: ' + data);
            }
        });
        if (countDogs > 0) {
            if (checkBusyWalker) {
                AJS.$.ajax({
                    type: "POST",
                    url: "/rest/restpaddock/1.0/restPaddock/newPaddock/" + idWalker + '/' + idClient + '/' + wTime,
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
            } else {
                alert("This Walker is busy, please choose another one or choose different time!");
            }

        } else {
            alert("You must select at least one dog!");
        }
    });

    dialog.addLink("Cancel", function (dialog) {
        dialog.hide();
    }, "#");

    AJS.$('#dialog-button-add-a-paddock-from-paddock').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});
