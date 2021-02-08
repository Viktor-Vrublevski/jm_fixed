class PaginationQuestionWithoutAnswer {

    constructor(page, size) {
        this.page = page;
        this.size = size;
        this.questionService = new QuestionService();
        this.questionWithoutAnswers = this.questionService.getQuestionWithoutAnswers(this.page, this.size);
    }




    writeQuestionWithoutAnswer() {

        $('.questionWithoutAnswers').children().remove();

        this.questionWithoutAnswers.then(function (response) {

            for (var i = 0; i < response.items.length; i++) {

                    let shuffledNames = response.items[i].listTagDto.map(i => i.name).sort(() => Math.random() - 0.5);
                    let text  = shuffledNames.map(i => `<a href="#" class="tag"> ${i} </a>`).join('');

                $('.questionWithoutAnswers').append(
                    "<div class=\"question-card d-flex\">" +
                    "   <div class=\"question-stats-container\">" +
                    "       <div class=\"stats\">" +
                    "           <div class=\"vote\">" +
                    "               <div class=\"vote-count\">" +
                    "                   <strong>" + response.items[i].countValuable + "</strong>" +
                    "               </div>" +
                    "               <div class=\"view-count-vote\">голосов</div>" +
                    "               <div class=\"view-count-answer\">" +
                    "                   <div class=\"status-unanswered\"><strong>" + response.items[i].countAnswer + "</strong></div>" +
                    "                   <div class=\"view-count\">ответов</div>" +
                    "               </div>" +
                    "               <div class=\"views warm d-flex\">" +
                    "                   <div class=\"views-number\">" + response.items[i].viewCount + "</div>" +
                    "                   <div class=\"views-text\">показов</div>" +
                    "               </div>" +
                    "           </div>" +
                    "       </div>" +
                    "</div>" +
                    "<div class=\"question-details\">" +
                    "   <div class=\"question-title\"><a href=\"api/question/" + response.items[i].id + "\">" + response.items[i].title + "</a></div>" +
                    "   <div class=\"question-text\">" + response.items[i].description +
                    "</div>" +
                    "<div class=\"d-flex item-between\">" +
                    "   <div class=\"w-50 card-body question-tags-container\">" +
                    text +
                    "   </div>" +
                    "<div class=\"user-info\">" +
                    "   <div class=\"user-info-change\">" +
                    "       <a href=\"#\" class=\"user-info-change-link\">изменён " +
                    "           <span class=\"user-info-change-time\"> " +
                    response.items[i].lastUpdateDateTime.substr(5, response.items[i].lastUpdateDateTime.indexOf("T") - 5)
                    + " " +
                    response.items[i].lastUpdateDateTime.substr(response.items[i].lastUpdateDateTime.indexOf("T") + 1, 5)
                    + " </span>" +
                    "       </a>" +
                    "   </div>" +
                    "<div class=\"user-info-gravatar d-flex\">" +
                    "   <a href=\"#\" class=\"user-info-gravatar-link\">" +
                    "       <div class=\"user-info-gravatar-wrapper\">" +
                    "           <img src=\"" + response.items[i].authorImage + "\" alt=\"\" width=\"32\" height=\"32\" class=\"user-info-img\"></div>" +
                    "   </a>" +
                    "<div class=\"user-info-details-wrapper\">" +
                    "   <a class=\"user-info-details-name\" href=\"api/user/" + response.items[i].authorId + "\">" + response.items[i].authorName + "</a>" +
                    "   <div class=\"user-info-stats\">" +
                    "       <span class=\"user-reputation\">" + "!!!" + "</span>" +
                    "       <span class=\"user-gold active-stats\">" + "###" + "</span>" +
                    "       <span class=\"user-silver active-stats\">" + "###" + "</span>" +
                    "       <span class=\"user-bronze active-stats\">" + "###" + "</span>" +
                    "   </div>" +
                    "</div>"
                );
            }
        })
        this.questWithoutAnswerPagesNavigation()
    }

    totalResultCountView() {

        this.questionWithoutAnswers.then(function (response) {
            var totalResultCount = response.totalResultCount;

            $("#totalResultCountView").empty()
            $("#totalResultCountView").append(
                "               <div class=\"totalResultCountView\">" +
                "                   <div class=\"totalResultCountView-count\">" + totalResultCount + "</div>" +
                "                   <div class=\"totalResultCountView-text\">вопросов без принятого ответа или без ответа, за который были отданы голоса</div>" +
                "               </div>"

            );

        })
    }

    questWithoutAnswerPagesNavigation() {
        var size = this.size;

        this.questionWithoutAnswers.then(function (response) {
                var currentPageNumber = response.currentPageNumber;
                var nextPage = response.currentPageNumber + 1;
                var secondNextPage = response.currentPageNumber + 2;
                var totalPageCount = response.totalPageCount;
                var previousPage = response.currentPageNumber - 1;

                $('.questionWithoutAnswerPagination').children().remove();

                if (currentPageNumber != 1) {
                    $('.questionWithoutAnswerPagination').append(
                        "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + previousPage + "," + size + ").writeQuestionWithoutAnswer()'>Назад</a></li>"
                    );
                }

                if (currentPageNumber == totalPageCount) {
                    $('.questionWithoutAnswerPagination').append(
                        "<li class=\"page-item-element active\"><a class=\"page-element-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    );
                }

                if (nextPage == totalPageCount) {
                    $('.questionWithoutAnswerPagination').append(
                        "<li class=\"page-item-element active\"><a class=\"page-element-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + nextPage + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + "Далее" + "</a></li>"
                    );
                }


                if (secondNextPage == totalPageCount) {
                    $('.questionWithoutAnswerPagination').append(
                        "<li class=\"page-item-element active\"><a class=\"page-element-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + nextPage + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + secondNextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + secondNextPage + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + "Далее" + "</a></li>"
                    );
                }


                if (secondNextPage < totalPageCount) {
                    $('.questionWithoutAnswerPagination').append(
                        "<li class=\"page-item-element active\"><a class=\"page-element-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + nextPage + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + secondNextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + secondNextPage + "</a></li>"
                        + "<li class=\"page-item-element\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + totalPageCount + "," + size + ").writeQuestionWithoutAnswer()'>" + totalPageCount + "</a></li>"
                        + "<li class=\"page-item-element\"><a class=\"page-element-link\" href=\"#\" onclick='new PaginationQuestionWithoutAnswer(" + nextPage + "," + size + ").writeQuestionWithoutAnswer()'>" + "Далее" + "</a></li>"
                    );
                }
            }
        )
    }
}