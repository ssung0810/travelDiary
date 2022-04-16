var main = {
    init : function() {
        var _this = this;

        $(function() {
            $.datepicker.setDefaults({
                dateFormat: 'yy-mm-dd',
                prevText: '이전 달',
                nextText: '다음 달',
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                dayNames: ['일', '월', '화', '수', '목', '금', '토'],
                dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
                showMonthAfterYear: true,
                yearSuffix: '년',
                beforeShow: function(i) { if ($(i).hasClass('read')) { return false; } }
            });

            $("#date").datepicker({
                dateFormat: "yy-mm-dd"
            });

            $("#searchDate").datepicker({
                dateFormat: "yy-mm-dd"
            });
        })

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
    },
};

main.init();