var main = {
    init : function() {
        var _this = this;

        $("#usernameBtn").on("click", function() {
            _this.duplicate();
        });
    },

    duplicate : function() {
        var username = $("#username").val();

        $.ajax({
            type: 'POST',
            url: "/member/api/duplicate",
            data: username,
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                alert("사용할 수 있는 별명입니다.");
                alert(data.result);
            }
//        }).done(function(data) {
//            alert("사용할 수 있는 별명입니다.");
//        }).fail(function(error) {
//            alert("이미 존재하는 별명입니다.");
//            $("#username").val("ddd");
        })
    }
};

main.init();