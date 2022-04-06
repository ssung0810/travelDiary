var main = {
    init : function() {
        var _this = this;

        $("#btn-board-delete").on("click", function() {
            _this.delete();
        });
    },

    delete : function() {
        var deleteCk = confirm("삭제하시겠습니까?");

        if(deleteCk == false) return;

        var id = $("#id").val();

        $.ajax({
            type: 'DELETE',
            url: "/board/"+id,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
        }).done(function() {
            alert("글이 삭제되었습니다.");
            window.location.href = '/board/privateBoardList';
        }).fail(function(error) {
            alert("삭제에 실패했습니다.");
            alert(JSON.stringify(error));
        })
    }
};

main.init();