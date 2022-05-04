var _BOARD = [];
var _MEMBER = [];

$(document).ready(function() {
    boardSearch();
    memberSearch();
});

// 취소 버튼 시 모달 숨기기
$(".modalCancel").on("click", function() {

    $("#memberModal").hide();
});

$("#boards").on("click", function() {
    $("#blackScreen").show();
    $("#boardModal").show();
});

$("#members").on("click", function() {
    $("#blackScreen").show();
    $("#memberModal").show();
});

$("#addBoard").on("click", function() {
    boardSearch();
});

$("#addMember").on("click", function() {
    memberSearch();
});

// 공유 게시글 리스트 저장
$("#shareBoardSave").on("click", function() {
    _BOARD = [];

    $("input[name=boards]:checked").each(function(i) {
        _BOARD.push($(this).val());
    });

    boardListSelect();

    $("#boardModal").hide();
    $("#blackScreen").hide();
});

// 공유 게시물 리스트 저장취소
$("#shareBoardCancel").on("click", function() {
    $("#blackScreen").hide();
    $("#boardModal").hide();

    boardListSelect();
});

// 공유대상 저장
$("#shareMemberSave").on("click",function() {
    _MEMBER = [];

    $("input[name=members]:checked").each(function(i) {
        _MEMBER.push($(this).val());
    });

    memberListSelect();

    $("#memberModal").hide();
    $("#blackScreen").hide();
});

// 공유 대상 저장취소
$("#shareMemberCancel").on("click", function() {
    $("#blackScreen").hide();
    $("#memberModal").hide();

    memberListSelect();
});

function boardListSelect() {
    $("input[name=boards]").attr("checked", false);

    $("input[name=boards]").each(function() {
        if(_BOARD.indexOf($(this).val()) > -1) {
            $(this).prop("checked", true)
        }
    });
}

function memberListSelect() {
    $("input[name=members]").attr("checked", false);

    $("input[name=members]").each(function() {
        if(_MEMBER.indexOf($(this).val()) > -1) {
            $(this).prop("checked", true)
        }
    });
}

// 공유 게시글 리스트 조회
function boardSearch() {
    var param = {
        type: $("#boardSearchType").val(),
        content: $("#boardSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/shareBoardList",
        data: param,
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#boardListBox").replaceWith(fragment);
        boardListSelect();
    }).fail(function(error) {
        console.log(error);
    })
}


// 공유 대상 리스트 조회
function memberSearch() {
    var param = {
        type: $("#searchType").val(),
        content: $("#memberSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/shareMemberList",
        data: param,
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#memberListBox").replaceWith(fragment);
        memberListSelect();
    }).fail(function(error) {
        console.log(error);
    })
}