var main = {
    init : function() {
        var _this = this;

        $("#usernameBtn").on("click", function() {
            _this.duplicate();
        });

//        $('#password, #password_check').change(function() {
//            console.log("sss");
//            let password = $("#password").val();
//            let password_check = $("#password_check").val();
//
//            console.log(password);
//            console.log(password_check);
//
//            if(password == password_check) {
//                $("#password_check_error").val("");
//            } else {
//                $("#password_check_error").val("비밀번호가 일치하지 않습니다.222");
//            }
//        });

//        $("#sign").on("click", function() {
//            let username_validation = $("username_validation").val();
//
//            if(username_validation == 0) {
//                alert("별명 중복체크는 필수입니다.");
//            } else {
//                $("#sign").submit();
//            }
//        });
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
            if(data == true) {
                alert("사용가능한 별명입니다.");
                $("#username_validation").val("1");
            } else {
                alert("이미 존재하는 별명입니다.");
                $("#username_validation").val("0");
            }
        }).fail(function(error) {
            console.log(error);
        })
    }
};

main.init();