$(document).ready(function() {
    // 공유폴더 리스트 가져오기
    var shareId = $("#shareId").val();

    $.ajax({
        type: "GET",
        url: "/share/api/shareList",
        ContentType: "application/json; charset=UTF-8"
    }).done(function(result) {
        for(var i=0; i<result.length; i++) {
            if(shareId == result[i].id) {
                $("#shareList").append("<option selected value="+result[i].id+">"+result[i].title+"</option>");
            } else {
                $("#shareList").append("<option value="+result[i].id+">"+result[i].title+"</option>");
            }
        }
    }).fail(function(error) {
        console.log(error);
    });


    // 공유폴더 선택 시 게시글 내용 전환하기
    $("#shareList").on("change", function() {
        var shareId = $("#shareList option:selected").val();

        if(shareId == 0) {
            window.location.href = "/board/privateBoardList";
        } else {
            window.location.href = "/share/"+shareId;
        }

//        $.ajax({
//            type: "GET",
//            url: "/shareBoard/"+shareId,
//            dataType: 'json',
//            ContentType: "application/json; charset=UTF-8"
//        }).done(function(result) {
//            console.log(result);
//        }).fail(function(error) {
//            console.log(error);
//        });
    });
});