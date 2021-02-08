class PaginationTag {

    constructor(page, size, sort, name) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.name = name;

        this.tagService = new TagService();

        if (this.sort === 'name') {
            this.tags = this.tagService.getTagDtoPaginationOrderByAlphabet(this.page, this.size);
        } else if (this.sort === 'new') {
            this.tags = this.tagService.getTagRecentDtoPagination(this.page, this.size);
        }
        else if (this.sort === 'search') {
            this.tags = this.tagService.getTagName(this.name, this.page, this.size);
        }
        else {
            this.tags = this.tagService.getTagListDtoByPopularPagination(this.page, this.size);
        }
    }

    writeTags() {
        $('#tagsTable').children().remove()

        this.tags.then(function (response) {

            for (var i = 0; i < response.items.length; i++) {
                $('#tagsTable').append(
                    " <div class=\"child\">"
                    + "<p class=\"media\">"
                    + "<a href=\"#\" class=\"mb-1\">" + response.items[i].name + "</a>"
                    + "<div><small>" + response.items[i].description + "</small></div>"
                    + "<p>"
                    + "<div class=\"child2\"><small class=\"text-muted\">" + response.items[i].countQuestion + " вопросов" + "</small></div>"
                    + "<div class=\"child2\">"
                    + "<small><a href=\"#\" class=\"text-muted\">" + response.items[i].countQuestionToDay + " задано сегодня, " + "</a></small>"
                    + "<small><a href=\"#\" class=\"text-muted\">" + response.items[i].countQuestionToWeek + " за неделю" + "</a></small>"
                    + "</div> </p> </div>");
            }
        })
        this.tagsPageNavigation()
    }

    writeTopTenTags(){
        $("#topTenTagsBar").empty();
        let topTenTags
        this.tags.then(function (response) {
            topTenTags="<div class=\"nav-col btn-group  btn-block mr-0\">"
            for (var i = 0; i < 5; i++) {
                topTenTags=topTenTags + '<button type=\"button\"  class=\"btn  btn-sm mr-2 mt-2  active \" ' +
                    'tag_id=' + response.items[i].id + '>' + response.items[i].name + '</button>'
            }
            topTenTags = topTenTags + "</div><div class=\"nav-col btn-group  btn-block mr-0\">"
            for (var i = 5; i < 10; i++) {
                topTenTags = topTenTags + '<button type=\"button\"  class=\"btn  btn-sm mr-2 mb-2  active \" ' +
                    'tag_id=' + response.items[i].id + '>' + response.items[i].name + '</button>'
            }
            topTenTags = topTenTags + "</div>"
            $("#topTenTagsBar").append(topTenTags)
        })
    }

    tagsPageNavigation() {
        const size = this.size;
        const sort = this.sort;
        const name = this.name;


        this.tags.then(function (response) {
            const currentPageNumber = response.currentPageNumber;
            const nextPage = response.currentPageNumber + 1;
            const secondNextPage = response.currentPageNumber + 2;
            const totalPageCount = response.totalPageCount;
            const previousPage = response.currentPageNumber - 1;


            $('#tagsPagesNavigation').children().remove();
            if (currentPageNumber !== 1) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + previousPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()' >Назад</a></li>"
                );
            }

            if (currentPageNumber === totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                );
            }

            if (nextPage === totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + "," +  "\""  + name  + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage === totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + secondNextPage + "," + size + ","  + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + ","  + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + secondNextPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + totalPageCount + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + totalPageCount + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + "," + "\""  + name  + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }
        })
    }
}
