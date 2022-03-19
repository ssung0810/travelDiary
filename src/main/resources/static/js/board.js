var main = {
    init : function() {
        var _this = this;

        $("#btn-board-delete").on("click", function() {
            _this.delete();
        });
    },

    delete : function() {
        var id = $("#id").val();

        $.ajax({
            type: "DELETE",
            url: "/board/"+id+"/delete",
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
        }).done(function() {
            alert("글이 삭제되었습니다.");
//            window.location.href="/board/privateBoardList"
        }).fail(function(error) {
            alert(JSON.stringify(error));
        })
    }
};

main.init();