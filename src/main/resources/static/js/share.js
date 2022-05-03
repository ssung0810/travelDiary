// 취소 버튼 시 모달 숨기기
$(".modalCancel").on("click", function() {
    $("#blackScreen").hide();
});

// 공유 게시글 리스트 조회
$("#addSearch, #boards").on("click", function() {
    $("#blackScreen").show();

    var param = {
        type: $("#searchType").val(),
        content: $("#boardSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/shareBoardList",
        data: param,
//        dataType: 'json',
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        console.log(fragment);
        $("#boardListBox").replaceWith(fragment);
    }).fail(function(error) {
        console.log("error");
        console.log(error);
    })
});


// 공유 게시글 리스트 저장
var boards;
$("#shareBoardSave").on("click", function() {
    $("input[name=boardId]:checked").each(function(i) {

    });

    $("#blackScreen").hide();
});