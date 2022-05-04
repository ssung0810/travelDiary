// 취소 버튼 시 모달 숨기기
$(".modalCancel").on("click", function() {
    $("#blackScreen").hide();
    $("#boardModal").hide();
    $("#memberModal").hide();
});

// 공유 게시글 리스트 조회
$("#addBoard, #boards").on("click", function() {
    $("#blackScreen").show();
    $("#boardModal").show();

    var param = {
        type: $("#boardSearchType").val(),
        content: $("#boardSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/shareBoardList",
        data: param,
//        dataType: 'json',
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#boardListBox").replaceWith(fragment);
    }).fail(function(error) {
        console.log("error");
        console.log(error);
    })
});

// 공유 게시글 리스트 저장
var boards;
$("#shareBoardSave").on("click", function() {
//    $("input[name=boardId]:checked").each(function(i) {
//
//    });

    $("#boardModal").hide();
    $("#blackScreen").hide();
});


// 공유 대상 리스트 조회
$("#members, #addMember").on("click", function() {
    $("#blackScreen").show();
    $("#memberModal").show();

    var param = {
        type: $("#searchType").val(),
        content: $("#memberSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/shareMemberList",
        data: param,
//        dataType: 'json',
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#memberListBox").replaceWith(fragment);
    }).fail(function(error) {
        console.log("error");
        console.log(error);
    })
});

// 공유대상 등록
$("#shareMemberSave").on("click",function() {
    $("#memberModal").hide();
    $("#blackScreen").hide();
});