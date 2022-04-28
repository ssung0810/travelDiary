$("#addSearch").on("click", function() {
    var param = {
        type: $("#searchType").val(),
        content: $("#boardSearch").val()
    };

    $.ajax({
        type: "POST",
        url: "/share",
        data: param,
        dataType: 'json',
        ContentType: "application/json; charset=UTF-8"
    }).done(function(result) {

    })
});

