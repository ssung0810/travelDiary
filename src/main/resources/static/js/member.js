var main = {
    init : function() {
        var _this = this;

        $("#usernameBtn").on("click", function() {
            _this.duplicate();
        });

        $("#image").on("change", function() {
            const upload_file = document.querySelector("#image");
            const upload_img = document.querySelector("#profileUpload");

            if(upload_file.files[0].type.indexOf('image') >= 0) {
                var reader = new FileReader();

                reader.addEventListener('load', () => {
                    upload_img.src = reader.result;
                });

                reader.readAsDataURL(upload_file.files[0]);
            } else {
                alert("이미지만 업로드가 가능합니다.");
                return;
            }
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