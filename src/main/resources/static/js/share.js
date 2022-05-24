var _BOARD = [];
var _MEMBER = [];

$(document).ready(function() {
    boardSearch();
    memberSearch();
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

    boardListSelect(_BOARD);
    $("#boardSearch").val("");

    $("#boardModal").hide();
    $("#blackScreen").hide();
});

// 공유 게시물 리스트 저장취소
$("#shareBoardCancel").on("click", function() {
    $("#blackScreen").hide();
    $("#boardModal").hide();

    boardListSelect(_BOARD);
    $("#boardSearch").val("");
});

// 공유대상 저장
$("#shareMemberSave").on("click",function() {
    _MEMBER = [];

    $("input[name=members]:checked").each(function(i) {
        _MEMBER.push($(this).val());
    });

    memberListSelect(_MEMBER);

    $("#memberModal").hide();
    $("#blackScreen").hide();
});

// 공유 대상 저장취소
$("#shareMemberCancel").on("click", function() {
    $("#blackScreen").hide();
    $("#memberModal").hide();

    memberListSelect(_MEMBER);
});

function boardListSelect(obj) {
    console.log(obj);
    $("input[name=boards]").attr("checked", false);

    $("input[name=boards]").each(function() {
        if(obj.length > 0 && obj.indexOf($(this).val()) > -1) {
            $(this).prop("checked", true)
        }
    });
}

function memberListSelect(obj) {
    $("input[name=members]").attr("checked", false);

    $("input[name=members]").each(function() {
        if(obj.length > 0 && obj.indexOf($(this).val()) > -1) {
            $(this).prop("checked", true)
        }
    });
}

// 공유 게시글 리스트 조회
function boardSearch() {
    var boardSelectObj = [];

    $("input[name=boards]:checked").each(function(i) {
        boardSelectObj.push($(this).val());
    });

    var param = {
        type: $("#boardSearchType").val(),
        value: $("#boardSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/share/shareBoard",
        data: param,
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#boardListBox").replaceWith(fragment);
        boardListSelect(boardSelectObj);
    }).fail(function(error) {
        console.log(error);
    })
}


// 공유 대상 리스트 조회
function memberSearch() {
    var memberSelectObj = [];

    $("input[name=members]:checked").each(function(i) {
        memberSelectObj.push($(this).val());
    });

    var param = {
        type: $("#searchType").val(),
        value: $("#memberSearch").val()
    };

    $.ajax({
        type: "GET",
        url: "/share/shareMember",
        data: param,
        ContentType: "application/json; charset=UTF-8"
    }).done(function(fragment) {
        $("#memberListBox").replaceWith(fragment);
        memberListSelect(memberSelectObj);
    }).fail(function(error) {
        console.log(error);
    })
}
