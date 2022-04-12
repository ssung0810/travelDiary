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
        }).done(function(data) {
            alert("사용가능한 별명입니다.");
            console.log(data);
        }).fail(function(error) {
            alert("이미 존재하는 별명입니다.");
            $("#username").val("안됨");
            $("validation").val("0");
        })
    }
};

main.init();