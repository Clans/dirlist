jQuery(function () {
    $("#expList li").children('ul').hide();
    $("#expList li").on('click', function (event) {
        $(this).children('ul').stop().slideToggle(350);
        $(this).has('ul').children(':first-child').toggleClass('expanded');
        event.stopPropagation();
    });
    $('#expList li').has('ul').children(':first-child').addClass('collapsed');

    $('#expandList').click(function (event) {
        $("#expList li").children('ul').show();
        $('#expList li').has('ul').children(':first-child').addClass('expanded');
    });

    $('#collapseList').click(function () {
        $("#expList li").children('ul').hide();
        $('#expList li').has('ul').children(':first-child').removeClass('expanded');
    });

    var resetListState = function () {
        $('.found').each(function () {
            $(this).replaceWith(this.childNodes);
        });
        $('#expList .visible, #expList .hidden').removeClass('visible hidden');
    };

    $('#listContainer').on('click', '.close-icon', function () {
        resetListState();
    });

    // Filter
    $('.search-box').keyup(function () {
        var $el = $(this);
        if ($el.val()) {                                // Is input.search-box not empty
            $('#expList .file, #listContainer .folder')
                .parents('li')
                .addClass('hidden')                 // All elements will be hidden
                .end()                              // Go to selectors
                .each(function () {
                    var $elem = $(this);
                    var textArr = $elem.text().split($el.val());
                    if (textArr[1]) {
                        var text = textArr[0];
                        for (var i = 1, length = textArr.length; i < length; i++) {
                            text += "<span class='found'>" + $el.val() + "</span>" + textArr[i];
                        }
                        $elem.html(text);
                        $elem.parents('li').removeClass('hidden').addClass('visible');
                    } else {
                        $elem.closest('li').removeClass('visible');
                    }
                });
        } else {
            resetListState();
        }
    });
});