AJS.$(document).ready(function() {
    AJS.$('.buttonAcceptPaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idAcceptCancel").innerHTML;
        var status = "accepted";
        var params = {
            "status": status
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restpaddock/1.0/restPaddock/updateStatusPaddock/" + id,
            dataType: "json",
            contentType: "application/json",
            data: json,
            success: function (data) {
                alert("OKAY, accept " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });

    AJS.$('.buttonInProcessPaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idInProcessComplete").innerHTML;
        var status = "in progress";
        var params = {
            "status": status
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restpaddock/1.0/restPaddock/updateStatusPaddock/" + id,
            dataType: "json",
            contentType: "application/json",
            data: json,
            success: function (data) {
                alert("OKAY, in progress " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });

    AJS.$('.buttonCompletePaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idInProcessComplete").innerHTML;
        var status = "complete";
        var params = {
            "status": status
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restpaddock/1.0/restPaddock/updateStatusPaddock/" + id,
            dataType: "json",
            contentType: "application/json",
            data: json,
            success: function (data) {
                alert("OKAY, complete " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });

    AJS.$('.buttonCancelPaddock').click(function () {
        var id = this.parentNode.parentNode.querySelector("#idAcceptCancel").innerHTML;
        var status = "cancel";
        var params = {
            "status": status
        };
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        AJS.$.ajax({
            type: "PUT",
            url: "/rest/restpaddock/1.0/restPaddock/updateStatusPaddock/" + id,
            dataType: "json",
            contentType: "application/json",
            data: json,
            success: function (data) {
                alert("OKAY, cancel " + data);
            },
            error: function (data) {
                alert('ERROR: ' + data);
            }
        });
        return false;
    });
});