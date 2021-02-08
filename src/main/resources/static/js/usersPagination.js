class PaginationUser {


    constructor(page, size, timeInterval) {
        this.page = page;
        this.size = size;
        this.timeInterval = timeInterval;

        this.userService = new UserService();

        if (this.timeInterval == 'week') {
            this.users = this.userService.getUserDtoPaginationByReputationOverWeek(this.page, this.size);
        } else if (this.timeInterval == 'month') {
            this.users = this.userService.getUserDtoPaginationByReputationOverMonth(this.page, this.size);
        }
        //заглушка
        else {
            this.users = this.userService.getUserDtoPaginationByReputationOverMonth(this.page, this.size);
        }
    }

    writeUsers() {

        $('#usersTable').children().remove()

        this.users.then(function (response) {
            for (var i = 0; i < response.items.length; i++) {
                $('#usersTable').append(
                    "<div class=\"col-3 mb-3\">"
                    + "<div class=\"media\">"
                    + "<img width='48' height='48' src=" + response.items[i].linkImage + " class=\"mr-3\"  alt=\"...\">"
                    + "<div class=\"media-body\">"
                    + "<a class=\"mt-0\" href=\"#\">" + response.items[i].fullName + "</a>"
                    + "<div>" + response.items[i].reputation + "</div>"
                    + "</div> </div> </div>");
            }
        })
        this.usersPagesNavigation()
    }

    usersPagesNavigation() {
        var size = this.size;
        var timeInterval = this.timeInterval;

        this.users.then(function (response) {
            var currentPageNumber = response.currentPageNumber;
            var nextPage = response.currentPageNumber + 1;
            var secondNextPage = response.currentPageNumber + 2;
            var totalPageCount = response.totalPageCount;
            var previousPage = response.currentPageNumber - 1;


            $('#usersPageNavigation').children().remove();
            if (currentPageNumber != 1) {
                $('#usersPageNavigation').append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + previousPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()' >Назад</a></li>"
                );
            }

            if (currentPageNumber == totalPageCount) {
                $('#usersPageNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                );
            }

            if (nextPage == totalPageCount) {
                $('#usersPageNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage == totalPageCount) {
                $('#usersPageNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + secondNextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#usersPageNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + secondNextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + totalPageCount + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + totalPageCount + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationUser(" + nextPage + "," + size + "," + "\"" + timeInterval + "\"" + ").writeUsers()'>" + "Далее" + "</a></li>"
                );
            }
        })
    }
}

