$(document).ready(function() {

    $('.contwo').click(function() {
        var href = $(this).attr("linkdata");
        console.log(href);
        if(href) {
            window.open(href, '_blank');
        }
    });

});
