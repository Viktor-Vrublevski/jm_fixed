class PaginationQuestion {

    constructor(page, size, type, id) {
        this.page = page;
        this.size = size;
        this.type = type;
        this.id = id;

        this.questionService = new QuestionService();

        if (this.type === 'normal') {
            this.questions = this.questionService.findPagination(this.page, this.size);
        } else if (this.type === 'popular') {
            this.questions = this.questionService.findPaginationPopular(this.page, this.size);
        } else if (this.type === 'withTags') {
                this.questions = this.questionService.getQuestionsWithGivenTags(this.page, this.size, this.id);
        } else if (this.type === 'withoutAnswers') {
            this.questions = this.questionService.getQuestionsWithoutAnswers(this.page, this.size)
        } else if (this.type === 'new') {
            this.questions = this.questionService.findPaginationNew(this.page, this.size);
        } else {
            this.questions = this.questionService.findPagination(this.page, this.size);
        }
    }

    setQuestions() {
        $('#questionsTable').children().remove()
        $('#questionsPagesNavigation').children().remove();



        this.questions.then(function (response) {

            for (var i = 0; i < response.items.length; i++) {
                const date = new Date(response.items[i].persistDateTime)
                const stringDate = ('0' + date.getDate()).slice(-2) + "."
                    + ('0' + (date.getMonth() + 1)).slice(-2) + "."
                    + date.getFullYear() + " " + ('0' + date.getHours()).slice(-2) + ":"
                    + ('0' + date.getMinutes()).slice(-2)

                let shuffledNames = response.items[i].listTagDto.map(i => i.name).sort(() => Math.random() - 0.5);
                let text  = shuffledNames.map(i => `<a href="#" class="tag"> ${i} </a>`).join('');

                $('.questionsTable').append(
                    "<a href=\"api/question/" + response.items[i].id + "\"</a>" +
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
                    "   <div class=\"question-title\"><a href=\"#\">" + response.items[i].title + "</a>" +
                    "<a href=\"api/question/" + response.items[i].id + "\"</a>" +
                    "</div>" +
                    "   <div class=\"question-text\">" + response.items[i].description +
                    "</div>" +
                    "<div class=\"d-flex item-between\">" +
                    "   <div class=\"w-50 card-body question-tags-container\">" +
                    text +
                    "   </div>" +
                    "<div class=\"user-info\">" +
                    "   <div class=\"user-info-change\">" +
                    "       <a href=\"#\" class=\"user-info-change-link\">задан "+ stringDate + " " +
                    "       </a>" +
                    "   </div>" +
                    "<div class=\"user-info-gravatar d-flex\">" +
                    "   <a href=\"#\" class=\"user-info-gravatar-link\">" +
                    "       <div class=\"user-info-gravatar-wrapper\">" +
                    "           <img src=\"" + response.items[i].authorImage + "\" alt=\"\" width=\"32\" height=\"32\" class=\"user-info-img\"></div>" +
                    "   </a>" +
                    "<div class=\"user-info-details-wrapper\">" +
                    "   <a class=\"user-info-details-name\" href=\"#\">" + response.items[i].authorName + "</a>" +
                    "   <div class=\"user-info-stats\">" +
                    "       <span class=\"user-reputation\">" + "!!!" + "</span>" +
                    "       <span class=\"user-gold active-stats\">" + "###" + "</span>" +
                    "       <span class=\"user-silver active-stats\">" + "###" + "</span>" +
                    "       <span class=\"user-bronze active-stats\">" + "###" + "</span>" +
                    "   </div>" +
                    "</div>"
                )
            }
        })
        this.questionsPagesNavigation()
    }

    questionsPagesNavigation() {
        var size = this.size;
        var type = this.type;

        this.questions.then(function (response) {
            var currentPageNumber = response.currentPageNumber;
            var nextPage = response.currentPageNumber + 1;
            var secondNextPage = response.currentPageNumber + 2;
            var totalPageCount = response.totalPageCount;
            var previousPage = response.currentPageNumber - 1;


            $('#questionsPagesNavigation').children().remove();
            if (currentPageNumber != 1) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + previousPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()' >Назад</a></li>"
                );
            }

            if (currentPageNumber == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                );
            }

            if (nextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + secondNextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + secondNextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + totalPageCount + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + totalPageCount + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }
        })
    }
}