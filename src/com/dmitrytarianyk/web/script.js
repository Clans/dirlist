$(function () {
    $("#expList li > ul").hide();
    $("#expList li").click(function (event) {
        $(this).children('ul').stop().slideToggle(250);
        $(this).has('ul').children(':first-child').toggleClass('expanded');
        event.stopPropagation();
    });
    $('#expList li > div.folder').addClass('collapsed');
});

$(function () {
    $('#expandList').click(function (event) {
        $("#expList li > ul").show();
        $('#expList li > div.folder').addClass('expanded');
    });
});

$(function () {
    $('#collapseList').click(function() {
        $("#expList li > ul").hide();
        $('#expList li > div.folder').removeClass('expanded');
    });
});