(function (window) {
    'use strict';
    var ENTER_KEY = 13; //ENTER Key code
    var STATUS = '';
    var ACTIVE = '/active';
    var COMPLETED = '/completed';

    function init() {
        STATUS = '';
        render(STATUS);
        bindingInitEvent();
        $('#new-todo').focus();
    }

    function render(option) {
        $.ajax({
            url: '/api/todos' + option,
            method: 'GET',
            dataType: 'JSON',
            success: function (data) {
                console.dir(data);
                var cnt;
                var todos = [];
                for (cnt = 0; cnt < data.length; cnt++) {
                    var checked = '';
                    var className = '';
                    if (data[cnt].completed === 1) {
                        className = 'class="completed"';
                        checked = 'checked';
                    }
                    todos.push("<li data-id='" + data[cnt].id + "' " + className + "><div class='view'><input class='toggle' type='checkbox' " + checked + "><label>" + data[cnt].todo + "</label><button class='destroy'></button></div></li>");
                }
                $(".todo-list").html(todos);
                $(".todo-count > strong").html(cnt);
            },
            error: function () {
                alert('error occurred');
                location.href = "./";
            }
        })
    }

    function bindingInitEvent() {
        $("#allBtn").click(function () {
            event.preventDefault();
            $(".filters > li > a.selected").removeClass();
            $("#allBtn").attr('class', 'selected');
            STATUS = '';
            render(STATUS);
        });

        $("#activeBtn").click(function () {
            event.preventDefault();
            $(".filters > li > a.selected").removeClass();
            $("#activeBtn").attr('class', 'selected');
            STATUS = ACTIVE;
            render(STATUS);
        });

        $("#completedBtn").click(function () {
            event.preventDefault();
            $(".filters > li > a.selected").removeClass();
            $("#completedBtn").attr('class', 'selected');
            STATUS = COMPLETED;
            render(STATUS);
        });

        $(".new-todo").keyup(function (e) {
            var $input = $(e.target);
            var text = $input.val().trim();

            if (e.keyCode !== ENTER_KEY || !text) {
                return;
            }

            $.ajax({
                url: '/api/todos',
                method: 'POST',
                data: JSON.stringify({
                    "todo": text
                }),
                headers: {
                    "content-type": "application/json"
                },
                success: function () {
                    render(STATUS);
                },
                error: function () {
                    alert('error occurred');
                    location.href = "./";
                }
            })
            $input.val('');
        });

        $(".clear-completed").click(function () {
            $.ajax({
                url: '/api/todos',
                method: 'DELETE',
                success: function () {
                    render(STATUS);
                },
                error: function () {
                    alert('error occurred');
                    location.href = "./";
                }
            })
        });

        $(".todo-list").on('click', '.toggle', function () {
            var $thisId = $(this).parent().parent().data('id');

            $.ajax({
                url: '/api/todos/' + $thisId,
                method: 'PUT',
                data: JSON.stringify({
                    "completed": this.checked ? 1 : 0
                }),
                headers: {
                    "content-type": "application/json"
                },
                success: function () {
                    render(STATUS);
                },
                error: function () {
                    alert('error occurred');
                    location.href = "./";
                }
            })
        });

        $(".todo-list").on('click', '.destroy', function () {
            var $thisId = $(this).parent().parent().data('id');

            $.ajax({
                url: '/api/todos/' + $thisId,
                method: 'DELETE',
                success: function () {
                    render(STATUS);
                },
                error: function () {
                    alert('error occurred');
                    location.href = "./";
                }
            })
        });
    }

    $(document).ready(function () {
        init();
    });
})
(window);