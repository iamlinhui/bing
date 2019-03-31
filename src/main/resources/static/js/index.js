$(function () {
    $(".card").click(function () {
        var data = $(this).attr("data");
        window.location.href = "/image/" + data;
    });
    $(".card").mouseenter(function () {
        $(this).children(".card-img-overlay").show();

    });
    $(".card").mouseleave(function () {
        $(this).children(".card-img-overlay").hide();
    });
});