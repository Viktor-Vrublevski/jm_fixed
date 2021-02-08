document.querySelector('.tip-button-1').onclick = function () {
    document.querySelector('.tip-text-1').classList.toggle('d-none');
}

document.querySelector('.tip-button-2').onclick = function () {
    document.querySelector('.tip-text-2').classList.toggle('d-none');
}

document.querySelector('.tip-button-3').onclick = function () {
    document.querySelector('.tip-text-3').classList.toggle('d-none');
}

document.querySelector('.tip-button-4').onclick = function () {
    document.querySelector('.tip-text-4').classList.toggle('d-none');
}

let buttonAskQuestion = document.getElementById('buttonAskQuestion');
let newQuestionId = 0;

buttonAskQuestion.onclick = function (e) {
    e.preventDefault();

    let tagNames = $('#tags').val().split(' ');
    let tags = [];

    for (let i = 0; i < tagNames.length; i++) {

        let str = '';

        tags.push(
            {
                id: i + 1,
                name: str + tagNames[i]
            })
    }

    let questionCreateDto = {
        title: $('#questionTitle').val(),
        userId: 153,
        description: 'description',
        tags: tags
    };

    if (!fetch('http://localhost:5557/api/question/add', {
        method: 'POST',
        headers: {
            'content-type': 'application/json;charset=utf-8', 'Authorization': $.cookie("token")
        },
        body: JSON.stringify(questionCreateDto)
    }).then(response => response.json()
    ).then(question => {
        newQuestionId = question.id;
        window.location.href = '/site';
    })) {
        alert('Вопрос не был добавлен');
    }
}

