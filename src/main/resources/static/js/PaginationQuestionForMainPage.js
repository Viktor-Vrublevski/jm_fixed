class PaginationQuestionForMainPage {

    constructor(page, size, type, id) {
        this.page = page;
        this.size = size;
        this.type = type;
        this.id = id;

        this.questionService = new QuestionService();

        if (this.type == 'new') {
            this.questions = this.questionService.findPaginationNew(this.page, this.size);
        } else if (this.type == 'popular') {
            this.questions = this.questionService.findPaginationPopularOverPeriod(this.page, this.size);
        } else if (this.type == 'popularWeek') {
            this.questions = this.questionService.findPaginationPopularOverPeriod(this.page, this.size, "week");
        } else if (this.type == 'popularMonth') {
            this.questions = this.questionService.findPaginationPopularOverPeriod(this.page, this.size, "month");
        } else if (this.type == 'withTags') {
            this.questions = this.questionService.getQuestionsWithGivenTags(this.page, this.size, this.id);
        } else {
            this.questions = this.questionService.findPagination(this.page, this.size);
        }

        // this.questions = this.questionService.findPaginationNew(this.page, this.size);

    }

    setQuestions() {
        $('#questionsAll').children().remove()
        $('#buttonToQuestionArea').children().remove()

        this.questions.then(function (response) {
            for (var i = 0; i < response.items.length; i++) {
                const date = new Date(response.items[i].persistDateTime)
                const stringDate = ('0' + date.getDate()).slice(-2) + "."
                                 + ('0' + (date.getMonth() + 1)).slice(-2) + "."
                                 + date.getFullYear()
                $('#questionsAll').append(
                    "        <a href=\"api/question/" + response.items[i].id + "\" class=\"list-group-item list-group-item-action h-100\">\n" +
                    "            <div class=\"row align-items-center h-100\">\n" +
                    "                <div class=\"col-sm-2 mx-auto\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].countValuable +"<br /> голосов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].countAnswer +"<br /> ответов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].viewCount +"<br /> показов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <div class=\"col-sm-10\">\n" +
                    "                    <div class=\"d-flex w-100 justify-content-between\">\n" +
                    "                        <h5 href=\"#\" class=\"mb-1\">" + response.items[i].title + "</h5>\n" +
                    "                        <small> задан "+ stringDate + "</small>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"nav-col btn-group  btn-block mr-0   \">\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[0].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn btn-sm\">" + response.items[i].listTagDto[1].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn btn-sm\">" + response.items[i].listTagDto[2].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm \">" + response.items[i].listTagDto[3].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm overflow-hidden\">" + response.items[i].listTagDto[4].name + "</button>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </a>"
                )
            }
            $('#buttonToQuestionArea').append(
                "            <div class=\"text-right\">\n" +
                "                <a type=\"button\" class=\"btn btn-primary\" href=\"/questionAria\" id=\"otherQuest\">Посмотреть другие вопросы</a>\n" +
                "            </div>"
            )
        })
    }
}
