$(document).ready(function() {
    // 공유폴더 리스트 가져오기
    var memberId = $("#memberId").val();

    $.ajax({
        type: "GET",
        url: "/shareList",
        data: memberId,
        dataType: 'json',
        ContentType: "application/json; charset=UTF-8"
    }).done(function(result) {
        for(var i=0; i<result.length; i++) {
            $("#shareList").append("<option value="+result[i].id+">"+result[i].title+"</option>");
        }
    }).fail(function(error) {
        console.log(error);
    });


    // 공유폴더 선택 시 게시글 내용 전환하기
    $("#shareList").on("change", function() {
        var shareId = $("#shareList option:selected").val();

        $.ajax({
            type: "GET",
            url: "/shareBoard/"+shareId,
            dataType: 'json',
            ContentType: "application/json; charset=UTF-8"
        }).done(function(result) {
            console.log(result);
        }).fail(function(error) {
            console.log(error);
        });
    });
});