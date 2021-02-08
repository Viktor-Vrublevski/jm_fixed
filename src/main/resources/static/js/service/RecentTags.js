$(document).ready(function (){
    let mainTag = new TagService().getTagListDtoByPopularPagination(1,20).then(function (response) {
        for (let i = 0; i < response.items.length; i++) {
            $('#mainTag').append(
                '<tr>'
                + '<td>'
                + '<div class="mb-1" onclick="new PaginationQuestionForMainPage(1,10,`withTags`,'+ response.items[i].id +').setQuestions()">'
                + response.items[i].name
                + '</div>'
                + '<span id="spanX">'
                +     '<span  class="item-multiplier-x">x</span>'
                +     '<span  class="item-multiplier-count ">'+ response.items[i].countQuestionToDay + '</span>'
                + '</span>'
                + '</td>'
                +'</tr>'
            );
        }
    })
})

$(document).ready(function (){
    let questionTag = new TagService().getTagListDtoByPopularPagination(1,20).then(function (response) {
        for (let i = 0; i < response.items.length; i++) {
            $('#questionTag').append(
                '<tr>'
                + '<td>'
                + '<div class="mb-1" onclick="new PaginationQuestion(1,10,`withTags`,'+ response.items[i].id +').setQuestions()">'
                + response.items[i].name
                + '</div>'
                + '<span id="spanX">'
                +     '<span  class="item-multiplier-x">x</span>'
                +     '<span  class="item-multiplier-count ">'+ response.items[i].countQuestionToDay + '</span>'
                + '</span>'
                + '</td>'
                +'</tr>'
            );
        }
    })
})

