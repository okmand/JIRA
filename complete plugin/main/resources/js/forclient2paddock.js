AJS.$(document).ready(function() {
    var dialog = new AJS.Dialog({
        width: 800,
        height: 500,
        id: "example-dialog",
        closeOnOutsideClick: true
    });

    dialog.addHeader("Adding a paddock");

    var idDog = document.querySelectorAll("#id");

    function selectPlace() {
        return "<select id=\"placeDialogPaddockFromForClient2Paddock\">" +
            "            <option>Площадь Горького</option>" +
            "            <option>Площадь Минина</option>" +
            "            <option>Центр Сормово</option>" +
            "            <option>Московский вокзал</option>" +
            "            <option>Пушкинский парк</option> " +
            "</select>"
    }

    function selectDog() {
        var result = "";
        idDog.forEach(function (item) {
            result += "<input type='checkbox' id='idDogDialogPaddockFromForClient2Paddock' value='" +
                item.innerHTML + "'>" + item.innerHTML + "  ";
        });
        return result;
    }

    function getCountDogs() {
        var ingredients = document.querySelectorAll('#idDogDialogPaddockFromForClient2Paddock');
        var count = 0;
        for (var i = 0; i < ingredients.length; i++) {
            if (ingredients[i].checked) {
                count++;
            }
        }
        return count;
    }

    function getTrueDog() {
        var ingredients = document.querySelectorAll('#idDogDialogPaddockFromForClient2Paddock');
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

    var idWalkerWalkerForClient = document.querySelectorAll("#idWalkerWalkerForClient");

    function selectWalker() {
        var result = "<select id=\"idWalkerWalkerForClient2Paddock\">";
        for (var i = 0; i < idWalkerWalkerForClient.length; i++) {
            result += "<option>" + idWalkerWalkerForClient.item(i).innerHTML + "</option>";
        }
        result += "</select>";
        return result;
    }

    dialog.addPanel("SinglePanel", "<p> Place: " + selectPlace() + "</p>" +
        "<p>Id dog:" + selectDog() + "</p>" +
        "<p>Walking time: <input type='datetime-local' id='walkingTimeDialogPaddockFromForClient2Paddock' min='2020-02-14T08:30' value='2020-03-15T08:30'></p>" +
        "<p>Duration: <input type='number' id='durationDialogPaddockFromForClient2Paddock' value='40'></p>" +
        "<p>Id walker:" + selectWalker() + "</p>", "singlePanel");

    dialog.addButton("Add", function (dialog) {
        var idClient = document.querySelector("#idForClient").innerHTML;
        var place = $("#placeDialogPaddockFromForClient2Paddock").val();
        var walkingTime = $("#walkingTimeDialogPaddockFromForClient2Paddock").val();
        var duration = $("#durationDialogPaddockFromForClient2Paddock").val();
        var status = "open";
        var idWalker = $("#idWalkerWalkerForClient2Paddock").val();
        var countDogs = getCountDogs();
        var idDogs = getTrueDog();
        var wTime = Date.parse(walkingTime);
        var params = {
            "place": place,
            "duration": duration,
            "status": status,
            "idDogs": idDogs
        }
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

    AJS.$('#dialog-button-add-a-paddock-from-for-client').click(function () {
        dialog.gotoPage(0);
        dialog.gotoPanel(0);
        dialog.show();
    });
});