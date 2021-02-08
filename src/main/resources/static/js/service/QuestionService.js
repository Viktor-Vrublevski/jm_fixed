class QuestionService {
    deleteQuestionById(id) {
        fetch('/api/question/' + id + '/delete', {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(function(response) {
            if (!response.ok) {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
            return response.json();
        }).catch(error => error.response.then(message => console.log(message)));
    }



    setTagForQuestion(id, tagDto) {
        fetch('/api/question/' + id + '/tag/add', {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            },
            body: JSON.stringify(tagDto)
        }).then(function(response) {
            if (!response.ok) {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
            return response.json();
        }).catch(error => error.response.then(message => console.log(message)));
    }

    getQuestionById(id) {
        let query = '/api/question/' + id;
        return this.getResponseQuestion(query);
    }

    findPagination(page, size) {
        let query = '/api/question/?page=' + page + '&size=' + size;
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response =>  {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch( error => error.response.then(message => console.log(message)));
    }

    findPaginationNew(page, size) {
        let query = '/api/question/order/new?page=' + page + '&size=' + size;
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response =>  {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch( error => error.response.then(message => console.log(message)));
    }

    findPaginationPopularOverPeriod(page, size, period='') {
        let query = '/api/question/popular/' + period + '?page=' + page + '&size=' + size;

        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        })
            .then(response =>  {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text();
                    throw error;
                }
            }).catch( error => error.response.then(message => console.log(message)));
    }

    findPaginationPopular(page, size) {
        let query = '/api/question/popular/?page=' + page + '&size=' + size;
        return fetch(query,{
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        })
            .then(response =>  {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text();
                    throw error;
                }
            }).catch( error => error.response.then(message => console.log(message)));
    }

    getQuestionsWithGivenTags(page, size, id) {
        let query = '/api/question/withTags?page=' + page + '&size=' + size + '&tagIds=' + id;
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response =>  {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch( error => error.response.then(message => console.log(message)));
    }

    getQuestionsWithoutAnswers(page, size) {
        let query = '/api/question/withoutAnswer?page=' + page + '&size=' + size;
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response =>  {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch( error => error.response.then(message => console.log(message)));
    }

    getResponseQuestion(query) {
        let result = new Array();
        fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        })
            .then(response => {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text();
                    throw error;
                }
            })
            .then(entity => result.push.apply(result, entity.items))
            .catch(error => error.response.then(message => console.log(message)));
        return result;
    }

    getQuestionWithoutAnswers(page, size) {
        let query = '/api/question/withoutAnswer?page=' + page + '&size=' + size;
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response => {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch(error => error.response.then(message => console.log(message)));
        return fetch(query,{
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        })
            .then(response => {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text();
                    throw error;
                }
            }).catch(error => error.response.then(message => console.log(message)));
    }
}