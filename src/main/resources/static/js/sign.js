var main = {
    init : function() {
        var _this = this;

        $("#sign").on("click", function() {
            _this.signCheck();
        });
    },

    signCheck : function() {
        var id = $("#id").val();

        $.ajax({
            type: 'DELETE',
            url: "/board/"+id+"/delete",
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
        }).done(function() {
            alert("글이 삭제되었습니다.");
        }).fail(function(error) {
            alert(JSON.stringify(error));
        })
    }
};

main.init();